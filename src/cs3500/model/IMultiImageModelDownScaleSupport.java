package cs3500.model;

/**
 * Interface representing models for Image Processing of images with multiple layers, all the prior
 * operations supported in the IMultiImageProcessingModel in addition to the ability to downscale
 * a multiImage.
 */
public interface IMultiImageModelDownScaleSupport extends IMultiImageProcessingModel {

  /**
   * Downsizes every layer in the multi-image to the desired height and width.
   * @param newWidth the desired new width of the multi-image
   * @param newHeight the desired new height of the multi-image
   * @throws IllegalStateException if there are no images to downsize
   * @throws IllegalArgumentException if the arguments are null, less than zero, or greater than or
   *                                  equal to the current width and height of the multi-image
   */
  void downScaleMultiImage(int newWidth, int newHeight)
      throws IllegalArgumentException, IllegalStateException;
}
