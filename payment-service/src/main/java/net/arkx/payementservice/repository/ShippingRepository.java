package net.arkx.payementservice.repository;

import net.arkx.payementservice.entities.Shipping;
import net.arkx.payementservice.entities.ShippingProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShippingRepository extends JpaRepository<Shipping,Long> {
    List<Shipping> findByShippingProviderId(Long shippingProviderId);
    List<Shipping> findByShippingProvider(ShippingProvider shippingProvider);



}
