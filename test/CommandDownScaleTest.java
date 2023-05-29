import static org.junit.Assert.assertEquals;

import cs3500.IImage;
import cs3500.Image;
import cs3500.controller.CommandDownScale;
import cs3500.controller.IImageProcessingCommandDownScaleModel;
import cs3500.model.IMultiImageModelDownScaleSupport;
import cs3500.model.IMultiImageProcessingModel;
import cs3500.model.MultiImageModelDownScaleSupport;
import cs3500.model.MultiImageProcessingModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for the CommandDownScale class.
 */
public class CommandDownScaleTest {

  IMultiImageModelDownScaleSupport model;
  IMultiImageProcessingModel oldModel;
  IImageProcessingCommandDownScaleModel command;
  List<List<Integer>> pixels3x3;
  List<List<Integer>> pixels2x2;
  List<List<Integer>> pixels1x1;
  IImage image3x3;
  IImage image2x2;
  IImage image1x1;

  @Before
  public void init() {
    this.model = new MultiImageModelDownScaleSupport();
    this.oldModel = new MultiImageProcessingModel();
    this.pixels3x3 = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(255, 0, 75, 128, 45, 200, 0, 0, 0)),
        new ArrayList<>(Arrays.asList(190, 34, 134, 243, 0, 17, 120, 45, 200)),
        new ArrayList<>(Arrays.asList(20, 224, 34, 255, 255, 255, 200, 90, 110))));
    this.pixels2x2 = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(255, 120, 0, 255, 255, 255)),
        new ArrayList<>(Arrays.asList(190, 34, 134, 0, 126, 17))));
    this.pixels1x1 = new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(255, 120, 75))));
    this.image3x3 = new Image(this.pixels3x3, 3, 3);
    this.image2x2 = new Image(this.pixels2x2, 2, 2);
    this.image1x1 = new Image(this.pixels1x1, 1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void operateCheckThatPassingInOldModelThrowsException() {
    oldModel.addEmptyLayer();
    oldModel.loadNewImage(image2x2);
    this.command = new CommandDownScale("1","1");
    this.command.operate(oldModel);
  }

  @Test(expected = IllegalStateException.class)
  public void operateMultiImageNoImagesInModel() {
    model.addEmptyLayer();
    model.addEmptyLayer();
    this.command = new CommandDownScale("1","1");
    this.command.operate(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void operateNullWidth() {
    oldModel.addEmptyLayer();
    oldModel.loadNewImage(image2x2);
    this.command = new CommandDownScale(null,"1");
    this.command.operate(oldModel);
  }

  @Test(expected = IllegalArgumentException.class)
  public void operateNullHeight() {
    model.addEmptyLayer();
    model.addEmptyLayer();
    this.command = new CommandDownScale("1",null);
    this.command.operate(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void operateMultiImageWidthAndHeightLargerThanCurrentHeightAndWidth() {
    model.addEmptyLayer();
    model.addEmptyLayer();
    model.loadNewImage(image1x1);
    this.command = new CommandDownScale("2","2");
    this.command.operate(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void operateWrongStringInputWidth() {
    model.addEmptyLayer();
    model.addEmptyLayer();
    model.loadNewImage(image3x3);
    this.command = new CommandDownScale("sadf","2");
    this.command.operate(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void operateWrongStringInputHeight() {
    model.addEmptyLayer();
    model.addEmptyLayer();
    model.loadNewImage(image3x3);
    this.command = new CommandDownScale("2","sdf");
    this.command.operate(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void operateMultiImageNegativeNewWidth() {
    model.addEmptyLayer();
    model.addEmptyLayer();
    model.loadNewImage(image2x2);
    this.command = new CommandDownScale("-1","1");
    this.command.operate(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void operateMultiImageNegativeNewHeight() {
    model.addEmptyLayer();
    model.addEmptyLayer();
    model.loadNewImage(image2x2);
    this.command = new CommandDownScale("1","-1");
    this.command.operate(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void operateMultiImageSameProportions() {
    model.addEmptyLayer();
    model.addEmptyLayer();
    model.loadNewImage(image2x2);
    this.command = new CommandDownScale("2","2");
    this.command.operate(model);
  }

  @Test
  public void opearteMultiImageValid3x3To2x2() {
    model.addEmptyLayer();
    model.addEmptyLayer();
    model.loadNewImage(image3x3);
    assertEquals(3, model.getTopmostVisible().getHeight());
    assertEquals(3, model.getTopmostVisible().getWidth());
    this.command = new CommandDownScale("2","2");
    this.command.operate(model);
    assertEquals(2, model.getTopmostVisible().getWidth());
    assertEquals(2, model.getTopmostVisible().getHeight());
    ArrayList<Integer> first = new ArrayList<Integer>(Arrays.asList(255,0,75,64,22,100));
    ArrayList<Integer> second = new ArrayList<Integer>(Arrays.asList(105,129,84,204,97,145));
    ArrayList<ArrayList<Integer>> pixels = new ArrayList<ArrayList<Integer>>();
    pixels.add(first);
    pixels.add(second);
    assertEquals(pixels,
        model.getTopmostVisible().getPixels());
  }

  @Test
  public void opearteMultiImageValid3x3To2x2NoEmptyLayers() {
    model.addEmptyLayer();
    model.loadNewImage(image3x3);
    assertEquals(3, model.getTopmostVisible().getHeight());
    assertEquals(3, model.getTopmostVisible().getWidth());
    this.command = new CommandDownScale("2","2");
    this.command.operate(model);
    assertEquals(2, model.getTopmostVisible().getWidth());
    assertEquals(2, model.getTopmostVisible().getHeight());
    ArrayList<Integer> first = new ArrayList<Integer>(Arrays.asList(255,0,75,64,22,100));
    ArrayList<Integer> second = new ArrayList<Integer>(Arrays.asList(105,129,84,204,97,145));
    ArrayList<ArrayList<Integer>> pixels = new ArrayList<ArrayList<Integer>>();
    pixels.add(first);
    pixels.add(second);
    assertEquals(pixels,
        model.getTopmostVisible().getPixels());
  }

  @Test
  public void opearteMultiImageValidMultipleImages() {
    model.addEmptyLayer();
    model.addEmptyLayer();
    model.loadNewImage(image3x3);
    model.updateCurrentLayer(1);
    model.loadNewImage(image3x3);
    assertEquals(3, model.getTopmostVisible().getHeight());
    assertEquals(3, model.getTopmostVisible().getWidth());
    this.command = new CommandDownScale("1","1");
    this.command.operate(model);
    assertEquals(1, model.getLayers().get(0).getImage().getWidth());
    assertEquals(1, model.getLayers().get(0).getImage().getHeight());
    assertEquals(1, model.getLayers().get(1).getImage().getWidth());
    assertEquals(1, model.getLayers().get(1).getImage().getHeight());
    ArrayList<Integer> first = new ArrayList<Integer>(Arrays.asList(255,0,75));
    ArrayList<ArrayList<Integer>> pixels = new ArrayList<ArrayList<Integer>>();
    pixels.add(first);
    assertEquals(pixels,
        model.getLayers().get(0).getImage().getPixels());
    assertEquals(pixels,
        model.getLayers().get(1).getImage().getPixels());
    assertEquals(2, model.getLayers().size());
  }

  @Test
  public void opearteMultiImageValidMultipleImagesAndEmptyLayers() {
    model.addEmptyLayer();
    model.addEmptyLayer();
    model.loadNewImage(image3x3);
    model.updateCurrentLayer(1);
    model.loadNewImage(image3x3);
    model.addEmptyLayer();
    model.addEmptyLayer();
    assertEquals(3, model.getTopmostVisible().getHeight());
    assertEquals(3, model.getTopmostVisible().getWidth());
    this.command = new CommandDownScale("1","1");
    this.command.operate(model);
    assertEquals(1, model.getLayers().get(0).getImage().getWidth());
    assertEquals(1, model.getLayers().get(0).getImage().getHeight());
    assertEquals(1, model.getLayers().get(1).getImage().getWidth());
    assertEquals(1, model.getLayers().get(1).getImage().getHeight());
    ArrayList<Integer> first = new ArrayList<Integer>(Arrays.asList(255,0,75));
    ArrayList<ArrayList<Integer>> pixels = new ArrayList<ArrayList<Integer>>();
    pixels.add(first);
    assertEquals(pixels,
        model.getLayers().get(0).getImage().getPixels());
    assertEquals(pixels,
        model.getLayers().get(1).getImage().getPixels());
    assertEquals(4, model.getLayers().size());
  }
}