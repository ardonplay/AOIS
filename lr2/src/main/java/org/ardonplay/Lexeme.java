package org.ardonplay;

import java.util.function.Supplier;

public class Lexeme {
  LexemeType type;
  String value;

  Symbol symbol;

  {
    symbol = null;
  }

  public Lexeme(LexemeType type, String value) {
    this.type = type;
    this.value = value;
  }

  public Lexeme(LexemeType type, Symbol symbol){
    this.type = type;
    this.symbol = symbol;
  }


  public Lexeme(LexemeType type, Character value) {
    this.type = type;
    this.value = value.toString();
  }


  @Override
  public String toString() {
    String output = "Lexeme{" +
        "type=" + type +
        ", value='";

    if (symbol != null){
      output += symbol.value + " " + symbol.name;
    }
    else {
      output += value + '\'';
    }
    return output + '}';
  }
}
