import static org.junit.Assert.assertEquals;

import cs3500.controller.CommandCreateNewCheckerboard;
import cs3500.controller.IImageProcessingCommand;
import cs3500.model.IMultiImageProcessingModel;
import cs3500.model.MultiImageProcessingModel;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the CommandCreateNewCheckerboard command class.
 */
public class CommandCreateNewCheckerboardTest {

  IMultiImageProcessingModel model;
  IImageProcessingCommand c;

  @Before
  public void init() {
    this.model = new MultiImageProcessingModel();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCommandCreateNewCheckerBoardNullTileSize() {
    this.c = new CommandCreateNewCheckerboard(null, "3", "3");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCommandCreateNewCheckerBoardNullNumTilesInRow() {
    this.c = new CommandCreateNewCheckerboard("2", null, "3");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCommandCreateNewCheckerBoardNullNumTilesInCol() {
    this.c = new CommandCreateNewCheckerboard("2", "3", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOperateCreateNewCheckerBoardNullModel() {
    this.c = new CommandCreateNewCheckerboard("2", "3", "3");
    this.c.operate(null);
  }

  @Test
  public void testOperateCreateNewCheckerBoard2x2CheckerBoard() {
    this.model.addEmptyLayer();
    this.c = new CommandCreateNewCheckerboard("2", "2", "2");
    this.c.operate(this.model);
    ArrayList<ArrayList<Integer>> pixels = new ArrayList<>();
    pixels.add(new ArrayList<>(Arrays.asList(255, 255, 255, 255, 255, 255, 0, 0, 0, 0, 0, 0)));
    pixels.add(new ArrayList<>(Arrays.asList(255, 255, 255, 255, 255, 255, 0, 0, 0, 0, 0, 0)));
    pixels.add(new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 255, 255, 255, 255, 255, 255)));
    pixels.add(new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 255, 255, 255, 255, 255, 255)));
    assertEquals(pixels, this.model.getCurrentImage().getPixels());
  }


  @Test
  public void testOperateCreateNewCheckerBoard3x3CheckerBoard() {
    this.model.addEmptyLayer();
    this.c = new CommandCreateNewCheckerboard("3", "3", "3");
    this.c.operate(this.model);
    ArrayList<ArrayList<Integer>> pixels = new ArrayList<>();
    pixels.add(new ArrayList<>(Arrays.asList(255, 255, 255, 255, 255, 255, 255, 255, 255,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255)));
    pixels.add(new ArrayList<>(Arrays.asList(255, 255, 255, 255, 255, 255, 255, 255, 255,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255)));
    pixels.add(new ArrayList<>(Arrays.asList(255, 255, 255, 255, 255, 255, 255, 255, 255,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255)));
    pixels.add(new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 255, 255, 255, 255,
        255, 255, 255, 255, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0)));
    pixels.add(new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 255, 255, 255, 255,
        255, 255, 255, 255, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0)));
    pixels.add(new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 255, 255, 255, 255,
        255, 255, 255, 255, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0)));
    pixels.add(new ArrayList<>(Arrays.asList(255, 255, 255, 255, 255, 255, 255, 255, 255, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255)));
    pixels.add(new ArrayList<>(Arrays.asList(255, 255, 255, 255, 255, 255, 255, 255, 255, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255)));
    pixels.add(new ArrayList<>(Arrays.asList(255, 255, 255, 255, 255, 255, 255, 255, 255, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255)));
    assertEquals(pixels, this.model.getCurrentImage().getPixels());
  }

  @Test(expected = IllegalStateException.class)
  public void testOperateCreateNewCheckerboardNoLayers() {
    this.c = new CommandCreateNewCheckerboard("3", "3" ,"3");
    this.c.operate(this.model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOperateCreateNewCheckerboardUnevenSizes() {
    this.model.addEmptyLayer();
    this.c = new CommandCreateNewCheckerboard("3", "3" ,"3");
    this.c.operate(this.model);
    this.model.addEmptyLayer();
    this.c = new CommandCreateNewCheckerboard("2", "2", "2");
    this.c.operate(this.model);
  }

  @Test(expected = IllegalStateException.class)
  public void testOperateCreateNewCheckerboardOnExistingImage() {
    this.model.addEmptyLayer();
    this.c = new CommandCreateNewCheckerboard("3", "3" ,"3");
    this.c.operate(this.model);
    this.c = new CommandCreateNewCheckerboard("3", "3", "3");
    this.c.operate(this.model);
  }
}