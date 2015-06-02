CS 257: Software Design

Final Project: Jadrian Runner

Group Members:

* Alex Griese

* Ben Withbroe

* Greg Erlandson

* Tore Banta

Running the Game:

- Compile the .java files
- Locate the class file Main (usually in 'out')
- Run Main from the command line "java Main"

If you run into difficulties running the game,
change the line "View.class.getResource("Menu.fxml")" in Main.java
to read the .fxml file from an absolute path:
View.class.getResource("/resources/Menu.fxml")