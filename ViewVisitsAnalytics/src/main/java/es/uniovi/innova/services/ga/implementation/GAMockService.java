package main.java.es.uniovi.innova.services.ga.implementation;

import java.util.Date;

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

	@Override
	public void setUA(String uA) {
	}

	@Override
	public String getUA() {
		return "";
	}

	@Override
	public int numOfVisitsByInterval(Date start, Date end) {
		return (int) Math.random()*10000000;
	}

}
