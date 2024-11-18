# Java Minesweeper Game

A an implementation of the classic Minesweeper game built with Java Swing, featuring a clean and intuitive user interface.


## Features

- Two board sizes: 10x10 and 15x15
- Clean and modern UI with hover effects
- Game timer
- Mine counter
- Right-click flagging system
- First-click protection (first click is always safe)

## Requirements

- Java Development Kit (JDK) 8 or higher
- Java Runtime Environment (JRE)

## Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/minesweeper.git
```

2. Navigate to the project directory:
```bash
cd minesweeper
```

3. Compile the Java files:
```bash
javac *.java
```

4. Run the game:
```bash
java Minisweeper
```

## How to Play

1. **Left Click**: Reveal a cell
2. **Right Click**: Place/remove a flag
3. **Game Objective**: Find all mines without triggering any of them
4. Numbers indicate how many mines are adjacent to that cell
5. Use flags to mark potential mine locations
6. The first click is always safe

## Game Controls

- **New Game**: Start a new game at any time
- **Board Size**: Choose between 10x10 (10 mines) and 15x15 (20 mines)
- **Timer**: Track your solving time
- **Mine Counter**: Shows remaining unflagged mines

## Project Structure

- `Minisweeper.java`: Main game window and initialization
- `GameBoard.java`: Game board components and logic
- `Cell.java`: Individual cell implementation
- `GameTimer.java`: Game timer functionality
- `GameConstants.java`: Game constants and configuration

## Features Deep Dive

### Game Board
- Dynamic board sizing (10x10 or 15x15)
- Intelligent mine placement
- First-click protection
- Recursive cell revealing for empty cells

### User Interface
- Hover effects on cells
- Clear status indicators
- Intuitive controls
- Responsive design

### Game Logic
- Accurate mine counting
- Flag placement system
- Win/lose detection
- Game state management

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/YourFeature`)
3. Commit your changes (`git commit -m 'Add some feature'`)
4. Push to the branch (`git push origin feature/YourFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.
