package com.gm;

import com.gm.model.*;
import com.gm.service.ProductService;
import com.gm.util.*;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class TestOrder {

    @Test
    public void testGetOrderListSuccess() throws URISyntaxException {

        RestTemplate restTemplate = new RestTemplate();
        Set<Product> prods = new HashSet<Product>();
        User u1 = new User("Peggy Olson","peggy@gmail.com","2569774","52091859079", UserRole.CLIENT);
        prods.add(new Product("100","Batom",20.0f,30.0f,10,"Garnier","Garnier", ProductType.BATOM));
        prods.add(new Product("101","Agua Micelar",30.0f,30.0f,10,"asdr","Garnfdier", ProductType.AGUA_MICELAR));
        prods.add(new Product("102","Shampoo",40.0f,60.0f,10,"fdfasr","Garnisser", ProductType.RIMEL));
        prods.add(new Product("103","Pó de Arroz",16.0f,30.0f,10,"easfier","Garanier", ProductType.PO_FACIAL));
        Box box = new Box("0","Caixa Setembro",80.0f,199.0f,10,prods);
        Subscription sub = new Subscription(SubscriptionType.PREMIUM,200.50,YearMonth.now(),box);

        final String baseUrl = "http://localhost:" + 8080 + "/order";
        URI uri = new URI(baseUrl);

        ResponseEntity<List<Order>> resultGetAll = restTemplate.exchange(uri, HttpMethod.GET,null, new ParameterizedTypeReference<List<Order>>() {});
        List<Order> orders = resultGetAll.getBody();
        for (Order o :
                orders) {
            Assert.assertTrue(o instanceof Order);
        }
        Assert.assertEquals(200, resultGetAll.getStatusCodeValue());
    }

    @Test
    public void testPostOrderSuccess() throws URISyntaxException {

        RestTemplate restTemplate = new RestTemplate();
        Set<Product> prods = new HashSet<Product>();
        User u1 = new User("Peggy Olson","pegggyy@gmail.com","2569774","52091859079", UserRole.CLIENT);
        prods.add(new Product("100","Batom",20.0f,30.0f,10,"Garnier","Garnier", ProductType.BATOM));
        prods.add(new Product("101","Agua Micelar",30.0f,30.0f,10,"asdr","Garnfdier", ProductType.AGUA_MICELAR));
        prods.add(new Product("102","Shampoo",40.0f,60.0f,10,"fdfasr","Garnisser", ProductType.RIMEL));
        prods.add(new Product("103","Pó de Arroz",16.0f,30.0f,10,"easfier","Garanier", ProductType.PO_FACIAL));
        Box box = new Box("0","Caixa Setembro",80.0f,199.0f,10,prods);
        Subscription sub = new Subscription(SubscriptionType.PREMIUM,200.50,YearMonth.now(),box);
        Order order = new Order(u1,sub,3, DispatchStatus.WAITING, PaymentType.BOLETO,PaymentStatus.REQUESTED);//

        final String baseUrl = "http://localhost:" + 8080 + "/order/";
        URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Order> request = new HttpEntity<>(order, headers);
        System.out.println(order);

        ResponseEntity<Order> resultPost = restTemplate.exchange(uri, HttpMethod.POST,request, new ParameterizedTypeReference<Order>() {});
        Order orderPosted = resultPost.getBody();

        ResponseEntity<Order> resultGet = restTemplate.exchange(new URI(baseUrl + orderPosted.getId()), HttpMethod.GET,null, new ParameterizedTypeReference<Order>() {});
        Order orderGet = resultGet.getBody();

        Assert.assertEquals(orderPosted.toString(),orderGet.toString());
        Assert.assertEquals(200, resultPost.getStatusCodeValue());

    }

    @Test
    public void testDeleteOrderSuccess() throws URISyntaxException {

        RestTemplate restTemplate = new RestTemplate();
        Set<Product> prods = new HashSet<Product>();
        User u1 = new User("Peggy Olson","peggyy@gmail.com","2569774","52091859079", UserRole.CLIENT);
        prods.add(new Product("100","Batom",20.0f,30.0f,10,"Garnier","Garnier", ProductType.BATOM));
        prods.add(new Product("101","Agua Micelar",30.0f,30.0f,10,"asdr","Garnfdier", ProductType.AGUA_MICELAR));
        prods.add(new Product("102","Shampoo",40.0f,60.0f,10,"fdfasr","Garnisser", ProductType.RIMEL));
        prods.add(new Product("103","Pó de Arroz",16.0f,30.0f,10,"easfier","Garanier", ProductType.PO_FACIAL));
        Box box = new Box("0","Caixa Setembro",80.0f,199.0f,10,prods);
        Subscription sub = new Subscription(SubscriptionType.PREMIUM,200.50,YearMonth.now(),box);
        Order order = new Order(u1,sub,3, DispatchStatus.WAITING, PaymentType.BOLETO,PaymentStatus.REQUESTED);//

        final String baseUrl = "http://localhost:" + 8080 + "/order/";
        URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Order> request = new HttpEntity<>(order, headers);
        System.out.println(order);

        ResponseEntity<Order> resultPost = restTemplate.exchange(uri, HttpMethod.POST,request, new ParameterizedTypeReference<Order>() {});
        Order orderPosted = resultPost.getBody();


        ResponseEntity<Order> resultGet = restTemplate.exchange(new URI(baseUrl + orderPosted.getId()), HttpMethod.GET,null, new ParameterizedTypeReference<Order>() {});
        Order orderGet = resultGet.getBody();

        Assert.assertEquals(orderPosted.toString(),orderGet.toString());
        Assert.assertEquals(200, resultPost.getStatusCodeValue());


        ResponseEntity<String> resultDel = restTemplate.exchange(new URI(baseUrl + orderPosted.getId()), HttpMethod.DELETE,null, new ParameterizedTypeReference<String>() {});

        System.out.println(resultDel.getBody());
        Assert.assertEquals("Order has been deleted.", resultDel.getBody());

        ResponseEntity<String> resultDel2;
        try {
            resultDel2 = restTemplate.exchange(new URI(baseUrl + orderPosted.getId()), HttpMethod.DELETE, null, new ParameterizedTypeReference<String>() {
            });
            Assert.assertEquals("Failed to delete. Order does not exist.", resultDel2.getBody());
        } catch (HttpClientErrorException e) {
            Assert.assertEquals("Failed to delete. Order does not exist.", e.getResponseBodyAsString());
        }
    }


}
