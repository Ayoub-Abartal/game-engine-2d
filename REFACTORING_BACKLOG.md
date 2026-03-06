# Technical Debt & Architecture Roadmap

Framework evolution tracking for production readiness and multi-genre support.

## Phase 1: Core Stability

### Completed
- [x] **Logging Infrastructure** - java.util.logging facade with file rotation
- [x] **Physics Extraction** - PlatformerPhysics with single-responsibility methods
- [x] **Interface Segregation** - Positioned, Updatable, Drawable, Interactable, Collidable

### In Progress
- [ ] **Core Architecture Refactor**
  - Extract GameWindow from GameEngine
  - Implement SceneManager.
  - Refactor GameEngine → GameApplication (Template Method)
  - Fix GameLoop thread safety (proper pause/resume)

### Pending
- [ ] **Test Coverage** - PlatformerPhysics, AABB, Vector2D, collision resolution
- [ ] **Error Handling** - Custom exceptions, graceful resource loading failures
- [ ] **API Documentation** - Complete JavaDoc coverage for public APIs

## Phase 2: Framework Maturity

### Dependency Management
- [ ] **SoundManager** - Remove static singleton, inject AudioSystem interface
- [x] **NPC Extraction** - Move game-specific NPC to game code, keep Entity in engine
- [ ] **DialogueSystem** - Interface-based dialogue with multiple implementations

### Stateless Design
- [ ] **PhysicsState** - Extract mutable state from PlatformerPhysics
- [ ] **Interactable Helpers** - Remove logic from interface.

## Phase 3: Multi-Genre Support

### Grid-Based Games (Candy Crush, Match-3)
- [ ] **GridManager** - Tile grid with swap, match detection, gravity
- [ ] **GridPhysics** - Snap-to-grid movement, match validation

### Top-Down Games (RPG, Roguelike)
- [ ] **TopDownPhysics** - 8-direction movement, no gravity
- [ ] **Pathfinding** - A* for NPC navigation

### Advanced Platformer
- [ ] **State Machine** - Player states (idle, run, jump, fall, dash, wall-slide)
- [ ] **Advanced Movement** - Double jump, variable jump height, coyote time

## Phase 4: Performance & Scalability

### Optimization (When Needed)
- [ ] **Entity Component System** - Data-oriented design for 500+ entities
- [ ] **Object Pooling** - Reduce GC pressure for particles, projectiles
- [ ] **Spatial Partitioning** - Quadtree for collision optimization

### Observability
- [ ] **Performance Profiling** - FPS counter, frame time graph, memory usage
- [ ] **Event System** - Decouple game logic with EventBus

## Phase 5: Tooling & Polish

- [ ] **Serialization** - Save/load game state (JSON)
- [ ] **Asset Pipeline** - Hot-reload resources during development
- [ ] **Debug Renderer** - Collision boxes, velocity vectors, grid overlay

## Design Principles

**Opinionated Structure:**
- Scene-based architecture (mandatory)
- Entity base class with interface composition
- Framework controls lifecycle (Template Method)

**Flexible Implementation:**
- Genre-specific physics modules (optional)
- Interface-based systems (swappable)
- Composition over inheritance where practical

**Pragmatic Evolution:**
- Refactor based on actual pain points
- Avoiding premature optimization (no ECS until 500+ entities)
- Maintaining backward compatibility within major versions

## Architecture Decisions

**Framework :** Framework (inversion of control via GameApplication)

**Entity Model:** OOP with interface segregation.

**Scene Management:** State pattern with future support for scene stack

**Physics:** Modular genre-specific implementations (PlatformerPhysics, GridPhysics, TopDownPhysics)

**Rendering:** Swing-based (acceptable for 2D, cross-platform via Java)
