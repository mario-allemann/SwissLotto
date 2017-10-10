package lotto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Lotto {
	public static void main(String[] args) {
		final int NORMAL_RANGE = 42;
		final int LUCKY_RANGE = 6;
		final int NORMAL_LIMIT = 6;
		final int LUCKY_LIMIT = 1;

		Random rand = new Random();
		ArrayList<Integer> userPick = new ArrayList<Integer>();
		Scanner scan = new Scanner(System.in);
		int input = 1;

		int count = 1;

		// Picks n lottery numbers (n = NORMAL_LIMIT)
		while (userPick.size() < NORMAL_LIMIT) {
			System.out.println(count + ". Zahl eingeben: ");
			input = scan.nextInt();
			if (input < 1 || input > NORMAL_RANGE) {
				System.err.println("Zahl muss zwischen 1 und " + NORMAL_RANGE + " liegen!");
			} else {
				if (userPick.contains(input)) {
					System.err.println("Zahl bereits gewählt!");
				} else {
					userPick.add(input);
					count++;
				}
			}

		}
		System.out.println(userPick);

		// Picks n lucky numbers (n = LUCKY_LIMIT)
		ArrayList<Integer> luckyPick = new ArrayList<Integer>();
		while (luckyPick.size() < LUCKY_LIMIT) {
			System.out.println("Glückszahl wählen: ");
			int inputLucky = scan.nextInt();
			if (inputLucky < 1 || inputLucky > LUCKY_RANGE) {
				System.err.println("Zahl muss zwischen 1 und " + LUCKY_RANGE + " liegen");
			} else {
				if (luckyPick.contains(inputLucky)) {
					System.err.println("Zahl bereits gewählt");
				} else {
					luckyPick.add(inputLucky);
				}
			}
		}

		// Sorts lists and prints them
		Collections.sort(userPick);
		Collections.sort(luckyPick);
		System.out.println(userPick);
		System.out.println(luckyPick);

		int tries = 0;
		ArrayList<Integer> randomNumbers = new ArrayList<Integer>();
		ArrayList<Integer> randomLuckies = new ArrayList<Integer>();
		int randomNumber;
		int randomLucky;
		
		
		//Generates n random Numbers and n luckyNumber randomly and checks against entered numbers
		//TODO 
		while (!userPick.equals(randomNumbers) || !luckyPick.equals(randomLuckies)) {
			randomNumbers.clear();
			randomLuckies.clear();
			while (randomNumbers.size() < NORMAL_LIMIT) {
				randomNumber = rand.nextInt(NORMAL_RANGE + 1);
				while (randomNumbers.contains(randomNumber)) {
					randomNumber = rand.nextInt(NORMAL_RANGE + 1);
					} 
					randomNumbers.add(randomNumber);
				}
				while (randomLuckies.size() < luckyPick.size()) {			
					randomLucky = rand.nextInt(LUCKY_RANGE + 1);
					
					while (randomLuckies.contains(randomLucky)){
						randomLucky = rand.nextInt(LUCKY_RANGE + 1);
					}
					randomLuckies.add(randomLucky);
					
				}
				Collections.sort(randomNumbers);
				Collections.sort(randomLuckies);
				tries++;
			}
			System.out.println("Versuche: " + tries);
			System.out.println("Zahlen: " + randomNumbers);
			System.out.println("Glückszahlen: " + randomLuckies);
			
			
			

		}
		
	}
