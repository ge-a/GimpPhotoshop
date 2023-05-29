import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import cs3500.IImage;
import cs3500.ILayer;
import cs3500.Image;
import cs3500.Layer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the layer class.
 */
public class LayerTest {

  List<List<Integer>> pixels4x4;
  List<List<Integer>> pixels3x3;
  List<List<Integer>> pixels2x2;
  List<List<Integer>> pixels1x1;
  IImage image4x4;
  IImage image3x3;
  IImage image2x2;
  IImage image1x1;

  @Before
  public void initData() {
    this.pixels4x4 = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(255, 120, 75, 128, 45, 200, 78, 189, 230, 154, 20, 231)),
        new ArrayList<>(Arrays.asList(190, 34, 134, 243, 126, 17, 120, 45, 200, 231, 45, 195)),
        new ArrayList<>(Arrays.asList(20, 224, 34, 153, 69, 23, 200, 90, 110, 84, 21, 0)),
        new ArrayList<>(Arrays.asList(0, 0, 0, 255, 255, 255, 0, 255, 0, 255, 255, 0))));
    this.pixels3x3 = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(255, 0, 75, 128, 45, 200, 0, 0, 0)),
        new ArrayList<>(Arrays.asList(190, 34, 134, 243, 0, 17, 120, 45, 200)),
        new ArrayList<>(Arrays.asList(20, 224, 34, 255, 255, 255, 200, 90, 110))));
    this.pixels2x2 = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(255, 120, 0, 255, 255, 255)),
        new ArrayList<>(Arrays.asList(190, 34, 134, 0, 126, 17))));
    this.pixels1x1 = new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(255, 120, 75))));
    this.image4x4 = new Image(this.pixels4x4, 4, 4);
    this.image3x3 = new Image(this.pixels3x3, 3, 3);
    this.image2x2 = new Image(this.pixels2x2, 2, 2);
    this.image1x1 = new Image(this.pixels1x1, 1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullImageLayerConstructor() {
    Layer layer = new Layer(null, false);
  }

  @Test
  public void testGetImage4x4() {
    ILayer layer = new Layer(this.image4x4, true);
    assertEquals(this.pixels4x4, layer.getImage().getPixels());
    assertEquals(4, layer.getImage().getHeight());
    assertEquals(4, layer.getImage().getWidth());
  }

  @Test
  public void testGetImage3x3() {
    ILayer layer = new Layer(this.image3x3, true);
    assertEquals(this.pixels3x3, layer.getImage().getPixels());
    assertEquals(3, layer.getImage().getHeight());
    assertEquals(3, layer.getImage().getWidth());
  }

  @Test
  public void testGetImage2x2() {
    ILayer layer = new Layer(this.image2x2, true);
    assertEquals(this.pixels2x2, layer.getImage().getPixels());
    assertEquals(2, layer.getImage().getHeight());
    assertEquals(2, layer.getImage().getWidth());
  }

  @Test
  public void testGetImage1x1() {
    ILayer layer = new Layer(this.image1x1, true);
    assertEquals(this.pixels1x1, layer.getImage().getPixels());
    assertEquals(1, layer.getImage().getHeight());
    assertEquals(1, layer.getImage().getWidth());
  }

  @Test
  public void testGetVisibilityTrue() {
    ILayer layer = new Layer(this.image2x2, true);
    assertTrue(layer.getVisibility());
  }

  @Test
  public void testGetVisibilityFalse() {
    ILayer layer = new Layer(this.image3x3, false);
    assertFalse(layer.getVisibility());
  }

  @Test
  public void testGetVisibilityDefaultConstructor() {
    ILayer layer = new Layer();
    assertFalse(layer.getVisibility());
  }

  @Test
  public void testChangeVisibilityTrueToFalse() {
    ILayer layer = new Layer(this.image1x1, true);
    layer.changeVisibility(false);
    assertFalse(layer.getVisibility());
  }

  @Test
  public void testChangeVisibilityFalseToTrue() {
    ILayer layer = new Layer(this.image3x3, false);
    layer.changeVisibility(true);
    assertTrue(layer.getVisibility());
  }

  @Test
  public void testChangeVisibilityFalseToTrueDefaultConstructor() {
    ILayer layer = new Layer();
    layer.changeVisibility(true);
    assertTrue(layer.getVisibility());
  }

  @Test
  public void testChangeVisibilityTrueToTrue() {
    ILayer layer = new Layer(this.image2x2, true);
    layer.changeVisibility(true);
    assertTrue(layer.getVisibility());
  }

  @Test
  public void testChangeVisibilityFalseToFalse() {
    ILayer layer = new Layer(this.image4x4, false);
    layer.changeVisibility(false);
    assertFalse(layer.getVisibility());
  }
}