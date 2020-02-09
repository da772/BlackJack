# BlackJack project for CSC 478

## Build Instructions
### Windows
	1. Clone repository
	2. Run scripts/GenerateProject_Windows.bat
	3. Eclipse -> File -> Import -> BlackJack
	4. Run src/engine.main.java
	
### Mac
	1. Clone repository
	2. Run scripts/GenerateProject_Mac.command
	3. Eclipse -> File -> Import -> BlackJack
	4. Right click src/engine/Main.java -> Properties
	5. Run/Debug Settings -> New... -> Java Application -> Arguments Tab
	6. VM arguments add: -XstartOnFirstThread
	7. Apply -> Ok -> Apply and Close
	8. Run src/engine.main.java


### Todo
- Engine
	- Graphics
		- Object Rendering
			- [X] Vertex/Index Buffers
			- [X] VertexArrays
			- [ ] Quad Renderer
				- [ ] Batching
			- [ ] Collision Detection
		- Shader
			- Textures
				- [X] Transparency
				- [X] Texture Pool
			- [X] Layouts
			- [X] Uniforms
			- [X] View Projection Matrix
			- [X] Shader Pool
		- Hud Rendering
			- [X] Text Rendering
				- [ ] Text Batching
				- [X] Multiple fonts
				- [X] Lines of text
			- [ ] Buttons
			- [X] Static position
			- [ ] Mouse Detection
		- Renderer
			- [ ]  Z-Ordering
			- [X]  Anti-Aliasing
		- Window
			- [X] Events
			- [X] Viewport
			- [X] Input
		- Camera
			- Orthographic Camera
				- [X] Zoom
				- [X] Move
				- [X] Rotate
	- Application
		- Main Loop
			- [X] Delta Time
			- [X] Frame Cap
			- [X] FPS Counter
			- [ ] Profiling tools
		- Events
			- [X] Window Resized
			- [X] Key Pressed/Released
			- [X] Mouse Moved
			- [X] Mouse Button Pressed/Released
			- [X] Mouse Scrolled
			- [X] Window Closed
	- Audio
- Gameplay
	- Scenes
		- Main Menu
			- Settings
				- [ ] Volume Slider
				- Window Settings
					- [ ] Full screen
					- [ ] Resolution
			- Game Setup
				- [ ] Load Game
				- [ ] Starting cash
				- [ ] Amount of players
				- [ ] Amount of decks
				- [ ] Gametype (BlackJack, Texas Hold Em?, Omaha?)
		- [ ] BlackJack
			- Graphical Objects
				- [ ] Display user's cards
				- [ ] Display dealer's cards
				- [ ] Display opponnent's cards
				- [ ] Display poker board
				- [ ] Display poker chips on board
			- UI
				- [ ] Show player stats (current money)
				- [ ] Betting options (Slider?, input box? Increase/Decrease Buttons?)
				- [ ] Hit Button
				- [ ] Stay Button
				- [ ] Split Button
				- [ ] Double Button
				- [ ] Settings Icon
				- [ ] Quit Game
				- [ ] Save Game
	- Game Manager
		- BlackJack Manager
			- [ ] State ( Waiting on players/Your Turn)
			- [ ] Player Info (Money, Current Cards, Current Bet Amt)
			- [ ] AI
				- Info
				- Logic
					- [ ] Hitting Logic [Hit chart?](https://www.blackjackapprenticeship.com/wp-content/uploads/2018/08/BJA_Basic_Strategy.jpg)
					- [ ] Betting Logic
	- Assets
		- Images
			- [ ] Card Sprite Sheet
			- [ ] Poker board
			- [ ] UI backgrounds/borders
		- Sounds
			- [ ] Card Drawing sound
			- [ ] Poker Chips falling sound
			- [ ] Menu Clicks
			- [ ] Background music
		
