package org.ardonplay.logic;

import java.util.List;

public class LexemeBuffer {

  private int pos;

  public List<Lexeme> lexemes;

  public LexemeBuffer(List<Lexeme> lexemes) {
    this.lexemes = lexemes;
  }

  public Lexeme next() {
    return lexemes.get(pos++);
  }

  public void back() {
    pos--;
  }

  public int getPos() {
    return pos;
  }

  public void setPos(int pos) {
    this.pos = pos;
  }
}
