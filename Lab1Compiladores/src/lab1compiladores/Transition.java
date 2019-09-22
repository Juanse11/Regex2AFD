/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab1compiladores;

import java.util.ArrayList;

/**
 *
 * @author juan-
 */
public class Transition {

    private int stateID;
    private Character symbol;
    
    
    public Transition(int stateID, Character symbol) {
        this.stateID = stateID;
        this.symbol = symbol;
    }

    public int getStateID() {
        return stateID;
    }

    public void setStateID(int stateID) {
        this.stateID = stateID;
    }

    public Character getSymbol() {
        return symbol;
    }

    public void setSymbol(Character symbol) {
        this.symbol = symbol;
    }

}
