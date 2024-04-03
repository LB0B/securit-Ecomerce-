package net.arkx.payementservice.repository;

import net.arkx.payementservice.entities.Payment;
import net.arkx.payementservice.entities.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByPaymentMethod(PaymentMethod paymentMethod);

}
