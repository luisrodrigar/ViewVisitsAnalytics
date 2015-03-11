package es.uniovi.innova.ViewVisitsAnalytics.services.ga.implementation;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



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

import es.uniovi.innova.ViewVisitsAnalytics.services.ga.IGAService;

/**
 * Class for getting information provided for Google Analytics
 * 
 * @author luisrodrigar - DiiSandoval
 *
 */
public class GAnalyticsService implements IGAService {
	
	final static Logger log = LoggerFactory.getLogger(GAnalyticsService.class);

	private static HttpTransport TRANSPORT;
	private static final JacksonFactory JSON_FACTORY = JacksonFactory
			.getDefaultInstance();

	private static final java.io.File DATA_STORE_DIR = new java.io.File(
			System.getProperty("user.home"), ".store/analytics_sample");

	private static FileDataStoreFactory dataStoreFactory;

	private static GoogleClientSecrets clientSecrets;

	private static final Collection<String> SCOPE = Collections
			.singleton(AnalyticsScopes.ANALYTICS_READONLY);
	private static final String APPLICATION_NAME = "Visits";

	// UA - the identificador was created by Google Analytics
	private String UA;

	public GAnalyticsService() {
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			Reader reader = new FileReader(classLoader.getResource(
					"client_secrets.json").getFile());
			clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, reader);
		} catch (IOException e) {
			throw new Error("No client_secres.json found\n", e);
		}
	}

	/**
	 * Return the visits from a website provided by Google Analytics. If the
	 * start date is after end date, the method swap the dates.
	 * 
	 * @param start
	 *            - this date must be before end date, unless the dates swap
	 * @param end
	 *            - this date must be after start date, unless the dates swap
	 * @return the visits in this interval
	 */
	@Override
	public int getVisitsByInterval(Date start, Date end, String ua) {
		log.info("Stuffs to do: get the visits from the input interval");
		setUA(ua);
		String startDate, endDate;
		if (start.compareTo(end) <= 0) {
			startDate = getStringDate(start);
			endDate = getStringDate(end);
		} else {
			endDate = getStringDate(start);
			startDate = getStringDate(end);
		}
		return calculateVisits(startDate, endDate);
	}

	@Override
	public Map<String, String> getVisitsByPage(Date start, Date end, String ua) {
		log.info("Stuffs to do: get the visits per web page from a website");
		setUA(ua);
		String startDate, endDate;
		if (start.compareTo(end) <= 0) {
			startDate = getStringDate(start);
			endDate = getStringDate(end);
		} else {
			endDate = getStringDate(start);
			startDate = getStringDate(end);
		}
		return calculateVisitsByPage(startDate, endDate);
	}
	
	@Override
	public Map<String, String> getVisitsByOS(Date start, Date end, String ua) {
		log.info("Stuffs to do: get the visits by operative system");
		setUA(ua);
		String startDate, endDate;
		if (start.compareTo(end) <= 0) {
			startDate = getStringDate(start);
			endDate = getStringDate(end);
		} else {
			endDate = getStringDate(start);
			startDate = getStringDate(end);
		}
		return calculateOS(startDate, endDate);
	}
	
	@Override
	public Map<String, String> getVisitsByBrowser(Date start, Date end, String ua) {
		log.info("Stuffs to do: get the visits by web browser");
		setUA(ua);
		String startDate, endDate;
		if(start==null && end==null){
			startDate = "yesterday";
			endDate = "yesterday";
		}
		if (start.compareTo(end) <= 0) {
			startDate = getStringDate(start);
			endDate = getStringDate(end);
		} else {
			endDate = getStringDate(start);
			startDate = getStringDate(end);
		}
		return calculateBrowser(startDate, endDate);
	}

	@SuppressWarnings("deprecation")
	private String getStringDate(Date date) {
		return date.getYear() + 1900 + "-"
				+ getStringNumber(date.getMonth() + 1) + "-"
				+ getStringNumber(date.getDate());
	}

	/**
	 * Obtain the visits of a website between start and end date
	 * 
	 * @param startDate
	 * @param endDate
	 * @return visits
	 */
	private int calculateVisits(String startDate, String endDate) {
		int visits = 0;
		try {
			TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
			Analytics analytics = initializeAnalytics();
			String profileId = getProfileIdByUA(analytics, UA);
			if (profileId == null) {
				System.err.println("No profiles found.");
			} else {
				GaData gaData = executeDataQueryGetVisits(analytics, profileId,
						startDate, endDate);
				try {
					visits = Integer.valueOf(gaData.getRows().get(0).get(0));
				} catch (NullPointerException ne) {
					visits = 0;
				}
			}
		} catch (GoogleJsonResponseException e) {
			System.err.println("There was a service error: "
					+ e.getDetails().getCode() + " : "
					+ e.getDetails().getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return visits;
	}

	private Map<String, String> calculateVisitsByPage(String startDate,
			String endDate) {
		Map<String, String> mapPageVisits = new TreeMap<String, String>();
		try {
			TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
			Analytics analytics = initializeAnalytics();
			String profileId = getProfileIdByUA(analytics, UA);
			if (profileId == null) {
				System.err.println("No profiles found.");
			} else {
				GaData gaData = executeDataQueryGetPagesVisits(analytics,
						profileId, startDate, endDate);
				try {
					for (List<String> row : gaData.getRows()) {
						List<String> data = new ArrayList<String>();
						for (String colum : row) {
							data.add(colum);
						}
						mapPageVisits.put(data.get(0), data.get(1));
					}

				} catch (NullPointerException ne) {
					System.out.println("No visits to pages");
					;
				}
			}
		} catch (GoogleJsonResponseException e) {
			System.err.println("There was a service error: "
					+ e.getDetails().getCode() + " : "
					+ e.getDetails().getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return mapPageVisits;
	}

	private Map<String, String> calculateOS(String startDate, String endDate) {
		Map<String, String> mapOS = new TreeMap<String, String>();
		try {
			TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
			Analytics analytics = initializeAnalytics();
			String profileId = getProfileIdByUA(analytics, UA);
			if (profileId == null) {
				System.err.println("No profiles found.");
			} else {
				GaData gaData = executeDataQueryGetOS(analytics, profileId,
						startDate, endDate);
				try {
					for (List<String> row : gaData.getRows()) {
						List<String> data = new ArrayList<String>();
						for (String colum : row) {
							data.add(colum);
						}
						mapOS.put(data.get(0), data.get(1));
					}

				} catch (NullPointerException ne) {
					System.out.println("No visits to pages");
					;
				}
			}
		} catch (GoogleJsonResponseException e) {
			System.err.println("There was a service error: "
					+ e.getDetails().getCode() + " : "
					+ e.getDetails().getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return mapOS;
	}
	
	private Map<String, String> calculateBrowser(String startDate,
			String endDate) {
		Map<String, String> mapBrowser = new TreeMap<String, String>();
		try {
			TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
			Analytics analytics = initializeAnalytics();
			String profileId = getProfileIdByUA(analytics, UA);
			if (profileId == null) {
				System.err.println("No profiles found.");
			} else {
				GaData gaData = executeDataQueryGetBrowser(analytics, profileId,
						startDate, endDate);
				try {
					for (List<String> row : gaData.getRows()) {
						List<String> data = new ArrayList<String>();
						for (String colum : row) {
							data.add(colum);
						}
						mapBrowser.put(data.get(0), data.get(1));
					}

				} catch (NullPointerException ne) {
					System.out.println("No visits to pages");
					;
				}
			}
		} catch (GoogleJsonResponseException e) {
			System.err.println("There was a service error: "
					+ e.getDetails().getCode() + " : "
					+ e.getDetails().getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return mapBrowser;
	}

	/**
	 * If number less than 10, it will return: 0'number' If number more than 9,
	 * the response is the number
	 * 
	 * @param number
	 *            for checking
	 * @return number checked
	 */
	private String getStringNumber(int number) {
		return (String) (number < 10 ? "0" + number : "" + number);
	}

	/**
	 * Task about authorization on Google Analytics
	 * 
	 * @return Credentials
	 * @throws IOException
	 */
	private static Credential authorize() throws IOException {
		// set up authorization code flow
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				TRANSPORT, JSON_FACTORY, clientSecrets, SCOPE)
				.setDataStoreFactory(dataStoreFactory).build();
		// authorize
		return new AuthorizationCodeInstalledApp(flow,
				new LocalServerReceiver()).authorize("user");
	}

	/**
	 * Authorizacition and connect to Google Analytics API
	 * 
	 * @return Analytics object
	 * @throws Exception
	 */
	private static Analytics initializeAnalytics() throws Exception {
		// Authorization.
		Credential credential = authorize();

		// Set up and return Google Analytics API client.
		return new Analytics.Builder(TRANSPORT, JSON_FACTORY, credential)
				.setApplicationName(APPLICATION_NAME).build();
	}

	/**
	 * Obtain the profile of the google analytics account
	 * 
	 * @param Analytics
	 * @param Google
	 *            Analytics id e.i. UA-XXXXX-Y
	 * @return Profile
	 * @throws IOException
	 */
	private static String getProfileIdByUA(Analytics analytics, String ua)
			throws IOException {
		String profileId = null;

		// Query accounts collection.
		Accounts accounts = analytics.management().accounts().list().execute();

		if (accounts.getItems().isEmpty()) {
			System.err.println("No accounts found");
		} else {
			String firstAccountId = accounts.getItems().get(0).getId();

			// Query webproperties collection.
			Webproperties webproperties = analytics.management()
					.webproperties().list(firstAccountId).execute();

			if (webproperties.getItems().isEmpty()) {
				System.err.println("No Webproperties found");
			} else {
				String webpropertyId = "";
				for (Webproperty each : webproperties.getItems())
					if (each.getId().equals(ua))
						webpropertyId = each.getId();

				// Query profiles collection.
				Profiles profiles = analytics.management().profiles()
						.list(firstAccountId, webpropertyId).execute();

				if (profiles.getItems().isEmpty()) {
					System.err.println("No profiles found");
				} else {
					profileId = profiles.getItems().get(0).getId();
				}
			}
		}
		return profileId;
	}

	/**
	 * Query to execute for getting the info about the website
	 * 
	 * @param analytics
	 *            - authorization and credentials
	 * @param profileId
	 *            - profile about the website to obtain the visits
	 * @param startDate
	 *            - initial date for starting to count the visits
	 * @param endDate
	 *            - end date for finishing to count the visits
	 * @return the info about the visits of the profile
	 * @throws IOException
	 */
	private static GaData executeDataQueryGetVisits(Analytics analytics,
			String profileId, String startDate, String endDate)
			throws IOException {
		return analytics.data().ga().get("ga:" + profileId, // Table Id. ga: +
															// profile id.
				startDate, // Start date.
				endDate, // End date.
				"ga:visits") // Metrics.
				.execute();
	}

	private static GaData executeDataQueryGetPagesVisits(Analytics analytics,
			String profileId, String startDate, String endDate)
			throws IOException {
		return analytics.data().ga().get("ga:" + profileId, // Table Id. ga: +
															// profile id.
				startDate, // Start date.
				endDate, // End date.
				"ga:visits") // Metrics.
				.setDimensions("ga:pagePath").setSort("ga:visits").execute();
	}
	
	private GaData executeDataQueryGetOS(Analytics analytics, String profileId,
			String startDate, String endDate) throws IOException {
		return analytics.data().ga().get("ga:" + profileId,
				startDate, 
				endDate, 
				"ga:visits")
				.setDimensions("ga:operatingSystem").setSort("ga:visits").execute();
	}

	private GaData executeDataQueryGetBrowser(Analytics analytics,
			String profileId, String startDate, String endDate) throws IOException {
		return analytics.data().ga().get("ga:" + profileId,
				startDate, 
				endDate, 
				"ga:visits")
				.setDimensions("ga:browser").setSort("ga:visits").execute();
	}
	
	/**
	 * Show the data about the name profile and the visits associated
	 * Method for printing in console the data
	 * @param data
	 *            about Google Analytics profile
	 */
	@SuppressWarnings("unused")
	private static void printGaData(GaData results) {
		// Print info about the Google Analytics profile
		System.out.println("printing results for profile: "
				+ results.getProfileInfo().getProfileName());
		System.out.println("printing results for web profile propery: "
				+ results.getProfileInfo().getWebPropertyId());
		System.out.println("printing results for account id: "
				+ results.getProfileInfo().getAccountId());

		if (results.getRows() == null || results.getRows().isEmpty()) {
			System.out.println("No results Found.");
		} else {

			// Print column headers, in this case, the visits
			for (ColumnHeaders header : results.getColumnHeaders()) {
				System.out.printf("%30s", header.getName());
			}
			System.out.println();

			// Print the visits of the website
			for (List<String> row : results.getRows()) {
				for (String column : row) {
					System.out.printf("%30s", column);
				}
				System.out.println();
			}

			System.out.println();
		}
	}

	/**
	 * The Google Analytics ID for querying the visits of the website which has
	 * this ID
	 * 
	 * @return Google
	 */
	public String getUA() {
		return UA;
	}

	/**
	 * Allow to change the Google Analytics ID for querying visits of other web
	 * site.
	 * 
	 * @param uA
	 */
	public void setUA(String uA) {
		UA = uA;
	}

}
