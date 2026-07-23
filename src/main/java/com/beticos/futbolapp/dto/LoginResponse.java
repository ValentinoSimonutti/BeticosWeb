package com.beticos.futbolapp.dto;

public class LoginResponse {

    private String mensaje;

    public LoginResponse() {
    }

    public LoginResponse(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}