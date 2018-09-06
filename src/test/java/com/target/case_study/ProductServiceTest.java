package com.target.case_study;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.target.case_study.model.CurrentPricing;
import com.target.case_study.model.Product;
import com.target.case_study.repository.CurrentPricingRepository;
import com.target.case_study.service.ProductService;
import com.target.case_study.service.RedSkyApiService;
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

/**
 * Tests for ProductService class
 * TODO: add more tests for all use cases, especially on sad paths.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    private ProductService productService;

    @Mock
    private RedSkyApiService mockRedSkyApiService;

    @Mock
    private CurrentPricingRepository currentPricingRepository;

    private JsonNode productNode;

    private String productId;

    private String productName;

    private Product product;

    private CurrentPricing currentPricing;

    private String badProductId;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        productService = new ProductService(
                mockRedSkyApiService,
                currentPricingRepository
        );

        productId = "133586";
        badProductId = "99988765";
        productName = "The Big Lebowski";
        String productString = "{\"product\": {\"item\": {\"product_description\": {\"title\": \"The Big Lebowski\"}}}}";
        ObjectMapper objectMapper = new ObjectMapper();
        productNode = objectMapper.readTree(productString);
        currentPricing = new CurrentPricing(13.49, "USD");
        product = new Product(Integer.parseInt(productId), productName, currentPricing);
    }

    @Test
    public void testGetProduct() throws IOException {
        Mockito.when(mockRedSkyApiService.getProductInfo(Mockito.anyString())).thenReturn(productNode);
        Mockito.when(mockRedSkyApiService.getProductTitle(Mockito.any(JsonNode.class))).thenReturn(productName);
        Mockito.when(currentPricingRepository.findByProductId(productId)).thenReturn(currentPricing);
        Product expectedResult = this.product;
        Product actualResult = productService.getProduct(productId);
        Assert.assertEquals(expectedResult.getId(), actualResult.getId());
        Assert.assertEquals(expectedResult.getName(), actualResult.getName());

    }

    @Test(expected = NotFoundException.class)
    public void testGetProductThrowsNotFoundExceptionWithBadId() throws IOException {
        Mockito.when(mockRedSkyApiService.getProductInfo(badProductId)).thenThrow(NotFoundException.class);

        productService.getProduct(badProductId);
    }

    @Test
    public void testUpdateProductPricing() {
        Mockito.when(currentPricingRepository.findByProductId(productId)).thenReturn(currentPricing);
        productService.updateProductPricing(currentPricing, productId);
        Mockito.verify(currentPricingRepository, Mockito.times(1)).save(Mockito.any(CurrentPricing.class));
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateProductPricingWithBadId() {
        Mockito.when(currentPricingRepository.findByProductId(badProductId)).thenThrow(NotFoundException.class);

        productService.updateProductPricing(currentPricing, badProductId);
    }
}
