package util;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import renderer.Transform;

public class MathLib {
	
	public static Matrix4f createTransformMatrix(Vector3f pos, Vector3f rot, Vector3f scale) {
		Matrix4f matrix = new Matrix4f();
		matrix = matrix.translate(pos);
		matrix = matrix.rotate( (float)Math.toRadians(rot.x), new Vector3f(1,0,0)  );
		matrix = matrix.rotate( (float)Math.toRadians(rot.y), new Vector3f(0,1,0)  );
		matrix = matrix.rotate( (float)Math.toRadians(rot.z), new Vector3f(0,0,1)  );
		matrix = matrix.scale(scale);
		return matrix;
	}
	
	public static Matrix4f createTransformMatrix(Transform transform) {
		return createTransformMatrix(transform.GetPosition(), transform.GetRotation(), transform.GetScale());
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
	
	
	
	
	public static float GetRangePct(float minValue, float maxValue, float value) {
		final float divisor = maxValue - minValue;
		if (divisor == 0)
		{
			return (value >= maxValue) ? 1.f : 0.f;
		}
		return (value - minValue) / divisor;
	}
	
	public static float GetMappedRangeValueUnclamped(float xIn, float yIn, float xOut, float yOut, float value) {
		float f = GetRangeValue(xOut, yOut, GetRangePct(xIn, yIn, value));
		return f;
	}
	
	public static float GetRangeValue(float x, float y, float pct) {
		return Lerp(x, y, pct);
	}
	
	public static float Lerp(float a, float b, float amt) {
		return a+amt * (b-a);
	}

	public static float Clamp(float a, float min, float max) {
		return  a > max ? max : a < min ? min : a;
	}
	
	public static boolean InBounds(float x, float min, float max) {
		return x < max && x > min;
	}
	
}
