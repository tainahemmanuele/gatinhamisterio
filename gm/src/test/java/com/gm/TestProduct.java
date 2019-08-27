package com.gm;

import com.gm.model.Product;
import com.gm.service.ProductService;
import com.gm.util.ProductType;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
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

        ResponseEntity<List<Product>> result = restTemplate.exchange(uri, HttpMethod.GET,null, new ParameterizedTypeReference<List<Product>>() {});
        List<Product> products = result.getBody();

        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());
        for (Product p : products) {
            Assert.assertEquals(true,p instanceof Product);
        }
    }

    @Test
    public void testPostProductSuccess() throws URISyntaxException
    {

        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + 8080 + "/product";
        URI uri = new URI(baseUrl);
        Product product = new Product("300","Batom",20.0f,30.0f,10,"Loreal","Loreal", ProductType.BATOM);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Product> request = new HttpEntity<>(product, headers);
        ResponseEntity<Product> result = restTemplate.exchange(uri, HttpMethod.POST,request, new ParameterizedTypeReference<Product>() {});


        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());

    }

    @Test
    public void testDeleteProductSuccess() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + 8080 + "/product/";
        URI uri = new URI(baseUrl);

        ResponseEntity<List<Product>> result = restTemplate.exchange(uri, HttpMethod.GET,null, new ParameterizedTypeReference<List<Product>>() {});
        List<Product> products = result.getBody();
        String barcode = "1234566";
        Product product = new Product(barcode,"Batom",20.0f,30.0f,10,"Loreal","Loreal", ProductType.BATOM);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Product> request = new HttpEntity<>(product, headers);
        ResponseEntity<Product> resultPost = restTemplate.exchange(uri, HttpMethod.POST,request, new ParameterizedTypeReference<Product>() {});


        Assert.assertEquals(true, resultPost.getBody() instanceof Product);
        System.out.println("ResultPost ID:" + resultPost.getBody().getId());
        ResponseEntity<List<Product>> resultGet = restTemplate.exchange(uri, HttpMethod.GET,null, new ParameterizedTypeReference<List<Product>>() {});
        List<Product> produtos = resultGet.getBody();

        Boolean found = false;
        Product product1 = null;
        for (Product p :
                produtos) {
            found = (found || (p.getBarcode().equals(barcode)));
            if (found && product1 == null)
                product1 = p;
        }
        Assert.assertEquals(true, found);
        ResponseEntity<String> resultDel = restTemplate.exchange(new URI(baseUrl + product1.getId()), HttpMethod.DELETE,null, new ParameterizedTypeReference<String>() {});

        System.out.println(resultDel.getBody());
        Assert.assertEquals("Product has been deleted.", resultDel.getBody());

    }

}
