package main.engine.dialogue;

import main.game.config.PlateformerConfig;

import java.awt.*;

/**
 * DialogueBox - Renders text boxes for NPC conversations.
 * 
 * Simple, clean design inspired by classic RPGs.
 */
public class DialogueBox {
    
    private String[] pages;
    private int currentPage = 0;
    private boolean active = false;
    private String speakerName = "";
    
    // Typewriter effect
    private String displayedText = "";
    private int charIndex = 0;
    private int typeSpeed = 2;  // Frames per character
    private int typeCounter = 0;
    private boolean textComplete = false;
    
    // Box dimensions
    private int boxX, boxY, boxWidth, boxHeight;
    
    // Colors
    private Color bgColor = new Color(20, 20, 30, 230);
    private Color borderColor = new Color(100, 100, 120);
    private Color textColor = new Color(240, 240, 250);
    private Color nameColor = new Color(255, 200, 100);
    private Color hintColor = new Color(150, 150, 160);
    
    public DialogueBox() {
        // Position at bottom of screen
        boxWidth = PlateformerConfig.SCREEN_WIDTH - 100;
        boxHeight = 120;
        boxX = 50;
        boxY = PlateformerConfig.SCREEN_HEIGHT - boxHeight - 30;
    }
    
    /**
     * Start a new main.engine.dialogue sequence.
     */
    public void startDialogue(String speaker, String[] pages) {
        this.speakerName = speaker;
        this.pages = pages;
        this.currentPage = 0;
        this.active = true;
        resetTypewriter();
    }
    
    /**
     * Advance to next page or close if done.
     */
    public void advance() {
        if (!active) return;
        
        if (!textComplete) {
            // Skip typewriter, show full text
            displayedText = pages[currentPage];
            textComplete = true;
        } else {
            // Go to next page
            currentPage++;
            if (currentPage >= pages.length) {
                close();
            } else {
                resetTypewriter();
            }
        }
    }
    
    private void resetTypewriter() {
        displayedText = "";
        charIndex = 0;
        typeCounter = 0;
        textComplete = false;
    }
    
    public void close() {
        active = false;
        pages = null;
        currentPage = 0;
    }
    
    public void update() {
        if (!active || textComplete) return;
        
        // Typewriter effect
        typeCounter++;
        if (typeCounter >= typeSpeed) {
            typeCounter = 0;
            if (charIndex < pages[currentPage].length()) {
                charIndex++;
                displayedText = pages[currentPage].substring(0, charIndex);
            } else {
                textComplete = true;
            }
        }
    }
    
    public void draw(Graphics2D g2d) {
        if (!active) return;
        
        // Draw shadow
        g2d.setColor(new Color(0, 0, 0, 100));
        g2d.fillRoundRect(boxX + 4, boxY + 4, boxWidth, boxHeight, 15, 15);
        
        // Draw box background
        g2d.setColor(bgColor);
        g2d.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 15, 15);
        
        // Draw border
        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRoundRect(boxX, boxY, boxWidth, boxHeight, 15, 15);
        
        // Draw speaker name
        if (!speakerName.isEmpty()) {
            g2d.setColor(nameColor);
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.drawString(speakerName, boxX + 20, boxY + 25);
        }
        
        // Draw text (support multi-line with \n)
        g2d.setColor(textColor);
        g2d.setFont(new Font("Arial", Font.PLAIN, 14));
        
        String[] lines = displayedText.split("\n");
        int lineY = boxY + 50;
        for (String line : lines) {
            g2d.drawString(line, boxX + 20, lineY);
            lineY += 20;
        }
        
        // Draw continue hint
        if (textComplete) {
            g2d.setColor(hintColor);
            g2d.setFont(new Font("Arial", Font.PLAIN, 11));
            String hint = currentPage < pages.length - 1 ? 
                "Press SPACE to continue..." : "Press SPACE to close";
            g2d.drawString(hint, boxX + boxWidth - 150, boxY + boxHeight - 10);
            
            // Blinking arrow
            if ((System.currentTimeMillis() / 500) % 2 == 0) {
                g2d.drawString("▼", boxX + boxWidth - 25, boxY + boxHeight - 10);
            }
        }
        
        // Page indicator
        g2d.setColor(hintColor);
        g2d.setFont(new Font("Arial", Font.PLAIN, 10));
        g2d.drawString((currentPage + 1) + "/" + pages.length, boxX + 20, boxY + boxHeight - 10);
    }
    
    public boolean isActive() { return active; }
}
