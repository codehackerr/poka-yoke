package com.light.pokayoke._static;


import org.junit.Test;


import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertThat;

public class LightBulbTest {

    @Test
    public void a_new_light_is_always_off(){
        assertThat(LightBulb.newLight(), isA(OffLight.class));
    }

    @Test
    public void an_off_light_can_be_turned_on(){
       assertThat(LightBulb.newLight().on(), isA(OnLight.class));
    }

    @Test
    public void an_on_light_can_be_turned_off(){
        OnLight on = LightBulb.newLight().on();
        assertThat(on.off(), isA(OffLight.class));
    }
}
