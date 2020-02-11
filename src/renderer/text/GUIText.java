package renderer.text;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import renderer.Buffer.VertexBuffer;
import util.MathLib;
import renderer.GUI;
import renderer.GUIRenderer;
import renderer.Renderer;
import renderer.Shader;
import renderer.ShaderLib;
import renderer.Texture;
import renderer.Transform;
import renderer.VertexArray;


public class GUIText extends GUI {

	private String textString;
	private float fontSize;
	
	private VertexArray varray;
	private VertexBuffer vbuffer;
	private int verticesSize;
	
	private Texture texture;
	protected int renderType = 0;
	
	private float lineMaxSize;
	private int numberOfLines;
		

	private FontType font;
	private String fontString;

	private boolean centerText = false;
	

	/**
	 * Creates a new text, loads the text's quads into a VAO, and adds the text
	 * to the screen.
	 * 
	 * @param text
	 *            - the text.
	 * @param fontSize
	 *            - the font size of the text, where a font size of 1 is the
	 *            default size.
	 * @param font
	 *            - the font that this text should use.
	 * @param position
	 *            - the position on the screen where the top left corner of the
	 *            text should be rendered. The top left corner of the screen is
	 *            (0, 0) and the bottom right is (1, 1).
	 * @param maxLineLength
	 *            - basically the width of the virtual page in terms of screen
	 *            width (1 is full screen width, 0.5 is half the width of the
	 *            screen, etc.) Text cannot go off the edge of the page, so if
	 *            the text is longer than this length it will go onto the next
	 *            line. When text is centered it is centered into the middle of
	 *            the line, based on this line length value.
	 * @param centered
	 *            - whether the text should be centered or not.
	 */
	
	
	public GUIText(String text, float fontSize, String font,  Transform transform, Vector4f color, float maxLineLength,
			boolean centered) {
		shader_strings = ShaderLib.Shader_Font;
		this.textString = text;
		this.fontSize = fontSize;
		this.fontString = font;
		this.lineMaxSize = maxLineLength;
		this.centerText = centered;
		this.transform = transform;
		this.color = color;
		this.UVScale = new Vector2f(1.f,1.f);
		_Init();
	}
	
	public void SetText(String text) {
		this.textString = text;
		varray.CleanUp();
		vbuffer.CleanUp();
		_Init();
	}
	

	@Override
	public void Add() {
		GUIRenderer.Add(this);
		added = true;
	}

	/**
	 * @return The font used by this text.
	 */
	public FontType getFont() {
		return font;
	}


	/**
	 * @return The number of lines of text. This is determined when the text is
	 *         loaded, based on the length of the text and the max line length
	 *         that is set.
	 */
	public int getNumberOfLines() {
		return numberOfLines;
	}

	/**
	 * @return the ID of the text's VAO, which contains all the vertex data for
	 *         the quads on which the text will be rendered.
	 */
	public VertexArray getVertexArray() {
		return varray;
	}

	
	public int getVerticesSize() {
		return verticesSize;
	}
	
	/**
	 * Set the VAO and vertex count for this text.
	 * 
	 * @param vao
	 *            - the VAO containing all the vertex data for the quads on
	 *            which the text will be rendered.
	 * @param verticesCount
	 *            - the total number of vertices in all of the quads.
	 */
	public void setMeshInfo(float[] vertices) {
		varray = new VertexArray();
		verticesSize = vertices.length;
		vbuffer = new VertexBuffer(vertices, vertices.length);
		varray.AddVertexBuffer(vbuffer, new VertexArray.BufferLayout( new VertexArray.BufferElement[]{
			new VertexArray.BufferElement(VertexArray.ElementType.Float2, "position"),
			new VertexArray.BufferElement(VertexArray.ElementType.Float2, "textureCoords")
		}));
	}

	/**
	 * @return The total number of vertices of all the text's quads.
	 */
	

	/**
	 * @return the font size of the text (a font size of 1 is normal).
	 */
	protected float getFontSize() {
		return fontSize;
	}

	/**
	 * Sets the number of lines that this text covers (method used only in
	 * loading).
	 * 
	 * @param number
	 */
	protected void setNumberOfLines(int number) {
		this.numberOfLines = number;
	}

	/**
	 * @return {@code true} if the text should be centered.
	 */
	protected boolean isCentered() {
		return centerText;
	}

	/**
	 * @return The maximum length of a line of this text.
	 */
	public float getMaxLineSize() {
		return lineMaxSize;
	}

	/**
	 * @return The string of text.
	 */
	protected String getTextString() {
		return textString;
	}


	@Override
	public void Bind() {
		shader.Bind();
		texture.Bind();
		shader.UploadUniformFloat4("u_Color", color);
		shader.UploadUniformMat4("u_Transform", _transform.GetTransformMatrix() );
		varray.Bind();
	}


	@Override
	public void UnBind() {
		shader.UnBind();
		texture.UnBind();
		varray.UnBind();
	}

	public void SetMaxLineSize(float size) {
		this.lineMaxSize = size;
		UpdateMeshInfo();
	}
	
	public void SetFontSize(float size) {
		this.fontSize= size;
		UpdateMeshInfo();
	}
	
	protected void CreateMeshInfo() {
		TextMeshData data = font.loadText(this);
		setMeshInfo(data.getVertices());
	}
	
	protected void UpdateMeshInfo() {
		varray.CleanUp();
		vbuffer.CleanUp();
		FontType.Remove(font);
		TextMeshData data = font.loadText(this);
		setMeshInfo(data.getVertices());
	}
	
	protected void SetUp() {
		this.texture = Texture.Create(fontString+".png", true);
		this.font = FontType.Create(fontString);
		shader = Shader.Create(shader_strings);
		CreateMeshInfo();
		UpdateTransform();
	}
	
	@Override
	protected void _Init() {
		SetUp();
	}
	

	@Override
	public int IndicesCount() {
		return this.getVerticesSize();
	}


	@Override
	public void CleanUp() {
		varray.CleanUp();
		vbuffer.CleanUp();
		Texture.Remove(texture);
		Shader.Remove(shader);
		FontType.Remove(font);
	}

	@Override
	public int VertexCount() {
		return getVerticesSize();
	}
	
	@Override
	protected void UpdateTransform() {
		this.zOrder = transform.GetPosition().z;
		this.transform.SetPosition(transform.GetPosition().x, transform.GetPosition().y, 0f);
		this._transform = new Transform(transform.GetPosition(), transform.GetRotation(), 
				transform.GetScale());
		this._transform.SetPosition(new Vector3f(MathLib.GetMappedRangeValueUnclamped(-1, 1, -2, 2, MathLib.Clamp(_transform.GetPosition().x,-1,1)), 
				-MathLib.GetMappedRangeValueUnclamped(-1, 1, -2, 2, -MathLib.Clamp(_transform.GetPosition().y,-1,1)),_transform.GetPosition().z ));
		this._transform.SetScale(new Vector3f(1f));
		this.transform.SetPosition(transform.GetPosition().x, transform.GetPosition().y, transform.GetPosition().z);
	}

	@Override
	public void Draw() {
		Renderer.DrawArrays(getVerticesSize());
	}

}
