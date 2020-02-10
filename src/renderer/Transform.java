package renderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import util.MathLib;


/*
 * Transform 
 *   Holds position information and stores into matrix
 * 
 * 
 */

public class Transform {

	private Vector3f position, rotation, scale;
	private Matrix4f transform;
	
	public Transform(Vector3f position, Vector3f rotation, Vector3f scale) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
		CalculateTransformMatrix();
	}
	
	public Transform(Vector3f position) {
		this.position = position;
		this.rotation = new Vector3f(0,0,0);
		this.scale = new Vector3f(1.f,1.f,1.f);
		CalculateTransformMatrix();
	}
	
	public void SetPosition(Vector3f position) {
		this.position = position;
		CalculateTransformMatrix();
	}
	
	public void SetPosition(float x, float y, float z) {
		this.position = new Vector3f(x,y,z);
		CalculateTransformMatrix();
	}
	
	public void SetRotation(Vector3f rotation) {
		this.rotation = rotation;
		CalculateTransformMatrix();
	}
	
	public void SetRotation(float x, float y, float z) {
		this.rotation = new Vector3f(x,y,z);
		CalculateTransformMatrix();
	}
	
	public void SetScale(Vector3f scale) {
		this.scale= scale;
		CalculateTransformMatrix();
	}
	
	public void SetScale(float x, float y, float z) {
		this.scale = new Vector3f(x,y,z);
		CalculateTransformMatrix();
	}
	
	public Vector3f GetPosition() {
		return this.position;
	}
	
	public Vector3f GetRotation() {
		return this.rotation;
	}
	
	public Vector3f GetScale() {
		return this.scale;
	}
	
	public Matrix4f GetTransformMatrix() {
		return this.transform;
	}
	
	private void CalculateTransformMatrix() {
		this.transform = MathLib.createTransformMatrix(position, rotation, scale);
	}
	
	public static Transform ScaleBasedPosition(Transform transform) {
		transform.SetPosition(new Vector3f( 
				transform.position.x - ( ( 1-transform.scale.x) * transform.position.x/2 ),
				transform.position.y - ( (1-transform.scale.y) * transform.position.y/2),
				transform.position.z - ( (1-transform.scale.z) * transform.position.z)));
		return transform;
	}
	
	
	
	
	
	
}
