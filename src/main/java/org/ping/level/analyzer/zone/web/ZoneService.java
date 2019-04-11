package org.ping.level.analyzer.zone.web;

import org.ping.level.analyzer.geodata.PingGeoData;
import org.ping.level.analyzer.geodata.PingGeoDataRepo;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ZoneService {
    private final PingGeoDataRepo dataRepo;

    public ZoneService(final PingGeoDataRepo dataRepo) {
        this.dataRepo = dataRepo;
    }

    public Iterable<Zone> getZones(ZoneRequest zoneRequest) {
        List<Double> lats = zoneRequest.getLatitudes();
        List<Double> lngs = zoneRequest.getLongitudes();
        List<PingGeoData> data = dataRepo.getBy(
                zoneRequest.getStart(), zoneRequest.getEnd(),
                Collections.min(lats),
                Collections.max(lats),
                Collections.min(lngs),
                Collections.max(lngs), zoneRequest.getPing());
        return new PingGeoDataToZones(zoneRequest.getAccuracy()).convert(data);
    }
}
