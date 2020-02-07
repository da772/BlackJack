package renderer;


import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

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
	
	int rendererId;
	List<Integer> shaderIds = new ArrayList<Integer>();
	
	
	public Shader(String vertexSource, String fragmentSource) {
		vertex = vertexSource;
		fragment = fragmentSource;
		CompileShader();
	}
	
	public Shader(String[] src) {
		vertex = src[0];
		fragment = src[1];
		CompileShader();
	}
	
	public Shader() {
		vertex = ShaderLib.PositionF3_V_Shader[0];
		fragment = ShaderLib.PositionF3_V_Shader[1];
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
		FloatBuffer ptr = BufferUtils.createFloatBuffer(4);
		data.get(ptr);
		ptr.flip();
		GL30.glUniform4fv(loc, ptr);
	}
	
	public void UploadUniformFloat3(String name, Vector3f data) {
		int loc = GL30.glGetUniformLocation(rendererId, name);
		FloatBuffer ptr = BufferUtils.createFloatBuffer(3);
		data.get(ptr);
		ptr.flip();
		GL30.glUniform3fv(loc, ptr);
	}
	
	public void UploadUniformFloat2(String name, Vector2f data) {
		int loc = GL30.glGetUniformLocation(rendererId, name);
		FloatBuffer ptr = BufferUtils.createFloatBuffer(2);
		data.get(ptr);
		ptr.flip();
		GL30.glUniform2fv(loc, ptr);
	}
	
	public void UploadUniformFloat(String name, float data) {
		int loc = GL30.glGetUniformLocation(rendererId, name);
		FloatBuffer ptr = BufferUtils.createFloatBuffer(1);
		ptr.put(data);
		ptr.flip();
		GL30.glUniform1f(loc, data);
	}
	
	public void UploadUniformMat4(String name, Matrix4f data) {
		int loc = GL30.glGetUniformLocation(rendererId, name);
		try (MemoryStack stack = MemoryStack.stackPush())
		{
			FloatBuffer fb = stack.mallocFloat(16);
			data.get(fb);
			GL30.glUniformMatrix4fv(loc, false, fb);
		}
	}
	
	public void UploadUniformMat3(String name, Matrix3f data) {
		int loc = GL30.glGetUniformLocation(rendererId, name);
		try (MemoryStack stack = MemoryStack.stackPush())
		{
			FloatBuffer fb = stack.mallocFloat(12);
			data.get(fb);
			GL30.glUniformMatrix3fv(loc, false, fb);
		}
	}
	
	
	
}
