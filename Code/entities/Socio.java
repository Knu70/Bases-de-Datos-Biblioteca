/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upsa.prueba2biblioteca.entities;

/**
 *
 * @author ldiaz
 */
public class Socio 
{
    private String codigo;
    private String dni;
    private String nombre;

    public Socio() {
    }

    public Socio(String codigo, String dni, String nombre) {
        this.codigo = codigo;
        this.dni = dni;
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Socio{" + "codigo=" + codigo + ", dni=" + dni + ", nombre=" + nombre + '}';
    }
    
    
}
