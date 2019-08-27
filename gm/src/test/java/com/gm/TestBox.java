package com.gm;

import com.gm.model.Box;
import com.gm.model.Product;
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
public class TestBox {

    @Test
    public void testGetBoxListSuccess() throws URISyntaxException
    {

        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + 8080 + "/box";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class); ;

        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(true, result.getBody().contains("Caixa Agosto"));
    }

    @Test
    public void testPostBoxSuccess() throws URISyntaxException
    {

        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + 8080 + "/box";
        URI uri = new URI(baseUrl);
        List<Product> prods = new ArrayList<Product>();
        prods.add(new Product("100","Batom",20.0f,30.0f,10,"Garnier","Garnier", ProductType.BATOM));
        prods.add(new Product("101","Agua Micelar",30.0f,30.0f,10,"asdr","Garnfdier", ProductType.AGUA_MICELAR));
        prods.add(new Product("102","Shampoo",40.0f,60.0f,10,"fdfasr","Garnisser", ProductType.RIMEL));
        prods.add(new Product("103","Pó de Arroz",16.0f,30.0f,10,"easfier","Garanier", ProductType.PO_FACIAL));
        Box box = new Box("0","Caixa Setembro",80.0f,199.0f,10,prods);


        ResponseEntity<String> result = restTemplate.postForEntity(uri,box,String.class); ;

        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());

    }
}
