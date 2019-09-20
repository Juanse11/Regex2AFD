/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab1compiladores;

import java.util.Arrays;
import java.util.*;

/**
 *
 * @author Visitante
 */
public class SyntaxTree {

    public SyntaxTree() {
    }
    Stack<Node> operandStack = new Stack();
    Stack<Character> operatorStack = new Stack();
    private ArrayList<Character> operators = new ArrayList<>();
    private ArrayList<Character> symbols = new ArrayList<>();

    public Node constructTree(String regex) {
        char[] regexCharArr = regex.toCharArray();
        getOperators();
        getSymbols(regexCharArr);

        int id = 0;
        for (char c : regexCharArr) {
            if (c == '(') {
                operatorStack.push(c);
            } else if (c == ')') {
                while (operatorStack.get(operatorStack.size() - 1) != '(') {
                    performOperation();
                }
                operatorStack.pop();
            } else if (!isOperator(c)) {
                id = id+1;
                Node t = new Node(Character.toString(c), id);
                operandStack.push(t);
            } else if (isOperator(c)) {
                while (!operatorStack.isEmpty() && checkPriority(c, operatorStack.get(operatorStack.size() - 1))) {
                    performOperation();
                }
                operatorStack.push(c);

            }
        }
        while (!operatorStack.isEmpty()) {
            performOperation();
        }
        Node tree = operandStack.pop();
        return tree;
    }

    public  void traverseTree(Node node) {
        if (node == null) {
            return;
        } else {
            traverseTree(node.getLeftNode());
            traverseTree(node.getRightNode());
            if (node.getLeftNode() == null && node.getRightNode() == null) {
                node.setIsNullable(false);
                ArrayList<Node> currentFirstPos = new ArrayList<Node>();
                currentFirstPos.add(node);
                node.addToFirstPos(currentFirstPos);
                ArrayList<Node> currentLastPos = new ArrayList<Node>();
                currentLastPos.add(node);
                node.addToLastPos(currentLastPos);
            } else {
                Node leftChildNode = node.getLeftNode();
                Node rightChildNode = node.getRightNode();
                ArrayList<Node> currentFirstPos = new ArrayList();
                ArrayList<Node> currentLastPos = new ArrayList();
                switch (node.getSymbol()) {

                    case "|":
                        node.setIsNullable(leftChildNode.isNullable() || rightChildNode.isNullable());
                        currentFirstPos = mergeArrayWithoutDuplicates(leftChildNode.getFirstPos(), rightChildNode.getFirstPos());
                        node.addToFirstPos(currentFirstPos);
                        currentLastPos = mergeArrayWithoutDuplicates(leftChildNode.getLastPos(), rightChildNode.getLastPos());
                        node.addToLastPos(currentLastPos);
                        break;
                    case ".":
                        node.setIsNullable(leftChildNode.isNullable() && rightChildNode.isNullable());

                        if (leftChildNode.isNullable()) {
                            currentFirstPos = mergeArrayWithoutDuplicates(leftChildNode.getFirstPos(), rightChildNode.getFirstPos());
                            node.addToFirstPos(currentFirstPos);
                        } else {
                            node.addToFirstPos(leftChildNode.getFirstPos());
                        }
                        if (rightChildNode.isNullable()) {
                            currentLastPos = mergeArrayWithoutDuplicates(leftChildNode.getLastPos(), rightChildNode.getLastPos());
                            node.addToLastPos(currentLastPos);
                        } else {
                            node.addToLastPos(rightChildNode.getLastPos());
                        }
                        break;
                    case "+":

                        break;
                    case "*":
                        node.setIsNullable(true);
                        node.addToFirstPos(leftChildNode.getFirstPos());
                        node.addToLastPos(leftChildNode.getLastPos());
                        break;
                }
            }
        }

    }
    
    private void setFirstPos(){
        
    }

    private ArrayList<Node> mergeArrayWithoutDuplicates(ArrayList<Node> arr1, ArrayList<Node> arr2) {
        ArrayList<Node> arr3 = arr1;
        for (Node n : arr2) {
            if (!arr3.contains(n)) {
                arr3.add(n);
            }
        }
        return arr3;
    }

    public void printBinaryTree(Node root, int level) {
        if (root == null) {
            return;
        }
        printBinaryTree(root.getRightNode(), level + 1);
        if (level != 0) {
            for (int i = 0; i < level - 1; i++) {
                System.out.print("|\t");
            }
            System.out.println("|-------" + root.getSymbol());
        } else {
            System.out.println(root.getSymbol());
        }
        printBinaryTree(root.getLeftNode(), level + 1);
    }

    private void performOperation() {
        char op = operatorStack.pop();
        mergeNodes(Character.toString(op));

    }

    private void mergeNodes(String symbol) {
        Node root = new Node(symbol, 0);
        Node node2 = operandStack.pop();
        if (!symbol.equals("*") && !symbol.equals("+")) {
            Node node1 = operandStack.pop();
            root.setLeftNode(node1);
            root.setRightNode(node2);
            node1.setParentNode(root);
            node2.setParentNode(root);
        } else {
            root.setLeftNode(node2);
            root.setRightNode(null);
            node2.setParentNode(root);
        }
        operandStack.push(root);
    }

    private boolean checkPriority(char first, Character second) {
        if (first == second) {
            return true;
        }
        if (first == '*') {
            return false;
        }
        if (second == '*') {
            return true;
        }
        if (first == '.') {
            return false;
        }
        if (second == '.') {
            return true;
        }
        if (first == '|') {
            return false;
        }
        return true;
    }

    private void getOperators() {
        Character[] ops = {'*', '|', '+', '?', '.'};
        operators.addAll(Arrays.asList(ops));
    }

    private void getSymbols(char[] regex) {
        for (Character c : regex) {
            if ((!operators.contains(c)) && !symbols.contains(c) && (c != '(') && (c != ')')) {
                symbols.add(c);
            }
        }
    }

    private boolean isOperator(char c) {
        return operators.contains(c);
    }

}
