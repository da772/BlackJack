package renderer;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class Renderer {

	public static Camera cam;
	
	private static List<Integer> Buffers = new ArrayList<Integer>();
	private static List<Integer> VertexArrays = new ArrayList<Integer>();
	private static List<Shader> Shaders = new ArrayList<Shader>();
	private static List<Integer> Textures = new ArrayList<Integer>();

	public static void Init() {
		GL30.glEnable(GL11.GL_CULL_FACE);
		GL30.glEnable(GL11.GL_DEPTH_TEST);
		GL30.glCullFace(GL11.GL_BACK);
		GL30.glEnable(GL11.GL_BLEND);
		GL30.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	
		cam = new Camera(0, 1280f, 0, 720f, .0001f, 10000f, 90f);
	}

	public static void Prepare() {
		GL30.glClearColor(1, 0, 1, 1);
		GL30.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	public static void SetViewport(int x, int y, int width, int height) {
		GL30.glViewport(x, y, width, height);
		GL30.glMatrixMode(GL11.GL_PROJECTION);
		//GL30.glLoadIdentity();
		//gluPerspective(45.f, (float) width / (float) height, 0.1f, 100.0f);
	}

	private static void gluPerspective(float fovy, float aspect, float near, float far) {
		float bottom = -near * (float) Math.tan(fovy / 2);
		float top = -bottom;
		float left = aspect * bottom;
		float right = -left;
		GL30.glFrustum(left, right, bottom, top, near, far);
	}

	public static void Draw(Mesh mesh, Matrix4f transform) {
		if (mesh == null)
			return;
		mesh.GetShader().Bind();
		if (mesh.GetTexture() != null)
			mesh.GetTexture().Bind();
		mesh.GetVertexArray().Bind();
		GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.GetIndexCount(), GL11.GL_UNSIGNED_INT, 0);
	}

	public static void AddBuffer(int id) {
		Buffers.add(id);
	}

	public static void AddVertexArray(int id) {
		VertexArrays.add(id);
	}
	
	public static void AddTexture(int id) {
		Textures.add(id);
	}
	
	public static void AddShader(Shader s) {
		Shaders.add(s);
	}

	public static void Cleanup() {
		for (int id : Buffers) {
			GL30.glDeleteBuffers(id);
		}
		Buffers.clear();

		for (int id : VertexArrays) {
			GL30.glDeleteVertexArrays(id);
		}
		VertexArrays.clear();
		for (int id : Textures) {
			GL30.glDeleteTextures(id);
		}
		Textures.clear();
		for (Shader s : Shaders) {
			s.Cleanup();
		}
		Shaders.clear();
	}

}
