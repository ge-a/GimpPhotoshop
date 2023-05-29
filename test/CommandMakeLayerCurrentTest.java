import static org.junit.Assert.assertEquals;

import cs3500.controller.CommandMakeLayerCurrent;
import cs3500.controller.IImageProcessingCommand;
import cs3500.model.IMultiImageProcessingModel;
import cs3500.model.MultiImageProcessingModel;
import org.junit.Test;
import org.junit.Before;

/**
 * Tests for the CommandMakeLayerCurrent command class.
 */
public class CommandMakeLayerCurrentTest {

  IMultiImageProcessingModel model;
  IImageProcessingCommand c;

  @Before
  public void init() {
    this.model = new MultiImageProcessingModel();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCommandMakeLayerCurrentNullLayerNumber() {
    this.c = new CommandMakeLayerCurrent(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOperateMakeLayerCurrentNulLModel() {
    this.c = new CommandMakeLayerCurrent("1");
    this.model.addEmptyLayer();
    this.c.operate(null);
  }

  @Test
  public void testOperateMakeFirstLayerCurrent() {
    this.c = new CommandMakeLayerCurrent("1");
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    assertEquals((Integer) 1, this.model.getCurrentOrder().get(0));
    this.c.operate(model);
    assertEquals((Integer) 0, this.model.getCurrentOrder().get(0));
  }

  @Test
  public void testOperateMakeSecondLayerCurrent() {
    this.c = new CommandMakeLayerCurrent("2");
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    assertEquals((Integer) 2, this.model.getCurrentOrder().get(0));
    this.c.operate(model);
    assertEquals((Integer) 1, this.model.getCurrentOrder().get(0));
  }

  @Test
  public void testOperateMakeAlreadyCurrentLayerCurrent() {
    this.c = new CommandMakeLayerCurrent("1");
    this.model.addEmptyLayer();
    assertEquals((Integer) 0, this.model.getCurrentOrder().get(0));
    this.c.operate(model);
    assertEquals((Integer) 0, this.model.getCurrentOrder().get(0));
  }


  @Test(expected = IllegalArgumentException.class)
  public void testOperateMakeNonExistingSmallLayerCurrent() {
    this.c = new CommandMakeLayerCurrent("-1");
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.c.operate(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOperateMakeLayerCurrentNoLayers() {
    this.c = new CommandMakeLayerCurrent("1");
    this.c.operate(model);
  }
}