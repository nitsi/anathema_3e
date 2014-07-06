package net.sf.anathema.hero.languages.model;

import net.sf.anathema.hero.individual.model.HeroModel;
import net.sf.anathema.library.event.ChangeListener;
import net.sf.anathema.library.identifier.Identifier;
import net.sf.anathema.library.identifier.SimpleIdentifier;
import net.sf.anathema.library.model.RemovableEntryModel;

import java.util.Collection;

public interface LanguagesModel extends RemovableEntryModel<Identifier>, HeroModel {

  Identifier ID = new SimpleIdentifier("Languages");

  Collection<Identifier> getPredefinedLanguages();

  boolean isPredefinedLanguage(Object object);

  Identifier getPredefinedLanguageById(String displayName);

  void selectBarbarianLanguage(String customName);

  void selectLanguage(Identifier language);

  boolean isEntryAllowed();

  int getPredefinedLanguageCount();

  int getBarbarianLanguageCount();

  int getLanguagePointsSpent();

  void addCharacterChangedListener(ChangeListener listener);

  int getLanguagePointsAllowed();

  Identifier getSelectedEntry();
}