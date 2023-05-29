package cs3500.model;

import cs3500.ILayer;
import cs3500.Image;
import cs3500.Layer;
import java.util.ArrayList;
import java.util.List;

/**
 * The class represents a model class for processing multi-images with multiple images stored
 * across several different layers. It supports all the prior functionality of the
 * MultiImageProcessingModel in additiona to support for downscaling
 */
public class MultiImageModelDownScaleSupport extends MultiImageProcessingModel implements
    IMultiImageModelDownScaleSupport {

  /**
   * Constructs a MultiImageModelDownScaleSupport object.
   */
  public MultiImageModelDownScaleSupport() {
    super();
  }

  @Override
  public void downScaleMultiImage(int newWidth, int newHeight) {
    if (this.height == 0 && this.width == 0) {
      throw new IllegalStateException("No images to downsize");
    }
    if (this.height <= newHeight || this.width <= newWidth) {
      throw new IllegalArgumentException("Invalid action command, cannot expand image");
    }
    if (newWidth <= 0 || newHeight <= 0) {
      throw new IllegalArgumentException("Width and height must be greater than zero");
    }
    List<ILayer> newLayer = new ArrayList<ILayer>();
    for (ILayer l : layers) {
      if (l.getImage().getWidth() == -1 && l.getImage().getHeight() == -1) {
        newLayer.add(l);
      }
      else {
        List<List<Integer>> oldPixels = l.getImage().getPixels();
        List<List<Integer>> newPixels = new ArrayList<>();
        for (int i = 0; i < newHeight; i++) {
          newPixels.add(new ArrayList<Integer>());
          int xPixelValue = 0;
          for (int j = 0; j < newWidth * 3; j += 3) {
            downScaleChannel(i, newHeight, newWidth, 0, newPixels, oldPixels, xPixelValue);
            downScaleChannel(i, newHeight, newWidth, 1, newPixels, oldPixels, xPixelValue);
            downScaleChannel(i, newHeight, newWidth, 2, newPixels, oldPixels, xPixelValue);

            xPixelValue++;
          }
        }
        newLayer.add(new Layer(new Image(newPixels, newHeight, newWidth), l.getVisibility()));
      }
    }
    layers = newLayer;
    this.height = newHeight;
    this.width = newWidth;
  }

  /**
   * Performs the downsizing algorithm on the given channelNum in the given pixel location.
   *
   * @param channelRow the y-coordinate of the current pixel
   * @param newHeight the new height of the multi-image
   * @param newWidth the new width of the multi-image
   * @param channelNum the channel being operated on within the current pixel
   * @param newPixels the pixels of the downsized image
   * @param oldPixels the pixels of the previous image
   * @param xPixelValue the x-coordinate of the current pixel
   * @throws IllegalArgumentException if any of the arguments are null or the width/height are less
   *                                  than equal to zero
   */

  protected void downScaleChannel(int channelRow,
      int newHeight, int newWidth, int channelNum, List<List<Integer>> newPixels,
      List<List<Integer>> oldPixels, int xPixelValue) throws IllegalArgumentException {
    if (newPixels == null || oldPixels == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }
    if (newWidth <= 0 || newHeight <= 0) {
      throw new IllegalArgumentException("New height and new width must be greater than zero");
    }

    double heightRatio = (double) (height) / (double) (newHeight);
    double widthRatio = (double) (width) / (double) (newWidth);
    double yCord = channelRow * heightRatio;
    double xCord = xPixelValue * widthRatio;

    int downX = (int) xCord;
    int upX = (int) xCord + 1;

    int downY = (int) yCord;
    int upY = downY + 1;

    int a = oldPixels.get(downY).get(downX * 3 + channelNum);
    int b = oldPixels.get(downY).get(upX * 3 + channelNum);
    int c = oldPixels.get(upY).get(downX * 3 + channelNum);
    int d = oldPixels.get(upY).get(upX * 3 + channelNum);

    double m = b * (xCord - downX) + (a * (upX - xCord));
    double n = d * (xCord - downX) + (c * (upX - xCord));
    double colorValue = n * (yCord - downY) + (m * (upY - yCord));

    newPixels.get(channelRow).add((int) colorValue);
  }
}
