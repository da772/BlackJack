package engine;

import org.joml.Vector3f;

import engine.Events.Event;
import engine.Events.WindowResizedEvent;
import renderer.Camera;
import util.MatrixMath;

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
	
	public abstract boolean OnWindowResized(WindowResizedEvent e);
	
	public static class OrthographicCameraController extends CameraController {
		
		float zoomLevel = 1f;
		
		public OrthographicCameraController(float aspectRatio) {
			super();
			this.aspectRatio = aspectRatio;
			camera = new Camera.OrthographicCamera(-aspectRatio * zoomLevel, aspectRatio * zoomLevel, -zoomLevel, zoomLevel);

		}

		public boolean OnZoom(float x, float y, float amt) {
			zoomLevel = MatrixMath.Clamp(zoomLevel - (y*amt), 0.01f, 100f);
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