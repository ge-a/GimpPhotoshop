import static org.junit.Assert.assertEquals;

import cs3500.controller.CommandRemoveLayer;
import cs3500.controller.IImageProcessingCommand;
import cs3500.model.IMultiImageProcessingModel;
import cs3500.model.MultiImageProcessingModel;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the CommandRemoveLayer command class.
 */
public class CommandRemoveLayerTest {

  IMultiImageProcessingModel model;
  IImageProcessingCommand c;

  @Before
  public void init() {
    this.model = new MultiImageProcessingModel();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCommandRemoveLayerNullLayerNumber() {
    this.c = new CommandRemoveLayer(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOperateRemoveLayerNullModel() {
    this.model.addEmptyLayer();
    this.c = new CommandRemoveLayer("1");
    this.c.operate(null);
  }

  @Test
  public void testOperateRemoveLayerFirstLayer() {
    this.model.addEmptyLayer();
    this.c = new CommandRemoveLayer("1");
    this.c.operate(this.model);
    assertEquals(0, this.model.getLayers().size());
  }

  @Test
  public void testOperateRemoveLayerSecondLayer() {
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.c = new CommandRemoveLayer("2");
    assertEquals((Integer) 2, this.model.getCurrentOrder().get(0));
    this.c.operate(this.model);
    assertEquals(2, this.model.getLayers().size());
    assertEquals((Integer) 1, this.model.getCurrentOrder().get(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOperateRemoveRemovedLayer() {
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.c = new CommandRemoveLayer("2");
    this.c.operate(this.model);
    this.c = new CommandRemoveLayer("3");
    this.c.operate(this.model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOperateRemoveNonExistantLargeLayer() {
    this.model.addEmptyLayer();
    this.c = new CommandRemoveLayer("2");
    this.c.operate(this.model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOperateRemoveNonExistantSmallLayer() {
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.c = new CommandRemoveLayer("0");
    this.c.operate(this.model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOperateRemoveNoLayers() {
    this.c = new CommandRemoveLayer("1");
    this.c.operate(this.model);
  }
}