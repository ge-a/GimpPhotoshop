package cs3500.view;

/**
 * Interface representing the different operations that should be able to
 * performed from listening to the actions events from the GUI.
 */
public interface IViewListener {

  /**
   * Creates a new layer in the multi-image when there is a create event in the GUI.
   */
  void handleCreateEvent();

  /**
   * Loads an image into an empty layer in a multi-image when there is a load event
   * in the GUI.
   *
   * @param file the file to be loaded in
   * @throws IllegalArgumentException if the file is null
   */
  void handleLoadEvent(String file) throws IllegalArgumentException;

  /**
   * Makes a layer at the inputted index the current layer when there is
   * a current event in the GUI.
   *
   * @param layerNum the layer to be made the current
   * @throws IllegalArgumentException if the layer number is null
   */
  void handleCurrentEvent(String layerNum) throws IllegalArgumentException;

  /**
   * Makes a layer invisible at the inputted layer number when there
   * is an invisible event in the GUI.
   *
   * @param layerNum the layer to be made invisible
   * @throws IllegalArgumentException if the layer number is null
   */
  void handleInvisibleEvent(String layerNum) throws IllegalArgumentException;

  /**
   * Makes a layer visible at the inputted layer number when there is
   * a visible event in the GUI.
   *
   * @param layerNum the layer to be made invisible
   * @throws IllegalArgumentException if the layer number is null
   */
  void handleVisibleEvent(String layerNum) throws IllegalArgumentException;

  /**
   * Saves the image at the topmost visible layer when there is a save event
   * in the GUI.
   *
   * @param filename the name of the file the image will be saved as
   * @throws IllegalArgumentException if the filename is null
   */
  void handleSaveEvent(String filename) throws IllegalArgumentException;

  /**
   * Removes the layer at the inputted layer number when there is a
   * remove event in the GUI.
   *
   * @param layerNum number of the layer to be removed
   * @throws IllegalArgumentException if the layerNum is null
   */
  void handleRemoveEvent(String layerNum) throws IllegalArgumentException;

  /**
   * Blurs the image at the current layer when there is a blur event in the
   * GUI.
   */
  void handleBlurEvent();

  /**
   * Sharpens the image at the current layer when there is a sharpen event
   * in the GUI.
   */
  void handleSharpenEvent();

  /**
   * Applies the sepia filter to the image at the current layer when there is
   * a sepia event in the GUI.
   */
  void handleSepiaEvent();

  /**
   * Applies the greyscale filter to the image at the current layer when there
   * is a greyscale event in the GUI.
   */
  void handleGreyscaleEvent();

  /**
   * Saves all of the layers of the multi-image into a folder when there is a
   * save all event in the GUI.
   *
   * @param filetype the type of the file to be saved as
   * @param folderName the name of the folder to be saved into
   * @throws IllegalArgumentException if the filetype or folder name are null
   */
  void handleSaveAllEvent(String filetype, String folderName) throws IllegalArgumentException;

  /**
   * Loads an image with the inputted filename into the multi-image
   * when there is a load multi-image event in the GUI.
   *
   * @param filename the name of the file to be loaded in
   * @throws IllegalArgumentException if the filename is null
   */
  void handleLoadMultiImageEvent(String filename) throws IllegalArgumentException;

  /**
   * Creates and loads a checkerboard image into an empty layer when there is a
   * checkerboard event in the GUI.
   *
   * @param tileSize the length of a side of each tile in pixels
   * @param rows the number of rows of tiles in the image
   * @param cols the number of cols of tiles in the image
   * @throws IllegalArgumentException if any of the arguments are null
   */
  void handleCreateCheckerboard(String tileSize, String rows, String cols)
      throws IllegalArgumentException;


  /**
   * Downsizes every layer in the multi-image to the desired height and width.
   *
   * @param newWidth the new width of the multi-image
   * @param newHeight the new height of the multi-image
   * @throws IllegalArgumentException if any of the arguments are null
   */
  void handleDownScale(String newWidth, String newHeight) throws IllegalArgumentException;
}
