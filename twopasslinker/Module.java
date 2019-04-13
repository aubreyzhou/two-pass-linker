package twopasslinker;

import java.util.ArrayList;

/* *
 * Module class that stores information of a module, including base address, length and the uses in the module
 */
public class Module{
	private int base;
	private int length;
	private ArrayList<ArrayList<String>> useList = new ArrayList<ArrayList<String>>();
	
	public Module() {
		
	}
	public Module(int b, int l) {
		base = b;
		length = l;
	}
	
	public void setBase(int b) {
		this.base = b;
	}
	
	public void setLength(int l) {
		this.length = l;
	}
	public int getBase() {
		return this.base;
	}
	
	public int getLength() {
		return this.length;
	}
	
	public ArrayList<ArrayList<String>> getUse(){
		return this.useList;
	}
}
