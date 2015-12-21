package com.notif;


public class LightBulb {
    private boolean off = true;

    public boolean is_off() {
        return this.off;
    }

    public LightBulb on() {
        this.off = false;
        return this;
    }
}