import cs3500.controller.CommandLoadImage;
import cs3500.controller.IImageProcessingCommand;
import cs3500.model.IMultiImageProcessingModel;
import cs3500.model.MultiImageProcessingModel;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the CommandLoadImage command class.
 */
public class CommandLoadImageTest {

  IMultiImageProcessingModel model;
  IImageProcessingCommand c;

  @Before
  public void init() {
    this.model = new MultiImageProcessingModel();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCommandLoadImageNullFilename() {
    this.c = new CommandLoadImage(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOperateLoadImageNullModel() {
    this.c = new CommandLoadImage("file");
    this.model.addEmptyLayer();
    this.c.operate(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOperateLoadImageInvalidFileType() {
    this.c = new CommandLoadImage("file.notarealfiletype");
    this.model.addEmptyLayer();
    this.c.operate(model);
  }
}