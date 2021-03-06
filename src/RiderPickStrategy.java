import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Scanner;

public class RiderPickStrategy extends ObserverRideScheme implements RideScheduleScheme {

	public RiderPickStrategy() throws ParseException {
		super();
		// TODO Auto-generated constructor stub
	}

	SimpleDateFormat format = new SimpleDateFormat("hh:mm");
	private Scanner sc = new Scanner(System.in);

	@Override
	public void pickUserFromHome(int date, User choosingUser) throws ParseException {
		
		if (choosingUser.getListRideHome().containsKey(date-1)) {
			System.out.println("You already have a driver at this day");
			
		} else {
			for(Map.Entry<String, User> entry : SystemCaller.driverTable.entrySet())
			{
				User theUser = entry.getValue();
				if(theUser.getMemberSchedule().get(date-1).isAvailableHome())
				{
					if(entry.getValue().getRegion() - choosingUser.getRegion() >= 0)
					{
						if(choosingUser.getMemberSchedule().get(date-1).getHomeTime().equals(entry.getValue().getMemberSchedule().get(date-1).getHomeTime()))
						{
							System.out.println("You may carpool with: " + entry.getValue().getName() + "  Username: " + entry.getValue().getUsername() +
									" at " + format.format(choosingUser.getMemberSchedule().get(date-1).getHomeTime()));
						}
					}
				}
			}
			System.out.print(" Enter the username of driver you want to carpool: ");
			String usernameChoice = sc.nextLine();
			Driver driver = (Driver) SystemCaller.driverTable.get(usernameChoice);
			// Check available
			if((driver != null) && (driver.getMemberSchedule().get(date-1).isAvailableHome()))
			{
				if (choosingUser.viewCash() <= 0 || choosingUser.viewCredit() <= 0) {
					System.out.println("Not enough cash or credit...");
				} else {				

					choosingUser.addRideFromHome(date-1,driver); // Add driver to pickup THIS rider
					driver.reserveOneSeatHome(date-1,choosingUser); // reserve one seat from Home

					System.out.println("Done, ride with: " + driver.getName());
					notifyObserver("Calling observer pattern - Rider: " + choosingUser.getName() + " will ride with " + driver.getName());
				}
			} 
			else
			{
				System.out.println("This driver isn't available.");
			}
		}

	}

	@Override
	public void pickUserFromSchool(int date, User choosingUser) throws ParseException {
		
		if (choosingUser.getListRideSchool().containsKey(date-1)) {
			System.out.println("You already have a driver at this day");
			
		} else {
			 			
			for(Map.Entry<String, User> entry : SystemCaller.driverTable.entrySet())
			{
				User theUser = entry.getValue();
				if(theUser.getMemberSchedule().get(date-1).isAvailableSchool())
				{
					if(entry.getValue().getRegion() - choosingUser.getRegion()  >= 0)
					{
						if(choosingUser.getMemberSchedule().get(date-1).getSchoolTime().equals(entry.getValue().getMemberSchedule().get(date-1).getSchoolTime()))
						{
							System.out.println("You may carpool with: " + entry.getValue().getName() + "  Username: " + entry.getValue().getUsername() +
									" at " + format.format(choosingUser.getMemberSchedule().get(date-1).getSchoolTime()));
						}
					}
				}
			}
			System.out.print(" Enter the username of driver you want to carpool: ");
			String usernameChoice = sc.nextLine();
			Driver driver = (Driver) SystemCaller.driverTable.get(usernameChoice);

			// Check driver available
			if(((driver != null) && (driver.getMemberSchedule().get(date-1).isAvailableSchool())))
			{
				if (choosingUser.viewCash() <= 0 || choosingUser.viewCredit() <= 0) {
					System.out.println("Not enough cash or credit...");
				} else {				

					choosingUser.addRideFromSchool(date-1,driver); // Add driver to pickup THIS rider.
					driver.reserveOneSeatSchool(date-1,choosingUser); // reserve one seat in driver

					System.out.println("Done, ride with: " + driver.getName());
					notifyObserver("Calling observer pattern - Rider: " + choosingUser.getName() + " will ride with " + driver.getName());
				}
			} 
			else
			{
				System.out.println("This driver isn't available.");
			}
		}
		
	
		
	}
}