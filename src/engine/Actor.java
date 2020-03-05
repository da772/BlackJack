package engine;

import java.util.HashMap;
import java.util.Map;



public class Actor {
	
	
	protected Map<String, Component> components = new HashMap<String, Component>();
	
	protected String _name;
	protected Scene scene;
	protected boolean begin;
	protected boolean update = false;
	
	
	
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
	public Actor(String name) {
		this._name = name;
		Init();
	}
	
	protected void Init() {
		Scene s = SceneManager.GetCurrentScene();
		
		if (s != null) {
			s.AddActor(this);
			this.scene = s;
		}
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
		component.SetActor(this);
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
			_c.SetActor(this);
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
		component.SetActor(null);
		component.OnEnd();
		return this;
	}

	
	/**
	 * DOES NOT CLEAN UP COMPONENTS (USED FOR CHANGING OWNERSHIP)
	 * @param component (Component)
	 *            - the component to remove from the actor
	 * @return Actor
	 * 			  - return actor this call is made on
	**/
	public Actor RemoveComponent_NOCLEAN(Component component) {
		components.remove(component.GetName());
		component.SetActor(null);
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
		components.get(name).SetActor(null);
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
		OnUpdate(deltaTime);
	};
	
	
	protected void Begin() {
		for (Object key : components.keySet()) {
			components.get(key).OnBegin();
		}
		begin = true;
		OnBegin();
	}
	
	public boolean CanUpdate() {
		return this.update;
	}
	
	public void SetUpdate(boolean b) {
		this.scene.SetUpdate(this, b);
	}
	
	protected void End() {
		for (Object key : components.keySet()) {
			components.get(key).OnEnd();
		}
		components.clear();
		OnEnd();
		begin = false;
	}

	
	public void OnEnd() {};
	public void OnUpdate(float deltaTime) {};
	public void OnBegin() {};
	
}