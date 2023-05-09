package net.nvsoftware.iPaymentService.repository;

import net.nvsoftware.iPaymentService.entity.PaymentEntity;
import net.nvsoftware.iPaymentService.model.PaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    PaymentEntity findByOrderId(long orderId);
}
