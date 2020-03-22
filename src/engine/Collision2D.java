package engine;

import java.util.ArrayList;
import java.util.List;

import engine.Collider2D.CollisionObjectType;
import engine.Events.Event;
import engine.renderer.GUIRenderer;
import engine.util.MathLib;

public class Collision2D extends Thread {

	public static List<Collider2D> GUIcolliders = new ArrayList<Collider2D>();
	public static List<Collider2D> GUITitlecolliders = new ArrayList<Collider2D>();
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
	
	public static void Reset() {
		End();
		Begin();
	}
	
	private Collision2D() {};
	
	@Override
	/**
	 * Creates new thread which runs looop
	 */
	public void run() {
		while (Application.app.running && running) {
			try {
				OnUpdate();
				Thread.sleep(1000/Application.app.fpsCap);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
	}
	
	public static void OnUpdate() {
		// Keep track if we hit anything
		boolean hit = false;
		// Try, possible for queues to change since we are on separate thread
		try {
			// loop through title GUI
			for (int i = GUITitlecolliders.size()-1; i >= 0 ; i--) {
				Collider2D h = GUITitlecolliders.get(i);
				// Check if we are in bounds of window
				if (!MathLib.InBounds(mouseX, 0f, (float)GUIRenderer.GetWidth())
						|| !MathLib.InBounds( mouseY, 0f, (float)GUIRenderer.GetHeight())) {
					hit = true;
				}
				// Make sure we haven't hit anything yet
				if (!hit) {
					// Check if we are inside GUI's rect
					if (mouseX >= h.GetRect().x && mouseX <= h.GetRect().y
						&& mouseY >= h.GetRect().z && mouseY <= h.GetRect().w) 
					{
						// If we arent already inside the GUI run MouseEnter()
						if (!h.IsMouseOver()) {
							h.SetMouseEnter();
						}
						// Tell the loop we hit something
						hit = true;

					} else {
						// We aren't inside the GUI's rect, make sure we call exit if we were in it last frame
						if (h.IsMouseOver()) {
							h.SetMouseExit();
						}
					}
				} else {
					// If we hit something else make sure we call exit if we were in it last frame
					if (h.IsMouseOver()) {
						h.SetMouseExit();
					}
					
				}
			}
			// loop through GUI
			for (int i = GUIcolliders.size()-1; i >= 0 ; i--) {
				Collider2D h = GUIcolliders.get(i);
				// Check if we are in bounds of window
				if (!MathLib.InBounds(mouseX, 0f, (float)GUIRenderer.GetWidth())
						|| !MathLib.InBounds( mouseY, GUIRenderer.GetMinHeight(), (float)GUIRenderer.GetHeight())) {
					hit = true;
				}
				// Make sure we haven't hit anything yet
				if (!hit) {
					// Check if we are inside GUI's rect
					if (mouseX >= h.GetRect().x && mouseX <= h.GetRect().y
						&& mouseY >= h.GetRect().z && mouseY <= h.GetRect().w && 
						(selectedCollider == null || (selectedCollider != null && !selectedCollider.isLocked)) ) 
					{
						// If we arent already inside the GUI run MouseEnter()
						if (!h.IsMouseOver()) {
							h.SetMouseEnter();
						}
						// Tell the loop we hit something
						hit = true;

					} else {
						// We aren't inside the GUI's rect, make sure we call exit if we were in it last frame
						if (h.IsMouseOver() && !h.isLocked) {
							h.SetMouseExit();
						}
					}
				} else {
					// If we hit something else make sure we call exit if we were in it last frame
					if (h.IsMouseOver()) {
						h.SetMouseExit();
					}
					
				}
			}
			// Loop through mesh colliders
			for (int i = Meshcolliders.size()-1; i >= 0; i--) {
				Collider2D h = Meshcolliders.get(i);
				// Make sure we haven't hit anything yet
				if (!hit) {
					// Check if the mouse is in the bounds
					if (mouseX >= h.GetRect().x && mouseX <= h.GetRect().y
						&& mouseY >= h.GetRect().z && mouseY <= h.GetRect().w) 
					{
						// if we hit something and we arent already inside it call mouse enter
						if (!h.IsMouseOver()) {
							h.SetMouseEnter();
						}
						hit = true;
					} else {
						// if we didnt hit it, but we are not longer inside it call mouse exit
						if (h.IsMouseOver()) {
							h.SetMouseExit();
						}
					}
				} else {
					// If we hit something else make sure we call exit if we were in it last frame
					if (h.IsMouseOver()) {
						h.SetMouseExit();
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

	/**
	 * 
	 * @param g - collider to set as selected
	 */
	public static void SetSelected(Collider2D g) {
		if (selectedCollider != null)
			selectedCollider.RemoveSelection();
		selectedCollider = g;
	}
	
	public static Collider2D GetSelected() {
		return selectedCollider;
	}
		
	/**
	 * 
	 * @param event - event passed
	 */
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
		
	/**
	 * 
	 * @param collider - collider to add
	 */
	public static void Add(Collider2D collider) {
		if (!collider.collision) return;
		if (collider.GetCollisionObjectType() == CollisionObjectType.GUI) {
			AddGUICollider(collider);
		} else if (collider.GetCollisionObjectType() == CollisionObjectType.Mesh) {
			AddMeshCollider(collider);
		}
	}
	
	public static void CleanUp () {
		GUIcolliders.clear();
		Meshcolliders.clear();
		GUITitlecolliders.clear();
		selectedCollider = null;
	}
	
	/**
	 * 
	 * @param collider - gui collider to add
	 */
	private static void AddGUICollider(Collider2D collider ) {
		if (!GUIcolliders.contains(collider) && !collider.isWindow) {
			GUIcolliders.add(collider);
			GUIcolliders.sort((h1, h2) -> {
				return h1.GetZOrder() > h2.GetZOrder() ? 1 : h1.GetZOrder() == h2.GetZOrder() ? 0 : -1;
			});
		} else if (!GUITitlecolliders.contains(collider) && collider.isWindow) {
			GUITitlecolliders.add(collider);
			GUITitlecolliders.sort((h1, h2) -> {
				return h1.GetZOrder() > h2.GetZOrder() ? 1 : h1.GetZOrder() == h2.GetZOrder() ? 0 : -1;
			});
		}
	}

	/**
	 * 
	 * @param collider - mesh collider to add
	 */
	private static void AddMeshCollider(Collider2D collider ) {
		if (!Meshcolliders.contains(collider)) {
			Meshcolliders.add(collider);
			Meshcolliders.sort((h1, h2) -> {
				return h1.GetZOrder() > h2.GetZOrder() ? 1 : h1.GetZOrder() == h2.GetZOrder() ? 0 : -1;
			});
		}
	}
	
	/**
	 * 
	 * @param collider - collider to remove
	 */
	public static void Remove(Collider2D collider) {
		if (collider.GetCollisionObjectType() == CollisionObjectType.GUI) {
			RemoveGUICollider(collider);
		} else if (collider.GetCollisionObjectType() == CollisionObjectType.Mesh) {
			AddMeshCollider(collider);
		}
	}
	
	/**
	 * 
	 * @param hud - gui to remove
	 */
	public static void RemoveGUICollider(Collider2D hud) {
		if (GUIcolliders.contains(hud)) {
			if (hud.IsMouseOver()) {
				hud.SetMouseExit();
			}
			GUIcolliders.remove(hud);
		} else if (GUITitlecolliders.contains(hud)) {
			if (hud.IsMouseOver()) {
				hud.SetMouseExit();
			}
			GUITitlecolliders.remove(hud);
		}
	}
	
	/**
	 * 
	 * @param hud - mesh to remove
	 */
	public static void RemoveMeshCollider(Collider2D hud) {
		if (Meshcolliders.contains(hud)) {
			Meshcolliders.remove(hud);
		}
	}
	
		
}
