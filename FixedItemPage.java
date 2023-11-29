package calculMPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class FixedItemPage extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4733463548937282114L;
	static int chargeRef;
	static ArrayList<String> chargeStr;
	static ArrayList<Integer> chargeInt;
	static int chargeNumOfMeal;
	JLabel nameNumOfMeal;
	JTextField textNumOfMeal;
	JButton fixedPageAdderB;
	JButton fixedPageSaveButton;
	JScrollPane fixedPageScrollP;
	JTextField fixedPAdderText1;
	JTextField fixedPAdderText2;
	JTextArea fixedPageList;
	JLabel fixedPageDescriptif;
	JLabel fixedAdderName;
	JLabel fixedAdderNameT1;
	JLabel fixedAdderNameT2;
	JLabel fixedAdderNameT3;
	JPanel fixedPage;
	JPanel fixedPageAdder;
	
	//Class who reign over the Fixed charge and add them directly in the RecipeClass ( RecipeClassAdder.newRecipe() / add them after the ref charge )

	FixedItemPage() {

		chargeRef = 25;

		chargeStr = new ArrayList<String>();
		chargeInt = new ArrayList<Integer>();
		
		nameNumOfMeal = new JLabel("Nombres de Repas sur une période :");
		nameNumOfMeal.setFont(new Font("Consolas 10", Font.PLAIN, 30));
		
		textNumOfMeal = new JTextField();
		textNumOfMeal.setPreferredSize(new Dimension(200, 50));
		textNumOfMeal.setFont(new Font("Consolas 10", Font.PLAIN, 30));

		fixedPageSaveButton = new JButton("Sauvegarder");
		fixedPageSaveButton.setSize(new Dimension(50, 20));
		fixedPageSaveButton.addActionListener(this);
		fixedPageSaveButton.setFont(new Font("Consolas 10", Font.PLAIN, 30));
		fixedPageSaveButton.setFocusable(false);

		fixedPageDescriptif = new JLabel(" Nom                                 Valeur                           ");
		fixedPageDescriptif.setFont(new Font("Consolas 10", Font.PLAIN, 30));

		fixedPageList = new JTextArea();
		fixedPageList.setLineWrap(true);
		fixedPageList.setWrapStyleWord(true);
		fixedPageList.setFont(new Font("Courier New", Font.PLAIN, 25));

		fixedPageScrollP = new JScrollPane(fixedPageList);
		fixedPageScrollP.setPreferredSize(new Dimension(1050, 560));
		fixedPageScrollP.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		fixedPage = new JPanel();
		fixedPage.setLayout(new FlowLayout(10, 10, 10));
		fixedPage.add(nameNumOfMeal);
		fixedPage.add(textNumOfMeal);
		fixedPage.add(fixedPageDescriptif);
		fixedPage.add(fixedPageSaveButton);
		fixedPage.add(fixedPageScrollP);
		fixedPage.setBackground(Color.lightGray);
		fixedPage.setPreferredSize(new Dimension(1100, 600));

		fixedAdderName = new JLabel(" Ajouter des valeurs :");
		fixedAdderName.setFont(new Font("Consolas 10", Font.PLAIN, 30));

		fixedAdderNameT1 = new JLabel("Nom du produit :");
		fixedAdderNameT1.setFont(new Font("Consolas 10", Font.PLAIN, 30));

		fixedPAdderText1 = new JTextField();
		fixedPAdderText1.setPreferredSize(new Dimension(200, 50));
		fixedPAdderText1.setFont(new Font("Consolas 10", Font.PLAIN, 30));

		fixedAdderNameT2 = new JLabel("Valeur sur la période  et ");
		fixedAdderNameT2.setFont(new Font("Consolas 10", Font.PLAIN, 26));
		fixedAdderNameT2.setPreferredSize(new Dimension(300, 20));
		
		fixedAdderNameT3 = new JLabel(" et en centime :");
		fixedAdderNameT3.setFont(new Font("Consolas 10", Font.PLAIN, 26));
		fixedAdderNameT3.setPreferredSize(new Dimension(300, 20));

		fixedPAdderText2 = new JTextField();
		fixedPAdderText2.setPreferredSize(new Dimension(200, 50));
		fixedPAdderText2.setFont(new Font("Consolas 10", Font.PLAIN, 30));

		fixedPageAdderB = new JButton("Ajouter charge fixe");
		fixedPageAdderB.addActionListener(this);
		fixedPageAdderB.setFont(new Font("Consolas 10", Font.PLAIN, 30));

		fixedPageAdder = new JPanel();
		fixedPageAdder.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 25));
		fixedPageAdder.add(fixedAdderName);
		fixedPageAdder.setBackground(Color.lightGray);
		fixedPageAdder.add(fixedAdderNameT1);
		fixedPageAdder.add(fixedPAdderText1);
		fixedPageAdder.add(fixedAdderNameT2);
		fixedPageAdder.add(fixedAdderNameT3);
		fixedPageAdder.add(fixedPAdderText2);
		fixedPageAdder.add(fixedPageAdderB);
		fixedPageAdder.setPreferredSize(new Dimension(500, 900));

		this.setLayout(new BorderLayout(10, 10));
		this.add(fixedPage, BorderLayout.WEST);
		this.add(fixedPageAdder, BorderLayout.EAST);
		
		loadChargeFromSave();
		loadCharge();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == fixedPageAdderB && !fixedPAdderText1.getText().isEmpty()
				&& !fixedPAdderText2.getText().isEmpty()) {
			boolean valid = true;
			try {
				chargeInt.add(Integer.valueOf(fixedPAdderText2.getText()));
			} catch (NumberFormatException e1) {
				valid = false;
			}
			if (valid == true) {
				chargeStr.add(fixedPAdderText1.getText());
				fixedPAdderText1.setText("");
				fixedPAdderText2.setText("");
				loadCharge();
			}
		}
		if (e.getSource() == fixedPageSaveButton) {
			saveCharge();
			saveChargeToSave();
		}
	}
	//this part is dedicated to the memory / load and save 
	//save the charge who is in the textArea in the program memory ( chargeStr and chargeInt )
	public void saveCharge() {

		String str = fixedPageList.getText();
		chargeStr.clear();
		chargeInt.clear();
		while (str.length() > 0) {
			String str1 = str.substring(0, chargeRef - 5).trim();
			str = str.substring(chargeRef - 5, str.length()).trim();
			int ref = str.indexOf('\n');
			if (ref == -1) {
				ref = str.length();
			}
			int str2 = Integer.valueOf(str.substring(0, ref).trim());
			str = str.substring(ref, str.length());
			chargeStr.add(str1);
			chargeInt.add(str2);
		}
		loadCharge();
	}

	// transfer the memory ( chargeStr and chargeInt ) in the textArea
	public void loadCharge() {

		String str1 = "";
		for (int i = 0; i < chargeStr.size(); i++) {
			str1 = str1 + " " + chargeStr.get(i);
			for (int j = 0; j < chargeRef - chargeStr.get(i).length(); j++) {
				str1 = str1 + " ";
			}
			str1 = str1 + chargeInt.get(i) + "\n";
		}
		fixedPageList.setText(str1);
	}

	// save the charge in the save file from the memory ( chargeStr and chargeInt )
	public void saveChargeToSave() {
		try {
			File fileS = new File(MainCalcMarge.path+"\\SaveFixedCharge.txt");
			PrintWriter save = new PrintWriter(fileS.getAbsolutePath());
			if(textNumOfMeal.getText().length() < 1){
				textNumOfMeal.setText("0");
			}
			chargeNumOfMeal = Integer.valueOf(textNumOfMeal.getText());
			save.print(chargeNumOfMeal+ "\n");
			for (int i = 0; i < chargeStr.size(); i++) {
				save.print(chargeStr.get(i) + "\n");
				save.print(chargeInt.get(i) + "\n");
			}
			save.close();
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
		}
	}

	// load the charge from the save to the memory ( chargeStr and chargeInt )
	public void loadChargeFromSave() {

		File fileS = new File(MainCalcMarge.path+"\\SaveFixedCharge.txt");
		Scanner sc;
		try {
			sc = new Scanner(new File(fileS.getAbsolutePath()));
			String str = sc.nextLine();
			chargeNumOfMeal = Integer.valueOf(str);
			textNumOfMeal.setText(str);
			while (sc.hasNext()) {
				str = sc.nextLine();
				chargeStr.add(str);
				str = sc.nextLine();
				chargeInt.add(Integer.valueOf(str));
			}
			sc.close();

		} catch (FileNotFoundException e) {
			System.out.println("file not found (fixed charge)");
		} catch (NoSuchElementException e) {
			System.out.println("blank file  (fixed charge)");
		}
	}
}
