package twopasslinker;

import java.util.ArrayList;
import java.util.Scanner;

/* *
 * The linker class that contains methods to process the input
 */
public class Linker {
	private ArrayList<Module> modList = new ArrayList<Module>();
	private ArrayList<Symbol> symList = new ArrayList<Symbol>();
	//total number of 5-digit numbers
	private int count;
	private ArrayList<Number> numList = new ArrayList<Number>();
	
	/**
	 * Store input into lists of modules, symbols and numbers and prepare for further processing
	 * @param Scanner object input that takes in inputs
	 */
	public void one(Scanner input) {
		
		int modNum = input.nextInt();
		 //for each module
		 for(int i=0;i<modNum;i++) {
			 Module m = new Module();
			 //add the module to the list of modules
			 modList.add(m);
			 
			 int defNum = Integer.parseInt(input.next()); 
			 //for each definition, build a new symbol object with its name and relative address 
			 for(int j=0;j<defNum;j++) {
				 String name = input.next();
				 //if name exceeds 6 characters, take the first 6 characters as name
				 if(name.length()>6) {
					 System.out.println("Error: The length name of symbol exceeds 6; first 6 characters taken as name");
					 char[] n = name.toCharArray();
					 name = null;
					 for(int a=0;a<6;a++) {
						 name+=n[a];
					 }
				 }
				 int rel = Integer.parseInt(input.next());
				 Symbol s;
				 //symbol is already defined, remove it from the list
				 if(indexOfSym(name)!=-1) {
					 symList.remove(indexOfSym(name));
					 s = new Symbol(name,rel,i);
					 s.setErr("Error:This variable is multiply defined;last value used");
				 }
				 else {
					 s = new Symbol(name,rel,i);
				 } 
				//add the symbol to the list of symbols
				 symList.add(s);
			 }
			 
			 //add uses to useList for future processing
			 int useNum = input.nextInt();
			 for(int k=0;k<useNum;k++) {
				 //array list for storing uses of each symbol
				 ArrayList<String> s = new ArrayList<String>();
				 m.getUse().add(s);
				 s.add(input.next());
				 String loc = input.next();
				 while(Integer.parseInt(loc)!=-1) {
					 s.add(loc);
					 loc = input.next();
				 }
			 }
			 
			 //process texts
			 int numNum =Integer.parseInt(input.next());
			 count += numNum;
			 //set module's length
			 m.setLength(numNum);
			 //set the first module's base address to 0
			 if(i==0)
				 m.setBase(0);
			 //set the module's base as sum of last module's base and length
			 else
				 m.setBase(modList.get(i-1).getLength()+modList.get(i-1).getBase());
			
			 //for each text entry, build a Number object and store it in numList
			 for(int r=0;r<numNum;r++) {
				 int number = Integer.parseInt(input.next());
				 int type = number%10;
				 int opt = number/10000;
				 int add = (number/10)%1000;
				 Number n = new Number(opt,add,type);
				 //set the number's module base address
				 n.setMod(m.getBase());
				 numList.add(n);
			 }
		 }		 
		 
		 //set all symbols' absolute address as sum of its relative address and the base address of its module
		 for(int o=0;o<symList.size();o++) {
			 Symbol sym=symList.get(o);
			 int mod=sym.getMod();
			 //relative address exceeds length of module
			 if(sym.getRel()>=modList.get(mod).getLength()) {
				 sym.setRel(modList.get(mod).getLength()-1);
				 sym.setErr("Error: Definition exceeds module size; last word in module used.");
			 }
			 int abs =sym.getRel()+modList.get(mod).getBase(); 
			 sym.setAbs(abs);
		 }
		 
		 //print symbol table
		 System.out.println("Symbol Table");
		 for(int p=0;p<symList.size();p++) {
			 if(symList.get(p).getErr()!=null)
				 System.out.println(symList.get(p).getName()+"="+symList.get(p).getAbs()+" "+symList.get(p).getErr());
			 else
				 System.out.println(symList.get(p).getName()+"="+symList.get(p).getAbs());
		 }
		 System.out.println();
	}
	/**
	 * Process the uses and generate memory map
	 */
	public void two() {
		ArrayList<Symbol> used = new ArrayList<Symbol>();
		//process use list
		for(int i=0;i<modList.size();i++) {
			ArrayList<ArrayList<String>> s = modList.get(i).getUse();
			//uses in each module
			for(int j=0;j<s.size();j++) {
				ArrayList<String> u = s.get(j);
				 //use of each symbol
				for (int o=1;o<u.size();o++) {
					//relative address of the symbol
					int rel = Integer.parseInt(u.get(o));
					//get the number that matches the symbol--base address of module + relative address of symbol
					Number match = numList.get(modList.get(i).getBase()+rel);
					if(indexOfSym(u.get(0))!=-1) {
						//set its symbol
						if(match.getSym()!=null)
							match.setErr("Error: Multiple variables used in instruction; all but last ignored.");
						match.setSym(symList.get(indexOfSym(u.get(0))));
						used.add(symList.get(indexOfSym(u.get(0))));
					}
					
					 else {
						 match.setErr("Error: "+u.get(0)+" is not defined; 111 used");
						 match.setType(5);
					 }
				}
					
			}
		}
		//print memory map according to each text's type
		System.out.println("Memory Map");
		for(int k=0;k<count;k++) {
			Number c = numList.get(k);
			 if(c.getType()==1) 
				 c.setMem(c.getOpt()*1000+c.getAdd());
				 
			 else if(c.getType()==2) {
				 if(c.getAdd()>=300) {
					 c.setMem(c.getOpt()*1000+299);
					 c.setErr("Error: Absolute address exceeds machine size; largest legal value used.");
				 }
				 else 
					 c.setMem(c.getOpt()*1000+c.getAdd());	  
			 }
				
			 else if(c.getType()==3) 
				 c.setMem(c.getOpt()*1000+c.getAdd()+c.getMod());
				 
			 else if(c.getType()==4) 
				 c.setMem(c.getOpt()*1000+c.getSym().getAbs());
				
			 else if(c.getType()==5) 
				 c.setMem(c.getOpt()*1000+111);
			
			 //print memory map and errors
			 if(c.getErr()!=null) {
				 System.out.printf("%-4s",k+": ");
				 System.out.println(c.getMem()+" "+c.getErr()); 
			 }
				 
			 else {
				 System.out.printf("%-4s",k+": ");
				 System.out.println(c.getMem());
			 }
				 
		}
		//check if any symbol is defined but not used
		for(int p=0;p<symList.size();p++) {
			Symbol test = symList.get(p);
			if(!(used.contains(test)))
				System.out.println("Warning: "+test.getName()+" was defined in module "+test.getMod()+" but never used" );
		}
		
	}
	/**
	 * Get the index of the symbol with given name in symList
	 * @param name of a symbol
	 * @return index of the symbol corresponds to the name
	 */
	public int indexOfSym(String name) {
		for (int i=0;i<symList.size();i++) {
			if(symList.get(i).getName().equals(name))
				return i;
		}
		return -1;
	}
}
