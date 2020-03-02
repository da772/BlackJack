package engine;

import org.joml.Vector3f;

import engine.Events.Event;
import engine.Events.WindowResizedEvent;
import util.MathLib;

public abstract class CameraController {

	protected Camera camera;
	protected float aspectRatio;
	public Vector3f Position;
	public float rotation;
	float targetAspectRatio = 0;
	
	public CameraController() {
		Position = new Vector3f();
	}
	
	public Camera GetCamera() {
		return camera;
	}
	
	/**
	 * 
	 * @param event - event passed
	 */
	public void OnEvent(Event event) {	
		Events.EventDispatcher<Event> dispatcher = new Events.EventDispatcher<Event>(event);
		dispatcher.Dispatch(WindowResizedEvent.GetGenericType(), new Events.EventFunction<Event>(event) {
			@Override public boolean run(Event t) { return OnWindowResized((WindowResizedEvent) t);} });
	}
	
	/**
	 * 
	 * @param deltaTime - percentage of second
	 */
	public void OnUpdate(float deltaTime) {
		camera.SetPosition(Position);
		camera.SetRotation(rotation);
	}
	
	/**
	 * 
	 * @param pos - position
	 */
	public void SetPosition(Vector3f pos) {
		this.Position = pos;
	}
	
	public void SetRotation(float rot) {
		this.rotation = rot;
	}
	
	public void SetTargetAspectRatio(float r) {
		this.targetAspectRatio = r;
	}
	
	/**
	 * 
	 * @param x - x zoom
	 * @param y - y zoom
	 * @param amt - amount to zoom
	 * @return
	 */
	public boolean OnZoom(float x, float y, float amt) {
		return false;
	}
	
	
	/**
	 * 
	 * @param e - window resized event
	 * @return
	 */
	public abstract boolean OnWindowResized(WindowResizedEvent e);
	
	public static class Orthographic extends CameraController {
		
		float zoomLevel = 1f;
		
		/**
		 * 
		 * @param aspectRatio
		 */
		public Orthographic(float aspectRatio) {
			super();
			this.aspectRatio = aspectRatio;
			camera = new Camera.OrthographicCamera(-aspectRatio * zoomLevel, aspectRatio * zoomLevel, -zoomLevel, zoomLevel);
			OnZoom(0,0,0);
		}

		public float GetZoomLevel() {
			return this.zoomLevel;
		}
		
		public void SetZoomLevel(float z) {
			this.zoomLevel = z;
			OnZoom(0,0,0);
		}
		
		@Override
		public boolean OnZoom(float x, float y, float amt) {
			zoomLevel = MathLib.Clamp(zoomLevel - (y*amt), 0.01f, 100f);
			camera.SetProjection(-aspectRatio * zoomLevel, aspectRatio * zoomLevel, -zoomLevel, zoomLevel);
			return false;
		}
		
		@Override
		public boolean OnWindowResized(WindowResizedEvent e) {
			aspectRatio = this.targetAspectRatio != 0 ? this.targetAspectRatio : (float)e.GetWidth()/(float)e.GetHeight();
			camera.SetProjection(-aspectRatio * zoomLevel, aspectRatio* zoomLevel, -zoomLevel, zoomLevel);
			return false;
		}
	}
	
public static class Perspective extends CameraController {
		
		float zoomLevel = 1f;
		
		/**
		 * 
		 * @param aspectRatio
		 */
		public Perspective(float aspectRatio) {
			super();
			this.aspectRatio = aspectRatio;
			camera = new Camera.OrthographicCamera(-aspectRatio * zoomLevel, aspectRatio * zoomLevel, -zoomLevel, zoomLevel);
		}

		public float GetZoomLevel() {
			return this.zoomLevel;
		}
		
		@Override
		public boolean OnZoom(float x, float y, float amt) {
			zoomLevel = MathLib.Clamp(zoomLevel - (y*amt), 0.01f, 100f);
			camera.SetProjection(-aspectRatio * zoomLevel, aspectRatio * zoomLevel, -zoomLevel, zoomLevel);
			return false;
		}
		
		@Override
		public boolean OnWindowResized(WindowResizedEvent e) {
			aspectRatio = (float)e.GetWidth()/(float)e.GetHeight();
			camera.SetProjection(-aspectRatio * zoomLevel, aspectRatio * zoomLevel, -zoomLevel, zoomLevel);
			return false;
		}
	}
	
	
}
