package com.example.movienightplanner.api;

import java.util.*;
import com.fasterxml.jackson.annotation.*;

public class Welcome {
    private String[] destinationAddresses;
    private String[] originAddresses;
    private Row[] rows;
    private String status;

    @JsonProperty("destination_addresses")
    public String[] getDestinationAddresses() { return destinationAddresses; }
    @JsonProperty("destination_addresses")
    public void setDestinationAddresses(String[] value) { this.destinationAddresses = value; }

    @JsonProperty("origin_addresses")
    public String[] getOriginAddresses() { return originAddresses; }
    @JsonProperty("origin_addresses")
    public void setOriginAddresses(String[] value) { this.originAddresses = value; }

    @JsonProperty("rows")
    public Row[] getRows() { return rows; }
    @JsonProperty("rows")
    public void setRows(Row[] value) { this.rows = value; }

    @JsonProperty("status")
    public String getStatus() { return status; }
    @JsonProperty("status")
    public void setStatus(String value) { this.status = value; }
}

