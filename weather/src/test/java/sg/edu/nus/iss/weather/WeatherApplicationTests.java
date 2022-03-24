package sg.edu.nus.iss.weather;

import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.util.Optional;

import org.junit.jupiter.api.Assertions.*;
// import org.apache.tomcat.util.http.parser.MediaType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import sg.edu.nus.iss.weather.models.Weather;
import sg.edu.nus.iss.weather.services.WeatherService;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class WeatherApplicationTests {

	@Autowired
	private WeatherService weatherSvc;

	@Autowired
	private MockMvc mvc;

	@Test
	void contextLoads() {
		Optional<Weather> opt = weatherSvc.getWeather("singapore");
		Assertions.assertTrue(opt.isPresent());
	}


	@Test
	void shouldReturnWeatherOfTokyo() throws Exception {

		// checking if able to call this API
		// GET /weather?city=tokyo
		RequestBuilder req = MockMvcRequestBuilders.get("/weather")
							.queryParam("city", "tokyo")
							.accept(MediaType.TEXT_HTML);

		MvcResult result = mvc.perform(req).andReturn();
		int status = result.getResponse().getStatus();
		Assertions.assertEquals(200,status);

		String payload = result.getResponse().getContentAsString();
		Assertions.assertTrue(payload.indexOf("<span>Tokyo</span>") > 0);
		 

	}

}
