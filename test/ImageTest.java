import static org.junit.Assert.assertEquals;

import cs3500.IImage;
import cs3500.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the Image class.
 */
public class ImageTest {

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
    this.pixels3x3 = new ArrayList<>();
    for (int i = 0; i < 9; i++) {
      pixels3x3.add(new ArrayList<>());
      for (int j = 10; j < 13; j++) {
        pixels3x3.get(i).add(j + i);
      }
    }
    this.pixels4x4 = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(255, 120, 75, 128, 45, 200, 78, 189, 230, 154, 20, 231)),
        new ArrayList<>(Arrays.asList(190, 34, 134, 243, 126, 17, 120, 45, 200, 231, 45, 195)),
        new ArrayList<>(Arrays.asList(20, 224, 34, 153, 69, 23, 200, 90, 110, 84, 21, 0)),
        new ArrayList<>(Arrays.asList(0, 0, 0, 255, 255, 255, 0, 255, 0, 255, 255, 0))));
    this.pixels2x2 = new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(255, 120, 0, 255, 255, 255)),
        new ArrayList<>(Arrays.asList(190, 34, 134, 0, 126, 17))));
    this.pixels1x1 = new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(255, 120, 75))));
    this.image4x4 = new Image(pixels4x4, 4, 4);
    this.image3x3 = new Image(pixels3x3, 3, 3);
    this.image2x2 = new Image(pixels2x2, 2, 2);
    this.image1x1 = new Image(pixels1x1, 1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullPixels() {
    new Image(null, 5, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorZeroHeight() {
    new Image(pixels3x3, 0, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorZeroWidth() {
    new Image(pixels3x3, 5, 0);
  }


  @Test
  public void testGetPixelsValidList3x3() {
    List<List<Integer>> pixels = image3x3.getPixels();
    assertEquals(9, pixels.size());
    assertEquals(new ArrayList<>(Arrays.asList(10,11,12)), pixels.get(0));
    assertEquals(new ArrayList<>(Arrays.asList(11,12,13)), pixels.get(1));
    assertEquals(new ArrayList<>(Arrays.asList(18,19,20)), pixels.get(8));
  }

  @Test
  public void testGetPixelsValidList1x1() {
    List<List<Integer>> pixels = image1x1.getPixels();
    assertEquals(1, pixels.size());
    assertEquals(new ArrayList<>(Arrays.asList(255,120,75)), pixels.get(0));
  }

  @Test
  public void testGetPixelsValidList2x2() {
    List<List<Integer>> pixels = image2x2.getPixels();
    assertEquals(2,pixels.size());
    assertEquals(new ArrayList<>(Arrays.asList(255,120,0,255,255,255)), pixels.get(0));
  }

  @Test
  public void testGetHeightValidImages() {
    assertEquals(2, image2x2.getHeight());
    assertEquals(3,image3x3.getHeight());
    assertEquals(4,image4x4.getHeight());
  }

  @Test
  public void testGetWidthValidImages() {
    assertEquals(2, image2x2.getWidth());
    assertEquals(3,image3x3.getWidth());
    assertEquals(4,image4x4.getWidth());
  }

  @Test
  public void testToString1x1ValidImage() {
    assertEquals("P3\n"
        + "1 1\n"
        + "255\n"
        + "255\n"
        + "120\n"
        + "75\n", image1x1.toString());
  }

  @Test
  public void testToString4x4ValidImage() {
    assertEquals("P3\n"
        + "4 4\n"
        + "255\n"
        + "255\n"
        + "120\n"
        + "75\n"
        + "128\n"
        + "45\n"
        + "200\n"
        + "78\n"
        + "189\n"
        + "230\n"
        + "154\n"
        + "20\n"
        + "231\n"
        + "190\n"
        + "34\n"
        + "134\n"
        + "243\n"
        + "126\n"
        + "17\n"
        + "120\n"
        + "45\n"
        + "200\n"
        + "231\n"
        + "45\n"
        + "195\n"
        + "20\n"
        + "224\n"
        + "34\n"
        + "153\n"
        + "69\n"
        + "23\n"
        + "200\n"
        + "90\n"
        + "110\n"
        + "84\n"
        + "21\n"
        + "0\n"
        + "0\n"
        + "0\n"
        + "0\n"
        + "255\n"
        + "255\n"
        + "255\n"
        + "0\n"
        + "255\n"
        + "0\n"
        + "255\n"
        + "255\n"
        + "0\n", image4x4.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetColorValueNullBufferedImage() {
    this.image1x1.setColorValue(null);
  }

  @Test
  public void testSetRGB1x1Image() {
    BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    assertEquals(1, this.image1x1.setColorValue(image).getHeight());
    assertEquals(1, this.image1x1.setColorValue(image).getWidth());
    assertEquals(-16777216, this.image1x1.setColorValue(image).getRGB(0,0));
  }

  @Test
  public void testSetRGB2x2Image() {
    BufferedImage image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
    assertEquals(2, this.image2x2.setColorValue(image).getHeight());
    assertEquals(2, this.image2x2.setColorValue(image).getWidth());
    assertEquals(-16777216, this.image2x2.setColorValue(image).getRGB(1,1));
  }

  @Test
  public void testSetRGB4x4Image() {
    BufferedImage image = new BufferedImage(4, 4, BufferedImage.TYPE_INT_RGB);
    assertEquals(4, this.image4x4.setColorValue(image).getHeight());
    assertEquals(4, this.image4x4.setColorValue(image).getWidth());
    assertEquals(-16777216, this.image4x4.setColorValue(image).getRGB(3,3));
  }
}