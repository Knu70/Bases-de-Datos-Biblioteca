/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upsa.prueba2biblioteca;

import es.upsa.prueba2biblioteca.dao.Dao;
import es.upsa.prueba2biblioteca.dao.impl.OracleDao;
import es.upsa.prueba2biblioteca.entities.Historico;
import es.upsa.prueba2biblioteca.entities.Libro;
import es.upsa.prueba2biblioteca.entities.Socio;
import es.upsa.prueba2biblioteca.exceptions.AppException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;



/**
 *
 * @author ldiaz
 */
public class Main {
    public static void main(String[] args) throws Exception 
    {
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        String usuario = "c##luisdiaz";
        String pass = "1995";
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        Scanner sn = new Scanner(System.in);
        boolean exit = false;
        int opc;
        try ( Dao dao = new OracleDao(url, usuario, pass) )
        {
            while ( !exit )
            {
                System.out.println("---BIBLIOTECA---");
                System.out.println("\t 1. Insertar Libro");
                System.out.println("\t 2. Insertar Socio");
                System.out.println("\t 3. Prestar Libro");
                System.out.println("\t 4. Devolver Libro");
                System.out.println("\t 5. Historico Libro");
                System.out.println("\t 6. Historico Socio");
                System.out.println("\t 7. Salir");

                System.out.println("\t Escribe una de las opciones: ");
                opc = sn.nextInt();

                switch ( opc )
                {
                    case 1:
                        System.out.println("\t Introduce el ISBN del libro");
                        String isbn = sn.next();
                        System.out.println("\t Introduce el Titulo del libro");
                        String titulo = sn.next();
                        dao.insertarLibro(isbn, titulo);
                        break;
                    case 2:
                        System.out.println("\t Introduce el DNI del socio");
                        String dni = sn.next();
                        System.out.println("\t Introduce el Nombre del socio");
                        String nombre = sn.next();
                        dao.insertarSocio(dni, nombre);  
                        break;
                    case 3:  
                        System.out.println("\t Introduce el DNI del socio");
                        String dniP = sn.next();
                        System.out.println("\t Introduce el ISBN del libro");
                        String isbnP = sn.next();
                        System.out.println("\t Introduce la fecha del prestamo");
                        Date fechaPrestamo = sdf.parse(sn.next());
                        dao.prestarLibro(isbnP, dniP, fechaPrestamo); 
                        break;
                    case 4:
                        System.out.println("\t Introduce el ISBN del libro");
                        String isbnD = sn.next();
                        System.out.println("\t Introduce la fecha de devolucion");
                        Date fechaDevolucion = sdf.parse(sn.next());
                        dao.devolverLibro(isbnD, fechaDevolucion); 
                        break;
                    case 5:
                        System.out.println("\t Introduce el ISBN del libro");
                        String isbnH = sn.next();
                        System.out.println(dao.historicoLibro(isbnH));
                        //List<Historico> historicos = dao.historicoLibro(isbnH);
                        //for(Historico historico: historicos) 
                        break;
                    case 6:
                        System.out.println("\t Introduce el Codigo del socio");
                        String codigoS = sn.next();
                        System.out.println(dao.historicoSocio(codigoS));
                        break;
                    case 7:
                        exit = true;
                        break;
                    default:
                        System.out.println("Solo numeros entre el 1 y 7");
                }
            }
        }
        
    }
    
}
