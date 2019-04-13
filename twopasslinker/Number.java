package twopasslinker;

/* *
 * Number class that stores information of a program text
 */
public class Number {
	private int opt;
	private int address;
	private int type;
	private int memory;
	private int module;
	private Symbol symbol;
	private String error;
	
	public Number(int o, int a, int t) {
		opt = o;
		address = a;
		type = t;
	}
	
	public int getOpt(){
		return this.opt;
	}
	
	public int getAdd() {
		return this.address;
	}
	
	public int getType() {
		return this.type;
	}
	
	public int getMod() {
		return this.module;
	}
	
	public Symbol getSym() {
		return this.symbol;
	}
	
	public int getMem() {
		return this.memory;
	}
	public String getErr() {
		return this.error;
	}
	
	public void setMem(int m) {
		this.memory = m;
	}
	
	public void setMod(int m) {
		this.module = m;
	}
	public void setSym(Symbol x) {
		this.symbol = x;
	}
	public void setType(int x) {
		this.type=5;
	}
	public void setErr(String e) {
		this.error= e;
	}
}
