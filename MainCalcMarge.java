package calculMPackage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.*;

public class MainCalcMarge extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4648259714891788959L;

	static JButton recipePageButton;
	static JButton itemPageButton;
	static JButton statPageButton;
	static JButton fixedPageButton;

	JPanel menu;
	JComboBox<?> comboBox;

	ItemPage itemPage;
	static JPanel panel;

	static MainCalcMarge frame;
	static String path;
	static String[] paths;
	static File file;
	static File[] files;
	boolean firstSelected;
	// generate a menu bar with 4 button who govern 4 pages ( switch with the JPanel
	// : panel )

	MainCalcMarge(String namePath, int posComboBox) {

		super("CalculMarge");
		
		path = "Save\\";
		file = new File("Save");
		files = file.listFiles();
		paths = new String[files.length + 1];
		for (int i = 0; i < paths.length - 1; i++) {
			paths[i] = files[i].getName();
		}
		paths[paths.length - 1] = "Nouveau Répertoire";
		path = "Save\\" + namePath;
		
		firstSelected = false;
		comboBox = new JComboBox<Object>(paths);
		comboBox.addActionListener(this);
		comboBox.setFont(new Font("Consolas 10", Font.PLAIN, 30));
		comboBox.setSelectedIndex(posComboBox);

		itemPage = new ItemPage();
		StatisticPage statPage = new StatisticPage();

		panel = new JPanel();
		panel = statPage;

		fixedPageButton = new JButton("Charges Fixes");
		fixedPageButton.addActionListener(this);
		fixedPageButton.setFont(new Font("Consolas 10", Font.PLAIN, 30));
		fixedPageButton.setFocusable(false);

		itemPageButton = new JButton("Liste des charges");
		itemPageButton.addActionListener(this);
		itemPageButton.setFont(new Font("Consolas 10", Font.PLAIN, 30));
		itemPageButton.setFocusable(false);

		recipePageButton = new JButton("Liste des recettes");
		recipePageButton.addActionListener(this);
		recipePageButton.setFont(new Font("Consolas 10", Font.PLAIN, 30));
		recipePageButton.setFocusable(false);

		statPageButton = new JButton("Statistique");
		statPageButton.addActionListener(this);
		statPageButton.setFont(new Font("Consolas 10", Font.PLAIN, 30));
		statPageButton.setFocusable(false);

		menu = new JPanel();
		menu.setBackground(Color.lightGray);
		menu.setPreferredSize(new Dimension(0, 100));
		menu.add(fixedPageButton);
		menu.add(itemPageButton);
		menu.add(recipePageButton);
		menu.add(statPageButton);
		menu.add(comboBox);

		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setLayout(new BorderLayout(10, 10));
		this.add(menu, BorderLayout.NORTH);
		this.add(panel);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		
		file = new File("Save");
		File[] files = file.listFiles();
		if(files.length == 0) {
			String name = JOptionPane.showInputDialog("Quel est le nom du nouveau répèrtoire ? : ");
			String pathName = "Save\\" + name;
			Path path = Paths.get(pathName);
			try {
				Files.createDirectories(path);
				path = Paths.get(pathName+"\\SaveRecipe");
				Files.createDirectories(path);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		paths = new String[files.length + 1];
		for (int i = 0; i < paths.length - 1; i++) {
			paths[i] = files[i].getName();
		}
		paths[paths.length - 1] = "Nouveau Répertoire";
		frame = new MainCalcMarge(paths[0], 0);
	}

	// if a button is click, it's delete the panel, and replace it with the
	// modification
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == itemPageButton) {
			getContentPane().remove(panel);
			panel = new JPanel();
			itemPage = new ItemPage();
			panel = itemPage;
			add(panel);
			validate();
			repaint();
		}
		if (e.getSource() == recipePageButton) {
			getContentPane().remove(panel);
			panel = new JPanel();
			RecipePage recipePage = new RecipePage();
			panel = recipePage;
			add(panel);
			validate();
			repaint();
		}
		if (e.getSource() == statPageButton) {
			getContentPane().remove(panel);
			panel = new JPanel();
			StatisticPage statPage = new StatisticPage();
			panel = statPage;
			add(panel);
			validate();
			repaint();
		}
		if (e.getSource() == fixedPageButton) {
			getContentPane().remove(panel);
			panel = new JPanel();
			FixedItemPage fixedPage = new FixedItemPage();
			panel = fixedPage;
			add(panel);
			validate();
			repaint();
		}
		if (e.getSource() == comboBox) {
			if (comboBox.getSelectedIndex() == paths.length - 1) {
				String name = JOptionPane.showInputDialog("Quel est le nom du nouveau répèrtoire ? : ");
				String pathName = "Save\\" + name;
				Path path = Paths.get(pathName);
				try {
					Files.createDirectories(path);
					path = Paths.get(pathName+"\\SaveRecipe");
					Files.createDirectories(path);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				frame.dispose();
				frame = new MainCalcMarge(name, paths.length - 1);
			}
			else {
				if(firstSelected == true) {
					frame.dispose();
				    frame = new MainCalcMarge(paths[comboBox.getSelectedIndex()], comboBox.getSelectedIndex());
				}
				else {
					firstSelected = true;
				}
				
				
			}
			
		}
	}
}