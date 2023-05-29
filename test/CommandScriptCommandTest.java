
import cs3500.controller.CommandScriptCommand;
import cs3500.controller.IImageProcessingCommand;
import cs3500.model.IMultiImageProcessingModel;
import cs3500.model.MultiImageProcessingModel;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the CommandScriptCommand command class.
 */
public class CommandScriptCommandTest {

  IMultiImageProcessingModel model;
  IImageProcessingCommand c;

  @Before
  public void init() {
    this.model = new MultiImageProcessingModel();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCommandScriptCommandNullFilename() {
    this.c = new CommandScriptCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOperateScriptCommandNullModel() {
    this.c = new CommandScriptCommand("file.txt");
    this.model.addEmptyLayer();
    this.c.operate(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOperateScriptCommandInvalidFileType() {
    this.c = new CommandScriptCommand("file.notarealfiletype");
    this.model.addEmptyLayer();
    this.c.operate(model);
  }
}