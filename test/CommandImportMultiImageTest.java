import cs3500.controller.CommandImportMultiImage;
import cs3500.controller.IImageProcessingCommand;
import cs3500.model.IMultiImageProcessingModel;
import cs3500.model.MultiImageProcessingModel;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the CommandImportMultiImage command class.
 */
public class CommandImportMultiImageTest {

  IMultiImageProcessingModel model;
  IImageProcessingCommand c;

  @Before
  public void init() {
    this.model = new MultiImageProcessingModel();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCommandImportMultiImageNullFilename() {
    this.c = new CommandImportMultiImage(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOperateImportMultiImageNullModel() {
    this.c = new CommandImportMultiImage("filename");
    this.c.operate(null);
  }
}