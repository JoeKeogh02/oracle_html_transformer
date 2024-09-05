package application;

import javax.swing.SwingUtilities;

import com.formdev.flatlaf.FlatDarkLaf;

public class Main {
	public static void main(String[] a) {
		SwingUtilities.invokeLater(() -> {
			FlatDarkLaf.setup();
			
			App app = new App();
			app.start();
		});

	}
}
