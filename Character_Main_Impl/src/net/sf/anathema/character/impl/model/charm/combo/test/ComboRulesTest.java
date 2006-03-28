package net.sf.anathema.character.impl.model.charm.combo.test;

import net.sf.anathema.character.generic.impl.magic.test.DummyMartialArtsCharm;
import net.sf.anathema.character.generic.magic.ICharm;
import net.sf.anathema.character.generic.magic.charms.CharmType;
import net.sf.anathema.character.generic.magic.charms.ComboRestrictions;
import net.sf.anathema.character.generic.magic.charms.DurationType;
import net.sf.anathema.character.generic.traits.types.AbilityType;
import net.sf.anathema.character.generic.traits.types.ValuedTraitType;
import net.sf.anathema.character.impl.model.charm.combo.ComboRules;
import net.sf.anathema.character.impl.model.charm.test.DummyCharmUtilities;

public class ComboRulesTest extends AbstractComboRulesTestCase {

  private ComboRules rules;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    this.rules = new ComboRules();
  }

  public void testDurationComboLegal() throws Exception {
    assertTrue(rules.isCharmComboLegal(new DummyMartialArtsCharm(DurationType.Instant, CharmType.Reflexive)));
    assertFalse(rules.isCharmComboLegal(new DummyMartialArtsCharm(DurationType.Other, CharmType.Reflexive)));
  }

  public void testRestrictionComboLegal() throws Exception {
    assertFalse(rules.isCharmComboLegal(DummyCharmUtilities.createCharm(DurationType.Instant, new ComboRestrictions(
        null,
        Boolean.FALSE))));
    assertTrue(rules.isCharmComboLegal(DummyCharmUtilities.createCharm(DurationType.Other, new ComboRestrictions(
        null,
        Boolean.TRUE))));
  }

  public void testRestrictionByCharmType() throws Exception {
    ComboRestrictions restrictions = new ComboRestrictions();
    restrictions.addRestrictedCharmType(CharmType.ExtraAction);
    ICharm charm1 = DummyCharmUtilities.createCharm(CharmType.Supplemental, restrictions, new ValuedTraitType(
        AbilityType.Archery,
        3));
    ICharm charm2 = DummyCharmUtilities.createCharm(CharmType.ExtraAction, new ValuedTraitType(AbilityType.Archery, 3));
    assertFalse(rules.isComboLegal(charm1, charm2));
  }

  public void testRestrictionById() throws Exception {
    ComboRestrictions restrictions = new ComboRestrictions();
    String forbiddenId = "DummyCharm";//$NON-NLS-1$
    restrictions.addRestrictedCharmId(forbiddenId);
    ICharm charm1 = DummyCharmUtilities.createCharm(CharmType.Supplemental, restrictions, new ValuedTraitType(
        AbilityType.Archery,
        3));
    DummyMartialArtsCharm charm2 = (DummyMartialArtsCharm) DummyCharmUtilities.createCharm(
        CharmType.ExtraAction,
        new ValuedTraitType(AbilityType.Archery, 3));
    charm2.setId(forbiddenId);
    assertFalse(rules.isComboLegal(charm1, charm2));
  }

  public void testRestrictionByPrerequisite() throws Exception {
    ComboRestrictions restrictions = new ComboRestrictions();
    restrictions.addRestrictedTraitType(AbilityType.Awareness);
    ICharm charm1 = DummyCharmUtilities.createCharm(CharmType.Reflexive, restrictions, new ValuedTraitType(
        AbilityType.Archery,
        3));
    ICharm charm2 = DummyCharmUtilities.createCharm(
        CharmType.ExtraAction,
        new ValuedTraitType(AbilityType.Awareness, 3));
    assertFalse(rules.isComboLegal(charm1, charm2));
  }

  public void testCharmComboSelf() throws Exception {
    ICharm charm1 = DummyCharmUtilities.createCharm(CharmType.Reflexive);
    assertFalse(rules.isComboLegal(charm1, charm1));
  }

  public void testCharmComboAttributeAbilityReflexiveForbidden() throws Exception {
    getRules().setCrossPrerequisiteTypeComboAllowed(false);
    assertFalse(comboAbilityAttributeCharms(CharmType.Reflexive, CharmType.Reflexive));
  }

  public void testCharmComboAttributeAbilityReflexiveAllowed() throws Exception {
    getRules().setCrossPrerequisiteTypeComboAllowed(true);
    assertTrue(comboAbilityAttributeCharms(CharmType.Reflexive, CharmType.Reflexive));
  }
}