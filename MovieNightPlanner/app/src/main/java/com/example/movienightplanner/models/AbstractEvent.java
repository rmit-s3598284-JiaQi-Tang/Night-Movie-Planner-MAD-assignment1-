package com.example.movienightplanner.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class AbstractEvent implements Event {

    private String id;
    private String tittle;
    private String startDate;
    private String endDate;
    private String venue;
    private String location;
    private Integer movieId;
    private MovieImpl movie;
    private List<String> attendees;
    private Date dateTime;

    public AbstractEvent(String id, String tittle, String startDate, String endDate, String venue, String location) {
        this.id = id;
        this.tittle = tittle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.venue = venue;
        this.location = location;
        this.attendees = new ArrayList<>();
        this.dateTime = new Date();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getTittle() {
        return tittle;
    }

    @Override
    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    @Override
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    @Override
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public MovieImpl getMovie() {
        return movie;
    }

    @Override
    public void setMovie(MovieImpl movie) {
        this.movie = movie;
    }

    @Override
    public List<String> getAttendees() {
        return attendees;
    }

    @Override
    public void setAttendees(List<String> attendees) {
        this.attendees = attendees;
    }

    @Override
    public Date getDateTime() {
        return dateTime;
    }

    @Override
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public Integer getMovieId() {
        return movieId;
    }

    @Override
    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }
}
