package project3;

import java.util.ArrayList;

public class ProcessControlBlock {
	private String pid;
	private ArrayList<Frame> pageTable = new ArrayList<>();
	private int length;

	public ProcessControlBlock() {}
	
	/**
	 * @return the pid
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * @param pid the pid to set
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	/**
	 * @return the pageTable
	 */
	public ArrayList<Frame> getPageTable() {
		return pageTable;
	}
	/**
	 * @param pageTable the pageTable to set
	 */
	public void setPageTable(ArrayList<Frame> pageTable) {
		this.pageTable = pageTable;
	}
	/**
	 * @return pageTable size
	 */
	public int getPageTableSize() {
		return pageTable.size();
	}
	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}
	/**
	 * @param length the length to set
	 */
	public void setLength(int length) {
		this.length = length;
	}
	
	/**
	 * Helper method to easily add frames to page table
	 * 
	 * @param frame the frame to add to the page table
	 */
	public void appendFrame(Frame frame) {
		pageTable.add(frame);
	}
}