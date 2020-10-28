import java.util.*;

public class Shop {
	public static String numSuffix(int i) {
		int rem = i % 10;
		switch (rem) {
			   case 0:
			   case 4:
			   case 5:
			   case 6:
			   case 7:
		       case 8:
		       case 9:
		    	      return (i + "th");
		       case 1: 
		    	      if (i % 100 != 11)
		    	    	  	return (i + "st");
		    	      else
		    	    	  	return (i + "th");
		       case 2:
		    	      if (i % 100 != 12)
		    	    	  	return (i + "nd");
		    	      else 
		    	    	  	return (i + "th");
		       case 3:
		    	      if (i % 100 != 13)
		    	    	  	return (i + "rd");
		    	      else
		    	    	  	return (i + "th");
		       default:
		    	   break;

		}
		return "";
	}

	public static int chooseFunction(Scanner input) {
		int function;
		System.out.println("This program supports 4 functions:");
		System.out.println("   1. Setup Shop\n   2. Buy\n   3. List Items\n   4. Checkout");
		System.out.print("Please choose the function you want: ");
		function = input.nextInt();

		return function;
	}

	public static void setup(int numItems, String[] itemNames, double[] itemPrice, int[] itemPairings, double[] additDisc, Scanner input) {
		System.out.println();
		double minAmntDisc;
		double additionalDisc;
		for(int i = 0; i < numItems; i++) {
			System.out.print("Enter the name of the " + numSuffix(i+1) + " product: ");
			itemNames[i] = input.next();													//item name
			System.out.print("Enter the per package price of " + itemNames[i] + ": ");
			itemPrice[i] = input.nextDouble();												//item price set
			System.out.print("Enter the number of packages ('x'); to qualify for Special Discount "
					+ "(buy 'x' get 1 free)\nfor "+ itemNames[i] + ", or 0 if no Special Discount offered: ");
			itemPairings[i] = input.nextInt();												//special discount pckges	
			
		}		
		System.out.println("");
		System.out.print("Enter the dollar amount to qualify for Additional Discount (or 0 if none offered): ");
		minAmntDisc = input.nextDouble();
		additDisc[0] = minAmntDisc;
		if(minAmntDisc > 0) {
			System.out.print("Enter the Additional Discount rate (e.g., 0.1 for 10%): ");
			additionalDisc = input.nextDouble();
			while((additionalDisc < 0) || (additionalDisc > 0.5)) {         //Will assure the additional discount rate is right(will keep asking)
				System.out.print("Invalid input. Enter a value > 0 and <= 0.5: ");
				additionalDisc = input.nextDouble();
			}	
			additDisc[1] = additionalDisc;
		}
		System.out.println();
	}

	public static void buy(int numItems, String[] itemName, double[] itemAmount, Scanner input) {
		System.out.println();
		for(int i = 0; i < numItems; i++) {
			System.out.print("Enter the number of " + itemName[i] + " packages to buy: ");
			double productAmount = input.nextDouble();
			while(productAmount < 0) {         //Assures that the amount set for product is correct
				System.out.print("Invalid input. Enter a value >= 0: ");
				productAmount = input.nextDouble();
			}
			itemAmount[i]+=productAmount;
		}
		System.out.println();
	}

	public static void listItems(int numItems, String[] itemName, double[] itemAmount,double[] itemPrice) {
		int itemAmountTst = 0;
		System.out.println();
		for(int i = 0; i < numItems; i++) {
			if(itemAmount[i] > 0) {
				if(itemAmount[i] == 1) { //Will handle plural for package(s)
					System.out.printf("%d package of %s  @ $%.02f per pkg = $%.02f\n",((int)itemAmount[i]), itemName[i], itemPrice[i], (itemAmount[i]*itemPrice[i]));
				}else {
					System.out.printf("%d packages of %s  @ $%.02f per pkg = $%.02f\n",((int)itemAmount[i]), itemName[i], itemPrice[i], (itemAmount[i]*itemPrice[i]));
				}
				itemAmountTst++;
			}
		}
		if(itemAmountTst == 0) { //Will print out nothing if all of the packages had input of 0 for amount
			System.out.println("No items were purchased.");
		}
		System.out.println();
	}

	public static void checkOut(int numItems, double[] itemAmount, double[] itemPrice, int[]itemPairings, double[] additDisc) {
		double originalSub = 0;
		double newSubTotal = 0;
		double specialDiscTotal = 0;
		for(int i = 0; i < numItems; i++) {
			originalSub+=(itemAmount[i] * itemPrice[i]);
			if(itemAmount[i] > 0) {
				int tempA = itemPairings[i]+1;
				specialDiscTotal+= ((((itemAmount[i] - (itemAmount[i]%tempA)))/tempA)*(itemPrice[i])); //special discount adding together
			}
		}
		newSubTotal = originalSub - specialDiscTotal;
		System.out.printf("\nOriginal Sub Total:\t\t  $%.02f\n", originalSub);           //Prints out orig. subtotal
		if(specialDiscTotal > 0) {
			System.out.printf("Special Discounts:\t\t -$%.02f\n", specialDiscTotal);    //prints special discount if its true
		}else {
			System.out.println("No Special Discounts applied");							
		}
		System.out.printf("New Sub Total:\t\t\t  $%.02f\n", newSubTotal);					//new subtotal
		
		if(additDisc[0] != 0 && (newSubTotal > additDisc[0])) {		//prints out additional discount if threshold was passed and if there was one
			System.out.printf("Additonal %d%% Discount:\t\t -$%.02f\n" , ((int)(additDisc[1]*100)), (newSubTotal*+additDisc[1]));
		}else {
			System.out.println("You did not qualify for an Additional Discount");
		}
		System.out.printf("Final Sub Total:\t\t  $%.02f\n", (newSubTotal - (newSubTotal*additDisc[1])));   //final total
		
	}

	public static void init(int[] arr) {
		for(int i = 0; i < arr.length; i++) {
			arr[i] = 0;
		}
	}
	public static void init(double[] arr) {
		for(int i = 0; i < arr.length; i++) {
			arr[i] = 0;
		}
	}
	//clears out values/init
	public static void init(int[] setupProcess, String[] itemNames, double[] itemPrice, double[] itemAmount, int[] itemPairings){
		init(setupProcess);
		for(int i = 0; i < itemNames.length; i++) {
			itemNames[i] = "";
			itemPrice [i] = 0;
			itemAmount[i] = 0;
			itemPairings[i] = 0;
		}
	}

	public static void main (String[] args) {
		Scanner input = new Scanner(System.in);
		int response;
		int max = 100;       //For overloading
		
		int[] setupProcess = {0,0,0,0};     //Allows to keep track of what function has been completed
		String[] itemName = new String[max];
		double[] itemPrice = new double[max];
		double[] itemAmount = new double[max];
		
		int[] itemPairings = new int[max];     
		double[] additDisc = new double[max];    //Additional discounts from min and rate
		
		
		int numItems = 0;

		do {
			do {
				response = chooseFunction(input);
				if(response == 1) {      // Handles Setup
					if(setupProcess[0] != 0) {    //Will clear values from previous setup (allows multiple setups in a single run)
						init(setupProcess, itemName, itemPrice, itemAmount, itemPairings);
					}
					System.out.print("Please enter the number of items to setup shop: ");
					numItems = input.nextInt();
					setup(numItems, itemName, itemPrice, itemPairings, additDisc, input);
					setupProcess[0] = 1;
				}
				
				if(response == 2 && (setupProcess[0] != 0)) {      // Handles Buying
					buy(numItems, itemName, itemAmount, input);
					setupProcess[1] = 1;
				}else if (response == 2 && (setupProcess[0] == 0)) {
					System.out.println("\nShop is not set up yet!\n");
				}
				
				if(response == 3 && (setupProcess[0] != 0) && (setupProcess[1] != 0)) {  // Handles Listing out items
					listItems(numItems, itemName, itemAmount, itemPrice);
					setupProcess[2] = 1;
				}else if(response == 3 && (setupProcess[0] == 0)) {       //List items was selected but shop was not setup
					System.out.println("\nShop is not set up yet!\n");
				}else if(response == 3 && (setupProcess[1] == 0)) {       // List items was selected but no items were bought
					System.out.println("\nYou have not bought anything!\n");
				}
				
				if(response == 4 && (setupProcess[0] != 0) && (setupProcess[1] != 0)) {  // Handles checkout
					checkOut(numItems, itemAmount, itemPrice, itemPairings, additDisc);	
					System.out.println("\nThanks for coming!\n");
					setupProcess[3] = 1;
				}else if(response == 4 && (setupProcess[0] == 0)) {
					System.out.println("\nShop is not set up yet!\n");	//Checkout was selected but shop was not set up
				}else if(response == 4 && (setupProcess[1] == 0)) {
					System.out.println("\nYou have not bought anything!\n"); // Checkout was selected but no items were bought
				}
			}while(response != 4 || setupProcess[3]==0); // Whole loop will run as long as checkout isn't selected and is not completed
			System.out.println("------------------------------------------------");
			System.out.print("Woud you like to re-run (1 for yes, 0 for no)? ");
			response = input.nextInt();
			System.out.println("------------------------------------------------\n");
			
			if(response == 1) {                                                    
				init(setupProcess, itemName, itemPrice, itemAmount, itemPairings); //Clears out every value that would be from previous run
				init(additDisc);
				numItems = 0;
			}
		}while(response != 0); //if response is = 0, the whole program will quit 
		
		input.close();
	}
}