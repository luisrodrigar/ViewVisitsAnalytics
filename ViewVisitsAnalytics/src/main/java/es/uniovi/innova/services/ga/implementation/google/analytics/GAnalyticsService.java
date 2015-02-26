package main.java.es.uniovi.innova.services.ga.implementation.google.analytics;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import main.java.es.uniovi.innova.services.ga.IGAService;

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

/**
 * Class for getting information provided for Google Analytics
 * 
 * @author luisrodrigar - DiiSandoval
 *
 */
public class GAnalyticsService implements IGAService {

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
	public int numOfVisitsByDay(int day, int month, int year) {
		String startDate = year + "-" + getStringNumber(month) + "-"
				+ getStringNumber(day);
		String endDate = startDate;
		return calculateVisits(startDate, endDate);
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
	public int numOfVisitsByMonth(int month, int year) {
		String startDate = year + "-" + getStringNumber(month) + "-01";
		String endDate = year
				+ "-"
				+ getStringNumber(month)
				+ "-"
				+ getStringNumber(Calendar.getInstance().getActualMaximum(
						Calendar.DAY_OF_MONTH));
		return calculateVisits(startDate, endDate);
	}

	/**
	 * Number of visits during a specific year
	 * 
	 * @param year
	 *            - the year to obtain the total visits
	 */
	@Override
	public int numOfVisitsByYear(int year) {
		String startDate = year + "-01-01";
		String endDate = year + "-12-31";
		return calculateVisits(startDate, endDate);
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
				GaData gaData = executeDataQuery(analytics, profileId,
						startDate, endDate);
				printGaData(gaData);
				visits = Integer.valueOf(gaData.getRows().get(0).get(0));
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
	private static GaData executeDataQuery(Analytics analytics,
			String profileId, String startDate, String endDate)
			throws IOException {
		return analytics.data().ga().get("ga:" + profileId, // Table Id. ga: +
															// profile id.
				startDate, // Start date.
				endDate, // End date.
				"ga:visits") // Metrics.
				.execute();
	}

	/**
	 * Show the data about the name profile and the visits associated
	 * 
	 * @param data
	 *            about Google Analytics profile
	 */
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

	@Override
	public int numOfVisitsBetweenTwoDates(int day_before, int month_before,
			int year_before, int day_after, int month_after, int year_after) {

		String startDate = year_before + "-" + getStringNumber(month_before)
				+ "-" + getStringNumber(day_before);
		String endDate = year_after + "-" + getStringNumber(month_after) + "-"
				+ getStringNumber(day_after);

		return calculateVisits(startDate, endDate);

	}

}
