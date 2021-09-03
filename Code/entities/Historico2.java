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
public class Historico2 
{
    private String isbn;
    private String titulo;
    private Date fecha_prestamo;
    private Date fecha_devolucion;

    public Historico2() {
    }

    public Historico2(String isbn, String titulo, Date fecha_prestamo, Date fecha_devolucion) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.fecha_prestamo = fecha_prestamo;
        this.fecha_devolucion = fecha_devolucion;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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
        return "Historico Socio {" + "isbn=" + isbn + ", titulo=" + titulo + ", fecha_prestamo=" + fecha_prestamo + ", fecha_devolucion=" + fecha_devolucion + "}\n";
    }
    
}
