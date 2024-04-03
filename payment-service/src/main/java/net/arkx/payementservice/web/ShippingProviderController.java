package net.arkx.payementservice.web;

import net.arkx.payementservice.entities.Shipping;
import net.arkx.payementservice.entities.ShippingProvider;
import net.arkx.payementservice.service.ShippingProviderService;
import net.arkx.payementservice.service.ShippingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("/shippingProvider")
public class ShippingProviderController {
    private final ShippingProviderService shippingProviderService;

    public ShippingProviderController(ShippingProviderService shippingProviderService) {
        this.shippingProviderService = shippingProviderService;
    }

    //Add new Shipping Provider
    @PostMapping("/createNewShippingProvider")
    public void addNewShippingProvider(@RequestBody ShippingProvider shippingProvider){
        shippingProviderService.addNewShippingProvider(shippingProvider);

    }

    //Update Shipping Provider Name
    @PutMapping("/updateProviderName/{idProvider}")
    public ShippingProvider updateNameProvider(@PathVariable Long idProvider, String nameProvider){
        return shippingProviderService.updateShippingProviderName(nameProvider,idProvider);
    }

    //Delete Shipping Provider By id
    @DeleteMapping("/deleteSippingProvider/{idToRemove}")
    public void removeShippingMethod(@PathVariable Long idToRemove){
        shippingProviderService.deleteShippingProvider(idToRemove);
    }

    //Get Shipping Provider By id
    @GetMapping("getProvider/{id}")
    public ShippingProvider getProvider(@PathVariable Long id){
        return shippingProviderService.getShippingProviderById(id);
    }
    //Get all Shipping Provider
    @GetMapping
    public List<ShippingProvider> getAllProviders(){
        return shippingProviderService.getAllProviders();
    }

}
