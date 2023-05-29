import static org.junit.Assert.assertEquals;

import cs3500.controller.IImageProcessingController;
import cs3500.controller.ImageProcessingController;
import cs3500.model.IMultiImageProcessingModel;
import cs3500.model.MultiImageProcessingModel;
import cs3500.view.IImageProcessingView;
import cs3500.view.ImageProcessingView;
import java.io.StringReader;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the ImageProcessingController class.
 */
public class ImageProcessingControllerTest {
  StringBuilder inputText;
  Appendable output;
  Readable input;
  IImageProcessingView view;
  IMultiImageProcessingModel mockModel;
  IMultiImageProcessingModel actualModel;
  IImageProcessingController controller;

  @Before
  public void initData() {
    inputText = new StringBuilder();
    output = new StringBuilder();
    view = new ImageProcessingView(output);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testControllerConstructorNullModelInput() {
    new ImageProcessingController(null,new StringReader(inputText.toString()),view);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testControllerConstructorNullReadableInput() {
    inputText.append("create");
    input = new StringReader(inputText.toString());
    mockModel = new MockModelMultiImageProcessing(output);
    new ImageProcessingController(mockModel,null,view);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testControllerConstructorNullAppendableInput() {
    inputText.append("create");
    input = new StringReader(inputText.toString());
    mockModel = new MockModelMultiImageProcessing(output);
    new ImageProcessingController(mockModel,input,null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testControllerConstructorTwoInputsNullReadable() {
    inputText.append("create");
    input = new StringReader(inputText.toString());
    mockModel = new MockModelMultiImageProcessing(output);
    new ImageProcessingController(null,output);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testControllerConstructorTwoInputsNullAppendable() {
    inputText.append("create");
    input = new StringReader(inputText.toString());
    mockModel = new MockModelMultiImageProcessing(output);
    new ImageProcessingController(input,null);
  }

  @Test
  public void runMockPhotoshopCreateNewLayer() {
    inputText.append("create");
    input = new StringReader(inputText.toString());
    mockModel = new MockModelMultiImageProcessing(output);
    controller = new ImageProcessingController(mockModel, input, view);
    controller.runPhotoshop();
    assertEquals("Layer added Created layer number 0\n", output.toString());
  }

  @Test
  public void runMockPhotoshopCreateNewLayerSetCurrent() {
    inputText.append("create current 1");
    input = new StringReader(inputText.toString());
    mockModel = new MockModelMultiImageProcessing(output);
    controller = new ImageProcessingController(mockModel, input, view);
    controller.runPhotoshop();
    assertEquals("Layer added Created layer number 0\n"
        + "Current Layer updated to 1", output.toString());
  }

  @Test
  public void runMockPhotoshopCreateMultipleLayersRemoveOneLayer() {
    inputText.append("create create remove 1");
    input = new StringReader(inputText.toString());
    mockModel = new MockModelMultiImageProcessing(output);
    controller = new ImageProcessingController(mockModel, input, view);
    controller.runPhotoshop();
    assertEquals("Layer added Created layer number 0\n"
        + "Layer added Created layer number 0\n"
        + "Layer1removed", output.toString());
  }

  @Test
  public void runMockPhotoshopInvalidCommand() {
    inputText.append("create create command");
    input = new StringReader(inputText.toString());
    mockModel = new MockModelMultiImageProcessing(output);
    controller = new ImageProcessingController(mockModel, input, view);
    controller.runPhotoshop();
    assertEquals("Layer added Created layer number 0\n"
        + "Layer added Created layer number 0\n"
        + "this command does not exist!", output.toString());
  }

  @Test
  public void runMockPhotoshopCreateLayerMakeVisible() {
    inputText.append("create visible 1");
    input = new StringReader(inputText.toString());
    mockModel = new MockModelMultiImageProcessing(output);
    controller = new ImageProcessingController(mockModel, input, view);
    controller.runPhotoshop();
    assertEquals("Layer added Created layer number 0\n"
        + "Transparency updated 1Status: true", output.toString());
  }

  @Test
  public void runMockPhotoshopCreateLayerMakeVisibleMakeInvisible() {
    inputText.append("create visible 1 invisible 1");
    input = new StringReader(inputText.toString());
    mockModel = new MockModelMultiImageProcessing(output);
    controller = new ImageProcessingController(mockModel, input, view);
    controller.runPhotoshop();
    assertEquals("Layer added Created layer number 0\n"
        + "Transparency updated 1Status: trueTransparency updated 1Status: false",
        output.toString());
  }

  @Test
  public void runPhotoshopActualInvalidLoadImage() {
    inputText.append("load random.png");
    input = new StringReader(inputText.toString());
    actualModel = new MultiImageProcessingModel();
    controller = new ImageProcessingController(actualModel, input, view);
    controller.runPhotoshop();
    assertEquals("File cannot be accessed",
        output.toString());
  }

  @Test
  public void runPhotoshopActualInvalidCallToCurrent() {
    inputText.append("current 2");
    input = new StringReader(inputText.toString());
    actualModel = new MultiImageProcessingModel();
    controller = new ImageProcessingController(actualModel, input, view);
    controller.runPhotoshop();
    assertEquals("Invalid current layer!!",
        output.toString());
  }

  @Test
  public void runPhotoshopActualInvalidCallToCurrentAfterCreation() {
    inputText.append("create create current 0");
    input = new StringReader(inputText.toString());
    actualModel = new MultiImageProcessingModel();
    controller = new ImageProcessingController(actualModel, input, view);
    controller.runPhotoshop();
    assertEquals("Created layer number 1\n"
            + "Created layer number 2\n"
            + "Invalid current layer!!",
        output.toString());
  }

  @Test
  public void runPhotoshopActualInvalidCallToCurrentAfterRemoval() {
    inputText.append("create create remove 2 current 2");
    input = new StringReader(inputText.toString());
    actualModel = new MultiImageProcessingModel();
    controller = new ImageProcessingController(actualModel, input, view);
    controller.runPhotoshop();
    assertEquals("Created layer number 1\n"
            + "Created layer number 2\n"
            + "Invalid current layer!!",
        output.toString());
  }

  @Test
  public void runPhotoshopActualValidCallToCurrentAfterRemoval() {
    inputText.append("create create remove 2 current 2");
    input = new StringReader(inputText.toString());
    actualModel = new MultiImageProcessingModel();
    controller = new ImageProcessingController(actualModel, input, view);
    controller.runPhotoshop();
    assertEquals("Created layer number 1\n"
            + "Created layer number 2\n"
            + "Invalid current layer!!",
        output.toString());
  }

  @Test
  public void runPhotoshopActualBlurNonexistentPhoto() {
    inputText.append("create create blur");
    input = new StringReader(inputText.toString());
    actualModel = new MultiImageProcessingModel();
    controller = new ImageProcessingController(actualModel, input, view);
    controller.runPhotoshop();
    assertEquals("Created layer number 1\n"
            + "Created layer number 2\n",
        output.toString());
  }

  @Test
  public void runPhotoshopActualSharpenNonexistentPhoto() {
    inputText.append("create create sharpen");
    input = new StringReader(inputText.toString());
    actualModel = new MultiImageProcessingModel();
    controller = new ImageProcessingController(actualModel, input, view);
    controller.runPhotoshop();
    assertEquals("Created layer number 1\n"
            + "Created layer number 2\n",
        output.toString());
  }

  @Test
  public void runPhotoshopActualSepiaNonexistentPhoto() {
    inputText.append("create create sepia");
    input = new StringReader(inputText.toString());
    actualModel = new MultiImageProcessingModel();
    controller = new ImageProcessingController(actualModel, input, view);
    controller.runPhotoshop();
    assertEquals("Created layer number 1\n"
            + "Created layer number 2\n",
        output.toString());
  }

  @Test
  public void runPhotoshopActualGreyscaleNonexistentPhoto() {
    inputText.append("create create greyscale");
    input = new StringReader(inputText.toString());
    actualModel = new MultiImageProcessingModel();
    controller = new ImageProcessingController(actualModel, input, view);
    controller.runPhotoshop();
    assertEquals("Created layer number 1\n"
            + "Created layer number 2\n",
        output.toString());
  }





}