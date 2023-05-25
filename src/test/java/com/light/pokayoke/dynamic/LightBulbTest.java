package com.light.pokayoke.dynamic;


import org.junit.Test;

import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class LightBulbTest {

    @Test
    public void a_new_light_is_always_off(){
        assertTrue(LightBulb.newLight().is_off());
    }

    @Test
    public void an_off_light_can_be_turned_on(){
       assertTrue(LightBulb.newLight().toggle().is_on());
    }

    @Test
    public void an_on_light_can_be_turned_off(){
        assertTrue(LightBulb.newLight().toggle().toggle().is_off());
    }

    @Test
    public void dynamic_on_off_cycles_should_work() {
        LightBulb light_bulb = LightBulb.newLight();
        for (int i = 0, repetitions = 9; i < repetitions; i++) {
            light_bulb = light_bulb.toggle();
        }
        assertTrue(light_bulb.is_on());
    }

}
