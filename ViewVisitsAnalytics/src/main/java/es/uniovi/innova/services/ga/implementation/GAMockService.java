package main.java.es.uniovi.innova.services.ga.implementation;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

import main.java.es.uniovi.innova.services.ga.IGAService;

public class GAMockService implements IGAService {

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> getVisitsByPage(Date start, Date end) {
		return Collections.EMPTY_MAP;
	}

	@Override
	public void setUA(String uA) {
	}

	@Override
	public String getUA() {
		return "";
	}

	@Override
	public int getVisitsByInterval(Date start, Date end) {
		return (int) Math.random()*10000000;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> getVisitsByOS(Date start, Date end) {
		return Collections.EMPTY_MAP;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> getVisitsByBrowser(Date start, Date end) {
		return Collections.EMPTY_MAP;
	}

}