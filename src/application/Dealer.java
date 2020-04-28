package application;

import java.util.Random;

import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.Actor;
import engine.SceneManager;
import engine.audio.AudioManager;
import engine.renderer.Transform;
import engine.renderer.GUI.GUIQuad;
import engine.renderer.GUI.GUIText;
import engine.util.Timing;

public class Dealer{
	
    // dealer just needs some generic name and a balance
	private int balance; 
	private Hand hand;
	private boolean uiCreated = false, handCreated = false, cardShow = false, betting =false;
	private int handSize = 0;
	private float waitTimeInMS = 0;
	private Card nextCard;


	public Dealer() {
		this.balance = 1000;
		this.hand = new Hand();
	}
	
	public Hand getHand() {
		return this.hand;
	}
	
	public int getBalance() {
		return this.balance;
	}
	
	public void addToBalance(int amount) {
		this.balance += amount;
	}
	
	public void subtractFromBalance(int amount) {
		this.balance -= amount;
		
	}
	
	public void clearBalance() {
		this.balance = 0;
	}
	
	public void addCard(Card card) {
		this.hand.addCard(card);
		updateUI();
		updateHand();
	}
	

	// Add and animate card into hand
	// Requirement(3.2.3)
	public boolean addCard(Deck d, float deltaTime) {
		if (this.nextCard == null) {
			this.nextCard = d.drawCard();
		}
		Actor a = Actor.Get(this.toString()+"_"+nextCard.getCardTextureID());
		if (a != null) {
			CardMesh mesh = (CardMesh) a.GetComponent("cardMesh");
			if (mesh != null) {
				if (mesh.AnimateTo(getCardPos(0, this.getHand().GetCardCount()), deltaTime, 1.5f, .0f)) {
					this.getHand().addCard(nextCard);
					updateUI();
					updateHand();
					this.nextCard = null;
					Actor.Remove(a.GetName());
					return true;
				}
			}
		} else {
			CardMesh mesh = new CardMesh("cardMesh", new Transform(new Vector3f(4.25f,0,6.25f), new Vector3f(0.f, 180.f,0.f), new Vector3f(1f))
					, nextCard.getCardTextureID(), "card_back_black", SceneManager.GetCurrentScene().GetCameraController().GetCamera());
			new Actor(this.toString()+"_"+nextCard.getCardTextureID()).AddComponent(mesh);
			AudioManager.CreateAudioSource(this.toString()+"_"+nextCard.getCardTextureID()+"_place", "Audio/cardPlace3.wav", "sfx", 1.f, 1.f, false, true);
			AudioManager.PlaySource(this.toString()+"_"+nextCard.getCardTextureID()+"_place");
			return false;
		}
		
		return false;
	}
	
	public boolean showDealerCard(boolean animate, float deltaTime) {
		if (!animate) {
			if (this.handCreated && !this.cardShow) {
				((CardMesh)Actor.Get("cardDealer0").GetComponent("Mesh")).SetRotation(new Vector3f(0f,0f,0f));
				this.cardShow = true;
				updateUI();
				updateHand();
				return true;
			}
		} else {
			if (this.handCreated) {
				this.cardShow = true;
				if (((CardMesh)Actor.Get("cardDealer0").GetComponent("Mesh")).AnimateTo(getCardPos(0,0), deltaTime, .5f, 0.0f)) {
					updateUI();
					updateHand();
					return true;
				}
			}
		}
		return false;
	}
	


	// Dealers continue to hit until their hand is atleast 17
	// Requirement(4.2.3)
	public void computerPlayTurn(Round r, double firstCallTimeinMS) {
		
		if (!betting) {
			waitTimeInMS = new Random().nextInt(5)*100;
			this.betting = true;
			this.updateUI();
		}
		
		if (Timing.getTimeMS() - firstCallTimeinMS >= waitTimeInMS) {
			int play = getHand().getTotal() < 17 ? 0 : 1;
			r.setCurrentPlayerInput(play);
			waitTimeInMS = 0;
			this.betting = false;
		}
	
		
	}
	
	
	
	public void setup() {
		createUI();
		createHand();
	}
	
	
	public void reset() {
		this.hand = new Hand();
		this.cardShow = false;
		this.updateHand();
		this.updateUI();
		
	}
	
	public void createUI() {
		if (this.uiCreated) {
			destroyUI();
			return;
		} else {
			new Actor("dealerUI").AddComponent(
					new GUIQuad("quad", new Transform(new Vector3f(0,.8f,1f), new Vector3f(0f), new Vector3f(.125f,.15f, 1f)), "Images/roundedTexture.png", 
							new Vector4f(0f,0f,0f,.5f))
					.AddChild(new GUIText(
							"name",
							new Transform(new Vector3f(0f,.075f,.01f)),
							"Fonts/morningStar",
							"Dealer",
							new Vector4f(1f),
							.125f,
							1f,
							true)).AddChild(
							new GUIText(
							"text",
							new Transform(new Vector3f(0f,-.035f,.01f)),
							"Fonts/BebasNeue",
							GenerateText(),
							new Vector4f(1f),
							.125f,
							1f,
							true))
					);
			this.uiCreated = true;
		}
		
	}
	
	public void updateUI() {
		if (this.uiCreated) {
			((GUIQuad) Actor.Get("dealerUI").GetComponent("quad")).RemoveChild("text")
			.AddChild(new GUIText("text", new Transform(new Vector3f(0f, -.05f, .01f)), "Fonts/BebasNeue",
					GenerateText(),
					new Vector4f(1f), .15f, 1f, true));
		}
	}
	
	private String GenerateText() {
		int total = cardShow ? hand.getTotal() : hand.getTotalDealer();
		return total <= 0 ? "Balance: \n$" + getBalance()  + "\nWaiting..." : "Balance: \n$" + getBalance() +"\nHand Total: "+ total;
	}
	
	private void destroyUI() {
		if (this.uiCreated) {
			Actor.Remove("dealerUI");
			this.uiCreated = false;
		}
	}
	
	public void createHand() {
		if (!this.handCreated) {
			this.handSize = this.hand.GetCardCount();
			for (int i = 0; i < hand.GetCardCount(); i++) {
				new Actor("cardDealer"+i).AddComponent(new CardMesh("Mesh", 
						new Transform( // Card Transform
								new Vector3f(.25f-(.25f*i), 5f-(.01f*i), 1f+(.01f*i)), // Position
								i == 0 && !cardShow ? new Vector3f(0f,180f,0f) : new Vector3f(0f, 0f, 0f), // Rotation
								new Vector3f(1.f, 1.f, 1f) ), // Scale
						hand.getCard(i).getCardTextureID(), // Card front Suit
						"card_back_black", // Card back Suit
						SceneManager.GetCurrentScene().GetCameraController().GetCamera()));// Camera))
			}
			this.handCreated = true;
		}	
	}
	
	public void updateHand() {
		destroyHand();
		createHand();
	}
	
	private void destroyHand() {
		if (this.handCreated) {
			for (int i = 0; i < this.handSize; i++) {
				Actor.Remove("cardDealer"+i);
			}
			this.handSize = 0;
			this.handCreated = false;
		}
	}
	
	public Transform getCardPos(int _id, int i) {
		switch (_id) {
		default:
		case 0: {
			// Add player cards in correct position (creates meshes)
			return new Transform( // Card Transform
					new Vector3f(.25f-(.25f*i), 5f-(.01f*i), 1f+(.01f*i)), // Position
					i == 0 && !cardShow ? new Vector3f(0f,180f,0f) : new Vector3f(0f, 0f, 0f), // Rotation
					new Vector3f(1.f, 1.f, 1f) );
		}
		}
	
		
	}
	
}