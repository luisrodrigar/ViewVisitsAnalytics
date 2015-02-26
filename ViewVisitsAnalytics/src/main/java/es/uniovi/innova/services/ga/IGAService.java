package main.java.es.uniovi.innova.services.ga;

public interface IGAService {
	int numOfVisitsByDay(int day, int month, int year);

	int numOfVisitsByMonth(int month, int year);

	int numOfVisitsByYear(int year);

	int numOfVisitsBetweenTwoDates(int day_before, int month_before,
			int year_before, int day_after, int month_after, int year_after);

	void setUA(String uA);

	String getUA();
}
