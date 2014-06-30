package net.sf.anathema.hero.charms.model;

import net.sf.anathema.hero.magic.charm.Charm;
import net.sf.anathema.hero.framework.type.CharacterType;
import net.sf.anathema.lib.util.Identifier;

public interface ICharmGroup extends Identifier {

  Charm[] getAllCharms();

  CharacterType getCharacterType();

  boolean isMartialArtsGroup();

  boolean isCharmFromGroup(Charm charm);
}