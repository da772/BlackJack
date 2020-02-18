package engine;

import java.util.HashMap;
import java.util.Map;

import engine.Events.Event;

public class SceneManager {

	private static Map<String, Scene> scenes = new HashMap<String, Scene>();
	private static Scene activeScene;

	/**
	 * 
	 * @param scene - Scene to add
	 */
	public static void Add(Scene scene) {
		if (scenes.containsKey(scene.GetName()) ) return;
		scenes.put(scene.GetName(), scene);
	}
	
	/**
	 * 
	 * @param scene - scene to remove
	 */
	public static void Remove(Scene scene) {
		scenes.remove(scene.GetName());
	}
	
	/**
	 * 
	 * @param scene - unique identifier string to remove
	 */
	public static void Remove(String scene) {
		scenes.remove(scene);
	}
	
	
	public static void OnUpdate(float deltaTime) {
		if (activeScene != null) activeScene.OnUpdate(deltaTime);
	}
	
	/**
	 * 
	 * @param scene - scene to set as active, calls end on previous scene
	 */
	public static void SetCurrentScene(Scene scene) {
		if (activeScene == scene) return;
		if (activeScene != null) activeScene.End();
		activeScene = scene;
		activeScene.Begin();
	}
	
	/**
	 * 
	 * @return - return active scene
	 */
	public static Scene GetCurrentScene() {
		return activeScene;
	}
	
	/**
	 * 
	 * @return - returns amount of scenes manager is keeping track of
	 */
	public static int GetSceneCount() {
		return scenes.size();
	}
	
	public static void SetCurrentScene(String scene) {
		SetCurrentScene(scenes.get(scene));
	}
	
	public static void OnEvent(Event event) {
		if (activeScene != null) activeScene.Event(event);
	}
	
	public static void Shutdown() {
		if (activeScene != null) activeScene.End();
		scenes.clear();
		activeScene = null;
	}
	
	
}
