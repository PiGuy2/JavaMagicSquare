package magicSquare;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class MagicSquare {
	int [] [] magicSquare;

	public MagicSquare () {
		while (true) {
			try {
				magicSquare = generateMagicSquare(3).getArray();
				break;
			} catch (MagicSquareGenerationError e) {
				continue;
			}
		}
	}

	public MagicSquare (int d) throws MagicSquareGenerationError {
		magicSquare = generateMagicSquare(d).getArray();
	}

	public MagicSquare (int [] [] square) throws InvalidSquareException {
		checkSquareError(square);
		magicSquare = square;
	}

	public static boolean checkEveryValueDifferent (int [] [] square) {
		ArrayList<Integer> v = new ArrayList<>();
		for (int [] row : square) {
			for (int i : row) {
				if (v.contains(i)) {
					return false;
				}
				v.add(i);
			}
		}
		return true;
	}

	public static boolean checkIfValidSquareSimple (int [] [] square) {
		for (int [] row : square) {
			int sum = sumArray(row);
			if (sum != sumArray(square[0])) {
				return false;
			}
		}
		for (int column = 0; column < square[0].length; column++) {
			int sum = 0;
			for (int row = 0; row < square.length; row++) {
				sum += square[row][column];
			}
			if (sum != sumArray(square[0])) {
				return false;
			}
		}
		int d1Sum = 0;
		int d2Sum = 0;
		for (int i = 0; i < square.length; i++) {
			d1Sum += square[i][i];
			int inverse = square.length - (i + 1);
			d2Sum += square[inverse][inverse];
		}
		if (d1Sum != sumArray(square[0]) || d2Sum != sumArray(square[0])) {
			return false;
		}
		return true;
	}

	public static String checkSquare (int [] [] square) {
		if (square == null || square.length == 0 || square[0] == null || square[0].length == 0) {
			return "The \"square\" argument cannot be null.";
		}
		for (int [] row : square) {
			if (row.length != square[0].length) {
				return "All rows in the \"square\" array must have the same length.";
			}
		}
		if (square.length != square[0].length) {
			return "The \"square\" argument must have equal dimensions.";
		}
		for (int [] row : square) {
			for (int i : row) {
				if (i < 1) {
					return "All numbers in the \"square\" array must not be less than one.";
				}
				if (i > Math.pow(square.length, 2)) {
					return "All numbers in the \"square\" array must be greater than n^2 (which is "
							+ (int) Math.pow(square.length, 2) + ").";
				}
			}
		}
		if (!checkIfValidSquareSimple(square)) {
			return "All rows, columns, and diagonals of the \"square\" argument must add up to the same number.";
		}
		if (!checkEveryValueDifferent(square)) {
			return "Every value in the \"square\" argument must be different.";
		}
		return null;
	}

	public static void checkSquareError (int [] [] square) throws InvalidSquareException {
		String error = checkSquare(square);
		if (error != null) {
			throw new InvalidSquareException(error);
		}
	}

	public static MagicSquare generateMagicSquare (int d) throws MagicSquareGenerationError {
		int [] [] retA = new int [d] [d];
		int d2 = (int) Math.pow(d, 2);

		if (d < 1) {
			throw new MagicSquareGenerationError("The dimension of the magic sqaure must be at least 1.");
		} else if (d == 1) {
			try {
				return new MagicSquare(new int [] [] {{1}});
			} catch (InvalidSquareException e) {
				throw new MagicSquareGenerationError();
			}
		} else if (d == 2) {
			throw new MagicSquareGenerationError("It is impossible to generate a 2x2 magic square.");
		} else if (d % 2 == 1) {
			int x = (d - 1) / 2;
			int y = 0;
			for (int i = 1; i <= d2; i++) {
				// System.out.println(i + ": " + "\tX: " + x + "\tY: " + y);
				retA[y][x] = i;
				if (i == d2) {
					break;
				}
				int nX = mod(x + 1, d);
				int nY = mod(y - 1, d);
				if (retA[nY][nX] == 0) {
					x = nX;
					y = nY;
				} else if (retA[mod(y + 1, d)][x] == 0) {
					y = mod(y + 1, d);
				} else {
					// System.out.println("Value: " + ret[mod(y + 1, d)][x]);
					throw new MagicSquareGenerationError();
				}
			}
		} else if (d == 4) {
			Vector<Integer> nums = new Vector<>();
			for (int l = 0; l < d; l++) {
				retA[l][l] = (l * d) + l + 1;
				retA[l][(d - 1) - l] = (l * d) + d - l;
				nums.add(retA[l][l]);
				nums.add(retA[l][(d - 1) - l]);
			}
			for (int y = d - 1; y >= 0; y--) {
				for (int x = d - 1; x >= 0; x--) {
					// System.out.print(y + ": " + x);
					if (retA[y][x] == 0) {
						// System.out.print(" = ");
						for (int i = 1; i <= d2; i++) {
							if (!nums.contains(i)) {
								retA[y][x] = i;
								nums.add(i);
								// System.out.print("+\t" + i);
								break;
							}
							// System.out.print("-");
						}
					}
					// System.out.print("\n");
				}
			}
		} else {
			throw new MagicSquareGenerationError("The dimensions of the magic sqaure must be odd or they must be 4.");
		}

		// for (int [] row : retA) {
		// for (int n : row) {
		// System.out.print(formatNum(n, 2) + " ");
		// }
		// System.out.print("\n");
		// }

		MagicSquare ret;
		try {
			ret = new MagicSquare(retA);
		} catch (InvalidSquareException e) {
			throw new MagicSquareGenerationError("Error when creating the magic square: " + e.getMessage());
		}

		// ret.print();
		// System.out.print("\n");

		Random rand = new Random();
		ret = rotateSquareClockwise(ret, rand.nextInt(4));
		if (rand.nextBoolean()) {
			ret = flipSquare(ret, true);
		}
		if (rand.nextBoolean()) {
			ret = flipSquare(ret, false);
		}
		return ret;
	}

	private static MagicSquare flipSquare (MagicSquare magic, boolean vertical) {
		int [] [] array = magic.getArray();
		int d = array.length;
		int [] [] ret = new int [d] [d];
		for (int x = 0; x < d; x++) {
			for (int y = 0; y < d; y++) {
				if (vertical) {
					ret[y][x] = array[(d - 1) - y][x];
				} else {
					ret[y][x] = array[y][(d - 1) - x];
				}
			}
		}
		magic.setArray(ret);
		return magic;
	}

	private static String formatNum (int n, int len, boolean fillZeros) {
		String ret = String.valueOf(n);
		int zeros = len - ret.length();
		for (int i = 0; i < zeros; i++) {
			ret = (fillZeros ? "0" : " ") + ret;
		}
		if (ret.length() > 3) {
			for (int i = 3; i < ret.length(); i += 3) {
				ret = ret.substring(0, ret.length() - i) + "," + ret.substring(ret.length() - i);
			}
		}
		if (fillZeros) {
			// return ret.replaceAll("0,", "00");
			return ret;
		}
		return ret.replaceAll(" ,", "  ");
	}

	/**
	 * @return n modulo m
	 */
	private static int mod (int n, int m) {
		while (n < 0) {
			n += m;
		}
		return n % m;
	}

	private static MagicSquare rotateSquareClockwise (MagicSquare magic) {
		int [] [] array = magic.getArray();
		int d = array.length;
		int [] [] ret = new int [d] [d];
		int nX = 0;
		int nY = 0;
		mainfor: for (int oX = 0; oX < d; oX++) {
			for (int oY = d - 1; oY >= 0; oY--) {
				ret[nY][nX] = array[oY][oX];
				nX++;
				if (nX >= d) {
					nX = 0;
					nY++;
				}
				if (nY >= d) {
					break mainfor;
				}
			}
		}
		magic.setArray(ret);
		return magic;
	}

	// TODO switch
	private static MagicSquare rotateSquareClockwise (MagicSquare magic, int n) {
		n = mod(n, 4);
		for (int i = 0; i < n; i++) {
			magic = rotateSquareClockwise(magic);
		}
		return magic;
	}

	private static int sumArray (int [] n) {
		int sum = 0;
		for (int i : n) {
			sum += i;
		}
		return sum;
	}

	public int [] [] getArray () {
		return magicSquare;
	}

	public void print () {
		print(false);
	}

	public void print (boolean pad) {
		int max = 0;
		int sum = 0;
		for (int [] row : magicSquare) {
			sum += row[0];
			for (int n : row) {
				max = (n > max) ? n : max;
			}
		}
		int len = (int) (Math.log10(max) + 1);
		System.out.println("Sum: " + formatNum(sum, String.valueOf(sum).length(), true));
		for (int [] row : magicSquare) {
			for (int n : row) {
				System.out.print(formatNum(n, len, pad) + ((len > 1) ? "  " : " "));
			}
			System.out.print("\n");
		}
	}

	public boolean setArray (int [] [] newArray) {
		try {
			checkSquareError(newArray);
			magicSquare = newArray;
			return true;
		} catch (InvalidSquareException e) {
			return false;
		}
	}
}
