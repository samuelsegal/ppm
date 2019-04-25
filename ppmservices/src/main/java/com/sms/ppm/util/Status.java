package com.sms.ppm.util;

public enum Status {
    TO_DO("TO_DO"), IN_PROGRESS("IN_PROGRESS"), COMPLETE("COMPLETE");
    private String value;

    Status(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
