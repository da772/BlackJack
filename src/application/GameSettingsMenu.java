package application;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.Actor;
import engine.Application;
import engine.audio.AudioManager;
import engine.renderer.Renderer;
import engine.renderer.Transform;
import engine.renderer.GUI.GUIButton;
import engine.renderer.GUI.GUIQuad;
import engine.renderer.GUI.GUISlider;
import engine.renderer.GUI.GUISliderBar;
import engine.renderer.GUI.GUIText;
import engine.util.MathLib;

public class GameSettingsMenu extends Actor {

	
	private static final Vector4f titleTextColor = ColorPalette.CityBreeze;
	
	private static final Vector4f mainTitleTextColor = ColorPalette.CityBreeze;
	
	public static void Show() {
		if (menu == null) {
			menu = new GameSettingsMenu("SettingMenu");
		} 
	}
	
	public static void Hide() {
		if (menu != null) {
			Actor.Remove(menu._name, menu.scene);
			menu = null;
		}
	}
	
	public static boolean IsActive() {
		return menu != null;
	}
	
	private static GameSettingsMenu menu;
	
	private GameSettingsMenu(String name) {
		super(name);
	}

	

	@Override
	public void OnBegin() {
		// Create background quad to contain menu
		GUIQuad backGround = new GUIQuad(
				"Background",
				new Transform(new Vector3f(0f,0f,2000f),new Vector3f(0f), new Vector3f(1f)),
				"Images/blankTexture.png",
				new Vector4f(0.176f,.203f,0.211f,1f),
				new Vector2f(1f)
				);
		
		// Create header text
		GUIText mainText = new GUIText("mainText",
				new Transform(new Vector3f(0f,.8f,.1f)),
				"Fonts/BebasNeue",
				"Game Settings",
				mainTitleTextColor,
				1f,
				3f,
				true
				);
		
		// Create button container
		GUIQuad buttonArea = new GUIQuad(
				"buttonArea",
				new Transform(new Vector3f(0f,-.2f,.1f),new Vector3f(0f), new Vector3f(.75f,.75f,1f)),
				"Images/blankTexture.png",
				new Vector4f(1f,1f,1f,0f),
				new Vector2f(1f)
				);
		
		// Create Full
		GUIQuad fullScreen = CreateFullScreenButton();
		
		GUIQuad musicVolume = CreateMusicVolumeButton();
		
		GUIQuad soundVolume = CreateSoundVolumeButton();
			
		GUIQuad vsync = CreateVSyncButton();
			
		GUIQuad fpsCap = CreateFpsButton();
			
			
		GUIQuad renderScale = CreateRenderScaleButton();
			
		GUIQuad backButton = CreateBackButton();
						
					
		AddComponent(backGround.AddChild(mainText).
				AddChild(buttonArea.AddChild(fullScreen).AddChild(musicVolume).AddChild(soundVolume).AddChild(fpsCap).AddChild(vsync).AddChild(renderScale).AddChild(backButton)));
						
	};
					
	@Override
	public void OnEnd() {
		menu = null;
	};
	
	
	private static GUIQuad CreateBackButton() {
		return (GUIQuad) new GUIQuad("backButtonBack",
				new Transform(new Vector3f(0f,-.4f, .1f),new Vector3f(0f), new Vector3f(.75f,.08f,1f)),
				"Images/blankTexture.png",
				new Vector4f(1f,0f,0f,0f),
				new Vector2f(1f)).AddChild(new GUIButton("backButton", new Transform(new Vector3f(0f,0f, .1f), new Vector3f(0f), new Vector3f(.15f, .08f, 1f)),
						"Images/blankTexture.png", "Images/blankTexture.png", ColorPalette.DraculaOrchid) {

			@Override
			protected void OnSelect() {
				// TODO Auto-generated method stub
				SetColor(ColorPalette.AmericanRiver);
				AudioManager.CreateAudioSource("gameSettingsBack", "Audio/buttonMouseOver.wav", "sfx", 1f, 1f, false, true);
				AudioManager.PlaySource("gameSettingsBack");
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
				SetColor(ColorPalette.DraculaOrchid);
			}
			
			}.AddChild(new GUIText("backButtonText",new Transform(new Vector3f(0f,0f,.1f)),"Fonts/BebasNeue","Back",
				titleTextColor,1f,2.5f,true
				)));
	}

	private static GUIQuad CreateRenderScaleButton() {
		// TODO Auto-generated method stub
		return (GUIQuad) new GUIQuad("renderScale",
				new Transform(new Vector3f(0f,-.2f, .1f),new Vector3f(0f), new Vector3f(.75f,.08f,1f)),
				"Images/blankTexture.png",
				new Vector4f(1f,0f,0f,0f),
				new Vector2f(1f)).AddChild(
				// Create slider
				new GUISlider(
								"slider",
				new Transform(
						new Vector3f(.25f,0f,.01f),
						new Vector3f(0f),
						new Vector3f(.20f,.02f,1f)
						),
				"Images/blankTexture.png", // Texture of the hud
				new Vector4f(223/255f, 230/255f, 233/255f,1.0f), 0f,200f){
				
					private int _value = 0;
					@Override
					protected void OnValueChangedRaw(float value) {
						if (_value != (Math.round((float)value/25))*25) {
							_value = (Math.round((float)value/25))*25;
							if (_value < 25) {
								_value = 25;
							}
							if (this.parent != null) {
							((GUIText)this.parent.GetChild("renderScaleValue")).SetText( _value + "%");
						}
					}
				}
				@Override
				public void OnDragComplete() {
					Renderer.SetRenderScale(_value/100f);
					float __value = _value > 25 ? _value : 0f;
					((GUISliderBar)GetChild("sliderBar")).SetSliderLocation(MathLib.GetMappedRangeValueUnclamped(0, 200, 0, 1, __value));
				}
				@Override
				protected void OnValueChanged(float value) {
					
				}
				// Create sliderbar
				}.AddChild(new GUISliderBar(
					"sliderBar",
				new Transform(
						new Vector3f(0f,0f,.01f),
						new Vector3f(0f),
						new Vector3f(.02f,.018f,1f)
						),
				"Images/blankTexture.png", // Texture of the hud
				new Vector4f(99/255f, 110/255f, 114/255f,1.0f), MathLib.GetMappedRangeValueUnclamped(0f, 200f, 0f, 1f, (Renderer.GetRenderScale()*100f)),false)).
				AddChild(new GUIText("musicVolumeSlider",new Transform(new Vector3f(-.5f,0f,.1f)),"Fonts/BebasNeue","Render Scale",
											titleTextColor,1f,2.5f,true
											))
				// Create text
				).AddChild(new GUIText("renderScaleValue",new Transform(new Vector3f(.55f,0f,.1f)),"Fonts/BebasNeue","",
				titleTextColor,1f,2.5f,true
				));
	}

	private static GUIQuad CreateFpsButton() {
		return (GUIQuad) new GUIQuad("fpsCapBack",
				new Transform(new Vector3f(0f,.0f, .1f),new Vector3f(0f), new Vector3f(.75f,.08f,1f)),
				"Images/blankTexture.png",
				new Vector4f(1f,0f,0f,0f),
				new Vector2f(1f)).AddChild(
						new GUISlider(
								"slider",
				new Transform(
						new Vector3f(.25f,0f,.01f),
						new Vector3f(0f),
						new Vector3f(.20f,.02f,1f)
						),
				"Images/blankTexture.png", // Texture of the hud
				new Vector4f(223/255f, 230/255f, 233/255f,1.0f), 30f,256f){
				
					private int _value = 0;
					@Override
					protected void OnValueChangedRaw(float value) {
						if (_value != Math.round(value)) {
							_value = Math.round(value) ;
							if (this.parent != null) {
								if (_value >= 256) {
									//Application.SetFPSCap(5000);
									((GUIText)this.parent.GetChild("fpsValue")).SetText("Infinite");
								} else {
									//Application.SetFPSCap(_value);
									((GUIText)this.parent.GetChild("fpsValue")).SetText( _value + " fps");
								}
						}
					}
				}
				@Override
				public void OnDragComplete() {
					if (_value >= 256) {
						Application.SetFPSCap(5000);
					} else {
						Application.SetFPSCap(_value);
					}
				}
				@Override
				protected void OnValueChanged(float value) {
					
				}
				
				// Create SliderBar
				}.AddChild(new GUISliderBar(
					"sliderBar",
				new Transform(
						new Vector3f(0f,0f,.01f),
						new Vector3f(0f),
						new Vector3f(.02f,.018f,1f)
						),
				"Images/blankTexture.png", // Texture of the hud
				new Vector4f(99/255f, 110/255f, 114/255f,1.0f), Application.GetFPSCap() > 256 ? 1f : MathLib.GetMappedRangeValueUnclamped(30f, 255f, 0, 255/256f, Application.GetFPSCap()),false)).
				AddChild(new GUIText("musicVolumeSlider",new Transform(new Vector3f(-.5f,0f,.1f)),"Fonts/BebasNeue","FPS Cap",
											titleTextColor,1f,2.5f,true
											))
				// Create Text		
				).AddChild(new GUIText("fpsValue",new Transform(new Vector3f(.55f,0f,.1f)),"Fonts/BebasNeue","",
				titleTextColor,1f,2.5f,true
				));
	}

	private static GUIQuad CreateVSyncButton() {
		return (GUIQuad) new GUIQuad("vsyncBack",
				new Transform(new Vector3f(0f,.2f, .1f),new Vector3f(0f), new Vector3f(.75f,.08f,1f)),"Images/blankTexture.png",
				new Vector4f(1f,0f,0f,0f), new Vector2f(1f)).AddChild(
				
				//Create Button		
				new GUIButton("vsyncButton",  new Transform(new Vector3f(.25f, 0f,.1f), new Vector3f(0f), new Vector3f(.02f,.035f, 1f)),
				Application.IsVsync() ? "Images/Buttons/checkedBox.png" : "Images/Buttons/checkBox.png",
				!Application.IsVsync() ? "Images/Buttons/checkedBox.png" : "Images/Buttons/checkBox.png", new Vector4f(1f)
				) {
				
					private boolean startFull = Application.IsVsync();
					
					@Override
					protected void OnSelect() {
						// TODO Auto-generated method stub
						SetColor(178/255f, 190/255f, 195/255f,1.0f);
					}
				
					@Override
					protected void OnMousePressed() {
						// TODO Auto-generated method stub
						
					}
					@Override
					protected void OnMouseReleased() {
						SetButtonTexture(startFull ? Application.IsVsync() : !Application.IsVsync());
						Application.SetVSync(!Application.IsVsync());
					}
				
					@Override
					public void OnDeselect() {
						// TODO Auto-generated method stub
						SetColor(223/255f, 230/255f, 233/255f,1.0f);
					}
				// Create Text		
				}.AddChild(new GUIText("fullScreenText",new Transform(new Vector3f(-.5f,0f,.1f)),"Fonts/BebasNeue","VSync",
								titleTextColor,1f,2.5f,true
				)));
	}

	private static GUIQuad CreateSoundVolumeButton() {
		return  (GUIQuad) new GUIQuad("soundEffectVolumeBack",
				new Transform(new Vector3f(0f,.4f, .1f),new Vector3f(0f), new Vector3f(.75f,.08f,1f)),
				"Images/blankTexture.png",
				new Vector4f(1f,0f,0f,0f),
				new Vector2f(1f)).AddChild(
						new GUISlider(
						"slider",
						new Transform(
								new Vector3f(.25f,0f,.01f),
								new Vector3f(0f),
								new Vector3f(.20f,.02f,1f)
								),
						"Images/blankTexture.png", // Texture of the hud
						new Vector4f(223/255f, 230/255f, 233/255f,1.0f),0f,100f){

							private float _value = 0;
							@Override
							protected void OnValueChanged(float value) {
								if (_value != value) {
									_value = value;
									AudioManager.SetCategoryVolume("sfx", Math.round(_value)/100f);
									if (this.parent != null) {
										((GUIText)this.parent.GetChild("soundVolumeValue")).SetText(Math.round(_value) + "%");
									}
								}
							}
							// Create SLider BAr
							}.AddChild(new GUISliderBar(
								"sliderBar",
								new Transform(
										new Vector3f(0f,0f,.01f),
										new Vector3f(0f),
										new Vector3f(.02f,.018f,1f)
										),
								"Images/blankTexture.png", // Texture of the hud
								new Vector4f(99/255f, 110/255f, 114/255f,1.0f), AudioManager.GetCategoryVolume("sfx"),false)).
								AddChild(new GUIText("musicVolumeSlider",new Transform(new Vector3f(-.5f,0f,.1f)),"Fonts/BebasNeue","Sfx Volume",
										titleTextColor,1f,2.5f,true
										))
					// Create text
					).AddChild(new GUIText("soundVolumeValue",new Transform(new Vector3f(.55f,0f,.1f)),"Fonts/BebasNeue",  Integer.toString(Math.round(AudioManager.GetCategoryVolume("sfx")*100f)) + "%",
					titleTextColor,1f,2.5f,true
					));
	}

	private static GUIQuad CreateMusicVolumeButton() {
		return (GUIQuad) new GUIQuad("musicVolumeBack",
				new Transform(new Vector3f(0f,.6f, .1f),new Vector3f(0f), new Vector3f(.75f,.08f,1f)),
				"Images/blankTexture.png",
				new Vector4f(1f,0f,0f,0f),
				new Vector2f(1f)).AddChild(
						new GUISlider(
						"slider",
						new Transform(
								new Vector3f(.25f,0f,.01f),
								new Vector3f(0f),
								new Vector3f(.20f,.02f,1f)
								),
						"Images/blankTexture.png", // Texture of the hud
						new Vector4f(223/255f, 230/255f, 233/255f,1.0f),0f,100f){

							private float _value = 0;
							@Override
							protected void OnValueChanged(float value) {
								if (_value != value) {
									_value = value;
									AudioManager.SetCategoryVolume("music", Math.round(_value)/100f);
									if (this.parent != null) {
										((GUIText)this.parent.GetChild("musicVolumeValue")).SetText(Math.round(_value) + "%");
									}
								}
							}
							// Create Slider Bar
							}.AddChild(new GUISliderBar(
								"sliderBar",
								new Transform(
										new Vector3f(0f,0f,.01f),
										new Vector3f(0f),
										new Vector3f(.02f,.018f,1f)
										),
								"Images/blankTexture.png", // Texture of the hud
								new Vector4f(99/255f, 110/255f, 114/255f,1.0f),AudioManager.GetCategoryVolume("music"),false)).
								AddChild(new GUIText("musicVolumeSlider",new Transform(new Vector3f(-.5f,0f,.1f)),"Fonts/BebasNeue","Music Volume",
										titleTextColor,1f,2.5f,true
										))
				).AddChild(new GUIText("musicVolumeValue",new Transform(new Vector3f(.55f,0f,.1f)),"Fonts/BebasNeue","",
						titleTextColor,1f,2.5f,true
						));
	}

	private static GUIQuad CreateFullScreenButton() {
		return (GUIQuad) new GUIQuad("fullScreenBack",
				new Transform(new Vector3f(0f,.8f, .1f),new Vector3f(0f), new Vector3f(.75f,.08f,1f)),"Images/blankTexture.png",
				new Vector4f(1f,0f,0f,0f), new Vector2f(1f)).AddChild(
				
				//Create Button		
				new GUIButton("fullScreenButton",  new Transform(new Vector3f(.25f, 0f,.1f), new Vector3f(0f), new Vector3f(.02f,.035f, 1f)),
				Application.GetWindow().IsFullScreen() ? "Images/Buttons/checkedBox.png" : "Images/Buttons/checkBox.png",
				!Application.GetWindow().IsFullScreen() ? "Images/Buttons/checkedBox.png" : "Images/Buttons/checkBox.png", new Vector4f(1f)
				) {
				
					private boolean startFull = Application.GetWindow().IsFullScreen();
					
					@Override
					protected void OnSelect() {
						// TODO Auto-generated method stub
						SetColor(178/255f, 190/255f, 195/255f,1.0f);
					}
				
					@Override
					protected void OnMousePressed() {
						// TODO Auto-generated method stub
						
					}
				
					@Override
					protected void OnMouseReleased() {
						SetButtonTexture(startFull ? Application.GetWindow().IsFullScreen() : !Application.GetWindow().IsFullScreen());
						Application.GetWindow().SetFullScreen(!Application.GetWindow().IsFullScreen());
					}
				
					@Override
					public void OnDeselect() {
						// TODO Auto-generated method stub
						SetColor(223/255f, 230/255f, 233/255f,1.0f);
					}
							
				}.AddChild(new GUIText("fullScreenText",new Transform(new Vector3f(-.5f,0f,.1f)),"Fonts/BebasNeue","Full Screen",
								titleTextColor,1f,2.5f,true
				)));
	}


	
	
}
