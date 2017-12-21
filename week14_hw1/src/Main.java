import java.io.*;
import java.util.*;

class VacationPlanner {
	private VacationBuilder builder;

	public void setBuilder(VacationBuilder builder) {
		this.builder = builder;
	}

	public VacationBuilder getBuilder() {
		return builder;
	}

	public Vacation constructPlan(Map<String, String> plan) {
		if(plan.containsKey("Day")) {
			builder.chooseDay(plan.get("Day"));
		}
		if(plan.containsKey("Hotel")) {
			builder.chooseHotel(plan.get("Hotel"));
		}
		if(plan.containsKey("Reservation")) {
			builder.chooseReservation(plan.get("Reservation"));
		}
		if(plan.containsKey("SpecialEvent")) {
			builder.chooseSpecialEvent(plan.get("SpecialEvent"));
		}
		if(plan.containsKey("Tickets")) {
			builder.chooseTickets(plan.get("Tickets"));
		}
		return builder.getVacation();
	}
}

interface VacationBuilder {
	public void chooseDay(String day);
	public void chooseHotel(String hotel);
	public void chooseReservation(String reservation);
	public void chooseSpecialEvent(String event);
	public void chooseTickets(String tickets);
	public Vacation getVacation();
}

class NormalVacationBuilder implements VacationBuilder {
	private NormalVacation vacation = new NormalVacation();

	public void chooseDay(String day) {
		vacation.setDay(day);
	}
	public void chooseHotel(String hotel) {
		vacation.setHotel(hotel);
	}
	public void chooseReservation(String reservation) {
		vacation.setReservation(reservation);
	}
	public void chooseSpecialEvent(String event) {
		vacation.setEvent(event);
	}
	public void chooseTickets(String tickets) {
		vacation.setTickets(tickets);
	}
	public Vacation getVacation() {
		return vacation;
	}
}

class BackpackingVacationBuilder implements VacationBuilder {
	private BackpackingVacation vacation = new BackpackingVacation();

	public void chooseDay(String day) {
		vacation.setDay(day);
	}
	public void chooseHotel(String hotel) {
		vacation.setHotel(hotel);
	}
	public void chooseReservation(String reservation) {
		vacation.setReservation(reservation);
	}
	public void chooseSpecialEvent(String event) {
		vacation.setEvent(event);
	}
	public void chooseTickets(String tickets) {
		vacation.setTickets(tickets);
	}
	public Vacation getVacation() {
		return vacation;
	}
}

class Vacation {
	private String type = "";
	private String day = "";
	private String hotel = "";
	private String reservation = "";
	private String specialEvent = "";
	private String tickets = "";

	public Vacation(String s) {
		type = s;
		day = "";
		hotel = "";
		reservation = "";
		specialEvent = "";
		tickets = "";
	}

	public void setDay(String d) {
		day = d;
	}

	public void setHotel(String h) {
		hotel = h;
	}

	public void setReservation(String r) {
		reservation = r;
	}

	public void setEvent(String e) {
		specialEvent = e;
	}

	public void setTickets(String t) {
		tickets = t;
	}

	public String getContent() {
		String output = "";
		output = type + "\nDay:" + day + "\nHotel:" + hotel + "\nReservation:" + reservation + "\nSpecialEvent:" + specialEvent + "\nTickets:" + tickets;
		return output;
	}
}

class NormalVacation extends Vacation {
	public NormalVacation() {
		super("NormalVacation");
	}
}

class BackpackingVacation extends Vacation {
	public BackpackingVacation() {
		super("BackpackingVacation");
	}
}

public class Main {
	public static void main(String args[]) {

		String filename = args[0];
		VacationPlanner planner = new VacationPlanner();
		HashMap<String,String> plan = new HashMap<>();

		try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;

            while ((line=br.readLine()) != null) {
				if(line.startsWith("Normal")) {
					if(planner.getBuilder() != null) {
						System.out.println(planner.constructPlan(plan).getContent());
					}
                    planner.setBuilder(new NormalVacationBuilder());
					plan = new HashMap<>();
                }
                else if(line.startsWith("Backpacking")){
					if(planner.getBuilder() != null) {
						System.out.println(planner.constructPlan(plan).getContent());
					}
					planner.setBuilder(new BackpackingVacationBuilder());
					plan = new HashMap<>();
                }
				else {
					String[] s = line.split("\\s+");
					plan.put(s[0], s[1]);
				}
            }
			if(planner.getBuilder() != null) {
				System.out.println(planner.constructPlan(plan).getContent());
			}
        }
        catch (IOException e) {
            System.out.println(e);
        }
	}
}
