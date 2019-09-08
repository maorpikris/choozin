package com.choozin.utils;

public class FieldValidationState {
    private boolean valid;
    private String message;

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FieldValidationState(boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }
}
