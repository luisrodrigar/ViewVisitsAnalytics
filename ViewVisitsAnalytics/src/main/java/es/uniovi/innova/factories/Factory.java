package main.java.es.uniovi.innova.factories;

import main.java.es.uniovi.innova.services.ga.IGAService;
import main.java.es.uniovi.innova.services.ga.IPortalesService;

public class Factory {

	private IGAService serviceGoogleAnalytics;
	private IPortalesService servicePortales;

	public IGAService getServiceGoogleAnalytics() {
		return serviceGoogleAnalytics;
	}

	public void setServiceGoogleAnalytics(IGAService serviceGoogleAnalytics) {
		this.serviceGoogleAnalytics = serviceGoogleAnalytics;
	}

	public IPortalesService getServicePortales() {
		return servicePortales;
	}

	public void setServicePortales(IPortalesService servicePortales) {
		this.servicePortales = servicePortales;
	}



	
	
	
}
