/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controlador;
import controller.DAO.SingletonDAO;
import controller.DTOFormulario;
import controller.IParametros;
import java.util.ArrayList;
import java.util.Random;
import model.Configuracion;
import model.DireccionPCD;
import model.FormularioSolicitante;

/**
 *
 * @author ersolano
 */
public class ModeloExAdmision {

    /**
     * @param args the command line arguments
     */
    
    public static Controlador elCtrl = new Controlador();
    public static ArrayList<FormularioSolicitante> forms = new ArrayList(); //para manejar los mismos formularios
    
    public static void generarFormularios(){
            for(int i = 50;i<=100;i++){
            FormularioSolicitante form = demoFormularios2(i);
            forms.add(form);
            
        }
    }

    
    public static void demoFormulario(){
    int idSolic = 1000;
    String nombreSolic ="Solicitante 1000";
    String correoSolic ="correo1000@gmail.com";
    String celularSolic ="8123-4567";
    String colegioSolic ="Colegio del Solicitante 1000";
    DireccionPCD dirSolic = SingletonDAO.getInstance().getPCD(4); 
    String detalleDir = "Cerquita del Morazán";
    String carreraSolic = "IC";
    String sedeSolic = "SJ";
    
    DTOFormulario elDTO = new  DTOFormulario(idSolic, nombreSolic, 
                                correoSolic, celularSolic, colegioSolic, 
                                dirSolic, detalleDir, carreraSolic, sedeSolic);
    
    boolean resultado = elCtrl.registrarFormulario(elDTO);
    System.out.println( resultado   ? "Formulario registrado y es el numero: "+
            elCtrl.getFormulario(idSolic): "No pudo registrar el formulario");
    }
    
    public static void demoCarreras(){
        System.out.println("Visualizar todas las carreras de la institucion");
        System.out.println(elCtrl.getCarreras());
        
        String unaSede = "SJ";
        String unaCarrera = "IC";
        int nuevoPuntajeCarrera= 600;
        int nuevaAdmisionCarrera = 100;
        
        System.out.println("Modificar puntaje de admision a " + 
                            nuevoPuntajeCarrera +" de una carrera particular " + 
                           unaSede+"-"+unaCarrera );
        boolean resultado = elCtrl.editarPuntajeMinimoAdmision(unaCarrera, unaSede, nuevoPuntajeCarrera);
        System.out.println( resultado ? "Puntaje minimo modificado" : "No encontro la carrera para cambio de puntaje");
                
        System.out.println("Modificar capacidad de admision a " +
                            nuevaAdmisionCarrera +" de una carrera particular " + 
                           unaSede+"-"+unaCarrera );
        resultado = elCtrl.editarCapacidadAdmision(unaCarrera, unaSede, nuevaAdmisionCarrera);
        System.out.println( resultado ? "Puntaje minimo modificado" : "No encontro la carrera para cambio de capacidad de admision");

        System.out.println("visualizar las carreras de la sede "+ unaSede);
        System.out.println(elCtrl.getCarrerasPorSede(unaSede));
        
    }
    
    public static void demoConfiguracion(){
        int nuevoPuntaje = 900;
         System.out.println("Obteniendo Puntaje General de Admisión actual" +
                            elCtrl.getPuntajeGeneralAdmision()); 
         
         System.out.println("Editando Puntaje General de Admisión ");
        elCtrl.editarPuntajeGeneralAdmision( nuevoPuntaje );
        
        System.out.println("Obteniendo Puntaje General de Admisión " +
                            elCtrl.getPuntajeGeneralAdmision());        
        
        System.out.println("Obteniendo parámetros de forma generica"); 
        System.out.println(Configuracion.getInstance().getParam(IParametros.MAXIMO_PUNTAJE, Integer.class));
        System.out.println(Configuracion.getInstance().getParam(IParametros.ADMIN_USR, String.class));
        System.out.println(Configuracion.getInstance().getParam(IParametros.ADMIN_PWD, String.class));
        System.out.println(Configuracion.getInstance().getParam("INTENTOS", Integer.class));
        System.out.println(Configuracion.getInstance().getParam("CONSECUTIVO_FORM", Integer.class));
        
        System.out.println("Guardando Configuración...");
        elCtrl.guardarConfiguracion();
       Configuracion.getInstance().guardarProperties();
  
    }
    
    public static void generarCitasExamen(ArrayList<FormularioSolicitante> forms){
        elCtrl.generarCitas(forms);
    }
    
    public static void notificarCita( ArrayList<FormularioSolicitante> solicitantes){
        elCtrl.notificarCita(forms);
    }
    
    //Crea formularios con idSolicitante diferente
    public static FormularioSolicitante demoFormularios2(int idSolicitante){
        Random rand = new Random();
        String nombreSolic ="Solicitante "+idSolicitante;
        String correoSolic ="correo1000@gmail.com";
        String celularSolic ="8123-4567";
        String colegioSolic ="Colegio del Solicitante "+idSolicitante;
        DireccionPCD dirSolic = SingletonDAO.getInstance().getPCD(4); 
        String detalleDir = "Cerquita del Morazán";
        String carreraSolic = "IC";
        if(rand.nextInt(2) == 1){
            carreraSolic = "PI";
        }
        
        String sedeSolic = "CA";

        DTOFormulario elDTO = new  DTOFormulario(idSolicitante, nombreSolic, 
                                    correoSolic, celularSolic, colegioSolic, 
                                    dirSolic, detalleDir, carreraSolic, sedeSolic);
        
        elCtrl.registrarFormulario(elDTO);
        return elCtrl.getFormulario(idSolicitante);
    }
    
    public static void gestionExAdmision(){
        for(int i = 50;i<=100;i++){
            FormularioSolicitante form = elCtrl.getFormulario(i);
            elCtrl.simulacionAplicacionExamen(form.getIdSolic());
            //System.out.println("P obt: "+form.getDetalleExamen().getPuntajeObtenido());

           
            
        }
        
        mostrarResulPorCarrera("PI");
    }
    
    public static void mostrarResulPorCarrera(String carrera){
        ArrayList<FormularioSolicitante> resultados = elCtrl.getFormsPorCarrera_Solicitante(carrera,forms);
        System.out.println("Size: "+resultados.size());
        for (int i = 0; i < resultados.size(); i++) {
            System.out.println("Carrera: "+resultados.get(i).getCarreraSolic().getNombre() + 
                    "\n Solicitante: "+resultados.get(i).getIdSolic() + "---- Puntaje obtenido: "+resultados.get(i).getDetalleExamen().getPuntajeObtenido());
        }
        

    }


    /*
    * @author Andres
    * @param nombreCarrera string del nombre de la carrera que se desea ver los estados de los formularios
    * @dev Este metodo se encarga de mostrar los estados de los formularios de una carrera en especifico, ordenados por puntaje, primero salen los admitidos, despues los en espera y por ultimo los rechazados
    * */
    public static void verEstadosFormulariosCarrera(String codigoCarrera){
        ArrayList<FormularioSolicitante> formulariosCarrera = elCtrl.getFormsPorCarrera_Solicitante(codigoCarrera,forms);

        ArrayList<FormularioSolicitante> formulariosCarreraOrdenados = elCtrl.darEstadosFormulariosCarrera(formulariosCarrera);
        for(FormularioSolicitante f : formulariosCarreraOrdenados){
            System.out.println("Estado: "+f.getEstado());
            System.out.println("Puntaje: "+f.getDetalleExamen().getPuntajeObtenido());
            System.out.println("Puntaje Minimo: "+f.getCarreraSolic().getPuntajeMinimo());
            System.out.println("Cupo: "+f.getCarreraSolic().getMaxAdmision() + "\n");
            System.out.println("Formulario: "+f.getIdSolic());
            System.out.println("Carrera: "+f.getCarreraSolic().getNombre());
        }
    }
    
    
    public static void mostrarEstadoSolicitanteCarrera(int idSolicitante){
        FormularioSolicitante solicitante = elCtrl.getFormulario(idSolicitante);
        if (solicitante.getDetalleExamen().getPuntajeObtenido() != 0){
            System.out.println("Solicitante: " + solicitante.getNombreSolic());
            System.out.println("Carrera Solicitada : " + solicitante.getCarreraSolic().toString());
            System.out.println("Resultado de prueba: " + solicitante.getDetalleExamen().getPuntajeObtenido());
            System.out.println("Estado: " + solicitante.getEstado());
        }
        System.out.println("Aun no esta disponible");
    }

    public static void main(String[] args) {

        
        generarFormularios();
        System.out.println("Generar citas de admision:");
        generarCitasExamen(forms);
        notificarCita(forms);        
        
        System.out.println("En demoConfiguracion");
        //demoConfiguracion();
        System.out.println(elCtrl.getFormulario(1000));
    
        System.out.println("En demoCarreras");
        demoCarreras();
        
        System.out.println("En demoFormulario");
       // demoFormulario();
       
        System.out.println("\nEstado final solicitante en carrera pre-examen");
        mostrarEstadoSolicitanteCarrera(50);
        
        System.out.println("Gestion Examen Admision");
        gestionExAdmision();
        
        System.out.println("\nEstado final solicitante en carrera");
        mostrarEstadoSolicitanteCarrera(50);

        System.out.println("\n Ver estados de formularios de una carrera");
        verEstadosFormulariosCarrera("IC");
       
        
     }
    
}
