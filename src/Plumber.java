import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Plumber {
    public static void main(String args[]) throws ParseException {

        //setup
        Scanner sc = new Scanner(System.in);
        Scanner scan = new Scanner(System.in);
        Scanner scanner = new Scanner(System.in);
        double weekdayDay = 52;
        double weekdayNight = (weekdayDay * 0.5) + weekdayDay;
        double SaturdayDay = 76;
        double SaturdayNight = (SaturdayDay * 0.5) + SaturdayDay;
        double SundayDay = 124;
        double SundayNight = (SundayDay * 0.5) + SundayDay;
        double serviceRateDay = 0;
        double serviceRateNight = 0;
        String minimumTwoHours;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat day = new SimpleDateFormat("EEEE");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        SimpleDateFormat timeAmPm = new SimpleDateFormat("hh:mm a");
        Date startingTime = time.parse("8:00");
        Date endingTime = time.parse("17:00");
        Date now = new Date();
        sdf.setLenient(false);
        day.setLenient(false);
        time.setLenient(false);
        scan.useDelimiter(":");
        sc.useDelimiter("/");
        DecimalFormat df = new DecimalFormat("###.##");
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        String choice = "Y";


        //put in a loop if user wants to do again
        while (choice.equalsIgnoreCase("y")) {

            //Intro
            System.out.printf("%50s", "Leaky Plumbing Repair Company" + "\n");
            System.out.printf("%42s", "Service Charges" + "\n");
            System.out.println();

            //acquire name
            System.out.print("Plumber's Name (Last,First) > ");
            String name = sc.nextLine();

            //acquire service date
            System.out.print("Service Date (mm/dd/yyyy) > ");
            String date = sc.nextLine();

            //make sure it's a real date
            boolean one = true;
            while (one) {
                try {
                    sdf.parse(date);
                    one = false;
                } catch (ParseException e) {
                    System.out.print("Please enter a valid date in (mm/dd/yyyy) format: ");
                    date = sc.nextLine();
                }
            }


            //acquire starting time
            System.out.print("Start time > ");
            String start = sc.nextLine();

            //see if it's actually a legit time
            boolean two = true;
            while (two) {
                try {
                    time.parse(start);
                    two = false;
                } catch (ParseException e) {
                    System.out.print("Please try again (hours:minutes) > ");
                    start = sc.nextLine();
                }
            }

            //acquire timeStart
            Date startParsed = time.parse(start);


            //acquire end time
            System.out.print("End time > ");
            String end = scan.nextLine();

            //see if it's an actual time
            boolean three = true;
            while (three) {
                try {
                    time.parse(end);
                    three = false;
                } catch (ParseException e) {
                    System.out.print("Please try again (hours:minutes) > ");
                    end = scan.nextLine();
                }
            }

            //acquire timeEnd
            Date endParsed = time.parse(end);

            //subtract endTime with startTime
            double totalTime = Long.valueOf(endParsed.getTime() - startParsed.getTime()).doubleValue();

            //quantities
            double quantityDay = 0;
            double quantityNight = 0;

            //if starting time is equal to 8:00, after 8:00 and before 17:00
            if (startParsed.equals(startingTime) || startParsed.after(startingTime) && startParsed.before(endingTime)) {
                //if ending time is before 17:00, equal to 17:00 and after 8:00
                if ((endParsed.before(endingTime) || endParsed.equals(endingTime)) && endParsed.after(startingTime)) {
                    quantityDay = totalTime;
                }
                // if ending time is after 17:00 or before 8:00
                else {
                    quantityDay = endingTime.getTime() - startParsed.getTime();
                    quantityNight = endParsed.getTime() - endingTime.getTime();
                }
            }

            //if starting time is after 17:00 or equal to 17:00
            else if (startParsed.after(endingTime) || startParsed.equals(endingTime)) {
                //if ending time is after 17:00 or before 8:00 or equal to 8:00
                if (endParsed.after(endingTime) || endParsed.before(startingTime) || endParsed.equals(startingTime)) {
                    quantityNight = endParsed.getTime() - startParsed.getTime();
                }
                //if ending time is before 17:00 or after 8:00
                else {
                    quantityDay = endParsed.getTime() - startingTime.getTime();
                    quantityNight = startingTime.getTime() - startParsed.getTime();
                }

            }

            //if starting time is before 8:00
            else if (startParsed.before(startingTime)) {
                //if ending time is before 8:00 or equal to 8:00 and not 0:00
                if ((endParsed.before(startingTime) || endParsed.equals(startingTime)) && endParsed.getTime() / (3600 * 1000) != 8) {
                    quantityNight = endParsed.getTime() - startParsed.getTime();
                }
                //if ending time is 0:00 or after 8:00
                else {
                    //if ending time is after 17:00 or equal to 17:00 or 0:00
                    if (endParsed.after(endingTime) || endParsed.equals(endingTime) || endParsed.getTime() / (3600 * 1000) == 8) {
                        quantityDay = 9;
                        quantityNight = (startingTime.getTime() - startParsed.getTime()) + (endParsed.getTime() - endingTime.getTime());
                    }
                    //if ending time is before 17:00
                    else {
                        quantityDay = ((endParsed.getTime() - startingTime.getTime()));
                        quantityNight = ((startingTime.getTime() - startParsed.getTime()));
                    }
                }
            }

            //convert milliseconds to hours
            if (quantityDay != 9) {
                quantityDay /= (3600 * 1000);
            }

            quantityNight /= (3600 * 1000);

            //subtracting morning times to night times will give negative numbers, adding 24 hours gives appropriate time difference
            if (quantityDay < 0) {
                quantityDay += 24;
            }
            if (quantityNight < 0) {
                quantityNight += 24;
            }

            //determine day service rate
            Date parsed = sdf.parse(date);
            String whichDay = day.format(parsed);

            if (!whichDay.equals("Saturday") && !whichDay.equals("Sunday")) {
                serviceRateDay = weekdayDay;
                serviceRateNight = weekdayNight;
            } else if (whichDay.equals("Saturday")) {
                serviceRateDay = SaturdayDay;
                serviceRateNight = SaturdayNight;
            } else if (whichDay.equals("Sunday")) {
                serviceRateDay = SundayDay;
                serviceRateNight = SundayNight;
            }

            //time AM and PM
            String timeStart = timeAmPm.format(startParsed);
            String timeEnd = timeAmPm.format(endParsed);


            //minimum 2 hour application
            if (quantityDay <= 1 && quantityNight == 0) {
                minimumTwoHours = "Yes";
            } else {
                minimumTwoHours = "No";
            }

            //calculate total charges
            double totalDay = (quantityDay * serviceRateDay);
            double totalNight = (quantityNight * serviceRateNight);
            double total = totalDay + totalNight;
            if (minimumTwoHours.equals("Yes")) {
                if (quantityDay != 0) {
                    total = (quantityDay * serviceRateDay) + serviceRateDay;
                } else {
                    total = serviceRateDay * 2;
                }
            }


            //print everything out
            System.out.println();
            System.out.println("Date Printed: " + sdf.format(now));
            System.out.println("Plumber: " + name);
            System.out.println();
            System.out.println("Service date: " + whichDay + ", " + date);
            System.out.println("Service hours: " + timeStart + " - " + timeEnd);
            System.out.println("\nCharges:");
            System.out.printf("%-10s %-10s %-10s %-10s", "Type", "Rate", "Qty", "Amount");
            System.out.println();
            System.out.printf("%-10s %-10s %-10s %-10s", "Day", currency.format(serviceRateDay), df.format(quantityDay), currency.format(totalDay));
            System.out.println();
            System.out.printf("%-10s %-10s %-10s %-10s", "Evening", currency.format(serviceRateNight), df.format(quantityNight), currency.format(totalNight));
            System.out.println("\n");
            System.out.println("Minimum 2 hours applies: " + minimumTwoHours);
            System.out.println();
            System.out.println("\t\t\tTotal Charges: " + currency.format(total));

            //repeat process?
            System.out.println();
            System.out.print("Another invoice? (Y/N) > ");
            choice = scanner.nextLine();

            //make sure it's yes or no
            while (!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("N")) {
                System.out.print("Please try again (Y/N) > ");
                choice = scanner.nextLine();
            }

            //thank you
            if (choice.equalsIgnoreCase("n")) {System.out.println();
                System.out.println("\t\t\tThank you for your business.");}
            else {System.out.println("\n");}
        }
        //test

    }
}