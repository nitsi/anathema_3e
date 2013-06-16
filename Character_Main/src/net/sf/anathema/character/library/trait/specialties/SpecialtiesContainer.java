package net.sf.anathema.character.library.trait.specialties;

import net.sf.anathema.character.generic.framework.ITraitReference;
import net.sf.anathema.character.generic.framework.additionaltemplate.model.ITraitContext;
import net.sf.anathema.character.library.trait.subtrait.AbstractSubTraitContainer;

public class SpecialtiesContainer extends AbstractSubTraitContainer {

  public static final int ALLOWED_SPECIALTY_COUNT = 3;
  private final ITraitReference reference;
  private final ITraitContext traitContext;

  public SpecialtiesContainer(ITraitReference reference, ITraitContext traitContext) {
    this.reference = reference;
    this.traitContext = traitContext;
  }

  @Override
  public void handleAdditionOfContainedEquivalent(Specialty subTrait) {
    int maxAddition = ALLOWED_SPECIALTY_COUNT - getCurrentDotTotal();
    int addition = Math.min(1, maxAddition);
    subTrait.setCurrentValue(subTrait.getCurrentValue() + addition);
  }

  @Override
  public boolean isNewSubTraitAllowed() {
    return getCurrentDotTotal() < ALLOWED_SPECIALTY_COUNT;
  }

  @Override
  public Specialty createSubTrait(String name) {
    return new DefaultSpecialty(this, reference, name, traitContext);
  }
}