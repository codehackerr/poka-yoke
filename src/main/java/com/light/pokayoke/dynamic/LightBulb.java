package com.light.pokayoke.dynamic;


public abstract class LightBulb {
    public static LightBulb newLight() {
        return new OffLight();
    }
    public abstract LightBulb toggle();

    public boolean is_on() {
        return this instanceof OnLight;
    }

    public boolean is_off() {
        return this instanceof OffLight;
    }

    private static class OffLight extends LightBulb {
        @Override
        public LightBulb toggle() {
            return new OnLight();
        }
    }

    private static class OnLight extends LightBulb {
        @Override
        public LightBulb toggle() {
            return new OffLight();
        }
    }
}
