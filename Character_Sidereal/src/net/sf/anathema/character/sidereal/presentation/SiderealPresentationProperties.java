package net.sf.anathema.character.sidereal.presentation;

import java.awt.Color;

import net.sf.anathema.character.generic.impl.template.presentation.AbstractPresentationProperties;
import net.sf.anathema.character.generic.template.TemplateType;
import net.sf.anathema.character.generic.template.presentation.ICharmPresentationProperties;
import net.sf.anathema.character.generic.type.CharacterType;

public class SiderealPresentationProperties extends AbstractPresentationProperties {

  private final ICharmPresentationProperties charmPresentationProperties = new SiderealCharmPresentationProperties();

  public SiderealPresentationProperties() {
    super(new TemplateType(CharacterType.SIDEREAL));
  }

  public Color getColor() {
    return new Color(147, 112, 219);
  }

  public ICharmPresentationProperties getCharmPresentationProperties() {
    return charmPresentationProperties;
  }
}