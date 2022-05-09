package businessLogic;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.xml.ws.Endpoint;

import configuration.ConfigXML;

/**
 * Runs the Business Logic node as a separate, possibly remote process.
 */
public class BusinessLogicServer extends JDialog {

	final JTextArea textArea;
	String service;

	public BusinessLogicServer() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		setTitle("Business Logic Server");
		setBounds(100, 400, 500, 200);

		JPanel contentPanel = new JPanel(new BorderLayout(0, 0));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.add(contentPanel);

		textArea = new JTextArea();
		contentPanel.add(textArea);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		contentPanel.add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("Stop BUSINESS LOGIC");
		okButton.addActionListener(e -> {
			textArea.append("\nClosing the server... ");
			System.exit(1);
		});

		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		ConfigXML config = ConfigXML.getInstance();
		if (config.isBusinessLogicLocal())
			textArea.append("\nERROR, the business logic is configured as local");

		else {

			if (!config.isDataAccessLocal())
				System.out.println("\nWARNING: Please be sure ObjectdbManagerServer is launched" +
						"\n\tin machine: " + config.getDataAccessNode() + " port: " +
						config.getDataAccessPort()+"\n");	
			try {
				service= "http://" + config.getBusinessLogicNode() + ":" +
						config.getBusinessLogicPort()+"/ws/"+ config.getBusinessLogicName();

				Endpoint.publish(service, new BlFacadeImplementation());

				textArea.append("Running service at:\t" + service);
				textArea.append("\nPress button to stop this server... ");

			} catch (Exception e) {
				System.out.println("Error in BusinessLogicServer: ");
				textArea.append("\nYou should have not launched DBManagerServer...");
				textArea.append("\n\nor maybe there is a BusinessLogicServer already launched...");
				throw e;
			}
		}
	}

	public static void main(String[] args) {
		try {
			BusinessLogicServer dialog = new BusinessLogicServer();
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}