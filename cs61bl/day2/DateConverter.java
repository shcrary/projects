import java.io.*;

public class DateConverter {

  // Given a day number in 2008, an integer between 1 and 366,
  // as a command-line argument, prints the date in month/day format.
  // Example:
  //	java DateConverter 365
  // should print
  //	 12/30

  // The code is missing two assignment statements.
  public static void main (String [ ] args) {
    int dayOfYear = 0;
    try {
      dayOfYear = Integer.parseInt (args[0]);
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
    int month, dateInMonth, daysInMonth;
    month = 1;
    daysInMonth = 31;
 
    


    if(month >12 || dayOfYear  < 1){
     	 System.out.print("Out of bound. Please enter the number between 1 and 366.");
           return;
      }
    
    
    while (dayOfYear > daysInMonth) {
        dayOfYear = dayOfYear-daysInMonth;
        month++;   	
      if (month == 2) {
	daysInMonth = 29;
      } else if (month == 4 || month == 6 || month == 9 || month == 11) {
	daysInMonth = 30;
      } else {
	daysInMonth = 31;
      }
}


    
    dateInMonth = dayOfYear;
    System.out.println (month + "/" + dateInMonth);
  }
}
