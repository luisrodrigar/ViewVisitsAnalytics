package main.java.es.uniovi.innova.services.ga.implementation;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import main.java.es.uniovi.innova.services.ga.IGAService;
import main.java.es.uniovi.innova.services.ga.implementation.google.analytics.GAnalyticsService;
import main.java.es.uniovi.innova.services.ga.implementation.util.DateFormat;

import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.AnalyticsScopes;
import com.google.api.services.analytics.model.Accounts;
import com.google.api.services.analytics.model.GaData;
import com.google.api.services.analytics.model.GaData.ColumnHeaders;
import com.google.api.services.analytics.model.Profiles;
import com.google.api.services.analytics.model.Webproperties;
import com.google.api.services.analytics.model.Webproperty;

import org.apache.commons.logging.*;
import org.springframework.cache.annotation.Cacheable;

/**
 * Class for getting information provided for Google Analytics
 * 
 * @author luisrodrigar - DiiSandoval
 *
 */
public class GAnalyticsServiceNewData extends GAnalyticsService {

	/**
	 * Number of visits in one specific day
	 * 
	 * @param day
	 *            - the day of a month
	 * @param month
	 *            - the month of a year
	 * @param year
	 *            - the concrete year
	 */
	@Override
	@Cacheable(value = "actualVisits", key = "#root.methodName.concat(#day.toString()).concat(#month.toString()).concat(#year.toString())")
	public int numOfVisitsByDay(int day, int month, int year) {
		System.out.println("> Caché variable - Numero de visitas en un día");
		return super.numOfVisitsByDay(day, month, year);
	}

	/**
	 * Number of visits during a specific month
	 * 
	 * @param month
	 *            - the mont of a year
	 * @param year
	 *            - the concrete year
	 */
	@Override
	@Cacheable(value = "actualVisits", key = "#root.methodName.concat(#month.toString()).concat(#year.toString())")
	public int numOfVisitsByMonth(int month, int year) {
		System.out.println("> Caché variable - Numero de visitas en un mes");
		return super.numOfVisitsByMonth(month, year);
	}

	/**
	 * Number of visits during a specific year
	 * 
	 * @param year
	 *            - the year to obtain the total visits
	 */
	@Override
	@Cacheable(value = "actualVisits", key = "#root.methodName.concat(#year.toString())")
	public int numOfVisitsByYear(int year) {
		System.out.println("> Caché variable - Numero de visitas en un año");
		return super.numOfVisitsByYear(year);
	}

	@Override
	@Cacheable(value = "actualVisits", key = "#root.methodName.concat(#day_before.toString()).concat(#month_before.toString())"
			+ ".concat(#year_before.toString()).concat(#day_after.toString()).concat(#month_after.toString()).concat(#year_after.toString())")
	public int numOfVisitsBetweenTwoDates(int day_before, int month_before,
			int year_before, int day_after, int month_after, int year_after) {
		System.out
				.println("> Caché variable - Numero de visitas entre dos fechas");
		return super.numOfVisitsBetweenTwoDates(day_before, month_before,
				year_before, day_after, month_after, year_after);

	}

	@Override
	@Cacheable(value = "actualVisits", key = "#root.methodName.concat(#day_before.toString()).concat(#month_before.toString())"
			+ ".concat(#year_before.toString()).concat(#day_after.toString()).concat(#month_after.toString()).concat(#year_after.toString())")
	public Map<String, String> getVisitsByCountry(int day_before,
			int month_before, int year_before, int day_after, int month_after,
			int year_after) {
		System.out.println("> Caché variable - Numero de visitas por país");
		return super.getVisitsByCountry(day_before, month_before, year_before,
				day_after, month_after, year_after);

	}

	@Override
	@Cacheable(value = "actualVisits", key = "#root.methodName.concat(#day_before.toString()).concat(#month_before.toString())"
			+ ".concat(#year_before.toString()).concat(#day_after.toString()).concat(#month_after.toString()).concat(#year_after.toString())")
	public Map<String, String> getVisitsBySSOO(int day_before,
			int month_before, int year_before, int day_after, int month_after,
			int year_after) {
		System.out
				.println("> Caché variable - Numero de visitas por sistema operativo");
		return super.getVisitsBySSOO(day_before, month_before, year_before,
				day_after, month_after, year_after);

	}

	@Override
	@Cacheable(value = "actualVisits", key = "#root.methodName.concat(#day_before.toString()).concat(#month_before.toString())"
			+ ".concat(#year_before.toString()).concat(#day_after.toString()).concat(#month_after.toString()).concat(#year_after.toString())")
	public Map<String, String> getPageVisits(int day_before, int month_before,
			int year_before, int day_after, int month_after, int year_after) {
		System.out.println("> Caché variable - Numero de visitas por pagina");
		return super.getPageVisits(day_before, month_before, year_before,
				day_after, month_after, year_after);

	}

}
