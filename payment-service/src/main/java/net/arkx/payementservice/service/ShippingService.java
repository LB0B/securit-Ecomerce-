package net.arkx.payementservice.service;

import net.arkx.payementservice.entities.Shipping;
import net.arkx.payementservice.entities.ShippingProvider;
import net.arkx.payementservice.exceptions.entityExceptions.EntityNotFoundException;
import net.arkx.payementservice.model.Order;
import net.arkx.payementservice.orders.OrderRestClient;
import net.arkx.payementservice.repository.ShippingProviderRepository;
import net.arkx.payementservice.repository.ShippingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShippingService {
    private final ShippingRepository shippingRepository;
    private final ShippingProviderRepository shippingProviderRepository;
    private final OrderRestClient orderRestClient;

    public ShippingService(ShippingRepository shippingRepository, ShippingProviderRepository shippingProviderRepository, OrderRestClient orderRestClient) {
        this.shippingRepository = shippingRepository;
        this.shippingProviderRepository = shippingProviderRepository;

        this.orderRestClient = orderRestClient;
    }
    //Get Shipping by Id

    public Shipping findShippingById(Long id) {

        Shipping shipping = shippingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Shipping not found for id: " + id));

        Order order = orderRestClient.findOrderById(shipping.getOrderId());
        if (order == null) {
            throw new EntityNotFoundException("Order not found for shipping with id: " + id);
        }
        shipping.setOrder(order);

        return shipping;
    }
    //Get Shipping by provider
    public List<Shipping> findShippingByShippingProviderId(Long providerId){
        List<Shipping> shippingProvider =  shippingRepository.findByShippingProviderId(providerId);
        for(Shipping shipping:shippingProvider) {
            Long orderId = shipping.getOrderId();
            Order order = orderRestClient.findOrderById(orderId);
            shipping.setOrder(order);
        }
    return shippingProvider;
    }


    //Create a new Shipping
    public void createNewShipping(Long trackingNumber, Long orderId, Long shippingProviderId) {
        if (trackingNumber == null || orderId == null || shippingProviderId == null) {
            throw new EntityNotFoundException("Some args is null");
        }
        Shipping shipping = new Shipping();
        shipping.setTrackingNumber(trackingNumber);
        shipping.setOrderId(orderId);

        ShippingProvider shippingProvider = new ShippingProvider();
        shippingProvider.setId(shippingProviderId);

        shipping.setShippingProvider(shippingProvider);
        shippingRepository.save(shipping);
    }

    //Update Shipping
    public void updateShippingDetails(Long shippingId, Long orderId, Long trackingNumber, Long providerId) {
        Shipping theShipping = shippingRepository.findById(shippingId)
                .orElseThrow(() -> new EntityNotFoundException("Shipping not found for id: " + shippingId));

        Order order = orderRestClient.findOrderById(orderId);
        if (order != null) {
            theShipping.setOrderId(orderId);
            theShipping.setTrackingNumber(trackingNumber);

            if (providerId != null) {
                ShippingProvider newProvider = shippingProviderRepository.findById(providerId)
                        .orElseThrow(() -> new EntityNotFoundException("Shipping Provider not found for id: " + providerId));
                theShipping.setShippingProvider(newProvider);
            }

            shippingRepository.save(theShipping);
        } else {
            throw new EntityNotFoundException("Order does not exist with Id: " + orderId);
        }
    }
    //Delete Shipping by Id

    public void deleteShippingById(Long id){
        Shipping shippingToDelete = shippingRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Shipping not found with Id: "+id));
        shippingRepository.delete(shippingToDelete);
    }






}
