package com.denverted.eventPackage;

import java.net.URL;
import java.sql.Date;

import com.denverted.bandPackage.Band;

public class Event implements Comparable<Event> {


	//=============================================================
	// CLASS FIELDS
	//=============================================================
	private Date eventDateAndTime;
	private String eventTitle;
	private String ageLimit;
	private Band mainBand;
	private Band[] openers;
	private String eventVenue;
	private String eventLink;
	private String eventCost;
	//=============================================================

	
	public Event() {
		//TODO: Determine how constructor should create event object from response handler
	}
	@Override
	public String toString() {
		//TODO: determine how toString should be
		return "";
	}

	@Override
	public int compareTo(Event otherEvent) {
		/* 0 if equal  
		 * <0 if this earlier
		 * >0 if this later
		 */
		return getEventDateAndTime().compareTo(otherEvent.getEventDateAndTime());
	}
		

	//=============================================================
	//GETTERS AND SETTERS ARE BELOW
	//=============================================================
	public Date getEventDateAndTime() {
		return eventDateAndTime;
	}

	public void setEventDateAndTime(Date eventDateAndTime) {
		this.eventDateAndTime = eventDateAndTime;
	}
	
	public String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public String getAgeLimit() {
		return ageLimit;
	}

	public void setAgeLimit(String ageLimit) {
		this.ageLimit = ageLimit;
	}

	public Band getMainBand() {
		return mainBand;
	}

	public void setMainBand(Band mainBand) {
		this.mainBand = mainBand;
	}

	public Band[] getOpeners() {
		return openers;
	}

	public void setOpeners(Band[] openers) {
		this.openers = openers;
	}

	public String getEventVenue() {
		return eventVenue;
	}

	public void setEventVenue(String eventVenue) {
		this.eventVenue = eventVenue;
	}

	public String getEventLink() {
		return eventLink;
	}

	public void setEventLink(String eventLink) {
		this.eventLink = eventLink;
	}

	public String getEventCost() {
		return eventCost;
	}

	public void setEventCost(String eventCost) {
		this.eventCost = eventCost;
	}

}
