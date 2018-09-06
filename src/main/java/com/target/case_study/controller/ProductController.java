package com.target.case_study.controller;

import com.target.case_study.model.CurrentPricing;
import com.target.case_study.model.Product;
import com.target.case_study.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * GET request for retrieving products by id
     *
     * @param id productId
     * @return ResponseEntity collection of product title and pricing information
     */
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

    /**
     * PUT request for updating product pricing info by querying the mongoDB instance for matching product pricing and
     * updating if an entry with matching productId is found.
     *
     * @param id productId
     * @param currentPricing CurrentPricing
     * @return ResponseEntity result status code: 200, 400, or 404
     */
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
