import static org.junit.Assert.assertEquals;

import cs3500.IImage;
import cs3500.model.IImageProcessingModel;
import cs3500.Image;
import cs3500.model.ImageProcessingModel;
import cs3500.model.MultiImageModelDownScaleSupport;
import cs3500.model.MultiImageProcessingModel;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Abstract tests for the ImageProcessingModel and the MultiImageProcessingModel.
 */
public abstract class AbstractImageProcessingModelTest {

  IImageProcessingModel model;
  List<List<Integer>> pixels5x5;
  List<List<Integer>> pixels4x4;
  List<List<Integer>> pixels3x3;
  List<List<Integer>> pixels2x2;
  List<List<Integer>> pixels1x1;
  List<List<Integer>> smallPixels3x3;
  IImage image5x5;
  IImage image4x4;
  IImage image3x3;
  IImage image2x2;
  IImage image1x1;
  IImage smallImage3x3;


  @Before
  public void initData() {
    this.model = constructModel();
    this.smallPixels3x3 = new ArrayList<>();
    for (int i = 0; i < 9; i++) {
      this.smallPixels3x3.add(new ArrayList<>());
      for (int j = 10; j < 13; j++) {
        this.smallPixels3x3.get(i).add(j + i);
      }
    }
    this.pixels5x5 = new ArrayList<>(Arrays.asList(
        new ArrayList<>(
            Arrays.asList(255, 120, 75, 128, 45, 200, 78, 189, 230, 154, 20, 231, 0, 0, 0)),
        new ArrayList<>(
            Arrays.asList(190, 34, 134, 243, 126, 17, 120, 45, 200, 231, 45, 195, 255, 0, 255)),
        new ArrayList<>(
            Arrays.asList(20, 224, 34, 153, 69, 23, 200, 90, 110, 84, 21, 0, 125, 0, 12)),
        new ArrayList<>(Arrays.asList(0, 0, 0, 255, 255, 255, 0, 255, 0, 255, 255, 0, 0, 255, 0)),
        new ArrayList<>(
            Arrays.asList(125, 0, 123, 15, 0, 200, 0, 0, 0, 255, 255, 255, 235, 0, 125))));
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
    this.image5x5 = new Image(this.pixels5x5, 5, 5);
    this.image4x4 = new Image(this.pixels4x4, 4, 4);
    this.image3x3 = new Image(this.pixels3x3, 3, 3);
    this.image2x2 = new Image(this.pixels2x2, 2, 2);
    this.image1x1 = new Image(this.pixels1x1, 1, 1);
    this.smallImage3x3 = new Image(this.smallPixels3x3, 3, 3);
  }

  protected abstract IImageProcessingModel constructModel();

  /**
   * Tests for the ImageProcessingModel.
   */
  public static class ImageProcessingModelTest extends AbstractImageProcessingModelTest {
    @Override
    protected IImageProcessingModel constructModel() {
      return new ImageProcessingModel();
    }
  }

  /**
   * Tests for the MultiImageProcessingModel.
   */
  public static class MultiImageProcessingModelTestAbstract
      extends AbstractImageProcessingModelTest {
    @Override
    protected IImageProcessingModel constructModel() {
      return new MultiImageProcessingModel();
    }
  }

  /**
   * Tests for the MultiImageProcessingModelDownScale to ensure compatibility with prior features.
   */
  public static class MultiImageModelDownScale extends AbstractImageProcessingModelTest {

    @Override
    protected IImageProcessingModel constructModel() {
      return new MultiImageModelDownScaleSupport();
    }

  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddImageNullID() {
    this.model.addImage(null, this.image1x1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddImageNullImage() {
    this.model.addImage("image2x2", this.image2x2);
    this.model.addImage("image2x2", null);
  }

  @Test
  public void testAddImageValid() {
    this.model.addImage("image2x2", this.image2x2);
    assertEquals(this.image2x2, this.model.getImage("image2x2"));
    assertEquals(1, this.model.getImagesStoredSize());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveImageNullID() {
    this.model.addImage("image2x2", this.image2x2);
    this.model.removeImage(null);
  }

  @Test
  public void testRemoveImageValidOneItemStored() {
    this.model.addImage("image2x2", this.image2x2);
    this.model.removeImage("image2x2");
    assertEquals(0, this.model.getImagesStoredSize());
  }

  @Test
  public void testRemoveImageValidMultipleItemsStored() {
    this.model.addImage("2x2", this.image2x2);
    this.model.addImage("1x1", this.image1x1);
    this.model.addImage("3x3", this.smallImage3x3);
    this.model.addImage("4x4", this.image4x4);
    this.model.removeImage("2x2");
    assertEquals(3, model.getImagesStoredSize());
  }

  @Test
  public void testRemoveImageValidMultipleItemsStoredAttemptToRemoveInvalidID() {
    this.model.addImage("2x2", this.image2x2);
    this.model.addImage("1x1", this.image1x1);
    this.model.addImage("3x3", this.smallImage3x3);
    this.model.addImage("4x4", this.image4x4);
    this.model.removeImage("2x2");
    this.model.removeImage("5x5");
    assertEquals(3, this.model.getImagesStoredSize());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReplaceImageNullID() {
    this.model.addImage("2x2", this.image2x2);
    this.model.replaceImage(null, this.image4x4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReplaceImageNullImage() {
    this.model.addImage("2x2", this.image2x2);
    this.model.replaceImage("2x2", null);
  }

  @Test
  public void testReplaceImageIDDoesNotExist() {
    this.model.addImage("2x2", this.image2x2);
    this.model.replaceImage("2x3", this.image4x4);
    assertEquals(this.image2x2, this.model.getImage("2x2"));
    assertEquals(1, this.model.getImagesStoredSize());
  }

  @Test
  public void testReplaceImageValidReplaceMultipleItemsStoredSameValueStoredDifferentID() {
    this.model.addImage("2x2", this.image2x2);
    this.model.addImage("3x3", this.smallImage3x3);
    this.model.addImage("4x4", this.image4x4);
    this.model.replaceImage("2x2", this.image4x4);
    assertEquals(this.image4x4, this.model.getImage("2x2"));
    assertEquals(3, this.model.getImagesStoredSize());
  }


  @Test
  public void test1x1PixelImageBlur() {
    IImageProcessingModel model = new ImageProcessingModel();
    model.addImage("image1x1", this.image1x1);
    IImage blurredImage = model.blur(model.getImage("image1x1"));
    assertEquals(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(63, 30, 18)))),
        blurredImage.getPixels());
  }

  @Test
  public void test2x2PixelImageBlur() {
    IImageProcessingModel model = new ImageProcessingModel();
    IImage blurredImage = model.blur(this.image2x2);
    assertEquals(new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(117, 72, 48, 105, 95, 73)),
        new ArrayList<>(Arrays.asList(93, 53, 50, 69, 73, 51)))),
        blurredImage.getPixels());
  }

  @Test
  public void test3x3PixelImageBlur() {
    IImageProcessingModel model = new ImageProcessingModel();
    IImage blurredImage = model.blur(this.image3x3);
    assertEquals(new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(117, 9, 60, 111, 15, 81, 46, 10, 51)),
        new ArrayList<>(Arrays.asList(133, 53, 75, 173, 64, 113, 108, 39, 92)),
        new ArrayList<>(Arrays.asList(74, 91, 56, 138, 106, 102, 111, 58, 84)))),
        blurredImage.getPixels());
  }

  @Test
  public void test4x4PixelImageBlur() {
    IImageProcessingModel model = new ImageProcessingModel();
    IImage blurredImage = model.blur(this.image4x4);
    assertEquals(new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(117, 46, 60, 120, 68, 109, 98, 68, 148, 82, 35, 121)),
        new ArrayList<>(Arrays.asList(127, 72, 61, 165, 90, 98, 153, 73, 144, 117, 36, 121)),
        new ArrayList<>(Arrays.asList(77, 90, 42, 144, 121, 75, 153, 107, 82, 112, 69, 49)),
        new ArrayList<>(Arrays.asList(42, 63, 36, 95, 121, 73, 101, 141, 45, 85, 101, 6)))),
        blurredImage.getPixels());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullImageBlur() {
    IImageProcessingModel model = new ImageProcessingModel();
    IImage blurredImage = model.blur(null);
  }

  @Test
  public void test1x1PixelImageGreyscale() {
    IImageProcessingModel model = new ImageProcessingModel();
    IImage greyscaleImage = model.greyscale(this.image1x1);
    assertEquals(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(145, 145, 145)))),
        greyscaleImage.getPixels());
  }

  @Test
  public void test2x2PixelImageGreyscale() {
    IImageProcessingModel model = new ImageProcessingModel();
    IImage greyscaleImage = model.greyscale(this.image2x2);
    assertEquals(new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(140, 140, 140, 254, 254, 254)),
        new ArrayList<>(Arrays.asList(74, 74, 74, 91, 91, 91)))),
        greyscaleImage.getPixels());
  }

  @Test
  public void test3x3PixelImageGreyscale() {
    IImageProcessingModel model = new ImageProcessingModel();
    IImage greyscaleImage = model.greyscale(this.image3x3);
    assertEquals(new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(59, 59, 59, 73, 73, 73, 0, 0, 0)),
        new ArrayList<>(Arrays.asList(74, 74, 74, 52, 52, 52, 72, 72, 72)),
        new ArrayList<>(Arrays.asList(166, 166, 166, 254, 254, 254, 114, 114, 114)))),
        greyscaleImage.getPixels());
  }

  @Test
  public void test4x4PixelImageGreyscale() {
    IImageProcessingModel model = new ImageProcessingModel();
    IImage greyscaleImage = model.greyscale(this.image4x4);
    assertEquals(new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(145, 145, 145, 73, 73, 73, 168, 168, 168, 63, 63, 63)),
        new ArrayList<>(Arrays.asList(74, 74, 74, 143, 143, 143, 72, 72, 72, 95, 95, 95)),
        new ArrayList<>(Arrays.asList(166, 166, 166, 83, 83, 83, 114, 114, 114, 32, 32, 32)),
        new ArrayList<>(Arrays.asList(0, 0, 0, 254, 254, 254, 182, 182, 182, 236, 236, 236)))),
        greyscaleImage.getPixels());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullImageGreyscale() {
    IImageProcessingModel model = new ImageProcessingModel();
    IImage greyscaleImage = model.greyscale(null);
  }

  @Test
  public void test1x1PixelImageSepia() {
    IImageProcessingModel model = new ImageProcessingModel();
    IImage sepiaImage = model.sepia(this.image1x1);
    assertEquals(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(206, 183, 143)))),
        sepiaImage.getPixels());
  }

  @Test
  public void test2x2PixelImageSepia() {
    IImageProcessingModel model = new ImageProcessingModel();
    IImage sepiaImage = model.sepia(this.image2x2);
    assertEquals(new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(192, 171, 133, 255, 255, 238)),
        new ArrayList<>(Arrays.asList(126, 112, 87, 100, 89, 69)))),
        sepiaImage.getPixels());
  }

  @Test
  public void test3x3PixelImageSepia() {
    IImageProcessingModel model = new ImageProcessingModel();
    model.addImage("image3x3", this.image3x3);
    IImage sepiaImage = model.sepia(this.image3x3);
    assertEquals(new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(114, 101, 79, 122, 109, 85, 0, 0, 0)),
        new ArrayList<>(Arrays.asList(126, 112, 87, 98, 87, 68, 119, 106, 82)),
        new ArrayList<>(Arrays.asList(186, 166, 129, 255, 255, 238, 168, 150, 116)))),
        sepiaImage.getPixels());
  }

  @Test
  public void test4x4PixelImageSepia() {
    IImageProcessingModel model = new ImageProcessingModel();
    model.addImage("image4x4", this.image4x4);
    IImage sepiaImage = model.sepia(this.image4x4);
    assertEquals(new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(206, 183, 143, 122, 109, 85, 219, 195, 152, 119, 106, 82)),
        new ArrayList<>(Arrays.asList(126, 112, 87, 195, 174, 135, 119, 106, 82, 162, 144, 112)),
        new ArrayList<>(Arrays.asList(186, 166, 129, 117, 104, 81, 168, 150, 116, 49, 43, 34)),
        new ArrayList<>(Arrays.asList(0, 0, 0, 255, 255, 238, 196, 174, 136, 255, 255, 205)))),
        sepiaImage.getPixels());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullImageSepia() {
    IImageProcessingModel model = new ImageProcessingModel();
    model.addImage("null", null);
    IImage sepiaImage = model.sepia(null);
  }

  @Test
  public void test1x1PixelImageSharpen() {
    IImageProcessingModel model = new ImageProcessingModel();
    IImage sharpenedImage = model.sharpen(this.image1x1);
    assertEquals(new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(255, 120, 75)))),
        sharpenedImage.getPixels());
  }

  @Test
  public void test2x2PixelImageSharpen() {
    IImageProcessingModel model = new ImageProcessingModel();
    model.addImage("image2x2", this.image2x2);
    IImage sharpenedImage = model.sharpen(model.getImage("image2x2"));
    assertEquals(new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(255, 222, 100, 255, 255, 255)),
        new ArrayList<>(Arrays.asList(255, 158, 201, 173, 227, 113)))),
        sharpenedImage.getPixels());
  }

  @Test
  public void test3x3PixelImageSharpen() {
    IImageProcessingModel model = new ImageProcessingModel();
    model.addImage("image3x3", this.image3x3);
    IImage sharpenedImage = model.sharpen(model.getImage("image3x3"));
    assertEquals(new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(255, 0, 89, 255, 0, 255, 10, 0, 31)),
        new ArrayList<>(Arrays.asList(255, 148, 239, 255, 171, 255, 255, 109, 255)),
        new ArrayList<>(Arrays.asList(103, 255, 62, 255, 255, 255, 255, 127, 173)))),
        sharpenedImage.getPixels());
  }

  @Test
  public void test4x4PixelImageSharpen() {
    IImageProcessingModel model = new ImageProcessingModel();
    model.addImage("image4x4", this.image4x4);
    IImage sharpenedImage = model.sharpen(model.getImage("image4x4"));
    assertEquals(new ArrayList<>(Arrays.asList(
        new ArrayList<>(Arrays.asList(255, 95, 90, 244, 116, 255, 185, 190, 255, 160, 48, 255)),
        new ArrayList<>(Arrays.asList(255, 78, 122, 255, 226, 182, 255, 54, 255, 255, 14, 255)),
        new ArrayList<>(Arrays.asList(132, 253, 39, 255, 240, 94, 255, 255, 170, 160, 107, 9)),
        new ArrayList<>(Arrays.asList(13, 70, 20, 211, 255, 228, 137, 255, 24, 203, 255, 0)))),
        sharpenedImage.getPixels());
  }

  @Test
  public void test5x5PixelImageSharpen() {
    IImageProcessingModel model = new ImageProcessingModel();
    model.addImage("image5x5", this.image5x5);
    IImage sharpenedImage = model.sharpen(model.getImage("image5x5"));
    assertEquals(new ArrayList<>(Arrays.asList(
        new ArrayList<>(
            Arrays.asList(255, 95, 90, 244, 116, 255, 139, 190, 255, 208, 48, 255, 84, 0, 101)),
        new ArrayList<>(
            Arrays.asList(255, 78, 122, 255, 226, 182, 255, 23, 255, 255, 0, 255, 255, 0, 255)),
        new ArrayList<>(
            Arrays.asList(116, 253, 0, 217, 209, 23, 255, 215, 52, 193, 139, 4, 201, 39, 0)),
        new ArrayList<>(Arrays.asList(47, 70, 100, 214, 255, 255, 113, 255, 75, 255, 255, 0, 137,
            255, 4)),
        new ArrayList<>(
            Arrays.asList(145, 0, 217, 0, 15, 243, 77, 172, 126, 255, 255, 214, 255, 145, 174)))),
        sharpenedImage.getPixels());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullImageSharpen() {
    IImageProcessingModel model = new ImageProcessingModel();
    model.addImage("null", null);
    IImage sharpenedImage = model.sharpen(model.getImage("null"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullColorCheckerboard() {
    this.model.createCheckerboard(5,5,5,null, Color.BLACK);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullOtherColorCheckerboard() {
    this.model.createCheckerboard(5,5,5,Color.WHITE, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNumTilesInColsLessThanEqualZero() {
    this.model.createCheckerboard(5,5,-1,Color.WHITE, Color.BLACK);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNumTilesInRowsLessThanEqualZero() {
    this.model.createCheckerboard(5, 0, 5, Color.BLACK, Color.WHITE);
  }

  @Test
  public void testCreateValidCheckerBoardSmall() {
    IImage checkerBoard =
        this.model.createCheckerboard(2,1,1,Color.BLACK, Color.WHITE);
    ArrayList<ArrayList<Integer>> pixels = new ArrayList<ArrayList<Integer>>();
    pixels.add(new ArrayList<Integer>(Arrays.asList(255, 255, 255, 255, 255, 255)));
    pixels.add(new ArrayList<Integer>(Arrays.asList(255, 255, 255, 255, 255, 255)));
    assertEquals(pixels, checkerBoard.getPixels());
  }

  @Test
  public void testCreateValidCheckerBoardLarge() {
    IImage checkerBoard = this.model.createCheckerboard(3,3,3,
        Color.BLACK, Color.WHITE);
    ArrayList<ArrayList<Integer>> pixels = new ArrayList<ArrayList<Integer>>();
    pixels.add(new ArrayList<Integer>(Arrays.asList(255, 255, 255, 255, 255, 255, 255, 255, 255, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255)));
    pixels.add(new ArrayList<Integer>(Arrays.asList(255, 255, 255, 255, 255, 255, 255, 255, 255, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255)));
    pixels.add(new ArrayList<Integer>(Arrays.asList(255, 255, 255, 255, 255, 255, 255, 255, 255, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255)));
    pixels.add(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 255, 255, 255, 255,
        255, 255, 255, 255, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0)));
    pixels.add(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 255, 255, 255, 255,
        255, 255, 255, 255, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0)));
    pixels.add(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 255, 255, 255, 255,
        255, 255, 255, 255, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0)));
    pixels.add(new ArrayList<Integer>(Arrays.asList(255, 255, 255, 255, 255, 255, 255, 255, 255, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255)));
    pixels.add(new ArrayList<Integer>(Arrays.asList(255, 255, 255, 255, 255, 255, 255, 255, 255, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255)));
    pixels.add(new ArrayList<Integer>(Arrays.asList(255, 255, 255, 255, 255, 255, 255, 255, 255, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 255, 255, 255, 255, 255, 255, 255, 255, 255)));
    assertEquals(pixels, checkerBoard.getPixels());
  }
}