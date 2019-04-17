package com.example.movienightplanner.models;

import java.util.Date;
import java.util.List;

public abstract class AbstructEvent implements Event {


    @Override
    public String getId() {
        return null;
    }

    @Override
    public void setId(String id) {

    }

    @Override
    public String getTittle() {
        return null;
    }

    @Override
    public void setTittle(String tittle) {

    }

    @Override
    public String getStartDate() {
        return null;
    }

    @Override
    public void setStartDate(String startDate) {

    }

    @Override
    public String getEndDate() {
        return null;
    }

    @Override
    public void setEndDate(String endDate) {

    }

    @Override
    public String getVenue() {
        return null;
    }

    @Override
    public void setVenue(String venue) {

    }

    @Override
    public String getLocation() {
        return null;
    }

    @Override
    public void setLocation(String location) {

    }

    @Override
    public MovieImpl getMovie() {
        return null;
    }

    @Override
    public void setMovie(MovieImpl movie) {

    }

    @Override
    public List<String> getAttendees() {
        return null;
    }

    @Override
    public void setAttendees(List<String> attendees) {

    }

    @Override
    public Date getDateTime() {
        return null;
    }

    @Override
    public void setDateTime(Date dateTime) {

    }

}
