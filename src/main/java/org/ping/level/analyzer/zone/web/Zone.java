package org.ping.level.analyzer.zone.web;

import org.ping.level.analyzer.util.ColorUtil;

import java.io.Serializable;
import java.util.Date;

public class Zone implements Serializable {
    private double [][] box;
    private int numbers;
    private double ping;
    private String color;
    private TimeInterval interval;

    public static class TimeInterval {
        private Date start;
        private Date end;

        public TimeInterval() {
            this.start = new Date(Long.MAX_VALUE);
            this.end = new Date(Long.MIN_VALUE);
        }

        public Date getStart() {
            return start;
        }

        public Date getEnd() {
            return end;
        }

        public void setStart(Date start) {
            this.start = start;
        }

        public void setEnd(Date end) {
            this.end = end;
        }
    }

    public Zone(double[][] box, int numbers, double ping) {
        this.box = box;
        this.numbers = numbers;
        this.ping = ping;
        this.interval = new TimeInterval();
    }

    public void incrementNumber() {
        numbers += 1;
    }

    public void addPing(double ping) {
        this.ping = (this.ping + ping) / 2;
    }

    public void setColor(double min, double max) {
        this.color = ColorUtil.toPseudoColor(this.ping, min, max);;
    }

    public double[][] getBox() {
        return box;
    }

    public void setBox(double[][] box) {
        this.box = box;
    }

    public int getNumbers() {
        return numbers;
    }

    public void setNumbers(int numbers) {
        this.numbers = numbers;
    }

    public double getPing() {
        return ping;
    }

    public void setPing(double ping) {
        this.ping = ping;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public TimeInterval getInterval() {
        return interval;
    }

    public void setInterval(TimeInterval interval) {
        this.interval = interval;
    }
}
