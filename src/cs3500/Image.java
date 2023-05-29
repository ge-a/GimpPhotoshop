package cs3500;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a PPM, JPG, JPEG, or PNG image of type {@code IImage}. Support for returning the
 * file as a string, returning the pixels of the image, height, and width.
 */
public final class Image implements IImage {

  protected final List<List<Integer>> pixels;
  protected final int height;
  protected final int width;

  /**
   * Constructs an Image object and initializes its fields to the inputted values.
   * @param pixels the 2D arraylist of pixels in the image
   * @param height the height of the image
   * @param width the width of the image
   * @throws IllegalArgumentException if pixels is null or the height/width is zero
   */
  public Image(List<List<Integer>> pixels, int height, int width)
      throws IllegalArgumentException {
    if (pixels == null || height == 0 || width == 0) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }
    this.pixels = new ArrayList<>();
    for (int i = 0; i < pixels.size(); i++) {
      this.pixels.add(new ArrayList<>());
      for (int j = 0; j < pixels.get(i).size(); j++) {
        this.pixels.get(i).add(pixels.get(i).get(j));
      }
    }
    this.height = height;
    this.width = width;
  }

  @Override
  public List<List<Integer>> getPixels() {
    List<List<Integer>> channelList = new ArrayList<>();
    for (int i = 0; i < pixels.size(); i++) {
      channelList.add(new ArrayList<>());
      for (int j = 0; j < pixels.get(i).size(); j++) {
        channelList.get(i).add(pixels.get(i).get(j));
      }
    }
    return channelList;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public String toString() {
    StringBuilder ppm = new StringBuilder();
    ppm.append("P3\n");
    ppm.append(width).append(" ").append(height).append("\n");
    ppm.append("255\n");
    for (List<Integer> pixel : pixels) {
      for (Integer integer : pixel) {
        ppm.append(integer);
        ppm.append("\n");
      }
    }
    return ppm.toString();
  }

  @Override
  public BufferedImage setColorValue(BufferedImage image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("Cannot have null image");
    }
    BufferedImage newBufferedImage =
        new BufferedImage(this.width, this.height, image.getType());
    for (int i = 0; i < this.pixels.size(); i++ ) {
      for (int j = 0; j < this.pixels.get(i).size() - 3; j += 3) {
        Color color = new Color(this.pixels.get(i).get(j),
            this.pixels.get(i).get(j + 1), this.pixels.get(i).get(j + 2));
        int rgb = color.getRGB();
        newBufferedImage.setRGB(j / 3, i, rgb);
      }
    }
    return newBufferedImage;
  }


}
