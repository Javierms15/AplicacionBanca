package com.example.demo;

public class MaxUserConnectionsException extends RuntimeException {
    public MaxUserConnectionsException(String message) {
        super(message);
    }
}