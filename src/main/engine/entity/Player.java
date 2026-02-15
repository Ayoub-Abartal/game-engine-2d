package main.engine.entity;

import main.engine.api.Drawable;
import main.engine.api.Updatable;
import main.engine.audio.SoundManager;
import main.game.elements.ElementType;
import main.game.config.PlateformerConfig;
import main.engine.graphics.SpriteSheet;
import main.engine.input.KeyHandler;
import main.engine.physics.CollisionContext;
import main.engine.physics.Vector2D;
import main.game.ui.ElementInventory;
import main.engine.world.CollisionChecker;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Player with gravity and jumping (platformer style).
 * 
 * PHYSICS:
 * - velocityY: vertical speed (positive = falling, negative = rising)
 * - gravity: pulls player down each frame
 * - jumpForce: initial upward velocity when jumping
 * - onGround: can only jump when touching ground
 */
public class Player implements Updatable, Drawable {
    
    // === POSITION & SIZE ===

    private Vector2D position;
    private int width, height;
    private int speed;
    
    // === PHYSICS (Platformer) ===
    private Vector2D velocity;
    private static final Vector2D GRAVITY = new Vector2D(0, 0.5f);   // Pulls down each frame
    private static final float JUMP_FORCE = -14f;   // Higher jump!
    private static final float MAX_FALL_SPEED = 15f; // Terminal velocity
    private boolean onGround = false;
    
    // === STATE ===
    private ElementType currentElement = ElementType.NONE;
    private Direction direction = Direction.RIGHT;
    private int frameCounter = 0;
    private int animationFrame = 0;
    private boolean isMoving = false;
    
    // === ANIMATION ===
    private static final int FRAMES_PER_ANIMATION = 3;
    private static final int ANIMATION_SPEED = 8;
    
    // === SPRITES ===
    private BufferedImage[][] walkFrames;

    // === REFERENCES ===
    private KeyHandler keyHandler;
    private CollisionChecker collisionChecker;
    private ElementInventory inventory;




    public enum Direction {
        UP(0), RIGHT(1), DOWN(2), LEFT(3);
        public final int row;
        Direction(int row) { this.row = row; }
    }
    
    public Player(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
        this.width = 48;
        this.height = 64;
        this.speed = PlateformerConfig.PLAYER_SPEED;
        this.velocity = Vector2D.zero();
        this.position = new Vector2D(
                PlateformerConfig.SCREEN_WIDTH / 2 - width / 2,
                PlateformerConfig.SCREEN_HEIGHT / 2 - height / 2
        );
        loadSprites();
    }
    
    private void loadSprites() {
        SpriteSheet sheet = new SpriteSheet("/sprites/dwarf_hero-m-001.png", 48, 64);
        walkFrames = new BufferedImage[4][FRAMES_PER_ANIMATION];
        walkFrames[Direction.UP.row] = sheet.getRow(0);
        walkFrames[Direction.RIGHT.row] = sheet.getRow(1);
        walkFrames[Direction.DOWN.row] = sheet.getRow(2);
        walkFrames[Direction.LEFT.row] = sheet.getRow(3);
        System.out.println("Player sprites loaded!");
    }

    @Override
    public void update(double delta) {
        // Element switching
        if (inventory != null) {
            if (keyHandler.key1Pressed) inventory.switchTo(ElementType.FIRE);
            if (keyHandler.key2Pressed) inventory.switchTo(ElementType.WATER);
            if (keyHandler.key3Pressed) inventory.switchTo(ElementType.EARTH);
            if (keyHandler.key4Pressed) inventory.switchTo(ElementType.AIR);
            currentElement = inventory.getCurrentElement();
        }
        
        // === HORIZONTAL MOVEMENT ===
        Vector2D moveDirection = Vector2D.zero();
        isMoving = false;
        
        if (keyHandler.leftPressed) {
            moveDirection.x = -1;
            direction = Direction.LEFT;
            isMoving = true;
        }

        if (keyHandler.rightPressed) {
            moveDirection.x = 1;
            direction = Direction.RIGHT;
            isMoving = true;
        }

        // Apply movement speed
        if (moveDirection.length() > 0) {
            moveDirection.normalize();
            moveDirection.multiply(speed);
            velocity.x = moveDirection.x;
        } else {
            velocity.x = 0;
        }

        // Test horizontal collision
        Vector2D horizontalMovement = new Vector2D(velocity.x, 0);
        Vector2D testPosition = position.copy();
        testPosition.add(horizontalMovement);

        if (canMoveTo(testPosition)) {
            position.add(horizontalMovement);  //
        } else {
            velocity.x = 0;  // Hit wall
        }

        // === JUMPING ===
        // Press W or UP to jump (only when on ground)
        if ((keyHandler.upPressed || keyHandler.spacePressed) && onGround) {
            velocity.y = JUMP_FORCE;
            onGround = false;
            // Play jump sound
            try {
                SoundManager.play("jump");
            } catch (Exception e) {}
        }
        
        // === GRAVITY ===
        velocity.add(GRAVITY);
        
        // Cap fall speed
        if (velocity.y > MAX_FALL_SPEED) {
            velocity.y = MAX_FALL_SPEED;
        }

        // === VERTICAL MOVEMENT ===
        Vector2D verticalMovement = new Vector2D(0, velocity.y);
        testPosition = position.copy();
        testPosition.add(verticalMovement);

        if (velocity.y > 0) {
            // Falling
            if (canMoveTo(testPosition)) {
                position.add(verticalMovement);  // ← Using add()!
                onGround = false;
            } else {
                // Hit ground - find exact landing
                while (canMoveTo(new Vector2D(position.x, position.y + 1))) {
                    position.y++;
                }
                velocity.y = 0;
                onGround = true;
            }
        } else if (velocity.y < 0) {
            // Rising
            if (canMoveTo(testPosition)) {
                position.add(verticalMovement);  // ← Using add()!
            } else {
                velocity.y = 0;  // Hit ceiling
            }
        }

        // === ANIMATION ===
        if (isMoving || !onGround) {
            frameCounter++;
            if (frameCounter % ANIMATION_SPEED == 0) {
                animationFrame = (animationFrame + 1) % FRAMES_PER_ANIMATION;
            }
        } else {
            frameCounter = 0;
            animationFrame = 0;
        }
        
        // Keep on screen horizontally only (can fall off bottom to die)
        position.x = Math.max(0, Math.min(position.x, PlateformerConfig.SCREEN_WIDTH - width));
        // y is NOT clamped - player can fall below screen and die
    }
    
    /**
     * Check if player can move to position (no solid tiles, doors, or bridges).
     */
    private boolean canMoveTo(Vector2D position) {
        // Check tile collision && collidables
        return  collisionChecker != null &&
                collisionChecker.canMove(position, width, height,new CollisionContext(getCurrentElement()) );

    }

    @Override
    public void draw(Graphics2D g2d) {
        BufferedImage currentFrame = walkFrames[direction.row][animationFrame];
        drawGlow(g2d);
        g2d.drawImage(currentFrame, (int) position.x, (int) position.y, width, height, null);
        
        // Debug: show if on ground
        // g2d.setColor(onGround ? Color.GREEN : Color.RED);
        // g2d.fillRect(x, y - 5, 10, 5);
    }
    
    private void drawGlow(Graphics2D g2d) {
        if (currentElement == ElementType.NONE) return;
        Color color = currentElement.getGlowColor();
        int baseAlpha = isMoving ? (int)(Math.sin(frameCounter / 12.0) * 40 + 80) : 60;
        for (int i = 3; i > 0; i--) {
            int alpha = Math.min((baseAlpha / i) / 2, 100);
            g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha));
            int offset = i * 8;
            g2d.fillOval((int) (position.x - offset), (int) (position.y - offset) , width + offset * 2, height + offset * 2);
        }
    }
    
    // Getters
    public int getX() { return (int) position.x; }
    public int getY() { return (int) position.y; }
    public Vector2D getPosition() { return position; }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public ElementType getCurrentElement() { return currentElement; }
    public boolean isOnGround() { return onGround; }
    
    // Setters
    public void setElement(ElementType element) { this.currentElement = element; }
    public void setCollisionChecker(CollisionChecker cc) { this.collisionChecker = cc; }
    public void setInventory(ElementInventory inv) { this.inventory = inv; }
    public void setPosition(int x, int y) { position.set(x,y); }
}
