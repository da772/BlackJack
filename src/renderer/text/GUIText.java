package renderer.text;


import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import renderer.Buffer.VertexBuffer;
import renderer.Renderer;
import renderer.Texture;
import renderer.VertexArray;
import util.MathLib;


public class GUIText {

	private String textString;
	private float fontSize;
	
	private VertexArray varray;
	private VertexBuffer vbuffer;
	private int verticesSize;

	private Vector4f color = new Vector4f(0f, 0f, 0f, 1f);
	private Vector3f position = new Vector3f(0f, 0f, 0f);
	private Vector3f scale = new Vector3f(0f, 0f, 0f);
	private Vector3f rotation = new Vector3f(0f, 0f, 0f);
	
	private Matrix4f transform;
	private float lineMaxSize;
	private int numberOfLines;

	private FontType font;

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
	public GUIText(String text, float fontSize, String font,  Vector3f pos, Vector3f rot, Vector3f scale, float maxLineLength,
			boolean centered) {
		this.textString = text;
		this.fontSize = fontSize;
		this.font = new FontType(Texture.Create(font+".png", true), font);
		this.transform = MathLib.createTransformMatrix(new Vector3f(pos.x+1,  pos.y+1, pos.z), rot, scale);
		this.lineMaxSize = maxLineLength;
		this.centerText = centered;
		TextRenderer.loadText(this);;
	}
	
	
	public GUIText(String text, float fontSize, String font, Vector3f pos, Vector3f rot, Vector3f scale, Vector4f color, float maxLineLength,
			boolean centered) {
		this.textString = text;
		this.fontSize = fontSize;
		this.font = new FontType(Texture.Create(font+".png", true), font);
		this.position = pos;
		this.rotation = rot;
		this.scale = scale;
		this.color = color;
		CalculateTransform();
		this.lineMaxSize = maxLineLength;
		this.centerText = centered;
		TextRenderer.loadText(this);;
	}
	
	public void SetText(String text) {
		this.textString = text;
		this.remove();
		TextRenderer.loadText(this);
	}

	public void add() {
		TextRenderer.loadText(this);
	}
	
	/**
	 * Remove the text from the screen.
	 */
	public void remove() {
		TextRenderer.removeText(this);
		varray.CleanUp();
		vbuffer.CleanUp();
	}

	/**
	 * @return The font used by this text.
	 */
	public FontType getFont() {
		return font;
	}

	/**
	 * Set the colour of the text.
	 * 
	 * @param r
	 *            - red value, between 0 and 1.
	 * @param g
	 *            - green value, between 0 and 1.
	 * @param b
	 *            - blue value, between 0 and 1.
	 */
	public void setColor(float r, float g, float b, float a) {
		color.set(r, g, b, a);
	}
	
	public void setPosition(float x, float y, float z) {
		this.position = new Vector3f(x,y,z);
		CalculateTransform();
	}
	
	private void CalculateTransform() {
		this.transform = MathLib.createTransformMatrix(new Vector3f(MathLib.GetMappedRangeValueUnclamped(-1, 1, 0, 2, MathLib.Clamp(position.x, -1, 1)), 
				-MathLib.GetMappedRangeValueUnclamped(-1, 1, 0, 2,MathLib.Clamp(-position.y, -1, 1)), position.z  ), rotation, scale);
	}

	/**
	 * @return the color of the text.
	 */
	public Vector4f getColor() {
		return color;
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
	 * @return The position of the top-left corner of the text in screen-space.
	 *         (0, 0) is the top left corner of the screen, (1, 1) is the bottom
	 *         right.
	 */
	public Matrix4f getTransform() {
		return transform;
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
	protected float getMaxLineSize() {
		return lineMaxSize;
	}

	/**
	 * @return The string of text.
	 */
	protected String getTextString() {
		return textString;
	}

}