import static org.junit.Assert.assertEquals;

import cs3500.IImage;
import cs3500.Image;
import cs3500.controller.CommandSepia;
import cs3500.controller.IImageProcessingCommand;
import cs3500.model.IMultiImageProcessingModel;
import cs3500.model.MultiImageProcessingModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the CommandSepia command class.
 */
public class CommandSepiaTest {

  IMultiImageProcessingModel model;
  IImageProcessingCommand c;
  List<List<Integer>> pixels3x3;
  List<List<Integer>> pixels2x2;
  List<List<Integer>> pixels1x1;
  IImage image3x3;
  IImage image2x2;
  IImage image1x1;

  @Before
  public void init() {
    this.model = new MultiImageProcessingModel();
    this.c = new CommandSepia();
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
  public void testOperateSepiaNullModel() {
    this.model.addEmptyLayer();
    this.c.operate(null);
  }

  @Test
  public void testOperateSepia1x1Image() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(image1x1);
    this.c.operate(this.model);
    ArrayList<ArrayList<Integer>> pixels = new ArrayList<>();
    pixels.add(new ArrayList<>(Arrays.asList(206, 183, 143)));
    assertEquals(pixels, this.model.getCurrentImage().getPixels());
  }

  @Test
  public void testOperateSepia2x2Image() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(image2x2);
    this.c.operate(this.model);
    ArrayList<ArrayList<Integer>> pixels = new ArrayList<>();
    pixels.add(new ArrayList<>(Arrays.asList(192, 171, 133, 255, 255, 238)));
    pixels.add(new ArrayList<>(Arrays.asList(126, 112, 87, 100, 89, 69)));
    assertEquals(pixels, this.model.getCurrentImage().getPixels());
  }

  @Test
  public void testOperateSepia3x3Image() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(image3x3);
    this.c.operate(this.model);
    ArrayList<ArrayList<Integer>> pixels = new ArrayList<>();
    pixels.add(new ArrayList<>(Arrays.asList(114, 101, 79, 122, 109, 85, 0, 0, 0)));
    pixels.add(new ArrayList<>(Arrays.asList(126, 112, 87, 98, 87, 68, 119, 106, 82)));
    pixels.add(new ArrayList<>(Arrays.asList(186, 166, 129, 255, 255, 238, 168, 150, 116)));
    assertEquals(pixels, this.model.getCurrentImage().getPixels());
  }

  @Test(expected = IllegalStateException.class)
  public void testOperateBlur2x2ImageNoLayers() {
    this.c.operate(this.model);
  }
}