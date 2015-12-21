package com.notif;


public class LightBulb {
    public static final String INVALID_TRANSITION_OFF_to_OFF = "Cannot turn off a light bulb which is already off";
    private boolean off = true;

    public boolean is_off() {
        return this.off;
    }

    public LightBulb on() {
        this.off = false;
        return this;
    }

    public boolean is_on() {
        return !this.off;
    }

    public LightBulb off() {
        if(is_off()) throw new IllegalStateException(INVALID_TRANSITION_OFF_to_OFF);
        this.off = true;
        return this;
    }
}
