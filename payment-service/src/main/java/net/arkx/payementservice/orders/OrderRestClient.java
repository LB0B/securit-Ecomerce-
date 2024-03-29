package net.arkx.payementservice.orders;

import net.arkx.payementservice.model.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ORDER-SERVICE")
public interface OrderRestClient {
    @GetMapping("/orders/{id}")
    Order findOrderById(@PathVariable Long id);
}
