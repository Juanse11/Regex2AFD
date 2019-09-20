/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab1compiladores;

import java.util.ArrayList;

/**
 *
 * @author Visitante
 */
public class Node {
    private Node parentNode;
    private Node rightNode;
    private Node leftNode;
    private int nodeID;
    private String symbol;
    private boolean isNullable;
    private ArrayList<Node> firstPos;
    private ArrayList<Node> lastPos;
    private ArrayList<Node> followPos;
    
    
    
    public Node(String symbol, int nodeID) {
        this.nodeID = nodeID;
        this.symbol = symbol;
        this.firstPos = new ArrayList();
        this.lastPos = new ArrayList();
        this.followPos = new ArrayList();
    }

    public Node getParentNode() {
        return parentNode;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }

    public Node getRightNode() {
        return rightNode;
    }

    public void setRightNode(Node rightNode) {
        this.rightNode = rightNode;
    }

    public Node getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(Node leftNode) {
        this.leftNode = leftNode;
    }

    public int getNodeID() {
        return nodeID;
    }

    public void setNodeID(int nodeID) {
        this.nodeID = nodeID;
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public void setIsNullable(boolean isNullable) {
        this.isNullable = isNullable;
    }

    public ArrayList<Node> getFirstPos() {
        return firstPos;
    }

    public void setFirstPos(ArrayList<Node> firstPos) {
        this.firstPos = firstPos;
    }

    public ArrayList<Node> getLastPos() {
        return lastPos;
    }

    public void setLastPos(ArrayList<Node> lastPos) {
        this.lastPos = lastPos;
    }

    public ArrayList<Node> getFollowPos() {
        return followPos;
    }

    public void setFollowPos(ArrayList<Node> followPos) {
        this.followPos = followPos;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public void addToFirstPos(ArrayList<Node> node){
        this.firstPos.addAll(node);
    }
    public void addToLastPos(ArrayList<Node> node){
        this.lastPos.addAll(node);
    }
    public void addToFollowPos(ArrayList<Node> node){
        this.followPos.addAll(node);
    }
    
    
}
