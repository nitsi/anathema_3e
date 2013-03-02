package net.sf.anathema.framework.view.messaging;

import net.sf.anathema.lib.gui.message.MessageTypeUi;
import net.sf.anathema.lib.message.IBasicMessage;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class OneLineStatusBar implements StatusBar {

  private final JLabel label = new JLabel();
  private final JPanel panel = new JPanel(new BorderLayout());

  public OneLineStatusBar() {
    JButton button = new JButton("...");
    panel.add(label, BorderLayout.CENTER);
    label.setPreferredSize(new Dimension(350, 20));
    button.setPreferredSize(new Dimension(25, 20));
  }

  @Override
  public JComponent getComponent() {
    return panel;
  }

  @Override
  public void setLatestMessage(IBasicMessage message) {
    label.setIcon(MessageTypeUi.getInstance().getIcon(message.getType()));
    label.setText(message.getText());
    label.revalidate();
  }
}