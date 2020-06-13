package app.Game.Object;

import org.joml.Vector3f;

import app.engine.Mesh;

public class Tree extends GameObject {

	private final Vector3f position;
    private final Vector3f rotation;
    
	public Tree(Mesh mesh) {
		super(mesh);
		position = new Vector3f();
    	rotation = new Vector3f();
		// TODO Auto-generated constructor stub
	}

	public Vector3f getPosition() {
    	return position;
    }
    
    public void setPosition(float x, float y, float z) {
    	this.position.x = x;
    	this.position.y = y;
    	this.position.z = z;
    }
    
    public Vector3f getRotation() {
    	return rotation;
    }
    
    public void setRotation(float x, float y, float z) {
    	this.rotation.x = x;
    	this.rotation.y = y;
    	this.rotation.z = z;
    }
}
