package com.target.case_study.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.target.case_study.model.CurrentPricing;
import com.target.case_study.model.Product;
import com.target.case_study.repository.CurrentPricingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.io.IOException;

/**
 * Service for handling logic related to Products
 */
@Service
public class ProductService {

    @Autowired
    private RedSkyApiService redSkyApiService;

    @Autowired
    private CurrentPricingRepository currentPricingRepository;


    /**
     * @param redSkyApiService         RedSkyApiService
     * @param currentPricingRepository CurrentPricingRepository
     */
    public ProductService(
            RedSkyApiService redSkyApiService,
            CurrentPricingRepository currentPricingRepository
    ) {
        this.redSkyApiService = redSkyApiService;
        this.currentPricingRepository = currentPricingRepository;
    }

    /**
     * Queries the redSky API for product title and the pricing_db for pricing info by id, and returns the combined information as a Product
     *
     * @param id String - id of the product
     * @return Product
     * @throws IOException internal exception
     */
    public Product getProduct(String id) throws IOException {
        JsonNode productNode = redSkyApiService.getProductInfo(id);
        String productName = redSkyApiService.getProductTitle(productNode);

        CurrentPricing currentPricing = currentPricingRepository.findByProductId(id);

        return new Product(Integer.parseInt(id), productName, currentPricing);
    }

    /**
     * Retrieves the matching pricing document from the pricing_db.
     *
     * @param currentPricing CurrentPricing
     * @param id productId
     *
     * @throws NotFoundException if no matching entry is found in the pricing_db
     */
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
