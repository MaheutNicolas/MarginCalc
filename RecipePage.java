package calculMPackage;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class RecipePage extends JPanel implements ActionListener {

	private static final long serialVersionUID = -5027449941402692433L;

	int height;
	int individualHeight = 50;
	final static int width = 1020;
	JScrollPane scrollPane;
	JScrollBar scrollBar;
	JPanel listPanel;
	JPanel panel;
	JPanel panelAdder;
	RecipePageAdder recipeAdder;
	JButton refreshButton;
	JButton updateAutoButton;
	RecipeClass[] recipe;
	JButton[] recipeButtton;

	// the second page who govern the recipe modification / create and save recipe

	public RecipePage() {

		loadRecip();

		// create a new Recipe Adder who take ( the name, the price, the position of the
		// selected button / Important to save charge correctly,
		// the first boolean is here to verify if the recipe is here to be modified /add
		// the deleteButton if true
		// the second boolean is here to verify if the user want to resave all recipe /
		// it's charge and save with the modified charge
		recipeAdder = new RecipePageAdder("", "", new int[0], false, false);

		this.setBackground(Color.lightGray);
		this.setLayout(new BorderLayout(10, 10));

		listPanel = new JPanel();
		listPanel.setLayout(new FlowLayout(10, 10, 10));
		listPanel.setBackground(Color.lightGray);
		listPanel.setPreferredSize(new Dimension(1100, 600));
		add(listPanel);

		refreshButton = new JButton("Recharger");
		refreshButton.addActionListener(this);
		refreshButton.setPreferredSize(new Dimension(200, 50));
		refreshButton.setFont(new Font("Consolas 10", Font.PLAIN, 25));
		refreshButton.setFocusable(false);
		listPanel.add(refreshButton);

		updateAutoButton = new JButton("Mise à Jours");
		updateAutoButton.addActionListener(this);
		updateAutoButton.setPreferredSize(new Dimension(200, 50));
		updateAutoButton.setFont(new Font("Consolas 10", Font.PLAIN, 25));
		updateAutoButton.setFocusable(false);
		listPanel.add(updateAutoButton);

		scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(width, 600));
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		listPanel.add(scrollPane, BorderLayout.WEST);

		panel = new JPanel();
		panel.setBorder(UIManager.getBorder("TextPane.border"));
		panel.setBackground(Color.WHITE);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		scrollPane.setViewportView(panel);

		JLabel refNameList = new JLabel("Name                Prix      Marge", SwingConstants.CENTER);
		refNameList.setFont(new Font("Courier New", Font.PLAIN, 25));
		refNameList.setPreferredSize(new Dimension(800, 50));
		refNameList.setBackground(Color.WHITE);
		panel.add(refNameList);

		// create an array of button and add them with the correct name, with the panel
		// configured in a flowLayout.
		if (recipe != null) {
			recipeButtton = new JButton[recipe.length];
			for (int i = 0; i < recipeButtton.length; i++) {
				String name = recipe[i].name;
				for (int j = recipe[i].name.length(); j < 20; j++) {
					name = name + " ";
				}
				name = name + recipe[i].price;
				for (int j = String.valueOf(recipe[i].price).length(); j < 10; j++) {
					name = name + " ";
				}
				name = name + recipe[i].marge;
				recipeButtton[i] = new JButton(name);
				recipeButtton[i].addActionListener(this);
				recipeButtton[i].setFont(new Font("Courier New", Font.PLAIN, 25));
				recipeButtton[i].setPreferredSize(new Dimension(800, 50));
				recipeButtton[i].setFocusable(false);
				panel.add(recipeButtton[i]);
			}

			// compute the panel height by multiply it by the number of button
			height = individualHeight * (recipeButtton.length);
			panel.setPreferredSize(new Dimension(width, height));
		}
		panelAdder = new JPanel();
		panelAdder = recipeAdder;
		panelAdder.setPreferredSize(new Dimension(500, 900));
		panelAdder.setBackground(Color.LIGHT_GRAY);
		add(panelAdder, BorderLayout.EAST);
	}

	// load the recipe in a Serialization format ( the save version is in the
	// adderClass / It's use in a mean of communication between the 2 class)
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// if the charge is updates, a click in this button and the charge are updated
		if (e.getSource() == refreshButton) {
			this.remove(panelAdder);
			recipeAdder = new RecipePageAdder("", "", new int[0], false, false);
			panelAdder = recipeAdder;
			panelAdder.setBackground(Color.lightGray);
			add(panelAdder, BorderLayout.EAST);
			revalidate();
			repaint();
		}
		// if a recipe button is click then it's modifies slightly the adder with the
		// info for the user
		for (int i = 0; i < recipeButtton.length; i++) {
			if (e.getSource() == recipeButtton[i]) {
				this.remove(panelAdder);
				recipeAdder = new RecipePageAdder(recipe[i].name, String.valueOf(recipe[i].price), recipe[i].reference,
						true, false);
				panelAdder = recipeAdder;
				panelAdder.setBackground(Color.lightGray);
				add(panelAdder, BorderLayout.EAST);
				revalidate();
				repaint();
			}
		}
		// remove the panel / load the recipe in the adder and save it directly after (
		// in the adderClass ) finishing with load a blank version
		if (e.getSource() == updateAutoButton) {
			this.remove(panelAdder);
			for (int i = 0; i < recipe.length; i++) {
				new RecipePageAdder(recipe[i].name, String.valueOf(recipe[i].price), recipe[i].reference, false, true);
			}
			MainCalcMarge.recipePageButton.doClick();
		}
	}
}
