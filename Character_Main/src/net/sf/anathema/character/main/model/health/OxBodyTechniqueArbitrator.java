package net.sf.anathema.character.main.model.health;

import net.sf.anathema.character.main.charm.special.IOxBodyTechniqueConfiguration;

public interface OxBodyTechniqueArbitrator {

  void addOxBodyTechniqueConfiguration(IOxBodyTechniqueConfiguration configuration);

  boolean isIncrementAllowed(int increment);
}