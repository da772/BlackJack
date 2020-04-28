package engine;
import java.util.HashMap;
import java.util.Map;

import engine.Events.Event;

/**
 * 
 * Derivable class which controls the lifespan of attached actors
 * (Requirement 2.5.0)
 */
public abstract class Scene {	
	private String name;
	
	protected Map<String, Actor> actors = new HashMap<String, Actor>(0);
	protected Map<String, Actor> actors_update = new HashMap<String, Actor>(0);
	protected CameraController cam;
	boolean init = false;
	
	/**
	 * 
	 * @param name - set unique identifier name
	 * @param cam - camera controller for the scene to use
	 */
	public Scene(String name, CameraController cam) {
		this.name = name;
		this.cam = cam;
		SceneManager.Add(this);
	}
	
	/**
	 * 
	 * @return - unique identifier name
	 */
	public String GetName() {
		return name;
	}
	
	/**
	 * 
	 * @param cam - new camera
	 */
	public void SetCamera(CameraController cam) {
		this.cam = cam;
	}
	
	
	public CameraController GetCameraController() {
		return cam;
	}
	
	/**
	 * 
	 * @param actor - actor to add to scene
	 * @return
	 */
	public boolean AddActor(Actor actor) {
		if (!actors.containsKey(actor.GetName()) && actor != null) {
			actors.put(actor.GetName(), actor);
			if (HasBegin()) {
				actor.Begin();
			}
			if (actor.CanUpdate()) {
				System.out.println("Update");
				actors_update.put(name, actor);
			}
			return true;
		} else if (actors.containsKey(actor.GetName())) {
			try {
				throw new Exception("Actor with: "+actor.GetName() + " already exists!" );
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (actor != null)
			actor.End();
		return false;
	}
	
	/**
	 * 
	 * @param actor - actor to remove from scene
	 * @return
	 */
	public boolean RemoveActor(Actor actor) {
		if (actors.containsKey(actor.GetName())){
			actors.remove(actor.GetName()).End();
			return true;
		}
		actor.End();
		return false;
		
	}	
	
	/**
	 * 
	 * @param name - actor to remove from scene
	 * @return
	 */
	public boolean RemoveActor(String name) {
		if (actors.containsKey(name)){
			actors.remove(name).End();
			if (actors_update.containsKey(name)) {
				actors_update.remove(name);
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param deltaTime - percentage of second
	 */
	public void Update(float deltaTime) {
		if (init) {
			OnUpdate(deltaTime);
			for (Object key : actors_update.keySet()) {
				actors_update.get(key).Update(deltaTime);
			}
		}
	};
	
	public boolean HasBegin() {
		return init;
	}
	/**
	 * Initialized actors
	 */
	public void Begin() {
		OnBegin();
		for (Object key : actors.keySet()) {
			actors.get(key).Begin();
		}
		init = true;
	};
	
	/**
	 * Ends actors
	 */
	public void End() {
		OnEnd();
		for (Object key : actors.keySet()) {
			actors.get(key).End();
		}
		actors_update.clear();
		actors.clear();
		init = false;
	};
	
	public void Event(Event e) {
		OnEvent(e);
	};
	
	public Actor GetActor(String name) {
		return actors.get(name);
	}

	
	public int GetActorCount() {
		return actors.size();
	}
	
	public void SetUpdate(Actor a, boolean b) {
		if (b) {
			if (!actors_update.containsKey(a.GetName())) {
				actors_update.put(a.GetName(), a);
			}
		} else {
			if (actors_update.containsKey(a.GetName())) {
				actors_update.remove(a.GetName());
			}
		}
		
		
	}
	
	
	public abstract void OnUpdate(float deltaTime);
	
	public abstract void OnBegin();
	
	public abstract void OnEnd();
	
	public abstract void OnEvent(Event e);
	
	
	
}