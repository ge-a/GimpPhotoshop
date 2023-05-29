import static org.junit.Assert.assertTrue;

import cs3500.IImage;
import cs3500.Image;
import cs3500.controller.CommandMakeVisible;
import cs3500.controller.IImageProcessingCommand;
import cs3500.model.IMultiImageProcessingModel;
import cs3500.model.MultiImageProcessingModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the CommandMakeVisible command class.
 */
public class CommandMakeVisibleTest {
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
  public void testCommandMakeVisibleNullLayerNumber() {
    this.c = new CommandMakeVisible(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOperateMakeVisibleNullModel() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image2x2);
    this.c = new CommandMakeVisible("1");
    this.c.operate(null);
  }

  @Test
  public void testOperateMakeVisibleFirstLayer() {
    this.model.addEmptyLayer();
    this.c = new CommandMakeVisible("1");
    this.c.operate(model);
    assertTrue(this.model.getLayers().get(0).getVisibility());
  }

  @Test
  public void testOperateMakeVisibleSecondLayer() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image2x2);
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image2x2);
    this.model.addEmptyLayer();
    this.model.updateTransparency(2, false);
    this.c = new CommandMakeVisible("2");
    this.c.operate(model);
    assertTrue(this.model.getLayers().get(1).getVisibility());
  }

  @Test
  public void testOperateMakeVisibleVisibleLayer() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image3x3);
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image3x3);
    this.c = new CommandMakeVisible("2");
    this.c.operate(model);
    assertTrue(this.model.getLayers().get(1).getVisibility());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOperateMakeVisibleNonExistingLargeLayer() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image3x3);
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image3x3);
    this.model.addEmptyLayer();
    this.c = new CommandMakeVisible("5");
    this.c.operate(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOperateMakeVisibleNonExistingSmallLayer() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image3x3);
    this.model.addEmptyLayer();
    this.c = new CommandMakeVisible("-1");
    this.c.operate(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOperateMakeVisibleNoLayers() {
    this.c = new CommandMakeVisible("1");
    this.c.operate(model);
  }
}