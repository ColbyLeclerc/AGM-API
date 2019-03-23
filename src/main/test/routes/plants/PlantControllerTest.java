package routes.plants;

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

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlantControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private boolean setupRan = false;

    private static final String AUTH_ENDPOINT = "/auth";
    private static final String ENCLOSURE_ENDPOINT = "/enclosures";
    private static final String PLANT_ENDPOINT = "/plants";

    private static String AUTH_TOKEN = "";

    private static int PLANT_ID = 0;
    private static int ENCLOSURE_ID = 0;
    private static final String PLANT_NAME = "Plant Name";
    private static final int POT_SIZE = 12;
    private static final String POT_SIZE_UNITS = "Gallons";
    private static final double YIELD = 1.5;
    private static final String YIELD_UNITS = "lbs";
    private static final LocalDateTime DATE_HARVESTED = LocalDateTime.now();
    private static final LocalDateTime DATE_PLANTED = LocalDateTime.now().minus(Duration.ofSeconds(86400));

    @LocalServerPort
    private int port;

    private String getRootUrl() {

        return "http://localhost:" + port;

    }

    private Plant constructPlant(int enclosureId) {
        Plant plant = new Plant();

        plant.setEnclosureId(enclosureId);
        plant.setName(PLANT_NAME);
        plant.setPotSize(POT_SIZE);
        plant.setPotSizeUnits(POT_SIZE_UNITS);
        plant.setYield(YIELD);
        plant.setYieldUnits(YIELD_UNITS);
        plant.setDateHarvested(DATE_HARVESTED);
        plant.setDatePlanted(DATE_PLANTED);

        return plant;

    }


    @Test
    public void contextLoads() {

    }

    @Before
    @Test
    public void testCreatePlant() {

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

        //create enclosure
        HttpEntity<Enclosure> entityEnc = new HttpEntity<>(new Enclosure("plant enc test"), headers);

        ResponseEntity<Enclosure> responseEnc = restTemplate.exchange(getRootUrl() + ENCLOSURE_ENDPOINT,
                HttpMethod.POST, entityEnc, Enclosure.class);

        Assert.assertNotNull(responseEnc.getBody());

        System.out.println(StringUtility.applyCyan(responseEnc.toString()));

        Enclosure enc = responseEnc.getBody();

        ENCLOSURE_ID = enc.getEnclosureId();

        System.out.println(StringUtility.applyCyan("ENCLOSURE_ID : " + ENCLOSURE_ID));

        HttpEntity<Plant> entity = new HttpEntity<>(constructPlant(ENCLOSURE_ID), headers);

//        ResponseEntity<String> responseStr = restTemplate.exchange(getRootUrl() + PLANT_ENDPOINT,
//                HttpMethod.POST, entity, String.class);

//        System.out.println(responseStr.getBody());

        ResponseEntity<Plant> response = restTemplate.exchange(getRootUrl() + PLANT_ENDPOINT,
                HttpMethod.POST, entity, Plant.class);

        Plant plnt = response.getBody();

        System.out.println(StringUtility.ANSI_CYAN + response.getBody() + StringUtility.ANSI_RESET);

        Assert.assertNotNull(response.getBody());

        Assert.assertEquals(ENCLOSURE_ID, plnt.getEnclosureId());

        PLANT_ID = plnt.getPlantId();

        Assert.assertEquals(201, response.getStatusCodeValue());
        Assert.assertEquals(PLANT_NAME, plnt.getName());
        Assert.assertEquals(POT_SIZE, plnt.getPotSize());
        Assert.assertEquals(POT_SIZE_UNITS, plnt.getPotSizeUnits());
        Assert.assertEquals(YIELD, plnt.getYield(), 0);
        Assert.assertEquals(YIELD_UNITS, plnt.getYieldUnits());
        Assert.assertEquals(DATE_PLANTED, plnt.getDatePlanted());
        Assert.assertEquals(DATE_HARVESTED, plnt.getDateHarvested());
        Assert.assertEquals(ENCLOSURE_ID, plnt.getEnclosureId());

    }

    @Test
    public void testGetAllPlants() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", AUTH_TOKEN);

        HttpEntity<Plant> entity = new HttpEntity<>(constructPlant(ENCLOSURE_ID), headers);

        ResponseEntity<Plant> responseOne = restTemplate.exchange(getRootUrl() + PLANT_ENDPOINT,
                HttpMethod.POST, entity, Plant.class);

        ResponseEntity<Plant> responseTwo = restTemplate.exchange(getRootUrl() + PLANT_ENDPOINT,
                HttpMethod.POST, entity, Plant.class);

        ResponseEntity<Plant> responseThree = restTemplate.exchange(getRootUrl() + PLANT_ENDPOINT,
                HttpMethod.POST, entity, Plant.class);

        Assert.assertNotNull(responseOne.getBody());
        Assert.assertNotNull(responseTwo.getBody());
        Assert.assertNotNull(responseThree.getBody());

        ResponseEntity<List<Plant>> response = restTemplate.exchange(getRootUrl() + PLANT_ENDPOINT,
                HttpMethod.GET, entity, new ParameterizedTypeReference<List<Plant>>(){});

        System.out.println("response" + response.getBody());
        System.out.println("code " + response.getStatusCodeValue());

        Assert.assertNotNull(response.getBody());

        Set<Integer> plantIds = new HashSet<>();

        response.getBody().forEach(x -> plantIds.add(x.getPlantId()));

        Assert.assertTrue(plantIds.contains(PLANT_ID));
        Assert.assertEquals(ENCLOSURE_ID, responseOne.getBody().getEnclosureId());
        Assert.assertEquals(ENCLOSURE_ID, responseThree.getBody().getEnclosureId());
        Assert.assertEquals(ENCLOSURE_ID, responseThree.getBody().getEnclosureId());
        Assert.assertTrue(plantIds.contains(responseOne.getBody().getPlantId()));
        Assert.assertTrue(plantIds.contains(responseTwo.getBody().getPlantId()));
        Assert.assertTrue(plantIds.contains(responseThree.getBody().getPlantId()));

        //Three newly create plants plus the initial plant in @Before test
        Assert.assertEquals(4, plantIds.size());
    }

    @Test
    public void testGetPlant() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", AUTH_TOKEN);

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        System.out.println(StringUtility.ANSI_RED + "Attempting GET on " + PLANT_ENDPOINT + "/" + PLANT_ID + " using AUTH_TOKEN " + AUTH_TOKEN + StringUtility.ANSI_RESET);

        ResponseEntity<Plant> response = restTemplate.exchange(getRootUrl() + PLANT_ENDPOINT + "/" + PLANT_ID,
                HttpMethod.GET, entity, Plant.class);

        Plant plnt = response.getBody();

        Assert.assertNotNull(plnt);

        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(PLANT_NAME, plnt.getName());
        Assert.assertEquals(POT_SIZE, plnt.getPotSize());
        Assert.assertEquals(POT_SIZE_UNITS, plnt.getPotSizeUnits());
        Assert.assertEquals(YIELD, plnt.getYield(), 0);
        Assert.assertEquals(YIELD_UNITS, plnt.getYieldUnits());
        Assert.assertEquals(DATE_PLANTED, plnt.getDatePlanted());
        Assert.assertEquals(DATE_HARVESTED, plnt.getDateHarvested());
        Assert.assertEquals(ENCLOSURE_ID, plnt.getEnclosureId());

    }

    @Test
    public void deletePlant() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", AUTH_TOKEN);
//todo create one before and use that id

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<Plant> responseGetBefore = restTemplate.exchange(getRootUrl() + PLANT_ENDPOINT + "/" + PLANT_ID,
                HttpMethod.GET, entity, Plant.class);

        System.out.println(responseGetBefore.toString());

        Assert.assertNotNull(responseGetBefore.getBody());
        Assert.assertEquals(200, responseGetBefore.getStatusCodeValue());
        Assert.assertEquals(PLANT_ID, responseGetBefore.getBody().getPlantId());

        System.out.println(StringUtility.ANSI_CYAN + "Attempting to DELETE " + getRootUrl() + PLANT_ENDPOINT + "/" + PLANT_ID + StringUtility.ANSI_RESET);

        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + PLANT_ENDPOINT + "/" + PLANT_ID,
                HttpMethod.DELETE, entity, String.class);

        Assert.assertNotNull(response.getBody());

        Assert.assertEquals("{\"message\": \"plant deleted successfully\", \"deleted\":" +
                " \"true\", \"plant-id\": " + PLANT_ID + "}", response.getBody());

        ResponseEntity<String> responseGetAfter = restTemplate.exchange(getRootUrl() + "/enclosures/" + PLANT_ID,
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
