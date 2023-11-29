package calculMPackage;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JTextArea;

public class StatisticPage extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3929091309749044653L;

	JScrollPane scrollPane;
	JPanel listPanel;
	JPanel viewPanel;
	JButton[] arrayMenuButton;
	JButton statGeneralButton;
	JScrollPane textscrollPane;
	JTextArea textArea;
	RecipeClass[] recipe;
	int height;
	int individualHeight = 50;
	final static int width = 500;
	String[] nameShorted;
	static ArrayList<String> fixedChargeStr;
	static ArrayList<Integer> fixedChargeInt;
	int totalFixedCharge;
	double margeAverage;
	double rentabilityThreshold;
	int averageTicket;
	int chargeNumOfMeal;

	// the 3rd page who rule the collected data and show the important details about
	// the recipe
	public StatisticPage() {

		setLayout(null);
		setPreferredSize(new Dimension(1600, 600));
		loadRecip();
		loadChargeFromSave();

		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 500, 700);
		scrollPane.setViewportBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		add(scrollPane);

		listPanel = new JPanel();
		listPanel.setBackground(Color.lightGray);
		listPanel.setBounds(389, 435, 83, 110);
		listPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		scrollPane.setViewportView(listPanel);

		textArea = new JTextArea();
		textArea.setBounds(510, 0, 1020, 690);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("Courier New", Font.PLAIN, 25));
		textArea.setEditable(false);

		textscrollPane = new JScrollPane();
		textscrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		textscrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		textscrollPane.getVerticalScrollBar().setUnitIncrement(16);
		textscrollPane.setViewportView(textArea);
		textscrollPane.setBounds(510, 0, 1020, 690);
		add(textscrollPane);

		statGeneralButton = new JButton("Statistique");
		statGeneralButton.setFont(new Font("Courier New", Font.BOLD, 25));
		statGeneralButton.setPreferredSize(new Dimension(300, 50));
		statGeneralButton.addActionListener(this);
		statGeneralButton.setFocusable(false);
		listPanel.add(statGeneralButton);
		if (recipe != null) {
			arrayMenuButton = new JButton[recipe.length];
			for (int i = 0; i < arrayMenuButton.length; i++) {
				arrayMenuButton[i] = new JButton(recipe[i].name);
				arrayMenuButton[i].setFont(new Font("Courier New", Font.PLAIN, 25));
				arrayMenuButton[i].setPreferredSize(new Dimension(300, 50));
				arrayMenuButton[i].addActionListener(this);
				arrayMenuButton[i].setFocusable(false);
				listPanel.add(arrayMenuButton[i]);

			}
			height = individualHeight * (arrayMenuButton.length);
			listPanel.setPreferredSize(new Dimension(width, height));
		}

		setVisible(true);

	}

	// load recipe, the data is in the class and more easily accessible
	private void loadRecip() {

		File file = new File(MainCalcMarge.path + "\\SaveRecipe");
		if (file.listFiles() != null) {
			File[] files = file.listFiles();
			recipe = new RecipeClass[files.length];
			for (int i = 0; i < recipe.length; i++) {

				FileInputStream fileIn;
				try {
					fileIn = new FileInputStream(files[i]);
					ObjectInputStream in = new ObjectInputStream(fileIn);
					recipe[i] = (RecipeClass) in.readObject();
					recipe[i].initialize();
					in.close();
					fileIn.close();

				} catch (FileNotFoundException e) {
					System.out.println("file not found (recipe files)");

				} catch (IOException e) {
					System.out.println("blank file  (recipe files)");

				} catch (ClassNotFoundException e) {
					System.out.println("class not found ( recipe )");
				}
			}
		}
	}

	// when a button is click, the program transfer the data from the class to the
	// textArea
	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < arrayMenuButton.length; i++) {
			if (e.getSource() == arrayMenuButton[i]) {
				String text = "Nom : " + recipe[i].name + "\n";
				text += "Prix : " + recipe[i].price + "\n";
				text += "TVA : 10%      TVA : " + recipe[i].price / 10 + "\n";
				text += "Prix sans la TVA : " + recipe[i].priceWithoutTax + "\n";
				text += "Marge : " + recipe[i].marge + "    Pourcentage :" + recipe[i].margeInPercent + "%\n";
				BigDecimal a = new BigDecimal("100");
				text += "cout total : " + recipe[i].costTotal + "    Pourcentage :"
						+ (a.subtract(recipe[i].margeInPercent)) + "%\n\n";
				text += "Details des coûts :\n";
				text += "nombre de coût : " + recipe[i].cost.length + "\n";
				text += "Moyen des coût : " + recipe[i].mediumCost + "\n\n";
				text += "Noms :            Coût :            %Coût :         Coût/moyen:\n\n";
				for (int j = 0; j < recipe[i].cost.length; j++) {
					for (int y = 0; y < 3; y++) {
						String word = "";
						if (y == 0) {
							word = recipe[i].item[j];
						}
						if (y == 1) {
							word = String.valueOf(recipe[i].cost[j]);
						}
						if (y == 2) {
							word = String.valueOf(recipe[i].costInPercent[j].doubleValue()) + "%";
						}
						text += word;
						for (int x = word.length(); x < 18; x++) {
							text += " ";
						}
					}
					text += String.valueOf(recipe[i].MediumCostDiff[j]) + "\n";
				}
				textArea.setText(text);
			}
		}
		if (e.getSource() == statGeneralButton) {
			String text = "";
			// -----------Best Margin----------
			int posInList = 1;
			nameShorted = new String[recipe.length];
			int[] margeShorted = new int[recipe.length];
			for (int i = 0; i < recipe.length; i++) {
				margeShorted[i] = recipe[i].marge;
				nameShorted[i] = recipe[i].name;
			}
			margeShorted = selectionSort(margeShorted);
			text += "Meilleurs Marge : \n";
			for (int i = 0; i < nameShorted.length; i++) {
				text += (posInList++) + ".  " + nameShorted[i] + "  " + margeShorted[i] + "\n";
			}
			// ---------best Margin in %------
			text += "\n";
			posInList = 1;
			nameShorted = new String[recipe.length];
			double[] margePersentShorted = new double[recipe.length];
			for (int i = 0; i < recipe.length; i++) {
				margePersentShorted[i] = recipe[i].margeInPercent.doubleValue();
				nameShorted[i] = recipe[i].name;
			}
			margePersentShorted = selectionSort(margePersentShorted);
			text += "Meilleurs Marge en % : \n";
			for (int i = 0; i < margePersentShorted.length; i++) {
				text += (posInList++) + ".  " + nameShorted[i] + "  " + margePersentShorted[i] + "\n";
			}
			text += "\n" + "Analyse charges fixes :\n";
			text += "Taux de marges variables moyen :" + (double) Math.round(margeAverage * 100) / 100 + "\n";
			text += "Coûts des charges fixes total : " + totalFixedCharge + "\n";
			text += "seuil de rentabilité : " + (double) Math.round(rentabilityThreshold * 100) + "\n";
			text += "ticket moyen :" + (averageTicket) + "\n";
			text += "Nombre de repas pour atteindre le seuil :"
					+ (double) Math.round((rentabilityThreshold / averageTicket) * 100) + "\n";

			textArea.setText(text);

		}
	}

	private int[] selectionSort(int[] array) {

		for (int i = 0; i < array.length - 1; i++) {
			int max = i;
			for (int j = i + 1; j < array.length; j++) {
				if (array[max] < array[j]) {
					max = j;
				}
			}
			String tempname = nameShorted[i];
			nameShorted[i] = nameShorted[max];
			nameShorted[max] = tempname;

			int temp = array[i];
			array[i] = array[max];
			array[max] = temp;
		}
		return array;
	}

	private double[] selectionSort(double[] array) {

		for (int i = 0; i < array.length - 1; i++) {
			int max = i;
			for (int j = i + 1; j < array.length; j++) {
				if (array[max] < array[j]) {
					max = j;
				}
			}
			String tempname = nameShorted[i];
			nameShorted[i] = nameShorted[max];
			nameShorted[max] = tempname;

			double temp = array[i];
			array[i] = array[max];
			array[max] = temp;
		}
		return array;
	}

	public void loadChargeFromSave() {
		fixedChargeStr = new ArrayList<String>();
		fixedChargeInt = new ArrayList<Integer>();
		File fileS = new File(MainCalcMarge.path + "\\SaveFixedCharge.txt");
		Scanner sc;
		try {
			sc = new Scanner(new File(fileS.getAbsolutePath()));
			String str = sc.nextLine();
			chargeNumOfMeal = Integer.valueOf(str);
			while (sc.hasNext()) {
				str = sc.nextLine();
				fixedChargeStr.add(str);
				str = sc.nextLine();
				fixedChargeInt.add(Integer.valueOf(str));
			}
			sc.close();

		} catch (FileNotFoundException e) {
			System.out.println("file not found  (fixed charge)");
		} catch (NoSuchElementException e) {
			System.out.println("blank file  (fixed charge)");
		}
		if (recipe != null) {
			totalFixedCharge = 0;
			for (int i = 0; i < fixedChargeInt.size(); i++) {
				totalFixedCharge += fixedChargeInt.get(i);
			}
			for (int i = 0; i < recipe.length; i++) {
				margeAverage += recipe[i].margeVariableInPercent.doubleValue();
				averageTicket += recipe[i].priceWithoutTax;
			}
			if (recipe.length > 0) {
				margeAverage = margeAverage / recipe.length;
				rentabilityThreshold = totalFixedCharge / margeAverage;
				averageTicket = averageTicket / recipe.length;
			}
		}

	}
}