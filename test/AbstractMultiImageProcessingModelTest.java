import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cs3500.IImage;
import cs3500.ILayer;
import cs3500.Image;
import cs3500.Layer;
import cs3500.model.IMultiImageProcessingModel;
import cs3500.model.MultiImageModelDownScaleSupport;
import cs3500.model.MultiImageProcessingModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the new functionality in the MultiImageProcessingModel.
 */
public abstract class AbstractMultiImageProcessingModelTest {

  IMultiImageProcessingModel model;
  List<List<Integer>> pixels3x3;
  List<List<Integer>> pixels2x2;
  List<List<Integer>> pixels1x1;
  IImage image3x3;
  IImage image2x2;
  IImage image1x1;

  @Before
  public void init() {
    this.model = constructModel();
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

  protected abstract IMultiImageProcessingModel constructModel();

  /**
   * Tests for the MultiImageProcessingModel.
   */
  public static class MultiImageProcessingModelTestAbstract
      extends AbstractMultiImageProcessingModelTest {

    @Override
    protected IMultiImageProcessingModel constructModel() {
      return new MultiImageProcessingModel();
    }
  }

  /**
   * Tests for the MultiImageProcessingModel to ensure compatibility with prior features.
   */
  public static class MultiImageModelDownScale extends AbstractMultiImageProcessingModelTest {

    @Override
    protected IMultiImageProcessingModel constructModel() {
      return new MultiImageModelDownScaleSupport();
    }
  }

  @Test
  public void testUpdateTransparencyFalseToTrue() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image3x3);
    this.model.addEmptyLayer();
    this.model.updateTransparency(2, true);
    assertTrue(this.model.getLayers().get(1).getVisibility());
  }

  @Test
  public void testUpdateTransparencyFalseToFalse() {
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.updateTransparency(4, false);
    assertFalse(this.model.getLayers().get(3).getVisibility());
  }

  @Test
  public void testUpdateTransparencyTrueToFalse() {
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image3x3);
    this.model.updateTransparency(4, false);
    assertFalse(this.model.getLayers().get(3).getVisibility());
  }

  @Test
  public void testUpdateTransparencyTrueToTrue() {
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image3x3);
    this.model.updateTransparency(4, true);
    assertTrue(this.model.getLayers().get(3).getVisibility());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUpdateTransparencyNoLayers() {
    this.model.updateTransparency(1, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUpdateTransparencyTooLargeLayerNumber() {
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.updateTransparency(5, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUpdateTransparencyTooSmallLayerNumber() {
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.updateTransparency(-1, false);
  }

  @Test
  public void testUpdateCurrentLayerMakeFirstCurrent() {
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    assertEquals(3, (int) this.model.getCurrentOrder().get(0));
    this.model.updateCurrentLayer(1);
    assertEquals(0, (int) this.model.getCurrentOrder().get(0));
  }

  @Test
  public void testUpdateCurrentLayerMakeAlreadyCurrentCurrent() {
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    assertEquals(3, (int) this.model.getCurrentOrder().get(0));
    this.model.updateCurrentLayer(4);
    assertEquals(3, (int) this.model.getCurrentOrder().get(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUpdateCurrentLayerNoLayers() {
    this.model.updateCurrentLayer(1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUpdateCurrentLayerLayerNumberTooLarge() {
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.updateCurrentLayer(5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUpdateCurrentLayerLayerNumberTooSmall() {
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.updateCurrentLayer(-1);
  }

  @Test
  public void testRemoveLayerFirstLayer() {
    ArrayList<Integer> currentLayers = new ArrayList<Integer>(Arrays.asList(3, 2, 1, 0));
    ArrayList<Integer> nextCurrentLayers = new ArrayList<Integer>(Arrays.asList(2, 1, 0));
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    assertEquals(4, this.model.getLayers().size());
    assertEquals(4, this.model.getCurrentOrder().size());
    assertEquals(currentLayers, this.model.getCurrentOrder());
    this.model.removeLayer(1);
    assertEquals(3, this.model.getLayers().size());
    assertEquals(3, this.model.getCurrentOrder().size());
    assertEquals(nextCurrentLayers, this.model.getCurrentOrder());
  }

  @Test
  public void testRemoveLayerSecondLayerAdjustLargerValues() {
    ArrayList<Integer> currentLayers = new ArrayList<Integer>(Arrays.asList(3, 2, 1, 0));
    ArrayList<Integer> nextCurrentLayers = new ArrayList<Integer>(Arrays.asList(2, 1, 0));
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    assertEquals(currentLayers, this.model.getCurrentOrder());
    this.model.removeLayer(2);
    assertEquals(nextCurrentLayers, this.model.getCurrentOrder());


  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveLayerOutOfBoundsAfterAdjustment() {
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.removeLayer(1);
    this.model.removeLayer(2);
    this.model.removeLayer(3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveLayerLayerNumberTooSmall() {
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.removeLayer(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveLayerLayerNoLayers() {
    this.model.removeLayer(1);
  }

  @Test
  public void testAddOneEmptyLayer() {
    this.model.addEmptyLayer();
    assertEquals(1, this.model.getLayers().size());
    assertEquals(1, this.model.getCurrentOrder().size());
  }

  @Test
  public void testAddTwoEmptyLayers() {
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    assertEquals(2, this.model.getLayers().size());
    assertEquals(2, this.model.getCurrentOrder().size());
  }

  @Test
  public void testAddThreeEmptyLayers() {
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    assertEquals(3, this.model.getLayers().size());
    assertEquals(3, this.model.getCurrentOrder().size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadNewImageNullImage() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(null);
  }

  @Test(expected = IllegalStateException.class)
  public void testLoadNewImageNoLayers() {
    this.model.loadNewImage(this.image3x3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadNewImageDifferentImageSize() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image3x3);
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image2x2);
  }

  @Test(expected = IllegalStateException.class)
  public void testLoadNewImageFullLayer() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image2x2);
    this.model.addEmptyLayer();
    this.model.updateCurrentLayer(1);
    this.model.loadNewImage(this.image2x2);
  }

  @Test
  public void testLoadNewImageFirstImage() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image2x2);
    assertEquals(this.pixels2x2, this.model.getLayers().get(0).getImage().getPixels());
    assertEquals(2, this.model.getLayers().get(0).getImage().getWidth());
    assertEquals(2, this.model.getLayers().get(0).getImage().getHeight());
  }

  @Test
  public void testLoadNewImageTwoImages3x3() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image3x3);
    assertEquals(this.pixels3x3, this.model.getLayers().get(0).getImage().getPixels());
    assertEquals(3, this.model.getLayers().get(0).getImage().getWidth());
    assertEquals(3, this.model.getLayers().get(0).getImage().getHeight());
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image3x3);
    assertEquals(this.pixels3x3, this.model.getLayers().get(1).getImage().getPixels());
    assertEquals(3, this.model.getLayers().get(1).getImage().getWidth());
    assertEquals(3, this.model.getLayers().get(1).getImage().getHeight());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetCurrentImageNoLayers() {
    this.model.getCurrentImage();
  }

  @Test
  public void testGetCurrentImage2x2Image() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image2x2);
    assertEquals(this.pixels2x2, this.model.getCurrentImage().getPixels());
    assertEquals(2, this.model.getCurrentImage().getWidth());
    assertEquals(2, this.model.getCurrentImage().getHeight());
  }

  @Test
  public void testGetCurrentImage3x3Image() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image3x3);
    this.model.addEmptyLayer();
    this.model.updateCurrentLayer(1);
    assertEquals(this.pixels3x3, this.model.getCurrentImage().getPixels());
    assertEquals(3, this.model.getCurrentImage().getWidth());
    assertEquals(3, this.model.getCurrentImage().getHeight());
  }


  @Test(expected = IllegalStateException.class)
  public void testGetTopMostVisibleAllInvisible() {
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image3x3);
    this.model.updateTransparency(2, false);
    this.model.addEmptyLayer();
    this.model.getTopmostVisible();
  }

  @Test(expected = IllegalStateException.class)
  public void testGetTopMostVisibleNoLayers() {
    this.model.getTopmostVisible();
  }

  @Test
  public void testGetTopMostVisibleAllVisible() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image3x3);
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image3x3);
    assertEquals(this.image3x3.getPixels(), this.model.getTopmostVisible().getPixels());
    assertEquals(3, this.model.getTopmostVisible().getHeight());
    assertEquals(3, this.model.getTopmostVisible().getWidth());
  }

  @Test
  public void testGetTopMostVisibleSomeVisible() {
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image2x2);
    assertEquals(this.image2x2.getPixels(), this.model.getTopmostVisible().getPixels());
    assertEquals(2, this.model.getTopmostVisible().getHeight());
    assertEquals(2, this.model.getTopmostVisible().getWidth());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testChangeLayerImageNullImage() {
    this.model.addEmptyLayer();
    this.model.changeLayerImage(null);
  }

  @Test(expected = IllegalStateException.class)
  public void testChangeLayerImageNoImages() {
    this.model.changeLayerImage(this.image3x3);
  }

  @Test
  public void testChangeLayerImageBlurredImage() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image2x2);
    assertEquals(this.pixels2x2, this.model.getTopmostVisible().getPixels());
    this.model.changeLayerImage(this.model.blur(this.image2x2));
    ArrayList<ArrayList<Integer>> pixels = new ArrayList<>();
    pixels.add(new ArrayList<>(Arrays.asList(117, 72, 48, 105, 95, 73)));
    pixels.add(new ArrayList<>(Arrays.asList(93, 53, 50, 69, 73, 51)));
    assertEquals(pixels, this.model.getTopmostVisible().getPixels());
  }

  @Test
  public void testChangeLayerImageSharpenedImage() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image1x1);
    assertEquals(this.pixels1x1, this.model.getTopmostVisible().getPixels());
    this.model.changeLayerImage(this.model.sharpen(this.image1x1));
    ArrayList<ArrayList<Integer>> pixels = new ArrayList<>();
    pixels.add(new ArrayList<>(Arrays.asList(255, 120, 75)));
    assertEquals(pixels, this.model.getTopmostVisible().getPixels());
  }

  @Test
  public void testChangeLayerImageSepiaImage() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image2x2);
    assertEquals(this.pixels2x2, this.model.getTopmostVisible().getPixels());
    this.model.changeLayerImage(this.model.sepia(this.image2x2));
    ArrayList<ArrayList<Integer>> pixels = new ArrayList<>();
    pixels.add(new ArrayList<>(Arrays.asList(192, 171, 133, 255, 255, 238)));
    pixels.add(new ArrayList<>(Arrays.asList(126, 112, 87, 100, 89, 69)));
    assertEquals(pixels, this.model.getTopmostVisible().getPixels());
  }

  @Test
  public void testGetLayersEmptyLayers() {
    assertEquals(0, this.model.getLayers().size());
  }

  @Test
  public void testGetLayersOneLayer() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image3x3);
    assertEquals(1, this.model.getLayers().size());
    assertEquals(this.image3x3.getPixels(), this.model.getLayers().get(0).getImage().getPixels());
  }

  @Test
  public void testGetLayersTwoLayers() {
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image3x3);
    this.model.addEmptyLayer();
    this.model.loadNewImage(this.image3x3);
    assertEquals(2, this.model.getLayers().size());
    assertEquals(this.image3x3.getPixels(), this.model.getLayers().get(0).getImage().getPixels());
    assertEquals(this.image3x3.getPixels(), this.model.getLayers().get(1).getImage().getPixels());

  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetLayersNullListOfLayers() {
    model.setLayers(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetLayersNullLayer() {
    ArrayList<ILayer> layers = new ArrayList<ILayer>();
    layers.add(null);
    layers.add(new Layer(this.image2x2, true));
    layers.add(new Layer(this.image1x1, true));
    model.setLayers(layers);
  }

  @Test
  public void testSetLayersListOfLayers() {
    ArrayList<ILayer> layers = new ArrayList<ILayer>();
    layers.add(0, new Layer(this.image3x3, false));
    layers.add(0, new Layer(this.image3x3, true));
    layers.add(0, new Layer(this.image3x3, true));
    this.model.setLayers(layers);
    assertEquals(layers.get(0).getImage().getPixels(),
        this.model.getLayers().get(0).getImage().getPixels());
    assertEquals(layers.get(1).getImage().getPixels(),
        this.model.getLayers().get(1).getImage().getPixels());
    assertEquals(layers.get(2).getImage().getPixels(),
        this.model.getLayers().get(2).getImage().getPixels());
  }

  @Test
  public void testGetCurrentOrderEmptyCurrent() {
    assertEquals(new ArrayList<>(), this.model.getCurrentOrder());
  }

  @Test
  public void testGetCurrentOrderOneLayer() {
    ArrayList<Integer> currentLayers = new ArrayList<Integer>();
    currentLayers.add(0);
    this.model.addEmptyLayer();
    assertEquals(currentLayers, this.model.getCurrentOrder());
  }

  @Test
  public void testGetCurrentOrderThreeLayersCurrentsMoved() {
    ArrayList<Integer> currentLayers = new ArrayList<Integer>(Arrays.asList(3, 2, 1, 0));
    ArrayList<Integer> nextCurrentLayers = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3));
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    this.model.addEmptyLayer();
    assertEquals(currentLayers, this.model.getCurrentOrder());
    this.model.loadNewImage(this.image3x3);
    this.model.updateCurrentLayer(3);
    this.model.updateCurrentLayer(2);
    this.model.updateCurrentLayer(1);
    assertEquals(nextCurrentLayers, this.model.getCurrentOrder());
  }
}