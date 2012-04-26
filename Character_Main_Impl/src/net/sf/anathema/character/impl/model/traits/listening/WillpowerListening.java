package net.sf.anathema.character.impl.model.traits.listening;

import java.util.Arrays;

import net.sf.anathema.character.library.trait.ITrait;
import net.sf.anathema.character.library.trait.visitor.IDefaultTrait;
import net.sf.anathema.lib.control.IIntValueChangedListener;

public class WillpowerListening {

  public void initListening(final IDefaultTrait willpower, final IDefaultTrait[] virtues) {
    for (ITrait virtue : virtues) {
      virtue.addCreationPointListener(new IIntValueChangedListener() {
        @Override
        public void valueChanged(int newValue) {
          updateWillpowerCreationRange(willpower, virtues);
        }
      });
    }
    updateWillpowerCreationRange(willpower, virtues);
  }

  private void updateWillpowerCreationRange(final IDefaultTrait willpower, final IDefaultTrait[] virtues) {
    int newInitialValue = Math.min(calculateAbsoluteMinimalValue(virtues), willpower.getMaximalValue());
    int newUpperValue = Math.min(calculateUpperValue(virtues), willpower.getMaximalValue());
    willpower.setModifiedCreationRange(newInitialValue, newUpperValue);
  }

  private int calculateAbsoluteMinimalValue(final IDefaultTrait[] virtues) {
    int[] virtueCreationValues = orderVirtueCreationValuesAscending(virtues);
    return virtueCreationValues[virtueCreationValues.length - 1]
        + virtueCreationValues[virtueCreationValues.length - 2];
  }

  private int[] orderVirtueCreationValuesAscending(final IDefaultTrait[] virtues) {
    int[] virtueValues = new int[virtues.length];
    for (int index = 0; index < virtueValues.length; index++) {
      virtueValues[index] = virtues[index].getCreationValue();
    }
    Arrays.sort(virtueValues);
    return virtueValues;
  }

  private int calculateUpperValue(final IDefaultTrait[] virtues) {
    int[] virtueValues = orderVirtueCreationValuesAscending(virtues);
    return Math.max(virtueValues[virtueValues.length - 1] + virtueValues[virtueValues.length - 2], 8);
  }
}