package main.engine.ui;

import main.game.elements.ElementType;
import main.game.config.PlateformerConfig;
import main.game.ui.ElementInventory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * ModernUI - Beautiful, modern UI for the game.
 * 
 * Features:
 * - Sleek element inventory display
 * - Smooth animations
 * - Modern design with rounded corners and shadows
 */
public class ModernUI {
    
    private ElementInventory inventory;
    private int animCounter = 0;
    private BufferedImage stoneImage;
    
    // UI Colors
    private static final Color BG_DARK = new Color(20, 20, 30, 200);
    private static final Color BG_LIGHT = new Color(40, 40, 50, 180);
    private static final Color TEXT_COLOR = new Color(240, 240, 250);
    private static final Color LOCKED_COLOR = new Color(60, 60, 70, 150);
    
    public ModernUI(ElementInventory inventory) {
        this.inventory = inventory;
        loadStoneImage();
    }
    
    private void loadStoneImage() {
        try {
            stoneImage = ImageIO.read(getClass().getResourceAsStream("/shrines/stone1.png"));
            System.out.println("Stone image loaded!");
        } catch (IOException e) {
            System.err.println("Failed to load stone image: " + e.getMessage());
        }
    }
    
    public void update() {
        animCounter++;
    }
    
    /**
     * Draw the complete UI.
     */
    public void draw(Graphics2D g2d) {
        drawElementInventory(g2d);
        drawEmptyStone(g2d);
        drawInstructions(g2d);
        drawProgress(g2d);
    }
    
    /**
     * Draw Empty Stone indicator (given by Elder).
     */
    private void drawEmptyStone(Graphics2D g2d) {
        if (!inventory.hasEmptyStone()) return;
        
        int x = 20;
        int y = 90;
        int width = 200;
        int height = 60;
        
        // Background
        g2d.setColor(new Color(30, 25, 40, 220));
        g2d.fillRoundRect(x, y, width, height, 12, 12);
        
        // Border with subtle glow
        int pulse = (int)(Math.sin(animCounter / 20.0) * 30 + 80);
        g2d.setColor(new Color(180, 150, 255, pulse));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRoundRect(x, y, width, height, 12, 12);
        
        // Stone image
        if (stoneImage != null) {
            int imgSize = 48;
            int imgX = x + 8;
            int imgY = y + (height - imgSize) / 2;
            g2d.drawImage(stoneImage, imgX, imgY, imgSize, imgSize, null);
        } else {
            // Fallback crystal icon if image fails to load
            int crystalX = x + 16;
            int crystalY = y + 12;
            g2d.setColor(new Color(200, 180, 255, 200));
            int[] xPoints = {crystalX + 8, crystalX + 16, crystalX + 8, crystalX};
            int[] yPoints = {crystalY, crystalY + 12, crystalY + 24, crystalY + 12};
            g2d.fillPolygon(xPoints, yPoints, 4);
        }
        
        // Text - LARGER FONTS
        int textX = x + 65;
        g2d.setColor(new Color(220, 200, 255));
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.drawString("Empty Stone", textX, y + 24);
        g2d.setFont(new Font("Arial", Font.PLAIN, 14));
        g2d.setColor(new Color(180, 160, 200));
        g2d.drawString("Absorbs elements", textX, y + 44);
    }
    
    /**
     * Draw element inventory - shows collected elements.
     */
    private void drawElementInventory(Graphics2D g2d) {
        int startX = 20;
        int startY = 20;
        int slotSize = 50;
        int spacing = 10;
        int padding = 12;
        
        // Background panel
        int panelWidth = (slotSize + spacing) * 4 + padding * 2 - spacing;
        int panelHeight = slotSize + padding * 2;
        
        // Draw shadow
        g2d.setColor(new Color(0, 0, 0, 80));
        g2d.fillRoundRect(startX + 3, startY + 3, panelWidth, panelHeight, 15, 15);
        
        // Draw panel background
        g2d.setColor(BG_DARK);
        g2d.fillRoundRect(startX, startY, panelWidth, panelHeight, 15, 15);
        
        // Draw border
        g2d.setColor(new Color(100, 100, 120, 100));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(startX, startY, panelWidth, panelHeight, 15, 15);
        
        // Draw element slots
        ElementType[] elements = {ElementType.FIRE, ElementType.WATER, ElementType.EARTH, ElementType.AIR};
        String[] keys = {"1", "2", "3", "4"};
        
        for (int i = 0; i < elements.length; i++) {
            int slotX = startX + padding + i * (slotSize + spacing);
            int slotY = startY + padding;
            
            drawElementSlot(g2d, slotX, slotY, slotSize, elements[i], keys[i]);
        }
    }
    
    /**
     * Draw a single element slot.
     */
    private void drawElementSlot(Graphics2D g2d, int x, int y, int size, ElementType element, String key) {
        boolean collected = inventory.hasElement(element);
        boolean active = inventory.getCurrentElement() == element;
        
        // Slot background
        if (collected) {
            if (active) {
                // Active slot - pulsing glow
                int pulse = (int)(Math.sin(animCounter / 10.0) * 10 + 15);
                Color glowColor = element.getGlowColor();
                g2d.setColor(new Color(glowColor.getRed(), glowColor.getGreen(), glowColor.getBlue(), pulse));
                g2d.fillRoundRect(x - 5, y - 5, size + 10, size + 10, 12, 12);
            }
            g2d.setColor(BG_LIGHT);
        } else {
            // Locked slot
            g2d.setColor(LOCKED_COLOR);
        }
        g2d.fillRoundRect(x, y, size, size, 10, 10);
        
        // Slot border
        if (active) {
            g2d.setColor(element.getGlowColor());
            g2d.setStroke(new BasicStroke(3));
        } else {
            g2d.setColor(new Color(80, 80, 90, 150));
            g2d.setStroke(new BasicStroke(2));
        }
        g2d.drawRoundRect(x, y, size, size, 10, 10);
        
        // Element icon/color
        if (collected) {
            Color color = element.getGlowColor();
            
            // Glowing orb in center
            int orbSize = 24;
            int orbX = x + (size - orbSize) / 2;
            int orbY = y + (size - orbSize) / 2;
            
            // Glow effect
            for (int i = 2; i >= 0; i--) {
                int alpha = 30 - (i * 10);
                g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha));
                int offset = i * 4;
                g2d.fillOval(orbX - offset, orbY - offset, orbSize + offset * 2, orbSize + offset * 2);
            }
            
            // Orb
            g2d.setColor(color);
            g2d.fillOval(orbX, orbY, orbSize, orbSize);
            
            // Shine
            g2d.setColor(new Color(255, 255, 255, 150));
            g2d.fillOval(orbX + 6, orbY + 4, 8, 8);
        } else {
            // Locked icon
            g2d.setColor(new Color(100, 100, 110));
            g2d.setFont(new Font("Arial", Font.BOLD, 24));
            FontMetrics fm = g2d.getFontMetrics();
            String lockIcon = "X";
            int textX = x + (size - fm.stringWidth(lockIcon)) / 2;
            int textY = y + (size + fm.getAscent()) / 2 - 2;
            g2d.drawString(lockIcon, textX, textY);
        }
        
        // Key hint
        g2d.setColor(collected ? TEXT_COLOR : new Color(100, 100, 110));
        g2d.setFont(new Font("Arial", Font.BOLD, 10));
        FontMetrics fm = g2d.getFontMetrics();
        int keyX = x + (size - fm.stringWidth(key)) / 2;
        int keyY = y + size - 4;
        g2d.drawString(key, keyX, keyY);
    }
    
    /**
     * Draw progress indicator.
     */
    private void drawProgress(Graphics2D g2d) {
        int x = PlateformerConfig.SCREEN_WIDTH - 200;
        int y = 20;
        int width = 180;
        int height = 60;
        
        // Shadow
        g2d.setColor(new Color(0, 0, 0, 80));
        g2d.fillRoundRect(x + 3, y + 3, width, height, 15, 15);
        
        // Background
        g2d.setColor(BG_DARK);
        g2d.fillRoundRect(x, y, width, height, 15, 15);
        
        // Border
        g2d.setColor(new Color(100, 100, 120, 100));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(x, y, width, height, 15, 15);
        
        // Text - LARGER
        int collected = inventory.getCollectionCount();
        g2d.setColor(TEXT_COLOR);
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.drawString("Elements", x + 15, y + 26);
        
        // Progress bar
        int barX = x + 15;
        int barY = y + 35;
        int barWidth = width - 30;
        int barHeight = 10;
        
        // Bar background
        g2d.setColor(new Color(40, 40, 50));
        g2d.fillRoundRect(barX, barY, barWidth, barHeight, 5, 5);
        
        // Bar fill
        float progress = collected / 4.0f;
        int fillWidth = (int)(barWidth * progress);
        
        if (fillWidth > 0) {
            // Gradient fill
            Color startColor = new Color(100, 200, 255);
            Color endColor = new Color(150, 100, 255);
            GradientPaint gradient = new GradientPaint(barX, barY, startColor, barX + fillWidth, barY, endColor);
            g2d.setPaint(gradient);
            g2d.fillRoundRect(barX, barY, fillWidth, barHeight, 5, 5);
        }
        
        // Progress text - LARGER
        g2d.setColor(TEXT_COLOR);
        g2d.setFont(new Font("Arial", Font.PLAIN, 14));
        String progressText = collected + " / 4";
        FontMetrics fm = g2d.getFontMetrics();
        int textX = x + (width - fm.stringWidth(progressText)) / 2;
        g2d.drawString(progressText, textX, y + height - 10);
    }
    
    /**
     * Draw instructions at bottom.
     */
    private void drawInstructions(Graphics2D g2d) {
        int y = PlateformerConfig.SCREEN_HEIGHT - 55;
        int x = 20;
        int width = PlateformerConfig.SCREEN_WIDTH - 40;
        int height = 40;
        
        // Background
        g2d.setColor(new Color(20, 20, 30, 150));
        g2d.fillRoundRect(x, y, width, height, 10, 10);
        
        // Text - LARGER
        g2d.setColor(TEXT_COLOR);
        g2d.setFont(new Font("Arial", Font.PLAIN, 14));
        g2d.drawString("WASD / Arrow Keys: Move", x + 15, y + 18);
        g2d.drawString("1-4: Switch Element (collect orbs to unlock)", x + 15, y + 34);
        
        // Hint if all collected
        if (inventory.getCollectionCount() == 4) {
            g2d.setColor(new Color(100, 255, 100));
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            String text = "*** All Elements Collected! ***";
            FontMetrics fm = g2d.getFontMetrics();
            int textX = PlateformerConfig.SCREEN_WIDTH - fm.stringWidth(text) - 30;
            g2d.drawString(text, textX, y + 26);
        }
    }
}
