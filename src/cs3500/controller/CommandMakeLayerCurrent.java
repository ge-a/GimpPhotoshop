package cs3500.controller;

import cs3500.model.IMultiImageProcessingModel;

/**
 * This class represents a command object which makes the inputted layer number the current layer.
 */
public class CommandMakeLayerCurrent implements IImageProcessingCommand {

  private final String layerNumber;

  /**
   * Constructs a CommandMakeLayerCurrent object.
   *
   * @param layerNumber the layer to be made current
   * @throws IllegalArgumentException if the layer is null
   */
  public CommandMakeLayerCurrent(String layerNumber) throws IllegalArgumentException {
    if (layerNumber == null) {
      throw new IllegalArgumentException("Cannot have a null layer!!");
    }
    this.layerNumber = layerNumber;
  }

  @Override
  public void operate(IMultiImageProcessingModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Cannot have a null model!!");
    }
    try {
      model.updateCurrentLayer(Integer.parseInt(layerNumber));
    } catch (NumberFormatException nfe) {
      throw new IllegalArgumentException("Invalid layer number");
    }
  }
}
