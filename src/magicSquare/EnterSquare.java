/**
 * 
 */
package magicSquare;

/**
 * @author student
 *
 */
public class EnterSquare {
	public static void main (String [] args) {
		while (true) {
			String [] row1 = UserInput.get("Enter each value in the first row separated by spaces")
					.replaceAll(" +", " ").split(" ");
			int [] [] square = new int [row1.length] [row1.length];
			square[0] = stringArrayToIntArray(row1);
			for (int i = 1; i < row1.length; i++) {
				while (true) {
					String [] row = UserInput.get("Enter each value in the "
							+ ((i == row1.length - 1) ? "last" : ordinal(i + 1))
							+ " row separated by spaces").replaceAll(" +", " ").split(" ");
					if (row.length == row1.length) {
						square[i] = stringArrayToIntArray(row);
						break;
					}
					System.out.println("You must enter exactly " + row1.length
							+ " numbers. You entered " + row.length + " numbers.");
				}
			}
			System.out.println(
					"\nThat " + (MagicSquare.checkIfValidSquareSimple(square) ? "was" : "was not")
							+ " a magic square\n");
			if (!UserInput.getBool("Would you like to check another square")) {
				break;
			}
		}
		System.out.println("\nBye!");
	}

	private static int [] stringArrayToIntArray (String [] s) {
		int [] ints = new int [s.length];
		for (int i = 0; i < s.length; i++) {
			ints[i] = Integer.parseInt(s[i]);
		}
		return ints;
	}

	public static String ordinal (int i) {
		String [] sufixes = new String [] {"th", "st", "nd", "rd", "th", "th", "th", "th", "th",
				"th"};
		switch (i % 100) {
		case 11:
		case 12:
		case 13:
			return i + "th";
		default:
			return i + sufixes[i % 10];
		}
	}
}
