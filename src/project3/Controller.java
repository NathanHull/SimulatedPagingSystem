package project3;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public final class Controller {

	// Instance of Singleton Controller
	private static final Controller INSTANCE = new Controller();
	
	// Constant variables for sizes
	public static final int PHYSICAL_MEMORY_BYTES = 4096;
	public static final int FRAME_SIZE_BYTES = 512;
	public static final int NUMBER_OF_PAGES = PHYSICAL_MEMORY_BYTES/FRAME_SIZE_BYTES;
	
	// Queue for free frame list
	private static Queue<Frame> freeFrameList = new LinkedList<Frame>();
	
	// Array for physical memory table
	private static Frame physicalMemory[];
	
	// File reader for file currently being read
	private static BufferedReader br;
	
	
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
        EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmMain frmMain = new FrmMain();
					frmMain.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
}