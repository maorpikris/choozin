package com.choozin.utils;

// The structure of a field validation.
public class FieldValidationState {
    private boolean valid;
    private String message;

    public FieldValidationState(boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }

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
}
