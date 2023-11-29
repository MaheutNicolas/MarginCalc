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

public class ItemPage extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8664504444692729122L;
	
	static int chargeRef;
	static ArrayList<String> chargeStr;
	static ArrayList<Integer> chargeInt;
	JButton itemPageAdderB;
	JButton itemPageSaveButton;
	JScrollPane itemPageScrollP;
	JTextField itemPAdderText1;
	JTextField itemPAdderText2;
	JTextArea itemPageList;
	JLabel itemPageDescriptif;
	JLabel itemAdderName;
	JLabel itemAdderNameT1;
	JLabel itemAdderNameT2;
	JPanel itemPage;
	JPanel itemPageAdder;
	
	//the class govern the first page, it's take the input of charge and class it in an array ( in the form of a JTextArea )

	ItemPage() {

		chargeRef = 25;

		chargeStr = new ArrayList<String>();
		chargeInt = new ArrayList<Integer>();

		itemPageSaveButton = new JButton("Sauvegarder");
		itemPageSaveButton.setSize(new Dimension(50, 20));
		itemPageSaveButton.addActionListener(this);
		itemPageSaveButton.setFont(new Font("Consolas 10", Font.PLAIN, 30));
		itemPageSaveButton.setFocusable(false);

		itemPageDescriptif = new JLabel(" Nom                                 Valeur                ");
		itemPageDescriptif.setFont(new Font("Consolas 10", Font.PLAIN, 30));

		itemPageList = new JTextArea();
		itemPageList.setLineWrap(true);
		itemPageList.setWrapStyleWord(true);
		itemPageList.setFont(new Font("Courier New", Font.PLAIN, 25));

		itemPageScrollP = new JScrollPane(itemPageList);
		itemPageScrollP.setPreferredSize(new Dimension(1050, 620));
		itemPageScrollP.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		itemPage = new JPanel();
		itemPage.setLayout(new FlowLayout(10, 10, 10));
		itemPage.add(itemPageDescriptif);
		itemPage.add(itemPageSaveButton);
		itemPage.add(itemPageScrollP);
		itemPage.setBackground(Color.lightGray);
		itemPage.setPreferredSize(new Dimension(1100, 600));
		
		// ----- the second panel - the panel who in charge of add charge 
		
		itemAdderName = new JLabel(" Ajouter des valeurs :");
		itemAdderName.setFont(new Font("Consolas 10", Font.PLAIN, 30));

		itemAdderNameT1 = new JLabel("Nom du produit :");
		itemAdderNameT1.setFont(new Font("Consolas 10", Font.PLAIN, 30));

		itemPAdderText1 = new JTextField();
		itemPAdderText1.setPreferredSize(new Dimension(200, 50));
		itemPAdderText1.setFont(new Font("Consolas 10", Font.PLAIN, 30));
		
		itemAdderNameT2 = new JLabel("Valeur à l'unité et en centime :");
		itemAdderNameT2.setFont(new Font("Consolas 10", Font.PLAIN, 26));

		itemPAdderText2 = new JTextField();
		itemPAdderText2.setPreferredSize(new Dimension(200, 50));
		itemPAdderText2.setFont(new Font("Consolas 10", Font.PLAIN, 30));
		
		itemPageAdderB = new JButton("Ajouter charge");
		itemPageAdderB.addActionListener(this);
		itemPageAdderB.setFont(new Font("Consolas 10", Font.PLAIN, 30));

		itemPageAdder = new JPanel();
		itemPageAdder.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 40));
		itemPageAdder.add(itemAdderName);
		itemPageAdder.setBackground(Color.lightGray);
		itemPageAdder.add(itemAdderNameT1);
		itemPageAdder.add(itemPAdderText1);
		itemPageAdder.add(itemAdderNameT2);
		itemPageAdder.add(itemPAdderText2);
		itemPageAdder.add(itemPageAdderB);
		itemPageAdder.setPreferredSize(new Dimension(500, 900));
		
		this.setLayout(new BorderLayout(10, 10));
		this.add(itemPage,BorderLayout.WEST);
		this.add(itemPageAdder, BorderLayout.EAST);
		
		loadChargeFromSave();
		loadCharge();
	}
	
	//Verifies if the text are correct and complete, before call the adder method 
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == itemPageAdderB && !itemPAdderText1.getText().isEmpty()
				&& !itemPAdderText2.getText().isEmpty()) {
			boolean valid = true;
			try {
				chargeInt.add(Integer.valueOf(itemPAdderText2.getText()));
			} catch (NumberFormatException e1) {
				valid = false;
			}
			if(valid == true) {
			chargeStr.add(itemPAdderText1.getText());
			itemPAdderText1.setText("");
			itemPAdderText2.setText("");
			loadCharge();
			}
		}
		if (e.getSource() == itemPageSaveButton) {
			saveCharge();
			saveChargeToSave();
		}
	}
	//this part is dedicated to the memory / load and save 
	//save the charge who is in the textArea in the program memory ( chargeStr and chargeInt )
	public void saveCharge() {

		String str = itemPageList.getText();
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
	//transfer the memory ( chargeStr and chargeInt ) in the textArea
	public void loadCharge() {

		String str1 = "";
		for (int i = 0; i < chargeStr.size(); i++) {
			str1 = str1 + " " + chargeStr.get(i);
			for (int j = 0; j < chargeRef - chargeStr.get(i).length(); j++) {
				str1 = str1 + " ";
			}
			str1 = str1 + chargeInt.get(i) + "\n";
		}
		itemPageList.setText(str1);
	}
	//save the charge in the save file from the memory ( chargeStr and chargeInt )
	public void saveChargeToSave() {
		try {
			File fileS = new File(MainCalcMarge.path+"\\SaveCharge.txt");
			PrintWriter save = new PrintWriter(fileS.getAbsolutePath());
			for (int i = 0; i < chargeStr.size(); i++) {
				save.print(chargeStr.get(i) + "\n");
				save.print(chargeInt.get(i) + "\n");
			}
			save.close();
		} catch (FileNotFoundException e) {
			System.out.println("file not found (item charge)");
		}
	}
	//load the charge from the save to the memory ( chargeStr and chargeInt )
	public void loadChargeFromSave() {

		File fileS = new File(MainCalcMarge.path+"\\SaveCharge.txt");
		Scanner sc;
		try {
			sc = new Scanner(new File(fileS.getAbsolutePath()));
			while (sc.hasNext()) {
				String str = sc.nextLine();
				chargeStr.add(str);
				str = sc.nextLine();
				chargeInt.add(Integer.valueOf(str));
			}
			sc.close();

		} catch (FileNotFoundException e) {
			System.out.println("file not found (item charge)");
		} catch (NoSuchElementException e) {
			System.out.println("blank file (item charge)");
		}
	}
}
