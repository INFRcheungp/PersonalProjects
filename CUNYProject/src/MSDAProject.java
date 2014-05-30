/**
 * @author petercheung
 * 05/13/14
 * CUNY Masters in Data Analytics Assignment
 * 
 * This program asks the user to enter criteria from the consoel
 */

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;


public class MSDAProject {
	private static boolean validInput = false;
	private static Scanner actionInput = new Scanner(System.in);
	private static Double caratInput; 
	private static Double depthInput;
	private static Integer priceInput;
	private static int criteriaInput;
	private static String cutInput;
	private static String colorInput;
	private static String clarityInput;
	private static String dataColumn1 = "";
	private static String dataColumn2 = "";
	private static String data1 = "";
	private static String data2 = "";
	private static String selectSQLQuery;

	public static void main(String[] args) {
		
		try {
			/* Returns the Class object associated with the class or 
			 * interface with the given string name.
			 */
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("MySQL JDBC Driver not found.");
			e.printStackTrace();
			return;
		}
	 
		System.out.println("MySQL JDBC Driver Registered!");
		
		/* A connection (session) with a specific database. SQL statements 
		 * are executed and results are returned within the context of a connection.
		 */
		Connection connection = null;
		PreparedStatement preparedStatement = null;
	 
		try {
			// DriveManager is the basic service for managing a set of JDBC drivers.
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306","root", "11181begin");
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console\n" + e.getMessage());
			return;
		} 
	 
		// Successful database connection section.
		if (connection != null) {
			System.out.println("Connected to the database successfully!");
			

			System.out.println("----------Diamonds Searcher----------\n"
					+ "Please select two of the following criteria by entering their numbers: ");
			
	        System.out.println("(1) Search by carat\n"
	        		+ "(2) Search by cut\n"
	        		+ "(3) Search by color\n"
	        		+ "(4) Search by clarity\n"
	        		+ "(5) Search by depth\n"
	        		+ "(6) Search by price\n");
	        
	        System.out.print("First criteria: ");
	        criteriaInputValidator();
	        criteriaSelector(criteriaInput);
	        
	        System.out.print("Second criteria: ");	        
	        criteriaInputValidator();
	        criteriaSelector(criteriaInput);
	        
	        
	        // Execute the query, print results, and close the , ResultSet, PreparedStatement, and Connection objects.
			try {
			    selectSQLQuery = "SELECT * FROM CUNY_DB.diamonds WHERE " + 
	        			dataColumn1 + "='" + data1 + "' AND "+ 
	        			dataColumn2 + "='" + data2 + "';";
			    
		        System.out.println(selectSQLQuery);

				preparedStatement = connection.prepareStatement(selectSQLQuery);
				ResultSet queryResults = preparedStatement.executeQuery();
				
				System.out.println("\n----------Diamonds Results----------\n");
				System.out.print(String.format("%-10s", "ID", "%10s"));			
				System.out.print(String.format("%-10s", "Carat", "%10s"));
				System.out.print(String.format("%-10s", "Cut", "%10s"));
				System.out.print(String.format("%-10s", "Color", "%10s"));
				System.out.print(String.format("%-10s", "Clarity", "%10s"));
				System.out.print(String.format("%-10s", "Depth", "%10s"));
				System.out.print(String.format("%-10s", "Price", "%10s") + "\n");
				
				while (queryResults.next()) {
					System.out.print(String.format("%-10s", queryResults.getInt(1), "%10s"));			
					System.out.print(String.format("%-10s", queryResults.getDouble(2), "%10s"));
					System.out.print(String.format("%-10s", queryResults.getString(3), "%10s"));
					System.out.print(String.format("%-10s", queryResults.getString(4), "%10s"));
					System.out.print(String.format("%-10s", queryResults.getString(5), "%10s"));
					System.out.print(String.format("%-10s", queryResults.getDouble(6), "%10s"));
					System.out.print(String.format("%-10s", queryResults.getInt(7), "%10s") + "\n");
				}
				queryResults.close();
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} else {
			System.out.println("Failed to make connection!");
		}
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static void criteriaSelector(int criteriaInput) {
        switch(criteriaInput) {
    	case(1):	            	
    		caratInputValidator();
    		dataColumn1 = "carat";
    		if(dataColumn1.isEmpty()) {
        		dataColumn1 = "carat";
    		} else {
    			dataColumn2 = "carat";
    		}
    		break;
    	case(2):
    		cutInputValidator();
			if(dataColumn1.isEmpty()) {
	    		dataColumn1 = "cut";
			} else {
				dataColumn2 = "cut";
			}
	    	break;
    	case(3):
    		colorInputValidator();
			if(dataColumn1.isEmpty()) {
	    		dataColumn1 = "color";
			} else {
				dataColumn2 = "color";
			}
	    	break;
    	case(4):
    		clarityInputValidator();
			if(dataColumn1.isEmpty()) {
	    		dataColumn1 = "clarity";
			} else {
				dataColumn2 = "clarity";
			}
	    	break;
    	case(5):
    		depthInputValidator();
			if(dataColumn1.isEmpty()) {
	    		dataColumn1 = "depth";
			} else {
				dataColumn2 = "depth";
			}
	    	break;
    	case(6):
    		priceInputValidator();
			if(dataColumn1.isEmpty()) {
	    		dataColumn1 = "price";
			} else {
				dataColumn2 = "price";
			}
	    	break;
        }
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static void criteriaInputValidator() {
	    while (!validInput) {
	        try {
	        	criteriaInput = actionInput.nextInt();
				System.out.println("You entered " + criteriaInput);
				validInput = true;
	        } catch (InputMismatchException e) {
	            System.out.println("Invalid data type input. Please try again: ");
	            actionInput.nextLine();
	        }
	    }	 
		validInput = false;	
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static void priceInputValidator() {
	    while (!validInput) {
	        try {
				System.out.print("You selected to search by price. Please enter a price: ");
				priceInput = actionInput.nextInt();
				System.out.println("You entered: " + priceInput);
				validInput = true;
					
				if(data1.isEmpty()) {
					data1 = priceInput.toString();	
				} else {
					data2 = priceInput.toString();
				}
				
				} catch (InputMismatchException e) {
	            System.out.println("Invalid data type input. Please try again: ");
	            actionInput.nextLine();
	        }
	    }	 
		validInput = false;			
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static void depthInputValidator() {
	    while (!validInput) {
	        try {
				System.out.print("You selected to search by depth. Please enter a depth: ");
				depthInput = actionInput.nextDouble();
				System.out.println("You entered: " + depthInput);
				validInput = true;
				if(data1.isEmpty()) {
		    		data1 = depthInput.toString();
				} else {
					data2 = depthInput.toString();
				}
	        } catch (InputMismatchException e) {
	            System.out.println("Invalid data type input. Please try again: ");
	            actionInput.nextLine();
	        }
	    }	 
		validInput = false;			
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static void clarityInputValidator() {
	    while (!validInput) {
	        try {
				System.out.print("You selected to search by clarity. Please enter a clarity: ");
				clarityInput = actionInput.next();
				clarityInput.toUpperCase();
				System.out.println("You entered: " + clarityInput);
				validInput = true;
				if(data1.isEmpty()) {
		    		data1 = clarityInput;
				} else {
					data2 = clarityInput;
				}
	        } catch (InputMismatchException e) {
	            System.out.println("Invalid data type input. Please try again: ");
	            actionInput.nextLine();
	        }
	    }	 
		validInput = false;			
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static void colorInputValidator() {
	    while (!validInput) {
	        try {
				System.out.print("You selected to search by color. Please enter a color: ");
				colorInput = actionInput.next();
				colorInput.toUpperCase();
				System.out.println("You entered: " + colorInput);
				validInput = true;
				if(data1.isEmpty()) {
					System.out.println("TESTING");
		    		data1 = colorInput;
				} else {
					data2 = colorInput;
					System.out.println("TESTING2 " + data2);
				}
	        } catch (InputMismatchException e) {
	            System.out.println("Invalid data type input. Please try again: ");
	            actionInput.nextLine();
	        }
	    }	 
		validInput = false;		
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static void caratInputValidator(){ 
        while (!validInput) {
            try {
    			System.out.print("You selected to search by carat. Please enter a carat: ");
    			caratInput = actionInput.nextDouble();
    			System.out.println("You entered: " + caratInput);
    			validInput = true;
				if(data1.isEmpty()) {
		    		data1 = caratInput.toString();
				} else {
					data2 = caratInput.toString();
				}
            } catch (InputMismatchException e) {
                System.out.println("Invalid data type input. Please try again: ");
                actionInput.nextLine();
            }
        }
		validInput = false;
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static void cutInputValidator() {
		while (!validInput) {
			try {
				System.out.print("You selected to search by cut. Please enter a cut: ");
				cutInput = actionInput.next();
				cutInput.toUpperCase();
				System.out.println("You entered: " + cutInput);
				validInput = true;
				if(data1.isEmpty()) {
		    		data1 = cutInput;
				} else {
					data2 = cutInput;
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid data type input. Please try again: ");
				actionInput.nextLine();
			}
		}
		validInput = false;		
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

