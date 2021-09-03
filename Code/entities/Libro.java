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
public class Libro 
{
    private String isbn;
    private String titulo;
    private Estado estado;
    private String localizador;

    public Libro() {
    }

    public Libro(String isbn, String titulo, Estado estado, String localizador) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.estado = estado;
        this.localizador = localizador;
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getLocalizador() {
        return localizador;
    }

    public void setLocalizador(String localizador) {
        this.localizador = localizador;
    }

    @Override
    public String toString() {
        return "Libro{" + "isbn=" + isbn + ", titulo=" + titulo + ", estado=" + estado + ", localizador=" + localizador + '}';
    }
    
    
    
}
