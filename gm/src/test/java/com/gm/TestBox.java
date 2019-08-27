package com.gm;

import com.gm.model.Box;
import com.gm.model.Product;
import com.gm.util.ProductType;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TestBox {

    @Test
    public void testGetBoxListSuccess() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:" + 8080 + "/box/";
        URI uri = new URI(baseUrl);


        ResponseEntity<List<Box>> resultGet = restTemplate.exchange(uri, HttpMethod.GET,null, new ParameterizedTypeReference<List<Box>>() {});
        List<Box> boxes = resultGet.getBody();

        //Verify request succeed
        Assert.assertEquals(200, resultGet.getStatusCodeValue());
        for (Box b : boxes) {
            Assert.assertEquals(true, b instanceof Box);
        }

    }

    @Test
    public void testPostBoxSuccess() throws URISyntaxException {

        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + 8080 + "/box";
        URI uri = new URI(baseUrl);
        List<Product> prods = new ArrayList<Product>();
        prods.add(new Product("100","Batom",20.0f,30.0f,10,"Garnier","Garnier", ProductType.BATOM));
        prods.add(new Product("101","Agua Micelar",30.0f,30.0f,10,"asdr","Garnfdier", ProductType.AGUA_MICELAR));
        prods.add(new Product("102","Shampoo",40.0f,60.0f,10,"fdfasr","Garnisser", ProductType.RIMEL));
        prods.add(new Product("103","Pó de Arroz",16.0f,30.0f,10,"easfier","Garanier", ProductType.PO_FACIAL));
        Box box = new Box("0","Caixa Setembro",80.0f,199.0f,10,prods);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Box> request = new HttpEntity<>(box, headers);
        ResponseEntity<Box> resultPost = restTemplate.exchange(uri, HttpMethod.POST,request, new ParameterizedTypeReference<Box>() {});
        Box boxPosted = resultPost.getBody();

        //Verify request succeed
        Assert.assertEquals(200, resultPost.getStatusCodeValue());
        //Verify if it is a box
        Assert.assertEquals(true, boxPosted instanceof Box);
        //verify if every product is in the original list
        for (Product p :
                box.getProducts()) {
            Assert.assertEquals(true, prods.contains(p));
        }
        //Verify if the name of the new box is right
        Assert.assertEquals("Caixa Setembro",box.getName());
    }


    @Test
    public void testDeleteProductSuccess() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + 8080 + "/box/";
        URI uri = new URI(baseUrl);

        String barcode = "1234566";
        Box box = new Box(barcode,"Caixa",100.0f,150.0f,1,new ArrayList<Product>());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Box> request = new HttpEntity<Box>(box, headers);
        ResponseEntity<Box> resultPost = restTemplate.exchange(uri, HttpMethod.POST,request, new ParameterizedTypeReference<Box>() {});
        Box boxPosted = resultPost.getBody();

        Assert.assertEquals(true, boxPosted instanceof Box);
        System.out.println("ResultPost ID:" + resultPost.getBody().getId());

        ResponseEntity<String> resultDel = restTemplate.exchange(new URI(baseUrl + boxPosted.getId()), HttpMethod.DELETE,null, new ParameterizedTypeReference<String>() {});

        System.out.println(resultDel.getBody());
        Assert.assertEquals("Box has been deleted.", resultDel.getBody());
        ResponseEntity<String> resultDel2 = null;
        try {
                resultDel2 = restTemplate.exchange(new URI(baseUrl + boxPosted.getId()), HttpMethod.DELETE, null, new ParameterizedTypeReference<String>() {
            });
            Assert.assertEquals("Failed to delete. Box does not exist.", resultDel2.getBody());
        } catch (HttpClientErrorException e) {
            Assert.assertEquals("Failed to delete. Box does not exist.", e.getResponseBodyAsString());
        }
    }
}
