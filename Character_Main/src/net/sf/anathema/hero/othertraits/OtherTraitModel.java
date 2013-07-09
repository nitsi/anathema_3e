package net.sf.anathema.hero.othertraits;

import net.sf.anathema.character.main.template.ITraitLimitation;
import net.sf.anathema.hero.traits.TraitMap;
import net.sf.anathema.hero.model.HeroModel;
import net.sf.anathema.lib.util.Identifier;
import net.sf.anathema.lib.util.SimpleIdentifier;

public interface OtherTraitModel extends TraitMap, HeroModel {

  Identifier ID = new SimpleIdentifier("OtherTraits");

  int getEssenceCap(boolean modified);

  ITraitLimitation getEssenceLimitation();
}