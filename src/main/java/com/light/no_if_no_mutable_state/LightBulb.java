package com.light.no_if_no_mutable_state;


public class LightBulb {
    public static OffLight newLight() {
        return new OffLight();
    }
}
