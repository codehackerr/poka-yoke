package com.notif;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LightBulbTest {

    @Test
    public void a_new_light_is_always_off() {
        assertThat(new LightBulb().is_off(), is(true));
    }

}
