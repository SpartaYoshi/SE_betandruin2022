package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import businessLogic.BlFacade;
import businessLogic.BlFacadeImplementation;
import domain.Event;
import java.awt.Font;


public class MainAdminGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel mainPane;
	
	

	private BlFacade businessLogic;
	private JButton browseQuestionsBtn;
	private JButton createEventBtn;
	private JButton createQuestionBtn;
	private JButton setFeeBtn;
	private JLabel headerLbl;

	


	public MainAdminGUI() {
		super();
		businessLogic = new BlFacadeImplementation();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					//if (ConfigXML.getInstance().isBusinessLogicLocal()) facade.close();
				}
				catch (Exception e1) {
					System.out.println("Error: " + e1.toString() + " , likely problems "
							+ "with Business Logic or Data Accesse");
				}
				System.exit(1);
			}
		});

		this.setPreferredSize(new Dimension(400, 300));


		this.initializeMainPane();
		this.setContentPane(mainPane);
		

		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	

	public BlFacade getBusinessLogic() {
		return businessLogic;
	}

	public void setBusinessLogic (BlFacade afi){
		businessLogic = afi;
	}
	
	

	private void initializeMainPane() {
		mainPane = new JPanel();
		mainPane.setLayout(new GridLayout(5, 1, 0, 0));

		initializeBrowseQuestionsBtn();
		{
			headerLbl = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainAdminGUI.lblNewLabel.text")); //$NON-NLS-1$ //$NON-NLS-2$
			headerLbl.setHorizontalAlignment(SwingConstants.CENTER);
			headerLbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
			mainPane.add(headerLbl);
		}
		mainPane.add(browseQuestionsBtn);
		
		initializeCreateQuestionBtn();
		mainPane.add(createQuestionBtn);
		
		initializecreateEventBtn();
		mainPane.add(createEventBtn);
		
		initializeFeeBtn();
		mainPane.add(setFeeBtn);

		
	}

	private void initializeBrowseQuestionsBtn() {
		browseQuestionsBtn = new JButton();
		browseQuestionsBtn.setText(ResourceBundle.getBundle("Etiquetas").
				getString("BrowseQuestions"));
		browseQuestionsBtn.addActionListener(new java.awt.event.ActionListener() {
			
			public void actionPerformed(java.awt.event.ActionEvent e) {
				BrowseQuestionsGUI findQuestionsWindow = new BrowseQuestionsGUI(businessLogic);
				findQuestionsWindow.setVisible(true);
			}
		});
	}

	private void initializeCreateQuestionBtn() {
		createQuestionBtn = new JButton();
		createQuestionBtn.setText(ResourceBundle.getBundle("Etiquetas").
				getString("CreateQuestion"));
		createQuestionBtn.addActionListener(new java.awt.event.ActionListener() {
			
			public void actionPerformed(java.awt.event.ActionEvent e) {
				CreateQuestionGUI createQuestionWindow = new CreateQuestionGUI(businessLogic, new Vector<Event>());
				createQuestionWindow.setBusinessLogic(businessLogic);
				createQuestionWindow.setVisible(true);
			}
		});
	}

	private void initializecreateEventBtn() {
		createEventBtn = new JButton();
		createEventBtn.setText("Create Event");
		createEventBtn.addActionListener(new java.awt.event.ActionListener() {
			
			public void actionPerformed(java.awt.event.ActionEvent e) {
				CreateEventGUI createQuestionWindow = new CreateEventGUI(businessLogic, new Vector<Event>());
				createQuestionWindow.setBusinessLogic(businessLogic);
				createQuestionWindow.setVisible(true);
			}
		});
	}
	
	private void initializeFeeBtn() {
		setFeeBtn = new JButton();
		setFeeBtn.setText("Set new Fee");
		setFeeBtn.addActionListener(new java.awt.event.ActionListener() {
			
			public void actionPerformed(java.awt.event.ActionEvent e) {
				SetFeeGUI createQuestionWindow = new SetFeeGUI(businessLogic, new Vector<Event>());
				createQuestionWindow.setBusinessLogic(businessLogic);
				createQuestionWindow.setVisible(true);
			}
		});
	}
	
	
	
}