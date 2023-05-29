package cs3500.controller;

import cs3500.model.IMultiImageModelDownScaleSupport;

/**
 * Interface representing a command to be performed in the controller with the new
 * model supporting the downsizing command as well. Extends old command interface
 * to retain access to all previous commands.
 */
public interface IImageProcessingCommandDownScaleModel extends IImageProcessingCommand {

  /**
   * Performs the intended command of the command class that inherits this interface. Allows
   * commands supported by the IMultiImageModelDownScaleSupport model and all previous commands
   *
   * @param model the model used to create the intended effect of the command
   * @throws IllegalArgumentException if the model is null, and differing conditions based on the
   *                                  command
   */

  void operate(IMultiImageModelDownScaleSupport model);

}
