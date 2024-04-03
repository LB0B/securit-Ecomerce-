package net.arkx.payementservice.service;

import net.arkx.payementservice.entities.PaymentMethod;
import net.arkx.payementservice.entities.Shipping;
import net.arkx.payementservice.entities.ShippingProvider;
import net.arkx.payementservice.exceptions.entityExceptions.EntityNotFoundException;
import net.arkx.payementservice.repository.ShippingProviderRepository;
import net.arkx.payementservice.repository.ShippingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShippingProviderService {
    private final ShippingProviderRepository shippingProviderRepository;
    private final ShippingRepository shippingRepository;

    public ShippingProviderService(ShippingProviderRepository shippingProviderRepository, ShippingRepository shippingRepository) {
        this.shippingProviderRepository = shippingProviderRepository;
        this.shippingRepository = shippingRepository;
    }

    //Add new Shipping Provider
    public void addNewShippingProvider(ShippingProvider shippingProvider){
        shippingProviderRepository.save(shippingProvider);
    }
    //Update Shipping Method name
    public ShippingProvider updateShippingProviderName(String newName, Long idShipping){
        ShippingProvider shippingProvider = shippingProviderRepository.findById(idShipping)
                .orElseThrow(()->new EntityNotFoundException("Shipping provider not Found with id: "+idShipping));
        shippingProvider.setName(newName);
        return shippingProviderRepository.save(shippingProvider);
    }
    //Delete Shipping Provider
    public void deleteShippingProvider(Long idToDelete ){
        ShippingProvider shippingProviderToDelete = shippingProviderRepository.findById(idToDelete)
                .orElseThrow(()->new EntityNotFoundException("Can't find a shipping provider with Id: "+idToDelete));
        List<Shipping> shippingsWithShippingProvider = shippingRepository.findByShippingProvider(shippingProviderToDelete);
        for(Shipping shipping:shippingsWithShippingProvider){
            shipping.setShippingProvider(null);
            shippingRepository.save(shipping);
        }
        shippingProviderRepository.delete(shippingProviderToDelete);
    }

    //Get Shipping Provider By id
    public ShippingProvider getShippingProviderById(Long ProviderId){
        return shippingProviderRepository.findById(ProviderId).orElseThrow(()->new EntityNotFoundException("This provider doest not exist with Id : +ProviderId"));
    }

    //Get All Shipping Provider
    public List<ShippingProvider> getAllProviders(){
        return shippingProviderRepository.findAll();
    }


}
