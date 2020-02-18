package engine;

public abstract class Component {

	/**
	 * 
	 * @param s - unique identifier
	 */
	public Component(String s) {
		this._name = s;
	}
	
	protected String _name;
	
	/**
	 * 
	 * @return String - unique identifier
	 */
	public String GetName() {
		 return _name;
	};
	
	public abstract void OnBegin();
	
	public abstract void OnEnd();
	
}
