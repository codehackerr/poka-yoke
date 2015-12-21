package com.light.no_if_no_mutable_state;


import org.junit.Test;

import static org.hamcrest.core.Is.isA;
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

}
