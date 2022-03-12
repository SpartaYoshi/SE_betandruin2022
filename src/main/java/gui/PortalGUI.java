package gui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;


import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import businessLogic.BlFacade;
import businessLogic.BlFacadeImplementation;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;


public class PortalGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel mainPane;
	private JButton browseQuestionsBtn;
	private JPanel localePane;
	private JRadioButton euskaraRbtn;
	private JRadioButton castellanoRbtn;
	private JRadioButton englishRbtn;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private BlFacade businessLogic;
	protected JLabel welcomeLbl;
	private JButton btnLogin;
	private JButton btnRegister;

	


	public PortalGUI() {
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
		
		welcomeLbl = new JLabel();
		welcomeLbl.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLbl.setFont(new Font("Source Serif Pro", Font.BOLD, 19));
		welcomeLbl.setText("Welcome to Bet&Ruin 22!");
		mainPane.add(welcomeLbl);

		initializeRegister();
		mainPane.add(btnRegister);
		
		initializeLogin();
		mainPane.add(btnLogin);

		initializeBrowseQuestionsBtn();
		mainPane.add(browseQuestionsBtn);
		
		

		
		initializeLocalePane();
		mainPane.add(localePane);
	}

	private void initializeRegister() {
		btnRegister = new JButton();
		btnRegister.setText(ResourceBundle.getBundle("Etiquetas").
				getString("Register"));
		btnRegister.addActionListener(new java.awt.event.ActionListener() {
			
			public void actionPerformed(java.awt.event.ActionEvent e) {
				 // registerGUI
                try {
                    UserRegisterGUI registerGui = new UserRegisterGUI();
                    registerGui.setVisible(true);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
		});
		
	}
	
	private void initializeLogin() {
		btnLogin = new JButton();
		btnLogin.setText(ResourceBundle.getBundle("Etiquetas").
				getString("Login"));
		btnLogin.addActionListener(new java.awt.event.ActionListener() {
			
			public void actionPerformed(java.awt.event.ActionEvent e) {
				// enter to loginGUI
                try {
                    UserLoginGUI loginGui = new UserLoginGUI();
                    loginGui.setVisible(true);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
		});
		
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

	

	private void initializeLocalePane() {
		localePane = new JPanel();

		initializeEuskaraRbtn();
		localePane.add(euskaraRbtn);

		initializeCastellanoRbtn();
		localePane.add(castellanoRbtn);

		initializeEnglishRbtn();
		localePane.add(englishRbtn);
	}

	private void initializeEuskaraRbtn() {
		euskaraRbtn = new JRadioButton("Euskara");
		euskaraRbtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				Locale.setDefault(new Locale("eus"));
				System.out.println("Locale: " + Locale.getDefault());
				redraw();
			}
		});
		buttonGroup.add(euskaraRbtn);
	}

	private void initializeCastellanoRbtn() {
		castellanoRbtn = new JRadioButton("Castellano");
		castellanoRbtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("es"));
				System.out.println("Locale: " + Locale.getDefault());
				redraw();
			}
		});
		buttonGroup.add(castellanoRbtn);
	}

	private void initializeEnglishRbtn() {
		englishRbtn = new JRadioButton("English");
		englishRbtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("en"));
				System.out.println("Locale: " + Locale.getDefault());
				redraw();				}
		});
		buttonGroup.add(englishRbtn);
	}

	private void redraw() {
		welcomeLbl.setText(ResourceBundle.getBundle("Etiquetas").
				getString("Welcome"));
		browseQuestionsBtn.setText(ResourceBundle.getBundle("Etiquetas").
				getString("BrowseQuestions"));
		btnLogin.setText(ResourceBundle.getBundle("Etiquetas").
				getString("Login"));
		btnRegister.setText(ResourceBundle.getBundle("Etiquetas").
				getString("Register"));
		
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
	}
}