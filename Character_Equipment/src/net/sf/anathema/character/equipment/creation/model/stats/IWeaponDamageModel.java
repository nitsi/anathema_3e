package net.sf.anathema.character.equipment.creation.model.stats;

import net.sf.anathema.character.generic.health.HealthType;
import net.sf.anathema.lib.control.IChangeListener;
import net.sf.anathema.lib.workflow.intvalue.IIntValueModel;

public interface IWeaponDamageModel {

  IIntValueModel getDamageModel();
  
  IIntValueModel getMinDamageModel();
	
  HealthType getHealthType();

  void addHealthTypeChangeListener(IChangeListener listener);

  void setHealthType(HealthType healthType);
}