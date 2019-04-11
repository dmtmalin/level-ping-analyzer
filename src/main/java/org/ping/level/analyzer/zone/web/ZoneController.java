package org.ping.level.analyzer.zone.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
public class ZoneController {
    private final ZoneService service;

    public ZoneController(final ZoneService service) {
        this.service = service;
    }

    @GetMapping(value = {"", "/"})
    public String map() {
        return "map";
    }

    @PostMapping("/zones")
    @ResponseBody
    public Iterable<Zone> getZones(@RequestBody @Valid ZoneRequest zoneRequest) {
        return service.getZones(zoneRequest);
    }
}
