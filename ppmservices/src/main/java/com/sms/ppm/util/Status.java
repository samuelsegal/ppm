package com.sms.ppm.util;

public enum Status {
    TODO("TODO"), DONE("DONE"), COMPLETE("COMPLETE");
    private String value;

    Status(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
