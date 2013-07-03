package net.sf.anathema.character.generic.framework.xml.essence;

import net.sf.anathema.character.generic.template.essence.FactorizedTrait;
import net.sf.anathema.character.generic.traits.ValuedTraitType;
import net.sf.anathema.character.generic.traits.types.VirtueType;

public class TypeVirtuePoolPart implements IVirtuePoolPart {

  private final VirtueType virtueType;
  private final int multiplier;

  public TypeVirtuePoolPart(VirtueType virtueType, int multiplier) {
    this.virtueType = virtueType;
    this.multiplier = multiplier;
  }

  @Override
  public FactorizedTrait createFactorizedTrait(ValuedTraitType[] virtues) {
    return new FactorizedTrait(getVirtue(virtues), multiplier);
  }

  private ValuedTraitType getVirtue(ValuedTraitType[] traits) {
    for (ValuedTraitType trait : traits) {
      if (trait.getType() == virtueType) {
        return trait;
      }
    }
    throw new IllegalArgumentException("No trait given for type " + virtueType);
  }
}