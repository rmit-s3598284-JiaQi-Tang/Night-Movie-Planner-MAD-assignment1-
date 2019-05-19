package com.example.movienightplanner.api;

import java.util.*;
import com.fasterxml.jackson.annotation.*;

public class Distance {
    private String text;
    private long value;

    @JsonProperty("text")
    public String getText() { return text; }
    @JsonProperty("text")
    public void setText(String value) { this.text = value; }

    @JsonProperty("value")
    public long getValue() { return value; }
    @JsonProperty("value")
    public void setValue(long value) { this.value = value; }
}