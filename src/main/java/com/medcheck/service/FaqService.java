package com.medcheck.service;

import com.medcheck.model.Faq;
import com.medcheck.repository.FaqRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FaqService {

    private final FaqRepository repository;

    public FaqService(FaqRepository repository) {
        this.repository = repository;
    }

    public List<Faq> getPublicFaqs() {
        return repository.findByStatusTrueOrderByDisplayOrderAscIdAsc();
    }

    public List<Faq> getAllFaqsAdmin() {
        return repository.findAllByOrderByDisplayOrderAscIdAsc();
    }

    public Faq getFaq(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void saveFaq(Faq faq) {
        repository.save(faq);
    }

    public void toggleStatus(Long id) {
        Faq faq = getFaq(id);
        if (faq != null) {
            faq.setStatus(!faq.isStatus());
            repository.save(faq);
        }
    }

    public void deactivate(Long id) {
        Faq faq = getFaq(id);
        if (faq != null) {
            faq.setStatus(Boolean.FALSE);
            repository.save(faq);
        }
    }

    @Transactional
    public void seedDefaultsIfEmpty() {
        if (repository.count() > 0) {
            return;
        }

        repository.save(build(1, "MedCheck là gì?",
                "MedCheck là hệ thống giúp cộng đồng tra cứu thông tin thuốc và gửi báo cáo nghi ngờ thuốc giả để admin kiểm duyệt."));
        repository.save(build(2, "Tôi có thể tra cứu thuốc bằng cách nào?",
                "Bạn có thể nhập tên thuốc hoặc mã (SKU/barcode) trên trang chủ để tìm kiếm. Bấm vào kết quả để xem chi tiết."));
        repository.save(build(3, "Khi nào tôi nên gửi báo cáo thuốc giả?",
                "Khi phát hiện dấu hiệu bất thường (bao bì, tem, chất lượng, nguồn gốc…), bạn có thể gửi báo cáo kèm thông tin liên hệ để admin xác minh."));
        repository.save(build(4, "Tại sao danh mục/thuốc không hiển thị?",
                "Các mục có thể đang tạm tắt (status=false) hoặc thuốc chưa được kích hoạt. Bạn có thể kiểm tra lại ở trang admin."));
    }

    private Faq build(int order, String question, String answer) {
        Faq faq = new Faq();
        faq.setDisplayOrder(order);
        faq.setQuestion(question);
        faq.setAnswer(answer);
        faq.setStatus(Boolean.TRUE);
        return faq;
    }
}

