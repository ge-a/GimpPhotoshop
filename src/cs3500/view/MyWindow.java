package cs3500.view;

import cs3500.IImage;
import cs3500.Image;
import cs3500.model.IModelView;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 * Class representing the graphical user interface window will all of its visual features. The
 * MyWindow class also relays the actions performed on this GUI to the controller where the commands
 * which create the desired effect of each user action are called.
 */
public class MyWindow extends JFrame implements IView, ActionListener {

  private final JPanel mainPanel;
  private final JComboBox<String> menu;
  private final JLabel imageLabel;
  private final JComboBox<String> imageSaveLoad;
  private final JComboBox<String> filterDropDown;
  private final JList<String> layerList;
  private final List<IViewListener> viewListeners;
  private final IModelView model;

  /**
   * Constructs a MyWindow object with all of the GUI features.
   *
   * @param model the model of the GUI
   * @throws IllegalArgumentException if the model is null
   */
  public MyWindow(IModelView model) throws IllegalArgumentException {
    super();
    if (model == null) {
      throw new IllegalArgumentException("Cannot have null model!!");
    }
    this.model = model;
    this.viewListeners = new ArrayList<>();
    setSize(new Dimension(600, 600));
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new FlowLayout());

    this.mainPanel = new JPanel();
    JScrollPane mainScrollPain = new JScrollPane(this.mainPanel);
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    Dimension maxMainSize = new Dimension(450, 700);
    mainScrollPain.setPreferredSize(maxMainSize);

    this.menu = new JComboBox<>();
    String[] options1 = {"Remove Layer", "Create Layer", "Make Visible",
        "Make Invisible", "Blur", "Sharpen", "Sepia", "Greyscale", "Save All",
        "Load MultiImage", "Load Image", "Save Image", "Create Checkerboard", "Down Size"};
    for (String option : options1) {
      this.menu.addItem(option);
    }

    this.menu.setActionCommand("Menu");
    this.menu.addActionListener(this);

    JPanel menuPanel = new JPanel();
    menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.PAGE_AXIS));
    this.mainPanel.add(menuPanel);
    menuPanel.add(this.menu);
    javax.swing.JLabel menuLabel = new JLabel("Available Features");
    menuPanel.add(menuLabel);

    JButton removeImage = new JButton("Remove Layer");
    removeImage.setActionCommand("Remove Layer");
    removeImage.addActionListener(this);

    JButton createImage = new JButton("Create Layer");
    createImage.setActionCommand("Create Layer");
    createImage.addActionListener(this);

    JButton makeVisible = new JButton("Make Visible");
    makeVisible.setActionCommand("Make Visible");
    makeVisible.addActionListener(this);

    JButton makeInvisible = new JButton("Make Invisible");
    makeInvisible.setActionCommand("Make Invisible");
    makeInvisible.addActionListener(this);

    JButton makeCurrent = new JButton("Set Current");
    makeCurrent.setActionCommand("Set Current");
    makeCurrent.addActionListener(this);

    this.layerList = new JList<String>();

    this.filterDropDown = new JComboBox<String>();
    String[] options = {"Blur", "Sharpen", "Sepia", "Greyscale"};
    for (String option : options) {
      this.filterDropDown.addItem(option);
    }
    this.filterDropDown.setActionCommand("Image Filter Options");
    this.filterDropDown.addActionListener(this);

    this.imageSaveLoad = new JComboBox<String>();
    String[] options2 = {"Save All", "Load MultiImage", "Save Image",
        "Load Image", "Create Checkerboard"};
    for (java.lang.String option : options2) {
      this.imageSaveLoad.addItem(option);
    }
    this.imageSaveLoad.setActionCommand("Image Action Options");
    this.imageSaveLoad.addActionListener(this);

    add(mainScrollPain);
    this.mainPanel.add(menuLabel);
    this.mainPanel.add(this.menu);
    this.mainPanel.add(removeImage);
    this.mainPanel.add(createImage);
    this.mainPanel.add(makeVisible);
    this.mainPanel.add(makeInvisible);
    this.mainPanel.add(makeCurrent);
    this.mainPanel.add(this.filterDropDown);
    this.mainPanel.add(this.imageSaveLoad);
    this.mainPanel.add(this.layerList);

    imageLabel = new JLabel();
    JScrollPane imageScrollPane = new JScrollPane(imageLabel);
    imageLabel.setLayout(new GridLayout(1, 0, 10, 10));
    Dimension maxImageSize = new Dimension(400, 400);

    ImageIcon icon = getImageIcon();
    imageLabel.setIcon(icon);
    imageScrollPane.setPreferredSize(maxImageSize);
    this.mainPanel.add(imageScrollPane);

    JPanel fileOpenPanel = new JPanel();
    fileOpenPanel.setLayout(new FlowLayout());
    this.mainPanel.add(fileOpenPanel);
    fileOpenPanel.add(imageSaveLoad);
    JLabel fileOpenDisplay = new JLabel("Load and save images here");
    fileOpenPanel.add(fileOpenDisplay);

    askForFocus();

    pack();
  }

  @Override
  public void askForFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  /**
   * Returns an image icon of the topmost visible image in the entire multi-image being processed.
   *
   * @return the topmost visible image as an image icon
   */
  protected ImageIcon getImageIcon() {
    try {
      IImage originalImage = model.getTopmostVisible();
      IImage newImage = new Image(originalImage.getPixels(),
          originalImage.getHeight(), originalImage.getWidth());
      if (newImage.getWidth() != -1 && newImage.getHeight() != 0) {
        BufferedImage displayImage = newImage.setColorValue(new BufferedImage(newImage.getWidth(),
            newImage.getHeight(), BufferedImage.TYPE_INT_RGB));
        return new ImageIcon(displayImage);
      }
      return new ImageIcon(new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB));
    } catch (IllegalStateException ise) {
      return new ImageIcon(new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB));
    }
  }

  /**
   * Gets a ListModel of strings with information about each layer of the multi-image.
   *
   * @return a ListModel of strings with information on the layers of images
   */
  protected DefaultListModel<String> getLayerInfo() {
    DefaultListModel<String> listOfString = new DefaultListModel<>();
    for (int i = 0; i < model.getLayers().size(); i++) {
      StringBuilder layerInfo = new StringBuilder();
      layerInfo.append("Layer: ");
      layerInfo.append((i + 1));
      if (model.getLayers().get(i).getVisibility()) {
        layerInfo.append(" Visible ");
      } else {
        layerInfo.append(" Invisible ");
      }
      if (i == model.getCurrentOrder().get(0)) {
        layerInfo.append("Current Image");
      }
      listOfString.addElement(layerInfo.toString());
    }
    return listOfString;
  }

  @Override
  public void registerViewEventListener(IViewListener listener) throws IllegalArgumentException {
    if (listener == null) {
      throw new IllegalArgumentException("Cannot have null listener!!");
    }
    this.viewListeners.add(listener);
  }

  /**
   * Performs the create command for each of the listeners taken in from the controller.
   */
  protected void emitCreateEvent() {
    for (IViewListener listener : this.viewListeners) {
      listener.handleCreateEvent();
    }
  }

  /**
   * Performs the load command for each of the listeners taken in from the controller.
   *
   * @param file the file to be loaded
   */
  protected void emitLoadEvent(String file) {
    if (file == null) {
      write("Load Event cancelled.");
    } else {
      for (IViewListener listener : this.viewListeners) {
        listener.handleLoadEvent(file);
      }
    }
  }

  /**
   * Performs the save command for each of the listeners taken in from the controller.
   *
   * @param filename the name of the image to be saved as
   */
  protected void emitSaveEvent(String filename) {
    if (filename == null) {
      write("Save Event cancelled.");
    }
    else {
      for (IViewListener listener : this.viewListeners) {
        listener.handleSaveEvent(filename);
      }
    }
  }

  /**
   * Performs the visible command for each of the listeners taken in from the controller.
   *
   * @param layerNum the number of the layer to have its visibility changed
   */
  protected void emitVisibleEvent(String layerNum) {
    if (layerNum == null) {
      write("Visible Event cancelled.");
    }
    else {
      for (IViewListener listener : this.viewListeners) {
        listener.handleVisibleEvent(layerNum);
      }
    }
  }

  /**
   * Performs the invisible command for each of the listeners taken in from the controller.
   *
   * @param layerNum the number of the layer to have its visibility changed
   */
  protected void emitInvisibleEvent(String layerNum) {
    if (layerNum == null) {
      write("Invisible Event cancelled.");
    }
    else {
      for (IViewListener listener : this.viewListeners) {
        listener.handleInvisibleEvent(layerNum);
      }
    }
  }

  /**
   * Performs the current command for each of the listeners taken in from the controller.
   *
   * @param layerNum the number of the layer to have its visibility changed
   */
  protected void emitCurrentEvent(String layerNum) {
    if (layerNum == null) {
      write("Current event canclled!");
    }
    else {
      for (IViewListener listener : this.viewListeners) {
        listener.handleCurrentEvent(layerNum);
      }
    }
  }

  /**
   * Performs the remove command for each of the listeners taken in from the controller.
   *
   * @param layerNum the number of the layer to be removed
   */
  protected void emitRemoveEvent(String layerNum) {
    if (layerNum == null) {
      write("Remove Event Cancelled.");
    }
    else {
      for (IViewListener listener : this.viewListeners) {
        listener.handleRemoveEvent(layerNum);
      }
    }
  }

  /**
   * Performs the blue command for each of the listeners taken in from the controller.
   */
  protected void emitBlurEvent() {
    for (IViewListener listener : this.viewListeners) {
      listener.handleBlurEvent();
    }
  }

  /**
   * Performs the sharpen command for each of the listeners taken in the from the controller.
   */
  protected void emitSharpenEvent() {
    for (IViewListener listener : this.viewListeners) {
      listener.handleSharpenEvent();
    }
  }

  /**
   * Performs the sepia command for each of the listeners taken in from the controller.
   */
  protected void emitSepiaEvent() {
    for (IViewListener listener : this.viewListeners) {
      listener.handleSepiaEvent();
    }
  }

  /**
   * Performs the greyscale command for each of the listeners taken in from the controller.
   */
  protected void emitGreyscaleEvent() {
    for (IViewListener listener : this.viewListeners) {
      listener.handleGreyscaleEvent();
    }
  }

  /**
   * Performs the save all command for each of the listeners taken in from the controller.
   *
   * @param filetype   the type for the files to be saved as
   * @param folderName the name of the folder to saved as
   */
  protected void emitSaveAllEvent(String filetype, String folderName) {
    if (filetype == null || folderName == null) {
      write("Save event cancelled!!");
    } else {
      for (IViewListener listener : this.viewListeners) {
        listener.handleSaveAllEvent(filetype, folderName);
      }
    }
  }

  /**
   * Performs the load multi-image command for all of the listeners taken in from the controller.
   *
   * @param filename the name of the file to be imported in
   */
  protected void emitLoadMultiImageEvent(String filename) {
    if (filename == null) {
      write("Load MultiImage Event cancelled.");
    } else {
      for (IViewListener listener : this.viewListeners) {
        listener.handleLoadMultiImageEvent(filename);
      }
    }
  }

  /**
   * Performs the create checkerboard command for all of the listeners taken in from the
   * controller.
   *
   * @param tileSize the length in pixels of a side of a tile
   * @param rows     the number of rows of tiles in the checkerboard
   * @param cols     the number of cols of tiles in the checkerboard
   */
  protected void emitCreateCheckerboard(String tileSize, String rows, String cols) {
    if (tileSize == null || rows == null || cols == null) {
      write("Create Checkerboard Event cancelled.");
    } else {
      for (IViewListener listener : this.viewListeners) {
        listener.handleCreateCheckerboard(tileSize, rows, cols);
      }
    }
  }

  /**
   * Downsizes every layer in the multi-image to the desired height and width.
   *
   * @param newWidth the new width of the multi-image
   * @param newHeight the new height of the multi-image
   */
  protected void emitDownScaleEvent(String newWidth, String newHeight) {
    if (newWidth == null || newHeight == null) {
      write("Downscale event cancelled!!");
    } else {
      for (IViewListener listener : this.viewListeners) {
        listener.handleDownScale(newWidth, newHeight);
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Image Filter Options":
        String applyFilter = (String) filterDropDown.getSelectedItem();
        filterApplied(applyFilter);
        break;
      case "Image Action Options":
        String applyAction = (String) imageSaveLoad.getSelectedItem();
        actionApplied(applyAction);
        break;
      case "Menu":
        String applyMenuItem = (String) this.menu.getSelectedItem();
        menuItemApplied(applyMenuItem);
        break;
      default:
        menuItemApplied(e.getActionCommand());
    }
    DefaultListModel<String> listOfLayers = new DefaultListModel<>();
    for (int i = 0; i < model.getLayers().size(); i++) {
      listOfLayers.addElement(getLayerInfo().getElementAt(i));
    }
    this.layerList.setModel(listOfLayers);

    ImageIcon icon = getImageIcon();
    this.imageLabel.setIcon(icon);

    this.askForFocus();
  }

  /**
   * Performs the command relating to saving and loading images associated with the inputted action
   * event.
   *
   * @param s the menu item being selected
   */
  protected void menuItemApplied(String s) {
    switch (s) {
      case "Create Layer":
        emitCreateEvent();
        break;
      case "Make Visible":
        String layerNum = JOptionPane.showInputDialog("Input desired layer number");
        emitVisibleEvent(layerNum);
        break;
      case "Remove Layer":
        String layerNum2 = JOptionPane.showInputDialog("Input desired layer number");
        emitRemoveEvent(layerNum2);
        break;
      case "Make Invisible":
        String layerNumInvisible = JOptionPane.showInputDialog("Input desired layer number");
        emitInvisibleEvent(layerNumInvisible);
        break;
      case "Set Current":
        String layerNumCurrent = JOptionPane.showInputDialog("Input desired layer number");
        emitCurrentEvent(layerNumCurrent);
        break;
      case "Down Size":
        String newWidth = JOptionPane.showInputDialog("Input new Width");
        String newHeight = JOptionPane.showInputDialog("Input new Height");
        emitDownScaleEvent(newWidth, newHeight);
        break;
      default:
        if (!actionApplied(s) && !filterApplied(s)) {
          try {
            renderMessage("Invalid Command");
          } catch (java.io.IOException io) {
            // should not get here
          }
        }
    }
  }

  /**
   * Performs the command relating to saving and loading images associated with the inputted action
   * event.
   *
   * @param s the action event case
   * @return boolean flag if the action was properly applied
   */
  protected boolean actionApplied(String s) {
    switch (s) {
      case "Save All":
        String folderName = JOptionPane.showInputDialog("Input desired folder name");
        String filetype = JOptionPane.showInputDialog("Input desired file type");
        emitSaveAllEvent(filetype, folderName);
        return true;
      case "Load MultiImage":
        final JFileChooser fchooser = new JFileChooser(".");
        int revalue = fchooser.showOpenDialog(MyWindow.this);
        if (revalue == JFileChooser.APPROVE_OPTION) {
          File f = fchooser.getSelectedFile();
          emitLoadMultiImageEvent(f.getAbsolutePath());
        }
        return true;
      case "Save Image":
        String filename = JOptionPane.showInputDialog("Input new filename");
        emitSaveEvent(filename);
        return true;
      case "Load Image":
        final JFileChooser fchooser2 = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "JPG, PNG, JPEG, PPM", "jpg", "jpeg", "png", "ppm");
        fchooser2.setFileFilter(filter);
        int revalue2 = fchooser2.showOpenDialog(MyWindow.this);
        if (revalue2 == JFileChooser.APPROVE_OPTION) {
          File f = fchooser2.getSelectedFile();
          emitLoadEvent(f.getAbsolutePath());
        }
        return true;
      case "Create Checkerboard":
        String tilesize = JOptionPane.showInputDialog("Input tilesize");
        String rows = JOptionPane.showInputDialog("Input number of rows");
        String columns = JOptionPane.showInputDialog("Input number of columns");
        emitCreateCheckerboard(tilesize, rows, columns);
        return true;
      default:
        return false;
    }
  }

  /**
   * Performs the command relating to applying filters to images associated with the inputted action
   * event.
   *
   * @param s the action event case
   * @return boolean flag if the filter was able to be applied
   */
  protected boolean filterApplied(String s) {
    switch (s) {
      case "Blur":
        emitBlurEvent();
        return true;
      case "Sharpen":
        emitSharpenEvent();
        return true;
      case "Sepia":
        emitSepiaEvent();
        return true;
      case "Greyscale":
        emitGreyscaleEvent();
        return true;
      default:
        return false;
    }
  }

  @Override
  public void renderMessage(String message) throws IOException, IllegalArgumentException {
    if (message == null) {
      throw new IllegalArgumentException("Cannot have a null message!!");
    }
    JOptionPane.showMessageDialog(mainPanel, message);
  }

  /**
   * Writes a message out to the console.
   *
   * @param message the message to be written
   * @throws IllegalArgumentException if the message is null, or if it cannot write to the console
   */
  private void write(String message) throws IllegalArgumentException {
    if (message == null) {
      throw new IllegalArgumentException("cannot have null message");
    }
    try {
      renderMessage(message);
    } catch (IOException io) {
      throw new IllegalArgumentException("Could not display message");
    }
  }
}
