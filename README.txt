README:
In order to use this program, simply run the program from the main method in the image util class and this will run
the GUI controller. If you are running from the JAR file, you will have the option to either input “script”,
“text”, or “interactive” as a command line argument as explained in the assignment to
choose how to interact with the program. Inputting “-script filepath.txt” will allow you to run a script of your choice
through the program and the program will perform the commands written into the script. Inputting “-text” will
allow you to enter text based commands into the program in order to create and manipulate multi-images. Inputting
“-interactive” will open up a graphical user interface which will allow you to create and manipulate multi-images.

All parts of this program required in the assignment are complete. The GUI is completed with the option to make
commands through using one drop down menu with all commands, or through an organized set of smaller
drop down menus and buttons. While creating and loading in images, information on the layers you have created,
their visibility, and which layer is set as current will show up. The topmost visible image will also appear in a
box in the center for you to see what you are working on and will update as you change it by changing visibilities or
creating filters.

Design Changes:
- We decided to enable the user to decide which controller to use through initial input, allowing maximum flexibility
  between controllers and adding new controllers and functionality.
- We split the old IMultiImageProcessingModel interface into a IModelView interface and an IMultiImageProcessingModel
  interface in order to give the view access to the getters which do not mutate the model in order to allow the view to
  create the GUI properly with data from the model while restricting and preventing its ability to cause any problems
  with changing data.
- We created a MyWindow view class to represent the graphical user interface
- In this class we add on all of the aspects of the GUI with the menus, images, informations on layers, and buttons
- This class also serves to watch for the action events, with it extending the ActionListener. We made this decision
  because it enables the view to receive the actions and relay them to the Controller, which implements IViewListener,
  which then calls on the command classes we implemented earlier to perform the actions on the model.
- This prevents over coupling between the view and model, as the view only has access to a read-only version of the
  model, preventing mutation. The controller just updates the model according to the actions relayed by the view.
  The view is then updated through the read-only model. This is a design taught from lecture.

Extra Credit Design Changes
- To create the downscaling operation we made a new model interface, IMultiImageModelDownScaleSupport
  which extended our previous model interface IMultiImageProcessingModel, and added on the new method for image
  downscaling. We then made a new class, MultiImageDownScaleSupport which extended the old model
  MultiImageProcessingModel, and implemented the new interface in order to keep all previous functionality while
  adding the new image downscaling method.
- We also made a new command interface IImageProcessingCommandDownScaleModel, that extends the old command interface
  IImageProcessingCommand, with the new interface supporting a new operate method with the new model in it.
  This allowed the addition of the new command while supporting all previous commands without having to alter all
  previous command classes.
- This design also made it such that the old command interface IImageProcessingCommand and the old model
  IMutliImageProcessingModel do not support this new downscaling operation, properly segregating the old and new
  functionality.

Bee photo obtained from: https://pixabay.com/photos/bee-white-flowers-pollen-pollinate-6291207/
Image by ProsaClouds from Pixabay
