import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ro.mta.se.lab.model.WeatherDates;

import static junit.framework.TestCase.*;

/**
 * This class implements Junit tests for WeatherDates class
 *
 * @author Vlijia Stefan
 */

public class WeatherDatesTest {
    WeatherDates currentWeatherDates = null;

    @Before
    public void setUp() {
        currentWeatherDates = new WeatherDates();
    }

    @After
    public void eraseWeatherDates() {
        this.currentWeatherDates = null;
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    //verifica daca mesajul de eroare continut de eroarea aruncata
    //in cazul in care api key-ul este invalid, este cel asteptat
    @Test
    public void throwsExceptionsWrongApiKey() throws Exception {
        String API_KEY = "KK9caee444bc2df925cb6eba10841e9f28";
        String expectedMsg = "Server returned HTTP response code: 401 for URL: http://api.openweathermap.org/data/2.5/weather?id=524901" + "&appid=" + API_KEY + "&units=metric";

        exception.expect(Exception.class);
        exception.expectMessage(expectedMsg);

        currentWeatherDates.setApiKey(API_KEY);
    }

    //verifica daca mesajul de eroare continut de eroarea aruncata
    //in cazul in care id-ul orasului este gresit, este cel asteptat
    @Test
    public void throwsIOExceptionsForInexistentCityId() throws Exception {
        String id = "3524901";
        String API_KEY = "9caee444bc2df925cb6eba10841e9f28";
        String expectedMsg = "http://api.openweathermap.org/data/2.5/weather?id=" + id + "&appid=" + API_KEY + "&units=metric";

        exception.expect(Exception.class);
        exception.expectMessage(expectedMsg);

        assertTrue(currentWeatherDates.setApiKey(API_KEY));
        currentWeatherDates.updateWeatherDates(id);
    }

    //am observat ca exista campuri care in raspunsul json nu apar
    //desi in documentatia de pe site sunt specificate
    //in acest caz este posibil ca valabilitatea acestor campuri sa varieze
    @Test
    public void testIfDataExtactedFromJsonExists() throws Exception {
        String id = "524901";
        String API_KEY = "9caee444bc2df925cb6eba10841e9f28";

        assertTrue(currentWeatherDates.setApiKey(API_KEY));
        assertTrue(currentWeatherDates.updateWeatherDates(id));

        assertNotNull(currentWeatherDates.getTemp());
        assertNotNull(currentWeatherDates.getdescription());
        assertNotNull(currentWeatherDates.getHumidity());
        assertNotNull(currentWeatherDates.getImg());
        assertNotNull(currentWeatherDates.getPressure());
        assertNotNull(currentWeatherDates.getTime());
    }

    //campul Rain nu exista in raspuns desi in documentatie exista
    @Test
    public void testIfRainItemFomJsonExists() throws Exception {
        String id = "524901";
        String API_KEY = "9caee444bc2df925cb6eba10841e9f28";

        assertTrue(currentWeatherDates.setApiKey(API_KEY));
        assertTrue(currentWeatherDates.updateWeatherDates(id));

        assertNull(currentWeatherDates.getRain());
    }

}