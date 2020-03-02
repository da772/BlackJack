package renderer.GUI;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import renderer.GUIRenderer;
import renderer.Transform;
import util.MathLib;

public class GUISliderBar extends GUIQuad_Draggable {
	
	protected boolean isDragging = false;
	protected Vector2f slideDir;
	protected float start = 0;
	protected float value = 0;
	
	/**
	 * 
	 * @param name - unique identifier
	 * @param transform - quad transfom
	 * @param texture - quad texture
	 * @param quadColor - quad color
	 * @param textOffset - text position offset
	 * @param font - font file path
	 * @param text - text to write
	 * @param textColor - text color
	 * @param textWidth - text width
	 * @param textHeight - text height
	 * @param center - center text?
	 * @param autoSize - auto size width?
	 */
	public GUISliderBar(String name, Transform transform, String texture, Vector4f color, float start, boolean vertical) {
		super(name, transform, texture, color);
		this.slideDir = vertical ? new Vector2f(0f,1f) : new Vector2f(1f, 0f);
		nXPos = this.transform.GetPosition().x;
		nYPos = this.transform.GetPosition().y;
		this.start = start;
		this.value = MathLib.GetMappedRangeValueUnclamped(0,1, -1, 1, start);

	}
	
	protected float nXPos, nYPos;
	
	
	@Override
	public void _Init() {
		super._Init();
		SetStartLocation();
	}
	
	@Override
	protected void OnMouseExit() {
		DeselectGUI();
	}
	
	protected void SetStartLocation() {
		if (this.parent != null) {
			float _start = MathLib.GetMappedRangeValueUnclamped(0,1, -1, 1, start);
			Vector3f pos = new Vector3f(((this.parent.GetScale().x-this.GetScale().x)*_start)*this.slideDir.x+this.GetRelativePosition().x,
					((this.parent.GetScale().y-this.GetScale().y)*_start)*this.slideDir.y+this.GetRelativePosition().y,this.GetRelativePosition().z );
			SetRelativePosition(pos);
			SetValue();
		}
	}
	
	protected void SetValue() {
		if (parent != null) {
			if (this.slideDir.x > 0) {
				value = 1-MathLib.GetMappedRangeValueUnclamped(-(this.parent.GetScale().x-this.GetScale().x), this.parent.GetScale().x-this.GetScale().x, 0, 1, 
						this.GetRealPosition().x-this.parent.GetPosition().x);
			} else {
				value = MathLib.GetMappedRangeValueUnclamped(-(this.parent.GetScale().y-this.GetScale().y), this.parent.GetScale().y-this.GetScale().y, 0, 1,
						this.GetRealPosition().y-this.parent.GetPosition().y);
			}
			OnValueChange();
		}
	}
	
	public float GetValue() {
		return (float) (Math.round(value * 100.0) / 100.0);
	}
	
	protected void OnValueChange() {
		if (this.parent != null) {
			if (this.parent instanceof GUISlider) {
				((GUISlider)this.parent).ValueChanged(GetValue());
			}
		}
	}
	
	@Override
	public void Drag(float x, float y) {
		Vector3f newPos = new Vector3f( this.GetRelativePosition().x + (( ((x-dragPos.x)/GUIRenderer.GetWidth())*2f )*this.slideDir.x),
				this.GetRelativePosition().y - (( ((y-dragPos.y)/GUIRenderer.GetHeight()*2f) *this.slideDir.y) ),
				this.GetRelativePosition().z);
		if (this.parent != null) {
			 if (this.slideDir.x > 0 && newPos.x-.001f <= (this.parent.GetScale().x-this.GetScale().x) && 
					 newPos.x+.001f >= -(this.parent.GetScale().x-this.GetScale().x)
					){
				 SetRelativePosition(newPos);
				 SetValue();
			 } else if ( newPos.y-.001f <= (this.parent.GetScale().y-this.GetScale().x) && 
						 newPos.y+.001f >= -(this.parent.GetScale().y-this.GetScale().x)) {
				 SetRelativePosition(newPos);
				 SetValue();
			 }
		}
		dragPos.x = x;
		dragPos.y = y;
	}
	
	
	
}
