import static org.junit.Assert.assertEquals;

import cs3500.IImage;
import cs3500.ILayer;
import cs3500.Image;
import cs3500.ImageUtil;
import cs3500.model.IMultiImageProcessingModel;
import cs3500.model.MultiImageProcessingModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * Tests for the ImageUtil class.
 */
public class ImageUtilTest {

  @Test
  public void testImportImageBeePPM() {
    IImage imageTest = ImageUtil.readPPM("res/bee.ppm");

    assertEquals("P3\n"
        + "640 427", imageTest.toString().substring(0,10));
    assertEquals(3010622, imageTest.toString().length());
    assertEquals("1\n"
        + "245\n"
        + "244\n", imageTest.toString().substring(3010612));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImportPPMImageNull() {
    IImage imageTest = ImageUtil.readPPM(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImportPPMImageFileNotFound() {
    IImage imageTest = ImageUtil.readPPM("randomPhoto");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExportPPMImageNullImage() {
    ImageUtil.exportPPMImage(null, "newFile");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExportPPMImageNullFileName() {
    ImageUtil.exportPPMImage("P3\n1 1\n255\n255\n120\n75\n",
        null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadMultiImageNullFilename() {
    IMultiImageProcessingModel model = new MultiImageProcessingModel();
    ImageUtil.readMultiImage(null, model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadMultiImageNullModel() {
    IMultiImageProcessingModel model = new MultiImageProcessingModel();
    ImageUtil.readMultiImage("bee.ppm", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadMultiImageFileNotFound() {
    IMultiImageProcessingModel model = new MultiImageProcessingModel();
    ImageUtil.readMultiImage("null.png", model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadMultiImageInvalidFileType() {
    IMultiImageProcessingModel model = new MultiImageProcessingModel();
    ImageUtil.readMultiImage("null.null", model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadImageNullfilename() {
    ImageUtil.readImage(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadImageNonexistentFile() {
    ImageUtil.readImage("null.png");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExportLayeredImageNullLayers() {
    ArrayList<ILayer> layerMock = new ArrayList<ILayer>();
    ImageUtil.exportLayeredImage(null, "png", "folder");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExportLayeredImageNullFiletype() {
    ArrayList<ILayer> layerMock = new ArrayList<ILayer>();
    ImageUtil.exportLayeredImage(layerMock, null, "folder");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExportLayeredImageNullFolderName() {
    ArrayList<ILayer> layerMock = new ArrayList<ILayer>();
    ImageUtil.exportLayeredImage(layerMock, "png", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExportLayeredImageInvalidFiletype() {
    ArrayList<ILayer> layerMock = new ArrayList<ILayer>();
    ImageUtil.exportLayeredImage(layerMock, "pngf", "folder");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExportImageNullFileName() {
    List<List<Integer>> pixels1x1 =
        new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(255, 120, 75))));
    IImage imageEx = new Image(pixels1x1, 1, 1);
    ImageUtil.exportImage(imageEx,null, "sadf");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExportImageNullFileType() {
    List<List<Integer>> pixels1x1 =
        new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(255, 120, 75))));
    IImage imageEx = new Image(pixels1x1, 1, 1);
    ImageUtil.exportImage(imageEx,"sadf", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExportImageInvalidFileType() {
    List<List<Integer>> pixels1x1 =
        new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(255, 120, 75))));
    IImage imageEx = new Image(pixels1x1, 1, 1);
    ImageUtil.exportImage(imageEx,"sadf", "salsjdf");
  }



}