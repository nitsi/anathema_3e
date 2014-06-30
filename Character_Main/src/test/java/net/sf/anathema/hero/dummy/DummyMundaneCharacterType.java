package net.sf.anathema.hero.dummy;

import net.sf.anathema.hero.framework.type.CharacterType;

public class DummyMundaneCharacterType implements CharacterType {

  @Override
  public boolean isExaltType() {
    return false;
  }

  @Override
  public boolean isEssenceUser() {
    return false;
  }

  @Override
  public String getId() {
    return "Dummy";
  }

  public boolean equals(Object other) {
    return other instanceof DummyMundaneCharacterType;
  }
}