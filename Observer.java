import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class Observer extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField usertext;
	private static JTextArea textarea;
	private JButton button;
	private boolean started;
	private JButton save;
	
	private JButton clear;
	private static boolean saved;
	PrintWriter printwriter;
	Date date1;


	public Observer(){
		super("Observer");
		saved=false;
		setLayout(new FlowLayout());
		usertext=new JTextField();
		usertext.setColumns(50);

		started=false;
		usertext.setEditable(false);
		textarea=new JTextArea();
		textarea.setColumns(50);
		textarea.setRows(20);
		button=new JButton("Start");
		save=new JButton("Save as");
		clear=new JButton("Clear Text");
		

		textarea.setEditable(false);
		
		clear.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						clear();
					}
				}
				);
		save.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						save();
					}

				}
				);

		button.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						
						button();
						}
					}

				
				);
		usertext.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						usertext();
					}
				});

		add(usertext, BorderLayout.NORTH);
		add(button);
		add(save);
		add(new JScrollPane(textarea));
		add(clear);

		setSize(800,400);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[] args){
		Observer observe=new Observer();
	}
	
	public void clear(){
		class ClearText extends JFrame{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			JLabel label;
			JButton clear;
			JButton dontclear;
			ClearText(){
				label=new JLabel("Are you sure you want to clear data?(You have not saved)");
				clear=new JButton("Yes, clear");
				dontclear=new JButton("No, don't clear");

				clear.addActionListener(
						new ActionListener(){

							@Override
							public void actionPerformed(ActionEvent e) {
								clear();
								// TODO Auto-generated method stub

							}

						}
						);
				dontclear.addActionListener(
						new ActionListener(){

							@Override
							public void actionPerformed(ActionEvent e) {
								dontclear();
								// TODO Auto-generated method stub

							}

						}
						);
				add(label);
				add(dontclear);
				add(clear);
				setLayout(new FlowLayout());
				setSize(400,100);
				setVisible(true);

			}
			void clear(){
				Observer.textarea.setText("");
				setVisible(false);
			}
			void dontclear(){
				setVisible(false);
			}


		}
		if (textarea.getText().isEmpty()==true || saved==true){
			textarea.setText("");
		}else{
			ClearText cleartext=new ClearText();
		}

	}
	
	public void save(){
		class SaveObservation extends JFrame{

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			PrintWriter printwriter;
			JButton button;

			JTextField recipename;
			public SaveObservation(){
				setLayout(new FlowLayout());
				button=new JButton("Save Observation");
				recipename=new JTextField();
				recipename.setColumns(20);
				recipename.setEditable(true);

				button.addActionListener(
						new ActionListener(){
							@Override
							public void actionPerformed(ActionEvent event) {
								button();
							}
						}
						);
				add(new JLabel("Enter File Name"));
				add(recipename);
				add(button);
				//	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setSize(400,100);
				setVisible(true);

			}
			
			void button(){
				try {
					String recipe=recipename.getText().replaceAll("\\s+", "_");
					printwriter=new PrintWriter(recipe+".txt","UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				printwriter.println(Observer.textarea.getText());
				Observer.saved=true;
				printwriter.close();
				setVisible(false);
				dispose();
			}


		}
		if (saved==true){
			return;
		}
		SaveObservation saveobserve=new SaveObservation();
		// TODO Auto-generated method stub

	}
	public void button(){
		SimpleDateFormat dateformat= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		SimpleDateFormat formal = new SimpleDateFormat("HH:mm:ss");
		// TODO Auto-generated method stub
		if (started==false){
			
			try {
				date1=formal.parse(formal.format(date));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			textarea.setEditable(true);
			usertext.setEditable(true);
			textarea.append("\n"+dateformat.format(date)+": "+button.getText()+"(0 seconds into task) \n");
			started=true;
			clear.setEnabled(false);
			save.setEnabled(false);
			saved=false;
			button.setText("End");

			return;
		}

		if (started==true){
			textarea.setEditable(false);
			usertext.setEditable(false);
			Date date2=null;
			try {
				date2=formal.parse(formal.format(date));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long secs=(date2.getTime()-this.date1.getTime())/1000;
			textarea.append("\n"+dateformat.format(date)+": "+button.getText()+"("+secs+" seconds into task)"+" \n");
			started=false;
			clear.setEnabled(true);
			save.setEnabled(true);
			saved=false;
			button.setText("Start");
			return;
		}
	}
	public void usertext(){
		saved=false;
		SimpleDateFormat dateformat= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		SimpleDateFormat formal = new SimpleDateFormat("HH:mm:ss");
		Date date2 = null;
		try {
			date2=formal.parse(formal.format(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long secs=(date2.getTime()-this.date1.getTime())/1000;
		textarea.append(dateformat.format(date)+": "+usertext.getText()+"("+ secs+" seconds into task) \n");
		usertext.setText("");
		saved=false;
	}
	}




