/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import controller.DAO.SingletonDAO;
import java.util.Calendar;
import static java.util.Calendar.DATE;
import static java.util.Calendar.HOUR_OF_DAY;
import java.util.Date;
import java.util.List;
import model.CentroAplicacion;
import model.DatosExamen;
import model.FormularioSolicitante;
import controller.Comunicacion;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
/**
 *
 * @author globv
 */
public class GeneradorCitas {
    //clase creada para responder al ejercicio 8 de la tarea 4
    
    private ArrayList<Calendar> citasExistentes = new ArrayList<Calendar>();
    public int cantidadSolicitantesPosCita = 20;
    private Comunicacion notificador = new Comunicacion();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");	
    private Calendar fechaInicio = new GregorianCalendar(2020,10,28,9,0,0);

    
    
    
    public GeneradorCitas() {
        
    }

    
    void GenerarCitas(ArrayList<FormularioSolicitante> solicitantes){
        Calendar cita = fechaInicio;
        
        for( int i = 0; i < solicitantes.size(); i++){
            //mientras no todos los solicitnates tenga cita
            for(int j = 0; j < 4; j++){
              //mientras no todas las citas disponibles por tanda esten cubiertas
              for(int k = 0; k < cantidadSolicitantesPosCita; k++){
                  //mientras no todas las tandas de citas del dia esten cubiertas
                  this.citasExistentes.add(cita);
                  
                }
              cita.add(HOUR_OF_DAY, 4); //se le suman 4 horas
            }
            cita.add(DATE, 1);//se le suma 1 dia
            cita.set(HOUR_OF_DAY, 9); //se resetea la hora
        }
    }
    
    void asignarCitasASolicitantes(ArrayList<FormularioSolicitante> solicitantes){
        int i = 0;
        for(FormularioSolicitante solicitante : solicitantes){
            DatosExamen datosCita = solicitante.getDetalleExamen();
            datosCita.setCitaExamen(citasExistentes.get(i));
            
        }
    }
    
    public void notificar(ArrayList<FormularioSolicitante> solicitantes){
        
        for(FormularioSolicitante solicitante : solicitantes){
            DatosExamen exam = solicitante.getDetalleExamen();
            notificador.enviarCorreo(solicitante.getCorreoSolic());
            System.out.println(solicitante.getNombreSolic()+ ", su cita es" + exam.getCitaExamen().getTime());
        
        }
        
    }

}