package application;

import engine.Events.Event;
import renderer.Renderer;
import renderer.Transform;
import renderer.GUI.GUIButton;
import renderer.GUI.GUIQuad;
import renderer.GUI.GUIText;


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
		Actor.Create("StartButton", this).AddComponent(new GUIButton(
				"Button",new Transform( 
						new Vector3f(0,0,1.f), // Position x,y, Z-Order higher is on top
						new Vector3f(0f, 0f,0f),  // Rotation
						new Vector3f(.2f,.1f,1f)), // Scale x,y,z
						"Images/Buttons/mainMenuButtonUp.png",  // Button texture
						"Images/Buttons/mainMenuButtonDown.png", // Button pressed texture
						new Vector4f(0f,.6f,.075f,1f) // Quad Color r,g,b,a
						
						) {
							@Override
							protected void OnSelect() {
								// TODO Auto-generated method stub
							}
							@Override
							protected void OnMousePressed() {
								SetButtonTexture(true);
							}
							@Override
							protected void OnMouseReleased() {
								SetButtonTexture(false);
								SceneManager.SetCurrentScene("testScene");
							}
							@Override
							public void OnDeselect() {
								SetButtonTexture(false);
							}
						}.AddChild(new GUIText(
								"StartText",
								new Transform(new Vector3f(0f,0f,1f)),
								"Fonts/BebasNeue",
								"Start Game",
								new Vector4f(1f),
								.19f,
								2f,
								true
								)));
		
	
		
		// Create Settings button
		Actor.Create("SettingButton", this).AddComponent(new GUIButton(
				"Button",new Transform( 
						new Vector3f(0,-.25f,1.f), // Position x,y, Z-Order higher is on top
						new Vector3f(0f, 0f,0f),  // Rotation
						new Vector3f(.2f,.1f,1f)), // Scale x,y,z
						"Images/Buttons/mainMenuButtonUp.png",  // Button texture
						"Images/Buttons/mainMenuButtonDown.png", // Button pressed texture
						new Vector4f(0f,.6f,.075f,1f) // Quad Color r,g,b,a
						
						) {
							@Override
							protected void OnSelect() {
								// TODO Auto-generated method stub
							}
							@Override
							protected void OnMousePressed() {
								SetButtonTexture(true);
							}
							@Override
							protected void OnMouseReleased() {
								SetButtonTexture(false);
								//SceneManager.SetCurrentScene("testScene");
							}
							@Override
							public void OnDeselect() {
								SetButtonTexture(false);
							}
						}.AddChild(new GUIText(
								"SettingText",
								new Transform(new Vector3f(0f,0f,1f)),
								"Fonts/BebasNeue",
								"Settings",
								new Vector4f(1f),
								.19f,
								2f,
								true
								)));
			
		// Create Quit Button
		Actor.Create("QuitButton", this).AddComponent(new GUIButton(
				"Button",new Transform( 
						new Vector3f(0,-.5f,1.f), // Position x,y, Z-Order higher is on top
						new Vector3f(0f, 0f,0f),  // Rotation
						new Vector3f(.2f,.1f,1f)), // Scale x,y,z
						"Images/Buttons/mainMenuButtonUp.png",  // Button texture
						"Images/Buttons/mainMenuButtonDown.png", // Button pressed texture
						new Vector4f(0f,.6f,.075f,1f) // Quad Color r,g,b,a
						) {
							@Override
							protected void OnSelect() {
								// TODO Auto-generated method stub
							}
							@Override
							protected void OnMousePressed() {
								SetButtonTexture(true);
							}
							@Override
							protected void OnMouseReleased() {
								SetButtonTexture(false);
								Application.CloseApplication();
							}
							@Override
							public void OnDeselect() {
								SetButtonTexture(false);
							}
						}.AddChild(new GUIText(
								"QuitText",
								new Transform(new Vector3f(0f,0f,1f)),
								"Fonts/BebasNeue",
								"Quit",
								new Vector4f(1f),
								.19f,
								2f,
								true
								)));
				
		// Create background image
		Actor.Create("background").AddComponent(new GUIQuad(
				"quad",
				new Transform(),
				"Images/mainMenuBackgroundImage.png",
				new Vector4f(.75f, .07f,0f,1f),
				new Vector2f(1)
				));
		
		// Create Text
		Actor.Create("blackJackText", this).AddComponent(new GUIQuad("textQuad",new Transform(
				new Vector3f(0, .75f, .1f), // Position
				new Vector3f(0f), // Rotation (buggy keep at 0)
				new Vector3f(1f,.25f, 1f) // Quad Scale
				),
				"Images/blankTexture.png", // Texture
				new Vector4f(.125f, .125f,.25f,0f)// Quad Color
				).SetGUICollision(false).AddChild(new GUIText(
						"text",
						new Transform(),
						"Fonts/poker1",
						"Black Jack!",
						new Vector4f(1f),
						1f,
						5f,
						true
						)));
	}

	@Override
	public void OnEnd() {
		
	}

	@Override
	public void OnEvent(Event e) {
		
		
	}

	
	
}