/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upsa.prueba2biblioteca.dao.impl;

import es.upsa.prueba2biblioteca.dao.Dao;
import es.upsa.prueba2biblioteca.entities.Estado;
import es.upsa.prueba2biblioteca.entities.Historico;
import es.upsa.prueba2biblioteca.entities.Historico2;
import es.upsa.prueba2biblioteca.entities.Libro;
import es.upsa.prueba2biblioteca.entities.Prestamo;
import es.upsa.prueba2biblioteca.entities.Socio;
import es.upsa.prueba2biblioteca.exceptions.AppException;
import es.upsa.prueba2biblioteca.exceptions.NnLibrosEstadoException;
import es.upsa.prueba2biblioteca.exceptions.NnLibrosTituloException;
import es.upsa.prueba2biblioteca.exceptions.NnPrestamosDniException;
import es.upsa.prueba2biblioteca.exceptions.NnPrestamosFechaPretamoException;
import es.upsa.prueba2biblioteca.exceptions.NnPrestamosIsbnException;
import es.upsa.prueba2biblioteca.exceptions.NnSociosDniException;
import es.upsa.prueba2biblioteca.exceptions.NnSociosNombreException;
import es.upsa.prueba2biblioteca.exceptions.SQLNoControladoException;
import es.upsa.prueba2biblioteca.exceptions.UkLibrosLocalizadorException;
import es.upsa.prueba2biblioteca.exceptions.UkSociosDniException;
import java.sql.Connection;
import java.util.Date;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import oracle.jdbc.OracleDriver;

/**
 *
 * @author ldiaz
 */
public class OracleDao implements Dao
{
    private Connection connection;

    public OracleDao(String url, String usuario, String pass) throws AppException
    {
        try
        {
            Driver driver = new OracleDriver();
            DriverManager.registerDriver( driver );
            this.connection = DriverManager.getConnection(url, usuario, pass);
            
        } catch (SQLException sqlException)
          {
              throw new SQLNoControladoException(sqlException);            
          }
    }
    
    //inserta un nuevo libro con estado LIBRE y localizador NULL
    @Override
    public Libro insertarLibro(String isbn, String titulo) throws AppException
    {
        final String SQL = "INSERT INTO LIBROS(ISBN, TITULO, ESTADO, LOCALIZADOR)" +
                           "            VALUES(?,    ?,      ?,      ?          )" ;

        String[] fields = new String[] { "ISBN" };
        String localizador = null;
        Estado estado = Estado.LIBRE;
        try ( PreparedStatement ps = connection.prepareStatement(SQL, fields) )
        {
        ps.setString(1, isbn);
        ps.setString(2, titulo);
        ps.setString(3, estado.name());
        ps.setString(4, localizador);

        ps.executeUpdate();

        return new Libro(isbn, titulo, estado, localizador);


        }catch (SQLException sqlException)
            {
                String message = sqlException.getMessage();
                if      ( message.contains("UK_LIBROS.LOCALIZADOR") ) throw new UkLibrosLocalizadorException(localizador);
                else if ( message.contains("NN_LIBROS.TITULO") )      throw new NnLibrosTituloException();
                else if ( message.contains("NN_LIBROS.ESTADO") )      throw new NnLibrosEstadoException();

                throw new SQLNoControladoException(sqlException);
            }   
    }
    
    //inserta un nuevo socio 
    @Override
    public Socio insertarSocio(String dni, String nombre) throws AppException
    {
        final String SQL = "INSERT INTO SOCIOS(CODIGO,               DNI, NOMBRE)" +
                           "            VALUES(SEQ_SOCIOS.NEXTVAL,   ?,   ?     )";
       
        String[] fields = new String[] { "CODIGO" };
       
        try ( PreparedStatement ps = connection.prepareStatement(SQL, fields) )
        {
            ps.setString(1, dni);
            ps.setString(2, nombre);
            ps.executeUpdate();
            try( ResultSet rsKeys = ps.getGeneratedKeys() )
            {
            rsKeys.next();
            return new Socio(rsKeys.getString(1), dni, nombre);
            }
            
        } catch (SQLException sqlException)
            {
                String message = sqlException.getMessage();
                if      ( message.contains("UK_SOCIOS.DNI") )       throw new UkSociosDniException(dni);
                else if ( message.contains("NN_SOCIOS.DNI") )       throw new NnSociosDniException();
                else if ( message.contains("NN_SOCIOS.NOMBRE") )    throw new NnSociosNombreException();
                throw new SQLNoControladoException(sqlException);
            }
    }
    
    //actualiza un libro prestado cambiando el estado a OCUPADO y el localizador al del prestamo
   @Override
    public Libro actualizarLibroPrestamo(String isbn, String localizador) throws AppException
    {
        final String SQL =  "UPDATE LIBROS                           " +
                            "SET ESTADO = 'OCUPADO', LOCALIZADOR = ? " +
                            "WHERE ISBN = ?                          " ;

        
        try ( PreparedStatement ps = connection.prepareStatement(SQL) )
        {
            ps.setString(1, localizador);
            ps.setString(2, isbn);

            ps.executeUpdate();
            
            return null;
            
        }catch (SQLException sqlException)
            {
                String message = sqlException.getMessage();
                if      ( message.contains("UK_LIBROS.LOCALIZADOR") ) throw new UkLibrosLocalizadorException(localizador);
                else if ( message.contains("NN_LIBROS.ESTADO") )      throw new NnLibrosEstadoException();

                throw new SQLNoControladoException(sqlException);
            }   
        
    }
    
    //registra el prestamo con la fecha de devolucion = NULL 
    //llama a la funcion anterior para actualizar el libro prestado
    @Override
    public Prestamo insertarPrestamo(String isbn, String dni, Date fecha_prestamo) throws AppException
    {
        final String SQL = "INSERT INTO PRESTAMOS(LOCALIZADOR,               ISBN, DNI, FECHA_PRESTAMO, FECHA_DEVOLUCION)" +
                           "               VALUES(SEQ_PRESTAMOS.NEXTVAL,     ?,    ?,   ?,              ?               )" ;
       
        String[] fields = new String[] { "LOCALIZADOR" };
        String localizador = null;
        java.sql.Date fecha_devolucion = null;
        try ( PreparedStatement ps = connection.prepareStatement(SQL, fields) )
        {
            ps.setString(1, isbn);
            ps.setString(2, dni);
            java.sql.Date sqlDate = new java.sql.Date( fecha_prestamo.getTime() );
            ps.setDate(3, sqlDate);
            ps.setDate(4, fecha_devolucion);;
            ps.executeUpdate();

            try ( ResultSet rsKeys = ps.getGeneratedKeys() )
            {
                rsKeys.next();
                localizador = rsKeys.getString(1);
            }
            actualizarLibroPrestamo(isbn, localizador);
            Prestamo prestamo = new Prestamo();
            prestamo.setLocalizador(localizador);
            prestamo.setIsbn(isbn);
            prestamo.setDni(dni);
            prestamo.setFecha_prestamo(fecha_prestamo);
            prestamo.setFecha_devolucion(fecha_devolucion);
            return prestamo;
            
            
        } catch (SQLException sqlException)
            {
                String message = sqlException.getMessage();
                if ( message.contains("NN_PRESTAMOS.ISBN") )           throw new NnPrestamosIsbnException();
                else if ( message.contains("NN_PRESTAMOS.DNI") )            throw new NnPrestamosDniException();
                else if ( message.contains("NN_PRESTAMOS.FECHA_PRESTAMO") ) throw new NnPrestamosFechaPretamoException();

                throw new SQLNoControladoException(sqlException);
            }
        
        
    }
    
    //comprueba que el dni y el isbn existen para registrar un prestamo
    //llama a la funcion anterior para registrar el prestamo
    @Override
    public Optional<Prestamo> prestarLibro(String isbn, String dni, Date fecha_prestamo) throws AppException
    {
        final String SQL = "SELECT L.ISBN, S.DNI          " +
                           "FROM LIBROS L, SOCIOS S       " +
                           "WHERE L.ISBN = ? AND S.DNI = ?" ;
        try ( PreparedStatement ps = connection.prepareStatement(SQL) )
        {
            ps.setString(1, isbn);
            ps.setString(2, dni);
            try ( ResultSet rs = ps.executeQuery() )
            {
                if ( rs.next() )
                {
                    return Optional.of(insertarPrestamo(isbn, dni, fecha_prestamo));
                }
                else
                {
                    return Optional.empty();
                }
            }
        
        } catch (SQLException sqlException)
            {
                throw new SQLNoControladoException(sqlException);
            }
        
    }
    
    //actualiza el estado a libre y el localizador a null de un libro devuelto
    @Override
    public Libro actualizarLibroDevuelto(String isbn) throws AppException
    {
        final String SQL =  "UPDATE LIBROS                            " +
                            "SET ESTADO = 'LIBRE', LOCALIZADOR = ? " +
                            "WHERE ISBN = ?                           " ;

        
        try ( PreparedStatement ps = connection.prepareStatement(SQL) )
        {
            String localizador = null;
            ps.setString(1, localizador);
            ps.setString(2, isbn);

            ps.executeUpdate();
            
            Libro libro = new Libro();
            libro.setEstado(Estado.LIBRE);
            libro.setLocalizador(localizador);
            return libro;
            
        }catch (SQLException sqlException)
            {
                String message = sqlException.getMessage();
                if ( message.contains("NN_LIBROS.ESTADO") )      throw new NnLibrosEstadoException();

                throw new SQLNoControladoException(sqlException);
            }   
        
    }
    
    //actualiza la fecha de devolucion de un prestamo devuelto
    @Override
    public Prestamo actualizarPrestamoDevuelto(String isbn, Date fecha_devolucion) throws AppException
    {
        final String SQL =  "UPDATE PRESTAMOS           " +
                            "SET FECHA_DEVOLUCION = ?   " +
                            "WHERE ISBN = ?             " ;

        
        try ( PreparedStatement ps = connection.prepareStatement(SQL) )
        {
            java.sql.Date sqlDate = new java.sql.Date( fecha_devolucion.getTime() );
            ps.setDate(1, sqlDate);
            ps.setString(2, isbn);

            ps.executeUpdate();
            
            Prestamo prestamo = new Prestamo();
            prestamo.setFecha_devolucion(fecha_devolucion);
            return prestamo;
            
        }catch (SQLException sqlException)
            {
                throw new SQLNoControladoException(sqlException);
            }   
        
    }
    
    //devuelve un libro comprobando que el isbn existe, que su estado es ocupado y que el localizador
    //coincide en la tabla LIBROS Y PRESTAMOS
    //llama a las dos funciones anteriores para actualizar libRo y prestamo 
    @Override
    public Optional<Prestamo> devolverLibro(String isbn, Date fecha_devolucion) throws AppException
    {
        final String SQL = "SELECT P.ISBN                                                                " +
                           "FROM LIBROS L, PRESTAMOS P                                                   " +
                           "WHERE P.ISBN = ? AND L.ESTADO = 'OCUPADO' AND P.LOCALIZADOR = L.LOCALIZADOR  " ;
        try ( PreparedStatement ps = connection.prepareStatement(SQL) )
        {
            ps.setString(1, isbn);
            try ( ResultSet rs = ps.executeQuery() )
            {
                if ( rs.next() )
                {
                    actualizarLibroDevuelto(isbn);
                    java.sql.Date sqlDate = new java.sql.Date( fecha_devolucion.getTime() );
                    
                    return Optional.of(actualizarPrestamoDevuelto(isbn, sqlDate));
                }
                else
                {
                    return Optional.empty();
                }
            }
        
        } catch (SQLException sqlException)
            {
                String message = sqlException.getMessage();
                if ( message.contains("NN_LIBROS.ESTADO") )      throw new NnLibrosEstadoException();
                throw new SQLNoControladoException(sqlException);
            }
        
    }
    
    
    @Override
    public List<Historico> historicoLibro(String isbn) throws AppException 
    {
        final String SQL = "SELECT S.CODIGO, S.NOMBRE, P.FECHA_PRESTAMO, P.FECHA_DEVOLUCION " +
                           "FROM SOCIOS S, PRESTAMOS P                                      " +
                           " WHERE P.ISBN = ? AND P.DNI = S.DNI                             " +
                           "ORDER BY FECHA_PRESTAMO                                         " ;
        
        List<Historico> historicos = new LinkedList<>();
        
        try (PreparedStatement ps = connection.prepareStatement(SQL);
            )
        {
            ps.setString(1, isbn);
            try ( ResultSet rs = ps.executeQuery() )
            {
                if ( rs.next() )
                {
                    do
                    {
                        Historico historico = new Historico(); 
                        historico.setCodigo(rs.getString(1));
                        historico.setNombre(rs.getString(2));
                        historico.setFecha_prestamo(rs.getDate(3) );
                        historico.setFecha_devolucion(rs.getDate(4) );
                        historicos.add(historico);

                    } while ( rs.next() );
                }
            }
        }
        catch (SQLException sqlException)
          {
              String message = sqlException.getMessage();
              if ( message.contains("NN_PRESTAMOS.ISBN") )      throw new NnPrestamosIsbnException();
              throw new SQLNoControladoException(sqlException);            
          }
        return historicos;
    }
    
    
    public List<Historico2> historicoSocio(String codigo) throws AppException 
    {
        final String SQL = "SELECT L.ISBN, L.TITULO, P.FECHA_PRESTAMO, P.FECHA_DEVOLUCION " +
                           "FROM SOCIOS S, PRESTAMOS P, LIBROS L                          " +
                           " WHERE S.CODIGO = ? AND P.DNI = S.DNI AND P.ISBN = L.ISBN     " +
                           "ORDER BY FECHA_PRESTAMO                                       " ;
        
        List<Historico2> historicos = new LinkedList<>();
        
        try (PreparedStatement ps = connection.prepareStatement(SQL);
            )
        {
            ps.setString(1, codigo);
            try ( ResultSet rs = ps.executeQuery() )
            {
                if ( rs.next() )
                {
                    do
                    {
                        Historico2 historico = new Historico2(); 
                        historico.setIsbn(rs.getString(1));
                        historico.setTitulo(rs.getString(2));
                        historico.setFecha_prestamo(rs.getDate(3) );
                        historico.setFecha_devolucion(rs.getDate(4) );
                        historicos.add(historico);

                    } while ( rs.next() );
                }
            }
        }
        catch (SQLException sqlException)
          {
              String message = sqlException.getMessage();
              if ( message.contains("NN_PRESTAMOS.ISBN") )      throw new NnPrestamosIsbnException();
              throw new SQLNoControladoException(sqlException);            
          }
        return historicos;
    }
    
    @Override
    public void close() throws Exception 
    {
        if ( this.connection != null )
        {
            this.connection.close();
            this.connection = null;
        }
    }    
}
