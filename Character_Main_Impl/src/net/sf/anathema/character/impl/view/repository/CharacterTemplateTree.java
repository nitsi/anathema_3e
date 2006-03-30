package net.sf.anathema.character.impl.view.repository;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import net.sf.anathema.character.generic.framework.ICharacterGenerics;
import net.sf.anathema.character.generic.impl.IIconConstants;
import net.sf.anathema.character.generic.template.ICharacterTemplate;
import net.sf.anathema.character.generic.type.AbstractSupportedCharacterTypeVisitor;
import net.sf.anathema.character.generic.type.CharacterType;
import net.sf.anathema.character.view.repository.ICharacterTemplateTree;
import net.sf.anathema.lib.resources.IResources;

public class CharacterTemplateTree implements ICharacterTemplateTree {

  private JTree templateTree;
  private List<TreeSelectionListener> listeners = new ArrayList<TreeSelectionListener>();
  private final ICharacterGenerics generics;
  private final IResources resources;

  public CharacterTemplateTree(ICharacterGenerics generics, IResources resources) {
    this.generics = generics;
    this.resources = resources;
  }

  public void initTemplateTree() {
    DefaultTreeModel treeModel = createTreeModel();
    this.templateTree = new JTree(treeModel);
    templateTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    templateTree.addTreeSelectionListener(new TreeSelectionListener() {
      public void valueChanged(TreeSelectionEvent e) {
        fireValueChangedEvent(e);
      }
    });
    templateTree.setCellRenderer(createRenderer());
    Enumeration typeEnumeration = ((DefaultMutableTreeNode) templateTree.getModel().getRoot()).children();
    for (; typeEnumeration.hasMoreElements();) {
      TreeNode[] path = ((DefaultMutableTreeNode) typeEnumeration.nextElement()).getPath();
      templateTree.expandPath(new TreePath(path));
    }
    templateTree.setRootVisible(false);
  }

  private TreeCellRenderer createRenderer() {
    return new DefaultTreeCellRenderer() {
      @Override
      public Component getTreeCellRendererComponent(
          JTree tree,
          Object value,
          boolean sel,
          boolean expanded,
          boolean leaf,
          int row,
          boolean focus) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        Object object = node.getUserObject();
        if (object instanceof CharacterType) {
          CharacterType characterType = (CharacterType) object;
          String characterTypeString = resources.getString("CharacterGenerator.NewCharacter." //$NON-NLS-1$
              + (characterType).getId() + ".Name"); //$NON-NLS-1$
          Component renderComponent = super.getTreeCellRendererComponent(
              tree,
              characterTypeString,
              sel,
              expanded,
              leaf,
              row,
              focus);
          setIcon(getCharacterTypeIcon(characterType));
          return renderComponent;
        }
        if (object instanceof ICharacterTemplate) {
          ICharacterTemplate template = (ICharacterTemplate) object;
          String templateString = resources.getString(template.getPresentationProperties().getNewActionResource());
          Component renderComponent = super.getTreeCellRendererComponent(
              tree,
              templateString,
              sel,
              expanded,
              leaf,
              row,
              focus);
          setIcon(getCharacterTemplateIcon(template.getTemplateType().getCharacterType()));
          return renderComponent;
        }
        return super.getTreeCellRendererComponent(templateTree, value, sel, expanded, leaf, row, focus);
      }
    };
  }

  private Icon getCharacterTemplateIcon(CharacterType characterType) {
    final Icon[] typeIcon = new Icon[1];
    characterType.accept(new AbstractSupportedCharacterTypeVisitor() {
      public void visitSolar(CharacterType visitedType) {
        typeIcon[0] = resources.getImageIcon(IIconConstants.SOLAR_BALL_TINY);
      }

      public void visitMortal(CharacterType visitedType) {
        typeIcon[0] = resources.getImageIcon(IIconConstants.MORTAL_BALL_TINY);

      }

      public void visitSidereal(CharacterType visitedType) {
        typeIcon[0] = resources.getImageIcon(IIconConstants.SIDEREAL_BALL_TINY);

      }

      public void visitDB(CharacterType visitedType) {
        typeIcon[0] = resources.getImageIcon(IIconConstants.DB_BALL_TINY);
      }

      public void visitAbyssal(CharacterType visitedType) {
        typeIcon[0] = resources.getImageIcon(IIconConstants.ABYSSAL_BALL_TINY);
      }

      public void visitLunar(CharacterType type) {
        typeIcon[0] = resources.getImageIcon(IIconConstants.LUNAR_BALL_TINY);
      }
    });
    return typeIcon[0];
  }

  private Icon getCharacterTypeIcon(CharacterType characterType) {
    final Icon[] typeIcon = new Icon[1];
    characterType.accept(new AbstractSupportedCharacterTypeVisitor() {
      public void visitSolar(CharacterType visitedType) {
        typeIcon[0] = resources.getImageIcon(IIconConstants.SOLAR_ICON_TINY);
      }

      public void visitMortal(CharacterType visitedType) {
        typeIcon[0] = resources.getImageIcon(IIconConstants.MORTAL_ICON_TINY);

      }

      public void visitSidereal(CharacterType visitedType) {
        typeIcon[0] = resources.getImageIcon(IIconConstants.SIDEREAL_ICON_TINY);

      }

      public void visitDB(CharacterType visitedType) {
        typeIcon[0] = resources.getImageIcon(IIconConstants.DB_ICON_TINY);
      }

      public void visitAbyssal(CharacterType visitedType) {
        typeIcon[0] = resources.getImageIcon(IIconConstants.ABYSSAL_ICON_TINY);
      }

      public void visitLunar(CharacterType type) {
        typeIcon[0] = resources.getImageIcon(IIconConstants.LUNAR_ICON_TINY); 
      }
    });
    return typeIcon[0];
  }

  private DefaultTreeModel createTreeModel() {
    DefaultMutableTreeNode root = new DefaultMutableTreeNode(
        resources.getString("CharacterDialog.TemplateTree.RootNode"), //$NON-NLS-1$
        true);
    DefaultTreeModel treeModel = new DefaultTreeModel(root);
    for (CharacterType type : CharacterType.values()) {
      ICharacterTemplate[] templates = generics.getTemplateRegistry().getAllSupportedTemplates(type);
      if (templates.length == 0) {
        continue;
      }
      DefaultMutableTreeNode node = new DefaultMutableTreeNode(type);
      treeModel.insertNodeInto(node, root, root.getChildCount());
      
      
      for (ICharacterTemplate template : templates) {
        DefaultMutableTreeNode templateNode = new DefaultMutableTreeNode(template);
        treeModel.insertNodeInto(templateNode, node, node.getChildCount());
      }
    }
    return treeModel;
  }

  private synchronized void fireValueChangedEvent(TreeSelectionEvent e) {
    List<TreeSelectionListener> cloneList = new ArrayList<TreeSelectionListener>(listeners);
    for (TreeSelectionListener listener : cloneList) {
      listener.valueChanged(e);
    }
  }

  public JComponent getComponent() {
    return templateTree;
  }

  public boolean isTemplateSelected() {
    return getSelectedTemplate() != null;
  }

  public ICharacterTemplate getSelectedTemplate() {
    TreePath selectionPath = templateTree.getSelectionModel().getSelectionPath();
    if (selectionPath == null) {
      return null;
    }
    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectionPath.getLastPathComponent();
    Object userObject = selectedNode.getUserObject();
    if (userObject instanceof ICharacterTemplate) {
      return (ICharacterTemplate) userObject;
    }
    return null;
  }

  public void addTreeSelectionListener(TreeSelectionListener listener) {
    listeners.add(listener);
  }
}