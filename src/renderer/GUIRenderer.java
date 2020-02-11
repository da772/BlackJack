package renderer;

import java.util.ArrayList;
import java.util.List;


public class GUIRenderer {

	
	public static List<GUI> huds = new ArrayList<GUI>();
	
	
	public static void Add(GUI hud) {
		if (!huds.contains(hud)) {
			hud.Init();
			huds.add(hud);
			huds.sort((h1, h2) -> {
				return h1.zOrder > h2.zOrder ? 1 : h1.zOrder == h2.zOrder ? 0 : -1;
			});
			
		}
	}
	
	public static void Remove(GUI hud) {
		if (huds.contains(hud)) {
			huds.remove(hud);
			hud.CleanUp();
		}
	}
	
	public static void Render() {
		Renderer.SetDepth(false);
		for (int i =0; i < huds.size(); i++) {
			GUI h = huds.get(i);
			h.Bind();
			if (h.GetRenderType() == 0) {
				Renderer.DrawArrays(h.VertexCount());
			} else {
				Renderer.DrawElements(h.IndicesCount());
			}
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
	
}
