package net.arkx.payementservice.repository;

import net.arkx.payementservice.entities.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingRepository extends JpaRepository<Shipping,Long> {
}
