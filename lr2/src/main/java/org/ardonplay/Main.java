package org.ardonplay;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("""
                Symbols
                + — disjunction
                * — conjuction
                ~ — equivalence
                @ — implication
                ! — not""");
        Scanner stringScanner = new Scanner(System.in);
        System.out.print("Type an expression:");
        String expressionText = stringScanner.nextLine();

        LogicalExpressionSolver solver = new LogicalExpressionSolver(expressionText);

        System.out.println(solver);


    }


}
