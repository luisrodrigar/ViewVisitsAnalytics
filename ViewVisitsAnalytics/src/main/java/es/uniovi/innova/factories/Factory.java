package main.java.es.uniovi.innova.factories;

import main.java.es.uniovi.innova.services.ga.IGAService;
import main.java.es.uniovi.innova.services.ga.IPortalesService;
import main.java.es.uniovi.innova.services.ga.implementation.GAnalyticsServiceNewData;
import main.java.es.uniovi.innova.services.ga.implementation.GAnalyticsServiceOldData;

public class Factory {

	private GAnalyticsServiceNewData serviceGoogleAnalyticsNewData;
	private GAnalyticsServiceOldData serviceGoogleAnalyticsOldData;
	
	public IGAService getServiceGoogleAnalyticsNewData() {
		return serviceGoogleAnalyticsNewData;
	}

	public void setServiceGoogleAnalyticsNewData(
			IGAService serviceGoogleAnalyticsNewData) {
		this.serviceGoogleAnalyticsNewData = (GAnalyticsServiceNewData) serviceGoogleAnalyticsNewData;
	}

	public IGAService getServiceGoogleAnalyticsOldData() {
		return serviceGoogleAnalyticsOldData;
	}

	public void setServiceGoogleAnalyticsOldData(
			IGAService serviceGoogleAnalyticsOldData) {
		this.serviceGoogleAnalyticsOldData = (GAnalyticsServiceOldData) serviceGoogleAnalyticsOldData;
	}

	private IPortalesService servicePortales;


	public IPortalesService getServicePortales() {
		return servicePortales;
	}

	public void setServicePortales(IPortalesService servicePortales) {
		this.servicePortales = servicePortales;
	}



	
	
	
}
