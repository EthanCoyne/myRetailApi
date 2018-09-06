package com.target.case_study.controller;

import com.target.case_study.domain.CurrentPricing;
import com.target.case_study.domain.Product;
import com.target.case_study.repositories.CurrentPricingRepository;
import com.target.case_study.service.ProductService;
import org.bson.types.ObjectId;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<?> getProduct(@PathVariable("id") String id) {
        ResponseEntity result;
        try {
            Product product = productService.getProduct(id);

            result = new ResponseEntity<>(product, HttpStatus.OK);

        } catch (NotFoundException exception) {

            result = new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IOException exception) {

            result = new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProductPricingByProductId(@PathVariable("id") String id, @RequestBody CurrentPricing currentPricing) {
        ResponseEntity result;
        try {
            productService.updateProductPricing(currentPricing, id);

            result = new ResponseEntity<>(HttpStatus.OK);
        } catch (BadRequestException exception) {

            result = new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException exception) {

            result = new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }

        return result;
    }
}
