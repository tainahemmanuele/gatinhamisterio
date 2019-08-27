package com.gm;


import com.gm.model.User;
import com.gm.util.UserRole;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.net.URISyntaxException;
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

        User u1 = new User("Peggy Olson","peggy@gmail.com","2569774", UserRole.CLIENT);
        final String baseUrl = "http://localhost:" + 8080 + "/user";
        URI uri = new URI(baseUrl);



        ResponseEntity<String> result = restTemplate.postForEntity(uri,u1,String.class); ;

        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());
    }
}
