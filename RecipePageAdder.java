package calculMPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class RecipePageAdder extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5050529136944715539L;

	int height;
	int individualHeight = 50;
	final static int width = 500;
	JScrollPane scrollPaneAdder;
	JPanel panelAdder;
	JTextField nameField;
	JTextField priceField;
	JButton validEntryButton;
	JButton deleteRecipeButton;
	JRadioButton[] radioButtonArray;
	ArrayList<Integer> referenceList;
	String name;
	boolean autoUpdateFile;

	// this part organize the right panel in the RecipePage
	public RecipePageAdder(String name, String price, int[] reference, boolean changeRecipeVerif,
			boolean autoUpdateFile) {

		this.name = name;
		this.autoUpdateFile = autoUpdateFile;

		setBackground(Color.LIGHT_GRAY);
		setPreferredSize(new Dimension(500, 700));

		scrollPaneAdder = new JScrollPane();
		scrollPaneAdder.setViewportBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPaneAdder.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneAdder.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneAdder.setPreferredSize(new Dimension(500, 680));
		scrollPaneAdder.getVerticalScrollBar().setUnitIncrement(16);
		add(scrollPaneAdder);

		panelAdder = new JPanel();
		panelAdder.setBorder(UIManager.getBorder("TextPane.border"));
		panelAdder.setBackground(Color.LIGHT_GRAY);
		scrollPaneAdder.setViewportView(panelAdder);

		JLabel nameLabel = new JLabel("Nom :");
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		nameLabel.setPreferredSize(new Dimension(400, 50));
		panelAdder.add(nameLabel);

		nameField = new JTextField();
		nameField.setFont(new Font("Tahoma", Font.PLAIN, 30));
		nameField.setPreferredSize(new Dimension(300, 50));
		panelAdder.add(nameField);

		JLabel priceLabel = new JLabel("Prix :");
		priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		priceLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		priceLabel.setPreferredSize(new Dimension(400, 50));
		panelAdder.add(priceLabel);

		priceField = new JTextField();
		priceField.setFont(new Font("Tahoma", Font.PLAIN, 30));
		priceField.setPreferredSize(new Dimension(300, 50));
		panelAdder.add(priceField);
		// add an array of radio button and call the method loadNameRButton for take the
		// name
		radioButtonArray = new JRadioButton[ItemPage.chargeStr.size()];
		for (int i = 0; i < ItemPage.chargeStr.size(); i++) {
			radioButtonArray[i] = new JRadioButton(loadNameRButton(i));
			radioButtonArray[i].setFont(new Font("Courier New", Font.PLAIN, 15));
			radioButtonArray[i].setPreferredSize(new Dimension(300, 50));
			panelAdder.add(radioButtonArray[i]);
		}
		validEntryButton = new JButton("Valider");
		validEntryButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		validEntryButton.setFocusable(false);
		validEntryButton.setPreferredSize(new Dimension(300, 50));
		validEntryButton.addActionListener(this);
		panelAdder.add(validEntryButton);
		// same compute than before ( RecipePage )
		height = individualHeight * (ItemPage.chargeStr.size() + 8);
		panelAdder.setPreferredSize(new Dimension(width, height));
		// set the name price and the selected button with the parameter
		nameField.setText(name);
		priceField.setText(price);
		for (int i = 0; i < reference.length; i++) {
			radioButtonArray[reference[i]].setSelected(true);
		}
		// 1st boolean
		if (changeRecipeVerif == true) {
			deleteRecipeButton = new JButton("Supprimer");
			deleteRecipeButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
			deleteRecipeButton.setFocusable(false);
			deleteRecipeButton.setPreferredSize(new Dimension(300, 50));
			deleteRecipeButton.addActionListener(this);
			panelAdder.add(deleteRecipeButton);
		}
		// 2nd boolean
		if (autoUpdateFile == true) {
			newRecipe();
		}

	}

	// take the info of chargeStr and chargeInt and structure it in a String
	public String loadNameRButton(int i) {

		String str1 = "";
		str1 = str1 + " " + ItemPage.chargeStr.get(i);
		for (int j = 0; j < 22 - ItemPage.chargeStr.get(i).length(); j++) {
			str1 = str1 + " ";
		}
		str1 = str1 + ItemPage.chargeInt.get(i) + "\n";
		return str1;

	}

	// save the class in a serialized version before dispose of the frame and open a
	// new one
	// in this method, the loadRecipe ( in the RecipePage class ) will be reload
	// with a new file and a new button
	public void saveRecipe(String name, int price, int[] reference, String[] item, int[] cost) {
		RecipeClass recipe = new RecipeClass(name, price, reference, item, cost);
		try {
			File file = new File(MainCalcMarge.path+"\\SaveRecipe");
			File save = new File(file + "\\" + recipe.name + ".ser");
			FileOutputStream fileOut = new FileOutputStream(save);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(recipe);
			out.close();
			fileOut.close();

			if (autoUpdateFile == false) {
				MainCalcMarge.recipePageButton.doClick();
			}
		} catch (IOException e) {
			System.out.println("recipe non saved");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// check if the recipe info are good and save it
		if (e.getSource() == validEntryButton) {
			try {
				if (!nameField.getText().isEmpty() || !priceField.getText().isEmpty()) {
					for (int i = 0; i < radioButtonArray.length; i++) {
						if (radioButtonArray[i].isSelected()) {
							newRecipe();
							break;
						}
					}
				}
			} catch (NumberFormatException x) {
			}
		}

		// delete a file who carry the info and reload the frame
		if (e.getSource() == deleteRecipeButton)

		{
			File file = new File("SaveRecipe\\" + name + ".ser");
			file.delete();
			MainCalcMarge.recipePageButton.doClick();
		}
	}
	//organize and filter the data of this class( store them in a compact version ) before add the FixedItemPage charge
	public void newRecipe() {
		referenceList = new ArrayList<>();
		String name = nameField.getText();
		int price = Integer.valueOf(priceField.getText());
		for (int i = 0; i < radioButtonArray.length; i++) {
			if (radioButtonArray[i].isSelected()) {
				referenceList.add(i);
			}
		}
		final int quickCalc = referenceList.size()+FixedItemPage.chargeStr.size();
		String[] item = new String[quickCalc];
		int[] cost = new int[quickCalc];
		int[] ref = new int[referenceList.size()];
		for (int i = 0; i < referenceList.size(); i++) {
			ref[i] = referenceList.get(i);
			item[i] = ItemPage.chargeStr.get(referenceList.get(i));
			cost[i] = ItemPage.chargeInt.get(referenceList.get(i));
		}
		for (int i = 0; i < FixedItemPage.chargeStr.size(); i++) {
			item[i+referenceList.size()] = FixedItemPage.chargeStr.get(i);
			cost[i+referenceList.size()] = FixedItemPage.chargeInt.get(i)/FixedItemPage.chargeNumOfMeal;
		}
		saveRecipe(name, price, ref, item, cost);
	}
}
