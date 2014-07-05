package net.sf.anathema.hero.abilities.model;

import net.sf.anathema.hero.individual.model.Hero;
import net.sf.anathema.hero.traits.model.Trait;
import net.sf.anathema.hero.traits.model.TraitImpl;
import net.sf.anathema.hero.traits.model.TraitModelFetcher;
import net.sf.anathema.hero.traits.model.TraitType;
import net.sf.anathema.hero.traits.model.rules.minimum.DynamicMinimum;
import net.sf.anathema.hero.traits.model.state.TraitState;
import net.sf.anathema.hero.traits.model.state.TraitStateChangedListener;
import net.sf.anathema.hero.traits.model.state.TraitStateMap;
import net.sf.anathema.hero.traits.model.state.TraitStateModel;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static java.util.Arrays.asList;
import static net.sf.anathema.hero.traits.model.state.TraitState.Caste;
import static net.sf.anathema.hero.traits.model.state.TraitState.Favored;

public class TraitStateMapImpl implements TraitStateMap {

  private final Hero hero;
  private Map<TraitType, TraitStateModel> traitsByType = new HashMap<>();

  public TraitStateMapImpl(Hero hero) {
    this.hero = hero;
  }

  public void addTrait(TraitImpl trait) {
    traitsByType.put(trait.getType(), trait.getStateModel());
    DynamicMinimum favoredMinimum = new FavoredMinimum(this, trait);
    TraitModelFetcher.fetch(hero).getMinimumMap().addMinimum(trait.getType(), favoredMinimum);
  }

  @Override
  public void addTraitStateChangedListener(Trait trait, TraitStateChangedListener listener) {
    getStateModel(trait).addTraitStateChangedListener(listener);
  }

  @Override
  public TraitState getState(Trait trait) {
    return getStateModel(trait).getType();
  }

  @Override
  public boolean hasState(Trait trait, TraitState... states) {
    TraitState traitState = getState(trait);
    return asList(states).contains(traitState);
  }

  @Override
  public boolean isFavored(Trait trait) {
    return hasState(trait, Favored);
  }

  @Override
  public boolean isCasteOrFavored(Trait trait) {
    return hasState(trait, Favored, Caste);
  }

  @Override
  public void changeStateTo(Trait trait, TraitState state) {
    getStateModel(trait).changeStateTo(state);
  }

  @Override
  public void forEach(Consumer<TraitStateModel> consumer) {
    traitsByType.values().forEach(consumer);
  }

  @Override
  public void advanceFavorableState(Trait trait) {
    getStateModel(trait).advanceFavorableState();
  }

  @Override
  public void setFavored(Trait trait, boolean favored) {
    getStateModel(trait).setFavored(favored);
  }

  @Override
  public int getMinimalValue(Trait trait) {
    return getStateModel(trait).getMinimalValue();
  }

  private TraitStateModel getStateModel(Trait trait) {
    return traitsByType.get(trait.getType());
  }

}
