import cs3500.controller.CommandSaveImage;
import cs3500.controller.IImageProcessingCommand;
import cs3500.model.IMultiImageProcessingModel;
import cs3500.model.MultiImageProcessingModel;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the CommandSaveImage command class.
 */
public class CommandSaveImageTest {

  IMultiImageProcessingModel model;
  IImageProcessingCommand c;

  @Before
  public void init() {
    this.model = new MultiImageProcessingModel();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCommandSaveImageNullFilename() {
    this.c = new CommandSaveImage(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOperateSaveImageNullModel() {
    this.c = new CommandSaveImage("file");
    this.model.addEmptyLayer();
    this.c.operate(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOperateSaveImageInvalidFileType() {
    this.c = new CommandSaveImage("file.notarealfiletype");
    this.model.addEmptyLayer();
    this.c.operate(model);
  }
}