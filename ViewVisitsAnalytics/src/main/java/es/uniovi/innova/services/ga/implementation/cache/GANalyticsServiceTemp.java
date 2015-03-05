package main.java.es.uniovi.innova.services.ga.implementation.cache;

import java.util.Date;
import java.util.Map;

import main.java.es.uniovi.innova.services.ga.implementation.GAnalyticsService;

import org.springframework.cache.annotation.Cacheable;

public class GANalyticsServiceTemp extends GAnalyticsService {
	
	@Override
	@Cacheable(value = "visitsTemp", key = "#root.methodName.concat(#ua.toString()).concat(#start.toString()).concat(#end.toString())")
	public int getVisitsByInterval(Date start, Date end, String ua) {
		System.out.println("Cache Temporal");
		return super.getVisitsByInterval(start, end, ua);
	}

	@Override
	@Cacheable(value = "visitsTemp", key = "#root.methodName.concat(#ua.toString()).concat(#start.toString()).concat(#end.toString())")
	public Map<String, String> getVisitsByPage(Date start, Date end, String ua) {
		System.out.println("Cache Temporal");
		return super.getVisitsByPage(start, end, ua);
	}

	@Override
	@Cacheable(value = "visitsTemp", key = "#root.methodName.concat(#ua.toString()).concat(#start.toString()).concat(#end.toString())")
	public Map<String, String> getVisitsByOS(Date start, Date end, String ua) {
		System.out.println("Cache Temporal");
		return super.getVisitsByOS(start, end, ua);
	}

	@Override
	@Cacheable(value = "visitsTemp", key = "#root.methodName.concat(#ua.toString()).concat(#start.toString()).concat(#end.toString())")
	public Map<String, String> getVisitsByBrowser(Date start, Date end,
			String ua) {
		System.out.println("Cache Temporal");
		return super.getVisitsByBrowser(start, end, ua);
	}
}
