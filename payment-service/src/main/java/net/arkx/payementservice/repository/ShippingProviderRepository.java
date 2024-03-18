package net.arkx.payementservice.repository;

import net.arkx.payementservice.entities.ShippingProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingProviderRepository extends JpaRepository<ShippingProvider, Long> {
}
