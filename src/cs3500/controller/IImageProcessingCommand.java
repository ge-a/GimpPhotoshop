package cs3500.controller;

import cs3500.model.IMultiImageProcessingModel;

/**
 * Interface representing a command to be performed in the controller.
 */
public interface IImageProcessingCommand {

  /**
   * Performs the intended command of the command class that inherits this interface.
   *
   * @param model the model used to create the intended effect of the command
   * @throws IllegalArgumentException if the model is null, and differing conditions based on the
   *                                  command
   */
  void operate(IMultiImageProcessingModel model) throws IllegalArgumentException;

}
