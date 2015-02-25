package main.java.es.uniovi.innova.services.ga;

public interface IGAService {
	int numOfVisitsByDay(int day, int month, int year);

	int numOfVisitsByMonth(int month, int year);

	int numOfVisitsByYear(int year);
}
