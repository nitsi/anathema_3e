package net.sf.anathema.hero.charms.display.tree;

import net.sf.anathema.charm.data.reference.CategoryReference;
import net.sf.anathema.hero.charms.display.presenter.AbstractCharmGroupChangeListener;
import net.sf.anathema.hero.charms.display.presenter.CharmTreeArbitrator;

public class CharacterCharmGroupChangeListener extends AbstractCharmGroupChangeListener {

  public CharacterCharmGroupChangeListener(CharmTreeArbitrator arbitrator) {
    super(arbitrator);
  }

  @Override
  protected void modifyCharmVisuals(CategoryReference type) {
    // Nothing to do
  }
}
