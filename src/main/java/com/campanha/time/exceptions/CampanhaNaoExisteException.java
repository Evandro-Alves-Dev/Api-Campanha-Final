package com.campanha.time.exceptions;

public class CampanhaNaoExisteException extends RuntimeException{
    public CampanhaNaoExisteException(String message) {
    super(message);
    }
}
