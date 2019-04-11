package org.ping.level.analyzer.zone.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ZoneRequest implements Serializable {

    @NotNull
    private List<List<Double>> box;

    @NotNull
    @JsonFormat (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date start;

    @NotNull
    @JsonFormat (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date end;

    @NotNull
    private Integer ping;

    @NotNull
    private Integer accuracy;

    @JsonIgnore
    public List<Double> getLatitudes() {
        List<Double> lat = new ArrayList<>();
        box.forEach((List<Double> lngLat) ->
            lat.add(lngLat.get(1))
        );
        return lat;
    }

    @JsonIgnore
    public List<Double> getLongitudes() {
        List<Double> lng = new ArrayList<>();
        box.forEach((List<Double> lngLat) ->
            lng.add(lngLat.get(0))
        );
        return lng;
    }

    public List<List<Double>> getBox() {
        return box;
    }

    public void setBox(List<List<Double>> box) {
        this.box = box;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Integer getPing() {
        return ping;
    }

    public void setPing(Integer ping) {
        this.ping = ping;
    }

    public Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }
}
