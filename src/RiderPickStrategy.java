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
	public void pickUserFromHome(User choosingUser) throws ParseException {
		for(Map.Entry<String, User> entry : SystemCaller.driverTable.entrySet())
		{
			User theUser = entry.getValue();
			if(theUser.getMemberSchedule().isAvailableHome())
			{
				if(entry.getValue().getRegion() - choosingUser.getRegion() >= 0)
				{
					if(choosingUser.getMemberSchedule().getHomeTime().equals(entry.getValue().getMemberSchedule().getHomeTime()))
					{
						System.out.println("You may carpool with: " + entry.getValue().getName() + "  Username: " + entry.getValue().getUsername() +
								" at " + format.format(choosingUser.getMemberSchedule().getHomeTime()));
					}
				}
			}
		}
		System.out.print(" Enter the username of driver you want to carpool with, [0] to exit: ");
		String usernameChoice = sc.nextLine();
		Driver driver = (Driver) SystemCaller.driverTable.get(usernameChoice);
		// Check available
		if((driver != null) && (driver.getMemberSchedule().isAvailableHome()))
		{
			choosingUser.addRideFromHome(driver); // Add driver to pickup THIS rider
			driver.reserveOneSeatHome(choosingUser); // reserve one seat from Home

			System.out.println("Done, ride with: " + driver.getName());
			notifyObserver("Calling observer pattern - Rider: " + choosingUser.getName() + " will ride with " + driver.getName());
		} 
		else
		{
			System.out.println("This driver isn't available.");
		}
		
	}

	@Override
	public void pickUserFromSchool(User choosingUser) throws ParseException {
		for(Map.Entry<String, User> entry : SystemCaller.driverTable.entrySet())
		{
			User theUser = entry.getValue();
			if(theUser.getMemberSchedule().isAvailableSchool())
			{
				if(entry.getValue().getRegion() - choosingUser.getRegion()  >= 0)
				{
					if(choosingUser.getMemberSchedule().getSchoolTime().equals(entry.getValue().getMemberSchedule().getSchoolTime()))
					{
						System.out.println("You may carpool with: " + entry.getValue().getName() + "  Username: " + entry.getValue().getUsername() +
								" at " + format.format(choosingUser.getMemberSchedule().getSchoolTime()));
					}
				}
			}
		}
		System.out.print(" Enter the username of driver you want to carpool with, [0] to exit: ");
		String usernameChoice = sc.nextLine();
		Driver driver = (Driver) SystemCaller.driverTable.get(usernameChoice);

		// Check driver available
		if((driver != null) && (driver.getMemberSchedule().isAvailableSchool()))
		{

			choosingUser.addRideFromSchool(driver); // Add driver to pickup THIS rider.
			driver.reserveOneSeatSchool(choosingUser); // reserve one seat in driver

			System.out.println("Done, ride with: " + driver.getName());
			notifyObserver("Calling observer pattern - Rider: " + choosingUser.getName() + " will ride with " + driver.getName());
		} 
		else
		{
			System.out.println("This driver isn't available.");
		}

		
	}

}