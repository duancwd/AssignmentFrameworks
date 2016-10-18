import java.util.*;

public class Lab3{
	   
		/* generateArrayList is a method that takes an int, i, and returns
		 * a ArrayList<Integer> that contains the numbers 1 through i in ascending
		 * order.
		 * Use a for loop and list.add() to implement this method.
		 * list.add() adds whatever is inside the () to the end of your array.
		 * for example, list.add(5) will add the number 5 to the end of your array.
		 * 
		 *  Note: Lists and ArrayLists can hold many kinds of things.  Adding
		 *  <Integer> after List or ArrayList specifies that that array will
		 *  contain only objects of type Integer.  Java's compiler is 'smart' enough
		 *  to recognize that the primitive type int can function in many of the ways that
		 *  the Object Integer can, and so it will wrap ints into Integers so that
		 *  they can be used, however int and Integer are NOT the same. */
		
		public static ArrayList<Integer> generateArrayList(int i){
			ArrayList<Integer> list = new ArrayList<Integer>();
			//FIXME
			return list;
		}
		
		/* generateArray is to Arrays what generateArrayList was to ArrayLists
		 * 
		 * For Arrays:
		 * int[i] returns the value at index i of your integer array.
		 * int[i] = 5 sets the value of node at index i of your array to five.
		 * 
		 * Keep in mind that unlike with ArrayLists, you cannot add items to Arrays!
		 * You can overwrite stored values in Arrays, but you cannot increase their size! */
		
		
		public static int[] generateArray(int i){
			int[] arr = new int[i];
			//FIXME
			return arr;
		}
		
		/* printfArrayList displays the contents of the ArrayList<Integer>
		 * it is given using System.out.println and a for loop.
		 * Some helpful commands:
		 * 
		 * System.out.println() will print what's in () to your screen and move your
		 * prompt to a new line.
		 * list.get(i) will return the element at the i'th index of list
		 * list.size() returns the number of elements in list (list length).
		 * NOTE: we named our ArrayList list.  If you named your list foo, 
		 *       these commands would be foo.get(i), foo.size(), and foo.add(E e) */
		
		
		public static void printfArrayList(ArrayList<Integer> list){
			for(int i = 0; i < list.size(); i++){
				System.out.print(list.get(i) + ", ");
			}
			System.out.println();
		}
		
		/* Now we're going to give you a method that does the same thing as
		 * printfArrayList but instead of using for loops it will use an
		 * Iterator.  You will see Iterators and ArrayLists on your homework
		 * so it is important that you can recognize them and manipulate ArrayLists.
		 * 
		 * For more information on ArrayLists see:
		 * http://docs.oracle.com/javase/6/docs/api/java/util/ArrayList.html
		 * 
		 * For more information on Iterators see:
		 * http://docs.oracle.com/javase/7/docs/api/java/util/Iterator.html
		 * 
		 * while is another kind of loop.  A while loop only takes one argument,
		 * a condition, and will continue to run its body until that condition is 
		 * no longer true at which point it will terminate. In this case, the while
		 * loop runs as long as there is another integer in the ArrayList. Once there are
		 * no integers left in the ArrayList, the while loop will terminate. */
		
		public static void printArrayList(ArrayList<Integer> list){
			Iterator<Integer> itr = list.iterator();
			while(itr.hasNext()) {
				Integer element = itr.next();
				System.out.print(element + ", ");
			}
			System.out.println();
		}
		
		/* printArray serves the same function for Arrays that printArrayList and printfArrayList
		 * did for ArrayLists.  Use it if you need to look at the contents of your Arrays
		 * for this assignment.
		 * 
		 * arr.length is the length (or size) of arr.
		 * As with list, arr is just the name of your Array (could be foo.length for example). 
		 * 
		 * Also: notice that it's arr.length rather than arr.length().  This is because length is 
		 * not a method for Arrays but something stored in the Array itself. You must call
		 * ArrayList.size() because ArrayList size can change and so the ArrayList calculates its
		 * size using the size() method whenever it is called.  Arrays don't need to do this because
		 * their length never changes; they can tell you how long they are without needing to calculate.
		 */
		
		public static void printArray(int[] arr){
			for(int i = 0; i < arr.length; i++){
				System.out.print(arr[i] + ", ");
			}
			System.out.println();
		}
		
		
		/* isMemberOfArrayList is a method that takes a ArrayList<Integer>, list, and
		 * an int, i, and checks to see if i is anywhere in list, returning true
		 * if i is a member of list, and false otherwise.  You may find list.size()
		 * and list.get() to be useful here.  Also note that in java the boolean value
		 * true is written as true, and the boolean value false is written as false.
		 * 
		 * if syntax:
		 * 
		 * if(a == b){          //this is the condition (does a equal b?)
		 * 	  a++               //this is the consequence (what you do if a equals b)
		 * }
		 * else{
		 *    a--               //this is the alternative (what you do if a is not equal to b)     
		 * }
		 * 
		 * an if statement takes a condition and, if that condition evaluates to
		 * true, it runs the consequence.  If that condition does not evaluate to
		 * true, then it runs the alternative. This should be familiar from scheme.
		 * NOTE: Anything that is not false is true! */
		
		public static boolean isMemberOfArrayList(ArrayList<Integer> list, int i){
			Iterator<Integer> itr = list.iterator();
			while(itr.hasNext()){
				if(itr.next() == i){
					return true;
				}
			}
			return false;
		}
		
		
		/*isMemberOfArray is the Array equivalent of isMemberOfArrayList.
		 *
		 * Try to understand what the Iterator is doing in isMemberOfArrayList
		 * and then duplicate that functionality for Arrays using for and if.
		 */
		
		public static boolean isMemberOfArray(int[] arr, int i){
			//FIXME
		}
		
		/* Just as you can do Arrays with int[], you can do grids, or 
		 * 2-Dimensional Arrays, with int[][].  You can visualize a 2-Dimensional
		 * Array as a grid.  You can think of int[][] as int[row][column]
		 * where row is the row you're in, and column is the column you're in.
		 * If you visualize a multiplication table for example, the number in
		 * row 5, column 4, would be 20.  If you had a multiplication table called grid
		 * that was represented as a 2-Dimensional Integer Array (or int[][] ) you could
		 * find what 5*4 equaled by looking at grid[5][4] ##.  Your job in makeMultTable
		 * is to create such a multiplication table using your knowledge of nested for
		 * loops and integer Arrays.
		 * 
		 * int[1][2] = 5 sets the value of the node at row 1 and column 2 to 5.
		 * x = int[1][2] sets x to the value of the node at row 1 and column 2.
		 * 
		 * ## This isn't true for your implementation. Remember: Array indices start from 0!
		 */
		
		public static int[][] makeMultTable(int n) {
			//FIXME
		}

		/* isMemberOfGridRow takes an int[][], a rowNumber, and the number whose existence
		 * you wish to determine in the given row.  It should return true if the number is anywhere
		 * in that row of the grid, and false otherwise.  This method will combine what you've
		 * learned in your previous methods.  For example, if I want to check whether 25 is in
		 * the 0th row of my multiplication table this method will return false.  If I want to check
		 * whether 25 is in the 4th row of my multiplication table, it will return true.
		 */
		
		public static boolean isMemberOfGridRow(int[][] grid, int rowNumber, int number){
			//FIXME
		}
	  
		/* Now that we've built the tools (methods) that we want, it's
		 * time to make our main method which will do most of our work for this lab.
		 * The full main method has been given to you, though you should add printArray()
		 * and printArrayList calls to it to check your various methods.  For practice
		 * at home, should you need it, you can always delete the body of the main method
		 * and try to rewrite it using the guidelines below.  Also try writing your own tests.
		 * Testing your code and trying to predict where you might have problems is an invaluable
		 * skill and will help you learn how to avoid similar problems in the future!
		 * 
		 * First: 	create an ArrayList<Integer> called list containing the numbers 1-20 in order.
		 * 
		 * 		    Expected Result: [1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20]
		 * 
		 * 
		 * Second: 	write a for loop that adds 1 to every number in list
		 * 		   	that's smaller than 10 and subtracts 1 from every number in
		 * 		   	list that's bigger than 10.  You will need to use list.set()
		 * 			to replace values in the list with your updated versions.
		 * 			list.set(int index, int value) replaces the value at the given
		 * 			index with that of the given value.  For example list.set(5, 3)
		 * 			would set 3 as the value contained in the node at index 5 of list.
		 * 			REMINDER: list in list.set() can be the name of ANY ArrayList you're
		 * 					  using, list just happens to be what we named ours!
		 * 
		 * 			Expected Result: [2 3 4 5 6 7 8 9 10 9 10 11 12 13 14 15 16 17 18 19]
		 * 
		 * 
		 * Third:	write a for loop that adds to each integer in list the integer
		 * 		   	with the index one larger than it in list, leaving the last
		 *         	number in list unchanged.  For example, if my array is 
		 *         	[1 2 3 4 5] it will be [3 5 7 9 5] after being run through
		 *         	this loop.
		 *         
		 *         	Expected Result: [5 7 9 11 13 15 17 19 19 19 21 23 25 27 29 31 33 35 37 19]
		 * 
		 *         
		 * Finally: check to see if 21 is in list and, if it is, change the value of list
		 *  		at index 5 to 5.
		 *  
		 *  		Expected Result: [5 7 9 11 13 5 17 19 19 19 21 23 25 27 29 31 33 35 37 19]
		 * 
		 * Then: do Steps 1-4 for Arrays.  They will have the same expected results.  Don't worry about
		 * 		 the formatting on the print for the results as long as the numbers are the same
		 *       and in the same order.
		 * 
		 * Remember: You can use System.out.println() and the printfArrayList method 
		 * 			 to check your work as you go!
		 * 
		 * Also Remember: If you haven't finished a section of your code yet and are getting
		 * 				  errors when trying to compile because your main method isn't finding
		 * 				  some of the methods it wants, you can comment out the sections of the main
		 * 				  method that are causing you trouble by using // in front of each line you want 
		 * 			      to block out.
		 *                Remember to uncomment code you comment out once you implement the methods
		 *                they're supposed to test or you may miss errors! */
		
		public static void main(String[] args){
			ArrayList<Integer> list = new ArrayList<Integer>();
			list = generateArrayList(20);
			printfArrayList(list);
			
			for(int i = 0; i < list.size(); i++){
				if(list.get(i) < 10){
					list.set(i, list.get(i) + 1);
				}
				else{
					list.set(i, list.get(i) - 1);
				}
			}
			printfArrayList(list);
			
			for(int i = 0; i < list.size() - 1; i++){
				list.set(i, list.get(i) + (list.get(i + 1)));
			}
			if(isMemberOfArrayList(list, 5)){
				list.set(5, 5);
			}
			printfArrayList(list);
			
			int[] arr = generateArray(20);	
			printArray(arr);
			
			for(int i = 0; i < arr.length; i++){
				if(arr[i] < 10){
					arr[i] = arr[i] + 1;
				}
				else{
					arr[i] = arr[i] - 1;
				}
			}
			printArray(arr);
			
			for(int i = 0; i < arr.length - 1; i++){
				arr[i] = arr[i] + arr[i + 1];
			}
			if(isMemberOfArray(arr, 5)){
				arr[5] = 5;
			}
			printArray(arr);
			
			int[][] grid = makeMultTable(10);
			System.out.println("5*7 = " + grid[4][6]);
			System.out.println("8*2 = " + grid[7][1]);
			System.out.println("9*9 = " + grid[8][8]);
			System.out.println("true, 25 is in row four of my mult table: " + isMemberOfGridRow(makeMultTable(10), 4, 25));
			System.out.println("true, 18 is in row one of my multiplication table: " + isMemberOfGridRow(makeMultTable(10), 1, 18));
			System.out.println("false, 25 is in row three of my mult table: " + isMemberOfGridRow(makeMultTable(10), 3, 25));
	}	
}