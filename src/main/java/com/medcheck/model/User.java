package com.medcheck.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Size(min = 3, max = 100, message = "Tên đăng nhập phải từ 3 đến 100 ký tự")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    @Column(nullable = false)
    private String password;

    private String role;
    private Boolean blocked = Boolean.FALSE;
    private Integer spamStrike = 0;
    private Integer reportCount = 0;
    private LocalDateTime lastReportAt;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isBlocked() {
        return Boolean.TRUE.equals(blocked);
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public int getSpamStrike() {
        return spamStrike == null ? 0 : spamStrike;
    }

    public void setSpamStrike(Integer spamStrike) {
        this.spamStrike = spamStrike;
    }

    public int getReportCount() {
        return reportCount == null ? 0 : reportCount;
    }

    public void setReportCount(Integer reportCount) {
        this.reportCount = reportCount;
    }

    public LocalDateTime getLastReportAt() {
        return lastReportAt;
    }

    public void setLastReportAt(LocalDateTime lastReportAt) {
        this.lastReportAt = lastReportAt;
    }
}
