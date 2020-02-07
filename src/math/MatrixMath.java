package math;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class MatrixMath {
	
	public static Matrix4f createTransformMatrix(Vector3f pos, Vector3f rot, Vector3f scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.identity();
		matrix.translate(pos);
		matrix.rotate(rot.x, new Vector3f(1,0,0));
		matrix.rotate(rot.y, new Vector3f(0,1,0));
		matrix.rotate(rot.z, new Vector3f(0,0,1));
		matrix.scale(scale);
		return matrix;
	}
	
	public static Matrix4f createViewMatrix(Vector3f pos, Vector3f rot) {
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.identity();
		
		viewMatrix.rotate( (float)Math.toRadians(rot.x), new Vector3f(1,0,0)  );
		viewMatrix.rotate( (float)Math.toRadians(rot.y), new Vector3f(0,1,0)  );
		viewMatrix.rotate( (float)Math.toRadians(rot.z), new Vector3f(0,0,1)  );
		
		Vector3f nPos = new Vector3f(-pos.x, -pos.y, -pos.z);
		viewMatrix.translate(nPos);
		
		return viewMatrix;
	}

}