package cs3500.controller;

import cs3500.model.IMultiImageProcessingModel;

/**
 * This class represents a command object that will make the layer located at the provided layer
 * number visible.
 */
public class CommandMakeVisible implements IImageProcessingCommand {

  private final String layerNumber;

  /**
   * Constructs a CommandMakeVisible object.
   *
   * @param layerNumber the layer which is being made visible
   * @throws IllegalArgumentException if the layerNumber is null
   */
  public CommandMakeVisible(String layerNumber) throws IllegalArgumentException {
    if (layerNumber == null) {
      throw new IllegalArgumentException("Argument cannot be null");
    }
    this.layerNumber = layerNumber;
  }

  @Override
  public void operate(IMultiImageProcessingModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    try {
      model.updateTransparency(Integer.parseInt(layerNumber), true);
    }
    catch (NumberFormatException nfe) {
      throw new IllegalArgumentException("Invalid layerNumber");
    }
  }
}
