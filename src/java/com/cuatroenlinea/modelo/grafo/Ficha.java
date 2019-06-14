/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuatroenlinea.modelo.grafo;


import java.io.Serializable;

/**
 *
 * @author carloaiza
 */
public class Ficha implements Serializable{
   
    private String color;
    private String tablero;

    public Ficha(String color, String tablero) {
        this.color = color;
        this.tablero = tablero;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTablero() {
        return tablero;
    }

    public void setTablero(String tablero) {
        this.tablero = tablero;
    }



    
}
