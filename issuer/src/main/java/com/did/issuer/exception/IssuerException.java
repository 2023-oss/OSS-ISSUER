package com.did.issuer.exception;

import java.util.HashMap;
import java.util.Map;

public abstract class IssuerException extends RuntimeException {
    public final Map<String, String> validation = new HashMap<>();
    public IssuerException(String message){
        super(message);
    }
    public abstract int getStatusCode();
    public void addValidation(String fieldName, String message){
        validation.put(fieldName, message);
    }
}
