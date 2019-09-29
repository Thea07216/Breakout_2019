Please do not change the directory structure.
1. At first, when user do gradle run with passing two arguments(FPS AND SPEED)e.g  gradle run --args="30 2", 30 for FPS and 2 for ball speed, you can also give different ball speed and FPS . Then program will pop up a splash screen(which contains my name and student ID, says press Yes to start.Then Breakout Game start with 5 rows bricks, 
the user can either move the mouse or press the button to move the paddle.

MVC pattern, I store all ball, paddle, bricks and score value in the Model class, and Controller is embedded in the View Class by using actionListener, and once the event occurs, actionListen will call model method to update the state, then inside the model it will notify its Observer and repaint the view.

Additional Feature, I have special bricks color black, once it get collision with the ball, the paddle can be double width as a bonus, and after three times of paddle, paddle goes back to original width.

Resize Supported.
