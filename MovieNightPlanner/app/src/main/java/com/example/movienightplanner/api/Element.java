package com.example.movienightplanner.api;

import java.util.*;
import com.fasterxml.jackson.annotation.*;

public class Element {
    private Distance distance;
    private Distance duration;
    private String status;

    @JsonProperty("distance")
    public Distance getDistance() { return distance; }
    @JsonProperty("distance")
    public void setDistance(Distance value) { this.distance = value; }

    @JsonProperty("duration")
    public Distance getDuration() { return duration; }
    @JsonProperty("duration")
    public void setDuration(Distance value) { this.duration = value; }

    @JsonProperty("status")
    public String getStatus() { return status; }
    @JsonProperty("status")
    public void setStatus(String value) { this.status = value; }
}

