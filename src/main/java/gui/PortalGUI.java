package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BlFacade;
import businessLogic.BlFacadeImplementation;

import javax.swing.JLabel;

public class PortalGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
    private BlFacade businessLogic;

    private JButton browseQBtn;
    

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PortalGUI frame = new PortalGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    
    
    public BlFacade getBusinessLogic() {
        return businessLogic;
    }

    public void setBusinessLogic (BlFacade afi){
        businessLogic = afi;
    }
    
    /**
     * Create the frame.
     */
    public PortalGUI() {
    	businessLogic = new BlFacadeImplementation();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JButton signupButton = new JButton("Sign up");
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // registerGUI
                try {
                    UserRegisterGUI registerGui = new UserRegisterGUI();
                    registerGui.setVisible(true);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        signupButton.setBounds(137, 55, 139, 52);
        contentPane.add(signupButton);
        
        JButton loginButton = new JButton("Log in");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // entrar a loginGUI
                try {
                    UserLoginGUI loginGui = new UserLoginGUI();
                    loginGui.setVisible(true);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
        loginButton.setBounds(137, 118, 139, 52);
        contentPane.add(loginButton);
        
        JLabel lblNewLabel = new JLabel("Please, select an option");
        lblNewLabel.setBounds(137, 11, 139, 33);
        contentPane.add(lblNewLabel);
        
        browseQBtn = new JButton("Browse Questions");
        browseQBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        browseQBtn.setBounds(137, 181, 139, 52);
        contentPane.add(browseQBtn);
        
        
        
    }
    

}