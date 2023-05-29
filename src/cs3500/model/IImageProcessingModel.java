package cs3500.model;

import cs3500.IImage;
import java.awt.Color;

/**
 * Interface representing models for Image Processing. This interface supports applying a filter
 * transforming and creating an image, as well as modifying the images stored within any model that
 * extends it. Different classes can extend this to create different models for processing images.
 */
public interface IImageProcessingModel {
  
  /**
   * Adds the provided {@code IImage}at the given id.
   *
   * @param id    id of the image being added
   * @param image image to be added
   * @throws IllegalArgumentException if id or image are null
   */

  void addImage(String id, IImage image) throws IllegalArgumentException;

  /**
   * Removes the image at the provided id.
   *
   * @param id id of the image being added
   * @throws IllegalArgumentException if id is null
   */

  void removeImage(String id) throws IllegalArgumentException;

  /**
   * Replaces the image at the provided id with the given {@code IImage}.
   *
   * @param id    id of the image being replaced
   * @param image image to be added at the provided id
   * @throws IllegalArgumentException if id or image is null
   */

  void replaceImage(String id, IImage image) throws IllegalArgumentException;

  /**
   * Returns the image at the given id.
   *
   * @param id id of the image being returned
   * @return {@code IImage} at the given id
   * @throws IllegalArgumentException if id is null
   */

  IImage getImage(String id) throws IllegalArgumentException;

  /**
   * Returns the amount of images stored.
   *
   * @return int, amount of images stored
   */

  int getImagesStoredSize();

  /**
   * Blurs an image by applying a 3x3 kernel with different fractions to each channel of every pixel
   * of an inputted image.
   *
   * @param image the image to be blurred
   * @return a blurred image
   * @throws IllegalArgumentException if the image is null
   */

  IImage blur(IImage image) throws IllegalArgumentException;

  /**
   * Sharpens an image by applying a 5x5 kernel with different fractions to each channel of every
   * pixel of an inputted image.
   *
   * @param image the image to be sharpened
   * @return a sharpened image
   * @throws IllegalArgumentException if the image is null
   */
  IImage sharpen(IImage image) throws IllegalArgumentException;

  /**
   * Makes an image appear to be black and white by transforming the channel values of every pixel
   * of the image.
   *
   * @param image the image to be turned black and white
   * @return a greyscale image
   * @throws IllegalArgumentException if the image is null
   */
  IImage greyscale(IImage image) throws IllegalArgumentException;

  /**
   * Makes an image have a sepia tone by transforming the channel values of every pixel of the
   * image.
   *
   * @param image the image to have the sepia tone
   * @return a sepia image
   * @throws IllegalArgumentException if the image is null
   */
  IImage sepia(IImage image) throws IllegalArgumentException;

  /**
   * Creates an image with a checkerboard pattern to the parameters inputted.
   *
   * @param tileSize      the size of in pixels of the side of a tile
   * @param numTilesInRow the number of tiles in each row of the checkerboard
   * @param numTilesInCol the number of tiles in each column of the checkerboard
   * @param col1          the first color of the checkerboard
   * @param col2          the second color of the checkerboard
   * @return a new image with a checkerboard pattern
   * @throws IllegalArgumentException if the colors passed in are null, or if the tileSize is less
   *                                  than or equal to 1, or if the numTilesInRow or numTilesInCol
   *                                  are less than or equal to 0
   */
  IImage createCheckerboard(int tileSize, int numTilesInRow, int numTilesInCol, Color col1,
      Color col2) throws IllegalArgumentException;
}
