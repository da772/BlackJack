package engine;

public class Events {

	enum EventType {
		None, WindowClose, WindowResize, WindowFocus, WindowLostFocus, WindowMoved,
		AppUpdate, AppTick, AppRender,
		Key, KeyPressed, KeyReleased, KeyTyped,
		MouseButton, MouseButtonPressed, MouseButtonReleased, MouseMoved, MouseScrolled
	}
	
	enum EventCategory {
		None(0x00), Application(0x01), Input(0x02), Keyboard(0x04), Mouse(0x08), Window(0x10);
		private final int bit;
		EventCategory(int bit) { this.bit = bit; }
		public int getValue() { return bit; }
		
	}
	
	public static abstract class Event {
		public boolean handled = false;
		public abstract EventType GetEventType();
		public abstract String GetName();
		public abstract int GetCategoryFlags();
		public abstract String toString();
		
		protected abstract EventType GenericType();
		
		public boolean IsInCategory(EventCategory e) {
			return (GetCategoryFlags() & e.getValue()) > 0 ? true : false ;
		}
		
	}
	
	public static abstract class EventFunction<T extends Event> {
		public EventFunction(T c) {
			this.clss = c;
		}
	
		
		final public T clss;
		public abstract boolean run(T t);
	}
	
	public static class EventDispatcher<T extends Event> { 
		Event m_Event;
		
		EventDispatcher(Event event) {
			m_Event = event;
		}
		
		@SuppressWarnings({ "unchecked"})
		public <R extends Event> boolean Dispatch (EventType type, EventFunction<R> func) {
			try {
			if (m_Event.GenericType() == type) {
				m_Event.handled = func.run((R)m_Event);
				return true;
			}
			return false;
			} catch (Exception e) {
				return false;
			}

		}
		
	}
	
	public static class MouseMovedEvent extends Event {
		private float m_MouseX, m_MouseY;
		public int Type = EventCategory.Input.getValue() | EventCategory.Keyboard.getValue();
		
		public MouseMovedEvent(final float mouseX, final float mouseY) {
			this.m_MouseX = mouseX;
			this.m_MouseY = mouseY;
		}
		
		public final float GetMouseX() {
			return m_MouseX;
		};
		
		public final float GetMouseY() {
			return m_MouseY;
		};
		
		public EventType GetEventType() {
			return EventType.MouseMoved;
		};

		public static EventType GetGenericType() {
			return EventType.MouseMoved;
		}
		
		@Override
		protected EventType GenericType() {
			return EventType.MouseMoved;
		}

		@Override
		public String GetName() {
			return "MouseMoved";
		};

		@Override
		public int GetCategoryFlags() {
			return Type;
		};

		@Override
		public String toString() {
			return "MouseMovedEvent: " + m_MouseX + ", " + m_MouseY;
		};
		
	}
	
	
	public static class WindowResizedEvent extends Event {
		private float m_Width, m_Height;
		public int Type = EventCategory.Application.getValue() | EventCategory.Window.getValue();
		
		public  WindowResizedEvent(final int width, final int height) {
			this.m_Width = width;
			this.m_Height = height;
		}
		
		public final float GetWidth() {
			return m_Width;
		};
		
		public final float GetHeight() {
			return m_Height;
		};
		
		public EventType GetEventType() {
			return EventType.WindowResize;
		};
		
		public static EventType GetGenericType(){ return EventType.WindowResize; }
		
		@Override
		protected EventType GenericType(){ return EventType.WindowResize; }
		
		@Override
		public String GetName() {
			return "WindowResized";
		};

		@Override
		public int GetCategoryFlags() {
			return Type;
		};

		@Override
		public String toString() {
			return "WindowResized: " + m_Width + ", " + m_Height;
		};
		
	}
	
	public static class MouseScrolledEvent extends Event {
		private float m_ScrollX, m_ScrollY;
		
		public final int Type = EventCategory.Mouse.getValue() | EventCategory.Input.getValue();
		
		public MouseScrolledEvent(final float scrollX, final float scrollY) {
			this.m_ScrollX = scrollX;
			this.m_ScrollY = scrollY;
		}
		
		public final float GetScrollX() {
			return m_ScrollX;
		};
		
		public final float GetScrollY() {
			return m_ScrollY;
		};
		
		public EventType GetEventType() {
			return EventType.MouseScrolled;
		};
		
		public static EventType GetGenericType(){ return EventType.MouseScrolled; }
		
		@Override
		protected EventType GenericType(){ return EventType.MouseScrolled; }

		@Override
		public String GetName() {
			return "MouseScrolled";
		};

		@Override
		public int GetCategoryFlags() {
			return Type;
		};

		@Override
		public String toString() {
			return "MouseScrolledEvent: " + m_ScrollX + ", " + m_ScrollY;
		};
		
	}
	
	public static class WindowClosedEvent extends Event {
		private float m_ScrollX, m_ScrollY;
		
		public final int Type = EventCategory.Application.getValue() | EventCategory.Window.getValue();
		
		public WindowClosedEvent() {
			
		}
		
		public final float GetScrollX() {
			return m_ScrollX;
		};
		
		public final float GetScrollY() {
			return m_ScrollY;
		};
		
		public EventType GetEventType() {
			return EventType.WindowClose;
		};
		
		public static EventType GetGenericType(){ return EventType.MouseScrolled; }
		
		@Override
		protected EventType GenericType(){ return EventType.MouseScrolled; }

		@Override
		public String GetName() {
			return "WindowClosed";
		};

		@Override
		public int GetCategoryFlags() {
			return Type;
		};

		@Override
		public String toString() {
			return "WindowClosedEvent";
		};
		
	}
	

	public static abstract class MouseButtonEvent extends Event {
		protected int m_Keycode;
		public final int Type = EventCategory.Mouse.getValue() | EventCategory.Input.getValue();
		
		public final int GetKeyCode() { return m_Keycode; };
		
		protected MouseButtonEvent(final int keycode) {
			m_Keycode = keycode;
		}
		
		abstract public EventType GetEventType();
		public static EventType GetGenericType(){ return EventType.MouseButton; }
		@Override
		protected EventType GenericType(){ return EventType.MouseButton; }
	
		@Override
		abstract public String GetName();

		@Override
		public int GetCategoryFlags() {
			return Type;
		}

		@Override
		abstract public String toString();
		
	}
	
	public static class MouseButtonPressedEvent extends MouseButtonEvent {

		private int m_RepeatCount;
		
		protected MouseButtonPressedEvent(int keycode, int repeatCount) {
			super(keycode);
			m_RepeatCount = repeatCount;
			
		}
		
		public int GetRepeatCount() {
			return m_RepeatCount;
		}

		@Override
		public EventType GetEventType() {
			return EventType.MouseButtonPressed;
		}

		@Override
		public String GetName() {
			return "MouseButtonPressed";
		}

		@Override
		public String toString() {
			return "MouseButtonPressedEvent: " + m_Keycode + " (" + m_RepeatCount + " repeat).";
		}
		
	}
	
	
	public static class MouseButtonReleasedEvent extends MouseButtonEvent {
		
		protected MouseButtonReleasedEvent(int keycode) {
			super(keycode);
		}

		@Override
		public EventType GetEventType() {
			return EventType.MouseButtonReleased;
		}

		@Override
		public String GetName() {
			return "MouseButtonReleased";
		}

		@Override
		public String toString() {
			return "MouseButtonReleasedEvent: " + m_Keycode + ".";
		}
		
	}
	
	
	public static abstract class KeyEvent extends Event {
		protected int m_Keycode;
		public final int Type = EventCategory.Keyboard.getValue() | EventCategory.Input.getValue();
		
		public final int GetKeyCode() { return m_Keycode; };
		
		protected KeyEvent(final int keycode) {
			m_Keycode = keycode;
		}
		
		abstract public EventType GetEventType();
		public static EventType GetGenericType(){ return EventType.Key; }
		
		@Override
		protected EventType GenericType(){ return EventType.Key; }
		
		@Override
		abstract public String GetName();
		

		@Override
		public int GetCategoryFlags() {
			return Type;
		}

		@Override
		abstract public String toString();
		
	}
	
	public static class KeyPressedEvent extends KeyEvent {

		private int m_RepeatCount;
		
		protected KeyPressedEvent(int keycode, int repeatCount) {
			super(keycode);
			m_RepeatCount = repeatCount;
			
		}
		
		public int GetRepeatCount() {
			return m_RepeatCount;
		}

		@Override
		public EventType GetEventType() {
			return EventType.KeyPressed;
		}

		@Override
		public String GetName() {
			return "KeyPressed";
		}

		@Override
		public String toString() {
			return "KeyPressed Event: " + m_Keycode + " (" + m_RepeatCount + " repeat).";
		}
		
	}
	
	public static class KeyReleasedEvent extends KeyEvent {
		
		protected KeyReleasedEvent(int keycode) {
			super(keycode);
		}

		@Override
		public EventType GetEventType() {
			return EventType.KeyReleased;
		}

		@Override
		public String GetName() {
			return "KeyReleased";
		}

		@Override
		public String toString() {
			return "KeyReleased Event: " + m_Keycode + ".";
		}
		
	}
	
public static class KeyTypedEvent extends KeyEvent {
	
		public final int Type = EventCategory.Keyboard.getValue() | EventCategory.Input.getValue();
	
		protected KeyTypedEvent(int keycode) {
			super(keycode);
		}

		@Override
		public EventType GetEventType() {
			return EventType.KeyTyped;
		}

		@Override
		public String GetName() {
			return "KeyTyped";
		}

		@Override
		public String toString() {
			return "KeyTyped Event: " + m_Keycode + ".";
		}
		
	}
	
	
	
}
