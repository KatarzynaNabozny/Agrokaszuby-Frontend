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
public class Temp {

    private float day;
    private float min;
    private float max;
    private float night;
    private float eve;
    private float morn;
}
