package net.nvsoftware.iPaymentService.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long paymentId;
    private long orderId;
    private String PaymentMode;
    private String referenceNumber;
    private long totalAmount;
    private Instant paymentDate;
    private String PaymentStatus;

}
