package net.sf.anathema.character.impl.view.advantage;

import net.disy.commons.swing.layout.grid.GridDialogLayout;
import net.sf.anathema.character.library.overview.LabelledOverviewStringValueView;
import net.sf.anathema.character.library.trait.IModifiableCapTrait;
import net.sf.anathema.character.library.trait.view.SimpleTraitView;
import net.sf.anathema.framework.value.IIntValueView;
import net.sf.anathema.framework.value.IntegerViewFactory;
import net.sf.anathema.lib.gui.IView;
import net.sf.anathema.lib.workflow.labelledvalue.IValueView;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class EssencePanelView implements IView {

  private final JPanel panel = new JPanel(new GridDialogLayout(2, false));
  private final IntegerViewFactory guiConfiguration;

  public EssencePanelView(IntegerViewFactory guiConfiguration) {
    this.guiConfiguration = guiConfiguration;
  }

  public IIntValueView addEssenceView(String labelText, int value, int maxValue, IModifiableCapTrait trait) {
    SimpleTraitView essenceView = new SimpleTraitView(guiConfiguration, labelText, value, maxValue, trait);
    essenceView.addComponents(panel);
    return essenceView;
  }

  public IValueView<String> addPoolView(String labelText, String value) {
    LabelledOverviewStringValueView poolView = new LabelledOverviewStringValueView(labelText, value, false);
    poolView.addComponents(panel);
    return poolView;
  }

  @Override
  public JComponent getComponent() {
    return panel;
  }
}