package gui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import javax.swing.JComboBox;
import domain.Event;
import exceptions.EventFinished;

import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.Font;
import com.toedter.calendar.JCalendar;

import businessLogic.BlFacade;
import configuration.UtilDate;

public class CreateEventGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;


	private BlFacade businessLogic;


	private JPanel contentPane;
	private JTextField eventtextField;
	private JCalendar calendar = new JCalendar();
	private Calendar previousCalendar = null;
	private Calendar currentCalendar = null;
	private JLabel listOfEventsLabel = new JLabel("List of Events");
	private JLabel eventDateLabel = new JLabel("Event Date");
	private	JLabel writeEventText = new JLabel("Event:");
	private JButton createEventButton = new JButton("Create event");
	private JButton closeButton = new JButton("Close");
	private JComboBox<Event> eventcomboBox = new JComboBox<Event>();
	DefaultComboBoxModel<Event> eventModel = new DefaultComboBoxModel<Event>();
	private Vector<Date> datesWithEventsInCurrentMonth = new Vector<Date>();
	private final JLabel messageLabel = new JLabel();

	/**
	 * Launch the application.
	*/
/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//CreateEventGUI frame = new CreateEventGUI(businessLogic, datesWithEventsInCurrentMonth);
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

*/	
	public void setBusinessLogic(BlFacade bl) {
		businessLogic = bl;		
	}

	

	/**
	 * Create the frame.
	 */
	public CreateEventGUI(BlFacade bl, Vector<domain.Event> v) {
		businessLogic = bl;
		try {
			initialize(v);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initialize(Vector<domain.Event> v) throws Exception {
	
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 550, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		eventDateLabel.setBounds(new Rectangle(40, 15, 140, 25));
		eventDateLabel.setBounds(10, 25, 140, 25);
		contentPane.add(eventDateLabel);
		
		listOfEventsLabel.setBounds(new Rectangle(290, 18, 277, 20));
		listOfEventsLabel.setBounds(270, 27, 197, 20);
		contentPane.add(listOfEventsLabel);
		
		writeEventText.setBounds(new Rectangle(25, 211, 75, 20));
		writeEventText.setBounds(56, 218, 94, 20);
		contentPane.add(writeEventText);
		
		eventtextField = new JTextField();
		eventtextField.setBounds(new Rectangle(100, 211, 429, 20));
		eventtextField.setBounds(134, 219, 312, 20);
		contentPane.add(eventtextField);
		
		createEventButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		createEventButton.setBounds(143, 282, 111, 21);
		createEventButton.setEnabled(false);
		createEventButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				jButtonCreateEvent_actionPerformed(e);
			}
		});
		contentPane.add(createEventButton);
		
		closeButton.setBounds(290, 283, 85, 21);
		closeButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		contentPane.add(closeButton);
		
		
		calendar.setBounds(new Rectangle(40, 50, 225, 150));
		calendar.setBounds(10, 45, 225, 150);
		contentPane.add(calendar);
		
		eventcomboBox.setModel(eventModel);
		eventcomboBox.setBounds(270, 57, 226, 21);
		contentPane.add(eventcomboBox);
		
		this.calendar.addPropertyChangeListener(new PropertyChangeListener() {
			
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
				if (propertychangeevent.getPropertyName().equals("locale")) {
					calendar.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					currentCalendar = (Calendar) propertychangeevent.getOldValue();
					previousCalendar = (Calendar) propertychangeevent.getNewValue();
					System.out.println("calendarAnt: "+currentCalendar.getTime());
					System.out.println("calendarAct: "+previousCalendar.getTime());

					int monthAnt = currentCalendar.get(Calendar.MONTH);
					int monthAct = previousCalendar.get(Calendar.MONTH);
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) { 
							// Si en JCalendar estÃ¡ 30 de enero y se avanza al mes siguiente, 
							// devolverá 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							previousCalendar.set(Calendar.MONTH, monthAnt + 1);
							previousCalendar.set(Calendar.DAY_OF_MONTH, 1);
						}

						calendar.setCalendar(previousCalendar);

						datesWithEventsInCurrentMonth = businessLogic.getEventsMonth(calendar.getDate());
					}

					paintDaysWithEvents(calendar,datesWithEventsInCurrentMonth);

					Date firstDay = UtilDate.trim(previousCalendar.getTime());

					try {
						Vector<domain.Event> events = businessLogic.getEvents(firstDay);

						//if (events.isEmpty())
						//	listOfEventsLabel.setText("NoEvents" + ": " + dateformat1.
						//			format(previousCalendar.getTime()));
						//else
						//	listOfEventsLabel.setText(ResourceBundle.getBundle("Etiquetas").
						//			getString("Events") + " : " + dateformat1.
						//			format(previousCalendar.getTime()));
						eventcomboBox.removeAllItems();
						System.out.println("Events: " + events);

						for (domain.Event ev : events)
							eventModel.addElement(ev);
						eventcomboBox.repaint();

						createEventButton.setEnabled(true);

					} catch (Exception e1) {

						messageLabel.setText(e1.getMessage());
					}
				}
			}
		});
		
		
		
		
		datesWithEventsInCurrentMonth = businessLogic.getEventsMonth(calendar.getDate());
		paintDaysWithEvents(calendar,datesWithEventsInCurrentMonth);
		messageLabel.setForeground(Color.RED);
		messageLabel.setBounds(new Rectangle(175, 240, 305, 20));
		messageLabel.setBounds(111, 252, 305, 20);
		
		contentPane.add(messageLabel);
		
		
	}
	
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
	
	private void jButtonCreateEvent_actionPerformed(ActionEvent e) {
		//try {
			messageLabel.setText("");
		
			String[] description = eventtextField.getText().split("-");
		
			String team1 = description[0];
			String team2 = description[1];
			Date date = calendar.getDate();
		
			if (description.length>0) {
				try {
					Event newEvent = businessLogic.createEvent(team1, team2, date);
					newEvent.setEventNumber(null);
					messageLabel.setText("An event has been created");
					eventcomboBox.addItem(newEvent);
					eventcomboBox.repaint();
					
				} catch (EventFinished e1) {
					
					messageLabel.setText("The event could not be created");
					e1.printStackTrace();
				}
			} else messageLabel.setText("Must be written as: team1's name - team2's name");
			
	
		//} catch (EventFinished e1) {
		//	messageLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished") + team1;
		//}
	}
		
		
	
	
	public static void paintDaysWithEvents(JCalendar jCalendar, 
			Vector<Date> datesWithEventsCurrentMonth) {
		// For each day with events in current month, the background color for that day is changed.

		Calendar calendar = jCalendar.getCalendar();

		int month = calendar.get(Calendar.MONTH);
		int today = calendar.get(Calendar.DAY_OF_MONTH);
		int year = calendar.get(Calendar.YEAR);

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int offset = calendar.get(Calendar.DAY_OF_WEEK);

		if (Locale.getDefault().equals(new Locale("es")))
			offset += 4;
		else
			offset += 5;

		for (Date d:datesWithEventsCurrentMonth){
			calendar.setTime(d);
			System.out.println(d);

			// Obtain the component of the day in the panel of the DayChooser of the
			// JCalendar.
			// The component is located after the decorator buttons of "Sun", "Mon",... or
			// "Lun", "Mar"...,
			// the empty days before day 1 of month, and all the days previous to each day.
			// That number of components is calculated with "offset" and is different in
			// English and Spanish

			Component o = jCalendar.getDayChooser().getDayPanel()
					.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
			o.setBackground(Color.CYAN);
		}

		calendar.set(Calendar.DAY_OF_MONTH, today);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);
	}
	
	
}
