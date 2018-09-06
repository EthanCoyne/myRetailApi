package com.target.case_study.service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.io.IOException;

/**
 * Service for querying the RedSky API for product information
 */
@Service
public class RedSkyApiService extends BaseApiService {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public RedSkyApiService() {
        this.setBaseUrl("https://redsky.target.com/v2/pdp/tcin/");
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }

    /**
     * Queries the RedSky API for product information as a JsonNode.
     *
     * @param id productId
     * @return JsonNode
     * @throws IOException       Internal exception
     * @throws NotFoundException if no entry is found with matching productId in redSky API
     */
    public JsonNode getProductInfo(String id) throws IOException {
        JsonNode result;

        String productAsString = this.getPayload(this.url + id);
        if (productAsString.isEmpty()) {
            throw new NotFoundException("Product with matching id: " + id + " not found");
        }

        JsonNode jsonNode = objectMapper.readTree(productAsString);
        result = jsonNode.get("product");

        return result;
    }

    /**
     * Returns the product title from a product JsonNode
     *
     * @param productJson JsonNode of product information
     * @return String title of product
     */
    public String getProductTitle(JsonNode productJson) {

        return productJson.get("item")
                .get("product_description")
                .get("title")
                .toString()
                .replaceAll("\"", "");
    }
}
