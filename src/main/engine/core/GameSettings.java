package main.engine.core;

public class GameSettings {
    private String title;
    private int screenWidth;
    private int screenHeight;
    private int fps;
    private boolean resizable;
    private int tileSize=0;

    // Private constructor (only builder can create)
    private GameSettings(Builder builder) {
        this.title = builder.title;
        this.screenWidth = builder.screenWidth;
        // etc...
        this.screenHeight = builder.screenHeight;
        this.fps = builder.fps;
        this.resizable = builder.resizable;
        this.tileSize = builder.tileSize;
    }

    // Getters
    public String getTitle() { return title; }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getFps() {
        return fps;
    }

    public boolean isResizable() {
        return resizable;
    }

    public int getTileSize(){ return this.tileSize; }

    // Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String title = "Game";  // Default
        private int screenWidth = 800;  // Default
        private int screenHeight = 600; // Default
        private int fps = 60;           // Default
        private boolean resizable = false;
        private int tileSize;
        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder screenSize(int width, int height) {
            this.screenWidth = width;
            this.screenHeight = height;
            return this;
        }

        // etc...
        public Builder fps(int fps){
            this.fps = fps;
            return this;
        }

        public Builder resizable(boolean resizable){
            this.resizable = resizable;
            return this;
        }

        public Builder tileSize(int tileSize){
            this.tileSize = tileSize;
            return this;
        }

        public GameSettings build() {
            return new GameSettings(this);
        }
    }
}
