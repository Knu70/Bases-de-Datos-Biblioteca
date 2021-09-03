/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upsa.prueba2biblioteca.exceptions;

/**
 *
 * @author ldiaz
 */
public class UkSociosDniException extends AppException
{
    private String dni;

    public UkSociosDniException(String dni) {
        super("Hay otro socio con el mismo dni " + dni);
        this.dni = dni;
    }

    public String getDni() {
        return dni;
    }
}
