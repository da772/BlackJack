package application;

import engine.Events.Event;
import engine.renderer.Renderer;
import engine.renderer.Transform;
import engine.renderer.GUI.GUIButton;
import engine.renderer.GUI.GUIQuad;
import engine.renderer.GUI.GUIText;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.Actor;
import engine.Application;
import engine.CameraController;

import engine.Scene;
import engine.SceneManager;

public class MainMenuScene extends Scene {
	
	public MainMenuScene(String name, CameraController cam) {
		super(name, cam);
		this.cam = cam;	
	}

	@Override
	public void OnUpdate(float deltaTime) {
		
	}
	
	@Override
	public void OnBegin() {
		Renderer.SetClearColor(0f,0f,1f,1f);
		// Create Start Button
		new Actor("StartButton").AddComponent(new GUIButton(
				"Button",new Transform( 
						new Vector3f(0,0,1.f), // Position x,y, Z-Order higher is on top
						new Vector3f(0f, 0f,0f),  // Rotation
						new Vector3f(.2f,.08f,1f)), // Scale x,y,z
						"Images/blankTexture.png",  // Button texture
						"Images/blankTexture.png", // Button pressed texture
						new Vector4f(0f) // Quad Color r,g,b,a
						
						) {
							@Override
							protected void OnSelect() {
								// TODO Auto-generated method stub
								SetColor(ColorPalette.LightGreenBlue);
							}
							@Override
							protected void OnMousePressed() {
							}
							@Override
							protected void OnMouseReleased() {
								SceneManager.SetCurrentScene("testScene");
							}
							@Override
							public void OnDeselect() {
								SetColor(new Vector4f(0f));
							}
						}.AddChild(new GUIText(
								"StartText",
								new Transform(new Vector3f(0f,0f,1f)),
								"Fonts/morningStar",
								"Start Game",
								ColorPalette.HotPink,
								.19f,
								2f,
								true
								)).AddChild(new GUIText("StartText2",
										new Transform(new Vector3f(.0025f,-.005f,.1f)),
										"Fonts/morningStar",
										"Start Game",
										new Vector4f(1f),
										.19f,
										2f,
										true)));
		
	
		
		// Create Settings button
		new Actor("SettingButton").AddComponent(new GUIButton(
				"Button",new Transform( 
						new Vector3f(0,-.25f,1.f), // Position x,y, Z-Order higher is on top
						new Vector3f(0f, 0f,0f),  // Rotation
						new Vector3f(.2f,.08f,1f)), // Scale x,y,z
						"Images/blankTexture.png",  // Button texture
						"Images/blankTexture.png", // Button pressed texture
						new Vector4f(0.0f) // Quad Color r,g,b,a
						
						) {
							@Override
							protected void OnSelect() {
								// TODO Auto-generated method stub
								SetColor(ColorPalette.LightGreenBlue);
							}
							@Override
							protected void OnMousePressed() {
							}
							@Override
							protected void OnMouseReleased() {
								GameSettingsMenu.Show();
								//SceneManager.SetCurrentScene("testScene");
							}
							@Override
							public void OnDeselect() {
								SetColor(new Vector4f(0f));
							}
						}.AddChild(new GUIText(
								"SettingText",
								new Transform(new Vector3f(0f,0f,1f)),
								"Fonts/morningStar",
								"Settings",
								ColorPalette.HotPink,
								.19f,
								2f,
								true
								)).AddChild(new GUIText("SettingText2",
										new Transform(new Vector3f(.0025f,-.005f,.1f)),
										"Fonts/morningStar",
										"Settings",
										new Vector4f(1f),
										.19f,
										2f,
										true)));
			
		// Create Quit Button
		new Actor("QuitButton" ).AddComponent(new GUIButton(
				"Button",new Transform( 
						new Vector3f(0,-.5f,1.f), // Position x,y, Z-Order higher is on top
						new Vector3f(0f, 0f,0f),  // Rotation
						new Vector3f(.2f,.08f,1f)), // Scale x,y,z
						"Images/blankTexture.png",  // Button texture
						"Images/blankTexture.png", // Button pressed texture
						new Vector4f(0f) // Quad Color r,g,b,a
						) {
							@Override
							protected void OnSelect() {
								SetColor(ColorPalette.LightGreenBlue);
							}
							@Override
							protected void OnMousePressed() {
								
							}
							@Override
							protected void OnMouseReleased() {
								SetButtonTexture(false);
								Application.CloseApplication();
							}
							@Override
							public void OnDeselect() {
								SetColor(new Vector4f(0f));
							}
						}.AddChild(new GUIText(
								"QuitText",
								new Transform(new Vector3f(0f,0f,1f)),
								"Fonts/morningStar",
								"Quit",
								ColorPalette.HotPink,
								.19f,
								2f,
								true
								)).AddChild(new GUIText("QuitText2",
								new Transform(new Vector3f(.0025f,-.005f,.1f)),
								"Fonts/morningStar",
								"Quit",
								new Vector4f(1f),
								.19f,
								2f,
								true)));
				
		// Create background image
		new Actor("background").AddComponent(new GUIQuad(
				"quad",
				new Transform(),
				"Images/testBack.png",
				new Vector4f(.5f, .5f, .5f,1.0f),
				new Vector2f(1)
				));
		
		// Create Text
		new Actor("blackJackText").AddComponent(new GUIQuad("textQuad",new Transform(
				new Vector3f(0, .5f, .1f), // Position
				new Vector3f(0f), // Rotation (buggy keep at 0)
				new Vector3f(.5f,.35f, 1f) // Quad Scale
				),
				"Images/menuLogo3.png", // Texture
				new Vector4f(1f, 1f,1f,1f)// Quad Color
				).SetGUICollision(false));/*.AddChild(new GUIText(
						"text",
						new Transform(new Vector3f(0f, 0f, .2f)),
						"Fonts/morningStar",
						"Black Jack",
						ColorPalette.HotPink,
						1f,
						5f,
						true
						)).AddChild(new GUIText(
						"text",
						new Transform(new Vector3f(.01f,-.01f,.1f)),
						"Fonts/morningStar",
						"Black Jack",
						new Vector4f(1f),
						1f,
						5f,
						true
						)));
						*/
	}

	@Override
	public void OnEnd() {
		
	}

	@Override
	public void OnEvent(Event e) {
		
		
	}

	
	
}