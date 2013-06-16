package net.sf.anathema.character.impl.model.traits.listening;

import net.sf.anathema.character.generic.traits.ITraitType;
import net.sf.anathema.character.generic.traits.groups.ITraitTypeGroup;
import net.sf.anathema.character.generic.traits.groups.TraitTypeGroup;
import net.sf.anathema.character.generic.traits.types.OtherTraitType;
import net.sf.anathema.character.generic.traits.types.VirtueType;
import net.sf.anathema.character.impl.model.context.CharacterListening;
import net.sf.anathema.character.library.trait.Trait;
import net.sf.anathema.character.library.trait.favorable.FavorableState;
import net.sf.anathema.character.library.trait.favorable.IFavorableStateChangedListener;
import net.sf.anathema.character.library.trait.specialties.ISpecialtiesConfiguration;
import net.sf.anathema.character.library.trait.specialties.Specialty;
import net.sf.anathema.character.library.trait.subtrait.ISpecialtyListener;
import net.sf.anathema.character.model.traits.ICoreTraitConfiguration;

public class CharacterTraitListening {

  private final CharacterListening listening;
  private final ICoreTraitConfiguration traitConfiguration;

  public CharacterTraitListening(ICoreTraitConfiguration traitConfiguration, CharacterListening listening) {
    this.traitConfiguration = traitConfiguration;
    this.listening = listening;
  }

  public void initListening() {
    initAttributeListening();
    initAbilityListening();
    for (Trait virtue : traitConfiguration.getTraits(VirtueType.values())) {
      listening.addTraitListening(virtue);
    }
    listening.addTraitListening(traitConfiguration.getTrait(OtherTraitType.Willpower));
    listening.addTraitListening(traitConfiguration.getTrait(OtherTraitType.Essence));
  }

  private void initAbilityListening() {
    ITraitTypeGroup[] groups = traitConfiguration.getAbilityTypeGroups();
    ITraitType[] allAbilityTypes = TraitTypeGroup.getAllTraitTypes(groups);
    ISpecialtiesConfiguration specialtyConfiguration = traitConfiguration.getSpecialtyConfiguration();
    for (ITraitType traitType : allAbilityTypes) {
      Trait ability = traitConfiguration.getTrait(traitType);
      listening.addTraitListening(ability);
      ability.getFavorization().addFavorableStateChangedListener(new IFavorableStateChangedListener() {
        @Override
        public void favorableStateChanged(FavorableState state) {
          listening.fireCharacterChanged();
        }
      });
      specialtyConfiguration.getSpecialtiesContainer(traitType).addSubTraitListener(new ISpecialtyListener() {
        @Override
        public void subTraitRemoved(Specialty specialty) {
          listening.fireCharacterChanged();
        }

        @Override
        public void subTraitAdded(Specialty specialty) {
          listening.fireCharacterChanged();
        }

        @Override
        public void subTraitValueChanged() {
          listening.fireCharacterChanged();
        }
      });
    }
  }

  private void initAttributeListening() {
    ITraitTypeGroup[] groups = traitConfiguration.getAttributeTypeGroups();
    ITraitType[] allAttributeTypes = TraitTypeGroup.getAllTraitTypes(groups);
    for (ITraitType traitType : allAttributeTypes) {
      Trait attribute = traitConfiguration.getTrait(traitType);
      listening.addTraitListening(attribute);
    }
  }
}