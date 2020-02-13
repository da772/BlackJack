package renderer;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import renderer.mesh.Mesh2D;


public class Renderer {

	private static List<Integer> Buffers = new ArrayList<Integer>();
	private static List<Integer> VertexArrays = new ArrayList<Integer>();
	private static List<Shader> Shaders = new ArrayList<Shader>();
	private static List<Texture> Textures = new ArrayList<Texture>();
	
	public static void Init() {
		GL30.glEnable(GL11.GL_CULL_FACE);
		GL30.glEnable(GL11.GL_DEPTH_TEST);
		GL30.glCullFace(GL11.GL_BACK);
		GL30.glEnable(GL11.GL_BLEND);
		GL30.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL30.glEnable(GL30.GL_MULTISAMPLE);  
	}
	
	public static int GetBufferCount() {
		return Buffers.size();
	}
	
	public static int GetVertexArrayCount() {
		return VertexArrays.size();
	}

	public static void DisableCulling() {
		GL30.glDisable(GL11.GL_CULL_FACE);
	}
	
	public static void EnableCulling() {
		GL30.glEnable(GL11.GL_CULL_FACE);
		GL30.glCullFace(GL11.GL_BACK);
	}
	
	public static void Prepare() {
		GL30.glClearColor(1, 0, 1, 1);
		GL30.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public static void SetDepth(boolean t) {
		if (t) {
			GL30.glEnable(GL11.GL_DEPTH_TEST);
		} else {
			GL30.glDisable(GL11.GL_DEPTH_TEST);
		}
	}

	public static void SetViewport(int x, int y, int width, int height) {
		GL30.glViewport(x, y, width, height);
	}

	public static void DrawArrays(int size) {
		GL30.glDrawArrays(GL11.GL_TRIANGLES, 0, size);
	}
	
	public static void DrawIndexed(Mesh2D mesh) {
		if (mesh == null)
			return;
		mesh.Bind();
		GL30.glDrawElements(GL11.GL_TRIANGLES, mesh.GetIndexCount(), GL11.GL_UNSIGNED_INT, 0);
		mesh.UnBind();
	}
		
	public static void DrawElements(int count) {
		GL30.glDrawElements(GL11.GL_TRIANGLES, count, GL11.GL_UNSIGNED_INT, 0);
	}
	
	
	public static void Draw(VertexArray ar, int size) {
		if (ar == null)
			return;
		ar.Bind();
		GL30.glDrawArrays(GL11.GL_TRIANGLES, 0, size);
		ar.UnBind();
	}
	
	
	public static void Draw(Mesh2D mesh) {
		if (mesh == null)
			return;
		mesh.Bind();
		GL30.glDrawArrays(GL11.GL_TRIANGLES, 0, mesh.GetVertexCount());
		mesh.UnBind();
	}

	public static void AddBuffer(int id) {
		Buffers.add(id);
	}
	
	public static void RemoveBuffer(int id) {
		Buffers.remove((Object)id);
	}

	public static void AddVertexArray(int id) {
		VertexArrays.add(id);
	}
	
	public static void RemoveVertexArray(int id) {
		VertexArrays.remove((Object)id);
	}
	
	public static void AddTexture(Texture t) {
		Textures.add(t);
	}
	
	public static void RemoveTexture(Texture t) {
		Textures.remove(t);
	}
	
	public static void AddShader(Shader s) {
		Shaders.add(s);
	}
	

	public static void RemoveShader(Shader s) {
		Shaders.remove(s);
	}

	public static void ShutDown() {
		for (int id : Buffers) {
			GL30.glDeleteBuffers(id);
		}
		Buffers.clear();

		for (int id : VertexArrays) {
			GL30.glDeleteVertexArrays(id);
		}
		VertexArrays.clear();
		for (Texture t : Textures) {
			t.CleanUp();
		}
		Textures.clear();
		for (Shader s : Shaders) {
			s.Cleanup();
		}
		Shaders.clear();
	}

}
