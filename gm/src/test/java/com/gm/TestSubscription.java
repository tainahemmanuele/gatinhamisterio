package com.gm;

import com.gm.box.Box;
import com.gm.product.Product;
import com.gm.subscription.Subscription;
import com.gm.util.ProductType;
import com.gm.util.SubscriptionType;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.YearMonth;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class TestSubscription {


    @Test
    public void testGetSubscriptionListSuccess() throws URISyntaxException
    {

        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + 8080 + "/subscription/";
        URI uri = new URI(baseUrl);

        ResponseEntity<List<Subscription>> resultGetAll = restTemplate.exchange(uri, HttpMethod.GET,null, new ParameterizedTypeReference<List<Subscription>>() {});
        List<Subscription> subs = resultGetAll.getBody();

        //Verify request succeed
        Assert.assertEquals(200, resultGetAll.getStatusCodeValue());
        for (Subscription s : subs) {
            Assert.assertEquals(true,s instanceof Subscription);
        }



    }

    @Test
    public void testPostSubscriptionSuccess() throws URISyntaxException {

        RestTemplate restTemplate = new RestTemplate();
        Set<Product> prods = new HashSet<Product>();
        prods.add(new Product("100","Batom",20.0f,30.0f,10,"Garnier","Garnier", ProductType.BATOM));
        prods.add(new Product("101","Agua Micelar",30.0f,30.0f,10,"asdr","Garnfdier", ProductType.AGUA_MICELAR));
        prods.add(new Product("102","Shampoo",40.0f,60.0f,10,"fdfasr","Garnisser", ProductType.RIMEL));
        prods.add(new Product("103","Pó de Arroz",16.0f,30.0f,10,"easfier","Garanier", ProductType.PO_FACIAL));
        Box box = new Box("0","Caixa Setembro",80.0f,199.0f,10,prods);
        Subscription sub = new Subscription(SubscriptionType.PREMIUM,200.50,YearMonth.now(),box);

        final String baseUrl = "http://localhost:" + 8080 + "/subscription/";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Subscription> request = new HttpEntity<>(sub, headers);
        ResponseEntity<Subscription> resultPost = restTemplate.exchange(uri, HttpMethod.POST,request, new ParameterizedTypeReference<Subscription>() {});
        Subscription subPosted = resultPost.getBody();

        ResponseEntity<Subscription> resultGet = restTemplate.exchange(new URI(baseUrl + subPosted.getId()), HttpMethod.GET,null, new ParameterizedTypeReference<Subscription>() {});
        Subscription subGet = resultGet.getBody();


        //Verify request succeed
        Assert.assertEquals(200, resultPost.getStatusCodeValue());

        //verify if get and post result are equal
        Assert.assertEquals(subPosted.toString(),subGet.toString());

        //Assert.assertEquals(subGet.getBox().toString(),subPosted.getBox().toString());
        for (Product p :
                prods) {
            subPosted.getBox().getProducts().contains(p);
        }

    }


    @Test
    public void testDeleteSubscriptionSuccess() throws URISyntaxException {

        RestTemplate restTemplate = new RestTemplate();
        Set<Product> prods = new HashSet<Product>();
        prods.add(new Product("100","Batom",20.0f,30.0f,10,"Garnier","Garnier", ProductType.BATOM));
        prods.add(new Product("101","Agua Micelar",30.0f,30.0f,10,"asdr","Garnfdier", ProductType.AGUA_MICELAR));
        prods.add(new Product("102","Shampoo",40.0f,60.0f,10,"fdfasr","Garnisser", ProductType.RIMEL));
        prods.add(new Product("103","Pó de Arroz",16.0f,30.0f,10,"easfier","Garanier", ProductType.PO_FACIAL));
        Box box = new Box("0","Caixa Setembro",80.0f,199.0f,10,prods);
        Subscription sub = new Subscription(SubscriptionType.PREMIUM,200.50,YearMonth.now(),box);

        final String baseUrl = "http://localhost:" + 8080 + "/subscription/";
        URI uri = new URI(baseUrl);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Subscription> request = new HttpEntity<>(sub, headers);
        ResponseEntity<Subscription> resultPost = restTemplate.exchange(uri, HttpMethod.POST,request, new ParameterizedTypeReference<Subscription>() {});
        Subscription subPosted = resultPost.getBody();


        ResponseEntity<String> resultDel = restTemplate.exchange(new URI(baseUrl + subPosted.getId()), HttpMethod.DELETE,null, new ParameterizedTypeReference<String>() {});

        System.out.println(resultDel.getBody());
        Assert.assertEquals("Subscription has been deleted.", resultDel.getBody());

        ResponseEntity<String> resultDel2;
        try {
            resultDel2 = restTemplate.exchange(new URI(baseUrl + subPosted.getId()), HttpMethod.DELETE, null, new ParameterizedTypeReference<String>() {
            });
            Assert.assertEquals("Failed to delete. Subscription does not exist.", resultDel2.getBody());
        } catch (HttpClientErrorException e) {
            Assert.assertEquals("Failed to delete. Subscription does not exist.", e.getResponseBodyAsString());
        }
    }

}
