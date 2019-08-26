package com.gm;

import com.gm.model.Product;
import com.gm.service.ProductService;
import com.gm.util.ProductType;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TestProduct {


    @Test
    public void testGetProductListSuccess() throws URISyntaxException
    {

        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + 8080 + "/product";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class); ;

        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(true, result.getBody().contains("name"));
    }

    @Test
    public void testPostProductSuccess() throws URISyntaxException
    {

        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + 8080 + "/product";
        URI uri = new URI(baseUrl);
        Product product = new Product("300","Batom",20.0f,30.0f,10,"Loreal","Loreal", ProductType.BATOM);

        ResponseEntity<String> result = restTemplate.postForEntity(uri,product,String.class); ;

        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());

    }


}
