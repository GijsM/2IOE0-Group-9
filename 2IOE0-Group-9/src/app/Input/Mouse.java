package app.Input;

import app.Window;
import app.Util.Vec2;

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
	private static Mouse instance = null;
	
    private static boolean anyButton = false;
    public static boolean[] buttons = new boolean[8];
    public static boolean[] buttonsDown = new boolean[8];
    public static boolean[] buttonsUp = new boolean[8];

    private static List<Integer> downButtons = new ArrayList<>();
    private static List<Integer> upButtons = new ArrayList<>();

    private static Window window;
    private static DoubleBuffer xBuffer;
    private static DoubleBuffer yBuffer;
    
    private final Vector2d previousPos;
    private final Vector2d currentPos;
    private final Vector2f displVec;
    
    private boolean leftButtonPressed = false;
    private boolean rightButtonPressed = false;
    private boolean inWindow = false;

    public Mouse() {
        window = Window.getInstance();
        
        xBuffer = BufferUtils.createDoubleBuffer(1);
        yBuffer = BufferUtils.createDoubleBuffer(1);
        
        previousPos = new Vector2d(-1, -1);
        currentPos = new Vector2d(0, 0);
        displVec = new Vector2f();
    }
    
    public static Mouse getInstance() {
    	if (instance == null) {
    		instance = new Mouse();
    	}
    	return instance;
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



    public void reset() {
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

    public boolean anyButton() {
        return anyButton;
    }

    public boolean getButton(int buttonCode) {
        return buttons[buttonCode];
    }

    public boolean anyButtonDown() {
        return downButtons.size() > 0;
    }

    public boolean getButtonDown(int buttonCode) {
        return buttonsDown[buttonCode];
    }

    public boolean anyButtonUp() {
        return upButtons.size() > 0;
    }

    public boolean getButtonUp(int buttonCode) {
        return buttonsUp[buttonCode];
    }
    
    public boolean isLeftButtonPressed() {
        return leftButtonPressed;
    }

    public boolean isRightButtonPressed() {
        return rightButtonPressed;
    }
    
    public void setLeftButtonPressed(boolean val) {
        leftButtonPressed = val;
    }

    public void setRightButtonPressed(boolean val) {
        rightButtonPressed = val;
    }

    public Vec2 Position() {
        glfwGetCursorPos(window.getWindow(), xBuffer, yBuffer);
        return new Vec2((float)xBuffer.get(0), (float)yBuffer.get(0));
    }
    
    public Vector2f getDisplVec() {
        return displVec;
    }
}

// OLD
//public void init() {
//    glfwSetCursorPosCallback(window, (windowHandle, xpos, ypos) -> {
//        currentPos.x = xpos;
//        currentPos.y = ypos;
//    });
//    glfwSetCursorEnterCallback(window, (windowHandle, entered) -> {
//        inWindow = entered;
//    });
//    glfwSetMouseButtonCallback(window, (windowHandle, button, action, mode) -> {
//        leftButtonPressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
//        rightButtonPressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
//    });
//}