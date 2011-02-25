package net.sf.anathema.character.generic.framework.xml.trait.alternate;

import net.sf.anathema.character.generic.character.ILimitationContext;
import net.sf.anathema.character.generic.traits.ITraitType;

public interface IMinimumRestriction {

  public boolean isFullfilledWithout(ILimitationContext context, ITraitType traitType);
  
  public void clear();

  public int getStrictMinimumValue();

  public void addTraitType(ITraitType type);
}