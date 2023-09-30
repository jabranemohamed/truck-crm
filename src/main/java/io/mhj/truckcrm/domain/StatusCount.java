package io.mhj.truckcrm.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class StatusCount {

    private JourneyStatus status;
    private Long total;

}
