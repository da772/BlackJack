package engine;

import java.util.ArrayList;
import java.util.List;

import engine.Collider2D.CollisionObjectType;
import engine.Events.Event;
import util.Timing;

public class Collision2D extends Thread {

	public static List<Collider2D> GUIcolliders = new ArrayList<Collider2D>();
	public static List<Collider2D> Meshcolliders = new ArrayList<Collider2D>();
	private static float mouseX, mouseY;
	private static Collider2D selectedCollider = null;
	private static Collision2D collision;
	private static boolean running = false;
	
	
	public static void Begin() {
		collision = new Collision2D();
		running = true;
		collision.start();
	}
	
	public static void End() {
		running = false;
		collision = null;
	}
	
	private Collision2D() {};
	
	
	@Override
	public void run() {
		while (Application.app.running && running) {
			OnUpdate();
			Timing.sync(Application.app.fps, false);
		}	
	}
	
	public static void OnUpdate() {
		boolean hit = false;
		try {
			for (int i = GUIcolliders.size()-1; i >= 0 ; i--) {
				if (i >= GUIcolliders.size()) break;
				Collider2D h = GUIcolliders.get(i);
				if (!hit) {
					if (mouseX >= h.GetRect().x && mouseX <= h.GetRect().y
						&& mouseY >= h.GetRect().z && mouseY <= h.GetRect().w) 
					{
						if (!h.IsMouseOver()) {
							h.SetMouseEnter();
						}
						hit = true;
					} else {
						if (h.IsMouseOver()) {
							h.SetMouseExit();
						}
					}
				} else {
					if (h.IsMouseOver()) {
						h.SetMouseExit();
					}
				}
			}
			for (int i = Meshcolliders.size()-1; i >= 0; i--) {
				Collider2D h = Meshcolliders.get(i);
				if (!hit) {
					if (mouseX >= h.GetRect().x && mouseX <= h.GetRect().y
						&& mouseY >= h.GetRect().z && mouseY <= h.GetRect().w) 
					{
						if (!h.IsMouseOver()) {
							h.SetMouseEnter();
						}
						hit = true;
					} else {
						if (h.IsMouseOver()) {
							h.SetMouseExit();
						}
					}
				} else {
					if (h.IsMouseOver()) {
						h.SetMouseExit();
					}
				}
			}
		} catch (Exception e) {
			
		}
		
	}

	public static void SetSelected(Collider2D g) {
		if (selectedCollider != null)
			selectedCollider.RemoveSelection();
		selectedCollider = g;
	}
	
	public static Collider2D GetSelected() {
		return selectedCollider;
	}
		
	public static void OnEvent(Event event) {
		
		if (event instanceof Events.MouseMovedEvent) {
			Events.MouseMovedEvent e = (Events.MouseMovedEvent) event;
			mouseX = e.GetMouseX();
			mouseY = e.GetMouseY();
		}

		if (selectedCollider != null) {
			selectedCollider.SelectedOnEvent(event);
		}
		
	}
		
	public static void Add(Collider2D collider) {
		if (collider.GetCollisionObjectType() == CollisionObjectType.GUI) {
			AddGUICollider(collider);
		} else if (collider.GetCollisionObjectType() == CollisionObjectType.Mesh) {
			AddMeshCollider(collider);
		}
	}
	
	public static void CleanUp () {
		GUIcolliders.clear();
		Meshcolliders.clear();
		selectedCollider = null;
	}
	
	private static void AddGUICollider(Collider2D collider ) {
		if (!GUIcolliders.contains(collider)) {
			GUIcolliders.add(collider);
			GUIcolliders.sort((h1, h2) -> {
				return h1.GetZOrder() > h2.GetZOrder() ? 1 : h1.GetZOrder() == h2.GetZOrder() ? 0 : -1;
			});
		}
	}

	private static void AddMeshCollider(Collider2D collider ) {
		if (!Meshcolliders.contains(collider)) {
			Meshcolliders.add(collider);
			Meshcolliders.sort((h1, h2) -> {
				return h1.GetZOrder() > h2.GetZOrder() ? 1 : h1.GetZOrder() == h2.GetZOrder() ? 0 : -1;
			});
		}
	}
	
	public static void Remove(Collider2D collider) {
		if (collider.GetCollisionObjectType() == CollisionObjectType.GUI) {
			RemoveGUICollider(collider);
		} else if (collider.GetCollisionObjectType() == CollisionObjectType.Mesh) {
			AddMeshCollider(collider);
		}
	}
	
	public static void RemoveGUICollider(Collider2D hud) {
		if (GUIcolliders.contains(hud)) {
			GUIcolliders.remove(hud);
		}
	}
	
	public static void RemoveMeshCollider(Collider2D hud) {
		if (Meshcolliders.contains(hud)) {
			Meshcolliders.remove(hud);
		}
	}
	
		
}
