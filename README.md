# BrickDestroy

Fathan Sudardjat <br> 
20210111 <br>
Software Maintenance COMP2042

These are the changes that have been made to the original code:

- Restructured package to maven file structure
    - The source package was originally called test. This made differentiating between the source code and the test files difficult.
    - Restructuring allows maven to read the project files.
  

- Code refactoring has been done. This includes:
    - Achieving encapsulation by creating setters and getters for private variables.
    - De-cluttering large methods by extracting parts of methods into smaller ones.
      - For example, setGameTimer() was extracted from GameController constructor in order to keep the constructor short.
    - Repeated lines of code have been turned into their own methods for efficiency.
      - For example, there were 3 instances of similar lines of code in the Ball constructor, move(), and moveTo().
        - One method has been extracted from these repeated lines and these
    - Redundant lines of code has been removed
      - 2 lines of code in the ball constructor that set the speed was replaced by an existing method setSpeed()
      - Name parameter in brick has been removed since it is unused
  - Classes have been broken down in order to achieve single responsibility
      - The Crack class, which was initially a nested class inside of Brick, has been separated into its own class file.
      - The Levels class has been separated from Wall.
      - GameBoard has been split up into two smaller classes - GameView and GameController.
  - MVC was attempted through the splitting up of GameBoard into GameView and GameController. Files have been moved to respective Model, View, and Controller packages.
  

- Junit tests have been made for Model classes. This includes tests for Ball, Brick, Player, and Wall.
The tests are made for public methods of these classes and test the functionality of these classes.
  
- Maven support has been added. A pom file was generated that uses the jar plugin in order to generate a runnable jar file. 
Junit 5.4.2 dependency is stated in the pom file.
  

- Additions to the base game have been made. These include:
  - Added a visible game timer that decreases every second and ends the game when it reaches 0.
  - Added a visible score counter that increases depending on what brick is broken. 
  - Added an info screen that can be accessed by the home menu, showing user controls.
  - Added a fourth, playable level.
  - Changed the colors of the bricks, ball, player, and background.  
  - Changing steel brick to diamond brick as steel brick was too similarly colored to cement brick.
    


