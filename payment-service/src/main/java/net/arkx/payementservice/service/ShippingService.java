package net.arkx.payementservice.service;

import net.arkx.payementservice.entities.Payment;
import net.arkx.payementservice.entities.Shipping;
import net.arkx.payementservice.exceptions.orders.OrderNotFoundException;
import net.arkx.payementservice.exceptions.payments.PaymentNotFoundException;
import net.arkx.payementservice.exceptions.shipping.ShippingNotFoundException;
import net.arkx.payementservice.model.Order;
import net.arkx.payementservice.orders.OrderRestClient;
import net.arkx.payementservice.repository.ShippingRepository;
import org.springframework.stereotype.Service;

@Service
public class ShippingService {
    private final ShippingRepository shippingRepository;
    private final OrderRestClient orderRestClient;

    public ShippingService(ShippingRepository shippingRepository, OrderRestClient orderRestClient) {
        this.shippingRepository = shippingRepository;
        this.orderRestClient = orderRestClient;
    }


    public Shipping findShippingById(Long id) {
        // Récupérer l'objet Shipping par son identifiant
        Shipping shipping = shippingRepository.findById(id)
                .orElseThrow(() -> new ShippingNotFoundException("Shipping not found for id: " + id));

        // Récupérer l'objet Order associé à l'objet Shipping
        Order order = orderRestClient.findOrderById(shipping.getOrderId());
        if (order == null) {
            throw new OrderNotFoundException("Order not found for shipping with id: " + id);
        }

        // Mettre à jour l'objet Shipping avec l'objet Order récupéré
        shipping.setOrder(order);

        return shipping;
    }



}
