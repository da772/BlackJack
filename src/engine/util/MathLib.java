package engine.util;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.renderer.Transform;

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
	
	public static Vector3f DirectionSign(Vector3f start, Vector3f end) {
		float dx = Math.signum(end.x-start.x);
		float dy = Math.signum(end.y-start.y);
		float dz = Math.signum(end.z-start.z);
		
		return new Vector3f(dx,dy,dz);
	}
	
	public static boolean TransformEquals(Transform a, Transform b, float threshold) {
		return VectorEquals(a.GetPosition(), b.GetPosition(), threshold) &&
				VectorEquals(a.GetRotation(), b.GetRotation(), threshold) && 
				VectorEquals(a.GetScale(), b.GetScale(), threshold);
	}
	
	public static boolean VectorEquals(Vector3f a, Vector3f b, float threshold) {
		return a.distance(b) - threshold <= 0;
	}
	
	public static boolean VectorEquals(Vector4f a, Vector4f b, float threshold) {
		return a.distance(b) - threshold <= 0;
	}
	
	public static Vector3f MoveTowards(Vector3f current ,Vector3f target, float maxDistanceDelta) {
		float dX = target.x - current.x;
		float dY = target.y - current.y;
		float dZ = target.z - current.z;
		float sqdist = dX *dX + dY*dY+dZ*dZ;
		if (sqdist == 0 || sqdist <= maxDistanceDelta*maxDistanceDelta) {
			return target;
		}
		float dst = (float)Math.sqrt(sqdist);
		return new Vector3f(current.x+dX/dst*maxDistanceDelta, current.y+dY/dst*maxDistanceDelta, current.z+dZ/dst*maxDistanceDelta);
	}

	public static float GetRangePct(float minValue, float maxValue, float value) {
		final float divisor = maxValue - minValue;
		if (divisor == 0)
		{
			return (value >= maxValue) ? 1.f : 0.f;
		}
		return (value - minValue) / divisor;
	}
	
	public static float GetMappedRangeValue(float xIn, float yIn, float xOut, float yOut, float value) {
		float f = GetRangeValue(xOut, yOut, GetRangePct(xIn, yIn, value));
		return f;
	}
	
	public static float GetRangeValue(float x, float y, float pct) {
		return Lerp(x, y, pct);
	}
	
	public static Vector3f Lerp(Vector3f a, Vector3f b, float amt) {
		return new Vector3f( Lerp(a.x,b.x,amt), Lerp(a.y,b.y,amt), Lerp(a.z,b.z,amt));
	}
	
	public static Vector4f Lerp(Vector4f a, Vector4f b, float amt) {
		return new Vector4f( Lerp(a.x,b.x,amt), Lerp(a.y,b.y,amt), Lerp(a.z,b.z,amt), Lerp(a.w, b.w, amt) );
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
