package com.light.errorprone;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LightBulbTest {

    private LightBulb lightBulb;

    @Before
    public void setUp() {
        lightBulb = new LightBulb();
    }

    @Test
    public void a_new_light_is_always_off(){
        assertThat(lightBulb.is_off(), is(true));
    }

    @Test
    public void an_off_light_can_be_turned_on() throws InvalidTransition {
        lightBulb.on();
        assertThat(lightBulb.is_on(), is(true));
    }

    @Test
    public void an_on_light_can_be_turned_off() throws InvalidTransition {
        lightBulb.on();
        assertThat(lightBulb.is_on(), is(true));

        lightBulb.off();
        assertThat( lightBulb.is_off(), is(true));
    }

    @Test(expected = InvalidTransition.class)
    public void an_off_light_cannot_be_turned_off() throws InvalidTransition {
       lightBulb.off();
    }

    @Test(expected = InvalidTransition.class)
    public void an_on_light_cannot_be_turned_on() throws InvalidTransition {
        lightBulb.on();
        lightBulb.on();
    }

}
