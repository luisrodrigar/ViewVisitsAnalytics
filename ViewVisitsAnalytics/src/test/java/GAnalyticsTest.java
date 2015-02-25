package test.java;

import java.io.IOException;

import main.java.es.uniovi.innova.services.ga.implementation.google.analytics.GAnalyticsService;

import org.junit.Test;

public class GAnalyticsTest {

	@Test
	public void testAuthorization() throws IOException {
		GAnalyticsService service = new GAnalyticsService();
		service.numOfVisitsByDay(6,2,2015);
	}

}
