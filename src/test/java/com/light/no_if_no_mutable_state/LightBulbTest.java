package com.light.no_if_no_mutable_state;


import org.junit.Test;

import static org.hamcrest.core.Is.isA;
import static org.junit.Assert.assertThat;

public class LightBulbTest {

    @Test
    public void a_new_light_is_always_off(){
        assertThat(com.light.no_if_no_mutable_state.LightBulb.newLight(), isA(OffLight.class));
    }
}
