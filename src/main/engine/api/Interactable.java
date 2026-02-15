package main.engine.api;

import main.engine.physics.Vector2D;

import java.awt.*;

/**
 * Interactable -
 */
public interface Interactable {

    int getX();
    int getY();
    int getWidth();
    int getHeight();
    int getInteractRange();


    /**
     * Check if player is close enough to interact.
     */
    default boolean isPlayerNear(Vector2D playerPosition, int playerW, int playerH){

        Vector2D objectCenter = new Vector2D(
                getX() + getWidth() / 2,
                getY() + getHeight() / 2
        );

        Vector2D playerCenter = new Vector2D(
                playerPosition.x + playerW / 2,
                playerPosition.y + playerH / 2
        );

        double distance = objectCenter.distanceTo(playerCenter);

        return distance < getInteractRange();
    }


    /**
     * Draw "Press E" indicator above the interactable object.
     */
    default void drawInteractionIndicator(Graphics2D g2d){
        int indicatorY = getY() - 30;

        // Background
        g2d.setColor(new Color(0, 0, 0, 180));
        g2d.fillRoundRect(getX() + getWidth()/2 - 30, indicatorY, 60, 25, 8, 8);

        // Text
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.drawString("Press E", getX() + getWidth()/2 - 25, indicatorY + 17);
    }

    default boolean pauseGameOnInteract(){
        return false;
    }
    void setFocused(boolean focused);
    boolean isFocused();

    void interact();

    boolean isInteractable();



}
