package application;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.Actor;
import engine.Application;
import engine.SceneManager;
import engine.audio.AudioManager;

import engine.renderer.Transform;
import engine.renderer.GUI.GUIButton;
import engine.renderer.GUI.GUIQuad;

import engine.renderer.GUI.GUIText;


public class GamePauseMenu extends Actor {

	private static final Vector4f titleTextColor = ColorPalette.LightGreenBlue;
	private static final Vector4f buttonBackColor = new Vector4f(0f);
	private static final Vector4f buttonBackSelectedColor = ColorPalette.Pico8Pink;
	
	private static final Vector4f mainTitleTextColor = ColorPalette.LightGreenBlue;
	
	public static void Show() {
		if (menu == null) {
			Application.GetApp().SetPaused(true);
			menu = new GamePauseMenu("PauseMenu");
		} 
	}
	
	public static void Hide() {
		if (menu != null) {
			if (GameSettingsMenu.IsActive()) GameSettingsMenu.Hide();
			Actor.Remove(menu._name, menu.scene);
			menu = null;
			Application.GetApp().SetPaused(false);
		}
	}
	
	public static boolean IsActive() {
		return menu != null;
	}
	
	private static GamePauseMenu menu;
	
	private GamePauseMenu(String name) {
		super(name);
	}

	
	@Override
	public void OnBegin() {
		// Create background quad to contain menu
		GUIQuad backGround = new GUIQuad(
				"Background",
				new Transform(new Vector3f(0f,0f,500f),new Vector3f(0f), new Vector3f(1f)),
				"Images/blankTexture.png",
				new Vector4f(.0f,0f,0f,.9f),
				new Vector2f(1f)
				);
		
		// Create header text
		GUIText mainText = new GUIText("mainText",
				new Transform(new Vector3f(0f,.8f,.1f)),
				"Fonts/BebasNeue",
				"Pause Menu",
				mainTitleTextColor,
				1f,
				3f,
				true
				);
		
		// Create button container
		GUIQuad buttonArea = new GUIQuad(
				"buttonArea",
				new Transform(new Vector3f(0f,0f,.1f),new Vector3f(0f), new Vector3f(.75f,.75f,1f)),
				"Images/blankTexture.png",
				new Vector4f(1f,1f,1f,0f),
				new Vector2f(1f)
				);
		

			
		GUIQuad backButton = CreateBackButton();
		GUIQuad settingButton = CreateSettingButton();
		GUIQuad saveGameButton = SaveGameButton();	
		GUIQuad quitGameButton = QuitGameButton();
		
					
		AddComponent(backGround.AddChild(mainText).
				AddChild(buttonArea.AddChild(backButton).AddChild(settingButton).AddChild(saveGameButton).AddChild(quitGameButton)));
						
	};
					
	@Override
	public void OnEnd() {
		menu = null;
		
	};
	
	
	private static GUIQuad CreateBackButton() {
		return (GUIQuad) new GUIQuad("backButtonBack",
				new Transform(new Vector3f(0f, .4f, .1f),new Vector3f(0f), new Vector3f(.75f,.08f,1f)),
				"Images/blankTexture.png",
				new Vector4f(1f,0f,0f,0f),
				new Vector2f(1f)).AddChild(new GUIButton("backButton", new Transform(new Vector3f(0f,0f, .1f), new Vector3f(0f), new Vector3f(.15f, .08f, 1f)),
						"Images/blankTexture.png", "Images/blankTexture.png", buttonBackColor) {

			@Override
			protected void OnSelect() {
				AudioManager.CreateAudioSource("gamePauseBack", "Audio/buttonMouseOver.wav", "sfx", 1f, 1f, false, true);
				AudioManager.PlaySource("gamePauseBack");
				SetColor(buttonBackSelectedColor);
			}

			@Override
			protected void OnMousePressed() {
				// TODO Auto-generated method stub
				
			}

			@Override
			protected void OnMouseReleased() {
				Hide();
			}

			@Override
			public void OnDeselect() {
				// TODO Auto-generated method stub
				SetColor(buttonBackColor);
			}
			
			}.AddChild(new GUIText("backButtonText",new Transform(new Vector3f(0f,0f,.1f)),"Fonts/BebasNeue","Resume",
				titleTextColor,1f,2.5f,true
				)));
	}
	
	private static GUIQuad CreateSettingButton() {
		return (GUIQuad) new GUIQuad("settingButtonBack",
				new Transform(new Vector3f(0f, .2f, .1f),new Vector3f(0f), new Vector3f(.75f,.08f,1f)),
				"Images/blankTexture.png",
				new Vector4f(1f,0f,0f,0f),
				new Vector2f(1f)).AddChild(new GUIButton("backButton", new Transform(new Vector3f(0f,0f, .1f), new Vector3f(0f), new Vector3f(.15f, .08f, 1f)),
						"Images/blankTexture.png", "Images/blankTexture.png", buttonBackColor) {

			@Override
			protected void OnSelect() {
				// TODO Auto-generated method stub
				AudioManager.CreateAudioSource("gamePauseSetting", "Audio/buttonMouseOver.wav", "sfx", 1f, 1f, false, true);
				AudioManager.PlaySource("gamePauseSetting");
				SetColor(buttonBackSelectedColor);
			}

			@Override
			protected void OnMousePressed() {
				// TODO Auto-generated method stub
				
			}

			@Override
			protected void OnMouseReleased() {
				if (!GameSettingsMenu.IsActive()) GameSettingsMenu.Show(); else GameSettingsMenu.Hide();
			}

			@Override
			public void OnDeselect() {
				// TODO Auto-generated method stub
				SetColor(buttonBackColor);
			}
			
			}.AddChild(new GUIText("backButtonText",new Transform(new Vector3f(0f,0f,.1f)),"Fonts/BebasNeue","Settings",
				titleTextColor,1f,2.5f,true
				)));
	}

	
	private static GUIQuad SaveGameButton() {
		return (GUIQuad) new GUIQuad("saveButtonBack",
				new Transform(new Vector3f(0f, .0f, .1f),new Vector3f(0f), new Vector3f(.75f,.08f,1f)),
				"Images/blankTexture.png",
				new Vector4f(1f,0f,0f,0f),
				new Vector2f(1f)).AddChild(new GUIButton("backButton", new Transform(new Vector3f(0f,0f, .1f), new Vector3f(0f), new Vector3f(.15f, .08f, 1f)),
						"Images/blankTexture.png", "Images/blankTexture.png", buttonBackColor) {

			@Override
			protected void OnSelect() {
				// TODO Auto-generated method stub
				AudioManager.CreateAudioSource("gamePauseSetting", "Audio/buttonMouseOver.wav", "sfx", 1f, 1f, false, true);
				AudioManager.PlaySource("gamePauseSetting");
				SetColor(buttonBackSelectedColor);
			}

			@Override
			protected void OnMousePressed() {
				// TODO Auto-generated method stub
				
			}

			@Override
			protected void OnMouseReleased() {
				// TODO implement game save!
			}

			@Override
			public void OnDeselect() {
				// TODO Auto-generated method stub
				SetColor(buttonBackColor);
			}
			
			}.AddChild(new GUIText("backButtonText",new Transform(new Vector3f(0f,0f,.1f)),"Fonts/BebasNeue","Save",
				titleTextColor,1f,2.5f,true
				)));
	}
	
	private static GUIQuad QuitGameButton() {
		return (GUIQuad) new GUIQuad("quitButtonBack",
				new Transform(new Vector3f(0f, -.2f, .1f),new Vector3f(0f), new Vector3f(.75f,.08f,1f)),
				"Images/blankTexture.png",
				new Vector4f(1f,0f,0f,0f),
				new Vector2f(1f)).AddChild(new GUIButton("backButton", new Transform(new Vector3f(0f,0f, .1f), new Vector3f(0f), new Vector3f(.15f, .08f, 1f)),
						"Images/blankTexture.png", "Images/blankTexture.png", buttonBackColor) {

			@Override
			protected void OnSelect() {
				AudioManager.CreateAudioSource("gamePauseQuit", "Audio/buttonMouseOver.wav", "sfx", 1f, 1f, false, true);
				AudioManager.PlaySource("gamePauseQuit");
				SetColor(buttonBackSelectedColor);
			}

			@Override
			protected void OnMousePressed() {
				// TODO Auto-generated method stub
				
			}

			@Override
			protected void OnMouseReleased() {
				Hide();
				SceneManager.SetCurrentScene("mainMenu");
			}

			@Override
			public void OnDeselect() {
				// TODO Auto-generated method stub
				SetColor(buttonBackColor);
			}
			
			}.AddChild(new GUIText("backButtonText",new Transform(new Vector3f(0f,0f,.1f)),"Fonts/BebasNeue","Quit",
				titleTextColor,1f,2.5f,true
				)));
	}

	


	
	
}
