package renderer;

import java.util.ArrayList;
import java.util.List;

import renderer.mesh.Mesh2D;

public class Renderer2D {
	
	public static List<Mesh2D> meshes = new ArrayList<Mesh2D>();
	
	
	public static void Add(Mesh2D mesh) {
		if (!meshes.contains(mesh)) {
			mesh.Init();
			meshes.add(mesh);
			meshes.sort((h1, h2) -> {
				return h1.GetZOrder() > h2.GetZOrder()  ? 1 
						: h1.GetZOrder()  == h2.GetZOrder()  ? 0 : -1;
			});
			
		}
	}
	
	public static void Remove(Mesh2D mesh) {
		if (meshes.contains(mesh)) {
			meshes.remove(mesh);
			mesh.CleanUp();
		} else if (mesh != null) {
			mesh.CleanUp();
		}
	}

	public static void Render() {
		Renderer.SetDepth(true);
		for (int i =0; i < meshes.size(); i++) {
			Mesh2D m = meshes.get(i);
			m.Bind();
			m.Draw();
			m.UnBind();
		}
		Renderer.SetDepth(false);
	}
	
	public static void CleanUp () {
		for (Mesh2D m : meshes) {
			m.CleanUp();
		}
		meshes.clear();
	}
	

}
