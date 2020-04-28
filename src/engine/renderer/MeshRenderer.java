package engine.renderer;

import java.util.ArrayList;
import java.util.List;

import engine.renderer.mesh.Mesh;


/**
 * 
 * Three dimensional mesh pipeline
 * (Requirement 1.3.1)
 *
 */

public class MeshRenderer {
	
	public static List<Mesh> meshes = new ArrayList<Mesh>();
	
	/**
	 * 
	 * @param mesh - mesh to add to pipeline
	 * 
	 * 
	 * Sorts three dimensional graphics by z order
	 * (Requirement 1.1.4)
	 */
	public static void Add(Mesh mesh) {
		if (!meshes.contains(mesh)) {
			mesh.Init();
			meshes.add(mesh);
			meshes.sort((h1, h2) -> {
				return h1.GetZOrder() > h2.GetZOrder()  ? 1 
						: h1.GetZOrder()  == h2.GetZOrder()  ? 0 : -1;
			});
			
		}
	}
	
	/**
	 * 
	 * @param mesh - mesh to remove from pipeline
	 */
	public static void Remove(Mesh mesh) {
		if (meshes.contains(mesh)) {
			meshes.remove(mesh);
			mesh.CleanUp();
		} else if (mesh != null) {
			mesh.CleanUp();
		}
	}

	/**
	 * Render Pipeline
	 */
	public static void Render() {
		Renderer.SetDepth(true);
		for (int i =0; i < meshes.size(); i++) {
			Mesh m = meshes.get(i);
			m.Bind();
			m.Draw();
			m.UnBind();
		}
		Renderer.SetDepth(false);
	}
	
	/**
	 * Cleanup uncleaned meshes
	 */
	public static void CleanUp () {
		for (Mesh m : meshes) {
			m.CleanUp();
		}
		meshes.clear();
	}
	

}
