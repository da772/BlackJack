package engine;

import java.util.HashMap;
import java.util.Map;



public class Actor {
	
	
	protected Map<String, Component> components = new HashMap<String, Component>();
	
	protected String _name;
	protected Scene scene;
	protected boolean begin;
	
	/**
	 * @param name
	 *            - the unique identifier of actor
	 * @return Actor
	 * 			  - return created actor or null if identifier already used
	**/
	public static Actor Create(String name) {
		Scene s = SceneManager.GetCurrentScene();
		
		if (s != null) {
			Actor a = new Actor(name);
			s.AddActor(a);
			a.scene = s;
			return a;
		}

		return null;
	}
	
	
	
	/**
	 * @param name (String)
	 *            - the unique identifier of actor
	 * @param scene (Scene)
	 *            - the scene to add the actor to
	 *            
	 * @return Actor
	 * 			  - return created actor or null if identifier already used
	**/
	public static Actor Create(String name, Scene scene) {
		if (scene != null) {
			Actor a = new Actor(name);
			a.scene = scene;
			if (scene.AddActor(a)) {
				return a;	
			}
		}

		return null;
	}
	
	
	/**
	 * @param name (String)
	 *            - the unique identifier of actor
	 * @param scene (Scene)
	 *            - the scene to remove the actor from
	**/
	public static boolean Remove(String name, Scene s) {
		if (s!= null) {
			return s.RemoveActor(name);
		}
		return false;
	}
	

	/**
	 * @param name (String)
	 *            - the unique identifier of actor
	 * @return Actor
	 * 			  - return actor this call is made on
	**/
	protected Actor(String name) {
		this._name = name;
	}
	

	public String GetName() {
		return _name;
	}
	

	/**
	 * @param component (Component)
	 *            - the component to add to the actor
	 * @return Actor
	 * 			  - return actor this call is made on
	**/
	public Actor AddComponent(Component component) {
		components.put(component.GetName(), component);
		if (begin) {
			component.OnBegin();
		}
		return this;
	}
	
	/**
	 * @param component (Component[])
	 *            - the components to add to the actor
	 * @return Actor
	 * 			  - return actor this call is made on
	**/
	public Actor AddComponents(Component[] component) {
		for (Component _c : component) {
			components.put(_c.GetName(), _c);
			if (begin) _c.OnBegin();
		}
		return this;
	}
	
	/**
	 * @param component (Component)
	 *            - the component to remove from the actor
	 * @return Actor
	 * 			  - return actor this call is made on
	**/
	public Actor RemoveComponent(Component component) {
		components.remove(component.GetName());
		component.OnEnd();
		return this;
	}
	
	/**
	 * @param name (String)
	 *            - the component to remove from the actor by identifying name
	 * @return Actor
	 * 			  - return actor this call is made on
	**/
	public Actor RemoveComponent(String name) {
		components.get(name).OnEnd();
		components.remove(name);
		return this;
	}
	
	/**
	 * @param name (String)
	 *            - the component to get by identifying name
	 * @return Component
	**/
	public Component GetComponent(String name) {
		return components.get(name);
	}
	
	protected void Update(float deltaTime) {
		
	};
	
	
	protected void Begin() {
		for (Object key : components.keySet()) {
			components.get(key).OnBegin();
		}
		begin = true;
	}
	
	protected void End() {
		for (Object key : components.keySet()) {
			components.get(key).OnEnd();
		}
		components.clear();
		begin = false;
	}

	
	public void OnEnd() {};
	public void OnUpdate() {};
	public void OnBegin() {};
	
}