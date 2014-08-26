package net.sf.anathema.hero.charms.display.prerequisites;

import net.sf.anathema.charm.data.Charm;
import net.sf.anathema.charm.data.prerequisite.PrerequisiteProcessor;
import net.sf.anathema.charm.data.prerequisite.RequiredTraitType;
import net.sf.anathema.charm.data.reference.CategoryReference;
import net.sf.anathema.charm.data.reference.TreeReference;
import net.sf.anathema.magic.data.attribute.MagicAttribute;

import java.util.ArrayList;
import java.util.List;

public class ConcreteCharmRenderingParents implements PrerequisiteProcessor {

  public static List<Charm> collectRenderingParents(Charm charm) {
    ConcreteCharmRenderingParents concreteCharmRenderingParents = new ConcreteCharmRenderingParents();
    charm.getPrerequisites().forEachCharmPrerequisite(prerequisite -> prerequisite.process(concreteCharmRenderingParents));
    return concreteCharmRenderingParents.renderingParents;
  }

  public List<Charm> renderingParents = new ArrayList<>();

  @Override
  public void requiresMagicAttributes(MagicAttribute attribute, int count) {
    // nothing to do
  }
  
  @Override
	public void requiresMagicAttributesFromTree(TreeReference tree,
			MagicAttribute attribute, int count) {
		// nothing to do
	}

  @Override
  public void requiresCharm(Charm prerequisite) {
    renderingParents.add(prerequisite);
  }

  @Override
  public void requiresCharmFromSelection(Charm[] prerequisites, int threshold) {
    for (Charm prerequisite : prerequisites) {
      renderingParents.add(prerequisite);
    }
  }

  @Override
  public void requiresCharmsOfTraits(List<RequiredTraitType> traits, CategoryReference category, int count,
		  int minimumEssence) {
  	// nothing to do
  }

	@Override
	public void requiresCharmsOfAnyOneTrait(int threshold) {
		// nothing to do
	}
}