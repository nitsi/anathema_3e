package net.sf.anathema.hero.charms.compiler.special;

import net.sf.anathema.charm.data.prerequisite.RequiredTraitType;
import net.sf.anathema.charm.data.prerequisite.TraitPrerequisite;
import net.sf.anathema.charm.data.reference.CharmName;
import net.sf.anathema.charm.template.CharmTemplate;
import net.sf.anathema.charm.template.special.Repurchase;
import net.sf.anathema.charm.template.special.RepurchaseVisitor;
import net.sf.anathema.charm.template.special.Requirement;
import net.sf.anathema.charm.template.special.SpecialCharmTemplate;
import net.sf.anathema.charm.template.special.Tier;
import net.sf.anathema.charm.template.special.TierRepurchase;
import net.sf.anathema.charm.template.special.TraitRepurchase;
import net.sf.anathema.hero.charms.model.special.ISpecialCharm;
import net.sf.anathema.hero.charms.model.special.multilearn.CharmTier;
import net.sf.anathema.hero.charms.model.special.multilearn.EssenceFixedMultiLearnableCharm;
import net.sf.anathema.hero.charms.model.special.multilearn.StaticMultiLearnableCharm;
import net.sf.anathema.hero.charms.model.special.multilearn.TieredMultiLearnableCharm;
import net.sf.anathema.hero.charms.model.special.multilearn.TraitCharmTier;
import net.sf.anathema.hero.charms.model.special.multilearn.TraitDependentMultiLearnableCharm;
import net.sf.anathema.hero.traits.TraitTypeFinder;
import net.sf.anathema.hero.traits.model.TraitType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("UnusedDeclaration")
public class RepurchaseCharmBuilder implements SpecialCharmBuilder {

  private final TraitTypeFinder traitTypeFinder = new TraitTypeFinder();

  @Override
  public ISpecialCharm readCharm(SpecialCharmTemplate overallDto, AdditionalCharmFactory factory) {
  	final Repurchase repurchaseDto = overallDto.repurchase;
  	final CharmName charmName = new CharmName(overallDto.charmId);
  	final ISpecialCharm[] result = new ISpecialCharm[1];
  	final Map<String, Integer> minimums = factory.getCurrentMinimums(overallDto.charmId);
  			
  	repurchaseDto.visit(new RepurchaseVisitor() {

			@Override
			public void visitTraitRepurchase(TraitRepurchase repurchase) {
				
				List<Tier> tiers = new ArrayList<>();
				int startingPoint = minimums.get(repurchase.limitingTrait) + 1;
				for (int minimum = startingPoint; minimum <= AdditionalCharmFactory.TRAIT_MAX; minimum++) {
					Tier tier = createBaseTier(minimums);
					tier.requirements.stream().filter(requirement -> requirement.traitType.equals(repurchase.limitingTrait)).
						findFirst().get().traitValue = minimum;
					tiers.add(tier);
				}
				factory.generateCharms(overallDto.charmId, tiers);
				//result[0] = createTraitMultiLearnable(charmName, repurchase);
				
			}
  		
  	});
  	
  	return result[0];
    /*Repurchase repurchase = overallDto.repurchase;
    if (repurchase.isEssenceRepurchase) {
      return new EssenceFixedMultiLearnableCharm(new CharmName(overallDto.charmId));
    }
    CharmName charmName = new CharmName(overallDto.charmId);
    if (repurchase.traitRepurchase != null) {
      return createTraitMultiLearnable(charmName, repurchase.traitRepurchase);
    }
    if (repurchase.staticRepurchase != null) {
      return new StaticMultiLearnableCharm(charmName, repurchase.staticRepurchase.limit);
    }
    return createTierMultiLearnable(charmName, repurchase);*/
  }
  
  private Tier createBaseTier(Map<String, Integer> minimums) {
  	Tier tier = new Tier();
  	minimums.forEach((trait, value) -> tier.requirements.add(new Requirement(trait, value)));
  	return tier;
  }

  private ISpecialCharm createTierMultiLearnable(CharmName id, Repurchase repurchase) {
    /*TierRepurchase dto = repurchase.tierRepurchase;
    List<CharmTier> tiers = new ArrayList<>();
    for (Tier tier : dto.tiers) {
      tiers.add(createTier(tier));
    }
    return new TieredMultiLearnableCharm(id, tiers.toArray(new CharmTier[tiers.size()]));*/
  	return null;
  }

  private CharmTier createTier(Tier dto) {
    TraitCharmTier traitCharmTier = new TraitCharmTier();
    for (Requirement requirement : dto.requirements) {
      RequiredTraitType traitType = new RequiredTraitType(requirement.traitType);
      traitCharmTier.addRequirement(new TraitPrerequisite(traitType, requirement.traitValue));
    }
    return traitCharmTier;
  }

  private TraitDependentMultiLearnableCharm createTraitMultiLearnable(CharmName id, TraitRepurchase dto) {
    TraitType trait = traitTypeFinder.getTrait(dto.limitingTrait);
    int modifier = 0; //dto.modifier;
    int absoluteMax = 0; //dto.absoluteMax;
    return new TraitDependentMultiLearnableCharm(id, absoluteMax, trait, modifier);
  }

  @Override
  public boolean supports(SpecialCharmTemplate overallDto) {
    return overallDto.repurchase != null;
  }
}