CS 257: Software Design

Final Project: Jadrian Runner

Group Members:

* Alex Griese

* Ben Withbroe

* Greg Erlandson

* Tore Banta

Running the Game:

- Compile the .java files in the /src directory
- Run Main from the command line, execute "java Main"

If you run into difficulties running the game,
change the lines 136 and 157 in View.java, which say "View.class.getResource("Menu.fxml")"
to read the .fxml file from an absolute path:
View.class.getResource("/resources/Menu.fxml")