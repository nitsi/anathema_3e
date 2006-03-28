package net.sf.anathema.character.impl.model.charm.combo;

import net.sf.anathema.character.generic.magic.ICharm;
import net.sf.anathema.character.generic.magic.charms.CharmType;
import net.sf.anathema.character.generic.magic.charms.ICharmTypeVisitor;

public class SimpleCharmComboRules extends AbstractComboRules {

  public boolean isComboLegal(final ICharm simpleCharm, final ICharm otherCharm) {
    final boolean[] legal = new boolean[1];
    otherCharm.getCharmType().accept(new ICharmTypeVisitor() {
      public void visitSimple(CharmType visitedType) {
        legal[0] = false;
      }

      public void visitExtraAction(CharmType visitedType) {
        boolean allAbilitiesRule = allAbilitiesRuleApplied(simpleCharm, otherCharm);
        boolean samePrerequisite = haveSamePrerequisite(simpleCharm, otherCharm);
        boolean attributePrerequisites = haveAttributePrerequisites(simpleCharm, otherCharm);
        legal[0] = allAbilitiesRule || samePrerequisite || attributePrerequisites;
      }

      public void visitReflexive(CharmType visitedType) {
        legal[0] = true;
      }

      public void visitSupplemental(CharmType visitedType) {
        boolean allAbilitiesRule = allAbilitiesRuleApplied(simpleCharm, otherCharm);
        boolean samePrerequisite = haveSamePrerequisite(simpleCharm, otherCharm);
        boolean attributePrerequisites = haveAttributePrerequisites(simpleCharm, otherCharm);
        legal[0] = allAbilitiesRule || samePrerequisite || attributePrerequisites;
      }

      public void visitSpecial(CharmType visitedType) {
        // TODO Auto-generated method stub
      }
    });
    return legal[0];
  }
}