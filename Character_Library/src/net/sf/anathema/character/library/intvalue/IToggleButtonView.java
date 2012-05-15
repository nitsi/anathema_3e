package net.sf.anathema.character.library.intvalue;

import net.sf.anathema.lib.control.IBooleanValueChangedListener;

public interface IToggleButtonView {
  void addButtonSelectedListener(IBooleanValueChangedListener listener);

  void setButtonState(boolean selected, boolean enabled);
}