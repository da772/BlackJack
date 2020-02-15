package renderer;

import java.util.ArrayList;
import java.util.List;

import engine.Application;
import engine.Collision2D;
import renderer.GUI.GUI;


public class GUIRenderer {

	public static List<GUI> huds = new ArrayList<GUI>();
	
	public static void Add(GUI hud) {
		if (!huds.contains(hud)) {
			huds.add(hud);
			hud.Init();
			Collision2D.Add(hud);
			huds.sort((h1, h2) -> {
				return h1.GetZOrder() > h2.GetZOrder() ? 1 : h1.GetZOrder() == h2.GetZOrder() ? 0 : -1;
			});
			
		}
	}
	
	public static void Remove(GUI hud) {
		if (huds.contains(hud)) {
			huds.remove(hud);
			Collision2D.Remove(hud);
			hud.CleanUp();
		} else {
			if (hud != null) {
				hud.CleanUp();
			}
		}
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
	}

	
	public static int GetWidth() {
		return Application.app.GetWindow().GetWidth();
	}
	
	public static int GetHeight() {
		return  Application.app.GetWindow().GetHeight();
	}

	
}
