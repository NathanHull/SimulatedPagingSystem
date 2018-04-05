package project3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Queue;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.awt.Font;
import javax.swing.JFormattedTextField;

public class FrmMain extends JFrame {
	
	// Reference to Controller singleton
	final Controller controller;

	public JPanel contentPane;
	public JLabel lblLastLineRead;
	public JLabel lblProcessMemory;

	String physicalData[][];
	String processData[][];

	
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
		setBounds(100, 100, 848, 359);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// For selecting input file
		final JFileChooser fc = new JFileChooser();
		
		controller = Controller.getInstance();
		
		JLabel lblInputFileTitle = new JLabel("Input File: ");
		lblInputFileTitle.setBounds(149, 21, 67, 16);
		contentPane.add(lblInputFileTitle);
		
		JLabel lblInputFile = new JLabel("");
		lblInputFile.setBounds(217, 21, 157, 16);
		contentPane.add(lblInputFile);
		
		JButton btnNext = new JButton("Next");
		btnNext.setEnabled(false);
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.increment();
			}
		});
		btnNext.setBounds(8, 292, 117, 29);
		contentPane.add(btnNext);
		
		// User clicks button to choose input file
		JButton btnChangeFile = new JButton("Choose File");
		btnChangeFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fc.setCurrentDirectory(new File("/Downloads"));
				int returnVal = fc.showOpenDialog(FrmMain.this);

		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            lblInputFile.setText(file.getName());
		            controller.loadFile(file);
		            btnNext.setEnabled(true);
		        }
			}
		});
		btnChangeFile.setBounds(8, 16, 117, 29);
		contentPane.add(btnChangeFile);
		
		JLabel lblLastLineReadTitle = new JLabel("Last Line Read: ");
		lblLastLineReadTitle.setBounds(18, 57, 98, 16);
		contentPane.add(lblLastLineReadTitle);
		
		lblLastLineRead = new JLabel("");
		lblLastLineRead.setBounds(118, 57, 288, 16);
		contentPane.add(lblLastLineRead);
	
		
		// ====== PROCESS PAGE TABLE REPRESENTATION ======	
		
		// Add table data statically because editing Model crashes
		// Eclipse on Mac, some black magic with AWT/SWT deadlocking..?
		// https://www.eclipse.org/forums/index.php/t/273408/
		String processColumnNames[] = {"Page #", "Data"};

		// Data initalized globally so it can be changed from controller
		processData = new String[controller.NUMBER_OF_PAGES][2];
		for (int x = 0; x < processData.length; x++) {
			processData[x][0] = "" + x;
			processData[x][1] = "";
		}
		
		JScrollPane spProcessMemory = new JScrollPane();
		spProcessMemory.setBounds(16, 129, 400, 148);
		JTable tblProcessMemory = new JTable(processData, processColumnNames);
		tblProcessMemory.setGridColor(Color.BLACK);
		tblProcessMemory.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		spProcessMemory.setViewportView(tblProcessMemory);
		contentPane.add(spProcessMemory);
		
		lblProcessMemory = new JLabel("Process Memory");		
		lblProcessMemory.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblProcessMemory.setBounds(16, 104, 147, 16);
		contentPane.add(lblProcessMemory);
		
		
		// ====== PHYSICAL TABLE REPRESENTATION ======
		String physicalColumnNames[] = {"Frame #", "Data"};
		
		// Data initialized globally so it can be changed from controller
		physicalData = new String[controller.NUMBER_OF_PAGES][2];
		for (int x = 0; x < physicalData.length; x++) {
			physicalData[x][0] = "" + x;
			physicalData[x][1] = "";
		}
		
		JScrollPane spPhysicalMemory = new JScrollPane();
		spPhysicalMemory.setBounds(436, 129, 400, 148);
		JTable tblPhysicalMemory = new JTable(physicalData, physicalColumnNames);
		tblPhysicalMemory.setGridColor(Color.BLACK);
		tblPhysicalMemory.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		spPhysicalMemory.setViewportView(tblPhysicalMemory);
		contentPane.add(spPhysicalMemory);
		
		JLabel lblPhysicalMemory = new JLabel("Physical Memory");		
		lblPhysicalMemory.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblPhysicalMemory.setBounds(436, 104, 147, 16);
		contentPane.add(lblPhysicalMemory);
		
		JLabel lblPS = new JLabel("* Free Frames");
		lblPS.setBounds(551, 297, 85, 16);
		contentPane.add(lblPS);
		
		JFormattedTextField txtPM = new JFormattedTextField();
		txtPM.setText("" + controller.PHYSICAL_MEMORY_BYTES);
		txtPM.setBounds(569, 16, 67, 26);
		contentPane.add(txtPM);
		
		JLabel lblPM = new JLabel("Physical M (bytes): ");
		lblPM.setBounds(448, 21, 122, 16);
		contentPane.add(lblPM);
		
		JLabel lblFrameSize = new JLabel("Frame Size (bytes): ");
		lblFrameSize.setBounds(448, 52, 122, 16);
		contentPane.add(lblFrameSize);
		
		JFormattedTextField txtFrameSize = new JFormattedTextField();
		txtFrameSize.setText("" + controller.FRAME_SIZE_BYTES);
		txtFrameSize.setBounds(569, 47, 67, 26);
		contentPane.add(txtFrameSize);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.changeDimensions(Integer.parseInt(txtPM.getText()), Integer.parseInt(txtFrameSize.getText()));
			}
		});
		btnSubmit.setBounds(525, 80, 117, 29);
		contentPane.add(btnSubmit);
		
		contentPane.repaint();
	}
	
	public void updateTables(Frame physicalMemory[], Queue<Integer> freeFrameList, ProcessControlBlock pcb) {
		System.out.println("UPDATING");
		// Clear process table
		for (int x = 0; x < processData.length; x++) {
			processData[x][1] = "";
		}

		if (pcb.getPageTable() == null) {
			// No page table initialized, i.e. a process was halted
			lblProcessMemory.setText("Process " + pcb.getPid() + " Memory");
		} else {
			// Otherwise, load processor data in
			lblProcessMemory.setText("Process " + pcb.getPid() + " Memory");
			ArrayList<Frame> frames = pcb.getPageTable();
			for (int x = 0; x < pcb.getPageTableSize(); x++) {
				processData[x][1] = frames.get(x).toString();
			}
		}
		
		for (int x = 0; x < physicalData.length; x++) {
			if (physicalMemory[x] == null) {
				physicalData[x][1] = "";
			} else {
				if (freeFrameList.contains(x))
					physicalData[x][1] = '*' + physicalMemory[x].toString();
				else
					physicalData[x][1] = physicalMemory[x].toString();
			}
		}
		
		contentPane.repaint();
	}
	
	public void setLastLineReadText(String s) {
		lblLastLineRead.setText(s);
	}
}
