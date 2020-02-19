package renderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import util.MathLib;


/**
 * Contains position, rotation, and scale vectors, and calculate matrices based on those values
 * 
 */

public class Transform {

	private Vector3f position, rotation, scale;
	private Matrix4f transform;
	
	public Transform() {
		this.position = new Vector3f(0f);
		this.rotation = new Vector3f(0,0,0);
		this.scale = new Vector3f(1.f);
		CalculateTransformMatrix();
	}
	
	/**
	 * 
	 * @param position
	 * @param rotation
	 * @param scale
	 */
	public Transform(Vector3f position, Vector3f rotation, Vector3f scale) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
		CalculateTransformMatrix();
	}
	
	/**
	 * Rotation set to 0f scale set to 1f
	 * @param position
	 */
	public Transform(Vector3f position) {
		this.position = position;
		this.rotation = new Vector3f(0,0,0);
		this.scale = new Vector3f(1.f);
		CalculateTransformMatrix();
	}
	
	/**
	 * 
	 * @param position
	 */
	public void SetPosition(Vector3f position) {
		this.position = position;
		CalculateTransformMatrix();
	}
	
	/**
	 * 
	 * @param x - x position
	 * @param y - y position
	 * @param z - z position
	 */
	public void SetPosition(float x, float y, float z) {
		this.position = new Vector3f(x,y,z);
		CalculateTransformMatrix();
	}
	
	public void SetRotation(Vector3f rotation) {
		this.rotation = rotation;
		CalculateTransformMatrix();
	}
	
	/**
	 * 
	 * @param x - pitch
	 * @param y - yaw
	 * @param z - roll
	 */
	public void SetRotation(float x, float y, float z) {
		this.rotation = new Vector3f(x,y,z);
		CalculateTransformMatrix();
	}
	
	public void SetScale(Vector3f scale) {
		this.scale= scale;
		CalculateTransformMatrix();
	}
	
	/**
	 * 
	 * @param x - x scale
	 * @param y - y scale
	 * @param z - z scale
	 */
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
	
	/**
	 * Set position based on scale (deprecated)
	 * @param transform
	 * @return
	 */
	public static Transform ScaleBasedPosition(Transform transform) {
		transform.SetPosition(new Vector3f( 
				transform.position.x - ( ( 1-transform.scale.x) * transform.position.x/2 ),
				transform.position.y - ( (1-transform.scale.y) * transform.position.y/2),
				transform.position.z - ( (1-transform.scale.z) * transform.position.z)));
		return transform;
	}
	
}
