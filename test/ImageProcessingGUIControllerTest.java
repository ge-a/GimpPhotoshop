import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cs3500.IImage;
import cs3500.Image;
import cs3500.controller.ImageProcessingGUIController;
import cs3500.model.IMultiImageModelDownScaleSupport;
import cs3500.model.IMultiImageProcessingModel;
import cs3500.model.MultiImageModelDownScaleSupport;
import cs3500.view.IView;
import cs3500.view.IViewListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.Before;

/**
 * Tests for the ImageProcessingGUIController class.
 */
public class ImageProcessingGUIControllerTest {

  IMultiImageProcessingModel oldModel;
  IMultiImageModelDownScaleSupport model;
  IView view;
  IViewListener controller;
  List<List<Integer>> pixels3x3;
  List<List<Integer>> pixels2x2;
  List<List<Integer>> pixels1x1;
  IImage image3x3;
  IImage image2x2;
  IImage image1x1;
  Appendable ap;

  @Before
  public void init() {
    this.oldModel = new MultiImageModelDownScaleSupport();
    this.model = new MultiImageModelDownScaleSupport();
    this.ap = new StringBuilder();
    this.view = new MockView(this.ap);
    this.controller = new ImageProcessingGUIController(this.model, this.view);
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
  public void testImageProcessingGUIControllerNullModel() {
    this.controller = new ImageProcessingGUIController(null, this.view);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImageProcessingGUIControllerNulLView() {
    this.controller = new ImageProcessingGUIController(this.model, null);
  }

  @Test
  public void testHandleCreateEventAdd1Layer() {
    this.controller.handleCreateEvent();
    assertEquals(1, this.model.getLayers().size());
  }

  @Test
  public void testHandleCreateEventAdd2Layers() {
    this.controller.handleCreateEvent();
    this.controller.handleCreateEvent();
    assertEquals(2, this.model.getLayers().size());
  }

  @Test
  public void testHandleCreateEventAdd3Layers() {
    this.controller.handleCreateEvent();
    this.controller.handleCreateEvent();
    this.controller.handleCreateEvent();
    assertEquals(3, this.model.getLayers().size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHandleLoadEventNullFilename() {
    this.controller.handleLoadEvent(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHandleCurrentEventNullLayerNumber() {
    this.controller.handleCurrentEvent(null);
  }

  @Test
  public void testHandleCurrentEventMakeFirstLayerCurrent() {
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    assertEquals((Integer) 1, this.model.getCurrentOrder().get(0));
    this.controller.handleCurrentEvent("1");
    assertEquals((Integer) 0, this.model.getCurrentOrder().get(0));
  }

  @Test
  public void testHandleCurrentEventMakeSecondLayerCurrent() {
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    assertEquals((Integer) 2, this.model.getCurrentOrder().get(0));
    this.controller.handleCurrentEvent("2");
    assertEquals((Integer) 1, this.model.getCurrentOrder().get(0));
  }

  @Test
  public void testHandleCurrentEventMakeAlreadyCurrentLayerCurrent() {
    this.model.addEmptyLayer();
    assertEquals((Integer) 0, this.model.getCurrentOrder().get(0));
    this.controller.handleCurrentEvent("1");
    assertEquals((Integer) 0, this.model.getCurrentOrder().get(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHandleInvisibleEventNullLayerNumber() {
    this.controller.handleInvisibleEvent(null);
  }

  @Test
  public void testHandleInvisibleEventMakeInvisibleFirstLayer() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image1x1);
    this.controller.handleInvisibleEvent("1");
    assertFalse(this.model.getLayers().get(0).getVisibility());
  }

  @Test
  public void testHandleInvisibleEventMakeInvisibleSecondLayer() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image2x2);
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image2x2);
    this.model.addEmptyLayer();
    this.controller.handleInvisibleEvent("2");
    assertFalse(this.model.getLayers().get(1).getVisibility());
  }

  @Test
  public void testHandleInvisibleEventMakeInvisibleInvisibleLayer() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image3x3);
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image3x3);
    this.model.addEmptyLayer();
    this.controller.handleInvisibleEvent("3");
    assertFalse(this.model.getLayers().get(2).getVisibility());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHandleVisibleEventMakeVisibleNullLayerNumber() {
    this.controller.handleVisibleEvent(null);
  }

  @Test
  public void testHandleVisibleEventMakeVisibleFirstLayer() {
    this.model.addEmptyLayer();
    this.controller.handleVisibleEvent("1");
    assertTrue(this.model.getLayers().get(0).getVisibility());
  }

  @Test
  public void testHandleVisibleEventMakeVisibleSecondLayer() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image2x2);
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image2x2);
    this.model.addEmptyLayer();
    this.model.updateTransparency(2, false);
    this.controller.handleVisibleEvent("2");
    assertTrue(this.model.getLayers().get(1).getVisibility());
  }

  @Test
  public void testHandleVisibleEventMakeVisibleVisibleLayer() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image3x3);
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image3x3);
    this.controller.handleVisibleEvent("2");
    assertTrue(this.model.getLayers().get(1).getVisibility());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHandleSaveCommandSaveImageNullFilename() {
    this.controller.handleSaveEvent(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHandleRemoveEventNullLayerNumber() {
    this.controller.handleRemoveEvent(null);
  }

  @Test
  public void testHandleRemoveEventFirstLayer() {
    this.model.addEmptyLayer();
    this.controller.handleRemoveEvent("1");
    assertEquals(0, this.model.getLayers().size());
  }

  @Test
  public void testHandleRemoveEventSecondLayer() {
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    assertEquals((Integer) 2, this.model.getCurrentOrder().get(0));
    this.controller.handleRemoveEvent("2");
    assertEquals(2, this.model.getLayers().size());
    assertEquals((Integer) 1, this.model.getCurrentOrder().get(0));
  }

  @Test
  public void testHandleBlurEvent1x1Image() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(image1x1);
    this.controller.handleBlurEvent();
    ArrayList<ArrayList<Integer>> pixels = new ArrayList<>();
    pixels.add(new ArrayList<>(Arrays.asList(63, 30, 18)));
    assertEquals(pixels, this.model.getCurrentImage().getPixels());
  }

  @Test
  public void testHandleBlurEvent2x2Image() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(image2x2);
    this.controller.handleBlurEvent();
    ArrayList<ArrayList<Integer>> pixels = new ArrayList<>();
    pixels.add(new ArrayList<>(Arrays.asList(117, 72, 48, 105, 95, 73)));
    pixels.add(new ArrayList<>(Arrays.asList(93, 53, 50, 69, 73, 51)));
    assertEquals(pixels, this.model.getCurrentImage().getPixels());
  }

  @Test
  public void testHandleBlurEvent3x3Image() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(image3x3);
    this.controller.handleBlurEvent();
    ArrayList<ArrayList<Integer>> pixels = new ArrayList<>();
    pixels.add(new ArrayList<>(Arrays.asList(117, 9, 60, 111, 15, 81, 46, 10, 51)));
    pixels.add(new ArrayList<>(Arrays.asList(133, 53, 75, 173, 64, 113, 108, 39, 92)));
    pixels.add(new ArrayList<>(Arrays.asList(74, 91, 56, 138, 106, 102, 111, 58, 84)));
    assertEquals(pixels, this.model.getCurrentImage().getPixels());
  }

  @Test
  public void testHandleSharpenEvent1x1Image() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(image1x1);
    this.controller.handleSharpenEvent();
    ArrayList<ArrayList<Integer>> pixels = new ArrayList<>();
    pixels.add(new ArrayList<>(Arrays.asList(255, 120, 75)));
    assertEquals(pixels, this.model.getCurrentImage().getPixels());
  }

  @Test
  public void testHandleSharpenEvent2x2Image() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(image2x2);
    this.controller.handleSharpenEvent();
    ArrayList<ArrayList<Integer>> pixels = new ArrayList<>();
    pixels.add(new ArrayList<>(Arrays.asList(255, 222, 100, 255, 255, 255)));
    pixels.add(new ArrayList<>(Arrays.asList(255, 158, 201, 173, 227, 113)));
    assertEquals(pixels, this.model.getCurrentImage().getPixels());
  }

  @Test
  public void testHandleSharpenEvent3x3Image() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(image3x3);
    this.controller.handleSharpenEvent();
    ArrayList<ArrayList<Integer>> pixels = new ArrayList<>();
    pixels.add(new ArrayList<>(Arrays.asList(255, 0, 89, 255, 0, 255, 10, 0, 31)));
    pixels.add(new ArrayList<>(Arrays.asList(255, 148, 239, 255, 171, 255, 255, 109, 255)));
    pixels.add(new ArrayList<>(Arrays.asList(103, 255, 62, 255, 255, 255, 255, 127, 173)));
    assertEquals(pixels, this.model.getCurrentImage().getPixels());
  }

  @Test
  public void testHandleSepiaEvent1x1Image() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(image1x1);
    this.controller.handleSepiaEvent();
    ArrayList<ArrayList<Integer>> pixels = new ArrayList<>();
    pixels.add(new ArrayList<>(Arrays.asList(206, 183, 143)));
    assertEquals(pixels, this.model.getCurrentImage().getPixels());
  }

  @Test
  public void testHandleSepiaEvent2x2Image() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(image2x2);
    this.controller.handleSepiaEvent();
    ArrayList<ArrayList<Integer>> pixels = new ArrayList<>();
    pixels.add(new ArrayList<>(Arrays.asList(192, 171, 133, 255, 255, 238)));
    pixels.add(new ArrayList<>(Arrays.asList(126, 112, 87, 100, 89, 69)));
    assertEquals(pixels, this.model.getCurrentImage().getPixels());
  }

  @Test
  public void testHandleSepiaEvent3x3Image() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(image3x3);
    this.controller.handleSepiaEvent();
    ArrayList<ArrayList<Integer>> pixels = new ArrayList<>();
    pixels.add(new ArrayList<>(Arrays.asList(114, 101, 79, 122, 109, 85, 0, 0, 0)));
    pixels.add(new ArrayList<>(Arrays.asList(126, 112, 87, 98, 87, 68, 119, 106, 82)));
    pixels.add(new ArrayList<>(Arrays.asList(186, 166, 129, 255, 255, 238, 168, 150, 116)));
    assertEquals(pixels, this.model.getCurrentImage().getPixels());
  }

  @Test
  public void testHandleGreyscaleEvent1x1Image() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(image1x1);
    this.controller.handleGreyscaleEvent();
    ArrayList<ArrayList<Integer>> pixels = new ArrayList<>();
    pixels.add(new ArrayList<>(Arrays.asList(145, 145, 145)));
    assertEquals(pixels, this.model.getCurrentImage().getPixels());
  }

  @Test
  public void testHandleGreyscaleEvent2x2Image() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(image2x2);
    this.controller.handleGreyscaleEvent();
    ArrayList<ArrayList<Integer>> pixels = new ArrayList<>();
    pixels.add(new ArrayList<>(Arrays.asList(140, 140, 140, 254, 254, 254)));
    pixels.add(new ArrayList<>(Arrays.asList(74, 74, 74, 91, 91, 91)));
    assertEquals(pixels, this.model.getCurrentImage().getPixels());
  }

  @Test
  public void testHandleGreyscaleEvent3x3Image() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(image3x3);
    this.controller.handleGreyscaleEvent();
    ArrayList<ArrayList<Integer>> pixels = new ArrayList<>();
    pixels.add(new ArrayList<>(Arrays.asList(59, 59, 59, 73, 73, 73, 0, 0, 0)));
    pixels.add(new ArrayList<>(Arrays.asList(74, 74, 74, 52, 52, 52, 72, 72, 72)));
    pixels.add(new ArrayList<>(Arrays.asList(166, 166, 166, 254, 254, 254, 114, 114, 114)));
    assertEquals(pixels, this.model.getCurrentImage().getPixels());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHandleSaveAllEventNullFiletype() {
    this.controller.handleSaveAllEvent(null, "notAFolder");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHandleSaveAllEventNullFolderName() {
    this.controller.handleSaveAllEvent("ppm", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHandleLoadMultiImageEventNullFilename() {
    this.controller.handleLoadMultiImageEvent(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHandleCreateCheckerboardNullTileSize() {
    this.controller.handleCreateCheckerboard(null, "2", "2");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHandleCreateCheckerboardNullNumTilesInRow() {
    this.controller.handleCreateCheckerboard("2", null, "2");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHandleCreateCheckerboardNullNumTilesInCol() {
    this.controller.handleCreateCheckerboard("2", "2", null);
  }

  @Test
  public void testOperateCreateNewCheckerBoard2x2CheckerBoard() {
    this.model.addEmptyLayer();
    this.controller.handleCreateCheckerboard("2", "2", "2");
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
    this.controller.handleCreateCheckerboard("3", "3", "3");
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

  @Test(expected = IllegalArgumentException.class)
  public void operateNullStringInputHeight() {
    model.addEmptyLayer();
    model.addEmptyLayer();
    model.loadNewImage(image3x3);
    this.controller.handleDownScale(null,"1");
  }

  @Test(expected = IllegalArgumentException.class)
  public void operateNullStringInputWidth() {
    model.addEmptyLayer();
    model.addEmptyLayer();
    model.loadNewImage(image3x3);
    this.controller.handleDownScale("1",null);
  }

  @Test
  public void opearteMultiImageValid3x3To2x2() {
    model.addEmptyLayer();
    model.addEmptyLayer();
    model.loadNewImage(image3x3);
    assertEquals(3, model.getTopmostVisible().getHeight());
    assertEquals(3, model.getTopmostVisible().getWidth());
    this.controller.handleDownScale("2","2");
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
    this.controller.handleDownScale("2","2");
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
    this.controller.handleDownScale("1","1");
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
    this.controller.handleDownScale("1","1");
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