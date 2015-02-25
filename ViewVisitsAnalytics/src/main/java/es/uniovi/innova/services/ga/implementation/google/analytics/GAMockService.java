package main.java.es.uniovi.innova.services.ga.implementation.google.analytics;

import main.java.es.uniovi.innova.services.ga.IGAService;



public class GAMockService implements IGAService {

	@Override
	public int numOfVisitsByDay(int day, int month, int year) {
		return (int) Math.random()*250;
	}

	@Override
	public int numOfVisitsByMonth(int month, int year) {
		return (int) Math.random()*10000;
	}

	@Override
	public int numOfVisitsByYear(int year) {
		return (int) Math.random()*10000000;
	}

}
