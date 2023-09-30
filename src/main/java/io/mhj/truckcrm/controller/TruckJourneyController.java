package io.mhj.truckcrm.controller;

import io.mhj.truckcrm.domain.JourneyStatus;
import io.mhj.truckcrm.domain.TruckJourney;
import io.mhj.truckcrm.service.TruckJourneyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Lazy
@RequestMapping("/api/v1/journey")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class TruckJourneyController {

    private final TruckJourneyService truckJourneyService;


    @PostMapping("/random_data")
    public ResponseEntity createRandomData() throws Exception {
        truckJourneyService.generateRandomData();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TruckJourney>> getAllTruckJourney() throws Exception {
        List<TruckJourney> result = truckJourneyService.getAllActiveTruckJourney();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<JourneyStatus, Long>> getJourneyStatistics() throws Exception {
        Map<JourneyStatus, Long> allStatistique = truckJourneyService.getAllStatistique();
        return new ResponseEntity<>(allStatistique, HttpStatus.OK);
    }

    @PostMapping("/simulate_workflow")
    public ResponseEntity simulateWorkflow() throws Exception {
        truckJourneyService.simulateWorkflow();
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
