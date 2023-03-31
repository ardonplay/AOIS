package org.ardonplay.logic;

import java.util.Objects;

public class Symbol {

  public int value;

  public String name;

  {
    value = 0;
  }

  public Symbol(String name) {
    this.name = name;
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
