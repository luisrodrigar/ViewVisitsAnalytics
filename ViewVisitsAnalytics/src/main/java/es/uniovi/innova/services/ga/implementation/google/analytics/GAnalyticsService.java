package main.java.es.uniovi.innova.services.ga.implementation.google.analytics;


import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
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

public class GAnalyticsService implements IGAService {

	private static HttpTransport TRANSPORT;
	private static final JacksonFactory JSON_FACTORY = JacksonFactory
			.getDefaultInstance();

	private static final java.io.File DATA_STORE_DIR = new java.io.File(
			System.getProperty("user.home"), ".store/analytics_sample");

	private static FileDataStoreFactory dataStoreFactory;

	private static final GoogleClientSecrets clientSecrets;

	static {
		try {
			Reader reader = new FileReader("client_secrets.json");
			clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, reader);
		} catch (IOException e) {
			throw new Error("No client_secres.json found\n", e);
		}
	}

	private static final Collection<String> SCOPE = Collections
			.singleton(AnalyticsScopes.ANALYTICS_READONLY);
	private static final String APPLICATION_NAME = "Visits";

	@Override
	public int numOfVisitsByDay(int day, int month, int year) {
		String startDate = year + "-" + getStringNumber(month) + "-" + getStringNumber(day);
		String endDate = startDate;
		int visits = 0;
		try {
		      TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		      dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
		      Analytics analytics = initializeAnalytics();
		      String profileId = getFirstProfileId(analytics, "UA-57349981-1");
		      if (profileId == null) {
		        System.err.println("No profiles found.");
		      } else {
		        GaData gaData = executeDataQuery(analytics, profileId, startDate, endDate);
		        printGaData(gaData);
		        visits = Integer.valueOf(gaData.getRows().get(0).get(0));
		      }
		    } catch (GoogleJsonResponseException e) {
		      System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
		          + e.getDetails().getMessage());
		    } catch (Throwable t) {
		      t.printStackTrace();
		    }
		return visits;
	}
	
	private String getStringNumber(int number){
		return (String) (number<10? "0"+number: " " + number);
	}

	@Override
	public int numOfVisitsByMonth(int month, int year) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int numOfVisitsByYear(int year) {
		// TODO Auto-generated method stub
		return 0;
	}

	private static Credential authorize() throws IOException {
		// set up authorization code flow
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				TRANSPORT, JSON_FACTORY, clientSecrets, SCOPE)
				.setDataStoreFactory(dataStoreFactory).build();
		// authorize
		return new AuthorizationCodeInstalledApp(flow,
				new LocalServerReceiver()).authorize("user");
	}

	private static Analytics initializeAnalytics() throws Exception {
		// Authorization.
		Credential credential = authorize();

		// Set up and return Google Analytics API client.
		return new Analytics.Builder(TRANSPORT, JSON_FACTORY, credential)
				.setApplicationName(APPLICATION_NAME).build();
	}
	
	private static String getFirstProfileId(Analytics analytics, String ua) throws IOException {
	    String profileId = null;

	    // Query accounts collection.
	    Accounts accounts = analytics.management().accounts().list().execute();

	    if (accounts.getItems().isEmpty()) {
	      System.err.println("No accounts found");
	    } else {
	      String firstAccountId = accounts.getItems().get(0).getId();

	      // Query webproperties collection.
	      Webproperties webproperties =
	          analytics.management().webproperties().list(firstAccountId).execute();

	      if (webproperties.getItems().isEmpty()) {
	        System.err.println("No Webproperties found");
	      } else {
	    	  String webpropertyId = "";
	    	  for(Webproperty each: webproperties.getItems())
	    		  if(each.getId().equals(ua))
	    			 webpropertyId = each.getId(); 

	        // Query profiles collection.
	        Profiles profiles =
	            analytics.management().profiles().list(firstAccountId, webpropertyId).execute();

	        if (profiles.getItems().isEmpty()) {
	          System.err.println("No profiles found");
	        } else {
	          profileId = profiles.getItems().get(0).getId();
	        }
	      }
	    }
	    return profileId;
	  }
	
	private static GaData executeDataQuery(Analytics analytics, String profileId, String startDate, String endDate) throws IOException {
	    return analytics.data().ga().get("ga:" + profileId, // Table Id. ga: + profile id.
	        startDate, // Start date.
	        endDate, // End date.
	        "ga:visits") // Metrics.
	        .execute();
	  }
	
	private static void printGaData(GaData results) {
	    System.out.println(
	        "printing results for profile: " + results.getProfileInfo().getProfileName());
	    System.out.println(
		        "printing results for web profile propery: " + results.getProfileInfo().getWebPropertyId());
	    System.out.println(
		        "printing results for account id: " + results.getProfileInfo().getAccountId());
	    
	    if (results.getRows() == null || results.getRows().isEmpty()) {
	      System.out.println("No results Found.");
	    } else {

	      // Print column headers.
	      for (ColumnHeaders header : results.getColumnHeaders()) {
	        System.out.printf("%30s", header.getName());
	      }
	      System.out.println();

	      // Print actual data.
	      for (List<String> row : results.getRows()) {
	        for (String column : row) {
	          System.out.printf("%30s", column);
	        }
	        System.out.println();
	      }

	      System.out.println();
	    }
	  }

}
