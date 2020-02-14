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
	
	public CameraController() {
		Position = new Vector3f();
	}
	
	public Camera GetCamera() {
		return camera;
	}
	
	public void OnEvent(Event event) {	
		Events.EventDispatcher<Event> dispatcher = new Events.EventDispatcher<Event>(event);
		dispatcher.Dispatch(WindowResizedEvent.GetGenericType(), new Events.EventFunction<Event>(event) {
			@Override public boolean run(Event t) { return OnWindowResized((WindowResizedEvent) t);} });
	}
	
	public void OnUpdate(float deltaTime) {
		camera.SetPosition(Position);
		camera.SetRotation(rotation);
	}
	
	public void SetPosition(Vector3f pos) {
		this.Position = pos;
	}
	
	public boolean OnZoom(float x, float y, float amt) {
		return false;
	}
	
	public abstract boolean OnWindowResized(WindowResizedEvent e);
	
	public static class Orthographic extends CameraController {
		
		float zoomLevel = 1f;
		
		public Orthographic(float aspectRatio) {
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
	
public static class Perspective extends CameraController {
		
		float zoomLevel = 1f;
		
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
