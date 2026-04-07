package com.medcheck.service;

import com.medcheck.model.User;
import com.medcheck.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomOAuth2UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        String email = oauth2User.getAttribute("email");
        if (email == null || email.isBlank()) {
            OAuth2Error error = new OAuth2Error("invalid_user_info", "Google account did not return an email", null);
            throw new OAuth2AuthenticationException(error);
        }

        User localUser = userRepository.findByUsername(email)
                .orElseGet(() -> createGoogleUser(email));

        if (localUser.isBlocked()) {
            throw new LockedException("Tài khoản đã bị khóa vì nghi ngờ spam.");
        }

        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();
        if (userNameAttributeName == null || userNameAttributeName.isBlank()) {
            userNameAttributeName = "email";
        }

        return new DefaultOAuth2User(
                List.of(new SimpleGrantedAuthority(localUser.getRole())),
                oauth2User.getAttributes(),
                userNameAttributeName);
    }

    private User createGoogleUser(String email) {
        User user = new User();
        user.setUsername(email);
        user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
        user.setRole("USER");
        user.setBlocked(false);
        return userRepository.save(user);
    }
}
