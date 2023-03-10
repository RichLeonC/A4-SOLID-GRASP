/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import model.Carrera;
import model.Configuracion;
import model.FormularioSolicitante;
import controller.GeneradorCitas;
import java.util.Calendar;

/**
 *
 * @author ersolano
 */
public class Controlador {
    
    private AdmConfiguracion admConfig = new AdmConfiguracion();
    private AdmCarreras admCarreras = new AdmCarreras();
    private AdmFormularios admFormularios = new AdmFormularios();
    private GeneradorCitas generador = new GeneradorCitas();
    
    public Controlador() {
    }
    
    public boolean editarPuntajeGeneralAdmision(int nuevoValor){
        return admConfig.editarPuntajeAdmision(nuevoValor);
    }

    public int getPuntajeGeneralAdmision() {
        return admConfig.getPuntajeAdmision();
    }
    
    public boolean guardarConfiguracion(){
        return admConfig.guardarConfiguracion();
    }
    
    public List<Carrera> getCarreras(){
        return admCarreras.getCarreras();
    }
      
    public List<Carrera> getCarrerasPorSede(String sede){
        return admCarreras.getCarreras(sede);
    }
    
    public boolean editarCapacidadAdmision(String codigoCarrera, String codigoSede, int capacidad){
        return admCarreras.editarCarrera(codigoCarrera, codigoSede, capacidad);                
    }
    
    public boolean editarPuntajeMinimoAdmision(String codigoCarrera, String codigoSede, int puntaje){
        return admCarreras.editarCarrera(puntaje, codigoCarrera, codigoSede);                
    }

//    public Object getParam(String param, Class elTipo) {
//        if (Integer.class.equals(elTipo)) {
//            return Configuracion.getInstance().getParam(param, Integer.class);
//        }
//        if (String.class.equals(elTipo)) {
//            return Configuracion.getInstance().getParam(param, String.class);
//        }
//        if (Double.class.equals(elTipo)) {
//            return Configuracion.getInstance().getParam(param, Double.class);
//        }
//        return null;
//
//    }

    public boolean registrarFormulario(DTOFormulario elDTO) {
        // se hace cualquier otra operaci??n que se pudiera requerir 
        return admFormularios.registrarFormulario(elDTO);
    }
    
    public FormularioSolicitante getFormulario(int idSolic){
        return admFormularios.consultarFormulario(idSolic);
    }
    
    public void simulacionAplicacionExamen(int idSolic){
        admFormularios.simulacionAplicacionExamen(idSolic, null);
    }

    public ArrayList<FormularioSolicitante> darEstadosFormulariosCarrera(ArrayList<FormularioSolicitante> formularios){
        return admFormularios.darEstadosFormulariosCarrera(formularios);
    }
    public ArrayList<FormularioSolicitante> getFormsPorCarrera_Solicitante(String codigoCarrera, ArrayList<FormularioSolicitante> forms){
        return admFormularios.getDesgloseCandidatosPorSolicitante(codigoCarrera, forms);
    }
    
    public void generarCitas(ArrayList<FormularioSolicitante>  forms){
        generador.GenerarCitas(forms);
        generador.asignarCitasASolicitantes(forms);
        
    }
    
    public void notificarCita(ArrayList<FormularioSolicitante> forms ){
        generador.notificar(forms);
    }
    
    
}