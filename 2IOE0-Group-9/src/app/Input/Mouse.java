package app.Input;

import app.engine.Window;
import app.math.Vec2;

import org.joml.Vector2d;
import org.joml.Vector2f;
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
    
    private final Vector2d previousPos;
    private final Vector2d currentPos;
    private final Vector2f displVec;
    
    private boolean leftButtonPressed = false;
    private boolean rightButtonPressed = false;
    private boolean inWindow = false;

    public Mouse() {
        window = Window.getWindow();
        xBuffer = BufferUtils.createDoubleBuffer(1);
        yBuffer = BufferUtils.createDoubleBuffer(1);
        
        previousPos = new Vector2d(-1, -1);
        currentPos = new Vector2d(0, 0);
        displVec = new Vector2f();
    }
    
    public void init() {
        glfwSetCursorPosCallback(window, (windowHandle, xpos, ypos) -> {
            currentPos.x = xpos;
            currentPos.y = ypos;
        });
        glfwSetCursorEnterCallback(window, (windowHandle, entered) -> {
            inWindow = entered;
        });
        glfwSetMouseButtonCallback(window, (windowHandle, button, action, mode) -> {
            leftButtonPressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
            rightButtonPressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
        });
    }
    
    public void input() {
        displVec.x = 0;
        displVec.y = 0;
        if (previousPos.x > 0 && previousPos.y > 0 && inWindow) {
            double deltax = currentPos.x - previousPos.x;
            double deltay = currentPos.y - previousPos.y;
            boolean rotateX = deltax != 0;
            boolean rotateY = deltay != 0;
            if (rotateX) {
                displVec.y = (float) deltax;
            }
            if (rotateY) {
                displVec.x = (float) deltay;
            }
        }
        previousPos.x = currentPos.x;
        previousPos.y = currentPos.y;
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
    
    public boolean isLeftButtonPressed() {
        return leftButtonPressed;
    }

    public boolean isRightButtonPressed() {
        return rightButtonPressed;
    }

    public static Vec2 Position() {
        glfwGetCursorPos(window, xBuffer, yBuffer);
        return new Vec2((float)xBuffer.get(0), (float)yBuffer.get(0));
    }
    
    public Vector2f getDisplVec() {
        return displVec;
    }
}
