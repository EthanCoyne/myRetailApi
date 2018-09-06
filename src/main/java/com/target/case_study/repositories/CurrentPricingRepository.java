package com.target.case_study.repositories;

import com.target.case_study.domain.CurrentPricing;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentPricingRepository extends MongoRepository<CurrentPricing, String>{
//    CurrentPricing findCurrentPricingByProduct_id(String productId);

    CurrentPricing findBy_id(ObjectId _id);

    CurrentPricing findByProductId(String id);

}
