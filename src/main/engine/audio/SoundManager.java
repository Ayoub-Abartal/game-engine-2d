package main.engine.audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * SoundManager - Handles game main.engine.audio (sound effects and music).
 * 
 * Usage:
 *   SoundManager.play("jump");
 *   SoundManager.playMusic("level1");
 *   SoundManager.stopMusic();
 */
public class SoundManager {
    
    private static Map<String, Clip> sounds = new HashMap<>();
    private static Clip currentMusic;
    private static float sfxVolume = 0.8f;
    private static float musicVolume = 0.5f;
    private static boolean muted = false;
    
    /**
     * Load a sound effect.
     */
    public static void loadSound(String name, String path) {
        try {
            URL url = SoundManager.class.getResource(path);
            if (url == null) {
                System.err.println("Sound not found: " + path);
                return;
            }
            
            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            sounds.put(name, clip);
            System.out.println("Sound loaded: " + name);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Failed to load sound: " + path + " - " + e.getMessage());
        }
    }
    
    /**
     * Play a sound effect (one-shot).
     */
    public static void play(String name) {
        if (muted) return;
        
        Clip clip = sounds.get(name);
        if (clip != null) {
            clip.setFramePosition(0);
            setVolume(clip, sfxVolume);
            clip.start();
        }
    }
    
    /**
     * Play background music (loops).
     */
    public static void playMusic(String name) {
        if (currentMusic != null) {
            currentMusic.stop();
        }
        
        Clip clip = sounds.get(name);
        if (clip != null) {
            currentMusic = clip;
            clip.setFramePosition(0);
            setVolume(clip, musicVolume);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
    
    /**
     * Stop current music.
     */
    public static void stopMusic() {
        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic = null;
        }
    }
    
    /**
     * Set volume for a clip (0.0 to 1.0).
     */
    private static void setVolume(Clip clip, float volume) {
        try {
            FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            control.setValue(Math.max(dB, control.getMinimum()));
        } catch (Exception e) {
            // Volume control not supported
        }
    }
    
    public static void setSfxVolume(float vol) { sfxVolume = Math.max(0, Math.min(1, vol)); }
    public static void setMusicVolume(float vol) { musicVolume = Math.max(0, Math.min(1, vol)); }
    public static void setMuted(boolean m) { muted = m; if (m) stopMusic(); }
    public static boolean isMuted() { return muted; }
}
