package net.sf.anathema.hero.display.fx.perspective.navigation;

import net.sf.anathema.hero.application.perspective.HeroPoolModel;
import net.sf.anathema.hero.application.perspective.NewInteractionPresenter;
import net.sf.anathema.hero.application.perspective.Selector;
import net.sf.anathema.hero.application.perspective.UpdatingHeroesGridView;
import net.sf.anathema.hero.application.perspective.model.HeroIdentifier;
import net.sf.anathema.library.interaction.view.InteractionView;
import net.sf.anathema.platform.environment.Environment;

public class FrontInteractionPresenter {
  private final HeroPoolModel system;
  private final InteractionView view;
  private final Environment environment;
  private final UpdatingHeroesGridView gridView;
  private final Selector<HeroIdentifier> selector;

  public FrontInteractionPresenter(HeroPoolModel system, InteractionView view, Environment environment,
                                   UpdatingHeroesGridView gridView, Selector<HeroIdentifier> selector) {
    this.system = system;
    this.view = view;
    this.environment = environment;
    this.gridView = gridView;
    this.selector = selector;
  }

  public void initPresentation() {
    initNewInteraction();
  }

  private void initNewInteraction() {
    new NewInteractionPresenter(system, view.addTool(), environment, gridView, selector).initPresentation();
  }
}