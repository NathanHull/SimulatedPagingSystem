package project3;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public final class Controller {

	// Instance of Singleton Controller
	private static final Controller INSTANCE = new Controller();
	
	// Constant variables for sizes
	public int PHYSICAL_MEMORY_BYTES = 4096;
	public int FRAME_SIZE_BYTES = 512;
	public int NUMBER_OF_PAGES = PHYSICAL_MEMORY_BYTES/FRAME_SIZE_BYTES;
	
	// Queue for free frame list, stores index (location)
	// of free frames in physical memory in order
	private static Queue<Integer> freeFrameList = new LinkedList<>();
	
	// Array for physical memory table
	private static Frame physicalMemory[] = new Frame[INSTANCE.NUMBER_OF_PAGES];
	
	// File reader for file currently being read
	private static BufferedReader br;
	
	// Main form, aka view
	private static FrmMain frmMain;
	
	// List of processes currently in memory
	private static ArrayList<ProcessControlBlock> processes = new ArrayList<>();
	
	
	/*
	 * Empty as initialized final static instance for Singleton
	 */
	public Controller() {}
	
	/*
	 * Return final static instance of the controller
	 */
	public static Controller getInstance() {
		return INSTANCE;
	}
	
	/*
	 * Create and display frmMain
	 */
    private static void createAndShowGUI() {
        // Create and display window.
//        try {
//			SwingUtilities.invokeAndWait(new Runnable() {
//				@Override
//				public void run() {
//					try {
						frmMain = new FrmMain();
						frmMain.setVisible(true);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			});
//		} catch (InvocationTargetException e) {
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
    }
    
    /*
     * The controller is also the driver
     */
    public static void main(String[] args) {
        // Create and display GUI
        createAndShowGUI();
    }
    
    /*
     * Called when user selects an input file
     */
    public void loadFile(File f) {
    	if (f.isFile() && f.canRead()) {
    		try {
    			// Close file if one is already open
    			if (br != null && br.ready())
    				br.close();
				FileReader fr = new FileReader(f);
				br = new BufferedReader(fr);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
    
    /*
     * Called when Next button is clicked, advance one line
     * in file and add it to menu
     */
    public void increment() {
    	String line = "";
    	try {
			line = br.readLine();
			
			// Catch EOF - EARLY RETURN
			if (line == null) {
				JOptionPane.showMessageDialog(frmMain, "End of file reached.");
				return;
			}
			
			System.out.println("LINE: " + line);
			frmMain.setLastLineReadText(line);
			String elements[] = line.split(" ");
			ProcessControlBlock pcb = new ProcessControlBlock();
			
			if (elements[1].toLowerCase().equals("halt")) {
				// Received halt command, add all frames in
				// physical memory matching this pid to free list
				for (int x = 0; x < physicalMemory.length; x++) {
					if (physicalMemory[x] != null && physicalMemory[x].getPid().equals(elements[0])) {
						if (!freeFrameList.contains(x))
							freeFrameList.offer(x);
					}
				}
				
				// PCB is left mostly null to tell view to clear
				pcb.setPid(elements[0]);
				
			} else {
				// Adding new process data/text to memory,
				// first create its PCB
				String pid = elements[0];
				int textBytes = Integer.parseInt(elements[1]);
				int dataBytes = Integer.parseInt(elements[2]);

				pcb = new ProcessControlBlock();
				pcb.setPid(elements[0]);
				pcb.setLength(Integer.parseInt(elements[1]) + Integer.parseInt(elements[2]));
				
				// Then add frames to PCB page tables
				int pageNumber = 0;
				while (textBytes != 0) {
					Frame frame = new Frame();
					frame.setPid(pid);
					frame.setPage(pageNumber);
					frame.setType("text");
					
					// Decrement amount of bytes left by
					// size of frame, or remainder if smaller
					System.out.println("Text size: " + textBytes);
					frame.setSize(Math.min(FRAME_SIZE_BYTES, textBytes));
					textBytes -= Math.min(FRAME_SIZE_BYTES, textBytes);
					pageNumber++;
					
					// Add frame to PCB page table
					pcb.appendFrame(frame);
				}
				
				pageNumber = 0;
				while (dataBytes != 0) {
					Frame frame = new Frame();
					frame.setPid(pid);
					frame.setPage(pageNumber);
					frame.setType("data");
					
					frame.setSize(Math.min(FRAME_SIZE_BYTES, dataBytes));
					dataBytes -= Math.min(FRAME_SIZE_BYTES, dataBytes);
					pageNumber++;
					
					pcb.appendFrame(frame);
				}
				
				processes.add(pcb);
				
				// Finally, add process page table to memory
				
				// Ctr to keep track of number of pages added
				// to physical memory
				int pagesAdded = 0;
				
				// First try filling empty spots
				for (int x = 0; x < physicalMemory.length; x++) {
					if (physicalMemory[x] == null) {
						physicalMemory[x] = pcb.getPageTable().get(pagesAdded);
						pagesAdded++;
						if (pagesAdded >= pcb.getPageTableSize())
							break;
					}
				}
				
				// If not all pages have been added...
				while (pagesAdded < pcb.getPageTableSize()) {
					int freeFrame =	freeFrameList.poll();
					physicalMemory[freeFrame] = pcb.getPageTable().get(pagesAdded);
					pagesAdded++;
				}
			}
			
			// Update view
			frmMain.updateTables(physicalMemory, freeFrameList, pcb);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void changeDimensions(int pm, int fs) {
    	System.out.println("CHANGING DIMENSIONS");
    	INSTANCE.PHYSICAL_MEMORY_BYTES = pm;
    	INSTANCE.FRAME_SIZE_BYTES = fs;
    	System.out.println("New PM: " + PHYSICAL_MEMORY_BYTES);
    	System.out.println("New fs: " + FRAME_SIZE_BYTES);
    	System.out.println("New page: " + NUMBER_OF_PAGES);
    	
    	frmMain.dispose();
    	createAndShowGUI();
    }
}