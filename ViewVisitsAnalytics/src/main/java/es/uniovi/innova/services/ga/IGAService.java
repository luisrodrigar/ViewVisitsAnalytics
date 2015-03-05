package main.java.es.uniovi.innova.services.ga;

import java.util.Date;
import java.util.Map;

public interface IGAService {
	
	int getVisitsByInterval(Date start, Date end, String ua);
	
	Map<String, String> getVisitsByPage(Date start, Date end, String ua);


	Map<String, String> getVisitsByOS(Date start, Date end, String ua);

	Map<String, String> getVisitsByBrowser(Date start, Date end, String ua);
}
