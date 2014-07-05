package net.sf.anathema.hero.concept.model.concept;

import net.sf.anathema.hero.individual.splat.SplatType;

public interface CasteCollection {

  boolean containsCasteType(String casteTypeId);

  CasteType[] getAllCasteTypes(SplatType template);

  CasteType getById(String casteTypeId);

  boolean isEmpty();
}