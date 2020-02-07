package util;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class MatrixMath {
	
	public static Matrix4f createTransformMatrix(Vector3f pos, Vector3f rot, Vector3f scale) {
		Matrix4f matrix = new Matrix4f();
		matrix = matrix.translate(pos);
		matrix = matrix.rotate( (float)Math.toRadians(rot.x), new Vector3f(1,0,0)  );
		matrix = matrix.rotate( (float)Math.toRadians(rot.y), new Vector3f(0,1,0)  );
		matrix = matrix.rotate( (float)Math.toRadians(rot.z), new Vector3f(0,0,1)  );
		matrix = matrix.scale(scale);
		return matrix;
	}
	
	public static Matrix4f createViewMatrix(Vector3f pos, Vector3f rot) {
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix = viewMatrix.identity();
		
		viewMatrix = viewMatrix.rotate( (float)Math.toRadians(rot.x), new Vector3f(1,0,0)  );
		viewMatrix = viewMatrix.rotate( (float)Math.toRadians(rot.y), new Vector3f(0,1,0)  );
		viewMatrix = viewMatrix.rotate( (float)Math.toRadians(rot.z), new Vector3f(0,0,1)  );
		
		Vector3f nPos = new Vector3f(-pos.x, -pos.y, -pos.z);
		viewMatrix = viewMatrix.translate(nPos);
		
		return viewMatrix;
	}
	
	public static float Clamp(float x, float y, float w) {
		return x >= w ? w : x <= y  ? y : x;
	}

}
