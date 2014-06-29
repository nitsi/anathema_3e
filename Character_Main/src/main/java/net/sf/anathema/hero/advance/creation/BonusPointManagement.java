package net.sf.anathema.hero.advance.creation;

import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.hero.points.HeroBonusPointCalculator;
import net.sf.anathema.hero.points.PointModelFetcher;
import net.sf.anathema.hero.points.PointsModel;
import net.sf.anathema.hero.points.overview.SpendingModel;

public class BonusPointManagement implements IBonusPointManagement {

  private final BonusPointCalculator bonusPointCalculator = new BonusPointCalculator();
  private final SpendingModel totalModel;

  public BonusPointManagement(PointsCreationData creationData) {
    this.totalModel = new TotalBonusPointModel(creationData, bonusPointCalculator);
  }

  @Override
  public void recalculate() {
    bonusPointCalculator.recalculate();
  }

  public void addBonusPointCalculator(HeroBonusPointCalculator calculator) {
    bonusPointCalculator.addBonusPointCalculator(calculator);
  }

  @Override
  public SpendingModel getTotalModel() {
    return totalModel;
  }

  @Override
  public String getSummaryCategory() {
    return TotalBonusPointModel.SUMMARY_CATEGORY_ID;
  }
}