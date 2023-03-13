/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.DAO;

import java.util.*;

import model.*;

/**
 *
 * @author ersolano
 */
public class SingletonDAO {
    private static SingletonDAO instance;



    private  ArrayList<Carrera> tablaCarreras = new ArrayList();
    private  ArrayList<Sede> tablaSedes = new ArrayList(); 
    private  ArrayList<CentroAplicacion> tablaCentros = new ArrayList(); 
    private  ArrayList<DireccionPCD> tablaPCD = new ArrayList(); 
    private  ArrayList<FormularioSolicitante> tablaFormularios = new ArrayList();
    
    // temporalmente hay una representación de datos que estan en una BD 
    // simulando el acceso a una instancia del modelo de datos persistente
    private SingletonDAO() {
        tablaSedes.add(new Sede("CA","Cartago"));       //tablaSedes.get(0)
        tablaSedes.add(new Sede("SJ","San Jose"));      //tablaSedes.get(1)
        tablaSedes.add(new Sede("LI","Limon"));         //tablaSedes.get(2)
        tablaSedes.add(new Sede("SC","San Carlos"));    //tablaSedes.get(3)
        tablaSedes.add(new Sede("AL","Alajuela"));      //tablaSedes.get(4)

        //carreras de Cartago
        tablaCarreras.add(new Carrera("IC","Ingenieria en Computacion",tablaSedes.get(0), TGrado.BACHILLERATO, 140, 650));
        tablaCarreras.add(new Carrera("PI","Ingenieria en Produccion Industrial",tablaSedes.get(0), TGrado.LICENCIATURA, 100, 620));
        
        // carreras de San Jose
        tablaCarreras.add(new Carrera("IC","Ingenieria en Computacion",tablaSedes.get(1), TGrado.BACHILLERATO, 140, 650));
        
        // carreras de Limon
        tablaCarreras.add(new Carrera("PI","Ingenieria en Produccion Industrial",tablaSedes.get(2), TGrado.LICENCIATURA, 40, 520));
                        
        // direcciones PCD
        tablaPCD.add(new DireccionPCD("Cartago", "Central", "Oriental"));       //0
        tablaPCD.add(new DireccionPCD("Cartago", "Central", "Agua Caliente"));  //1
        tablaPCD.add(new DireccionPCD("Cartago", "Paraiso", "Santiago"));       //2
        tablaPCD.add(new DireccionPCD("Cartago", "Paraiso", "Orosi"));          //3
        tablaPCD.add(new DireccionPCD("San José", "Central", "Carmen"));        //4
        tablaPCD.add(new DireccionPCD("San José", "Central", "Hospital"));      //5
        tablaPCD.add(new DireccionPCD("San José", "Central", "Merced"));        //6
        
        // centros de aplicación de examen
        tablaCentros.add(new CentroAplicacion(100, "Colegio Agua Caliente", tablaPCD.get(1), "Colegio Académico"));
        tablaCentros.add(new CentroAplicacion(200, "TEC Campus Central", tablaPCD.get(0), "Campus Central ITCR"));
        tablaCentros.add(new CentroAplicacion(100, "TEC Campus Local SJ", tablaPCD.get(4), "CTLSJ"));
        
        }
    
    public static SingletonDAO getInstance(){
        if (instance == null)
            instance = new SingletonDAO();
        return instance;
    }
    
    public List<Carrera> getCarreras(){
        // pendiente: conectar a la persistencia y recuperar las carreras
  
        return tablaCarreras;
    }
    
    public List<Sede> getSedes(){
        // pendiente: conectar a la persistencia y recuperar las sedes
        List<Sede> sedes =new ArrayList();
        for (Iterator<Sede> it = tablaSedes.iterator(); it.hasNext();) {
            Sede sede = it.next();
            sedes.add(sede);
        }
        return sedes;
    }
    
    public List<Carrera> consultarCarrerasdeUnaSede (String unaSede){
        List<Carrera> carrerasSede =new ArrayList();
        for (Iterator<Carrera> it = tablaCarreras.iterator(); it.hasNext();) {
            Carrera carrera = it.next();
            if (carrera.getSede().getCodigo().equals(unaSede))
                carrerasSede.add(carrera);
        }
       return carrerasSede;
    }
    
    public Carrera consultarUnaCarrera (String codigoCarrera, String codigoSede){
        for (Carrera unaCarrera : tablaCarreras) {
            if ( unaCarrera.getCodigo().equals(codigoCarrera) && unaCarrera.getSede().getCodigo().equals(codigoSede) )
                return unaCarrera;
        }
        return null;
    }
    
    public boolean editarCarrera(Carrera unaCarrera){
        for (int i=0; i< tablaCarreras.size(); i++) {
            Carrera actual = tablaCarreras.get(i);
            if (actual.getCodigo().equals(unaCarrera.getCodigo()) && 
                actual.getSede().getCodigo().equals(unaCarrera.getSede().getCodigo())){
                tablaCarreras.set(i, unaCarrera);
                return true;
            }
        }
        return false;
    }

    public DireccionPCD getPCD(int pos){
        if (pos >= 0 && pos < tablaPCD.size())
            return tablaPCD.get(pos);
        else
            return null;
    }
    
    public FormularioSolicitante consultarFormulario(int idSolic){
        for (FormularioSolicitante form : tablaFormularios) {
            if (form.getIdSolic() == idSolic)
                return form;
        }
        return null;
    }


    /*
     * @author Andres
     * @dev Este metodo ordena todos los formularios de tablaFormularios mayor a menor por nota
     * */
    public void ordenarTablaFormularios(){
        Collections.sort(tablaFormularios);  // Ordena los formularios de mayor a menor por nota
    }

    /*
    * @author Andres
    * @param Carrera carrera: La carrera a la que se le desean buscar los formularios
    * @ret Un arraylist de enteros con los indices de todos los formularios asociados a la carrera
    * @dev Este metodo busca en la tabla de formularios todos los asociados a una carrera y les pone estado segun el corte y campos
    * */
    public ArrayList<FormularioSolicitante> darEstadosFormulariosCarrera(String nombreCarrera){
        // Nos dan el nombre de la carrera pero ocupamos es el objeto carrera
        Carrera carrera = null;
        for (Carrera c : tablaCarreras) {
            if (c.getNombre().equals(nombreCarrera)) {
                carrera = c;
                break;
            }
        }

        // Se debe ordenar primero para asegurar que primero se consideren las mejores notas
        ordenarTablaFormularios();

        // Lista de formularios de la carrera
        ArrayList<FormularioSolicitante> posiciones = new ArrayList();

        int aceptados = 0;  // Para saber cuando se supera el cupo
        int length = tablaFormularios.size();  // Para evitar calcularlo una y otra vez cada vez que se evalua la conidcion del for
        for (int i = 0; i < length; i++) { // Recorre la tabla de formularios
            if (tablaFormularios.get(i).getCarreraSolic().equals(carrera)) {// Revisa si el formulario es de la carrera
                posiciones.add(tablaFormularios.get(i)); // Agrega ese formulario a la lista que se va a retornar

                // Revisa que condicion le puede asignar
                if (tablaFormularios.get(i).getDetalleExamen().getPuntajeObtenido() < carrera.getPuntajeMinimo()) {
                    tablaFormularios.get(i).setEstado(TEstadoSolicitante.RECHAZADO);  // No le dio la nota minima, queda fuera
                } else {
                    // Obtuvo la nota minima
                    if(aceptados < carrera.getMaxAdmision()){ // se revisan los campos para ver si esta admitido o en espera
                        tablaFormularios.get(i).setEstado(TEstadoSolicitante.ADMITIDO);  // como viene ordenado, primero se revisan los que tienen mejor nota
                        aceptados++;
                    } else
                        tablaFormularios.get(i).setEstado(TEstadoSolicitante.CANDIDATO);
                }
            }
        }

        return posiciones; // Retornamos la lista de formularios de la carrera
    }


    
       public List obtenerFormulariosSolicitantes(){
        List<FormularioSolicitante> formularios = new ArrayList<FormularioSolicitante>();
        for (FormularioSolicitante form : tablaFormularios) {
            formularios.add(form);
        }
        return formularios;
    }
    
    public boolean agregarFormulario(FormularioSolicitante unFormulario){
        for (FormularioSolicitante form : tablaFormularios) {
            if (form.getNumero()== unFormulario.getNumero())
                return false;
        }
        tablaFormularios.add(unFormulario);
        return true;
    }
    
}
