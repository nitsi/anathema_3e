package net.sf.anathema.character.presenter.magic.combo;

import net.sf.anathema.character.generic.caste.CasteType;
import net.sf.anathema.character.generic.magic.ICharm;
import net.sf.anathema.character.generic.magic.description.MagicDescriptionProvider;
import net.sf.anathema.character.main.model.charms.CharmsModelFetcher;
import net.sf.anathema.character.main.model.combos.CombosModel;
import net.sf.anathema.character.main.model.concept.HeroConceptFetcher;
import net.sf.anathema.character.main.model.experience.ExperienceModelFetcher;
import net.sf.anathema.character.model.ICharacter;
import net.sf.anathema.character.main.model.charms.CharmsModel;

public class ComboConfigurationModel {

  private final ICharacter character;
  private final MagicDescriptionProvider magicDescriptionProvider;

  public ComboConfigurationModel(ICharacter character, MagicDescriptionProvider magicDescriptionProvider) {
    this.character = character;
    this.magicDescriptionProvider = magicDescriptionProvider;
  }

  public boolean isAlienCharmsAllowed() {
    CasteType caste = HeroConceptFetcher.fetch(character).getCaste().getType();
    return character.getTemplate().getMagicTemplate().getCharmTemplate().isAllowedAlienCharms(caste);
  }

  public CharmsModel getCharmConfiguration() {
    return CharmsModelFetcher.fetch(character);
  }

  public CombosModel getCombos() {
    return character.getCombos();
  }

  public MagicDescriptionProvider getMagicDescriptionProvider() {
    return magicDescriptionProvider;
  }

  public ICharm[] getLearnedCharms() {
    return CharmsModelFetcher.fetch(character).getLearnedCharms(isExperienced());
  }

  public boolean isExperienced() {
    return ExperienceModelFetcher.fetch(character).isExperienced();
  }
}
