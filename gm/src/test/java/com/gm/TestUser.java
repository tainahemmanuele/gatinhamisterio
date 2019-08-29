package com.gm;


import com.gm.model.User;
import com.gm.util.UserRole;
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
public class TestUser {

    @Test
    public void testGetUserListSuccess() throws URISyntaxException {

        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + 8080 + "/user";
        URI uri = new URI(baseUrl);

        ResponseEntity<List<User>> resultGetAll = restTemplate.exchange(uri, HttpMethod.GET,null, new ParameterizedTypeReference<List<User>>() {});
        List<User> users = resultGetAll.getBody();

        //Verify request succeed
        Assert.assertEquals(200, resultGetAll.getStatusCodeValue());
        for (User u : users) {
            Assert.assertEquals(true,u instanceof User);
        }

    }


    @Test
    public void testPostUserSuccess() throws URISyntaxException
    {

        RestTemplate restTemplate = new RestTemplate();

        User u1 = new User("Post Olson","Post@gmail.com","2569774","96958852095", UserRole.CLIENT);
        final String baseUrl = "http://localhost:" + 8080 + "/user";
        URI uri = new URI(baseUrl);



        ResponseEntity<String> result = restTemplate.postForEntity(uri,u1,String.class); ;

        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());
    }


    @Test
    public void testDeleteUserSuccess() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();

        User u1 = new User("Delete Draper","delete@gmail.com","scpdcgc", "52111375071",UserRole.CLIENT);
        final String baseUrl = "http://localhost:" + 8080 + "/user/";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<User>(u1, headers);
        ResponseEntity<User> resultPost = restTemplate.exchange(uri, HttpMethod.POST,request, new ParameterizedTypeReference<User>() {});
        User userPosted = resultPost.getBody();

        Assert.assertEquals(true, userPosted instanceof User);
        System.out.println("ResultPost ID:" + resultPost.getBody().getId());

        ResponseEntity<String> resultDel = restTemplate.exchange(new URI(baseUrl + userPosted.getId()), HttpMethod.DELETE,null, new ParameterizedTypeReference<String>() {});

        System.out.println(resultDel.getBody());
        Assert.assertEquals("User has been deleted.", resultDel.getBody());
        ResponseEntity<String> resultDel2 = null;
        try {
            resultDel2 = restTemplate.exchange(new URI(baseUrl + userPosted.getId()), HttpMethod.DELETE, null, new ParameterizedTypeReference<String>() {
            });
            Assert.assertEquals("Failed to delete. User does not exist.", resultDel2.getBody());
        } catch (HttpClientErrorException e) {
            Assert.assertEquals("Failed to delete. User does not exist.", e.getResponseBodyAsString());
        }
    }
}
