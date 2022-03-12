package gui;

import java.awt.BorderLayout;
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
					errorMessage.setText("Login credentials were incorrect. Please try again");
				}
					
				
				
				
				//2. pillar contraseña del passwordfield y mirar si la contraseña es correcta (usando el usuario que acabas de guardar)
					// si la contraseña no coincide, lanzar y catch: FailedLoginException
				

				//3. mirar si usuario es admin (ya existe un booleano como atributo pero falta el getter)
					// if (user.isAdmin()) {
					//				initWindow = new MainAdminGUI();
					//				bizlog = initWindow.getBusinessLogic();
					// }
				
				
				
				//4. ahora si que sí
				// initWindow.setVisible(true); 	
				
				
				////////////////////////////////
				// vinculación:		UserLoginGUI > MainUserGUI > businesslogic (BlFacade) > DataAccess
				
			}
		});
		
		
		
		
		
		
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblLoginUser, GroupLayout.PREFERRED_SIZE, 406, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(52)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lbUsername, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(textField, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lbPasswd, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(errorMessage, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE)
										.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE)))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(165)
							.addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(20, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblLoginUser, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(52)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbUsername, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addGap(46)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbPasswd, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(errorMessage, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
