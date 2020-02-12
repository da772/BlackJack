package renderer;

import java.util.ArrayList;
import java.util.List;

import engine.Application;
import engine.Events;
import engine.Events.Event;



public class GUIRenderer {

	public static List<GUI> huds = new ArrayList<GUI>();
	private static float mouseX, mouseY;
	private static int mouseButton = -1, mouseButtonReleased = -1;
	private static GUI selectedGUI = null;

	
	public static void Add(GUI hud) {
		if (!huds.contains(hud)) {
			hud.Init();
			huds.add(hud);
			huds.sort((h1, h2) -> {
				return h1.zOrder > h2.zOrder ? 1 : h1.zOrder == h2.zOrder ? 0 : -1;
			});
			
		}
	}
	
	public static void SetSelectedGUI(GUI g) {
		selectedGUI = g;
	}
	
	public static void Remove(GUI hud) {
		if (huds.contains(hud)) {
			huds.remove(hud);
			hud.CleanUp();
		}
	}
	
	public static void OnUpdate() {
		boolean hit = false;
		for (int i = huds.size()-1; i >= 0; i--) {
			GUI h = huds.get(i);
			if (!hit) {
				if (mouseX >= h.GetRect().x && mouseX <= h.GetRect().y
					&& mouseY >= h.GetRect().z && mouseY <= h.GetRect().w) 
				{
					if (!h.IsMouseOver()) {
						h.SetMouseEnter();
					}
					if (mouseButton >= 0) {
						h.SetMouseClicked(mouseButton);
					}
					if (mouseButtonReleased >= 0) {
						h.SetMouseButtonReleased(mouseButtonReleased);
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
		
		mouseButton = -1;
		mouseButtonReleased = -1;
	}
	
	public static void Render() {
		Renderer.SetDepth(false);
		for (int i =0; i < huds.size(); i++) {
			GUI h = huds.get(i);
			h.Bind();
			h.Draw();
			h.UnBind();
		}
		Renderer.SetDepth(true);
	}
	
	
	public static void CleanUp () {
		for (GUI h : huds) {
			h.CleanUp();
		}
		huds.clear();	
		selectedGUI = null;
	}

	
	public static int GetWidth() {
		
		return Application.app.GetWindow().GetWidth();
	}
	
	public static int GetHeight() {
		return  Application.app.GetWindow().GetHeight();
	}
	
	public static void OnEvent(Event event) {
		
		if (event instanceof Events.MouseMovedEvent) {
			Events.MouseMovedEvent e = (Events.MouseMovedEvent) event;
			mouseX = e.GetMouseX();
			mouseY = e.GetMouseY();
		}
		
		if (event instanceof Events.MouseButtonPressedEvent) {
			Events.MouseButtonPressedEvent e = (Events.MouseButtonPressedEvent) event;
			mouseButton = e.GetKeyCode();
		}
		
		if (event instanceof Events.MouseButtonReleasedEvent) {
			Events.MouseButtonReleasedEvent e = (Events.MouseButtonReleasedEvent) event;
			mouseButtonReleased = e.GetKeyCode();
		}
		
		if (selectedGUI != null) {
			selectedGUI.SelectedOnEvent(event);
		}
	}
	
	public static GUI GetSelectedGUI() {
		return selectedGUI;
	}
	
}
