package com.target.case_study;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.target.case_study.service.RedSkyApiService;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.NotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class RedSkyApiServiceTest {

    private RedSkyApiService redSkyApiService;

    private String productString;

    private JsonNode productNode;

    private String productTitle;

    private String badId;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        redSkyApiService = new RedSkyApiService();
        ObjectMapper objectMapper = new ObjectMapper();
        productString = "{\"product\": {\"item\": {\"product_description\": {\"title\": \"The Big Lebowski\"}}}}";
        productNode = objectMapper.readTree(productString).get("product");
        productTitle = "The Big Lebowski";
        badId = "99999";
    }

    @Test(expected = NotFoundException.class)
    public void testGetProductInfoWithBadId() {

    }

    @Test
    public void testGetProductTitle() {
        String expectedResult = productTitle;
        String actualResult = redSkyApiService.getProductTitle(productNode);

        Assert.assertEquals(expectedResult, actualResult);
    }
}
