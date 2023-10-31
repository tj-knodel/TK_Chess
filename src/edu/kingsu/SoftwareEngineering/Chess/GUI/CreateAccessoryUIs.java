package edu.kingsu.SoftwareEngineering.Chess.GUI;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Image;

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
		return new ImageIcon(getClass().getClassLoader().getResource(UILibrary.boardAppearanceFolder + imageToGet));
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

		int xPosition = xPos - (newWidth / 2);
		int yPosition = 154 - newHeight;

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
		UILibrary.UpgradePieceFrame.setBounds(177, 433, 624, 173); // Numbers from Figma Design
		UILibrary.UpgradePieceFrame.setIcon(
				new ImageIcon(getImage("UpgradeFrameBackground.png").getImage().getScaledInstance(624, 173,
						Image.SCALE_DEFAULT)));

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

	/**
	 * Shows the upgrade piece frame
	 * 
	 * @param isWhite true = upgrading a white piece, false = upgrading a black
	 *                piece
	 */
	public void showUpgradeFrame(boolean isWhite) {
		String text = (isWhite) ? "white" : "black";

		assignImage("queen_" + text + ".png", 89, upgradeQueenButton);
		assignImage("rook_" + text + ".png", 231, upgradeRookButton);
		assignImage("knight_" + text + ".png", 393, upgradeKnightButton);
		assignImage("bishop_" + text + ".png", 541, upgradeBishopButton);
		UILibrary.UpgradePieceFrame.setVisible(true);
	}

	// -----------------------------------------------------
	// -----------------------------------------------------

	/**
	 * Adds the action listeners which fires a event to GUI_Events when a user
	 * selects which piece to upgrade to.
	 */
	private void addUpgradeButtonsActionListeners() {
		upgradeQueenButton.addActionListener(e -> {
			UILibrary.UpgradePieceFrame.setVisible(false);
			GUI_Events.upgradeChessPieceWasClicked("queen");
		});
		upgradeBishopButton.addActionListener(e -> {
			UILibrary.UpgradePieceFrame.setVisible(false);
			GUI_Events.upgradeChessPieceWasClicked("bishop");
		});
		upgradeRookButton.addActionListener(e -> {
			UILibrary.UpgradePieceFrame.setVisible(false);
			GUI_Events.upgradeChessPieceWasClicked("rook");
		});
		upgradeKnightButton.addActionListener(e -> {
			UILibrary.UpgradePieceFrame.setVisible(false);
			GUI_Events.upgradeChessPieceWasClicked("knight");
		});
	}

	// -----------------------------------------------------
	// -----------------------------------------------------

	/**
	 * TODO
	 */
	private static JLabel endLabel;
	/**
	 * TODO
	 */
	private static JLabel endTitle;
	/**
	 * TODO
	 */
	private static JButton endRematchButton;
	/**
	 * TODO
	 */
	private static JButton endViewBoardButton;
	/**
	 * TODO
	 */
	private static JButton endNewGameButton;

	/**
	 * TODO
	 */
	private void createEndGameUI() {

	}

	// -----------------------------------------------------
	// -----------------------------------------------------

	/**
	 * Calls the functions to create the accessory Frames, upgrade a piece, end game frame.
	 */
	public CreateAccessoryUIs() {
		createUpgradePieceFrame();
		addUpgradeButtonsActionListeners();

	}

}
