package cs3500.model;

import cs3500.IImage;
import cs3500.ILayer;
import cs3500.Image;
import cs3500.Layer;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a model class for processing multi-images with multiple images stored
 * in different layers. This version of the model stores {@code ILayer} in an
 * arraylist to keep track of each image in the layer, and keeps an arraylist of
 * integers to keep track of what layer to mutate. This model class supports
 * many different methods which mutate the data within each layer as well
 * as the list of layers as a whole.
 */
public class MultiImageProcessingModel extends ImageProcessingModel implements
    IMultiImageProcessingModel {

  protected List<ILayer> layers;
  protected List<Integer> current;
  protected int height;
  protected int width;

  /**
   * Constructs a MultiImageProcessingModel object.
   */
  public MultiImageProcessingModel() {
    this.layers = new ArrayList<>();
    this.current = new ArrayList<>();
  }

  @Override
  public void updateTransparency(int layerNumber, boolean status) throws IllegalArgumentException {
    if (this.current.contains(layerNumber - 1)) {
      layers.get(layerNumber - 1).changeVisibility(status);
    } else {
      throw new IllegalArgumentException("Invalid layer number!!");
    }
  }

  @Override
  public void updateCurrentLayer(int layerNumber) throws IllegalArgumentException {
    for (int i = 0; i < current.size(); i++) {
      if (current.get(i) == layerNumber - 1) {
        current.remove(i);
        current.add(0, layerNumber - 1);
        return;
      }
    }
    throw new IllegalArgumentException("Invalid current layer!!");
  }

  @Override
  public void removeLayer(int layerNumber) {
    for (int i = 0; i < current.size(); i++) {
      if (this.current.get(i) == layerNumber - 1) {
        this.current.remove(i);
        this.layers.remove(layerNumber - 1);
        adjustCurrent(layerNumber);
        if (this.layers.size() == 0) {
          this.height = 0;
          this.width = 0;
        }
        return;
      }
    }
    throw new IllegalArgumentException("Layer does not exist! Cannot remove nonexistent layer!");
  }

  /**
   * Adjusts the values in the current arraylist so that
   * the numbers stay in a consecutive order.
   *
   * @param layerNumber the layer which was removed
   */
  private void adjustCurrent(int layerNumber) {
    for (int i = 0; i < this.current.size(); i++) {
      if (this.current.get(i) > layerNumber - 1) {
        this.current.set(i, this.current.get(i) - 1);
      }
    }
  }

  @Override
  public void addEmptyLayer() {
    int maxValue = layers.size();
    layers.add(new Layer());
    this.current.add(0, maxValue);
  }

  @Override
  public void loadNewImage(IImage image) throws IllegalArgumentException, IllegalStateException {
    if (image == null) {
      throw new IllegalArgumentException("Cannot have a null image!!");
    }
    if (this.layers.size() == 0) {
      throw new IllegalStateException("Cannot load image without any layers!");
    }
    int imageWidth = this.layers.get(this.current.get(0)).getImage().getWidth();
    int imageHeight = this.layers.get(this.current.get(0)).getImage().getHeight();
    if (this.height == 0 && this.width == 0) {
      this.height = image.getHeight();
      this.width = image.getWidth();
    } else if (image.getHeight() != this.height && image.getWidth() != this.width) {
      throw new IllegalArgumentException("Invalid Image size, must have a height of: "
          + this.height + " and a width of " + this.width);
    }
    if (imageWidth == -1 && imageHeight == -1) {
      layers.remove((int) this.current.get(0));
      layers.add(this.current.get(0),
          new Layer(new Image(image.getPixels(),
              image.getHeight(), image.getWidth()), true));
    } else {
      throw new IllegalStateException("Cannot load image onto non-empty layer!!");
    }
  }

  @Override
  public IImage getCurrentImage() throws IllegalStateException {
    if (this.current.size() == 0) {
      throw new IllegalStateException("Must have an image to apply a filter to!!");
    }
    return this.layers.get(this.current.get(0)).getImage();
  }

  @Override
  public IImage getTopmostVisible() throws IllegalStateException {
    for (ILayer layer : this.layers) {
      if (layer.getVisibility()) {
        return layer.getImage();
      }
    }
    throw new IllegalStateException("There are no visible images");
  }

  @Override
  public void changeLayerImage(IImage image)
      throws IllegalArgumentException, IllegalStateException {
    if (image == null) {
      throw new IllegalArgumentException("Cannot have null image!!");
    }
    if (this.current.size() == 0) {
      throw new IllegalStateException("There are no layers to swap images in");
    }
    boolean currentLayerVisibility = this.layers.get(this.current.get(0)).getVisibility();
    this.layers.remove((int) this.current.get(0));
    IImage newImage = new Image(image.getPixels(), image.getHeight(), image.getWidth());
    this.layers.add(this.current.get(0), new Layer(newImage, currentLayerVisibility));
  }

  @Override
  public ArrayList<ILayer> getLayers() {
    ArrayList<ILayer> layerCopy = new ArrayList<ILayer>();
    for (ILayer layer : layers) {
      layerCopy.add(new Layer(layer.getImage(), layer.getVisibility()));
    }
    return layerCopy;
  }

  @Override
  public void setLayers(ArrayList<ILayer> layers) throws IllegalArgumentException {
    if (layers == null) {
      throw new IllegalArgumentException("Cannot have null list of layers!!");
    }
    this.current = new ArrayList<>();
    this.layers = new ArrayList<>();
    for (int i = 0; i < layers.size(); i++) {
      if (layers.get(i) == null) {
        throw new IllegalArgumentException("Cannot have a null layer!!");
      }
      this.current.add(i);
      this.layers.add(new Layer(layers.get(i).getImage(), layers.get(i).getVisibility()));
    }
    this.height = layers.get(0).getImage().getHeight();
    this.width = layers.get(0).getImage().getWidth();
  }

  @Override
  public ArrayList<Integer> getCurrentOrder() {
    ArrayList<Integer> currentOrder = new ArrayList<Integer>();
    currentOrder.addAll(this.current);
    return currentOrder;
  }
}
