package engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;


public abstract class Camera {

	protected Matrix4f ProjectionMatrix, ViewMatrix, ViewProjectionMatrix;
	protected Vector3f Position;
	protected float rotation = 0f;
	
	public Camera() {
		Position = new Vector3f(0,0,0);
		ProjectionMatrix = new Matrix4f();
		ViewMatrix = new Matrix4f();
		ViewProjectionMatrix = new Matrix4f();
	}
	
	protected abstract void RecalculateViewMatrix();
	/**
	 * 
	 * @param left - camera left
	 * @param right - camera right
	 * @param bottom - camera bottom
	 * @param top - camera top
	 */
	public abstract void SetProjection(float left, float right, float bottom, float top);

	/**
	 * @return Matrix4f - ViewProjectionMatrix
	 */
	public Matrix4f GetViewProjectionMatrix() {
		return ViewProjectionMatrix;
	}

	/**
	 * @param position
	 */
	public void SetPosition(final Vector3f position) {
		this.Position = position;
		RecalculateViewMatrix();
	}
	
	/**
	 * 
	 * @param rot
	 */
	public void SetRotation(final float rot) {
		this.rotation = rot;
		RecalculateViewMatrix();
	}
	
	public static class PerspectiveCamera extends Camera {

		@Override
		protected void RecalculateViewMatrix() {
			ViewMatrix = new Matrix4f();
			ViewMatrix.identity().translate(Position).rotate((float)Math.toRadians(rotation), new Vector3f(1f,0f,0f)).invert(ViewMatrix);
			ProjectionMatrix.mul(ViewMatrix, ViewProjectionMatrix);
		}

		
		@Override
		/**
		 * 
		 * @param left - FOV
		 * @param right - Aspect Ratio
		 * @param bottom - 
		 * @param top - 
		 */
		public void SetProjection(float left, float right, float bottom, float top) {
			ProjectionMatrix.perspective(45f, right, -1, 1, ProjectionMatrix);
		}
		
		
		
	}
	
	public static class OrthographicCamera extends Camera {
		
		/**
		 * 
		 * @param left - negative aspect ratio
		 * @param right - aspect ratio
		 * @param bottom
		 * @param top
		 */
		public OrthographicCamera(float left, float right, float bottom, float top) {
			super();
			ProjectionMatrix.ortho(left, right, -1.f, 1.f, -1.f, 100000.f, ProjectionMatrix);
			RecalculateViewMatrix();
		}
		
		@Override
		protected void RecalculateViewMatrix() {
			ViewMatrix = new Matrix4f();
			ViewMatrix.identity().translate(Position).rotate((float)Math.toRadians(rotation), new Vector3f(1f,0f,0f)).invert(ViewMatrix);
			ProjectionMatrix.mul(ViewMatrix, ViewProjectionMatrix);
		}
		
		@Override
		/**
		 * 
		 * @param left - negative aspect ratio
		 * @param right - aspect ratio
		 * @param bottom
		 * @param top
		 */
		public void SetProjection(float left, float right, float bottom, float top) {
			ProjectionMatrix = new Matrix4f().ortho(left, right, bottom, top, -1.f, 100000.f, ProjectionMatrix);
			RecalculateViewMatrix();
		}
		
	}
	
	 
}
