package routes.sensor;

import io.colby.Application;
import io.colby.modules.auth.model.entity.Auth;
import io.colby.modules.routes.enclosures.model.entity.Enclosure;
import io.colby.modules.routes.plants.model.entity.Plant;
import io.colby.modules.routes.sensors.entity.SensorType;
import io.colby.modules.routes.sensors.model.entity.Sensor;
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

import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SensorControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private boolean setupRan = false;

    private static final String AUTH_ENDPOINT = "/auth";
    private static final String ENCLOSURE_ENDPOINT = "/enclosures";
    private static final String PLANT_ENDPOINT = "/plants";
    private static final String SENSOR_ENDPOINT = "/sensors";

    private static String AUTH_TOKEN = "";

    private static int ENCLOSURE_ID = 0;
    private static final String ENCLOSURE_TITLE = "Enclosure Title";
    private static final String ENCLOSURE_LOCATION = "Enclosure Location";
    private static final String DIMENSIONAL_UNITS = "Meters";
    private static final double ENCLOSURE_LENGTH = 1;
    private static final double ENCLOSURE_WIDTH = 1.0;
    private static final double ENCLOSURE_HEIGHT = 122.2111;

    private static int SENSOR_ID = 0;
    private static final SensorType SENSOR_TYPE_SM = SensorType.SOIL_MOISTURE;
    private static final SensorType SENSOR_TYPE_TMP = SensorType.SOIL_TEMPERATURE;
    private static final SensorType SENSOR_TYPE_HMD = SensorType.TEMPERATURE_HUMIDITY;

    private static int PLANT_ID = 0;
    private static final String PLANT_NAME = "Sensor plant name";
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

    private Enclosure constructEnclosure() {
        return new Enclosure(ENCLOSURE_TITLE, ENCLOSURE_LOCATION,
                DIMENSIONAL_UNITS, ENCLOSURE_LENGTH, ENCLOSURE_WIDTH, ENCLOSURE_HEIGHT);
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

    private Sensor constructSensor(SensorType sensorType, boolean enc, boolean plnt){
        Sensor sensor = new Sensor();

        sensor.setEnclosure(enc);
        sensor.setPlant(plnt);

        sensor.setEnclosureId(ENCLOSURE_ID);
        sensor.setPlantId(PLANT_ID);

        sensor.setType(sensorType);

        return sensor;
    }

    @Test
    public void contextLoads() {

    }

    @Before
    @Test
    public void testCreateSensor() {

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
        HttpEntity<Enclosure> sensorEnc = new HttpEntity<>(constructEnclosure(), headers);

        ResponseEntity<Enclosure> responseEnc = restTemplate.exchange(getRootUrl() + ENCLOSURE_ENDPOINT,
                HttpMethod.POST, sensorEnc, Enclosure.class);

        Assert.assertNotNull(responseEnc.getBody());

        System.out.println(StringUtility.applyCyan(responseEnc.toString()));

        Enclosure enc = responseEnc.getBody();

        ENCLOSURE_ID = enc.getEnclosureId();

        //create plant
        HttpEntity<Plant> entityPlnt = new HttpEntity<>(constructPlant(ENCLOSURE_ID), headers);

        ResponseEntity<Plant> responsePlnt = restTemplate.exchange(getRootUrl() + PLANT_ENDPOINT,
                HttpMethod.POST, entityPlnt, Plant.class);

        Assert.assertNotNull(responsePlnt.getBody());

        System.out.println(StringUtility.applyCyan(responseEnc.toString()));

        Plant plnt = responsePlnt.getBody();

        PLANT_ID = plnt.getPlantId();

        Assert.assertTrue(PLANT_ID > 0);
        Assert.assertTrue(ENCLOSURE_ID > 0);

        //create sensor - temp-humidity
        HttpEntity<Sensor> entitySnsr = new HttpEntity<>(constructSensor(SENSOR_TYPE_HMD, true, false), headers);

        ResponseEntity<Sensor> responseSnsr = restTemplate.exchange(getRootUrl() + SENSOR_ENDPOINT,
                HttpMethod.POST, entitySnsr, Sensor.class);

        Assert.assertNotNull(responseSnsr.getBody());

        Sensor snsr = responseSnsr.getBody();

        Assert.assertEquals(HttpServletResponse.SC_CREATED, responseSnsr.getStatusCodeValue());
        Assert.assertEquals(SENSOR_TYPE_HMD, snsr.getType());
        Assert.assertEquals(true, snsr.isEnclosure());
        Assert.assertEquals(false, snsr.isPlant());
        Assert.assertEquals(ENCLOSURE_ID, snsr.getEnclosureId());
        Assert.assertEquals(PLANT_ID, snsr.getPlantId());

        //create sensor - soil moisture
        entitySnsr = new HttpEntity<>(constructSensor(SENSOR_TYPE_SM, false, true), headers);

        responseSnsr = restTemplate.exchange(getRootUrl() + SENSOR_ENDPOINT,
                HttpMethod.POST, entitySnsr, Sensor.class);

        Assert.assertNotNull(responsePlnt.getBody());

        snsr = responseSnsr.getBody();

        SENSOR_ID = snsr.getSensorId();

        Assert.assertEquals(HttpServletResponse.SC_CREATED, responseSnsr.getStatusCodeValue());
        Assert.assertEquals(SENSOR_TYPE_SM, snsr.getType());
        Assert.assertEquals(false, snsr.isEnclosure());
        Assert.assertEquals(true, snsr.isPlant());
        Assert.assertEquals(ENCLOSURE_ID, snsr.getEnclosureId());
        Assert.assertEquals(PLANT_ID, snsr.getPlantId());

        //create sensor - soil temp
        entitySnsr = new HttpEntity<>(constructSensor(SENSOR_TYPE_TMP, false, true), headers);

        responseSnsr = restTemplate.exchange(getRootUrl() + SENSOR_ENDPOINT,
                HttpMethod.POST, entitySnsr, Sensor.class);

        Assert.assertNotNull(responsePlnt.getBody());

        snsr = responseSnsr.getBody();

        Assert.assertEquals(HttpServletResponse.SC_CREATED, responseSnsr.getStatusCodeValue());
        Assert.assertEquals(SENSOR_TYPE_TMP, snsr.getType());
        Assert.assertEquals(false, snsr.isEnclosure());
        Assert.assertEquals(true, snsr.isPlant());
        Assert.assertEquals(ENCLOSURE_ID, snsr.getEnclosureId());
        Assert.assertEquals(PLANT_ID, snsr.getPlantId());

        Assert.assertEquals(PLANT_NAME, plnt.getName());
        Assert.assertEquals(POT_SIZE, plnt.getPotSize());
        Assert.assertEquals(POT_SIZE_UNITS, plnt.getPotSizeUnits());
        Assert.assertEquals(YIELD, plnt.getYield(), 0);
        Assert.assertEquals(YIELD_UNITS, plnt.getYieldUnits());
        Assert.assertEquals(DATE_PLANTED, plnt.getDatePlanted());
        Assert.assertEquals(DATE_HARVESTED, plnt.getDateHarvested());
        Assert.assertEquals(ENCLOSURE_ID, plnt.getEnclosureId());

        Assert.assertEquals(ENCLOSURE_TITLE, enc.getTitle());
        Assert.assertEquals(ENCLOSURE_LOCATION, enc.getLocation());
        Assert.assertEquals(DIMENSIONAL_UNITS, enc.getDimensionUnits());
        Assert.assertEquals(ENCLOSURE_LENGTH, enc.getLength(), 0);
        Assert.assertEquals(ENCLOSURE_WIDTH, enc.getWidth(), 0);
        Assert.assertEquals(ENCLOSURE_HEIGHT, enc.getHeight(), 0);

    }

    @Test
    public void testGetAllSensors() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", AUTH_TOKEN);

        HttpEntity<Sensor> entity = new HttpEntity<>(constructSensor(SENSOR_TYPE_HMD, true, false), headers);

        ResponseEntity<Sensor> responseOne = restTemplate.exchange(getRootUrl() + SENSOR_ENDPOINT,
                HttpMethod.POST, entity, Sensor.class);

        ResponseEntity<Sensor> responseTwo = restTemplate.exchange(getRootUrl() + SENSOR_ENDPOINT,
                HttpMethod.POST, entity, Sensor.class);

        ResponseEntity<Sensor> responseThree = restTemplate.exchange(getRootUrl() + SENSOR_ENDPOINT,
                HttpMethod.POST, entity, Sensor.class);

        Assert.assertNotNull(responseOne.getBody());
        Assert.assertNotNull(responseTwo.getBody());
        Assert.assertNotNull(responseThree.getBody());

        ResponseEntity<List<Sensor>> response = restTemplate.exchange(getRootUrl() + SENSOR_ENDPOINT,
                HttpMethod.GET, entity, new ParameterizedTypeReference<List<Sensor>>(){});

        System.out.println("response" + response.getBody());
        System.out.println("code " + response.getStatusCodeValue());

        Assert.assertNotNull(response.getBody());

        Set<Integer> sensorIds = new HashSet<>();

        response.getBody().forEach(x -> sensorIds.add(x.getSensorId()));

        Assert.assertTrue(sensorIds.contains(SENSOR_ID));
        Assert.assertEquals(ENCLOSURE_ID, responseOne.getBody().getEnclosureId());
        Assert.assertEquals(PLANT_ID, responseOne.getBody().getPlantId());
        Assert.assertEquals(ENCLOSURE_ID, responseThree.getBody().getEnclosureId());
        Assert.assertEquals(PLANT_ID, responseThree.getBody().getPlantId());
        Assert.assertEquals(ENCLOSURE_ID, responseThree.getBody().getEnclosureId());
        Assert.assertEquals(PLANT_ID, responseThree.getBody().getPlantId());
        Assert.assertTrue(sensorIds.contains(responseOne.getBody().getSensorId()));
        Assert.assertTrue(sensorIds.contains(responseTwo.getBody().getSensorId()));
        Assert.assertTrue(sensorIds.contains(responseThree.getBody().getSensorId()));

        //Three newly create sensors plus the initial sensor in @Before test
        Assert.assertEquals(6, sensorIds.size());
    }

    @Test
    public void testGetSensor() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", AUTH_TOKEN);

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        System.out.println(StringUtility.ANSI_RED + "Attempting GET on " + SENSOR_ENDPOINT + "/" + SENSOR_ID + " using AUTH_TOKEN " + AUTH_TOKEN + StringUtility.ANSI_RESET);

        ResponseEntity<Sensor> response = restTemplate.exchange(getRootUrl() + SENSOR_ENDPOINT + "/" + SENSOR_ID,
                HttpMethod.GET, entity, Sensor.class);

        Sensor snsr = response.getBody();

        Assert.assertNotNull(snsr);

        Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatusCodeValue());
        Assert.assertEquals(SENSOR_TYPE_SM, snsr.getType());
        Assert.assertEquals(false, snsr.isEnclosure());
        Assert.assertEquals(true, snsr.isPlant());
        Assert.assertEquals(ENCLOSURE_ID, snsr.getEnclosureId());
        Assert.assertEquals(PLANT_ID, snsr.getPlantId());

    }

    @Test
    public void deleteSensor() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", AUTH_TOKEN);
//todo create one before and use that id

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<Sensor> responseGetBefore = restTemplate.exchange(getRootUrl() + SENSOR_ENDPOINT + "/" + SENSOR_ID,
                HttpMethod.GET, entity, Sensor.class);

        System.out.println(responseGetBefore.toString());

        Assert.assertNotNull(responseGetBefore.getBody());
        Assert.assertEquals(HttpServletResponse.SC_OK, responseGetBefore.getStatusCodeValue());
        Assert.assertEquals(SENSOR_ID, responseGetBefore.getBody().getSensorId());

        System.out.println(StringUtility.applyCyan("Attempting to DELETE " + getRootUrl() + SENSOR_ENDPOINT
                + "/" + SENSOR_ID ));

        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + SENSOR_ENDPOINT + "/" + SENSOR_ID,
                HttpMethod.DELETE, entity, String.class);

        Assert.assertNotNull(response.getBody());

        Assert.assertEquals("{\"message\": \"sensor deleted successfully\", \"deleted\":" +
                " \"true\", \"sensor-id\": " + SENSOR_ID + "}", response.getBody());

        ResponseEntity<String> responseGetAfter = restTemplate.exchange(getRootUrl() + "/sensors/" + SENSOR_ID,
                HttpMethod.GET, entity, String.class);

        Assert.assertEquals(HttpServletResponse.SC_NOT_FOUND, responseGetAfter.getStatusCode().value());
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
