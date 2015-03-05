package test.java;

import java.io.IOException;
import java.util.Date;

import main.java.es.uniovi.innova.services.ga.IGAService;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GAnalyticsTest {

	private BeanFactory factory;

	@SuppressWarnings("deprecation")
	@Test
	public void testAuthorization() throws IOException {
		factory = new ClassPathXmlApplicationContext("beans.xml");
		IGAService service = (IGAService) factory.getBean("gAnalyticsService");
		service.getVisitsByInterval(new Date(114, 11, 25), new Date(115,1,22),"UA-57349981-1");
	}

}
