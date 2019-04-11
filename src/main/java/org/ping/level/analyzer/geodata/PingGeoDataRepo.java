package org.ping.level.analyzer.geodata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PingGeoDataRepo extends JpaRepository<PingGeoData, Long> {

    @Query(value = "SELECT d.id, d.lat, d.lng, d.receive_at, d.send_at FROM ping_geo_data d" +
            " WHERE" +
            "   (d.receive_at BETWEEN :startTime AND :endTime) AND" +
            "   (d.lat BETWEEN :minLat AND :maxLat) AND" +
            "   (d.lng BETWEEN :minLng AND :maxLng) AND" +
            "   extract(epoch FROM (d.send_at - d.receive_at)) >= :ping", nativeQuery = true)
    List<PingGeoData> getBy(
            @Param("startTime") Date startTime,
            @Param("endTime") Date endTime,
            @Param("minLat") Double minLat,
            @Param("maxLat") Double maxLat,
            @Param("minLng") Double minLng,
            @Param("maxLng") Double maxLng,
            @Param("ping") Integer pingInSec);
}
