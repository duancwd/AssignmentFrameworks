package edu.indiana.cs.c212;

public class Person {
	
	//FIXME
	/**
	 *
	 * A person is a class that has a String firstName, and a String lastName
	 */	
	
	//FIXME
	/**
	 * Write a constructor that takes Strings firstName and lastName
	 * and initializes this class' fields appropriately.
	 * @param firstName The Person's first name
	 * @param lastName The Person's last name
	 */		
	
	//FIXME
	/**
	 * Write a constructor that takes one string of the form
	 * "FirstName LastName" and initializes this class' fields appropriately.
	 * @param name The name of the Person
	 */	
	
	
	//FIXME
	/**
	 * Add public getters and setters for firstName and lastName
	 * called getFirstName, setFirstName, getLastName, and setLastName respectively. 
	 */
	
	
	//FIXME
	/**
	 * The directory name of a person is of the form
	 * "LastName, F.", Where the F is the first initial
	 * of the first name. Write a method getDirectoryName() that
	 * @return a string of the form "LastName, F."
	 */

	//FIXME
	/**
	 * A Person is earlier in the directory than another person if 
	 * their directory name would come earlier in the dictionary.
	 * Write a method isEarlierInDirectory() that take another person
	 * and returns whether or not this person would come earlier in a  
	 * directory. If the two people have the same directory name, than this
	 * method should return false.
	 * 
	 * @param other The person to compare against
	 * @return true or false
	 */
	
	//FIXME
	/**
	 * This main tests the methods of your Person class.
	 * This method will not be graded, so feel free to add your 
	 * own tests. Uncomment out these lines as you implement the methods
	 * they call.
	 * @param args
	 */
	public static void main(String[] args){
		/*Person bob = new Person("Bob", "Sample");
		System.out.println("Bob Sample's first name is: " + bob.getFirstName());
		System.out.println("Bob Sample's last name is: " + bob.getLastName());
		System.out.println();*/
		
		/*Person jane = new Person("Jane Test");
		System.out.println("Jane Test's first name is: " + jane.getFirstName());
		System.out.println("Jane Test's last name is: " + jane.getLastName());
		System.out.println();*/
		
		/*jane.setFirstName("NotJane");
		jane.setLastName("Anymore");		
		System.out.println("Jane Test's new first name is: " + jane.getFirstName() + " (should be NotJane)");
		System.out.println("Jane Test's new last name is: " + jane.getLastName() + " (should be Anymore)");
		System.out.println();*/
		
		/*Person dexter = new Person("Dexter Morgan");
		
		System.out.println("Dexter's directory name is: " + dexter.getDirectoryName() + " (should be Morgan, D.)");
		System.out.println();*/
		
		/*System.out.println("Is Dexter Morgan earlier in the directory than Bob Sample?: " + dexter.isEarlierInDirectory(bob) + " (should be true)");
		System.out.println();*/
		
		/*Person don = new Person("Don Morgan");
		System.out.println("Is Dexter Morgan earlier in the directory than Don Morgan?: " + dexter.isEarlierInDirectory(don) + " (should be false)");
		System.out.println();*/
	}

	
	
}
