import com.sun.javafx.fxml.builder.JavaFXImageBuilder;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import ro.mta.se.lab.model.City;
import ro.mta.se.lab.model.WeatherDates;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertTrue;
import static junit.framework.TestCase.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)

/**
 * This class implements Junit tests for City class using
 * mockito library and mock object to simulate the
 * WeatherDates class functionalities
 *
 * @author Vlijia Stefan
 */

public class CityTest {
    String cityName = "Moscow";
    String cityId = "524901";
    String lat = "55.752220";
    String lon = "37.615555";
    Image img = null;

    @Before
    public void setUpImg() throws IOException {
        BufferedImage imageBuff =null;
        imageBuff = ImageIO.read(new File("src/main/resources/icons/cloudy.png"));
        this.img = SwingFXUtils.toFXImage(imageBuff, null);
    }

    @After
    public void erase() {
        img = null;
        city = null;
    }

    @Mock
    WeatherDates delegateWeatherDatesMock;

    @InjectMocks
    City city = new City(cityId,cityName,lat,lon);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    //verifica injectarea dependintei de tip setter injection
    @Test
    public void testInjection() throws Exception {
        city.updateWeather();
        verify(delegateWeatherDatesMock).updateWeatherDates(anyString());
    }

    //verifica functionarea metodelor clasei City
    @Test
    public void testMethods() throws Exception {
        WeatherDates weatherDatesMock = mock(WeatherDates.class);

        when(weatherDatesMock.getTemp()).thenReturn("temperatura");
        when(weatherDatesMock.getHumidity()).thenReturn("umiditate");
        when(weatherDatesMock.getPressure()).thenReturn("presiune");
        when(weatherDatesMock.getTime()).thenReturn("timp");
        when(weatherDatesMock.getWind()).thenReturn("vant");
        when(weatherDatesMock.getImg()).thenReturn(img);
        when(weatherDatesMock.getdescription()).thenReturn("descriere");
        when(weatherDatesMock.getRain()).thenReturn(null);

        when(weatherDatesMock.updateWeatherDates(anyString())).thenReturn(true);
        when(weatherDatesMock.setApiKey(anyString())).thenReturn(true);

        city.setWeatherDates(weatherDatesMock);

        assertNull(city.getRain());

        assertTrue(city.updateWeather());
        assertTrue(city.setWeatherApiKey("API_KEY"));

        assertEquals(city.getTemp(),"temperatura");
        assertEquals(city.getHumidity(), "umiditate");
        assertEquals(city.getPressure(), "presiune");
        assertEquals(city.getWind(), "vant");
        assertEquals(city.getTime(), "timp");
        assertEquals(city.getDescription(), "descriere");
        assertEquals(city.getImage(), img);
        assertEquals(city.getCityId(), cityId);
        assertEquals(city.getCityName(), cityName);
    }

    //verifica daca functiile obiectului mock sunt aplelate
    //corect in metodele clasei City
    @Test
    public void verifyCalls() throws Exception {
        WeatherDates weatherDatesMock = mock(WeatherDates.class);

        when(weatherDatesMock.setApiKey(anyString())).thenReturn(true);
        when(weatherDatesMock.updateWeatherDates(anyString())).thenReturn(true);

        city.setWeatherDates(weatherDatesMock);

        city.updateWeather();
        city.setWeatherApiKey(anyString());

        verify(weatherDatesMock, atLeastOnce()).setApiKey(anyString());
        verify(weatherDatesMock, atLeastOnce()).updateWeatherDates(anyString());
        verify(weatherDatesMock, never()).getTemp();

        verifyNoMoreInteractions(weatherDatesMock);
    }

    //verifica daca functia setWeatherApiKey() al clasei City nu altereaza
    //mesajul de eroare returnat de obiectul moock
    @Test
    public void testThrownInvalidApiKey() throws Exception {
        WeatherDates weatherDatesMock = mock(WeatherDates.class);
        when(weatherDatesMock.setApiKey(anyString())).thenThrow(new Exception("Invalid API_KEY!"));
        city.setWeatherDates(weatherDatesMock);

        exception.expect(Exception.class);
        exception.expectMessage("Invalid API_KEY!");
        city.setWeatherApiKey(anyString());
    }

    //verifica daca functia updateWeather() al clasei City nu altereaza
    //mesajul de eroare returnat de obiectul moock
    @Test
    public void testThrownInexistenCityId() throws Exception {
        WeatherDates weatherDatesMock = mock(WeatherDates.class);
        when(weatherDatesMock.updateWeatherDates(anyString())).thenThrow(new Exception("Inexisten cityId!"));
        city.setWeatherDates(weatherDatesMock);

        exception.expect(Exception.class);
        exception.expectMessage("Inexisten cityId!");
        city.updateWeather();
    }

}
