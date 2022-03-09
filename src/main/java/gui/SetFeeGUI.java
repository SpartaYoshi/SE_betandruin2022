package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.toedter.calendar.JCalendar;

import businessLogic.BlFacade;
import configuration.UtilDate;
import domain.Event;
import domain.Question;
import exceptions.FeeAlreadyExists;


public class SetFeeGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private BlFacade businessLogic;

	private JComboBox<Event> eventComboBox = new JComboBox<Event>();
	DefaultComboBoxModel<Event> eventModel = new DefaultComboBoxModel<Event>();

	private JLabel listOfEventsLbl = new JLabel(ResourceBundle.getBundle("Etiquetas").
			getString("ListEvents"));
	
	private JLabel listOfQuestionsLbl=new JLabel(ResourceBundle.getBundle("Etiquetas").
			getString("ListQuestions"));
	private JLabel ResultTextLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").
			getString("Result"));
	private JLabel FeeLbl = new JLabel(ResourceBundle.getBundle("Etiquetas").
			getString("Fee"));
	private JLabel eventDateLbl = new JLabel(ResourceBundle.getBundle("Etiquetas").
			getString("EventDate"));
	 

	private JTextField resultText = new JTextField();
	private JTextField feeText = new JTextField();
	private JCalendar calendar = new JCalendar();
	private Calendar previousCalendar = null;
	private Calendar currentCalendar = null;

	private JScrollPane eventScrollPane = new JScrollPane();

	private JButton setfeeBtn = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SetFee"));
	private JButton closeBtn = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JLabel msgLbl = new JLabel();
	private JLabel errorLbl = new JLabel();

	private Vector<Date> datesWithEventsInCurrentMonth = new Vector<Date>();
	private JComboBox<Question> questionsComboBox;
	DefaultComboBoxModel<Question> questionModel = new DefaultComboBoxModel<Question>();
	

	public void setBusinessLogic(BlFacade bl) {
		businessLogic = bl;		
	}

	public SetFeeGUI(BlFacade bl, Vector<domain.Event> v) {
		businessLogic = bl;
		try {
			jbInit(v);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit(Vector<domain.Event> v) throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("SetFee"));
		eventComboBox.setModel(eventModel);
		eventComboBox.setBounds(new Rectangle(275, 47, 250, 20));
		listOfEventsLbl.setBounds(new Rectangle(290, 18, 277, 20));
		ResultTextLabel.setBounds(new Rectangle(25, 211, 75, 20));
		resultText.setBounds(new Rectangle(100, 211, 119, 20));
		FeeLbl.setBounds(new Rectangle(25, 243, 75, 20));
		feeText.setBounds(new Rectangle(100, 240, 60, 20));

		calendar.setBounds(new Rectangle(40, 50, 225, 150));
		eventScrollPane.setBounds(new Rectangle(25, 44, 346, 116));

		setfeeBtn.setBounds(new Rectangle(100, 275, 130, 30));
		setfeeBtn.setEnabled(false);

		setfeeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonSet_actionPerformed(e);
			}
		});
		
		closeBtn.setBounds(new Rectangle(275, 275, 130, 30));
		
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});

		msgLbl.setBounds(new Rectangle(275, 209, 292, 20));
		msgLbl.setForeground(Color.red);
		// jLabelMsg.setSize(new Dimension(305, 20));

		errorLbl.setBounds(new Rectangle(175, 240, 305, 20));
		errorLbl.setForeground(Color.red);

		this.getContentPane().add(msgLbl, null);
		this.getContentPane().add(errorLbl, null);

		this.getContentPane().add(closeBtn, null);
		this.getContentPane().add(setfeeBtn, null);
		this.getContentPane().add(resultText, null);
		this.getContentPane().add(ResultTextLabel, null);
		this.getContentPane().add(feeText, null);

		this.getContentPane().add(FeeLbl, null);
		this.getContentPane().add(listOfEventsLbl, null);
		this.getContentPane().add(eventComboBox, null);

		this.getContentPane().add(calendar, null);

		datesWithEventsInCurrentMonth = businessLogic.getEventsMonth(calendar.getDate());
		paintDaysWithEvents(calendar,datesWithEventsInCurrentMonth);

		eventDateLbl.setBounds(new Rectangle(40, 15, 140, 25));
		eventDateLbl.setBounds(40, 16, 140, 25);
		getContentPane().add(eventDateLbl);
		
		
		listOfQuestionsLbl.setBounds(new Rectangle(290, 18, 277, 20));
		listOfQuestionsLbl.setBounds(275, 89, 277, 20);
		getContentPane().add(listOfQuestionsLbl);
		
		questionsComboBox = new JComboBox<Question>();
	
		questionsComboBox.setBounds(new Rectangle(275, 47, 250, 20));
		questionsComboBox.setBounds(275, 140, 292, 20);
		getContentPane().add(questionsComboBox);
		
		//code to set the questions
		this.setupQuestionBox();
		//code to enable the fee button
		this.enableFeeButton();

		// Code for JCalendar
		this.calendar.addPropertyChangeListener(new PropertyChangeListener() {
			
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
				if (propertychangeevent.getPropertyName().equals("locale")) {
					calendar.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					currentCalendar = (Calendar) propertychangeevent.getOldValue();
					previousCalendar = (Calendar) propertychangeevent.getNewValue();
					System.out.println("calendarAnt: "+currentCalendar.getTime());
					System.out.println("calendarAct: "+previousCalendar.getTime());
					DateFormat dateformat1 = DateFormat.getDateInstance(1, calendar.getLocale());

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
						errorLbl.setText(" ");//clear labels
						msgLbl.setText(" ");
						Vector<domain.Event> events = businessLogic.getEvents(firstDay);
						if (events.isEmpty())
							listOfEventsLbl.setText(ResourceBundle.getBundle("Etiquetas").
									getString("NoEvents") + ": " + dateformat1.
									format(previousCalendar.getTime()));
						else
							listOfEventsLbl.setText(ResourceBundle.getBundle("Etiquetas").
									getString("Events") + " : " + dateformat1.
									format(previousCalendar.getTime()));
						eventComboBox.removeAllItems();
						System.out.println("Events " + events);

						for (domain.Event ev : events)
							eventModel.addElement(ev);
						eventComboBox.repaint();
						

					} catch (Exception e1) {

						errorLbl.setText(e1.getMessage());
					}
				}
			}
		});
	}

	
	private void setupQuestionBox() {
		this.eventComboBox.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
						errorLbl.setText(" ");//clear labels
						msgLbl.setText(" ");
						questionsComboBox.removeAllItems();
						domain.Event event = ((domain.Event) eventComboBox.getSelectedItem());
						Vector<Question> eventquestions=event.getQuestions();
						System.out.println("QUESTIONS FOR THIS EVENT:"+eventquestions);
						
						if(eventquestions.isEmpty()) {
							listOfQuestionsLbl.setText("No questions found for this event");
						}else {
							listOfQuestionsLbl.setText("Choose an available question");
						}
						questionsComboBox.removeAllItems();

						for(domain.Question q: eventquestions) 
							questionsComboBox.addItem(q);
						questionsComboBox.repaint();

						
					
					}catch(Exception e2) {
						errorLbl.setText(e2.getMessage());
					}
			}
		});
	}
	/**
	 * Method to enable/disable the "setfee" button. Will enable when a question is selected
	 */
	
	private void enableFeeButton() {
		questionsComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					errorLbl.setText(" ");//clear labels
					msgLbl.setText(" ");
					domain.Question quest = ((domain.Question) questionsComboBox.getSelectedItem());
					
					if(quest!=null) {
						setfeeBtn.setEnabled(true);
					}else {
						setfeeBtn.setEnabled(false);
					}
					
					
				}catch(Exception e3) {
					errorLbl.setText(e3.getMessage());
				}
			}
		});
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

	/**
	 * ActionListener to set the fee when clicking into the "setFee" button.
	 * @param e
	 */
	private void jButtonSet_actionPerformed(ActionEvent e) {
		msgLbl.setText(" ");
		errorLbl.setText(" ");
		domain.Question quest = ((domain.Question) questionsComboBox.getSelectedItem());
		try {
			errorLbl.setText(" ");//clear labels
			msgLbl.setText(" ");
			String result=resultText.getText();
			float feeAmount=Float.parseFloat(feeText.getText());
			
			if(feeAmount<=0) {
				errorLbl.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorNumber"));

			}else {
					businessLogic.createFee(quest, result, feeAmount);
					msgLbl.setText(ResourceBundle.getBundle("Etiquetas").getString("FeeHasBeenSet"));
				 
			}
			
		}
		catch (FeeAlreadyExists e1) {
				errorLbl.setText("Sorry, the fee for that result already exists");
			}	
		catch (java.lang.NumberFormatException e1) {
			errorLbl.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorNumber"));	
			
		}
		
	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}