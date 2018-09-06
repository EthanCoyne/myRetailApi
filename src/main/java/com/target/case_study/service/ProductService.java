package com.target.case_study.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.target.case_study.domain.CurrentPricing;
import com.target.case_study.domain.Product;
import com.target.case_study.repositories.CurrentPricingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.io.IOException;

@Service
public class ProductService {

    @Autowired
    private RedSkyApiService redSkyApiService;

    @Autowired
    private CurrentPricingRepository currentPricingRepository;


    public ProductService(
            RedSkyApiService redSkyApiService,
            CurrentPricingRepository currentPricingRepository
    ) {
        this.redSkyApiService = redSkyApiService;
        this.currentPricingRepository = currentPricingRepository;
    }

    public Product getProduct(String id) throws IOException {
        JsonNode productNode = redSkyApiService.getProductInfo(id);
        String productName = redSkyApiService.getProductTitle(productNode);

        CurrentPricing currentPricing = currentPricingRepository.findByProductId(id);

        return new Product(Integer.parseInt(id), productName, currentPricing);
    }

    public void updateProductPricing(CurrentPricing currentPricing, String id) {
        CurrentPricing currentPricingToUpdate = currentPricingRepository.findByProductId(id);

        if (currentPricingToUpdate == null) {
            throw new NotFoundException("Entry for product with matching id: " + id + " not found");
        }

        currentPricing.set_id(currentPricingToUpdate.get_id());
        currentPricing.setProductId(id);

        currentPricingRepository.save(currentPricing);
    }
}
