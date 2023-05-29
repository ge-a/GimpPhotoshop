package cs3500.controller;

import cs3500.model.IMultiImageProcessingModel;

/**
 * This class represents a command object that makes an image at an inputted layer invisible.
 */
public class CommandMakeInvisible implements IImageProcessingCommand {

  private final String layerNumber;

  /**
   * Constructs a CommandMakeInvisible object.
   *
   * @param layerNumber the layer to be made invisible
   * @throws IllegalArgumentException if the layerNumber is null
   */
  public CommandMakeInvisible(String layerNumber) throws IllegalArgumentException {
    if (layerNumber == null) {
      throw new IllegalArgumentException("Cannot have a null layerNumber!!");
    }
    this.layerNumber = layerNumber;
  }

  @Override
  public void operate(IMultiImageProcessingModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Cannot have null model!!");
    }
    try {
      model.updateTransparency(Integer.parseInt(layerNumber), false);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid layer number!!");
    }
  }
}
