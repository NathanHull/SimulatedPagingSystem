package project3;

public class ProcessControlBlock {
	private int pid;
	private Frame pageTable[];
	private int length;
	
	/**
	 * @return the pid
	 */
	public int getPid() {
		return pid;
	}
	/**
	 * @param pid the pid to set
	 */
	public void setPid(int pid) {
		this.pid = pid;
	}
	/**
	 * @return the pageTable
	 */
	public Frame[] getPageTable() {
		return pageTable;
	}
	/**
	 * @param pageTable the pageTable to set
	 */
	public void setPageTable(Frame[] pageTable) {
		this.pageTable = pageTable;
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
	
	
}