/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.DAO.SingletonDAO;
import java.util.ArrayList;
import model.FormularioSolicitante;
import java.util.Calendar;
import java.util.Random;
import model.Carrera;
import model.Configuracion;

/**
 *
 * @author ersolano
 */
public class AdmFormularios {

    public AdmFormularios() {
    }
   
    public boolean registrarFormulario (DTOFormulario elDTO){
        
        // verifica que el solicitante no haya registrado otro anteriormente
        FormularioSolicitante elForm = SingletonDAO.getInstance().consultarFormulario (elDTO.getIdSolic());
        
        if (elForm == null){
            elForm = new FormularioSolicitante();
            elForm.setIdSolic(elDTO.getIdSolic());
            elForm.setNombreSolic(elDTO.getNombreSolic());
            elForm.setCorreoSolic(elDTO.getCorreoSolic());
            elForm.setCelularSolic(elDTO.getCelularSolic());
            elForm.setColegioSolic(elDTO.getColegioSolic());
            elForm.setDirSolicPCD(elDTO.getDirSolic());
            elForm.setDetalleDirSolic(elDTO.getDetalleDireccion());
            elForm.setCarreraSolic(elDTO.getCarreraSolic());
            
            boolean res= SingletonDAO.getInstance().agregarFormulario(elForm);
            return res;
        }
        return false;
    }
    
    public FormularioSolicitante consultarFormulario (int idSolic){
        return SingletonDAO.getInstance().consultarFormulario(idSolic);
   }

    public void darEstadosFormulariosCarrera(Carrera carrera){
        SingletonDAO.getInstance().darEstadosFormulariosCarrera(carrera);
    }
    
    public void simulacionAplicacionExamen(int idSolic, Calendar citaExamen){
        FormularioSolicitante form = consultarFormulario(idSolic);
        Random rand = new Random();
       // if(form.getDetalleExamen().getCitaExamen().equals(citaExamen)){
            int puntaje = rand.nextInt(901);
          //  System.out.println("P obtenido random: "+puntaje);
            form.getDetalleExamen().setPuntajeObtenido(puntaje);
      //  }
        
    }
    
    public ArrayList<FormularioSolicitante> getDesgloseCandidatosPorSolicitante(String codigoCarrera, ArrayList<FormularioSolicitante>forms){
        ArrayList<FormularioSolicitante> filtrado = new ArrayList();
        for(int i = 0; i<forms.size();i++){
          //  System.out.println(forms.get(i).getCarreraSolic(). + " == "+codigoCarrera );
            if(forms.get(i).getCarreraSolic().getCodigo().equals(codigoCarrera)){
                filtrado.add(forms.get(i));
            }
        }
        
       return filtrado;
    }
}
