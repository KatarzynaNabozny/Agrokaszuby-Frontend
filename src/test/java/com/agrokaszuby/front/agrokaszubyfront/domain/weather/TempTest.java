package com.agrokaszuby.front.agrokaszubyfront.domain.weather;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TempTest {

    Temp temp = new Temp();

    @Test
    void setDay() {
        //GIVEN
        float expected = 454;
        //WHEN
        temp.setDay(expected);
        //THEN
        assertEquals(expected, temp.getDay());
    }

    @Test
    void setMin() {
        //GIVEN
        float expected = 454;
        //WHEN
        temp.setMin(expected);
        //THEN
        assertEquals(expected, temp.getMin());
    }

    @Test
    void setMax() {
        //GIVEN
        float expected = 454;
        //WHEN
        temp.setMax(expected);
        //THEN
        assertEquals(expected, temp.getMax());
    }

    @Test
    void setNight() {
        //GIVEN
        float expected = 454;
        //WHEN
        temp.setNight(expected);
        //THEN
        assertEquals(expected, temp.getNight());
    }

    @Test
    void setEve() {
        //GIVEN
        float expected = 454;
        //WHEN
        temp.setEve(expected);
        //THEN
        assertEquals(expected, temp.getEve());
    }

    @Test
    void setMorn() {
        //GIVEN
        float expected = 454;
        //WHEN
        temp.setMorn(expected);
        //THEN
        assertEquals(expected, temp.getMorn());
    }
}