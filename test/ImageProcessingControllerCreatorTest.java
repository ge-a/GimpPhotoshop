import static org.junit.Assert.assertTrue;


import cs3500.controller.ImageProcessingController;
import cs3500.controller.ImageProcessingControllerCreator;
import cs3500.controller.ImageProcessingControllerCreator.ControllerType;
import cs3500.controller.ImageProcessingGUIController;
import cs3500.model.IMultiImageModelDownScaleSupport;
import cs3500.model.MultiImageModelDownScaleSupport;
import cs3500.model.MultiImageProcessingModel;
import java.io.StringReader;
import org.junit.Test;

/**
 * Tests for the ImageProcessingController factory class.
 */
public class ImageProcessingControllerCreatorTest {

  @Test
  public void createScriptingAndManualInput() {
    assertTrue(ImageProcessingControllerCreator.create(
        ControllerType.SCRIPTINGANDMANUALINPUT)
        instanceof ImageProcessingController);

  }

  @Test(expected = IllegalArgumentException.class)
  public void createInvalidArgument() {
    assertTrue(ImageProcessingControllerCreator.create(null)
        instanceof ImageProcessingController);
  }


  @Test
  public void createScriptingAndGui() {
    IMultiImageModelDownScaleSupport model = new MultiImageModelDownScaleSupport();
    assertTrue(ImageProcessingControllerCreator.create(ControllerType.GUI)
        instanceof ImageProcessingGUIController);
  }

  @Test
  public void createControllerSameModel() {
    assertTrue(ImageProcessingControllerCreator
        .createControllerSameReadable(ControllerType.SCRIPTINGANDMANUALINPUT,
            new MultiImageProcessingModel(), new StringReader(""))
        instanceof ImageProcessingController);
  }

  @Test(expected = IllegalArgumentException.class)
  public void createControllerSameModelNullModel() {
    assertTrue(ImageProcessingControllerCreator
        .createControllerSameReadable(ControllerType.SCRIPTINGANDMANUALINPUT,
            null, new StringReader(""))
        instanceof ImageProcessingController);
  }

  @Test(expected = IllegalArgumentException.class)
  public void createControllerSameModelNullType() {
    assertTrue(ImageProcessingControllerCreator
        .createControllerSameReadable(null,
            new MultiImageProcessingModel(), new StringReader(""))
        instanceof ImageProcessingController);
  }

  @Test(expected = IllegalArgumentException.class)
  public void createControllerSameModelNullReader() {
    assertTrue(ImageProcessingControllerCreator
        .createControllerSameReadable(ControllerType.SCRIPTINGANDMANUALINPUT,
            new MultiImageProcessingModel(), null)
        instanceof ImageProcessingController);
  }
}