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
public class State {

    private ArrayList<Node> positions = new ArrayList();
    private int stateID;

    public State(ArrayList<Node> U, int stateID) {
        this.positions = U;
        this.stateID = stateID;
    }

    public ArrayList<Node> getPositions() {
        return positions;
    }

    public void setPositions(ArrayList<Node> positions) {
        this.positions = positions;
    }

    
    public int getStateID() {
        return stateID;
    }

    public void setStateID(int stateID) {
        this.stateID = stateID;
    }

}
