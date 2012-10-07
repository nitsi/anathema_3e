package net.sf.anathema.character.equipment.impl.character.view;

import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;
import net.sf.anathema.character.equipment.MagicalMaterial;
import net.sf.anathema.character.equipment.character.view.IMagicalMaterialView;
import net.sf.anathema.lib.gui.widgets.ChangeableJComboBox;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import static net.sf.anathema.lib.gui.layout.LayoutUtils.fillWithoutInsets;

public class MagicMaterialView implements IMagicalMaterialView {

  private final ChangeableJComboBox<MagicalMaterial> materialCombo = new ChangeableJComboBox<MagicalMaterial>(
      new MagicalMaterial[0],
      false);
  private final JLabel label = new JLabel();
  private JPanel content;

  @Override
  public JComponent getComponent() {
    if (content == null) {
      content = new JPanel(new MigLayout(fillWithoutInsets()));
      content.add(label);
      content.add(materialCombo.getComponent(), new CC().growX().pushX());
    }
    return content;
  }

  @Override
  public void initView(String labelString, ListCellRenderer renderer, MagicalMaterial[] materials) {
    this.label.setText(labelString);
    materialCombo.setObjects(materials);
    materialCombo.setRenderer(renderer);
    setSelectedMaterial(null, false);
    if (content != null) {
      net.sf.anathema.lib.gui.swing.GuiUtilities.revalidateTree(content);
    }
  }

  @Override
  public void setSelectedMaterial(MagicalMaterial selection, boolean viewEnabled) {
    materialCombo.setSelectedObject(selection);
    materialCombo.getComponent().setEnabled(viewEnabled);
  }

  @Override
  public MagicalMaterial getSelectedMaterial() {
    return materialCombo.getSelectedObject();
  }
}