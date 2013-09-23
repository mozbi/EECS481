EECS481 Homework 2
==================

Game Description:
Matching Squares is a 4x4 memory and trial game. When the game starts, a 4x4
grid of squares will be shown on the screen with squares facing down. Squares
that are facing down are labelled as question marks. When a player clicks
on a square, the square flips and shows a picture on its flip side. The goal of
the game is to match all the pictures to their corresponding pairs. For example,
if the first square I click is an apple, the next square I click should also be
an apple for me to progress in the game. Once a pair of squares have been
clicked in succession, they disappear from the grid; and once all the squares
have been matched, the player wins. In this game, players can keep track of the
number of attempts or clicks they have made by looking at the "tries" variable
on the top right corner of the screen. Once a player successfully completes the
game, the game announces that the player has won.

Git Repository:
https://github.com/mozbi/EECS481 or
git@github.com:mozbi/eecs481

Build Instructions:
To run the code, follow these steps:
1. Import the project to Eclipse.

2. Turn on the Android Emulator for a device that runs on the Android 4.3 OS,
API level 18.

3. Hit the run button and select "matchingsquares". Once the project finishes
building, the game will show up on the emulator screen and be available for
play.

Code Locations:
1. Directory containing java source files: src/homework2/eecs481/matchingsquares 
2. Directory containing pictures used for squares: res/drawable-mdpi
3. Directory containing layout xml file: res/layout
