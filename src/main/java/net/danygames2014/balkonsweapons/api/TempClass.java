package net.danygames2014.balkonsweapons.api;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.glRotatef;

public class TempClass {
    public static TempClass tempClass = new TempClass();
    // Store rotation values for each axis
    private float rotX = 0.0f;
    private float rotY = 0.0f;
    private float rotZ = -45.0f; // Starting with your original -45 on Z

    // Speed of rotation when a key is held down
    private final float rotationSpeed = 1.5f;

    public void translate() {
        // 1. Handle the keyboard input
        handleInput();

        // 2. Apply the rotations to the OpenGL matrix
        // (Order matters in OpenGL: Y -> X -> Z is standard, but adjust as needed)
        GL11.glRotatef(0.0F, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(19.50F, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(352.50F, 0.0f, 0.0f, 1.0f);

        // Your original translation
        GL11.glTranslatef(-0.04F, 0.08F, -0.35F);

        GL11.glScalef(1.1f, 1.1f, 1.1f);
    }

    private void handleInput() {
        boolean changed = false;

        // X-Axis Control (Arrow Up / Arrow Down)
        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            rotX += rotationSpeed;
            changed = true;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            rotX -= rotationSpeed;
            changed = true;
        }

        // Y-Axis Control (Arrow Left / Arrow Right)
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            rotY += rotationSpeed;
            changed = true;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            rotY -= rotationSpeed;
            changed = true;
        }

        // Z-Axis Control (Page Up / Page Down)
        if (Keyboard.isKeyDown(Keyboard.KEY_PRIOR)) { // KEY_PRIOR is Page Up
            rotZ += rotationSpeed;
            changed = true;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_NEXT)) {  // KEY_NEXT is Page Down
            rotZ -= rotationSpeed;
            changed = true;
        }

        // 3. Print to console only if a value actually changed
        if (changed) {
            System.out.printf("Rotation -> X: %.2f | Y: %.2f | Z: %.2f%n", rotX, rotY, rotZ);
        }
    }
}
