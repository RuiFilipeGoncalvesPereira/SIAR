package siar;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Calculadora {

	private JFrame frame;
	private JTextField text1;
	private JTextField text2;
	private JTextField textRes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Calculadora window = new Calculadora();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Calculadora() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		text1 = new JTextField();
		text1.setBounds(42, 21, 86, 20);
		frame.getContentPane().add(text1);
		text1.setColumns(10);
		
		text2 = new JTextField();
		text2.setBounds(138, 21, 86, 20);
		frame.getContentPane().add(text2);
		text2.setColumns(10);
		
		JButton btnAdd = new JButton("ADD");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int num1,num2,ans;
				try
				{
					num1=Integer.parseInt(text1.getText());
					num2=Integer.parseInt(text2.getText());
				
				ans = num1 + num2;	
				textRes.setText(Integer.toString(ans)); 
				}catch(Exception e)
				{
					JOptionPane.showMessageDialog(null,"Por favor, Introduza um número válido");
				}
			}
		});
		btnAdd.setBounds(39, 67, 89, 23);
		frame.getContentPane().add(btnAdd);
		
		JButton btnMIn = new JButton("Minus");
		btnMIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int num1,num2,ans;
				try
				{
					num1=Integer.parseInt(text1.getText());
					num2=Integer.parseInt(text2.getText());
				
				ans = num1 - num2;	
				textRes.setText(Integer.toString(ans)); 
				}catch(Exception e1)
				{
					JOptionPane.showMessageDialog(null,"Por favor, Introduza um número válido");
				}
			}
		});
		btnMIn.setBounds(138, 67, 89, 23);
		frame.getContentPane().add(btnMIn);
		
		textRes = new JTextField();
		textRes.setBounds(138, 133, 86, 20);
		frame.getContentPane().add(textRes);
		textRes.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("O resultado:");
		lblNewLabel.setBounds(41, 136, 87, 14);
		frame.getContentPane().add(lblNewLabel);
	}

}
