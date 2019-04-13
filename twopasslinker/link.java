package twopasslinker;

import java.util.Scanner;

/* *
 * The main class that is responsible for obtaining user input and running the program.
 * @author Yutong Zhou
 *
 */
public class link {

	public static void main(String[] args) {
		//prompt the user for input
		 System.out.println("Please enter the input and press enter");
		 Scanner input = new Scanner(System.in);
		 Linker l = new Linker();
		 //first pass
		 l.one(input);
		 //second pass
		 l.two();
		 
	}

}
