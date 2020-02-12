package renderer;

import org.joml.Vector2f;
import org.joml.Vector4f;

import engine.Events.Event;
import renderer.Buffer.IndexBuffer;
import renderer.Buffer.VertexBuffer;

public class GUIQuad extends GUI {
	protected static final float[] vertices = {
			-1f,  -1f,   0,  0f, 0f,
			 1f,  -1f,   0f, 1f, 0f,
			 1f,   1f,   0f, 1f, 1f,
			-1f,   1f,   0f, 0f, 1f
	};
	
	protected static final int[] indices = {
			0,1,2,
			2,3,0
	};
	
	
	protected static IndexBuffer ibuffer = new IndexBuffer(indices, indices.length);
	protected static VertexBuffer vbuffer = new VertexBuffer(vertices, vertices.length);
	protected static VertexArray varray = new VertexArray().AddVertexBuffer(vbuffer,  new VertexArray.BufferLayout(new VertexArray.BufferElement[]{
			new VertexArray.BufferElement(VertexArray.ElementType.Float3, "u_Position"),
			new VertexArray.BufferElement(VertexArray.ElementType.Float2, "u_TexCoord") 
			})).AddIndexBuffer(ibuffer);
	
	public GUIQuad(Transform transform, Texture texture, Vector4f color, Vector2f UVScale) {
		super(transform, texture,color,UVScale);
	}
	
	public GUIQuad(Transform transform, Texture texture, Vector4f color, Vector2f UVScale, String[] shader) {
		super(transform, texture,color,UVScale, shader);
	}
	
	public GUIQuad(Transform transform, String texture, Vector4f color, Vector2f UVScale) {
		super(transform, texture, color, UVScale);
	}
	
	public GUIQuad(Transform transform, Vector4f color) {
		super(transform, color);
	}
	
	public GUIQuad(Transform transform, String texture, Vector4f color) {
		super(transform, texture, color);
	}
	
	public GUIQuad(Transform transform, String texture) {
		super(transform, texture);
	}
	
	@Override
	public void _Init() {
		shader = Shader.Create(shader_strings);
		texture = Texture.Create(this.texturePath);
	}
		
	@Override
	public void Bind() {
		shader.Bind();
		texture.Bind();
		shader.UploadUniformFloat4("u_Color", color);
		shader.UploadUniformFloat2("u_UVScale", UVScale);
		shader.UploadUniformMat4("u_Transform", _transform.GetTransformMatrix() );
		varray.Bind();
		
	}
	
	@Override
	public void UnBind() {
		shader.UnBind();
		texture.UnBind();
		varray.UnBind();
	}
	@Override
	public int IndicesCount() {
		return indices.length;
	}
	@Override
	public void CleanUp() {
		Texture.Remove(texture);
		Shader.Remove(shader);
	}

	@Override
	public int VertexCount() {
		return vertices.length;
	}

	@Override
	public void Draw() {
		Renderer.DrawElements(IndicesCount());
	};
	
	public void SetTexture(String texturePath) {
		this.texturePath = texturePath;
		Texture.Remove(this.texture);
		this.texture = Texture.Create(texturePath);
	}

	@Override
	public void SelectedOnEvent(Event e) {
		// TODO Auto-generated method stub
		
	}
	

	
	
	
	
	
}
