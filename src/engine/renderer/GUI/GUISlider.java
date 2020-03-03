package engine.renderer.GUI;

import org.joml.Vector4f;

import engine.renderer.Transform;
import engine.util.MathLib;

public abstract class GUISlider extends GUIQuad {

	protected float _value, maxValue, minValue, value;
	
	public GUISlider(String name, Transform transform, String texture, Vector4f color, float maxValue, float minValue) {
		super(name, transform, texture, color);
		this.maxValue = maxValue;
		this.minValue = minValue;
		
	}
	
	public void ValueChanged(float value) {
		this._value = value;
		OnValueChanged(MathLib.GetMappedRangeValueUnclamped(0,1,minValue, maxValue, value));
	}
	
	protected abstract void OnValueChanged(float value);
	
	
	
	
	

}
