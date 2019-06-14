/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuatroenlinea.controlador;

import com.cuatroenlinea.controlador.util.JsfUtil;
import com.cuatroenlinea.modelo.grafo.Arista;
import com.cuatroenlinea.modelo.grafo.Ficha;
import com.cuatroenlinea.modelo.grafo.Grafo;
import com.cuatroenlinea.modelo.grafo.Vertice;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.DiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.connector.StraightConnector;
import org.primefaces.model.diagram.endpoint.BlankEndPoint;
import org.primefaces.model.diagram.endpoint.DotEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;

/**
 *
 * @author USUARIO
 */
@Named(value = "controladorCuatroEnLinea")
@ApplicationScoped
public class ControladorCuatroEnLinea implements Serializable {

    private int altoFichas = 6;
    private int altoIntermedio = 7;    
    private int ancho = 7;

    public boolean isEstadoJuego() {
        return estadoJuego;
    }

    public void setEstadoJuego(boolean estadoJuego) {
        this.estadoJuego = estadoJuego;
    }
    private int alto = ancho-1;
    private int total = alto*ancho;
    private byte distancia = 4;
    private DefaultDiagramModel model;
    private Grafo tablero = new Grafo();
    private int fichaClick;
    private Vertice vertice;
    private boolean estadoJuego;
    private Date fecha;

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Grafo getTablero() {
        return tablero;
    }

    public void setTablero(Grafo tablero) {
        this.tablero = tablero;
    }

    public Vertice getVertice() {
        return vertice;
    }

    public void setVertice(Vertice vertice) {
        this.vertice = vertice;
    }

    public Date getFecha() {
        return new Date();
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    
    public ControladorCuatroEnLinea() {
    }

   public void llenarAristas() {
        
        //Crear aristas
        for(int i=1;i<=6;i++){ 
        for (Vertice vert: tablero.traerTablero("t"+i)) { 
            if(vert.getId() % ancho != 0){
                tablero.adicionarArista(vert.getId(), vert.getId() + 1, 2);
            }
            if(vert.getId() + ancho <= (total*i)){                    
                tablero.adicionarArista(vert.getId(), vert.getId() + ancho, 1);                                
                        
                if(vert.getId() % ancho != 0 ){
                    tablero.adicionarArista(vert.getId(), vert.getId() + ancho + 1, 1);
                    tablero.adicionarArista(vert.getId() + 1, vert.getId() + ancho, 1);
                }
            }
            }
        }
    }

  @PostConstruct
    public void pintarTablero() {

        model = new DefaultDiagramModel();
        model.setMaxConnections(-1);
        model.setConnectionsDetachable(false);
        int pos = 2;
        for(int t=0; t<6; t++){
        int x = pos;
        int y = 5;
        String color = "";
        String styleColor="ui-diagram-element-ficha-blanco";
        for (int i = 1; i <= alto; i++ ) {
            for (int j = 1 ; j <= ancho ; j++) {
                tablero.adicionarVertice(new Ficha(color, "t"+(t+1)));
                Element ceo = new Element(tablero.getVertices().size(), x + "em", y + "em");
                ceo.setDraggable(false);
                ceo.setStyleClass(styleColor);
                ceo.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM));
                    ceo.addEndPoint(new BlankEndPoint(EndPointAnchor.TOP));
                    ceo.addEndPoint(new BlankEndPoint(EndPointAnchor.LEFT));
                    ceo.addEndPoint(new BlankEndPoint(EndPointAnchor.RIGHT));
                model.addElement(ceo);
                x = x + 5;
            }
            y = y + 5;
            x = pos;
        }
        pos = pos +40;
        }
       llenarAristas();
        StraightConnector connector = new StraightConnector();
        connector.setPaintStyle("{strokeStyle:'#404a4e', lineWidth:3}");
        connector.setHoverPaintStyle("{strokeStyle:'#20282b'}");
        model.setDefaultConnector(connector);

        //recorrer aristas
        for (Arista arista : tablero.getAristas()) {
            Element origen = model.getElements().get(arista.getOrigen() - 1);
            Element destino = model.getElements().get(arista.getDestino() - 1);
             switch(arista.getPeso()){
                case 1:
                    model.connect(new Connection(origen.getEndPoints().get(0), destino.getEndPoints().get(1)));
                break;
                case 2:
                    model.connect(new Connection(origen.getEndPoints().get(3), destino.getEndPoints().get(2)));
                break;
            }          
        }
          
        
    }
    
    public void activarJuego(){
        estadoJuego = true;
        JsfUtil.addErrorMessage("Juego Habilitado");
    }
    
    public void onClickRight(){
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("elementId");

      
        fichaClick = vertice.getId();
       
    }
      
    public void pruebaMenu(){
        JsfUtil.addSuccessMessage(fichaClick + " presionada" );
    }
 

    public int getAltoFichas() {
        return altoFichas;
    }

    public void setAltoFichas(int altoFichas) {
        this.altoFichas = altoFichas;
    }

    public int getAltoIntermedio() {
        return altoIntermedio;
    }

    public void setAltoIntermedio(int altoIntermedio) {
        this.altoIntermedio = altoIntermedio;
    }

    public byte getDistancia() {
        return distancia;
    }

    public void setDistancia(byte distancia) {
        this.distancia = distancia;
    }

    public DefaultDiagramModel getModel() {
        return model;
    }

    public void setModel(DefaultDiagramModel model) {
        this.model = model;
    }

  

}
