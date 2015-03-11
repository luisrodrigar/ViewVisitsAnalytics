package es.uniovi.innova.ViewVisitsAnalytics.services.ga.implementation.cache;

import java.util.Date;
import java.util.Map;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

import es.uniovi.innova.ViewVisitsAnalytics.services.ga.implementation.GAnalyticsService;


public class GANalyticsServiceTemp extends GAnalyticsService {
	
	final static Logger log = LoggerFactory.getLogger(GAnalyticsService.class);
	
	@Override
	@Cacheable(value = "visitsTemp", key = "#root.methodName.concat(#ua.toString()).concat(#start.toString()).concat(#end.toString())")
	public int getVisitsByInterval(Date start, Date end, String ua) {
		log.info("Temp cache || data by interval || stored");
		return super.getVisitsByInterval(start, end, ua);
	}

	@Override
	@Cacheable(value = "visitsTemp", key = "#root.methodName.concat(#ua.toString()).concat(#start.toString()).concat(#end.toString())")
	public Map<String, String> getVisitsByPage(Date start, Date end, String ua) {
		log.info("Temp cache || visists data per web page || stored");
		return super.getVisitsByPage(start, end, ua);
	}

	@Override
	@Cacheable(value = "visitsTemp", key = "#root.methodName.concat(#ua.toString()).concat(#start.toString()).concat(#end.toString())")
	public Map<String, String> getVisitsByOS(Date start, Date end, String ua) {
		log.info("Temp cache || visits data by OS || stored");
		return super.getVisitsByOS(start, end, ua);
	}

	@Override
	@Cacheable(value = "visitsTemp", key = "#root.methodName.concat(#ua.toString()).concat(#start.toString()).concat(#end.toString())")
	public Map<String, String> getVisitsByBrowser(Date start, Date end,
			String ua) {
		log.info("Temp cache || visits data by web browser || stored");
		return super.getVisitsByBrowser(start, end, ua);
	}
}
