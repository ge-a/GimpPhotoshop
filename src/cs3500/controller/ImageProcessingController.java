package cs3500.controller;

import cs3500.view.IImageProcessingView;
import cs3500.model.IMultiImageProcessingModel;
import cs3500.view.ImageProcessingView;
import cs3500.model.MultiImageProcessingModel;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

/**
 * The controller of the image processing program which allows users to interact with
 * the model, calling commands which allows them to manipulate a multilayered image.
 */
public class ImageProcessingController implements IImageProcessingController {

  private final Readable in;
  Map<String, Function<Scanner, IImageProcessingCommand>> commands;
  private final IMultiImageProcessingModel model;
  private final IImageProcessingView view;

  /**
   * Constructs a ImageProcessingController object.
   * Puts the supported commands into a HashMap mapping the string command
   * key to the command object creating the desired effect of the command.
   *
   * @param model the model of the game
   * @param in the input of the game
   * @param view the view of the controller
   * @throws IllegalArgumentException if any of the inputted arguments are null
   */
  public ImageProcessingController(IMultiImageProcessingModel model,
      Readable in, IImageProcessingView view) throws IllegalArgumentException {
    if (in == null || view == null || model == null) {
      throw new IllegalArgumentException("Cannot have null Readable or Appendable");
    }
    this.model = model;
    this.in = in;
    this.view = view;
    this.commands = new HashMap<String, Function<Scanner, IImageProcessingCommand>>();
    this.commands.put("create", s -> new CommandAddLayer());
    this.commands.put("blur", s -> new CommandBlur());
    this.commands.put("loadcheckerboard", s -> new CommandCreateNewCheckerboard(s.next(),
        s.next(), s.next()));
    this.commands.put("greyscale", s -> new CommandGreyscale());
    this.commands.put("import", s -> new CommandImportMultiImage(s.next()));
    this.commands.put("load", s -> new CommandLoadImage(s.next()));
    this.commands.put("invisible", s -> new CommandMakeInvisible(s.next()));
    this.commands.put("current", s -> new CommandMakeLayerCurrent(s.next()));
    this.commands.put("visible", s -> new CommandMakeVisible(s.next()));
    this.commands.put("remove", s -> new CommandRemoveLayer(s.next()));
    this.commands.put("saveall", s -> new CommandSaveAllLayers(s.next(), s.next()));
    this.commands.put("save", s -> new CommandSaveImage(s.next()));
    this.commands.put("sepia", s -> new CommandSepia());
    this.commands.put("sharpen", s -> new CommandSharpen());
    this.commands.put("script", s -> new CommandScriptCommand(s.next()));
  }

  /**
   * Constructs an ImageProcessingController object.
   * Convenience constructor that automatically sets the model to a
   * MultiImageProcessingModel.
   * Puts the supported commands into a HashMap mapping the string command
   * key to the command class creating the desired effect of the command.
   *
   * @param in the input of the game
   * @param out the output of the game
   * @throws IllegalArgumentException if either of the inputted arguments are null
   */
  public ImageProcessingController(Readable in, Appendable out) throws IllegalArgumentException {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Cannot have null Readable or Appendable");
    }
    this.in = in;
    this.model = new MultiImageProcessingModel();
    this.view = new ImageProcessingView(out);
    this.commands = new HashMap<String, Function<Scanner, IImageProcessingCommand>>();
    this.commands.put("create", s -> new CommandAddLayer());
    this.commands.put("blur", s -> new CommandBlur());
    this.commands.put("loadcheckerboard", s -> new CommandCreateNewCheckerboard(s.next(),
        s.next(), s.next()));
    this.commands.put("greyscale", s -> new CommandGreyscale());
    this.commands.put("import", s -> new CommandImportMultiImage(s.next()));
    this.commands.put("load", s -> new CommandLoadImage(s.next()));
    this.commands.put("invisible", s -> new CommandMakeInvisible(s.next()));
    this.commands.put("current", s -> new CommandMakeLayerCurrent(s.next()));
    this.commands.put("visible", s -> new CommandMakeVisible(s.next()));
    this.commands.put("remove", s -> new CommandRemoveLayer(s.next()));
    this.commands.put("saveall", s -> new CommandSaveAllLayers(s.next(), s.next()));
    this.commands.put("save", s -> new CommandSaveImage(s.next()));
    this.commands.put("sepia", s -> new CommandSepia());
    this.commands.put("sharpen", s -> new CommandSharpen());
    this.commands.put("script", s -> new CommandScriptCommand(s.next()));
  }

  @Override
  public void runPhotoshop() {
    IImageProcessingCommand c;
    Scanner scanner = new Scanner(in);
    while (scanner.hasNext()) {
      String input = scanner.next().toLowerCase();
      if (input.equals("q") || input.equals("quit")) {
        return;
      }
      Function<Scanner, IImageProcessingCommand> cmd =
          this.commands.getOrDefault(input, null);
      if (cmd == null) {
        makeMessage("this command does not exist!");
      } else {
        try {
          c = cmd.apply(scanner);
          c.operate(this.model);
          if (input.equals("create")) {
            makeMessage("Created layer number " + model.getLayers().size() + "\n");
          }
          if (input.equals("script")) {
            StringBuilder updateMessage = new StringBuilder();
            for (int i = 0; i < model.getLayers().size(); i++) {
              updateMessage.append("Layer ").append(i + 1);
              if (model.getLayers().get(i).getVisibility()) {
                updateMessage.append(" Visible \n");
              }
              else {
                updateMessage.append(" Invisible \n");
              }
            }
            int currentLayerNumber = 0;
            if (model.getCurrentOrder().size() > 0) {
              currentLayerNumber = model.getCurrentOrder().get(0) + 1;
            }
            makeMessage("\nScript done running. \nUpdated Layer Amount:" + model.getLayers().size()
                + "\nCurrent Layer Number:" + currentLayerNumber + "\n" + updateMessage);

          }
        } catch (IllegalArgumentException | IllegalStateException ia) {
          makeMessage(ia.getMessage());
        }
      }
    }
  }

  /**
   * Relays a string message to be displayed in the view class.
   * @param message the string message to be relayed to the view
   * @throws IllegalStateException when writing to the appendable fails
   */
  private void makeMessage(String message) throws IllegalStateException {
    try {
      view.renderMessage(message);
    } catch (IOException e) {
      throw new IllegalStateException("Could not write to the appendable");
    }
  }
}
