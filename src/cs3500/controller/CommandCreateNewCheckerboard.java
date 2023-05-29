package cs3500.controller;

import cs3500.model.IMultiImageProcessingModel;
import java.awt.Color;

/**
 * This class represents a command object that programmatically creates an
 * image of a checkerboard at the current layer if it is empty.
 */
public class CommandCreateNewCheckerboard implements IImageProcessingCommand {

  private final String tileSize;
  private final String numTilesInRow;
  private final String numTilesInCol;

  /**
   * Constructs a CommandCreateNewCheckerboard object.
   *
   * @param tileSize the size in pixels of each side in each tile
   * @param numTilesInRow the number of tiles in each row
   * @param numTilesInCol the number of columns in each tile
   * @throws IllegalArgumentException if any of the arguments passed in are null
   */
  public CommandCreateNewCheckerboard(String tileSize, String numTilesInRow,
      String numTilesInCol) throws IllegalArgumentException {
    if (tileSize == null || numTilesInRow == null
        || numTilesInCol == null) {
      throw new IllegalArgumentException("Cannot have null arguments!!");
    }
    this.tileSize = tileSize;
    this.numTilesInRow = numTilesInRow;
    this.numTilesInCol = numTilesInCol;
  }

  @Override
  public void operate(IMultiImageProcessingModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Cannot have null model!!");
    }
    try {
      int integerTileSize = Integer.parseInt(tileSize);
      int integerNumTilesInRow = Integer.parseInt(numTilesInRow);
      int integerNumTilesInCol = Integer.parseInt(numTilesInCol);
      model.loadNewImage(model.createCheckerboard(integerTileSize, integerNumTilesInRow,
          integerNumTilesInCol, Color.BLACK, Color.WHITE));
    }
    catch (NumberFormatException nfe) {
      throw new IllegalArgumentException("Invalid tileSize, "
          + "numTilesInRow, or numTilesInCol value!!");
    }
  }
}
