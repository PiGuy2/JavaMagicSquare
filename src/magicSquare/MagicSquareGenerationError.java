package magicSquare;

/**
 * @author Robby
 *
 */
@SuppressWarnings("serial")
public class MagicSquareGenerationError extends Exception {
	public MagicSquareGenerationError () {
		super("Error while generating the magic square.");
	}

	public MagicSquareGenerationError (String message) {
		super(message);
	}

	public MagicSquareGenerationError (Throwable cause) {
		super(cause);
	}

	public MagicSquareGenerationError (String message, Throwable cause) {
		super(message, cause);
	}

	public MagicSquareGenerationError (String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
