package main.java.es.uniovi.innova.services.ga;

import java.util.Date;

public interface IGAService {
	int numOfVisitsByDay(int day, int month, int year);

	int numOfVisitsByMonth(int month, int year);

	int numOfVisitsByYear(int year);
	
	int numOfVisitsByInterval(Date start, Date end);
	
	void setUA(String uA);
	
	String getUA();
}
