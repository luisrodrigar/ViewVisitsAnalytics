package es.uniovi.innova.ViewVisitsAnalytics.factory;

import es.uniovi.innova.ViewVisitsAnalytics.services.ga.IGAService;
import es.uniovi.innova.ViewVisitsAnalytics.services.portal.IPortalesService;


public class Factory {
	private IGAService serviceGoggleAnalyticsTemp;
	private IGAService serviceGoggleAnalyticsPermanent;
	private IPortalesService servicePortales;


	public IPortalesService getServicePortales() {
		return servicePortales;
	}

	public void setServicePortales(IPortalesService servicePortales) {
		this.servicePortales = servicePortales;
	}

	public IGAService getServiceGoggleAnalyticsTemp() {
		return serviceGoggleAnalyticsTemp;
	}

	public void setServiceGoggleAnalyticsTemp(IGAService serviceGoggleAnalyticsTemp) {
		this.serviceGoggleAnalyticsTemp = serviceGoggleAnalyticsTemp;
	}

	public IGAService getServiceGoggleAnalyticsPermanent() {
		return serviceGoggleAnalyticsPermanent;
	}

	public void setServiceGoggleAnalyticsPermanent(
			IGAService serviceGoggleAnalyticsPermanent) {
		this.serviceGoggleAnalyticsPermanent = serviceGoggleAnalyticsPermanent;
	}

}
