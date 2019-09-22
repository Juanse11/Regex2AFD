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

    Queue<State> statesDQueue = new LinkedList();
    ArrayList<State> statesD = new ArrayList();
    ;
    private ArrayList<ArrayList<Transition>> TranD = new ArrayList();

    public AFD() {
    }

    public void constructAFD(Node root, ArrayList<Character> symbols) {
        int stateID = 0;
        State A = new State(root.getFirstPos(), stateID);
        statesDQueue.add(A);
        statesD.add(A);
        while (!statesDQueue.isEmpty()) {
            State T = statesDQueue.poll();
            ArrayList<Transition> transitions = new ArrayList();
            for (Character a : symbols) {
                stateID = T.getStateID();
                ArrayList<Node> U = new ArrayList();
                for (Node p : T.getPositions()) {
                    if (p.getSymbol().equals(a.toString())) {
                        U = mergeArrayWithoutDuplicates(U, p.getFollowPos());
                    }
                }
                int UID = findUInStatesD(U);
                if (!U.isEmpty()) {
                    if (findUInStatesD(U) == -1) {
                        stateID = stateID + 1;
                        statesD.add(new State(U, stateID));
                        statesDQueue.add(new State(U, stateID));
                    } else {
                        stateID = UID;
                    }
                    transitions.add(new Transition(stateID, a));
                }else{
                    transitions.add(new Transition(-1, a));
                }


            }
            TranD.add(transitions);
        }
    }

    private int findUInStatesD(ArrayList<Node> U) {
        for (State s : statesD) {
            if (s.getPositions().containsAll(U)) {
                return statesD.indexOf(s);
            }
        }
        return -1;
    }

    public ArrayList<ArrayList<Transition>> getTranD() {
        return TranD;
    }

    public void setTranD(ArrayList<ArrayList<Transition>> TranD) {
        this.TranD = TranD;
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

}
