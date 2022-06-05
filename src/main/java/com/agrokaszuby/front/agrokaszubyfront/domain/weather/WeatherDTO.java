package com.agrokaszuby.front.agrokaszubyfront.domain.weather;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDTO {
    private LocalDate date;
    private float maxTemperature;
}

