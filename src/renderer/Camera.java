package renderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import math.MatrixMath;


public class Camera {

	private Vector3f position;
	
	private Vector3f rotation;
	
	private Matrix4f viewMatrix;
	private Matrix4f projectionMatrix;
	private Matrix4f viewProjectionMatrix;
	
	float left, right, bottom, top,farPlane, nearPlane, fov;


	Camera(float left, float right, float bottom, float top, float nearPlane, float farPlane, float fov) {
		this.left = left;
		this.bottom = bottom;
		this.top = top;
		this.right =right;
		this.nearPlane = nearPlane;
		this.farPlane = farPlane;
		this.fov = fov;
		
		this.rotation = new Vector3f();
		this.position = new Vector3f();
		
		CalculateViewProjectionMatrix();
	}
	
	
	
	public Vector3f GetPosition() {
		return position;
	}
	
	public Vector3f GetRotation() {
		return rotation;
	}
	
	public void SetRotation(Vector3f rot) {
		this.rotation = rot;
		CalculateViewProjectionMatrix();
	}
	
	public void SetPosition(Vector3f pos) {
		this.position = pos;
		CalculateViewProjectionMatrix();
	}
	
	
	public Matrix4f GetViewMatrix() {
		return viewMatrix;
	}
	
	public Matrix4f GetProjectionMatrix() {
		return projectionMatrix;
	}
	public Matrix4f GetViewProjectionMatrix() {
		return viewProjectionMatrix;
	}
	
	protected void CalculateViewProjectionMatrix() {
		CalculateProjectionMatrix();
		CalculateViewMatrix();
	}
	
	protected void CalculateProjectionMatrix() {
		float aspectRatio = 16.f/9.f;
		float y_scale = (float)((1f / Math.tan(Math.toRadians(fov/2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		
		float frustum_length = farPlane - nearPlane;
		
		projectionMatrix = new Matrix4f();
		projectionMatrix.m00(x_scale);
		projectionMatrix.m11(y_scale);
		projectionMatrix.m22(((-farPlane+nearPlane)/frustum_length));
		projectionMatrix.m23(-1);
		projectionMatrix.m32(-((2*nearPlane*farPlane) / frustum_length));
		projectionMatrix.m33(0);
		
	}
	
	
	protected void CalculateViewMatrix() {
		viewMatrix = MatrixMath.createViewMatrix(position, rotation);
	}
	
	
	 
}
