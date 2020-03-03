package engine.audio;

import java.util.Scanner;

public class Test {

	
	public static void main(String[] args) {
		AudioManager.Init();
		boolean running = true;
		String piano = AudioManager.CreateAudioSource("Piano", "Audio/Piano2.wav", 1.f, 1.f, true, true);
		
		AudioManager.PlaySource(piano);
		Scanner scanner = new Scanner(System.in);
		while(running)  {
			System.out.println("Input Command!");
			if (scanner.hasNextLine()) {
				
				String cmd = scanner.nextLine();
				if (cmd.equals("mute")) {
					AudioManager.SetVolume("Piano", .0f);
					System.out.println("Set Volume to half");
				}
				
				if (cmd.equals("fourth")) {
					AudioManager.SetVolume("Piano", .25f);
					System.out.println("Set Volume to half");
				}
				if (cmd.equals("half")) {
					AudioManager.SetVolume("Piano", .5f);
					System.out.println("Set Volume to half");
				}
				if (cmd.equals("full")) {
					AudioManager.SetVolume("Piano", 1f);
					System.out.println("Set Volume to half");
				}
				if (cmd.equals("recreate")) {
					AudioManager.CreateAudioSource("Piano", "Audio/Piano2.wav", 1.f, 1.f, true, true);
				}
				if (cmd.equals("play")) {
					AudioManager.PlaySource("Piano");
				}
				if (cmd.equals("yeet")) {
					AudioManager.CreateAudioSource("Yeet", "Audio/yeet-sound-effect.wav", 1.f, 1.f, false, true);
					AudioManager.PlaySource("Yeet");
				}
				if (cmd.equals("stop")) {
					AudioManager.RemoveSource("Piano");
				}
				if (cmd.equals("quit")) {
					running = false;
				}
				if (cmd.equals("loop")) {
					AudioManager.SetLoop("Piano", false);
				}
			}	
		}
		scanner.close();
		AudioManager.Shutdown();
	}
	
}
