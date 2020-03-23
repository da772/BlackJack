package application;

import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.Actor;
import engine.SceneManager;
import engine.renderer.Transform;
import engine.renderer.GUI.GUIQuad;
import engine.renderer.GUI.GUIText;

public class Dealer{
	
    // dealer just needs some generic name and a balance
	private int balance; 
	private Hand hand;
	private boolean uiCreated = false, handCreated = false, cardShow = false;
	private int handSize = 0;

	public Dealer() {
		this.balance = 500000;
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
	
	public void showDealerCard() {
		if (this.handCreated && !this.cardShow) {
			((CardMesh)Actor.Get("cardDealer0").GetComponent("Mesh")).SetRotation(new Vector3f(0f,0f,0f));
			this.cardShow = true;
			updateUI();
			updateHand();
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
					new GUIQuad("quad", new Transform(new Vector3f(0,.8f,1f), new Vector3f(0f), new Vector3f(.125f,.125f, 1f)), "Images/roundedTexture.png", 
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
			.AddChild(new GUIText("text", new Transform(new Vector3f(0f, -.04f, .01f)), "Fonts/BebasNeue",
					GenerateText(),
					new Vector4f(1f), .125f, 1f, true));
		}
	}
	
	private String GenerateText() {
		int total = cardShow ? hand.getTotal() : hand.getTotalDealer();
		return total <= 0 ? "Waiting..." : "Hand Total: "+ total + (total > 21 ? "\nBust..." : total == 21 ? "\nBlack Jack!" : "");
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
						"card_back_red", // Card back Suit
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
	
}