package com.light.errorprone;


class LightBulb {
    public static final String INVALID_TRANSITION_OFF_to_OFF = "Cannot turn off a light bulb which is already off";
    public static final String INVALID_TRANSITION_ON_to_ON = "Cannot turn on a light bulb which is already on";

    private static final boolean OFF = false;
    private static final boolean ON = !OFF;

    private boolean state = OFF;

    public boolean is_off() {
        return !is_on();
    }

    public boolean is_on() {
        return this.state;
    }

    public void on() throws InvalidTransition {
        change_state(this.state, ON);
    }

    public void off() throws InvalidTransition {
        change_state(this.state, OFF);
    }

    void change_state(boolean current_state, boolean transition) throws InvalidTransition {
        if (current_state == OFF && transition == ON) {
            state = ON;
        } else if (current_state == OFF && transition == OFF) {
            throw new InvalidTransition(INVALID_TRANSITION_OFF_to_OFF);
        } else if (current_state == ON && transition == OFF) {
            state = OFF;
        } else if (current_state == ON && transition == ON) {
            throw new InvalidTransition(INVALID_TRANSITION_ON_to_ON);
        }
    }
}
