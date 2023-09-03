package com.did.issuer.exception;

public class NonExiststentException extends IssuerException{
    public NonExiststentException(String message){
        super(message);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
