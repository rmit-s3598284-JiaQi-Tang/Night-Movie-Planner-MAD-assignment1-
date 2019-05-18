package com.example.movienightplanner.models;

public class EventImpl extends AbstractEvent {

    public EventImpl(String id, String tittle, String startDate, String endDate, String venue, String location) {
        super(id, tittle, startDate, endDate, venue, location);
    }

    public EventImpl(String id, String tittle, String startDate, String endDate, String venue, String location, Integer movieId) {
        super(id, tittle, startDate, endDate, venue, location);
        this.setMovieId(movieId);
    }
}
