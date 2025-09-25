package com.pracking.routing.service;

import com.pracking.routing.dto.RoutingRequest;
import com.pracking.routing.dto.RoutingSuggestionDto;
import com.pracking.routing.entity.ParkingOption;
import com.pracking.routing.exception.BusinessException;
import com.pracking.routing.repository.ParkingOptionRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class RoutingPlannerService {

    private final ParkingOptionRepository repository;

    public RoutingPlannerService(ParkingOptionRepository repository) {
        this.repository = repository;
    }

    public List<RoutingSuggestionDto> listOptions() {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public RoutingSuggestionDto suggest(RoutingRequest request) {
        List<ParkingOption> candidates = repository.findByCity(request.getCurrentCity());
        if (request.getPreferredArea() != null && !request.getPreferredArea().isBlank()) {
            String preferred = request.getPreferredArea().toLowerCase(Locale.ROOT);
            candidates = candidates.stream()
                    .filter(option -> option.getArea().toLowerCase(Locale.ROOT).contains(preferred))
                    .collect(Collectors.toList());
        }
        String size = request.getVehicleSize().toUpperCase(Locale.ROOT);
        RoutingSuggestionDto suggestion = candidates.stream()
                .filter(option -> option.getSupportedSizes().toUpperCase(Locale.ROOT).contains(size))
                .filter(option -> option.getDistanceKm() <= request.getMaximumDistanceKm())
                .sorted(Comparator.comparingDouble(ParkingOption::getDistanceKm)
                        .thenComparing(ParkingOption::getAvailableSlots, Comparator.reverseOrder()))
                .map(this::toDto)
                .findFirst()
                .orElseThrow(() -> new BusinessException("No parking option matches criteria"));
        return suggestion;
    }

    private RoutingSuggestionDto toDto(ParkingOption option) {
        return new RoutingSuggestionDto(option.getId(), option.getLotName(), option.getCity(), option.getArea(),
                option.getDistanceKm(), option.getAvailableSlots(), option.getSupportedSizes());
    }
}
