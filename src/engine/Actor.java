package engine;

import java.util.HashMap;
import java.util.Map;

public class Actor {
	
	
	protected Map<String, Component> components = new HashMap<String, Component>();
	
	protected String _name;
	protected Scene scene;
	protected boolean begin;
	
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
	
	
	public static Actor Create(String name, Scene s) {
		if (s != null) {
			Actor a = new Actor(name);
			a.scene = s;
			if (s.AddActor(a)) {
				return a;	
			}
		}

		return null;
	}
	
	public static boolean Remove(String name, Scene s) {
		if (s!= null) {
			return s.RemoveActor(name);
		}
		return false;
	}
	
	
	protected Actor(String name) {
		this._name = name;
	}
	
	public String GetName() {
		return _name;
	}
	
	public Actor AddComponent(Component c) {
		components.put(c.GetName(), c);
		if (begin) {
			c.OnBegin();
		}
		return this;
	}
	
	public Actor AddComponents(Component[] c) {
		for (Component _c : c) {
			components.put(_c.GetName(), _c);
			if (begin) _c.OnBegin();
		}
		return this;
	}
	
	public Actor RemoveComponent(Component c) {
		components.remove(c.GetName());
		c.OnEnd();
		return this;
	}
	
	public Actor RemoveComponent(String name) {
		components.get(name).OnEnd();
		components.remove(name);
		return this;
	}
	
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