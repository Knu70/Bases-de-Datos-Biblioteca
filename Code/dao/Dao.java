/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upsa.prueba2biblioteca.dao;

import es.upsa.prueba2biblioteca.entities.Historico;
import es.upsa.prueba2biblioteca.entities.Historico2;
import es.upsa.prueba2biblioteca.entities.Libro;
import es.upsa.prueba2biblioteca.entities.Prestamo;
import es.upsa.prueba2biblioteca.entities.Socio;
import es.upsa.prueba2biblioteca.exceptions.AppException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author ldiaz
 */
public interface Dao extends AutoCloseable
{
    Libro insertarLibro(String isbn, String titulo) throws AppException;
    Socio insertarSocio(String dni, String nombre) throws AppException;
    Optional<Prestamo> prestarLibro(String isbn, String dni, Date fecha_prestamo) throws AppException;
    Prestamo insertarPrestamo(String isbn, String dni, Date fecha_prestamo) throws AppException;
    Libro actualizarLibroPrestamo(String isbn, String localizador) throws AppException;
    Optional<Prestamo> devolverLibro(String isbn, Date fecha_devolucion) throws AppException;
    Prestamo actualizarPrestamoDevuelto(String isbn, Date fecha_devolucion) throws AppException;
    Libro actualizarLibroDevuelto(String isbn) throws AppException;
    List<Historico> historicoLibro(String isbn) throws AppException;
    List<Historico2> historicoSocio(String codigo) throws AppException; 
}
