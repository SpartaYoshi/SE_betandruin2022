package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BlFacade;
import domain.User;
import exceptions.FailedLoginException;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class UserLoginGUI extends JFrame {

	
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private MainAdminGUI initWindow;
	
	private JButton btnLogin;
	
	private JLabel lblLoginUser;
	private JLabel lbUsername;
	private JLabel lbPasswd;
	private JLabel errorMessage;
	private JButton btnClose;
	
	private BlFacade bizlog;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserLoginGUI frame = new UserLoginGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UserLoginGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnLogin = new JButton("Log-in");
		btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel lblLoginUser = new JLabel("Log-in user:");
		lblLoginUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginUser.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		final JLabel lbUsername = new JLabel("Username:");
		lbUsername.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lbPasswd = new JLabel("Password:");
		lbPasswd.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		textField = new JTextField();
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		
		errorMessage = new JLabel("");
		
		btnClose = new JButton("Close");
		
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String username = lbUsername.getText();
				String password = new String (passwordField.getPassword());

				try {
					initWindow = new MainAdminGUI();	// TODO CAMBIAR POR "new MainUserGUI();" CUANDO HAGAIS LA CLASE
					bizlog = initWindow.getBusinessLogic();
					
					User user = bizlog.loginUser(username, password);
				
					if (user.isAdmin())
						initWindow = new MainAdminGUI();
				
					initWindow.setVisible(true);
					
				} catch (FailedLoginException e) {
					errorMessage.setForeground(Color.red);
					errorMessage.setText("Login credentials were incorrect. Please try again");
				}
			}
		});
		
		
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		
		
		
		
		
		
		
		
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(lblLoginUser, GroupLayout.PREFERRED_SIZE, 406, GroupLayout.PREFERRED_SIZE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(52)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lbUsername, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lbPasswd, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE)))
					.addGap(28))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(165)
					.addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 107, Short.MAX_VALUE)
					.addComponent(btnClose))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(61)
					.addComponent(errorMessage, GroupLayout.PREFERRED_SIZE, 332, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(33, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblLoginUser, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(37)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbUsername, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbPasswd, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addGap(26)
					.addComponent(errorMessage, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnClose)))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
