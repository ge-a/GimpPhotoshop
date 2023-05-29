package cs3500.model;

import cs3500.IImage;
import cs3500.ILayer;
import java.util.ArrayList;

/**
 * Interface representing models for Image Processing of images with multiple layers.
 * This interface supports updating the transparency of a layer, updating which layer is the current
 * layer, removing a layer, adding a layer to the top, loading a new image onto the current layer,
 * getting the image on the current layer, getting which image is topmost and visible, changing the
 * image on a layer, getting all the layers of a multi-image, creating a multi-image from a provided
 * set of layers, and getting the order of the last current images.
 */
public interface IMultiImageProcessingModel extends IImageProcessingModel, IModelView {

  /**
   * Updates the transparency state of the layer located at the given layerNumber to the provided
   * boolean status, true being visible and false being invisible.
   * @param layerNumber the location of the layer which is getting its transparency status changed
   * @param status the new status of the layer at the provided location
   * @throws IllegalArgumentException if the layerNumber is invalid, ie. does not have a layer at
   *                                  that location
   */
  void updateTransparency(int layerNumber, boolean status) throws IllegalArgumentException;

  /**
   * Sets the layer at the provided layerNumber as the current layer.
   * @param layerNumber the location of the layer being set as current
   * @throws IllegalArgumentException if the provided layerNumber is invalid, ie. does not have
   *                                  a layer at that location
   */
  void updateCurrentLayer(int layerNumber) throws IllegalArgumentException;

  /**
   * Removes the layer at the provided layerNumber.
   * @param layerNumber the location of the layer being removed
   * @throws IllegalArgumentException if the layerNumber is invalid, ie. does not have a layer at
   *                                  that location
   */
  void removeLayer(int layerNumber) throws IllegalArgumentException;

  /**
   * Adds an empty layer to the end of layers of the multi-image within the model.
   */
  void addEmptyLayer();

  /**
   * Loads the provided image onto the current layer if the layer is empty and if the
   * size of the image matches those already in the multi-image.
   * @param image the image to be added to the current layer
   * @throws IllegalArgumentException if the provided image is null, or if the image sizes do not
   *                                  match
   * @throws IllegalStateException if the current layer already has an image on it, or there are no
   *                              layers at all
   */
  void loadNewImage(IImage image) throws IllegalArgumentException, IllegalStateException;

  /**
   * Changes the IImage of the current layer to the provided IImage.
   * @param image the IImage being swapped into the current layer
   * @throws IllegalArgumentException if the provided image is null
   * @throws IllegalStateException if there are no layers in the multi-image
   */
  void changeLayerImage(IImage image) throws IllegalArgumentException, IllegalStateException;

  /**
   * Sets all the layers in the model to the provided list of ILayers.
   * @param layers the layers which will be set as the layers within the model
   * @throws IllegalArgumentException  if the list is null or a layer is null within the list
   */
  void setLayers(ArrayList<ILayer> layers) throws IllegalArgumentException;

}
