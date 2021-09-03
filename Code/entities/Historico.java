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
public class Historico 
{
    private String codigo;
    private String nombre;
    private Date fecha_prestamo;
    private Date fecha_devolucion;

    public Historico() {
    }

    public Historico(String codigo, String nombre, Date fecha_prestamo, Date fecha_devolucion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.fecha_prestamo = fecha_prestamo;
        this.fecha_devolucion = fecha_devolucion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        return "Historico Libro {" + "codigo=" + codigo + ", nombre=" + nombre + ", fecha_prestamo=" + fecha_prestamo + ", fecha_devolucion=" + fecha_devolucion + "}\n";
    }
    
}
