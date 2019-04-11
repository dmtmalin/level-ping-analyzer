package org.ping.level.analyzer.zone.web;

import org.ping.level.analyzer.geodata.PingGeoData;
import org.ping.level.analyzer.util.QuadTreeUtil;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PingGeoDataToZones implements Converter<List<PingGeoData>, List<Zone>> {
    private final int accuracy;
    private double max = 0, min = Double.MAX_VALUE;

    public PingGeoDataToZones(int accuracy) {
        this.accuracy = accuracy;
    }

    @Override
    public List<Zone> convert(List<PingGeoData> pingGeoData) {
        HashMap<String, Zone> zones = new HashMap<>();
        pingGeoData.forEach(data -> {
            double lat = data.getLat();
            double lng = data.getLng();
            double ping = data.getPing();
            Date time = data.getReceiveAt();

            String quad = QuadTreeUtil.latLngToQuad(lat, lng);
            String quadZone = quad.substring(0, accuracy);

            Zone zone;

            if(!zones.containsKey(quadZone)) {
                double[][] box = QuadTreeUtil.quadToBox(quadZone);
                zone = new Zone(box, 1, ping);
                zones.put(quadZone, zone);
            }
            else {
                zone = zones.get(quadZone);
                zone.incrementNumber();
                zone.addPing(ping);
            }

            Zone.TimeInterval interval = zone.getInterval();
            if (time.before(interval.getStart())) interval.setStart(time);
            if (time.after(interval.getEnd())) interval.setEnd(time);
        });
        zones.forEach((k, v) -> {
            if (v.getPing() > max) max = v.getPing();
            if (v.getPing() < min) min = v.getPing();
        });
        zones.forEach((k, v) -> v.setColor(min, max));
        return new ArrayList<>(zones.values());
    }
}
