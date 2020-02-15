package application;

import engine.CameraController;
import engine.Events.Event;
import renderer.Renderer;
import engine.Scene;

public class TestScene extends Scene {

	public TestScene(String name, CameraController cam) {
		super(name, cam);
	}

	@Override
	public void OnUpdate(float deltaTime) {
			
	}

	@Override
	public void OnBegin() {
		Renderer.SetClearColor(1f,1f,0f,1f);
	}

	@Override
	public void OnEnd() {
		
		
	}

	@Override
	public void OnEvent(Event e) {
		
		
	}

	
	
}
