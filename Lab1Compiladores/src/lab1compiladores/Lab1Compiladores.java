/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab1compiladores;

import java.util.Scanner;

/**
 *
 * @author Visitante
 */
public class Lab1Compiladores {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese la expresión regular: ");
        String regex = sc.nextLine()+".#";
        SyntaxTree st = new SyntaxTree();
        Node root = st.constructTree(regex);
        SyntaxTree.printBinaryTree(root, 0);
    }
    
}
