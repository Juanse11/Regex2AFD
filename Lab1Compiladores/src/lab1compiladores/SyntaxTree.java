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

    Stack<Node> operandStack = new Stack();
    Stack<Character> operatorStack = new Stack();
    private ArrayList<Character> operators = new ArrayList<>();
    private ArrayList<Character> symbols = new ArrayList<>();
    private ArrayList<Node> leafNodes = new ArrayList<>();

    public SyntaxTree() {
    }

    public Node constructTree(String regex) {
        char[] regexCharSymbols = regex.toCharArray();
        getOperators();
        getSymbols(regexCharSymbols);
        regex = parseRegex(regex);
        System.out.println(regex);
        char[] regexCharArr = regex.toCharArray();

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
                id = id + 1;
                Node n = new Node(Character.toString(c), id);
                leafNodes.add(n);
                operandStack.push(n);
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
        operandStack.push(new Node(Character.toString('#'), id + 1));
        operatorStack.push('.');
        performOperation();
        return operandStack.pop();
    }

    public void traverseTree(Node node) {
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

                        for (Node n : leftChildNode.getLastPos()) {
                            n.setFollowPos(mergeArrayWithoutDuplicates(n.getFollowPos(), rightChildNode.getFirstPos()));
                        }
                        break;
                    case "+":
                        node.setIsNullable(false);
                        node.addToFirstPos(leftChildNode.getFirstPos());
                        node.addToLastPos(leftChildNode.getLastPos());

                        for (Node n : node.getLastPos()) {
                            n.setFollowPos(mergeArrayWithoutDuplicates(n.getFollowPos(), node.getFirstPos()));
                        }
                        break;
                    case "*":
                        node.setIsNullable(true);
                        node.addToFirstPos(leftChildNode.getFirstPos());
                        node.addToLastPos(leftChildNode.getLastPos());

                        for (Node n : node.getLastPos()) {
                            n.setFollowPos(mergeArrayWithoutDuplicates(n.getFollowPos(), node.getFirstPos()));
                        }
                        break;
                    case "?":
                        node.setIsNullable(true);
                        node.addToFirstPos(leftChildNode.getFirstPos());
                        node.addToLastPos(leftChildNode.getLastPos());
                }
            }
        }

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

    private void performOperation() {
        char op = operatorStack.pop();
        mergeNodes(Character.toString(op));

    }

    private void mergeNodes(String symbol) {
        Node root = new Node(symbol, 0);
        Node node2 = operandStack.pop();
        if (!symbol.equals("*") && !symbol.equals("+") && !symbol.equals("?")) {
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
        Character[] ops = {'(', ')', '*', '+', '?', '.', '|'};
        ArrayList<Character> opsList = new ArrayList();
        opsList.addAll(Arrays.asList(ops));
        return opsList.indexOf(first) >= opsList.indexOf(second) && !second.equals('(');
    }

    private String parseRegex(String regex) {
        Character[] options = {'*', '+', ')', '?'};
        ArrayList<Character> opList = new ArrayList();
        opList.addAll(Arrays.asList(options));

        for (int i = 0; i < regex.length() - 1; i++) {
            if ((opList.contains(regex.charAt(i)) || symbols.contains(regex.charAt(i)))
                    && (symbols.contains(regex.charAt(i + 1)) || regex.charAt(i + 1) == '(')) {
                regex = regex.substring(0, i + 1) + '.' + regex.substring(i + 1, regex.length());

            }
        }
        return regex;
    }

    private void getOperators() {
        Character[] ops = {'*', '+', '.', '?', '|'};
        operators.addAll(Arrays.asList(ops));
    }

    public void getSymbols(char[] regex) {
        for (Character c : regex) {
            if ((!operators.contains(c)) && !symbols.contains(c) && (c != '(') && (c != ')') && (c != '#')) {
                symbols.add(c);
            }
        }
    }

    public ArrayList<Character> getInputSymbols() {
        return this.symbols;
    }

    public ArrayList<Node> getLeafNodes() {
        return this.leafNodes;
    }

    private boolean isOperator(char c) {
        return operators.contains(c);
    }

}
