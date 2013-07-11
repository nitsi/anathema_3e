package net.sf.anathema.hero.spells.display;

import net.sf.anathema.character.main.magic.description.MagicDescriptionProvider;
import net.sf.anathema.character.main.magic.model.magic.IMagicLearnListener;
import net.sf.anathema.character.main.magic.model.spells.CircleType;
import net.sf.anathema.character.main.magic.model.spells.ISpell;
import net.sf.anathema.hero.experience.ExperienceModelFetcher;
import net.sf.anathema.hero.magic.display.MagicViewListener;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.hero.spells.SpellsModelFetcher;
import net.sf.anathema.hero.spells.model.CircleModel;
import net.sf.anathema.lib.compare.I18nedIdentificateComparator;
import net.sf.anathema.lib.compare.I18nedIdentificateSorter;
import net.sf.anathema.lib.control.ChangeListener;
import net.sf.anathema.lib.control.ObjectValueListener;
import net.sf.anathema.lib.resources.Resources;
import net.sf.anathema.lib.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class SpellPresenter {

  private final net.sf.anathema.hero.spells.SpellModel spellConfiguration;
  private CircleModel circleModel;
  private final Hero hero;
  private final Resources resources;
  private CircleType circle;
  private final SpellView view;

  public SpellPresenter(CircleModel circleModel, Hero hero, Resources resources, SpellView view,
                        MagicDescriptionProvider magicDescriptionProvider) {
    this.circleModel = circleModel;
    this.hero = hero;
    SpellViewProperties properties = new SpellViewProperties(resources, hero, magicDescriptionProvider);
    this.resources = resources;
    this.spellConfiguration = SpellsModelFetcher.fetch(hero);
    this.view = view;
    view.prepare(properties);
    circle = circleModel.getCircles()[0];
  }

  public void initPresentation() {
    Identifier[] allowedCircles = circleModel.getCircles();
    view.initGui(allowedCircles);
    view.addMagicViewListener(new MagicViewListener() {
      @Override
      public void magicRemoved(Object[] removedSpells) {
        List<ISpell> spellList = new ArrayList<>();
        for (Object spellObject : removedSpells) {
          spellList.add((ISpell) spellObject);
        }
        spellConfiguration.removeSpells(spellList.toArray(new ISpell[spellList.size()]));
      }

      @Override
      public void magicAdded(Object[] addedSpells) {
        List<ISpell> spellList = new ArrayList<>();
        for (Object spellObject : addedSpells) {
          if (spellConfiguration.isSpellAllowed((ISpell) spellObject)) {
            spellList.add((ISpell) spellObject);
          }
        }
        spellConfiguration.addSpells(spellList.toArray(new ISpell[spellList.size()]));
      }
    });
    view.addCircleSelectionListener(new ObjectValueListener<CircleType>() {
      @Override
      public void valueChanged(CircleType circleType) {
        circle = circleType;
        List<ISpell> available = spellConfiguration.getAvailableSpellsInCircle(circle);
        view.setAvailableMagic(new I18nedIdentificateSorter<ISpell>().sortAscending(available, resources));
      }
    });
    spellConfiguration.addMagicLearnListener(new IMagicLearnListener<ISpell>() {
      @Override
      public void magicForgotten(ISpell[] magic) {
        forgetSpellListsInView(view, magic);
      }

      @Override
      public void magicLearned(ISpell[] magic) {
        learnSpellListsInView(view, magic);
      }
    });
    initSpellListsInView(view);
    ExperienceModelFetcher.fetch(hero).addStateChangeListener(new ChangeListener() {
      @Override
      public void changeOccurred() {
        view.clearSelection();
      }
    });
  }

  private void initSpellListsInView(SpellView spellView) {
    showLearnedSpells(spellView);
    showAvailableSpells(spellView);
  }

  private void forgetSpellListsInView(SpellView spellView, ISpell[] spells) {
    spellView.removeLearnedMagic(spells);
    List<Identifier> supportedSpells = getSpellsOfCurrentCircle(spells);
    spellView.addMagicOptions(supportedSpells, new I18nedIdentificateComparator(resources));
  }

  private void learnSpellListsInView(SpellView spellView, ISpell[] spells) {
    List<Identifier> supportedSpells = getSpellsOfCurrentCircle(spells);
    spellView.addLearnedMagic(supportedSpells);
    spellView.removeMagicOptions(supportedSpells);
  }

  private List<Identifier> getSpellsOfCurrentCircle(ISpell[] spells) {
    List<Identifier> supportedSpells = new ArrayList<>();
    for (ISpell spell : spells) {
      if (circle == spell.getCircleType()) {
        supportedSpells.add(spell);
      }
    }
    return supportedSpells;
  }

  private void showAvailableSpells(SpellView spellView) {
    List<ISpell> availableSpells = spellConfiguration.getAvailableSpellsInCircle(circle);
    List<ISpell> sortedSpells = new I18nedIdentificateSorter<ISpell>().sortAscending(availableSpells, resources);
    spellView.setAvailableMagic(sortedSpells);
  }

  private void showLearnedSpells(SpellView spellView) {
    List<ISpell> learnedSpells = spellConfiguration.getLearnedSpellsInCircles(circleModel.getCircles());
    spellView.setLearnedMagic(learnedSpells);
  }
}