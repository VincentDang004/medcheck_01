package com.medcheck.service;

import com.medcheck.model.Hotline;
import com.medcheck.repository.HotlineRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HotlineService {

    private final HotlineRepository repository;

    public HotlineService(HotlineRepository repository) {
        this.repository = repository;
    }

    public List<Hotline> getPublicHotlines() {
        return repository.findByStatusTrueOrderByDisplayOrderAscIdAsc();
    }

    public List<Hotline> getAllHotlinesAdmin() {
        return repository.findAllByOrderByDisplayOrderAscIdAsc();
    }

    public Hotline getHotline(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void saveHotline(Hotline hotline) {
        repository.save(hotline);
    }

    public void toggleStatus(Long id) {
        Hotline hotline = getHotline(id);
        if (hotline != null) {
            hotline.setStatus(!hotline.isStatus());
            repository.save(hotline);
        }
    }

    public void deactivate(Long id) {
        Hotline hotline = getHotline(id);
        if (hotline != null) {
            hotline.setStatus(Boolean.FALSE);
            repository.save(hotline);
        }
    }

    @Transactional
    public void seedDefaultsIfEmpty() {
        if (repository.count() > 0) {
            return;
        }

        repository.save(build(1, "Cấp cứu y tế", "115",
                "Gọi khi cần cấp cứu khẩn cấp, tai nạn, nguy kịch.", "Trung tâm Cấp cứu", "Toàn quốc", null));
        repository.save(build(2, "Cảnh sát", "113",
                "Gọi khi cần hỗ trợ an ninh, trật tự, báo tin khẩn.", "Công an", "Toàn quốc", null));
        repository.save(build(3, "Cứu hỏa", "114",
                "Gọi khi có cháy nổ, sự cố cần cứu hộ cứu nạn.", "PCCC", "Toàn quốc", null));
    }

    private Hotline build(
            int order,
            String name,
            String phone,
            String description,
            String organization,
            String region,
            String websiteUrl) {
        Hotline hotline = new Hotline();
        hotline.setDisplayOrder(order);
        hotline.setName(name);
        hotline.setPhone(phone);
        hotline.setDescription(description);
        hotline.setOrganization(organization);
        hotline.setRegion(region);
        hotline.setWebsiteUrl(websiteUrl);
        hotline.setStatus(Boolean.TRUE);
        return hotline;
    }
}

