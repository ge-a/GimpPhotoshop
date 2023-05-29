import static org.junit.Assert.assertEquals;

import cs3500.controller.CommandAddLayer;
import cs3500.controller.IImageProcessingCommand;
import cs3500.model.IMultiImageProcessingModel;
import cs3500.model.MultiImageProcessingModel;
import org.junit.Test;
import org.junit.Before;

/**
 * Tests for the CommandAddLayer command class.
 */
public class CommandAddLayerTest {

  IMultiImageProcessingModel model;
  IImageProcessingCommand c;

  @Before
  public void init() {
    this.model = new MultiImageProcessingModel();
    this.c = new CommandAddLayer();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOperateAddLayerNullModel() {
    this.c.operate(null);
  }

  @Test
  public void testOperateAdd1Layer() {
    this.c.operate(this.model);
    assertEquals(1, this.model.getLayers().size());
  }

  @Test
  public void testOperateAdd2Layers() {
    this.c.operate(this.model);
    this.c.operate(this.model);
    assertEquals(2, this.model.getLayers().size());
  }

  @Test
  public void testOperateAdd3Layers() {
    this.c.operate(this.model);
    this.c.operate(this.model);
    this.c.operate(this.model);
    assertEquals(3, this.model.getLayers().size());
  }
}