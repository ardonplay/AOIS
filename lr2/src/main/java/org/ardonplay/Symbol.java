package org.ardonplay;

import java.util.Objects;

public class Symbol {

  int value;

  String name;

  {
    value = 0;
  }

  public Symbol(String name, int value) {
    this.name = name;
    this.value = value;
  }

  public Symbol(String name) {
    this.name = name;
  }


  public Symbol(char name) {
    this.name = String.valueOf(name);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Symbol symbol = (Symbol) o;
    return value == symbol.value && Objects.equals(name, symbol.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, name);
  }
}
