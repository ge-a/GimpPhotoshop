package cs3500;


import cs3500.controller.IImageProcessingController;
import cs3500.controller.ImageProcessingControllerCreator;
import cs3500.controller.ImageProcessingControllerCreator.ControllerType;
import cs3500.controller.ImageProcessingGUIController;
import cs3500.model.IMultiImageModelDownScaleSupport;
import cs3500.model.IMultiImageProcessingModel;
import cs3500.model.MultiImageModelDownScaleSupport;
import cs3500.model.MultiImageProcessingModel;
import cs3500.view.MyWindow;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.imageio.ImageIO;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method as required.
 */
public class ImageUtil {

  /**
   * Imports in a folder of images with a text file of the specifications of the layers, and
   * visibility of each layer for the images to the model.
   *
   * @param filename the name of the file to be read
   * @param model    the model to set the inputted data
   * @throws IllegalArgumentException if the filename is null, if the file cannot be found, if the
   *                                  filetype is invalid, if the text of the boolean value is not
   *                                  true or false
   */
  public static void readMultiImage(String filename,
      IMultiImageProcessingModel model) throws IllegalArgumentException {
    ArrayList<ILayer> layers = new ArrayList<>();
    if (filename == null) {
      throw new IllegalArgumentException("File cannot be null");
    }
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(filename + "/instructions.txt"));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found");
    }
    while (sc.hasNext()) {
      StringBuilder builder = new StringBuilder();
      String imageName = sc.next();
      String type = sc.next();
      String visibility = sc.next().toLowerCase();
      IImage newImage;
      boolean isVisible;
      if (visibility.equals("true")) {
        isVisible = true;
      } else if (visibility.equals("false")) {
        isVisible = false;
      } else {
        throw new IllegalArgumentException("Invalid boolean value!!");
      }
      switch (type.toLowerCase()) {
        case "png":
        case "jpg":
        case "jpeg":
          builder.append(filename).append("/").append(imageName).append(".").append(type);
          newImage = readJPGPNG(builder.toString());
          break;
        case "ppm":
          builder.append(filename).append("/").append(imageName).append(".").append(type);
          newImage = readPPMImage(builder.toString());
          break;
        default:
          throw new IllegalArgumentException("Invalid file type in text file!!");
      }
      layers.add(new Layer(newImage, isVisible));
    }
    model.setLayers(layers);
  }

  /**
   * Imports in a PPM file to be an IImage.
   *
   * @param filename the name of the file to be imported
   * @return an IImage of the PPM that was imported in
   * @throws IllegalArgumentException if the filename is null, if the file cannot be found, if the
   *                                  file does not begin with P3
   */
  public static IImage readPPM(String filename) {
    return readPPMImage(filename);
  }

  /**
   * Helper method that goes through a PPM file to get its height, width, and pixels in a RGB
   * representation so it can be imported in as type IImage.
   *
   * @param filename the name of the file to be imported in
   * @return the inputted file as type IImage
   * @throws IllegalArgumentException if the filename is null, if the file cannot be found, if the
   *                                  file does not begin with P3
   */
  private static IImage readPPMImage(String filename) throws IllegalArgumentException {
    if (filename == null) {
      throw new IllegalArgumentException("File cannot be null");
    }
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }
    String s = builder.toString();

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    List<List<Integer>> pixels = new ArrayList<List<Integer>>();
    for (int i = 0; i < height; i++) {
      pixels.add(i, new ArrayList<>());
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        pixels.get(i).add(r);
        pixels.get(i).add(g);
        pixels.get(i).add(b);
      }
    }
    return new Image(pixels, height, width);
  }

  /**
   * Imports a JPEG, JPG, or PNG image to be an IImage.
   *
   * @param filename the name of the file to be imported
   * @return an image of type IImage of the JPEG, JPG, or PNG that was imported in
   * @throws IllegalArgumentException if the filename is null, if the file cannot be accessed.
   */
  public static IImage readImage(String filename) throws IllegalArgumentException {
    return readJPGPNG(filename);
  }

  /**
   * Helper method which goes through a PNG, JPG, or JPEG file and gets its height, width, and
   * pixels in an RGB representation so it can be imported as an IImage.
   *
   * @param filename the name of the file to be imported
   * @return an IImage representation of the file to be imported
   * @throws IllegalArgumentException if the filename is null or if the file cannot be accessed
   */
  private static IImage readJPGPNG(String filename) throws IllegalArgumentException {
    if (filename == null) {
      throw new IllegalArgumentException("Cannot have null filename!!");
    }
    List<List<Integer>> pixels = new ArrayList<>();
    int height;
    int width;
    try {
      File file = new File(filename);
      BufferedImage image = ImageIO.read(file);
      height = image.getHeight();
      width = image.getWidth();
      for (int i = 0; i < height; i++) {
        pixels.add(i, new ArrayList<>());
        for (int j = 0; j < width; j++) {
          int pixel = image.getRGB(j, i);
          Color color = new Color(pixel, true);
          int r = color.getRed();
          int g = color.getGreen();
          int b = color.getBlue();
          pixels.get(i).add(r);
          pixels.get(i).add(g);
          pixels.get(i).add(b);
        }
      }
    } catch (IOException io) {
      throw new IllegalArgumentException("File cannot be accessed");
    }
    return new Image(pixels, height, width);
  }

  /**
   * Exports a layered image in a folder with all of the images and a text file with the order of
   * each layer and its visibility.
   *
   * @param layers     the list of layers to be exported
   * @param fileType   the type of the file to be exported
   * @param folderName the name of the folder to exported
   * @throws IllegalArgumentException if any of the arguments passed in are null, if the filetype is
   *                                  invalid, or if it could not write to the interface
   */
  public static void exportLayeredImage(List<ILayer> layers, String fileType,
      String folderName) throws IllegalArgumentException {
    if (layers == null || fileType == null || folderName == null) {
      throw new IllegalArgumentException("Cannot have null arguments!!");
    }
    try {
      Files.createDirectories(Paths.get(folderName));
      StringBuilder layerNameNum;
      StringBuilder textFileInstructions = new StringBuilder();
      switch (fileType.toLowerCase()) {
        case "png":
        case "jpg":
        case "jpeg":
          for (int i = 0; i < layers.size(); i++) {
            layerNameNum = new StringBuilder();
            formatFiles(layerNameNum, textFileInstructions, fileType, i,
                layers, folderName);
            exportPNGJPG(layers.get(i).getImage(), layerNameNum.toString(), fileType);
          }
          exportNewFile(textFileInstructions.toString(), folderName + "/instructions.txt");
          break;
        case "ppm":
          for (int i = 0; i < layers.size(); i++) {
            layerNameNum = new StringBuilder();
            formatFiles(layerNameNum, textFileInstructions, fileType, i,
                layers, folderName);
            exportNewFile(layers.get(i).getImage().toString(),
                layerNameNum.toString());
          }
          exportNewFile(textFileInstructions.toString(), folderName + "/instructions.txt");
          break;
        default:
          throw new IllegalArgumentException("Invalid filetype!!");
      }

    } catch (IOException io) {
      throw new IllegalArgumentException("Could not create new Layered Image");
    }
  }

  /**
   * Helper method to continue appending to the strings needed to export a layered image.
   *
   * @param layerNameNum         the name of the layer number
   * @param textFileInstructions the instructions of how to import the file back
   * @param fileType             the type of file to export
   * @param i                    the index to get the layer at
   * @param layers               the list of layers to export
   * @param folderName           the name of the folder to be exported to
   * @throws IllegalArgumentException if any of the arguments passed in are null
   */
  private static void formatFiles(StringBuilder layerNameNum, StringBuilder textFileInstructions,
      String fileType, int i, List<ILayer> layers, String folderName)
      throws IllegalArgumentException {
    if (layerNameNum == null || textFileInstructions == null || fileType == null || layers == null
        || folderName == null) {
      throw new IllegalArgumentException("Cannot have null arguments!!");
    }
    layerNameNum.append(folderName).append("/").append(i).append(".")
        .append(fileType);
    textFileInstructions.append(i).append(" ").append(fileType).append(" ")
        .append(layers.get(i).getVisibility()).append("\n");
    exportNewFile(textFileInstructions.toString(), folderName + "/instructions.txt");
  }


  /**
   * Exports an image of type PNG, JPG, or JPEG using ImageIO.
   *
   * @param image    the image to be exported
   * @param filename the name of the file to be exported
   * @param type     the type of the image
   * @throws IllegalArgumentException if any of the arguments are null or if it could not write to
   *                                  the interface
   */
  public static void exportImage(IImage image, String filename, String type)
      throws IllegalArgumentException {
    if (image == null || filename == null || type == null) {
      throw new IllegalArgumentException("Image/filename cannot be null");
    }
    exportPNGJPG(image, filename, type);
  }

  /**
   * Helper to export a PNG, JPG, or JPEG file using ImageIO.
   *
   * @param image    the image to be exported
   * @param filename the name of the file to be exported
   * @param type     the type of the image
   * @throws IllegalArgumentException if any of the arguments are null or if it could not write to
   *                                  the interface
   */
  private static void exportPNGJPG(IImage image, String filename, String type)
      throws IllegalArgumentException {
    if (image == null || filename == null || type == null) {
      throw new IllegalArgumentException("Image/filename cannot be null");
    }
    String typeLower = type.toLowerCase();
    if (!typeLower.equals("png") && !typeLower.equals("jpeg") && !typeLower.equals("jpg")) {
      throw new IllegalArgumentException("Invalid filetype");
    }
    try {
      File newFile = new File(filename);
      BufferedImage newImage = new BufferedImage(image.getHeight(), image.getWidth(),
          BufferedImage.TYPE_INT_RGB);
      newImage = image.setColorValue(newImage);
      ImageIO.write(newImage, type, newFile);
    } catch (IOException io) {
      throw new IllegalArgumentException("Could not write to interface");
    }
  }

  /**
   * Exports a PPM file using a FileWriter.
   *
   * @param image    the image to be exported
   * @param filename the name of the file to be exported
   * @throws IllegalArgumentException if the image or filename are null or if it could not write to
   *                                  the interface
   */
  public static void exportPPMImage(String image, String filename) throws IllegalArgumentException {
    if (image == null || filename == null) {
      throw new IllegalArgumentException("Image/filename cannot be null");
    }
    exportNewFile(image, filename);
  }

  /**
   * Helper method to export a text based file using a FileWriter.
   *
   * @param image    the image to be exported
   * @param filename the name of the file to be exported
   * @throws IllegalArgumentException if the image or filename are null or if it could not write to
   *                                  interface
   */
  private static void exportNewFile(String image, String filename) throws IllegalArgumentException {
    if (image == null || filename == null) {
      throw new IllegalArgumentException("Image/filename cannot be null");
    }
    try {
      FileWriter myWriter = new FileWriter(filename);
      myWriter.write(image);
      myWriter.close();
    } catch (IOException io) {
      throw new IllegalArgumentException("Could not write to interface");
    }
  }

  /**
   * Main for running the controller and use the image processing program.
   *
   * @param args array of string arguments
   */
  public static void main(String[] args) {
    IMultiImageModelDownScaleSupport model = new MultiImageModelDownScaleSupport();
    if (args.length == 0) {
      MyWindow window = new MyWindow(model);
      window.setVisible(true);
      IImageProcessingController c = new ImageProcessingGUIController(model, window);
      c.runPhotoshop();
    } else {
      controllerSelection(args);
    }
  }

  /**
   * Decides what kind of program will be ran from a command line input.
   *
   * @param args array of strings relating to the type of controller used
   */
  protected static void controllerSelection(String[] args) {
    switch (args[0]) {
      case "-script":
        StringBuilder command = new StringBuilder();
        command.append("script ");
        command.append(args[1]);
        command.append(" q");
        Readable reader = new java.io.StringReader(command.toString());
        IImageProcessingController c = ImageProcessingControllerCreator
            .createControllerSameReadable(
                ControllerType.SCRIPTINGANDMANUALINPUT, new MultiImageProcessingModel(), reader);
        c.runPhotoshop();
        return;
      case "-text":
        IImageProcessingController c1 =
            ImageProcessingControllerCreator.create(
                ControllerType.SCRIPTINGANDMANUALINPUT);
        c1.runPhotoshop();
        return;
      case "-interactive":
        IImageProcessingController c2 =
            ImageProcessingControllerCreator.create(
                ControllerType.GUI);
        c2.runPhotoshop();
        return;
      default:
        System.out.println("Invalid Command");
    }
  }

}

