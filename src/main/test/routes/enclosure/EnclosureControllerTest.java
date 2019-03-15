package routes.enclosure;

import io.colby.Application;
import io.colby.modules.routes.enclosures.model.entity.Enclosure;
import io.colby.modules.routes.plants.model.entity.Plant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
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

    private static String ENCLOSURE_ENDPOINT = "/enclosures";

    private static int ENCLOSURE_ID = 0;
    private static String ENCLOSURE_TITLE = "Enclosure Title";
    private static String ENCLOSURE_LOCATION = "Enclosure Location";
    private static String DIMENSIONAL_UNITS = "Meters";
    private static double ENCLOSURE_LENGTH = 1;
    private static double ENCLOSURE_WIDTH = 1.0;
    private static double ENCLOSURE_HEIGHT = 122.2111;

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

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "TEST");

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
        headers.add("Authorization", "TEST");

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

        ResponseEntity<EnclosureList> response = restTemplate.exchange(getRootUrl() + ENCLOSURE_ENDPOINT,
                HttpMethod.GET, entity, EnclosureList.class);

        System.out.println("response" + response.getBody());
        System.out.println("code " + response.getStatusCodeValue());

        Assert.assertNotNull(response.getBody());

        ArrayList<Enclosure> enclosuresList = new ArrayList<>();
        enclosuresList.addAll(response.getBody().getEnclosures());

        Set<Integer> enclosureIds = new HashSet<>();

        enclosuresList.forEach(x -> enclosureIds.add(x.getEnclosureId()));

        Assert.assertTrue(enclosureIds.contains(ENCLOSURE_ID));
        Assert.assertTrue(enclosureIds.contains(responseOne.getBody().getEnclosureId()));
        Assert.assertTrue(enclosureIds.contains(responseTwo.getBody().getEnclosureId()));
        Assert.assertTrue(enclosureIds.contains(responseThree.getBody().getEnclosureId()));

        Assert.assertEquals(4, enclosureIds.size());
    }

    @Test
    public void testGetEnclosure() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "TEST");


        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<Enclosure> response = restTemplate.exchange(getRootUrl() + ENCLOSURE_ENDPOINT + "/" + ENCLOSURE_ID,
                HttpMethod.GET, entity, Enclosure.class);

        Enclosure enc = response.getBody();

        Assert.assertNotNull(response.getBody());

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
        headers.add("Authorization", "TEST");
//todo create one before and use that id

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<Enclosure> responseGetBefore = restTemplate.exchange(getRootUrl() + ENCLOSURE_ENDPOINT + "/" + ENCLOSURE_ID,
                HttpMethod.GET, entity, Enclosure.class);

        Assert.assertNotNull(responseGetBefore.getBody());
        Assert.assertEquals(200, responseGetBefore.getStatusCodeValue());
        Assert.assertEquals(ENCLOSURE_ID, responseGetBefore.getBody().getEnclosureId());

        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/enclosures/" + ENCLOSURE_ID,
                HttpMethod.DELETE, entity, String.class);

        Assert.assertEquals("{\"message\": \"enclosure deleted successfully\", \"deleted\":" +
                " \"true\", \"enclosure-id\": " + ENCLOSURE_ID + "}", response.getBody());

        ResponseEntity<String> responseGetAfter = restTemplate.exchange(getRootUrl() + "/enclosures/" + ENCLOSURE_ID,
                HttpMethod.GET, entity, String.class);

        Assert.assertEquals(404, responseGetAfter.getStatusCode().value());
    }

}

class EnclosureList {

    private HashMap<String, Enclosure> enclosures;

    Collection<Enclosure> getEnclosures() {
        return enclosures.values();
    }

    public void setEnclosures(HashMap<String, Enclosure> enclosures) {
        this.enclosures = enclosures;
    }

    @Override
    public String toString() {
        return "EnclosureList{" +
                "enclosures=" + enclosures +
                '}';
    }
}
