package renderer;


import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;

public class Shader {

	String vertex, fragment;
	String shaderSrc;
	
	int rendererId;
	List<Integer> shaderIds = new ArrayList<Integer>();
	private static Map<String, Shader> shaders = new HashMap<String, Shader>();
	int referenceCount;
	
	public static Shader Create(String[] source) {
		return Shader.Create(source[0], source[1]);
	}
	
	public static Shader Create() {
		return Shader.Create(ShaderLib.Shader_L_V3Pos);
	}
	
	public static Shader Create(String vertexSource, String fragmentSource) {
		String src = vertexSource+fragmentSource;
		if (shaders.containsKey(src)) {
			Shader t =  shaders.get(src);
			t.AddReferenceCount(1);
			return t;
		}
		Shader t = new Shader(vertexSource, fragmentSource);
		t.shaderSrc = src;
		t.AddReferenceCount(1);
		shaders.put(src, t);
		return t;
		
	}
	
	public static int GetPoolSize() {
		return shaders.size();
	}
	
	public static void Remove(Shader s) {
		if (shaders.containsValue(s)) {
			s.AddReferenceCount(-1);
		}
	}
	
	public String GetShaderSrc() {
		return this.shaderSrc;
	}
	
	private void AddReferenceCount(int i ) {
		this.referenceCount += i;
		if (this.referenceCount <= 0) {
			shaders.remove(this.shaderSrc);
			this.Cleanup();
			Renderer.RemoveShader(this);
		}
	}
	
	protected Shader(String vertexSource, String fragmentSource) {
		vertex = vertexSource;
		fragment = fragmentSource;
		CompileShader();
	}
	
	private void CompileShader() {
		rendererId = GL30.glCreateProgram();
		List<String> shaders = new ArrayList<String>(); 
		shaders.add(vertex);
		shaders.add(fragment);
		for (int i = 0; i < shaders.size(); i++) {
			
			int shader = GL30.glCreateShader(i == 0 ? GL30.GL_VERTEX_SHADER : GL30.GL_FRAGMENT_SHADER );
			GL30.glShaderSource(shader, shaders.get(i));
			GL30.glCompileShader(shader);
			IntBuffer isCompiled = BufferUtils.createIntBuffer(1);
			isCompiled.put(0);
			isCompiled.flip();
			
			GL30.glGetShaderiv(shader, GL30.GL_COMPILE_STATUS, isCompiled);
			if (isCompiled.get(0) == GL30.GL_FALSE) {
				
				
				String err = GL30.glGetShaderInfoLog(shader);
				
				GL30.glDeleteShader(shader);
				
				System.out.println("OpenGL Shader Error: " + err);
				return;	
			}
			isCompiled.clear();
			GL30.glAttachShader(rendererId, shader);
			shaderIds.add(shader);
			
		}
		
		GL30.glLinkProgram(rendererId);
		IntBuffer isLinked = BufferUtils.createIntBuffer(1);
		isLinked.put(0);
		isLinked.flip();
		GL30.glGetProgramiv(rendererId, GL30.GL_LINK_STATUS, isLinked);
		
		if (isLinked.get() == GL30.GL_FALSE) {
			String err = GL30.glGetProgramInfoLog(rendererId);
			
			GL30.glDeleteProgram(rendererId);
			for (int id : shaderIds) {
				GL30.glDeleteShader(id);
			}
			System.out.println("OpenGL Program Error: " + err);
			return;
			
		}
		isLinked.clear();
		Renderer.AddShader(this);
	}
	
	public void Cleanup() {
		GL30.glDeleteProgram(rendererId);
		for (int id : shaderIds) {
			GL30.glDeleteShader(id);
		}
	}
	
	public void Bind() {
		GL30.glUseProgram(rendererId);
	}
	
	public void UnBind() {
		GL30.glUseProgram(0);
	}
	
	
	public void UploadUniformFloat4(String name, Vector4f data) {
		int loc = GL30.glGetUniformLocation(rendererId, name);
		try (MemoryStack stack = MemoryStack.stackPush())
		{
			FloatBuffer fb = stack.mallocFloat(4);
			data.get(fb);
			GL30.glUniform4fv(loc, fb);
			fb.clear();
		}
	}
	
	public void UploadUniformFloat3(String name, Vector3f data) {
		int loc = GL30.glGetUniformLocation(rendererId, name);
		try (MemoryStack stack = MemoryStack.stackPush())
		{
			FloatBuffer fb = stack.mallocFloat(3);
			data.get(fb);
			GL30.glUniform3fv(loc, fb);
			fb.clear();
		}
	}
	
	public void UploadUniformFloat2(String name, Vector2f data) {
		int loc = GL30.glGetUniformLocation(rendererId, name);
		try (MemoryStack stack = MemoryStack.stackPush())
		{
			FloatBuffer fb = stack.mallocFloat(2);
			data.get(fb);
			GL30.glUniform2fv(loc, fb);
			fb.clear();
		}
	}
	
	public void UploadUniformFloat(String name, float data) {
		int loc = GL30.glGetUniformLocation(rendererId, name);
		try (MemoryStack stack = MemoryStack.stackPush())
		{
			FloatBuffer fb = stack.mallocFloat(1);
			fb.put(data);
			GL30.glUniform1fv(loc, fb);
			fb.clear();
		}
	}
	
	public void UploadUniformMat4(String name, Matrix4f data) {
		int loc = GL30.glGetUniformLocation(rendererId, name);
		try (MemoryStack stack = MemoryStack.stackPush())
		{
			FloatBuffer fb = stack.mallocFloat(16);
			data.get(fb);
			GL30.glUniformMatrix4fv(loc, false, fb);
			fb.clear();
		}
	}
	
	public void UploadUniformMat3(String name, Matrix3f data) {
		int loc = GL30.glGetUniformLocation(rendererId, name);
		try (MemoryStack stack = MemoryStack.stackPush())
		{
			FloatBuffer fb = stack.mallocFloat(12);
			data.get(fb);
			GL30.glUniformMatrix3fv(loc, false, fb);
			fb.clear();
		}
	}
	
	
	
}
