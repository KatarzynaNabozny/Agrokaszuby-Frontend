package com.agrokaszuby.front.agrokaszubyfront.domain.weather;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyForecast {
    private long dt;
    private float moon_phase;
    private Temp temp;
    private float pressure;
}
