package net.sf.anathema.hero.spells.model;

import net.sf.anathema.hero.abilities.model.AbilitiesModel;
import net.sf.anathema.hero.attributes.model.AttributeModel;
import net.sf.anathema.hero.charms.advance.MagicPointsModel;
import net.sf.anathema.hero.charms.model.CharmsModel;
import net.sf.anathema.hero.elsewhere.concept.HeroConcept;
import net.sf.anathema.hero.environment.template.TemplateFactory;
import net.sf.anathema.hero.experience.model.ExperienceModel;
import net.sf.anathema.hero.health.model.HealthModel;
import net.sf.anathema.hero.individual.model.HeroModelFactory;
import net.sf.anathema.hero.individual.model.SimpleModelTreeEntry;
import net.sf.anathema.hero.spells.template.SpellsTemplate;
import net.sf.anathema.hero.spells.template.SpellsTemplateLoader;
import net.sf.anathema.hero.spiritual.model.traits.SpiritualTraitModel;
import net.sf.anathema.hero.traits.model.TraitModel;

@SuppressWarnings("UnusedDeclaration")
public class SpellsModelFactory extends SimpleModelTreeEntry implements HeroModelFactory {

  public SpellsModelFactory() {
    super(SpellsModel.ID, CharmsModel.ID, AttributeModel.ID, AbilitiesModel.ID, SpiritualTraitModel.ID, TraitModel.ID, ExperienceModel.ID,
            HeroConcept.ID, HealthModel.ID, MagicPointsModel.ID);
  }

  @SuppressWarnings("unchecked")
  @Override
  public SpellsModel create(TemplateFactory templateFactory, String templateId) {
    SpellsTemplate template = SpellsTemplateLoader.loadTemplate(templateFactory, templateId);
    return new SpellsModelImpl(template);
  }
}
