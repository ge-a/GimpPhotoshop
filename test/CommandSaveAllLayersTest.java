
import cs3500.controller.CommandSaveAllLayers;
import cs3500.controller.IImageProcessingCommand;
import cs3500.model.IMultiImageProcessingModel;
import cs3500.model.MultiImageProcessingModel;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for CommandSaveAllLayers command class.
 */
public class CommandSaveAllLayersTest {

  IMultiImageProcessingModel model;
  IImageProcessingCommand c;

  @Before
  public void init() {
    this.model = new MultiImageProcessingModel();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCommandSaveAllLayersNullFiletype() {
    this.c = new CommandSaveAllLayers(null, "notAFolder");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCommandSaveAllLayersNullFolderName() {
    this.c = new CommandSaveAllLayers("ppm", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOperateSaveAllLayersNullModel() {
    this.c = new CommandSaveAllLayers("png", "noFolder");
    this.model.addEmptyLayer();
    this.c.operate(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOperateSaveAllLayersInvalidFiletype() {
    this.c = new CommandSaveAllLayers("ppp", "noFolder");
    this.model.addEmptyLayer();
    this.c.operate(this.model);
  }
}