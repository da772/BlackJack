package engine;

public abstract class Component {

	
	public Component(String s) {
		this._name = s;
	}
	
	protected String _name;
	
	public String GetName() {
		 return _name;
	};
	
	public abstract void OnBegin();
	
	public abstract void OnEnd();
	
}
