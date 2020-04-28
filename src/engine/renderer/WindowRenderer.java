package engine.renderer;

import java.util.ArrayList;
import java.util.List;

import engine.Application;
import engine.Collision2D;
import engine.WindowFrame;
import engine.renderer.GUI.GUI;

/**
 * 
 * Pipeline for rendering combining pipelines
 * (Requirement 1.3.3)
 *
 */
public class WindowRenderer {

	private static List<GUI> huds = new ArrayList<GUI>();
	private static GUI meshScreen;

	/**
	 * 
	 * @param hud - GUI to add to renderer
	 */
	public static void Add(GUI hud) {
		if (!huds.contains(hud)) {
			if (hud.isMeshScreen) {
				meshScreen = hud;
				hud.Init();
				return;
			}
			huds.add(hud);
			hud.Init();
			Collision2D.Add(hud);
			huds.sort((h1, h2) -> {
				return h1.GetZOrder() > h2.GetZOrder() ? 1 : h1.GetZOrder() == h2.GetZOrder() ? 0 : -1;
			});
			
		}
	}
	
	/**
	 * 
	 * @param hud - GUI to add to renderer
	 */
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
	
	/**
	 * Render Pipeline
	 */
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
	
	public static void RenderMeshScreen() {
		Renderer.SetDepth(false);
		if (meshScreen != null) {
			meshScreen.Bind();
			meshScreen.Draw();
			meshScreen.UnBind();
		}
		Renderer.SetDepth(true);
	}
	
	
	public static void CleanUp () {
		for (GUI h : huds) {
			h.CleanUp();
		}
		huds.clear();
		if (meshScreen != null) {
			meshScreen.CleanUp();
		}
	}

	public static int GetWidth() {
		return Application.GetWindow().GetWidth();
	}
	
	public static int GetHeight() {
		return  Application.GetWindow().GetHeight();
	}
	
	public static int GetMinHeight() {
		return (int) (WindowFrame.GetTop()*GetHeight());
	}

	
}
