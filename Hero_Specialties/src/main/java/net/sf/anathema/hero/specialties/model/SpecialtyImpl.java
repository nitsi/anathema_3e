package net.sf.anathema.hero.specialties.model;

import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.traits.model.DefaultTraitType;
import net.sf.anathema.hero.traits.model.TraitImpl;
import net.sf.anathema.hero.traits.model.TraitRules;
import net.sf.anathema.hero.traits.model.TraitType;
import net.sf.anathema.hero.traits.model.rules.TraitRulesImpl;
import net.sf.anathema.hero.traits.template.TraitTemplate;
import net.sf.anathema.hero.traits.template.TraitTemplateFactory;

public class SpecialtyImpl extends TraitImpl implements Specialty {

  private final String subTraitName;
  private final AbstractSubTraitContainer container;
  private final TraitType type;

  private static TraitRules createSpecialtyRules(Hero hero) {
    DefaultTraitType traitType = new DefaultTraitType("Specialty");
    TraitTemplate limitation = TraitTemplateFactory.createStaticLimitedTemplate(0, 3);
    return new TraitRulesImpl(traitType, limitation, hero);
  }

  public SpecialtyImpl(Hero hero, AbstractSubTraitContainer container, TraitType type, String specialtyName) {
    super(hero, createSpecialtyRules(hero));
    this.container = container;
    this.type = type;
    this.subTraitName = specialtyName;
  }

  @Override
  public String getName() {
    return subTraitName;
  }

  @Override
  public TraitType getBasicTraitType() {
    return type;
  }

  @Override
  public void setCurrentValue(int value) {
    int increment = value - getCurrentValue();
    if (container.getCurrentDotTotal() + increment <= SpecialtiesContainer.ALLOWED_SPECIALTY_COUNT) {
      super.setCurrentValue(value);
    } else {
      super.resetCurrentValue();
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof SpecialtyImpl)) {
      return false;
    }
    SpecialtyImpl other = (SpecialtyImpl) obj;
    return super.equals(obj) && other.getName().equals(getName()) && other.getBasicTraitType() == getBasicTraitType();
  }

  @Override
  public int hashCode() {
    return getName().hashCode() + getBasicTraitType().hashCode();
  }
}