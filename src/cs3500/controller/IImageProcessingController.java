package cs3500.controller;

/**
 * Interface for the image processing controller. An implementation of this
 * interface will work with the IMultiImageProcessingModel to create user
 * interaction in processing and filtering images.
 */
public interface IImageProcessingController {

  /**
   * Starts the image processing program where a user can input commands to
   * process, filter, and otherwise manipulate a layer of images.
   */
  void runPhotoshop() throws IllegalArgumentException;
}
