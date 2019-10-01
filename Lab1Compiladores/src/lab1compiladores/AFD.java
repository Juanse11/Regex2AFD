/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab1compiladores;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author juan-
 */
public class AFD {

    private Queue<State> statesDQueue = new LinkedList();

    private ArrayList<State> TranD = new ArrayList();

    public AFD() {
    }

    public void constructAFD(Node root, ArrayList<Character> symbols) {
        int stateID = 0;
        State A = new State(root.getFirstPos(), stateID);
        statesDQueue.add(A);
        A.setAcceptingState(isUAcceptingState(root.getFirstPos()));
        TranD.add(A);
        int id = 0;
        while (!statesDQueue.isEmpty()) {
            State T = statesDQueue.poll();
            ArrayList<Transition> transitions = new ArrayList();
            for (Character a : symbols) {
                
                ArrayList<Node> U = new ArrayList();
                boolean acceptingState = false;
                for (Node p : T.getPositions()) {
                    if (p.getSymbol().equals(a.toString())) {
                        U = mergeArrayWithoutDuplicates(U, p.getFollowPos());
                    }
                }
                int UID = findUInStatesD(U);
                acceptingState = isUAcceptingState(U);
                if (!U.isEmpty()) {
                    if (findUInStatesD(U) == -1) {
                        stateID = stateID + 1;
                        State s = new State(U, stateID);
                        s.setAcceptingState(acceptingState);
                        TranD.add(s);
                        statesDQueue.add(s);
                    } else {
                        stateID = UID;
                    }
                    transitions.add(new Transition(stateID, a));
                } else {
                    transitions.add(null);
                }

            }
            TranD.get(id).setTransitions(transitions);
            id++;
        }
    }

    private boolean isUAcceptingState(ArrayList<Node> U) {
        for (Node n : U) {
            if (n.getSymbol().equals("#")) {
                return true;
            }
        }
        return false;
    }

    private int findUInStatesD(ArrayList<Node> U) {
        for (State s : TranD) {
            if (s.getPositions().equals(U)) {
                return TranD.indexOf(s);
            }
        }
        return -1;
    }

    public boolean recognizeString(String word, ArrayList<State> TranD, ArrayList<Character> symbols) {
        State currentState = TranD.get(0);
        ArrayList<Transition> stateTransitions = new ArrayList();
        for (int i = 0; i < word.length(); i++) {
            Character currentChar = word.charAt(i);
            stateTransitions = TranD.get(currentState.getStateID()).getTransitions();
            int index = indexOfTransition(stateTransitions, currentChar);
            if (!symbols.contains(currentChar) || isTransitionsNull(stateTransitions) || index == -1) {
                return false;
            }
            currentState = TranD.get(stateTransitions.get(index).getStateID());
        }
        return currentState.isAcceptingState();
    }

    private boolean isTransitionsNull(ArrayList<Transition> transitions) {
        for (Transition t : transitions) {
            if (t != null) {
                return false;
            }
        }
        return true;
    }

    private int indexOfTransition(ArrayList<Transition> transitions, Character c) {
        for (int i = 0; i < transitions.size(); i++) {
            if (transitions.get(i) != null && transitions.get(i).getSymbol().equals(c)) {
                return i;
            }
        }
        return -1;
    }

    private ArrayList<Node> mergeArrayWithoutDuplicates(ArrayList<Node> arr1, ArrayList<Node> arr2) {
        ArrayList<Node> arr3 = (ArrayList<Node>) arr1.clone();
        for (Node n : arr2) {
            if (!arr3.contains(n)) {
                arr3.add(n);
            }
        }
        return arr3;
    }

    public ArrayList<State> getTranD() {
        return TranD;
    }

    public void setTranD(ArrayList<State> statesD) {
        this.TranD = statesD;
    }

}
