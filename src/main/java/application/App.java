package application;

import controller.CommandController;
import controller.LoginController;
import controller.NetworkConfigController;
import controller.TabbedAnswerController;
import controller.TransformationRuleController;
import data.dataSources.IOracleDataSource;
import data.dataSources.MockOracleDataSource;
import data.repositories.IOracleRepository;
import data.repositories.OracleRepository;
import utils.AnswerManager;
import utils.HtmlTransformationRuleManager;
import view.panels.ControlPanel;
import view.panels.LoginPanel;
import view.panels.TabbedAnswerPanel;
import view.panels.TransformationPanel;

public class App {
    private Gui gui;

    public App() {
    	// Initialise data
    	IOracleDataSource oracleDataSource = new MockOracleDataSource(null);
    	IOracleRepository oracleRepository = new OracleRepository(oracleDataSource);
    	
    	// Utils
    	HtmlTransformationRuleManager ruleManager = new HtmlTransformationRuleManager();
    	AnswerManager answerManager = new AnswerManager();
    	
        // Initialise panels
        ControlPanel controlPanel = new ControlPanel();
        LoginPanel loginPanel = new LoginPanel();
        TransformationPanel transformationPanel = new TransformationPanel();
        TabbedAnswerPanel tabbedAnswerPanel = new TabbedAnswerPanel();
        
        // Initialise controllers
        LoginController loginController = new LoginController(loginPanel, oracleRepository);
        NetworkConfigController networkConfigController = new NetworkConfigController(oracleRepository);
        TransformationRuleController ruleController = new TransformationRuleController(ruleManager, transformationPanel);
        TabbedAnswerController answerController = new TabbedAnswerController(tabbedAnswerPanel, answerManager);
        
        @SuppressWarnings("unused")
		CommandController commandController = new CommandController(controlPanel, answerController, ruleController, loginController, networkConfigController, oracleRepository, answerManager, ruleManager);
        

        // Initialise gui with the panels
        gui = new Gui(controlPanel, loginPanel, transformationPanel, tabbedAnswerPanel);
    }

    public void start() {
        gui.display(); // Display application
    }
}

