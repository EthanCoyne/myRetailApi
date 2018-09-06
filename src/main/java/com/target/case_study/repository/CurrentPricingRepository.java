package com.target.case_study.repository;

import com.target.case_study.model.CurrentPricing;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for querying MongoDB instance for current_pricing documents
 */
@Repository
public interface CurrentPricingRepository extends MongoRepository<CurrentPricing, String>{

    /**
     * Find a product pricing document by productId
     *
     * @param id productId
     * @return CurrentPricing
     */
    CurrentPricing findByProductId(String id);
}
