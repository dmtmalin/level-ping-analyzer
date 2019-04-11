package org.ping.level.analyzer.geodata;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PingGeoDataTest {
    private final PingGeoDataRepo repo;

    public PingGeoDataTest(final PingGeoDataRepo repo) {
        this.repo = repo;
        this.createTestData();
    }

    @Transactional
    public void createTestData() {
        if (repo.existsById(1L)) return;
        double minLng = 37.334698;
        double maxLng = 37.799969;
        double minLat = 55.607144;
        double maxLat = 55.845938;
        int minSec = 1;
        int maxSec = 3600;
        List<PingGeoData> listData = new LinkedList<>();
        for (int i = 0; i < 10000; i++) {
            PingGeoData data = new PingGeoData();
            data.setLat(ThreadLocalRandom.current().nextDouble(minLat, maxLat));
            data.setLng(ThreadLocalRandom.current().nextDouble(minLng, maxLng));
            data.setReceiveAt(new Date());
            data.setSendAt(addSecondsToDate(new Date(), ThreadLocalRandom.current().nextInt(minSec, maxSec)));
            listData.add(data);
        }
        repo.saveAll(listData);
    }

    private Date addSecondsToDate(Date date, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }
}
