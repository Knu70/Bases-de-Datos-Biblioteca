/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upsa.prueba2biblioteca.entities;

import java.util.Date;

/**
 *
 * @author ldiaz
 */
public class Prestamo 
{
    String localizador;
    String isbn;
    String dni;
    Date fecha_prestamo;
    Date fecha_devolucion;

    public Prestamo() {
    }

    public Prestamo(String localizador, String isbn, String dni, Date fecha_prestamo, Date fecha_devolucion) {
        this.localizador = localizador;
        this.isbn = isbn;
        this.dni = dni;
        this.fecha_prestamo = fecha_prestamo;
        this.fecha_devolucion = fecha_devolucion;
    }

    public String getLocalizador() {
        return localizador;
    }

    public void setLocalizador(String localizador) {
        this.localizador = localizador;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Date getFecha_prestamo() {
        return fecha_prestamo;
    }

    public void setFecha_prestamo(Date fecha_prestamo) {
        this.fecha_prestamo = fecha_prestamo;
    }

    public Date getFecha_devolucion() {
        return fecha_devolucion;
    }

    public void setFecha_devolucion(Date fecha_devolucion) {
        this.fecha_devolucion = fecha_devolucion;
    }

    @Override
    public String toString() {
        return "Prestamo{" + "localizador=" + localizador + ", isbn=" + isbn + ", dni=" + dni + ", fecha_prestamo=" + fecha_prestamo + ", fecha_devolucion=" + fecha_devolucion + '}';
    }

    
    
    
}
