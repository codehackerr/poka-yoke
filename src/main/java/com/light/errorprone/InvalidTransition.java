package com.light.errorprone;

public class InvalidTransition extends Throwable {
    public InvalidTransition(String message) {
        super(message);
    }
}
