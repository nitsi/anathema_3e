package net.sf.anathema.integrated;

import net.sf.anathema.framework.IAnathemaModel;
import net.sf.anathema.framework.view.perspective.Perspective;
import net.sf.anathema.framework.view.perspective.PerspectiveAutoCollector;
import net.sf.anathema.initialization.reflections.ReflectionObjectFactory;
import net.sf.anathema.initialization.reflections.Weight;
import net.sf.anathema.lib.resources.IResources;

import javax.swing.JComponent;

@PerspectiveAutoCollector
@Weight(weight = 9999)
public class IntegratedPerspective implements Perspective {

  @Override
  public String getTitle() {
    return "Integrated";
  }

  @Override
  public JComponent createContent(IAnathemaModel model, IResources resources, ReflectionObjectFactory instantiater) {
    IntegratedPerspectiveView view = new IntegratedPerspectiveView();
    new IntegratedPerspectivePresenter(model, view, resources,  instantiater).initPresentation();
    return view.createContent();
  }
}
