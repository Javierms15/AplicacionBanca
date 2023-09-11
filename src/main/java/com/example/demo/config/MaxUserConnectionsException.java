package com.example.demo.config;

public class MaxUserConnectionsException extends RuntimeException {
    public MaxUserConnectionsException(String message) {
        super(message);
    }
}