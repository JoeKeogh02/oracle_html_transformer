package application;

import java.awt.BorderLayout;

import view.panels.ControlPanel;
import view.panels.LoginPanel;
import view.panels.TabbedAnswerPanel;
import view.panels.TransformationPanel;

import javax.swing.*;

/**
 * The GUI class is responsible for creating and displaying the main graphical user interface (GUI)
 * of the Oracle Text Transformer application. It sets up the main application window, which includes
 * different panels like the control panel, transformation panel, and tabbed answer panel.
 * The layout is structured using a {@code JSplitPane} to divide the screen into sections.
 */
public class Gui {
    private JFrame frame;

    /**
     * Constructs a new GUI object with the specified components and sets up the layout of the main
     * application window. The GUI includes a control panel at the top, a transformation panel on the left,
     * and a tabbed answer panel on the right.
     *
     * @param controlPanel	the panel at the top that contains controls like buttons.
     * @param loginPanel  the panel responsible for handling user login (currently not added to the layout).
     * @param transformationPanel the panel on the left side of the screen for displaying transformation rules.
     * @param tabbedAnswerPanel  the panel on the right side of the screen for displaying tabbed answer content.
     */
    public Gui(
            ControlPanel controlPanel,
            LoginPanel loginPanel, // Currently not used
            TransformationPanel transformationPanel,
            TabbedAnswerPanel tabbedAnswerPanel
    ) {
        // Set up the main application window (JFrame)
        frame = new JFrame("Oracle Text Transformer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the application when the window is closed
        frame.setSize(800, 600); // Set initial size of the window
        frame.setLayout(new BorderLayout()); // Use BorderLayout for the frame's layout

        // Create the left side panel that holds the transformationPanel
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS)); // Use vertical box layout

        // Add the transformation panel to the left panel
        leftPanel.add(transformationPanel);

        // Create a split pane to divide the left and right panels
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(400); // Set the initial divider location
        splitPane.setResizeWeight(0.5); // Set the resize weight to evenly distribute space

        // Add the left and right components to the split pane
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(tabbedAnswerPanel);

        // Add the control panel at the top and the split pane in the center of the frame
        frame.add(controlPanel, BorderLayout.NORTH); 
        frame.add(splitPane, BorderLayout.CENTER); 
    }

    /**
     * Displays the GUI by setting the JFrame visible. This method should be called after
     * the GUI object is fully constructed. It ensures the frame is centered on the screen
     * and made visible.
     */
    public void display() {
        SwingUtilities.invokeLater(() -> {
            frame.setLocationRelativeTo(null); // Center the frame on the screen
            frame.setVisible(true); // Make the frame visible
        });
    }
}
