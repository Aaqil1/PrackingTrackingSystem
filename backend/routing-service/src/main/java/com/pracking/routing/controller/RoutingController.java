package com.pracking.routing.controller;

import com.pracking.routing.dto.RoutingRequest;
import com.pracking.routing.dto.RoutingSuggestionDto;
import com.pracking.routing.service.RoutingPlannerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routing")
public class RoutingController {

    private final RoutingPlannerService service;

    public RoutingController(RoutingPlannerService service) {
        this.service = service;
    }

    @GetMapping("/options")
    public List<RoutingSuggestionDto> listOptions() {
        return service.listOptions();
    }

    @PostMapping("/suggestions")
    @ResponseStatus(HttpStatus.OK)
    public RoutingSuggestionDto suggest(@Valid @RequestBody RoutingRequest request) {
        return service.suggest(request);
    }
}
