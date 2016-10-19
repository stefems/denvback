package com.denverted;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.denverted.eventPackage.Event;
import com.denverted.loggerPackage.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;


public class ReadTicketFlyHTML {
	
	
	//=============================================================
	// CLASS FIELDS
	//=============================================================
	//static list of events
	private static ArrayList<Event> eventMasterList = new ArrayList<Event>();
	private static Logger myLogger = null;
	//TODO Add more venues
	private static String[] URLS = {"marquis-theater",
									"hi-dive",
									"globe-hall",
									"cervantes"};
	private static String[] genres = {"rock", "pop", "rap", "country", "folk",
									  "r&b", "latin", "edm", "experimental", "garage" 
									  };
	private static String GetVenueEventListQuery = "";
	private static String GetLastFMArtist = "/2.0/?method=artist.search&artist=";
	private static String LastFMAPIKey = "&api_key=e8157cda22779739786019569aed2e73&format=json";
	
	//=============================================================
	
	public ReadTicketFlyHTML(Logger loggerArg) {
		myLogger = loggerArg;
		testGenreGet("Karl Blau");
		testGenreGet("Cher");
		testGenreGet("Bad Licks");
		//gatherEventDataViaAPI();
	}
	
	private void gatherEventDataViaAPI() {
		
		//for each URL (each venue)
		for (String currentVenue : URLS) {
			//make the connection object
			HttpURLConnection connection = null;
			String charset = "UTF-8";
			try {
				//make the URL object
				URL currentURL = new URL(currentVenue + "?" + GetVenueEventListQuery);
				//open the connection
				connection = (HttpURLConnection) currentURL.openConnection();
				connection.setRequestProperty("Accept-Charset", charset);
				InputStream response = connection.getInputStream();
				handleVenueEventsResponse(response);
				
			}
			catch (MalformedURLException badURL) {
				myLogger.addErrorMessage("A venue URL is bogus, man.");
			}
			catch (IOException e) {
				myLogger.addErrorMessage("A venue URL connection failed.");
			}
		}
		
		//sort the events according to the date
		Collections.sort(eventMasterList);
		
	}
	
	public void handleVenueEventsResponse(InputStream responseArg) {
		Event newEvent = null;
		//CODE TO ACQUIRE EVENT DATA AND ASSIGN TO EVENT OBJECT
			//will set the band objects too.
		
		//insert the event. who cares where. we'll sort it later
		eventMasterList.add(newEvent);
	}
	
	public ArrayList<Event> getEventMasterList() {
		return eventMasterList;
	}
	
	private void testGenreGet(String bandName) {
		//validate band name
		String validatedBandName = validateBandName(bandName);
		
		//make the connection object
		HttpURLConnection connection = null;
		String charset = "UTF-8";
		try {
			//make the URL object
			URL currentURL = new URL(GetLastFMArtist + bandName + LastFMAPIKey);
			//open the connection
			connection = (HttpURLConnection) currentURL.openConnection();
			connection.setRequestProperty("Accept-Charset", charset);
			InputStream response = connection.getInputStream();
			//check for error code
			//if good code
			//get the name value from the first artistmatch in the json response
			//send another request to get the toptags for the artistname
			//check for error code
			//if good code
			//take tag elements
			//send tag elements into tag understander method
		}
		catch (MalformedURLException badURL) {
			myLogger.addErrorMessage("A venue URL is bogus, man.");
		}
		catch (IOException e) {
			myLogger.addErrorMessage("A venue URL connection failed.");
		}
	}
	
	private String validateBandName(String bandNameArg) {
		//remove spaces from bandName
	    char[] bandNameAsCharArray= bandNameArg.toCharArray();
	    for (char currentChar : bandNameAsCharArray) {
	    	if (currentChar == ' ') {
	    		currentChar = '+';
	    	}
	    }
	    
	    //TODO: does the caps matter?
	    //TODO: we're gonna need more validation than this, holmes
	    return new String(bandNameAsCharArray);
	}
}



	/*private ArrayList<Event> takeHTML(Document docArg) {
		
		ArrayList<Event> eventObjects = new ArrayList<Event>();
		try {
			//get the event elements from the whole HTML page (class = "list-view-item vevent alt")
			Elements eventElements = docArg.getElementsByClass("list-view-item");
			//for testing the event query
			//for each event element found, create an event object
			for (Element event : eventElements) {
				Event newEvent = createEvent(event);
				if (newEvent != null && newEvent.getEventTitle() != null) {
					eventObjects.add(newEvent);
				}
				else {
					myLogger.addErrorMessage("We failed to create the event! Check a previous error.");
				}
			}
		}
		catch (Exception e) {
			myLogger.addErrorMessage("There was an issue in the takeHTML method when attempting to get the list of event elements.");
		}
		return eventObjects;
	}
	
	private Event createEvent(Element eventArg) {
		//Creating the event object to return when the HTML event has been copied
		Event currentEvent = new Event();	
		
		//init the new document for the specific page
		Document pageSpecific = null;
		
		//if the button element is found		
		if (eventArg.getElementsByClass("ticket-link").size() != 0) {
			//set the event link on the object
			currentEvent.setEventLink(eventArg.getElementsByClass("ticket-link").first().getElementsByTag("a").first().attr("href"));
		}
		else {
			myLogger.addErrorMessage("We failed to get the button element with which to get the specific event URL.");
			return null;
		}
		//Let's pause the execution for a moment (2 seconds) to not bombard their website so much
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			myLogger.addErrorMessage("We were waiting before accessing the specific event page and were interrupted.");
		}
		//create the document
		try {
			pageSpecific = Jsoup.connect(currentEvent.getEventLink()).userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36")
																	 .timeout(10000).get();
			if (pageSpecific == null) {
				throw new Exception();
			}
		}
		catch (IOException e1) {
			myLogger.addErrorMessage("We failed to load the specific event page for an unknown reason.");
			return null;
		}
		catch (Exception e2) {
			myLogger.addErrorMessage("We failed to load the specific event page because the URL doesn't exist.");
			return null;
		}
		
		//getting the title
		if (pageSpecific.getElementsByClass("event-name").size() != 0) {
			currentEvent.setEventTitle(pageSpecific.getElementsByClass("event-name").first().html());
		}
		
		//getting the date & time
		if (pageSpecific.getElementById("when") != null) {
			currentEvent.setEventTime(pageSpecific.getElementById("when").getElementsByTag("meta").first().attr("content") + " " + pageSpecific.getElementsByClass("time").first().html());
		}
		
		//getting the venue
		if (pageSpecific.getElementsByClass("venue").size() != 0) {
			currentEvent.setEventVenue(pageSpecific.getElementsByClass("venue").first().attr("title"));
		}
		
		//getting the openers
		if (pageSpecific.getElementsByClass("supports").size() != 0) {
			String openers = pageSpecific.getElementsByClass("supports").first().html();			
			currentEvent.setEventOpeners(openers);
		}
	
		//getting the age restriction
		if (pageSpecific.getElementsByClass("age-restriction").size() != 0) {
			currentEvent.setAgeLimit(pageSpecific.getElementsByClass("age-restriction").first().html());
		}
		
		//getting the cost
		if (pageSpecific.getElementById("price-range") != null) {
			currentEvent.setEventCost(pageSpecific.getElementById("price-range").html());
		}
		//TODO: The code to call the api and get the tags from last fm should happen here
		setGenreTag(currentEvent);
		return currentEvent;
	}
	
	private void setGenreTag(Event eventArg) {
		
		//get the artist name from the eventArg
		//String artistName
		
		//TODO: What will we do about the openers? 
	}
	
	private void gatherEventDataViaScraping() {
		//for each URL
		for (String currentVenue : URLS) {
			//try {
				//String headerAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36";
				//String headerKey = "User-Agent";
				Connection.Response response = null;
	            try {
	                response = Jsoup.connect(currentVenue)
	                        .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36")
	                        .timeout(10000)
	                        .ignoreHttpErrors(true) 
	                        .execute();
		            myLogger.addGoodMessage("Jsoup status message for connection to " + currentVenue + " is: " + response.statusMessage());
					//takeHTML returns an arraylist of events, so we're going to call it on each event and add that list to the allEvents list
					eventMasterList.addAll(takeHTML(Jsoup.connect(currentVenue).userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36")
	                        													.timeout(10000).get()));			
	            } catch (IOException e) {
	            	myLogger.addErrorMessage("Failed to connect and read from venue url " + currentVenue + " because of " + e);
	            }
		}
	}
	 */





