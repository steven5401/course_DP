import java.io.*;
import java.util.*;

class Boiler {
	private Chocolate chocolate = null;
	private static Boiler boiler;
	private BoilerState state;

	private Boiler(){
		state = BoilerState.empty;
	}

	public static Boiler getBoiler() {
		if(boiler == null) {
			boiler = new Boiler();
		}
		return boiler;
	}

	public void fill(Chocolate choco) {
		chocolate = choco;
		if(this.state == BoilerState.empty) {
			System.out.println("Fill chocolate");
			state = BoilerState.filled;
		}
	}

	public void boil() {
		if(this.state == BoilerState.filled) {
			System.out.println("Boil chocolate");
			state = BoilerState.boiled;
			chocolate.isBoiled = true;
		}
	}

	public void drain() {
		if(this.state == BoilerState.boiled) {
			System.out.println("Drain the boiled chocolate");
			state = BoilerState.empty;
		}
	}
}

enum BoilerState {
	empty,
	filled,
	boiled
}

class Chocolate {
	boolean isBoiled = false;
}

public class Main {
	public static void main(String args[]) {

		String filename = args[0];
		Boiler boiler = null;

		try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;

            while ((line=br.readLine()) != null) {
				if(line.startsWith("Fill")) {
					boiler = boiler.getBoiler();
					boiler.fill(new Chocolate());
                }
                else if(line.startsWith("Boil")){
					boiler = boiler.getBoiler();
					boiler.boil();
                }
				else if(line.startsWith("Drain")){
					boiler = boiler.getBoiler();
					boiler.drain();
				}
				else {

				}
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }
	}
}
