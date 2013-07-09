package net.sf.anathema.character.main.template;

import net.sf.anathema.character.main.testing.dummy.DummyBonusPointCosts;
import net.sf.anathema.character.main.template.points.FixedValueRatingCosts;
import net.sf.anathema.character.main.template.experience.CurrentRatingCosts;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DefaultBonusPointsTest {

  private DummyBonusPointCosts costs = new DummyBonusPointCosts();

  @Test
  public void testAbilityCost() throws Exception {
    CurrentRatingCosts unfavoredAbilityCosts = costs.getAbilityCosts(false);
    assertEquals(new FixedValueRatingCosts(2), unfavoredAbilityCosts);
    CurrentRatingCosts favoredAbilityCosts = costs.getAbilityCosts(true);
    assertEquals(new FixedValueRatingCosts(1), favoredAbilityCosts);
  }
}