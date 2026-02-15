package main.engine.entity;

import main.engine.api.Drawable;
import main.engine.api.Interactable;
import main.engine.api.Updatable;
import main.engine.dialogue.DialogueBox;
import main.engine.graphics.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * NPC - Non-Player Character base class
 * 
 * NPCs can be interacted with and have main.engine.dialogue.
 */
public class NPC extends Entity implements Interactable, Drawable, Updatable {
    
    protected String name;
    protected String[] dialogue;
    protected boolean canInteract = true;
    protected boolean hasInteracted = false;
    
    // Animation
    protected BufferedImage[] idleFrames;
    protected int animFrame = 0;
    protected int animCounter = 0;
    protected int animSpeed = 15;

    protected int interactedRange=60;
    // Interaction indicator
    protected boolean showIndicator = false;

    protected DialogueBox dialogueBox;
    
    public NPC(String name, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.name = name;
    }
    
    public void loadSprite(String path, int frameWidth, int frameHeight) {
        SpriteSheet sheet = new SpriteSheet(path, frameWidth, frameHeight);
        idleFrames = sheet.getRow(2);
    }
    

    @Override
    public void draw(Graphics2D g2d) {
        if (idleFrames != null && idleFrames.length > 0) {
            g2d.drawImage(idleFrames[animFrame], x, y, width, height, null);
        } else {
            g2d.setColor(Color.MAGENTA);
            g2d.fillRect(x, y, width, height);
        }
        
        // Draw interaction indicator if player is near
        if (isFocused() && isInteractable()) {
            drawInteractionIndicator(g2d);
        }
    }

    public void interact() {
        hasInteracted = true;
        dialogueBox.startDialogue(name,dialogue);
    }

    @Override
    public boolean isInteractable() {
        return this.canInteract;
    }

    public String getName() { return name; }

    public void setDialogue(String[] dialogue) { this.dialogue = dialogue; }

    public String[] getDialogue(){
        return dialogue;
    }

    public void setShowIndicator(boolean b){
        this.showIndicator = b;
    }
    @Override
    public int getInteractRange() {
        return this.interactedRange;
    }

    @Override
    public void setFocused(boolean focused) {
        this.showIndicator = focused;
    }

    @Override
    public boolean isFocused() {
        return this.showIndicator;
    }

    @Override
    public boolean pauseGameOnInteract(){
        return true;
    }
    public void setDialogueBox(DialogueBox dialogueBox){
        this.dialogueBox = dialogueBox;
    }


    @Override
    public void update(double deltaTime) {
        // Animate
        animCounter++;
        if (animCounter >= animSpeed) {
            animCounter = 0;
            if (idleFrames != null && idleFrames.length > 0) {
                animFrame = (animFrame + 1) % idleFrames.length;
            }
        }
    }
}
