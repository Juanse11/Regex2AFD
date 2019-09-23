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
    private boolean acceptingState;
    private ArrayList<Transition> transitions;

    public ArrayList<Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(ArrayList<Transition> transitions) {
        this.transitions = transitions;
    }

    public State(ArrayList<Node> U, int stateID) {
        this.positions = U;
        this.stateID = stateID;
    }

    public boolean isAcceptingState() {
        return acceptingState;
    }

    public void setAcceptingState(boolean acceptingState) {
        this.acceptingState = acceptingState;
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
