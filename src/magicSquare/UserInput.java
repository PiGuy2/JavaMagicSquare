package magicSquare;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author Robby
 *
 */
public class UserInput {
	static Scanner scan = new Scanner(System.in);

	/**
	 * Similar to {@link #getString(String)}, but includes error catching. This adds
	 * {@code ": "} to the end of {@code prompt}
	 * 
	 * @param prompt
	 *            is printed before user input is read
	 * @return String read from {@code System.in} using {@code Scanner.nextLine()},
	 *         or an empty {@code String} if {@code NoSuchElementException} is
	 *         raised 3 times
	 */
	public static String get (String prompt) {
		System.out.print(prompt + ": ");
		for (int i = 0; i < 3; i++) {
			try {
				return scan.nextLine();
			} catch (NoSuchElementException e) {
				continue;
			}
		}
		return "";
	}

	public static boolean getBool (String prompt) {
		while (true) {
			String resp = get(prompt);
			if (resp.startsWith("y") || resp.equals("sure") || resp.equals("ok")) {
				return true;
			}
			if (resp.startsWith("n")) {
				return false;
			}
			System.out.println("Please enter 'yes' or 'no'");
		}
	}

	/**
	 * @return String read from {@code System.in} using {@code Scanner.nextLine()}
	 * @throws NoSuchElementException
	 *             if no line was found
	 */
	public static String getString () {
		return scan.nextLine();
	}

	/**
	 * Similar to {@link #getString()}, but prints out {@code prompt} before reading
	 * user input
	 * 
	 * @param prompt
	 *            is printed before user input is read
	 * @return String read from {@code System.in} using {@code Scanner.nextLine()}
	 * @throws NoSuchElementException
	 *             if no line was found
	 */
	public static String getString (String prompt) {
		System.out.print(prompt);
		return getString();
	}

	public static int getInt (String prompt) {
		while (true) {
			String resp = get(prompt);
			try {
				return Integer.parseInt(resp);
			} catch (NumberFormatException e) {
				System.out.println("Please enter a number.");
				continue;
			}
		}
	}

	/**
	 * @param prompt
	 *            The String printed before user input is read
	 * @param lowerLimit
	 *            The lower limit for the user's input (inclusive)
	 * @param upperLimit
	 *            The upper limit for the user's input (exclusive)
	 * @return
	 */
	public static int getInt (String prompt, int lowerLimit, int upperLimit) {
		while (true) {
			String resp = get(prompt);
			try {
				int num = Integer.parseInt(resp);
				if (num < lowerLimit) {
					System.out.println(
							"Please enter a number larger than or equal to " + lowerLimit + ".");
				} else if (num >= upperLimit) {
					System.out.println("Please enter a number smaller than " + upperLimit + ".");
				} else {
					return num;
				}
				return Integer.MIN_VALUE;
			} catch (NumberFormatException e) {
				System.out.println("Please enter a number.");
				continue;
			}
		}
	}
}
