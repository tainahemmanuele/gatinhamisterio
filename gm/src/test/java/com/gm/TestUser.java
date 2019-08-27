package com.gm;


import com.gm.model.User;
import com.gm.util.UserRole;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.net.URISyntaxException;


@SpringBootTest
public class TestUser {

    @Test
    public void testGetUserListSuccess() throws URISyntaxException {

        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + 8080 + "/user";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        ;

        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(true, result.getBody().contains("Kim Swift"));
    }


    @Test
    public void testPostUserSuccess() throws URISyntaxException
    {

        RestTemplate restTemplate = new RestTemplate();

        User u1 = new User("Peggy Olson","peggy@gmail.com","2569774", UserRole.CLIENT);
        final String baseUrl = "http://localhost:" + 8080 + "/user";
        URI uri = new URI(baseUrl);



        ResponseEntity<String> result = restTemplate.postForEntity(uri,u1,String.class); ;

        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());
    }
}
