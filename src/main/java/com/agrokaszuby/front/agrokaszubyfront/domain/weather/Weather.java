package com.agrokaszuby.front.agrokaszubyfront.domain.weather;

import com.agrokaszuby.front.agrokaszubyfront.domain.weather.DailyForecast;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Weather {
    private float lat;
    private float lon;
    private List<DailyForecast> daily;
}
