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
						new Vector4f(0f,.6f,.075f,1f), // Quad Color r,g,b,a
						new Vector2f(0f,0f), // Font Offset (used to center text if needed) 
						"Fonts/BebasNeue",  // Font path
						"Start Game!", // Font String
						new Vector4f(.95f,.95f,.95f,1f), // Font color r,g,b,a
						.25f, // Text Line Width ( how wide each line will be can use \n in string for new line)
						2f, // Font Size
						true, // Center Text
						false // Auto expand width to match quad)
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
						});
		
	
		
		// Create Settings button
		Actor.Create("SettingButton", this).AddComponent(new GUIButton(
				"Button",new Transform( 
						new Vector3f(0,-.25f,1.f), // Position x,y, Z-Order higher is on top
						new Vector3f(0f, 0f,0f),  // Rotation
						new Vector3f(.2f,.1f,1f)), // Scale x,y,z
						"Images/Buttons/mainMenuButtonUp.png",  // Button texture
						"Images/Buttons/mainMenuButtonDown.png", // Button pressed texture
						new Vector4f(0f,.6f,.075f,1f), // Quad Color r,g,b,a
						new Vector2f(0f,0f), // Font Offset (used to center text if needed) 
						"Fonts/BebasNeue",  // Font path
						"Settings!", // Font String
						new Vector4f(.95f,.95f,.95f,1f), // Font color r,g,b,a
						.25f, // Text Line Width ( how wide each line will be can use \n in string for new line)
						2f, // Font Size
						true, // Center Text
						false // Auto expand width to match quad)
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
						});
			
		// Create Quit Button
		Actor.Create("QuitButton", this).AddComponent(new GUIButton(
				"Button",new Transform( 
						new Vector3f(0,-.5f,1.f), // Position x,y, Z-Order higher is on top
						new Vector3f(0f, 0f,0f),  // Rotation
						new Vector3f(.2f,.1f,1f)), // Scale x,y,z
						"Images/Buttons/mainMenuButtonUp.png",  // Button texture
						"Images/Buttons/mainMenuButtonDown.png", // Button pressed texture
						new Vector4f(0f,.6f,.075f,1f), // Quad Color r,g,b,a
						new Vector2f(0f,0f), // Font Offset (used to center text if needed) 
						"Fonts/BebasNeue",  // Font path
						"Quit!", // Font String
						new Vector4f(.95f,.95f,.95f,1f), // Font color r,g,b,a
						.25f, // Text Line Width ( how wide each line will be can use \n in string for new line)
						2f, // Font Size
						true, // Center Text
						true // Auto expand width to match quad)
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
								Application.app.CloseApplication();
							}
							@Override
							public void OnDeselect() {
								SetButtonTexture(false);
							}
						});
				
		// Create background image
		Actor.Create("background").AddComponent(new GUIQuad(
				"quad",
				new Transform(),
				"Images/mainMenuBackgroundImage.png",
				new Vector4f(.75f, .07f,0f,1f),
				new Vector2f(1)
				));
		
		// Create Text
		Actor.Create("blackJackText", this).AddComponent(new GUIText("textQuad",new Transform(
				new Vector3f(0, .75f, .1f), // Position
				new Vector3f(0f), // Rotation (buggy keep at 0)
				new Vector3f(1f,.25f, 1f) // Quad Scale
				),
				"Images/blankTexture.png", // Texture
				new Vector4f(.125f, .125f,.25f,0f), // Quad Color
				new Vector2f(0f,0f), // Text Position offset
				"Fonts/poker1", // Text Font
				"Black Jack", // Text
				new Vector4f(1.f,1f,1f,1f), // Text Color
				1f, // Textbox Width
				5f,// Font size
				true, // Center?
				false // Auto width based on quad?
				));
	}

	@Override
	public void OnEnd() {
		
	}

	@Override
	public void OnEvent(Event e) {
		
		
	}

	
	
}