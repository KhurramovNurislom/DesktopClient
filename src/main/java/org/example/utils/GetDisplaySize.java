package org.example.utils;

import java.awt.*;

public class GetDisplaySize {
    /**
     * Keyinchalikka monitorga moslashuvini sozlash uchun kerak
     */
    public void getDisplaySize() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = ge.getScreenDevices();
        for (GraphicsDevice device : devices) {
            DisplayMode mode = device.getDisplayMode();
            int screenWidth = mode.getWidth();
            int screenHeight = mode.getHeight();
            System.out.println("Monitor o'lchami: " + screenWidth + " x " + screenHeight + " piksel");
        }
    }
}
