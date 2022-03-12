package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import com.toedter.calendar.JCalendar;
//import javafx.scene.control.DatePicker;

import businessLogic.BlFacade;
import businessLogic.BlFacadeImplementation;
import domain.User;
import exceptions.UserIsTakenException;
import exceptions.UserIsUnderageException;


public class UserRegisterGUI extends JFrame {

	private JPanel contentPane;
	private JTextField tfName;
	private JTextField tfSurname;
	private JTextField tfUsername;
	private JPasswordField passwd;
	private BrowseQuestionsGUI initWindow;
	
	private BlFacade bizlog;
	private JCalendar calendar;
	private JLabel errorMessage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserRegisterGUI frame = new UserRegisterGUI();
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
	public UserRegisterGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 489, 533);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		tfName = new JTextField();
		tfName.setColumns(10);
		
		tfSurname = new JTextField();
		tfSurname.setColumns(10);
		
		tfUsername = new JTextField();
		tfUsername.setColumns(10);
		
		passwd = new JPasswordField();	
		
		calendar = new JCalendar();

		errorMessage = new JLabel("");
		errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton btRegister = new JButton("Register");
		btRegister.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				errorMessage.setText(" ");//clear the label
				String name = tfName.getText();
				String surname = tfSurname.getText();
				String username = tfUsername.getText();
				String password = new String (passwd.getPassword());
				Date birthday = calendar.getDate();
				
				
				User user = new User(username, password, name, surname, birthday);
				

				bizlog = new MainAdminGUI().getBusinessLogic();
				String errordet = bizlog.registerUser(user);
				
				if (!errordet.equals("")) {
					errorMessage.setForeground(Color.red);
					errorMessage.setText(errordet);
				} else {
					initWindow = new BrowseQuestionsGUI(bizlog);
					errorMessage.setForeground(Color.blue);
					errorMessage.setText("Register was succesful. Now you can login");
				}
					
				
			}
		});
		
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		
		JLabel lbName = new JLabel("Name:");
		lbName.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lbSurname = new JLabel("Surname:");
		lbSurname.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lbUsername = new JLabel("Username:");
		lbUsername.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lbPasswd = new JLabel("Password:");
		lbPasswd.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lbTitle = new JLabel("Register new user:");
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		JLabel lblBirthday = new JLabel("Birthday:");
		lblBirthday.setFont(new Font("Tahoma", Font.BOLD, 12));
				

		
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(lbTitle, GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btRegister)
							.addGap(109)
							.addComponent(btnClose)))
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(52)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(errorMessage, GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
							.addGap(59))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lbName, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
								.addComponent(lbSurname, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
								.addComponent(lbUsername, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
								.addComponent(lbPasswd, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblBirthday, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(50)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(passwd, 217, 217, 217)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
											.addComponent(tfUsername, GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
											.addComponent(tfSurname, GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
											.addComponent(tfName, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE))))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(49)
									.addComponent(calendar, GroupLayout.PREFERRED_SIZE, 225, GroupLayout.PREFERRED_SIZE)))
							.addContainerGap(66, Short.MAX_VALUE))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lbTitle)
					.addGap(16)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfName, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbName))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfSurname, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbSurname, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfUsername, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbUsername, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(passwd, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbPasswd, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(24)
							.addComponent(calendar, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(99)
							.addComponent(lblBirthday, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)))
					.addGap(14)
					.addComponent(errorMessage, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btRegister)
						.addComponent(btnClose))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
}
