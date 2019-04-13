package twopasslinker;

/* *
 * Symbol class that stores information of a symbol
 */
public class Symbol {
	private String name;
	private int abs = -1;
	private int rel;
	private int mod;
	private String err;
	
	public Symbol(String n, int r, int m) {
		this.name = n;
		this.rel = r;
		this.mod = m;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getRel() {
		return this.rel;
	}
	
	public int getAbs() {
		return this.abs;
	}
	
	public int getMod() {
		return this.mod;
	}
	
	public String getErr() {
		return this.err;
	}
	public void setAbs(int a) {
		this.abs = a;
	}
	
	public void setErr(String e) {
		this.err = e;
	}

	public void setRel(int r) {
		this.rel = r;
	}


}
