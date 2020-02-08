package renderer.font;

public class TextMeshData {
	
	private float[] vertexPositions;
	private float[] textureCoords;
	
	private float[] vertices;
	
	protected TextMeshData(float[] vertexPositions, float[] textureCoords){
		this.vertexPositions = vertexPositions;
		this.textureCoords = textureCoords;
		
		vertices = new float[ vertexPositions.length + textureCoords.length ];
		
		int row = 0;
		for (int i = 0; i < vertices.length; i+=4) {
			vertices[i] = vertexPositions[row * 2];
	        vertices[i+1] = vertexPositions[row * 2 + 1];
	        
	        vertices[i+2] = textureCoords[row * 2];
	        vertices[i+3] = textureCoords[row * 2 + 1];
	        
	        row ++;
		}
		
	}
	
	public float[] getVertices() {
		return vertices;
	}

	public float[] getVertexPositions() {
		return vertexPositions;
	}

	public float[] getTextureCoords() {
		return textureCoords;
	}

	public int getVertexCount() {
		return vertexPositions.length/2;
	}

}
