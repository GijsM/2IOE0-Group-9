package Input;

import engine.Window;
import math.Vec2;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

enum CursorImage {
    Pointer, Hand, HScroll, Vscroll
}

public class Mouse extends GLFWMouseButtonCallback {
    private static boolean anyButton = false;
    public static boolean[] buttons = new boolean[8];
    public static boolean[] buttonsDown = new boolean[8];
    public static boolean[] buttonsUp = new boolean[8];

    private static List<Integer> downButtons = new ArrayList<>();
    private static List<Integer> upButtons = new ArrayList<>();

    private static long window;
    private static DoubleBuffer xBuffer;
    private static DoubleBuffer yBuffer;

    public Mouse() {
        window = Window.Window();
        xBuffer = BufferUtils.createDoubleBuffer(1);
        yBuffer = BufferUtils.createDoubleBuffer(1);
    }

    public static void Reset() {
        for (int i = 0; i < downButtons.size(); i++) {
            buttonsDown[downButtons.get(i)] = false;
        }
        for (int i = 0; i < upButtons.size(); i++) {
            buttonsUp[upButtons.get(i)] = false;
        }
        anyButton = false;
        downButtons.clear();
        upButtons.clear();
    }

    @Override
    public void invoke(long window, int button, int action, int mods) {
        anyButton = true;
        buttons[button] = action != GLFW_RELEASE;
        if(action == GLFW_PRESS) {
            buttonsDown[button] = true;
            downButtons.add(button);
        }
        if(action == GLFW_RELEASE) {
            buttonsUp[button] = true;
            upButtons.add(button);
        }
    }

    public static boolean AnyButton() {
        return anyButton;
    }

    public static boolean GetButton(int buttonCode) {
        return buttons[buttonCode];
    }

    public static boolean AnyButtonDown() {
        return downButtons.size() > 0;
    }

    public static boolean GetButtonDown(int buttonCode) {
        return buttonsDown[buttonCode];
    }

    public static boolean AnyButtonUp() {
        return upButtons.size() > 0;
    }

    public static boolean GetButtonUp(int buttonCode) {
        return buttonsUp[buttonCode];
    }

    public static Vec2 Position() {
        glfwGetCursorPos(window, xBuffer, yBuffer);
        return new Vec2((float)xBuffer.get(0), (float)yBuffer.get(0));
    }
}
