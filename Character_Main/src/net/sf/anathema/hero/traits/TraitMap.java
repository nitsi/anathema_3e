package net.sf.anathema.hero.traits;

import net.sf.anathema.character.main.traits.TraitType;
import net.sf.anathema.character.main.library.trait.Trait;

public interface TraitMap {

  Trait getTrait(TraitType traitType);

  Trait[] getTraits(TraitType... traitType);

  Trait[] getAll();
}