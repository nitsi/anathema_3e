package net.sf.anathema.hero.traits.model.event;

import net.sf.anathema.hero.traits.model.state.TraitState;
import net.sf.anathema.hero.traits.model.state.TraitStateChangedListener;
import net.sf.anathema.library.change.ChangeAnnouncer;
import net.sf.anathema.library.change.ChangeFlavor;

public class FavoredChangedListener implements TraitStateChangedListener {
  private final ChangeAnnouncer changeAnnouncer;

  public FavoredChangedListener(ChangeAnnouncer changeAnnouncer) {
    this.changeAnnouncer = changeAnnouncer;
  }

  @Override
  public void favorableStateChanged(TraitState state) {
    changeAnnouncer.announceChangeOf(ChangeFlavor.UNSPECIFIED);
  }
}
