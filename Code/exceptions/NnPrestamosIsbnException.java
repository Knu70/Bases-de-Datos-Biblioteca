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
public class NnPrestamosIsbnException extends AppException
{
    public NnPrestamosIsbnException() {
        super("ISBN vacio");
    }
}