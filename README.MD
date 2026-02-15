# Java 2D Game Engine

A lightweight, component-based 2D game engine built with pure Java and Swing.

## Features

- **Entity System** - Base classes for players, NPCs, and game objects
- **Physics Engine** - AABB collision detection with vector math
- **Tile System** - CSV-based tile maps with collision layers
- **Input Handling** - Keyboard input management
- **Audio System** - Sound effects and background music support
- **Dialogue System** - Text-based dialogue with typewriter effect
- **Sprite Management** - Sprite sheets and frame-based animations
- **UI Components** - Modern UI rendering utilities

## Structure

```
engine/
├── api/          # Core interfaces
├── audio/        # Sound management
├── core/         # Game loop, scenes, resource loading
├── dialogue/     # Dialogue system
├── entity/       # Base entity classes
├── graphics/     # Sprite handling
├── input/        # Keyboard input
├── physics/      # Collision detection
├── ui/           # UI components
├── utils/        # Helper utilities
└── world/        # Tile system
```

## Usage

Copy the `engine` package into your Java project and extend the base classes:

```java
public class MyGame extends GameEngine {
    @Override
    protected void initialize() {
        // Setup your game
    }
}
```

## Requirements

- Java 11+
- No external dependencies

## License

MIT
