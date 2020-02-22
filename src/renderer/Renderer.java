package renderer;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import engine.TextureAtlas;
import renderer.Buffer.FrameBuffer;
import renderer.Buffer.RenderBuffer;


public class Renderer {

	private static List<Integer> Buffers = new ArrayList<Integer>();
	private static List<Integer> VertexArrays = new ArrayList<Integer>();
	private static List<Shader> Shaders = new ArrayList<Shader>();
	private static List<Texture> Textures = new ArrayList<Texture>();
	private static List<TextureAtlas> TextureAtlas = new ArrayList<TextureAtlas>();
	private static Vector4f clearColor = new Vector4f(1f,0f,1f,1f);
	private static FrameBuffer fbuffer;
	private static RenderBuffer rbuffer;
	
	public static void Init(int width, int height) {
		GL30.glEnable(GL11.GL_DEPTH_TEST);
		EnableCulling();
		GL30.glEnable(GL11.GL_BLEND);
		GL30.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL30.glEnable(GL30.GL_MULTISAMPLE);  
		ResizeBuffer(width,height);
		
	}
	
	public static Texture GetScreenTexture() {
		return fbuffer.GetTexture();
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
	
	public static void Render() {
		fbuffer.Bind();
		Prepare();		
		Renderer2D.Render();
		GUIRenderer.Render();
		fbuffer.UnBind();
		WindowRenderer.Render();
	}
	
	/**
	 * 
	 * @param r - red
	 * @param g - green
	 * @param b - blue
	 * @param a - alpha
	 */
	public static void SetClearColor(float r,float g,float b,float a) {
		clearColor = new Vector4f(r,g,b,a);
	}
	
	
	public static void Prepare() {
		GL30.glClearColor(clearColor.x,clearColor.y,clearColor.z, clearColor.w);
		GL30.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	/**
	 * 
	 * @param t - enable / disable depth
	 */
	public static void SetDepth(boolean t) {
		if (t) {
			GL30.glEnable(GL11.GL_DEPTH_TEST);
		} else {
			GL30.glDisable(GL11.GL_DEPTH_TEST);
		}
	}

	/**
	 * Should be used with framebuffers not width or height
	 * @param x - left most position
	 * @param y - bottom most position
	 * @param width - width of viewport
	 * @param height - height of viewport
	 */
	private static void SetViewport(int x, int y, int width, int height) {
		GL30.glViewport(x, y, width, height);
	}
	
	public static void Resize(int x, int y, int width, int height) {
		SetViewport(x,y,width,height);
		ResizeBuffer(width, height);
	}
	
	private static void ResizeBuffer(int width, int height) {
		if (fbuffer == null) fbuffer = new FrameBuffer("ScreenFrameBuffer", width, height);	
		if (rbuffer != null) rbuffer.CleanUp();
		rbuffer = new RenderBuffer(width, height);
		fbuffer.UpdateSize(width, height);
		fbuffer.Render(rbuffer);
	}

	/**
	 * Draw currently bound vertex array
	 * @param size - size of array
	 */
	public static void DrawArrays(int size) {
		GL30.glDrawArrays(GL11.GL_TRIANGLES, 0, size);
	}
	
	/**
	 * Draw currently bound vertex array with indices
	 * @param indexCount - size of indices
	 */
	public static void DrawIndexed(int indexCount) {
		GL30.glDrawElements(GL11.GL_TRIANGLES, indexCount, GL11.GL_UNSIGNED_INT, 0);
	}
		
	/**
	 * Draw currently bound vertex array with indices
	 * @param count - size of indices
	 */
	public static void DrawElements(int count) {
		GL30.glDrawElements(GL11.GL_TRIANGLES, count, GL11.GL_UNSIGNED_INT, 0);
	}
	
	/**
	 * 
	 * @param id - id of buffer to add
	 */
	public static void AddBuffer(int id) {
		Buffers.add(id);
	}
	
	/**
	 * 
	 * @param id - id of buffer to remove
	 */
	public static void RemoveBuffer(int id) {
		Buffers.remove((Object)id);
	}

	/**
	 * 
	 * @param id - id of vertex array to add
	 */
	public static void AddVertexArray(int id) {
		VertexArrays.add(id);
	}
	/**
	 * 
	 * @param id - id of vertex array to remove
	 */
	public static void RemoveVertexArray(int id) {
		VertexArrays.remove((Object)id);
	}
	
	/**
	 * 
	 * @param t - texture to add
	 */
	public static void AddTexture(Texture t) {
		Textures.add(t);
	}
	
	/**
	 * 
	 * @param t - texture to remove
	 */
	public static void RemoveTexture(Texture t) {
		Textures.remove(t);
	}
	/**
	 * 
	 * @param s - shader to add
	 */
	public static void AddShader(Shader s) {
		Shaders.add(s);
	}
	/**
	 * 
	 * @param s - shader to remove
	 */
	public static void RemoveShader(Shader s) {
		Shaders.remove(s);
	}

	/**
	 * 
	 * @param textureAtlas - texture atlas to add
	 */
	public static void AddTextureAtlas(TextureAtlas textureAtlas) {
		TextureAtlas.add(textureAtlas);
	}

	/**
	 * 
	 * @param textureAtlas - texture atlas to remove
	 */
	public static void RemoveAtlas(TextureAtlas textureAtlas) {
		TextureAtlas.remove(textureAtlas);
	}
	
	/**
	 * CleanUp all uncleared buffers, vertex arrays, textures, shaders, atlases
	 */
	public static void ShutDown() {
		
		Renderer2D.CleanUp();
		GUIRenderer.CleanUp();
		
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
		
		rbuffer.CleanUp();
		fbuffer.CleanUp();

	}
	
	

}
