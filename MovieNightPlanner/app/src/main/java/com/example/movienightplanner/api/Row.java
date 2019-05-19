package com.example.movienightplanner.api;

import java.util.*;
import com.fasterxml.jackson.annotation.*;

public class Row {
    private Element[] elements;

    @JsonProperty("elements")
    public Element[] getElements() { return elements; }
    @JsonProperty("elements")
    public void setElements(Element[] value) { this.elements = value; }
}
