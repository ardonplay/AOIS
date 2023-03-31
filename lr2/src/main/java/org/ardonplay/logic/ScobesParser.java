package org.ardonplay.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class ScobesParser {
    public static List<String> getAllSymbols(LexemeBuffer lexemeBuffer) {
        return lexemeBuffer.lexemes.stream().filter(lexeme -> lexeme.symbol != null)
                .map(lexeme -> lexeme.symbol.name).toList();
    }

    public static Map<Integer, Integer> getAllScobes(String expression) {
        Stack<Integer> stack = new Stack<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '!' && expression.charAt(i + 1) == '(') {
                stack.push(i);
            } else if (expression.charAt(i) == '(') {
                if (i - 1 >= 0 && expression.charAt(i - 1) == '!') {
                } else {
                    stack.push(i);
                }
            } else if (expression.charAt(i) == ')') {

                map.put(stack.pop(), i + 1);

            }
        }
        return map;
    }

}
