package com.example.movienightplanner.models;

import java.util.Date;
import java.util.List;

public interface Event {
	
	String getId();
	void setId(String id);
	String getTittle();
	void setTittle(String tittle);
	String getStartDate();
	void setStartDate(String startDate);
	String getEndDate();
	void setEndDate(String endDate);
	String getVenue();
	void setVenue(String venue);
	String getLocation();
	void setLocation(String location);
	List<String> getAttendees();
	void setAttendees(List<String> attendees);
	
}
