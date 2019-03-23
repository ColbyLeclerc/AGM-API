package routes.enclosure;

import io.colby.Application;
import io.colby.modules.auth.model.entity.Auth;
import io.colby.modules.routes.enclosures.model.entity.Enclosure;
import io.colby.modules.routes.plants.model.entity.Plant;
import io.colby.utility.StringUtility;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EnclosureControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private boolean setupRan = false;

    private static final String AUTH_ENDPOINT = "/auth";
    private static final String ENCLOSURE_ENDPOINT = "/enclosures";

    private static String AUTH_TOKEN = "";

    private static int ENCLOSURE_ID = 0;
    private static final String ENCLOSURE_TITLE = "Enclosure Title";
    private static final String ENCLOSURE_LOCATION = "Enclosure Location";
    private static final String DIMENSIONAL_UNITS = "Meters";
    private static final double ENCLOSURE_LENGTH = 1;
    private static final double ENCLOSURE_WIDTH = 1.0;
    private static final double ENCLOSURE_HEIGHT = 122.2111;

    @LocalServerPort
    private int port;

    private String getRootUrl() {

        return "http://localhost:" + port;

    }

    private Enclosure constructEnclosure() {
        return new Enclosure(ENCLOSURE_TITLE, ENCLOSURE_LOCATION,
                DIMENSIONAL_UNITS, ENCLOSURE_LENGTH, ENCLOSURE_WIDTH, ENCLOSURE_HEIGHT);
    }


    @Test
    public void contextLoads() {

    }

    @Before
    @Test
    public void testCreateEnclosure() {

        if (setupRan) {
            return;
        }

        setupRan = true;

        //TODO create admin auth header then set
        HttpHeaders headersAuth = new HttpHeaders();
        headersAuth.add("Authorization", "2E7F4CB19B91EB38D1144F741EAGGF");

        HttpEntity<AuthRequest> entityAuth = new HttpEntity<>(new AuthRequest("test@colby.io"), headersAuth);

        ResponseEntity<Auth> responseAuth = restTemplate.exchange(getRootUrl() + AUTH_ENDPOINT,
                HttpMethod.POST, entityAuth, Auth.class);

        Assert.assertNotNull(responseAuth.getBody());

        System.out.println(responseAuth.getBody());

        AUTH_TOKEN = responseAuth.getBody().getToken().toString();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", AUTH_TOKEN);

        HttpEntity<Enclosure> entity = new HttpEntity<>(constructEnclosure(), headers);

        ResponseEntity<Enclosure> response = restTemplate.exchange(getRootUrl() + ENCLOSURE_ENDPOINT,
                HttpMethod.POST, entity, Enclosure.class);

        Enclosure enc = response.getBody();

        System.out.println(response.getBody());

        Assert.assertNotNull(response.getBody());

        Assert.assertNotEquals(0, enc.getEnclosureId());

        ENCLOSURE_ID = enc.getEnclosureId();

        Assert.assertEquals(201, response.getStatusCodeValue());
        Assert.assertEquals(ENCLOSURE_TITLE, enc.getTitle());
        Assert.assertEquals(ENCLOSURE_LOCATION, enc.getLocation());
        Assert.assertEquals(DIMENSIONAL_UNITS, enc.getDimensionUnits());
        Assert.assertEquals(ENCLOSURE_LENGTH, enc.getLength(), 0);
        Assert.assertEquals(ENCLOSURE_WIDTH, enc.getWidth(), 0);
        Assert.assertEquals(ENCLOSURE_HEIGHT, enc.getHeight(), 0);
        Assert.assertEquals(new ArrayList<Plant>(), enc.getPlants());

    }

    @Test
    public void testGetAllEnclosure() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", AUTH_TOKEN);

        HttpEntity<Enclosure> entity = new HttpEntity<>(constructEnclosure(), headers);

        ResponseEntity<Enclosure> responseOne = restTemplate.exchange(getRootUrl() + ENCLOSURE_ENDPOINT,
                HttpMethod.POST, entity, Enclosure.class);

        ResponseEntity<Enclosure> responseTwo = restTemplate.exchange(getRootUrl() + ENCLOSURE_ENDPOINT,
                HttpMethod.POST, entity, Enclosure.class);

        ResponseEntity<Enclosure> responseThree = restTemplate.exchange(getRootUrl() + ENCLOSURE_ENDPOINT,
                HttpMethod.POST, entity, Enclosure.class);

        Assert.assertNotNull(responseOne.getBody());
        Assert.assertNotNull(responseTwo.getBody());
        Assert.assertNotNull(responseThree.getBody());

        ResponseEntity<List<Enclosure>> response = restTemplate.exchange(getRootUrl() + ENCLOSURE_ENDPOINT,
                HttpMethod.GET, entity, new ParameterizedTypeReference<List<Enclosure>>(){});

        System.out.println("response" + response.getBody());
        System.out.println("code " + response.getStatusCodeValue());

        Assert.assertNotNull(response.getBody());

//        ArrayList<Enclosure> enclosuresList = new ArrayList<>();
//        enclosuresList.addAll(response.getBody().getEnclosures());

        Set<Integer> enclosureIds = new HashSet<>();

        response.getBody().forEach(x -> enclosureIds.add(x.getEnclosureId()));

        Assert.assertTrue(enclosureIds.contains(ENCLOSURE_ID));
        Assert.assertTrue(enclosureIds.contains(responseOne.getBody().getEnclosureId()));
        Assert.assertTrue(enclosureIds.contains(responseTwo.getBody().getEnclosureId()));
        Assert.assertTrue(enclosureIds.contains(responseThree.getBody().getEnclosureId()));

        Assert.assertEquals(4, enclosureIds.size());
    }

    @Test
    public void testGetEnclosure() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", AUTH_TOKEN);



        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        System.out.println(StringUtility.ANSI_RED + "Attempting GET on " + ENCLOSURE_ENDPOINT + "/" + ENCLOSURE_ID + " using AUTH_TOKEN " + AUTH_TOKEN + StringUtility.ANSI_RESET);

        ResponseEntity<Enclosure> response = restTemplate.exchange(getRootUrl() + ENCLOSURE_ENDPOINT + "/" + ENCLOSURE_ID,
                HttpMethod.GET, entity, Enclosure.class);

        Enclosure enc = response.getBody();

        Assert.assertNotNull(enc);

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(ENCLOSURE_ID, enc.getEnclosureId());
        Assert.assertEquals(ENCLOSURE_TITLE, enc.getTitle());
        Assert.assertEquals(ENCLOSURE_LOCATION, enc.getLocation());
        Assert.assertEquals(DIMENSIONAL_UNITS, enc.getDimensionUnits());
        Assert.assertEquals(ENCLOSURE_LENGTH, enc.getLength(), 0);
        Assert.assertEquals(ENCLOSURE_WIDTH, enc.getWidth(), 0);
        Assert.assertEquals(ENCLOSURE_HEIGHT, enc.getHeight(), 0);

    }

    @Test
    public void deleteEnclosure() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", AUTH_TOKEN);
//todo create one before and use that id

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<Enclosure> responseGetBefore = restTemplate.exchange(getRootUrl() + ENCLOSURE_ENDPOINT + "/" + ENCLOSURE_ID,
                HttpMethod.GET, entity, Enclosure.class);

        System.out.println(responseGetBefore.toString());

        Assert.assertNotNull(responseGetBefore.getBody());
        Assert.assertEquals(200, responseGetBefore.getStatusCodeValue());
        Assert.assertEquals(ENCLOSURE_ID, responseGetBefore.getBody().getEnclosureId());

        System.out.println(StringUtility.ANSI_CYAN + "Attempting to DELETE " + getRootUrl() + ENCLOSURE_ENDPOINT + "/" + ENCLOSURE_ID + StringUtility.ANSI_RESET);

        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + ENCLOSURE_ENDPOINT + "/" + ENCLOSURE_ID,
                HttpMethod.DELETE, entity, String.class);

        Assert.assertEquals("{\"message\": \"enclosure deleted successfully\", \"deleted\":" +
                " \"true\", \"enclosure-id\": " + ENCLOSURE_ID + "}", response.getBody());

        ResponseEntity<String> responseGetAfter = restTemplate.exchange(getRootUrl() + "/enclosures/" + ENCLOSURE_ID,
                HttpMethod.GET, entity, String.class);

        Assert.assertEquals(404, responseGetAfter.getStatusCode().value());
    }

}

class AuthRequest {
    private String email_addr;

    AuthRequest(String email){
        this.email_addr = email;
    }

    public String getEmailAddr() {
        return email_addr;
    }

    public void setEmailAddr(String token) {
        this.email_addr = token;
    }
}
