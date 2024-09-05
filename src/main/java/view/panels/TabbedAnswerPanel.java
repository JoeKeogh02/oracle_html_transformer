package view.panels;

import javax.swing.*;
import java.awt.BorderLayout;

public class TabbedAnswerPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4661399326225405987L;
	private JTabbedPane tabbedPane;

    public TabbedAnswerPanel() {
        tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);
        initializeTabs();
    }

    private void initializeTabs() {
        tabbedPane.addTab("All", new JPanel());
        tabbedPane.addTab("Finished", new JPanel());
        tabbedPane.addTab("Updating", new JPanel());
        tabbedPane.addTab("Waiting", new JPanel());
        tabbedPane.addTab("Error", new JPanel());
    }

    // Update the content of a specific tab
    public void updateTabContent(String tabName, JPanel content) {
        int index = tabbedPane.indexOfTab(tabName);
        if (index != -1) {
            JScrollPane scrollPane = new JScrollPane(content);
            tabbedPane.setComponentAt(index, scrollPane);
        }
    }
}
