package io.mhj.truckcrm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.mhj.truckcrm.domain.*;
import io.mhj.truckcrm.kafka.TruckProducer;
import io.mhj.truckcrm.repository.DriverRepository;
import io.mhj.truckcrm.repository.RouteRepository;
import io.mhj.truckcrm.repository.TruckJourneyRepository;
import io.mhj.truckcrm.repository.TruckRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
@Builder
@Slf4j
public class TruckJourneyService {

    private final TruckJourneyRepository truckJourneyRepository;

    private final ObjectMapper objectMapper;

    public static final String DRIVER = "Driver ";

    public static final String CITY = "City ";

    public static final String TRUCK = "Truck ";

    private final String ICON_URL = "https://bootdey.com/img/Content/avatar/";

    private final DriverRepository driverRepository;

    private final RouteRepository routeRepository;

    private final TruckRepository truckRepository;

    private final List<String> truckImage = List.of("https://images.unsplash.com/photo-1501700493788-fa1a4fc9fe62?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=3789&q=80", "https://images.unsplash.com/photo-1601584115197-04ecc0da31d7?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8dHJ1Y2t8ZW58MHx8MHx8fDA%3D&auto=format&fit=crop&w=800&q=60", "https://images.unsplash.com/photo-1559331922-516bac7dbf86?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Nzl8fHRydWNrfGVufDB8fDB8fHww&auto=format&fit=crop&w=800&q=60", "https://images.unsplash.com/photo-1555907188-f9fd038c95d4?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8ODR8fHRydWNrfGVufDB8fDB8fHww&auto=format&fit=crop&w=800&q=60", "https://images.unsplash.com/photo-1602710383198-67873f6ab200?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTE3fHx0cnVja3xlbnwwfHwwfHx8MA%3D%3D&auto=format&fit=crop&w=800&q=60", "https://images.unsplash.com/photo-1602710383198-67873f6ab200?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTE3fHx0cnVja3xlbnwwfHwwfHx8MA%3D%3D&auto=format&fit=crop&w=800&q=60", "https://images.unsplash.com/photo-1602710383198-67873f6ab200?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTE3fHx0cnVja3xlbnwwfHwwfHx8MA%3D%3D&auto=format&fit=crop&w=800&q=60", "https://images.unsplash.com/photo-1602710383198-67873f6ab200?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTE3fHx0cnVja3xlbnwwfHwwfHx8MA%3D%3D&auto=format&fit=crop&w=800&q=60", "https://images.unsplash.com/photo-1602710383198-67873f6ab200?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTE3fHx0cnVja3xlbnwwfHwwfHx8MA%3D%3D&auto=format&fit=crop&w=800&q=60", "https://images.unsplash.com/photo-1602710383198-67873f6ab200?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTE3fHx0cnVja3xlbnwwfHwwfHx8MA%3D%3D&auto=format&fit=crop&w=800&q=60");

    private final TruckProducer truckProducer;

    public void processEvent(ConsumerRecord<String, String> consumerRecord) throws JsonProcessingException {
        TruckJourney truckJourney = objectMapper.readValue(consumerRecord.value(), TruckJourney.class);
        log.info("truckJourney : {} ", truckJourney);
        save(truckJourney);
    }

    private void updateWorkflow(TruckJourney truckJourney) {

    }

    private void save(TruckJourney truckJourney) {
        truckJourneyRepository.save(truckJourney);
    }


    public void generateRandomData() throws Exception {
        //Create 10 drivers
        List<Driver> driverList = new ArrayList<>();
        for (var i = 0; i < 8; i++) {
            driverList.add((Driver.builder().lastName(DRIVER + i).firstName(DRIVER + i)
                    .id(UUID.randomUUID()).icon(ICON_URL + "avatar" + (i + 1) + ".png").build()));
        }
        List<Driver> createdDriverList = driverRepository.saveAll(driverList);

        //Create routes
        List<Route> routesList = new ArrayList<>();
        for (var i = 0; i < 8; i++) {
            routesList.add((Route.builder().departureCity(CITY + i).targetCity(CITY + i)
                    .id(UUID.randomUUID()).build()));
        }
        List<Route> createdRouteList = routeRepository.saveAll(routesList);

        //Create Trucks
        List<Truck> truckList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            truckList.add((Truck.builder().image(DRIVER + i).tire("Michellin").cost("" + (i + 1) * 10000)
                    .matricule("Matricule " + i + 1).modelYear("" + 2000 + i).type(TruckType.DIESEL).serie(TRUCK + " " + i).id(UUID.randomUUID()).build()));
        }
        List<Truck> createdTruckList = truckRepository.saveAll(truckList);

        List<TruckJourney> truckJournyList = new ArrayList<>();
        for (var i = 0; i < 8; i++) {
            truckJournyList.add(TruckJourney.builder().id(UUID.randomUUID()).truck(createdTruckList.get(i)).driver(createdDriverList.get(i))
                    .date(LocalDate.now()).arriving_date(null).status(JourneyStatus.LOADING)
                    .actif(true).route(createdRouteList.get(i)).courseDistance((i + 1) * 400).travelDistance(0).changed(0).build());
        }

        //Send the data to kafka
        truckProducer.sendTruckJourney(truckJournyList);
    }


    public List<TruckJourney> getAllActiveTruckJourney() {
        return truckJourneyRepository.findAllByActifIsTrue();
    }

    public Map<JourneyStatus, Long> getAllStatistique() {
        var activeJourneyStatistique = new HashMap<JourneyStatus, Long>();
        List<StatusCount> statusCounts = truckJourneyRepository.countTotalJourneyByStatus();
        statusCounts.stream().forEach(statusCount -> {
            activeJourneyStatistique.put(statusCount.getStatus(), statusCount.getTotal());
        });
        return activeJourneyStatistique;
    }

    public void simulateWorkflow() throws JsonProcessingException {
        List<TruckJourney> allActifJourney = truckJourneyRepository.findAllByActifIsTrue();
        for (var randomIndex = 0; randomIndex < allActifJourney.size(); randomIndex++) {
            updateTruckJourney(allActifJourney.get(randomIndex), randomIndex);
        }

        //Send the data to kafka
        truckProducer.sendTruckJourney(allActifJourney);
    }

    private void updateTruckJourney(TruckJourney truckJourney, int randomIndex) {
        int changedTime = truckJourney.getChanged();
        if (changedTime == 0) { // first change increment distance and status
            truckJourney.setTravelDistance(getRandomDistance(truckJourney.getTravelDistance(), truckJourney.getCourseDistance()));
            truckJourney.setStatus(JourneyStatus.DRIVING);
            truckJourney.setChanged(1);
        }
        if (changedTime == 1) { // first change increment distance and status
            truckJourney.setTravelDistance(getRandomDistance(truckJourney.getTravelDistance(), truckJourney.getCourseDistance()));
            //random based on randomIndex
            if (randomIndex % 2 == 0)
                truckJourney.setStatus(JourneyStatus.PAUSE);
            else
                truckJourney.setStatus(JourneyStatus.AT_CUSTOMS);

            truckJourney.setChanged(2);
        }
        if (changedTime == 2) {
            truckJourney.setTravelDistance(getRandomDistance(truckJourney.getTravelDistance(), truckJourney.getCourseDistance()));
            if (truckJourney.getStatus().equals(JourneyStatus.PAUSE)) {
                truckJourney.setStatus(JourneyStatus.AT_CUSTOMS);
            } else if (truckJourney.getStatus().equals(JourneyStatus.AT_CUSTOMS)) {
                truckJourney.setStatus(JourneyStatus.PAUSE);
            }
            truckJourney.setChanged(3);
        }
        if (changedTime == 3) {
            truckJourney.setTravelDistance(truckJourney.getCourseDistance());
            truckJourney.setStatus(JourneyStatus.UNLOADING);
            truckJourney.setArriving_date(LocalDate.now());
            truckJourney.setArrived(true);
        }
    }

    public int getRandomDistance(int start, int end) {
        Random r = new Random();
        return r.nextInt((end - start) + 1) + start;
    }

}
