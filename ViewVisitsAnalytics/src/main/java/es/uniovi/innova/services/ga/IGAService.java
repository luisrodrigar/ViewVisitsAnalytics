package main.java.es.uniovi.innova.services.ga;

import java.util.Date;
import java.util.Map;

public interface IGAService {
	
	int numOfVisitsByInterval(Date start, Date end);
	
	Map<String, String> getVisitsByPage(Date start, Date end);
	
	void setUA(String uA);
	
	String getUA();
}
