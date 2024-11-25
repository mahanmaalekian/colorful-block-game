# Colorful Block Game

## Overview

This project is a visual game where players manipulate a recursive quad-tree game board to achieve specific goals. The board is made of blocks, which can be rotated, reflected, or smashed into smaller sub-blocks. The objective is to score the most points by either forming the largest connected "blob" of a given color or placing as much of a given color as possible along the board's perimeter.

The game board resembles a Mondrian painting and offers a mix of strategy and randomness. It is implemented in Java and includes a graphical interface for gameplay.

![image](https://github.com/user-attachments/assets/ca6521d4-0550-400e-af4b-ea42966ae98e)


## Gameplay

- **Game Board**: Represented as a quad-tree structure. Each block is either a single-colored square or subdivided into four smaller blocks.
- **Player Goals**:
  - **Blob Goal**: Form the largest connected group of blocks of a specific color.
  - **Perimeter Goal**: Place the most blocks of a specific color on the board's outer edges.
- **Available Moves**:
  - **Rotate**: Turn a block clockwise or counterclockwise.
  - **Reflect**: Flip a block horizontally or vertically.
  - **Smash**: Subdivide a block into four smaller blocks (if allowed).

The player with the highest score at the end of the game wins.

## Setup

### Prerequisites
- **Java Development Kit (JDK)**: Ensure you have JDK 8 or higher installed.
- **Integrated Development Environment (IDE)**: Recommended for running and debugging the code (e.g., IntelliJ IDEA or Eclipse).

### Installation
1. Clone the repository

2. Open the project in your IDE:

- Ensure the src folder is marked as the source root.
- Verify that the correct JDK is configured for the project.

3. Compile and run the game:
- Navigate to the BlockGame.java file.
- Run the main method to start the game.

### Playing the Game
- Select a maximum depth for the board and start playing.
- Use the GUI to make moves (rotate, reflect, smash) and track your score.
- Enjoy experimenting with different strategies to achieve your assigned goal.
