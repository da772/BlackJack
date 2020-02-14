package renderer;

import org.joml.Vector4f;

public class TextureCoords {
	
	private Vector4f xCoords1, yCoords1, xCoords2,yCoords2 ;
	
	public TextureCoords(Vector4f xCoords, Vector4f yCoords) {
		this.xCoords1 = xCoords;
		this.yCoords1 = yCoords;
		this.yCoords2 = xCoords;
		this.xCoords2 = yCoords;
	}
	
	public TextureCoords(Vector4f xCoords1, Vector4f yCoords1, Vector4f xCoords2, Vector4f yCoords2 ) {
		this.xCoords1 = xCoords1;
		this.yCoords1 = yCoords1;
		this.xCoords2 = xCoords2;
		this.yCoords2 = yCoords2;
	}
	
	public Vector4f GetYCoords1() {
		return yCoords1;
	}
	
	public Vector4f GetXCoords1() {
		return xCoords1;
	}
	
	public Vector4f GetYCoords2() {
		return yCoords2;
	}
	
	public Vector4f GetXCoords2() {
		return xCoords2;
	}

}
