package net.arkx.payementservice.web;

import net.arkx.payementservice.entities.Shipping;
import net.arkx.payementservice.exceptions.entityExceptions.EntityNotFoundException;
import net.arkx.payementservice.service.ShippingService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController @RequestMapping("/shippings")
public class ShippingController {
    private final ShippingService shippingService;

    public ShippingController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }
    //Get shipping by Id
    @GetMapping("/{id}")
    public Shipping getShippingById(@PathVariable Long id){
        return shippingService.findShippingById(id);
    }

    //Get Shipping by provider
    @GetMapping("/shippingProvider/{providerId}")
    public List<Shipping> getShippingByShippingProviderId(@PathVariable Long providerId) {
        return shippingService.findShippingByShippingProviderId(providerId);
    }

    //Create new Shipping
    @PostMapping("/createShipping")
    public void createNewShipping(@Validated  Long trackingNumber, Long orderId, Long shippingProviderId){

            shippingService.createNewShipping(trackingNumber, orderId, shippingProviderId);

    }
    //Update Shipping
    @PutMapping("/updateShipping/{shippingId}")
    public void updateShipping(@PathVariable Long shippingId, @RequestBody Map<String, Long> requestBody) {
        Long newOrderId = requestBody.get("newOrderId");
        Long newTrackingNumber = requestBody.get("newTrackingNumber");
        Long newProviderId = requestBody.get("newProviderId");

        shippingService.updateShippingDetails(shippingId, newOrderId, newTrackingNumber, newProviderId);
    }
    //Delete Shipping
    @DeleteMapping("/deleteShipping/{id}")
    public void removeShippingById(@PathVariable Long id){
        shippingService.deleteShippingById(id);
    }
}
