/*
 *
 * M411 LB03 Bier Webservice
 *
 */

package ch.oconnor.WebService;

import java.util.Scanner;


/**
 *
 * @author Christopher O'Connor
 * @date 22/01/2019
 *
 */


public class Main {

	public static void main(String[] args) {

		new Main().top();

	}


	private static BeerAdmin BA = new BeerAdmin();
	private static Scanner   sc = new Scanner(System.in);


	private void top() {

		String in = "";

		while(!in.equalsIgnoreCase("x")) {

			Menu();

			in = read();

			switch(in) {

				case "1": BA.printStyles(); break;

				case "2": BA.printStyles(read("Enter a Style Name:")); break;

				case "3": BA.getBeerListForStyle(readInt("Enter ID of Style:")); break;

				case "4": BA.printBeer(read("Enter ID of Beer:")); break;

				case "5": BA.loadAll(); break;

				case "6": BA.printAll(); break;

				default:
					System.out.println("\t\t\t" + in + " -> ???..");

			}

		}

	}

	/**
	 * Print String (Menu) for User to choose from.
	 */
	private void Menu(){

		System.out.println(

				"\n\n\t1) Print Styles" +
				  "\n\t2) Search for Style(Name)" +
				  "\n\t3) Print All Beers of 1x Style(ID)" +
				  "\n\t4) Search for Beer(ID)" +
				"\n\n\t5) Load  *All" +
				  "\n\t6) Print *All" +
				"\n\n\tx) Exit"

		);

	}

	/**
	 * Print Prefix "//> " for nice looking Terminal-UI before User Enters something.
	 * Read User Input as String.
	 *
	 * @return String User Entered Input.
	 *
	 */
	private String read() {

		System.out.print("\n\t\t//> ");

		return sc.nextLine().trim();

	}

	/**
	 * Print String (Message) to User Then return String Value from Method {@link #read}.
	 *
	 * @param s String Message for User with additional Information what he could / should Enter.
	 * @return String User Entered Input {@link #read}.
	 *
	 */
	private String read(String s) {

		System.out.println("\t\t//> " + s);

		return read();

	}


	/**
	 * Print String (Message) to User Then check String Value if it is a INT from Method {@link #isInt(String)}.
	 * Loop Process until input is valid (int) (Matches Regex) {@link #isInt(String)}.
	 *
	 * @param s String Message for User with additional Information what he could / should Enter.
	 * @return Valid int value from String User Entered {@link #read}.
	 *
	 */
	private int readInt(String s) {

		String input = read(s);

		while(!(isInt(input))) {

			System.out.println("\t\t//> " + s);
			input = read(s);

		}

		return Integer.valueOf(input);

	}


	/**
	 * Check with Regex If String only contains Numbers
	 * If Regex matches, return True else False and
	 * Print User Message to only Enter Numbers.
	 *
	 * @param s Entered User Input
	 * @return True if String is a Number, False if Exception is Thrown
	 */
	private boolean isInt(String s) {

		if(s.matches("^[0-9]+")) return true;

		System.out.println("\t\t\t//> \"" + s + "\" Is 100% not a Integer!" +
						 "\n\t\t\t//> I'm Sorry! I underestimated your Mental capability to know what a Number is ... [!]" +
					   "\n\n\t\t\t//> hint* ^[0-9]+\n");

		return false;

	}

}
