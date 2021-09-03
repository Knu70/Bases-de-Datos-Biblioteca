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
public class UkLibrosLocalizadorException extends AppException
{
    private String localizador;

    public UkLibrosLocalizadorException(String localizador) {
        super("Hay otro libro con el localizador " + localizador);
        this.localizador = localizador;
    }

    public String getLocalizador() {
        return localizador;
    }
}
