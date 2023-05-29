package cs3500;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * This interface represents the different types of Images. Different objects can extend this
 * to create different versions of images with different formats.
 */
public interface IImage {

  /**
   * Returns the pixels of this {@code IImage}.
   * @return 2D-array list of the pixels of this {@code IImage}
   */
  List<List<Integer>> getPixels();

  /**
   * Returns the height of this {@code IImage}.
   * @return the height of this {@code IImage}
   */
  int getHeight();

  /**
   * Returns the width of this {@code IImage}.
   * @return the width of this {@code IImage}
   */
  int getWidth();


  /**
   * Produces a string of the image in a PPM format.
   *
   * @return the string of the image in PPM format
   */
  String toString();


  /**
   * Sets the pixels of a JPG, JPEG, or PNG image to have the correct
   * RBG values.
   *
   * @param image the original buffered image
   * @return a buffered image with the corred RBG values
   * @throws IllegalArgumentException if the image is null
   */
  BufferedImage setColorValue(BufferedImage image) throws IllegalArgumentException;


}
