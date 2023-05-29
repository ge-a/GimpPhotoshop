package cs3500.model;

import cs3500.IImage;
import cs3500.Image;
import cs3500.model.IImageProcessingModel;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a model class for Image Processing. This version utilizes images of type
 * {@code IImage} and stores the IImages in a map. This class also supports applying
 * {@code IFilter} transformations and filters to any {@code IImage} within {@code images}, and
 * creating a new {@code IImage} in any way supported by the given {@code ICreateImage}. This
 * class can add a new {@code IImage}, remove an {@code IImage}, and replace an {@code IImage}
 * within images as well.
 */
public class ImageProcessingModel implements IImageProcessingModel {
  protected final Map<String, IImage> images;

  /**
   * Constructs an ImageProcessingModel object.
   */
  public ImageProcessingModel() {
    images = new HashMap<String, IImage>();
  }

  @Override
  public void addImage(String id, IImage image) throws IllegalArgumentException {
    if (id == null || image == null) {
      throw new IllegalArgumentException("Cannot have null image or id!!");
    }
    images.putIfAbsent(id, image);
  }

  @Override
  public void removeImage(String id) throws IllegalArgumentException {
    if (id == null) {
      throw new IllegalArgumentException("Cannot have null id!!");
    }
    images.remove(id);
  }

  @Override
  public void replaceImage(String id, IImage image) throws IllegalArgumentException {
    if (id == null || image == null) {
      throw new IllegalArgumentException("Cannot have null image or id!!");
    }
    images.replace(id, image);
  }

  @Override
  public final IImage getImage(String id) throws IllegalArgumentException {
    if (id == null) {
      throw new IllegalArgumentException("Cannot have a null id");
    }
    return images.get(id);
  }

  @Override
  public int getImagesStoredSize() {
    return images.size();
  }

  @Override
  public IImage blur(IImage image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("ID cannot be null");
    }
    ArrayList<ArrayList<Double>> kernel = new ArrayList<ArrayList<Double>>();
    kernel.add(new ArrayList<Double>(Arrays.asList(.0625, .125, .0625)));
    kernel.add(new ArrayList<Double>(Arrays.asList(.125, .25, .125)));
    kernel.add(new ArrayList<Double>(Arrays.asList(.0625, .125, .0625)));
    return kernelFilterImage(image, kernel);
  }

  @Override
  public IImage sharpen(IImage image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("ID cannot be null");
    }
    ArrayList<ArrayList<Double>> kernel = new ArrayList<ArrayList<Double>>();

    kernel.add(new ArrayList<Double>(Arrays.asList(-0.125, -0.125, -0.125, -0.125, -0.125)));
    kernel.add(new ArrayList<Double>(Arrays.asList(-0.125, 0.25, 0.25, 0.25, -0.125)));
    kernel.add(new ArrayList<Double>(Arrays.asList(-0.125, 0.25, 1.0, 0.25, -0.125)));
    kernel.add(new ArrayList<Double>(Arrays.asList(-0.125, 0.25, 0.25, 0.25, -0.125)));
    kernel.add(new ArrayList<Double>(Arrays.asList(-0.125, -0.125, -0.125, -0.125, -0.125)));
    return kernelFilterImage(image, kernel);
  }

  @Override
  public IImage greyscale(IImage image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("ID cannot be null");
    }
    ArrayList<ArrayList<Double>> transformationValues = new ArrayList<>();
    transformationValues.add(new ArrayList<Double>(Arrays.asList(.2126, .7152, .0722)));
    transformationValues.add(new ArrayList<Double>(Arrays.asList(.2126, .7152, .0722)));
    transformationValues.add(new ArrayList<Double>(Arrays.asList(.2126, .7152, .0722)));
    return colorFilterImage(image, transformationValues);
  }

  @Override
  public IImage sepia(IImage image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("ID cannot be null");
    }
    ArrayList<ArrayList<Double>> transformationValues = new ArrayList<ArrayList<Double>>();
    transformationValues.add(new ArrayList<Double>(Arrays.asList(.393, .769, .189)));
    transformationValues.add(new ArrayList<Double>(Arrays.asList(.349, .686, .168)));
    transformationValues.add(new ArrayList<Double>(Arrays.asList(.272, .534, .131)));
    return colorFilterImage(image, transformationValues);
  }

  @Override
  public IImage createCheckerboard(int tileSize, int numTilesInRow, int numTilesInCol, Color col1,
      Color col2) throws IllegalArgumentException {
    if (tileSize <= 1) {
      throw new IllegalArgumentException("Cannot have a tileSize of 1 or less!!");
    }
    if (numTilesInCol <= 0 || numTilesInRow <= 0) {
      throw new IllegalArgumentException("Cannot have 0 or less tiles in each column or row");
    }
    if (col1 == null || col2 == null) {
      throw new IllegalArgumentException("Cannot have null colors");
    }
    int tileColorRow = 0;
    int tileColorCol = 0;
    List<List<Integer>> pixels = new ArrayList<>();
    for (int i = 0; i < tileSize * numTilesInCol; i++) {
      pixels.add(new ArrayList<>());
      if (i % tileSize == 0) {
        tileColorCol++;
      }
      if (tileColorCol % 2 == 0) {
        tileColorRow++;
      }
      for (int j = 0; j < tileSize * numTilesInRow; j++) {
        if (j % tileSize == 0) {
          tileColorRow++;
        }
        if (tileColorRow % 2 == 0) {
          pixels.get(i).add(col1.getRed());
          pixels.get(i).add(col1.getGreen());
          pixels.get(i).add(col1.getBlue());
        }
        else if (tileColorRow % 2 == 1) {
          pixels.get(i).add(col2.getRed());
          pixels.get(i).add(col2.getGreen());
          pixels.get(i).add(col2.getBlue());
        }
      }
      tileColorRow = 0;
    }
    return new Image(pixels, tileSize * numTilesInRow, tileSize * numTilesInCol);
  }

  /**
   * Applies a kernel to a every pixel of the an, changing the channel
   * values of each pixel based on the passed in kernel. Requires the
   * kernel to a square with an odd number of values on each side.
   *
   * @param image the image to be changed
   * @param kernel the kernel by which the pixels' values will be changed
   * @return a new image with the kernel applied to each pixel of the image
   * @throws IllegalArgumentException if the image or kernel is null
   */
  protected IImage kernelFilterImage(IImage image, ArrayList<ArrayList<Double>> kernel)
      throws IllegalArgumentException {
    if (image == null || kernel == null) {
      throw new IllegalArgumentException("Cannot have a null image/kernel");
    }
    for (int i = 0; i < kernel.size(); i++) {
      if (kernel.size() % 2 != 1 && kernel.get(i).size() % 2 != 1) {
        throw new IllegalArgumentException("Cannot have kernel with even dimensions!!");
      }
    }
    List<List<Integer>> pixels = image.getPixels();
    List<List<Integer>> newPixels = new ArrayList<>();

    for (int i = 0; i < pixels.size(); i++) {
      newPixels.add(new ArrayList<>());
      for (int j = 0; j < pixels.get(i).size(); j++) {
        int startY = i - kernel.size() / 2;
        int channelVal = 0;
        for (int y = 0; y < kernel.size(); y++) {
          int startX = (j - ((kernel.get(0).size() / 2) * 3));
          for (int x = 0; x < kernel.get(y).size(); x++) {
            if (startY >= 0 && startX >= 0
                && startY < pixels.size()
                && startX < pixels.get(i).size()) {
              if (j % 3 == 0) {
                channelVal += (int) (pixels.get(startY).get(startX) * kernel.get(y).get(x));
              }
              if (j % 3 == 1) {
                channelVal += (int) (pixels.get(startY).get(startX) * kernel.get(y).get(x));
              }
              if (j % 3 == 2) {
                channelVal += (int) (pixels.get(startY).get(startX) * kernel.get(y).get(x));
              }
            }
            startX += 3;
          }
          startY += 1;
        }
        newPixels.get(i).add(clamp(channelVal));
      }
    }
    return new Image(newPixels, image.getHeight(), image.getWidth());
  }

  /**
   * Applies a color filter onto an image multiplying color values of an image based
   * on the passed in arraylist of values by which to transform each pixel's color values.
   *
   * @param image the image which has a filter applied to it
   * @param transformationValues the values by which each color value will be altered
   * @return a new image with the desired filter applied
   * @throws IllegalArgumentException if the image or transformationValues are null
   */
  protected IImage colorFilterImage(IImage image, ArrayList<ArrayList<Double>> transformationValues)
      throws IllegalArgumentException {
    if (image == null || transformationValues == null) {
      throw new IllegalArgumentException("Cannot have null image/transformation values");
    }
    List<List<Integer>> pixels = image.getPixels();
    List<List<Integer>> newPixels = new ArrayList<>();
    int colorValueR;
    int colorValueG;
    int colorValueB;
    for (int i = 0; i < pixels.size(); i++) {
      newPixels.add(new ArrayList<>());
      for (int j = 0; j < pixels.get(i).size(); j++) {
        if (j % 3 == 0) {
          colorValueR = (int) ((pixels.get(i).get(j) * transformationValues.get(0).get(0))
              + (pixels.get(i).get(j + 1) * transformationValues.get(0).get(1))
              + (pixels.get(i).get(j + 2) * transformationValues.get(0).get(2)));
          newPixels.get(i).add(clamp(colorValueR));
        }
        if (j % 3 == 1) {
          colorValueG = (int) ((pixels.get(i).get(j - 1) * transformationValues.get(1).get(0))
              + (pixels.get(i).get(j) * transformationValues.get(1).get(1))
              + (pixels.get(i).get(j + 1) * transformationValues.get(1).get(2)));
          newPixels.get(i).add(clamp(colorValueG));
        }
        if (j % 3 == 2) {
          colorValueB = (int) ((pixels.get(i).get(j - 2) * transformationValues.get(2).get(0))
              + (pixels.get(i).get(j - 1) * transformationValues.get(2).get(1))
              + (pixels.get(i).get(j) * transformationValues.get(2).get(2)));
          newPixels.get(i).add(clamp(colorValueB));
        }
      }
    }
    return new Image(newPixels, image.getHeight(), image.getWidth());
  }


  /**
   * Prevents any filters being applied to an image from
   * altering any channel value of a pixel to be above 255.
   * @param colorValue the value of a channel
   * @return the value of the channel if it is under 256
   *         otherwise, return 255
   */
  protected int clamp(int colorValue) {
    if (colorValue < 256 && colorValue >= 0) {
      return colorValue;
    }
    else if (colorValue < 0) {
      return 0;
    }
    else {
      return 255;
    }
  }
}
