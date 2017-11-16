import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

enum Status {
    cancel,
    pickup,
    reservation
}

class Time {
    int date;
    int hour;
    public Time() {
        date = 1;
        hour = 0;
    }
    public boolean equals(Time t) {
        return this.date == t.date && this.hour == t.hour;
    }
    public boolean smallerThan(Time t, int h) {
        return this.date == t.date && (this.hour - t.hour <= h);
    }
}

class Customer {
    String name = "gg";
    String phone;
    public Customer(String n, String p) {
        name = n;
        phone = p;
    }
    public boolean euqals(Customer c) {
        return this.name.equals(c.name) && this.phone.equals(c.phone);
    }
    public boolean equals(String n, String p) {
        return this.name.equals(n) && this.phone.equals(p);
    }
}

class Reservation {
    Customer customer;
    int reservationNum;
    int numAttendees;
    Status status;
    public Reservation(Customer c, int r, int n, Status s) {
        customer = c;
        reservationNum = r;
        numAttendees = n;
        status = s;
    }
}

class Performance {
    Time time;
    int remainSeats;
    LinkedList<Reservation> reservationList;
    public Performance(Time t, int r) {
        this.time = t;
        this.remainSeats = r;
        this.reservationList = new LinkedList<Reservation>();
    }
}

class BoxStaff {
    public void pickup(LinkedList<Performance> performanceList, int theaterReservationNum, int reservationNum, String name, String phone) {
        for (Performance p : performanceList) {
            for (Reservation r : p.reservationList) {
                if (r.reservationNum == reservationNum) {
                    if (r.customer.equals(name, phone)) {
                        if (r.status == Status.cancel) {
                            System.out.println("Pickup fail. Reservation already cancelled.");
                        } else if (r.status == Status.pickup) {
                            System.out.println("Pickup fail. Reservation already picked up.");
                        } else {
                            r.status = Status.pickup;
                            System.out.println("Pickup #" + reservationNum + ". name: " + name + ", tickets: " + r.numAttendees);
                        }
                    } else {
                        System.out.println("Pickup fail. Identification check fail.");
                    }
                    return;
                }
            }
        }
        System.out.println("Pickup fail. No such reservation.");
    }
    public void clear(Performance performance) {
        for (Reservation r : performance.reservationList) {
            if (r.status == Status.reservation || r.status == Status.cancel) {
                System.out.println("Clear #" + r.reservationNum + ". name: " + r.customer.name + ", tickets: " + r.numAttendees);
            }
        }
        performance.reservationList = null;
    }
}

class CustomerStaff {
    public void cancel(LinkedList<Performance> performanceList, int theaterReservationNum, int reservationNum, String name, String phone) {
        for (Performance p : performanceList) {
            for (Reservation r : p.reservationList) {
                if (r.reservationNum == reservationNum) {
                    if (r.customer.equals(name, phone)) {
                        if (r.status == Status.cancel) {
                            System.out.println("Cancel fail. Reservation already cancelled.");
                        } else if (r.status == Status.pickup) {
                            System.out.println("Cancel fail. Reservation already picked up.");
                        } else {
                            r.status = Status.cancel;
                            System.out.println("Cancel #" + reservationNum + ". name: " + name + ", tickets: " +r.numAttendees);
                        }
                    } else {
                        System.out.println("Cancel fail. Identification check fail.");
                    }
                    return;
                }
            }
        }
        System.out.println("Cancel fail. No such reservation.");
    }
    public int book(LinkedList<Performance> performanceList, int reservationNum, int performanceDate, String name, String phone, int numAttendees) {
        for (Performance p : performanceList) {
            if (p.time.date == performanceDate) {//only book existing performance
                if (p.remainSeats >= numAttendees) {
                    p.remainSeats -= numAttendees;
                    System.out.println("Reserve #" + reservationNum + ". name: " + name + ", tickets: " + numAttendees);
                    p.reservationList.add(new Reservation(new Customer(name, phone), reservationNum, numAttendees, Status.reservation));
                    return reservationNum + 1;
                } else {//fail
                    System.out.println("Book fail. Not enough seats in requested performance.");
                    return reservationNum;
                }
            }
        }
        return reservationNum;
    }
    public void cancelAll(Performance performance) {
        for (Reservation r : performance.reservationList) {
            if (r.status == Status.reservation) {
                r.status = Status.cancel;
                System.out.println("Cancel #" + r.reservationNum + ". name: " + r.customer.name + ", tickets: " + r.numAttendees);
            }
        }
    }
}

class Theater {
    LinkedList<Performance> performanceList;
    BoxStaff boxStaff;
    CustomerStaff customerStaff;
    int reservationNum = 1;
    public Theater() {
        performanceList = new LinkedList<Performance>();
        boxStaff = new BoxStaff();
        customerStaff = new CustomerStaff();
    }
    public void updateTime(Time t) {
        for (Performance p : performanceList) {
            if (p.time.smallerThan(t, 2)) {
                customerStaff.cancelAll(p);
            }
            if (p.time.equals(t)) {
                boxStaff.clear(p);
                performanceList.remove(p);
            }
        }
    }
    public void book(int performanceDate, String name, String phone, int numAttendees) {
        reservationNum = customerStaff.book(this.performanceList, this.reservationNum, performanceDate, name, phone, numAttendees);
    }
    public void cancel(int reservationNum, String name, String phone) {
        customerStaff.cancel(this.performanceList, this.reservationNum, reservationNum, name, phone);
    }
    public void pickup(int reservationNum, String name, String phone) {
        boxStaff.pickup(this.performanceList, this.reservationNum, reservationNum, name, phone);
    }
}
public class Main {
	public static void main(String args[]){
    try{
        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        String currentLine;
        Theater theater = new Theater();
        while((currentLine = reader.readLine()) != null) {
            String[] s = currentLine.split("\\s+");
            Time t = new Time();
            if (s[0].equals("performance")) {
                t.date = Integer.parseInt(s[1]);
                t.hour = Integer.parseInt(s[2]);
            } else {
                t.date = Integer.parseInt(s[1].split(",")[0]);
                t.hour = Integer.parseInt(s[1].split(",")[1]);
            }
            theater.updateTime(t);
            if (s[0].equals("performance")) {
                theater.performanceList.add(new Performance(t, Integer.parseInt(s[3])));
            }
            if (s[0].equals("book")) {
                theater.book(Integer.parseInt(s[2]), s[3], s[4], Integer.parseInt(s[5]));
            }
            if (s[0].equals("cancel")) {
                theater.cancel(Integer.parseInt(s[2]), s[3], s[4]);
            }
            if (s[0].equals("pickup")) {
                theater.pickup(Integer.parseInt(s[2]), s[3], s[4]);
            }
            if (s[0].equals("time")) {
                //do nothing
            }
        }
    } catch(IOException e){
			e.printStackTrace();
		}
  }
}



