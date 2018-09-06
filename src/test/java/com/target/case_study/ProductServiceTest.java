package com.target.case_study;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.target.case_study.domain.CurrentPricing;
import com.target.case_study.domain.Product;
import com.target.case_study.repositories.CurrentPricingRepository;
import com.target.case_study.service.ProductService;
import com.target.case_study.service.RedSkyApiService;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.NotFoundException;
import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    private ProductService productService;

    @Mock
    private RedSkyApiService mockRedSkyApiService;

    @Mock
    CurrentPricingRepository currentPricingRepository;

    private String productString;

    private JsonNode productNode;

    private Integer productId;

    private String productName;

    private Product product;

    private CurrentPricing currentPricing;

    private Double value;

    private ObjectId objectId;

    private String badProductId;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        productService = new ProductService(
                mockRedSkyApiService,
                currentPricingRepository
        );

        productId = 133586;
        badProductId = "999999";
        productName = "The Big Lebowski";
        productString = "{\"product\": {\"item\": {\"product_description\": {\"title\": \"The Big Lebowski\"}}}}";
        value = 13.49;
        objectId = new ObjectId();
        ObjectMapper objectMapper = new ObjectMapper();
        productNode = objectMapper.readTree(productString);
        currentPricing = new CurrentPricing(value, "USD");
        product = new Product(productId, productName, currentPricing);
    }

    @Test
    public void testGetProduct() throws IOException {
        Mockito.when(mockRedSkyApiService.getProductInfo(Mockito.anyString())).thenReturn(productNode);
        Mockito.when(mockRedSkyApiService.getProductTitle(Mockito.any(JsonNode.class))).thenReturn(productName);
        Mockito.when(currentPricingRepository.findByProductId(productId.toString())).thenReturn(currentPricing);
        Product expectedResult = this.product;
        Product actualResult = productService.getProduct(productId.toString());
        Assert.assertEquals(expectedResult.getId(), actualResult.getId());
        Assert.assertEquals(expectedResult.getName(), actualResult.getName());

    }

    @Test
    public void testUpdateProductPricing() {
        Mockito.when(currentPricingRepository.findByProductId(productId.toString())).thenReturn(currentPricing);
        productService.updateProductPricing(currentPricing, productId.toString());
        Mockito.verify(currentPricingRepository, Mockito.times(1)).save(Mockito.any(CurrentPricing.class));
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateProductPricingWithBadId() {
        Mockito.when(currentPricingRepository.findByProductId(badProductId)).thenThrow(NotFoundException.class);

        productService.updateProductPricing(currentPricing, badProductId);
    }
}
