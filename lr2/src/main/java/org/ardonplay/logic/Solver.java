package org.ardonplay.logic;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Solver {

  public static List<String> getSymbols(LexemeBuffer lexemeBuffer) {
    return lexemeBuffer.lexemes.stream().filter(lexeme -> lexeme.symbol != null)
        .map(lexeme -> lexeme.symbol.name).toList();
  }
  public static Map<String, Symbol> symbols = new HashMap<>();


  public static List<Lexeme> lexAnalyze(String expText) {
    List<Lexeme> lexemes = new ArrayList<>();
    int pos = 0;
    while (pos < expText.length()) {
      char c = expText.charAt(pos);
      switch (c) {
        case '(' -> {
          lexemes.add(new Lexeme(LexemeType.LEFT_BRACKET, c));
          pos++;
        }
        case ')' -> {
          lexemes.add(new Lexeme(LexemeType.RIGHT_BRACKET, c));
          pos++;
        }
        case '!' -> {
          lexemes.add(new Lexeme(LexemeType.OP_NOT, c));
          pos++;
        }
        case '+' -> {
          lexemes.add(new Lexeme(LexemeType.OP_DIS, c));
          pos++;
        }
        case '@' -> {
          lexemes.add(new Lexeme(LexemeType.OP_IMPL, c));
          pos++;
        }
        case '~' -> {
          lexemes.add(new Lexeme(LexemeType.OP_EQ, c));
          pos++;
        }
        case '*' -> {
          lexemes.add(new Lexeme(LexemeType.OP_CON, c));
          pos++;
        }
        default -> {
          if (c <= 'Z' && c >= 'A') {
            StringBuilder symbol = new StringBuilder();
            do {
              symbol.append(c);
              pos++;
              if (pos >= expText.length()) {
                break;
              }
              c = expText.charAt(pos);
            } while (c <= 'Z' && c >= 'A');
            symbols.put(String.valueOf(symbol), new Symbol(String.valueOf(symbol)));
            lexemes.add(new Lexeme(LexemeType.SYMBOL, symbols.get(String.valueOf(symbol))));
          } else {
            if (c != ' ') {
              throw new RuntimeException("Unexpected character: " + c);
            }
            pos++;
          }
        }
      }
    }
    lexemes.add(new Lexeme(LexemeType.EOF, ""));
    return lexemes;
  }

  public static int expr(LexemeBuffer lexemes) {
    Lexeme lexeme = lexemes.next();
    if (lexeme.type == LexemeType.EOF) {
      return 0;
    } else {
      lexemes.back();
      return solve(lexemes);
    }
  }

  public static int solve(LexemeBuffer lexemes) {
    int value = factor(lexemes);
    while (true) {
      Lexeme lexeme = lexemes.next();
      switch (lexeme.type) {
        case OP_DIS -> value = LogicalOperations.disjunction(value, factor(lexemes));
        case OP_CON -> value = LogicalOperations.conjunction(value, factor(lexemes));
        case OP_EQ -> value = LogicalOperations.equivalence(value, factor(lexemes));
        case OP_IMPL -> value = LogicalOperations.implication(value, factor(lexemes));
        case EOF, RIGHT_BRACKET -> {
          lexemes.back();
          return value;
        }
        default -> throw new RuntimeException("Unexpected token: " + lexeme.value
            + " at position: " + lexemes.getPos());
      }
    }
  }


  public static int factor(LexemeBuffer lexemes) {
    Lexeme lexeme = lexemes.next();
    switch (lexeme.type) {
      case SYMBOL -> {
        return lexeme.symbol.value;
      }
      case OP_NOT -> {
        lexeme = lexemes.next();
        if (lexeme.type == LexemeType.SYMBOL) {
          return LogicalOperations.not(lexeme.symbol.value);
        } else {
          return LogicalOperations.not(solve(lexemes));
        }
      }
      case LEFT_BRACKET -> {
        int value = solve(lexemes);
        lexeme = lexemes.next();
        if (lexeme.type != LexemeType.RIGHT_BRACKET) {
          throw new RuntimeException("Unexpected token: " + lexeme.value
              + " at position: " + lexemes.getPos());
        }
        return value;
      }
      default -> throw new RuntimeException("Unexpected token: " + lexeme.value
          + " at position: " + lexemes.getPos());
    }
  }
}
