package main.java.es.uniovi.innova.factory;

import main.java.es.uniovi.innova.services.ga.IGAService;
import main.java.es.uniovi.innova.services.portal.IPortalesService;

public class Factory {
	private IGAService serviceGoggleAnalytics;
	private IPortalesService servicePortales;

	public IGAService getServiceGoggleAnalytics() {
		return serviceGoggleAnalytics;
	}

	public void setServiceGoggleAnalytics(IGAService serviceGoggleAnalytics) {
		this.serviceGoggleAnalytics = serviceGoggleAnalytics;
	}

	public IPortalesService getServicePortales() {
		return servicePortales;
	}

	public void setServicePortales(IPortalesService servicePortales) {
		this.servicePortales = servicePortales;
	}

}
