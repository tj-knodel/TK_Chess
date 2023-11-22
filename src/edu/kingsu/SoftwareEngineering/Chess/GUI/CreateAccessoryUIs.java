package edu.kingsu.SoftwareEngineering.Chess.GUI;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import java.awt.Image;
import java.awt.event.ActionListener;

/**
 * Creates Miscellaneous accessory frames;
 * Upgrade Piece Frame, End game frame
 * 
 * @author Noah Bulas
 * @version V1
 */
public class CreateAccessoryUIs {

	/**
	 * Gets the image from the local jar File
	 * 
	 * @param imageToGet Name of the image + type
	 * @return The image
	 */
	public ImageIcon getImage(String imageToGet) {
		return new ImageIcon(getClass().getClassLoader().getResource(imageToGet));
	}

	/**
	 * Gets the image from the source chess appearance folder.
	 * 
	 * @param imageToGet Name of the image + type
	 * @return The image
	 */
	private ImageIcon getBoardImage(String imageToGet) {
		if (!UILibrary.isAbsoluteFilePath)
			return new ImageIcon(getClass().getClassLoader().getResource(UILibrary.boardAppearanceFolder + imageToGet));
		else
			return new ImageIcon(UILibrary.boardAppearanceFolder + imageToGet);
	}

	// -----------------------------------------------------
	// -----------------------------------------------------

	/**
	 * Max size a piece can be in pixels X; in the upgrade piece frame
	 */
	private static int maxUpgradePieceSize_X = 120;

	/**
	 * Max size a piece can be in pixels Y; in the upgrade piece frame
	 */
	private static int maxUpgradePieceSize_Y = 140;

	/**
	 * Show the specific piece in the tile
	 * Resizes and positions the image to fit perfectly within the tile
	 * 
	 * @param imageString
	 */
	private void assignImage(String imageString, int xPos, JButton upgradeButton) {

		ImageIcon imageToDisplay = getBoardImage(imageString);

		// Following Sizing is copy-paste from ImageViewer
		int original_X_size = imageToDisplay.getIconWidth();
		int original_Y_size = imageToDisplay.getIconHeight();
		int newHeight = original_Y_size;
		int newWidth = original_X_size;

		// Maintain Aspect Ratio, Following 2 if statements taken from
		// "https://stackoverflow.com/questions/10245220/"
		if (original_X_size > maxUpgradePieceSize_X) {
			newWidth = maxUpgradePieceSize_X;
			newHeight = (newWidth * original_Y_size) / original_X_size;
		}
		if (newHeight > maxUpgradePieceSize_Y) {
			newHeight = maxUpgradePieceSize_Y;
			newWidth = (newHeight * original_X_size) / original_Y_size;
		}

		int xPosition = UILibrary.resizeModule.scale_X(xPos - (newWidth / 2));
		int yPosition = UILibrary.resizeModule.scale_Y(154 - newHeight);
		newWidth = UILibrary.resizeModule.scale_X(newWidth);
		newHeight = UILibrary.resizeModule.scale_Y(newHeight);

		upgradeButton.setVisible(true);
		upgradeButton.setIcon(new ImageIcon(
				getBoardImage(imageString).getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT)));
		upgradeButton.setBounds(xPosition, yPosition, newWidth, newHeight);
		upgradeButton.setOpaque(false);
		upgradeButton.setContentAreaFilled(false);
		upgradeButton.setBorderPainted(false);
	}

	/**
	 * JButton associated for choosing to upgrade to a queen
	 */
	private static JButton upgradeQueenButton;

	/**
	 * JButton associated for choosing to upgrade to a rook
	 */
	private static JButton upgradeRookButton;

	/**
	 * JButton associated for choosing to upgrade to a knight
	 */
	private static JButton upgradeKnightButton;

	/**
	 * JButton associated for choosing to upgrade to a bishop
	 */
	private static JButton upgradeBishopButton;

	/**
	 * Creates the upgrade piece frame/popup
	 */
	private void createUpgradePieceFrame() {
		UILibrary.UpgradePieceFrame = new JLabel();
		UILibrary.resizeModule.setVariableBounds(UILibrary.UpgradePieceFrame, null, 177, 433, 624, 173); // Numbers from Figma Design
		UILibrary.resizeModule.setVariableBounds(UILibrary.UpgradePieceFrame, getImage("UpgradeFrameBackground.png"));

		upgradeQueenButton = new JButton();
		upgradeRookButton = new JButton();
		upgradeKnightButton = new JButton();
		upgradeBishopButton = new JButton();
		UILibrary.UpgradePieceFrame.add(upgradeQueenButton);
		UILibrary.UpgradePieceFrame.add(upgradeRookButton);
		UILibrary.UpgradePieceFrame.add(upgradeKnightButton);
		UILibrary.UpgradePieceFrame.add(upgradeBishopButton);

		UILibrary.MainFrame.add(UILibrary.UpgradePieceFrame);
		UILibrary.UpgradePieceFrame.setVisible(false);
	}

	// -----------------------------------------------------
	// -----------------------------------------------------

	private void removeActionListeners(JButton button) {
		for (ActionListener listener : button.getActionListeners()) {
			button.removeActionListener(listener);
		}
	}

	/* 
	/**
	 * Shows the upgrade piece frame
	 * 
	 * @param isWhite true = upgrading a white piece, false = upgrading a black piece
	 */
	private Object[] options = { "QUEEN", "ROOK", "BISHOP", "KNIGHT" };
	public String showUpgradeFrame(boolean isWhite) {

		Object result = JOptionPane.showInputDialog(null, "Choose a Piece to upgrade to.", "Upgrade Chess Piece",
            JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		
		System.out.println("Result: " + result + "\n");

		if (result == options[0])
			return "Queen";
		else if (result == options[1])
			return "Rook";
		else if (result == options[2])
			return "Bishop";
		else if (result == options[3])
			return "Knight";
		else
			return null;
	}

	// -----------------------------------------------------
	// -----------------------------------------------------

	/**
	 * Background for the EndGameFrame
	 */
	public static JLabel endLabel;
	/**
	 * Title show to the user in the end game frame, suggested test "Checkmate, You Win!", "You Lose", "Draw", etc
	 */
	public static JLabel endTitle;

	/**
	 * Create the EndGameFrame and its contents
	 */
	private void createEndGameUI() {
		endLabel = new JLabel();
		UILibrary.resizeModule.setVariableBounds(endLabel, null, 135, 387, 681, 259); // Numbers from Figma Design
		UILibrary.resizeModule.setVariableBounds(endLabel, getImage("EndGameFrame.png"));

		JLabel TextLabel = new JLabel("Game Over", SwingConstants.CENTER);
		UILibrary.resizeModule.setTextBounds(TextLabel, 20);
		TextLabel.setForeground(UILibrary.TextColor_White);
		UILibrary.resizeModule.setVariableBounds(TextLabel, null, 62, 24, 566, 34); // Numbers from Figma Design
		TextLabel.setOpaque(false);
		endLabel.add(TextLabel);

		endTitle = new JLabel("YOU WIN!", SwingConstants.CENTER);
		UILibrary.resizeModule.setTextBounds(endTitle, 50);
		endTitle.setForeground(UILibrary.TextColor_White);
		UILibrary.resizeModule.setVariableBounds(endTitle, null, 62, 64, 566, 71); // Numbers from Figma Design
		endTitle.setOpaque(false);
		endLabel.add(endTitle);

		UILibrary.endRematchButton = new JButton();
		UILibrary.resizeModule.setVariableBounds(UILibrary.endRematchButton, null, 37, 155, 183, 79);
		UILibrary.resizeModule.setVariableBounds(UILibrary.endRematchButton, getImage("RematchButton.png"));
		UILibrary.endRematchButton.setOpaque(false);
		UILibrary.endRematchButton.setContentAreaFilled(false);
		UILibrary.endRematchButton.setBorderPainted(false);

		UILibrary.endViewBoardButton = new JButton();
		UILibrary.resizeModule.setVariableBounds(UILibrary.endViewBoardButton, null, 249, 155, 183, 79);
		UILibrary.resizeModule.setVariableBounds(UILibrary.endViewBoardButton, getImage("ViewboardButton.png"));
		UILibrary.endViewBoardButton.setOpaque(false);
		UILibrary.endViewBoardButton.setContentAreaFilled(false);
		UILibrary.endViewBoardButton.setBorderPainted(false);

		UILibrary.endNewGameButton = new JButton();
		UILibrary.resizeModule.setVariableBounds(UILibrary.endNewGameButton, null, 461, 155, 183, 79);
		UILibrary.resizeModule.setVariableBounds(UILibrary.endNewGameButton, getImage("NewGameButton.png"));
		UILibrary.endNewGameButton.setOpaque(false);
		UILibrary.endNewGameButton.setContentAreaFilled(false);
		UILibrary.endNewGameButton.setBorderPainted(false);

		endLabel.add(UILibrary.endRematchButton);
		endLabel.add(UILibrary.endViewBoardButton);
		endLabel.add(UILibrary.endNewGameButton);

		endLabel.setVisible(false);
		UILibrary.MainFrame.add(endLabel);
	}

	// -----------------------------------------------------
	// -----------------------------------------------------

	/**
	 * Calls the functions to create the accessory Frames, upgrade a piece, end game frame.
	 */
	public CreateAccessoryUIs() {
		// Upgrade Piece
		createUpgradePieceFrame();

		// End Game
		createEndGameUI();
	}

}
