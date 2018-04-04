package project3;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.awt.Font;

public class FrmMain extends JFrame {

	private JPanel contentPane;
	private JTable tblPhysicalMemory;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmMain frame = new FrmMain();
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
	public FrmMain() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 567);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// For selecting input file
		final JFileChooser fc = new JFileChooser();
		
		// Reference to Controller singleton
		final Controller controller = Controller.getInstance();
		
		JLabel lblInputFileTitle = new JLabel("Input File: ");
		lblInputFileTitle.setBounds(149, 21, 67, 16);
		contentPane.add(lblInputFileTitle);
		
		JLabel lblInputFile = new JLabel("");
		lblInputFile.setBounds(217, 21, 157, 16);
		contentPane.add(lblInputFile);
		
		// User clicks button to choose input file
		JButton btnChangeFile = new JButton("Choose File");
		btnChangeFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showOpenDialog(FrmMain.this);

		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            lblInputFile.setText(file.getName());
		            controller.loadFile(file);
		        }
			}
		});
		btnChangeFile.setBounds(8, 16, 117, 29);
		contentPane.add(btnChangeFile);
		
		JLabel lblLastLineReadTitle = new JLabel("Last Line Read: ");
		lblLastLineReadTitle.setBounds(18, 57, 98, 16);
		contentPane.add(lblLastLineReadTitle);
		
		JLabel lblLastRead = new JLabel("");
		lblLastRead.setBounds(118, 57, 288, 16);
		contentPane.add(lblLastRead);
		
		JTable tblPhysicalMemory = new JTable(new DefaultTableModel());
		tblPhysicalMemory.setBounds(16, 129, 200, 90);		
		contentPane.add(tblPhysicalMemory);
		
		JLabel lblPhysicalMemory = new JLabel("Physical Memory");		
		lblPhysicalMemory.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblPhysicalMemory.setBounds(16, 104, 147, 16);
		contentPane.add(lblPhysicalMemory);
	}
}
