package net.sf.anathema.platform.svgtree.document.visualizer;

import net.sf.anathema.graph.graph.LayeredGraph;
import net.sf.anathema.graph.nodes.ISimpleNode;
import net.sf.anathema.platform.svgtree.document.components.ILayer;
import net.sf.anathema.platform.svgtree.document.components.IVisualizableNode;

import java.awt.Dimension;

public abstract class AbstractTreeVisualizer extends AbstractCascadeVisualizer {

  public AbstractTreeVisualizer(ITreePresentationProperties properties, LayeredGraph graph, VisualizedGraphFactory factory) {
    super(properties, graph, factory);
  }

  private void createVisualizableNodes(int layerIndex) {
    ISimpleNode[] layerNodes = getGraph().getNodesByLayer(layerIndex + 1);
    for (ISimpleNode currentNode : layerNodes) {
      getNodeFactory().registerVisualizableNode(currentNode);
    }
  }

  @Override
  public IVisualizedGraph buildTree() {
    int layerCount = getGraph().getDeepestLayer();
    for (int layerIndex = layerCount - 1; layerIndex >= 0; layerIndex--) {
      createVisualizableNodes(layerIndex);
    }
    ILayer[] layers = createLayers(layerCount);
    int treeWidth = setPositionRecursively(getInitialLayer(layers).getNodes()[0],
            0) - getProperties().getGapDimension().width;
    Dimension dimension = new Dimension(treeWidth, new TreeDimensionCalculator(getProperties()).getTreeHeight(layers));
    return getGraphFactory().createWithDimension(layers, dimension);
  }

  private int setPositionRecursively(IVisualizableNode node, int rightSide) {
    IVisualizableNode[] relatives = getRelatives(node);
    if (relatives.length == 0) {
      node.setPosition(rightSide + node.getWidth() / 2);
      return node.getRightSide() + getProperties().getGapDimension().width;
    }
    for (IVisualizableNode relative : relatives) {
      rightSide = setPositionRecursively(relative, rightSide);
    }
    node.setPosition((relatives[0].getPosition() + relatives[relatives.length - 1].getPosition()) / 2);
    return rightSide;
  }

  protected abstract ILayer getInitialLayer(ILayer[] layers);

  protected abstract IVisualizableNode[] getRelatives(IVisualizableNode node);
}