import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;


interface Component {
  	public String getName();
}

class ThickCrustDough implements Component {
    public String getName() {
        return "ThickCrustDough";
    }
}

class ThinCrustDough implements Component {
    public String getName() {
        return "ThinCrustDough";
    }
}

class MarinaraSauce implements Component {
    public String getName() {
        return "MarinaraSauce";
    }
}

class PlumTomatoSauce implements Component {
    public String getName() {
        return "PlumTomatoSauce";
    }
}

abstract class Flavor {
  	Style myStyle;
    public Flavor(Style style){
       this.myStyle = style;
    }
	abstract String getFlavor();
    public void show() {
    	System.out.println("Prepare " + this.getFlavor() + " Pizza with " + this.myStyle.show());
    }
}

class Cheese extends Flavor {
  
    public Cheese(Style style){
        super(style);
    }
  
    public String getFlavor(){
        return "Cheese";
    }
}
    
class Pepperoni extends Flavor {
  
    public Pepperoni(Style style){
        super(style);
        //this.myStyle = style;
    }
  
    public String getFlavor(){
        return "Pepperoni";
    }
  /*
  public void show(){
    System.out.println( "Prepare " + this.getFlavor() + " Pizza with " + this.myStyle.show());
    return;
  }
  */
}

class FlavorFactory {
    public Flavor createCheese(Style style) {
        return new Cheese(style);
    }
    public Flavor createPepperoni(Style style) {
        return new Pepperoni(style);
    }
}

abstract class Style {
    public List<Component> myComponentList;
    public Style() {
    	myComponentList = new ArrayList<Component>();
    }
  	public String show() {
        return String.format(
            "%s and %s",
            myComponentList.get(0).getName(),
            myComponentList.get(1).getName()
        );
  	}
}

class NY extends Style {
	public NY() {
      	myComponentList.add(new ThinCrustDough());
		myComponentList.add(new MarinaraSauce());
    }
}

class Chicago extends Style {
	public Chicago() {
      	myComponentList.add(new ThickCrustDough());
		myComponentList.add(new PlumTomatoSauce());
    }
}

class StyleFactory {
    public Style createNY() {
      	return new NY();
    }

    public Style createChicago() {
      	return new Chicago();
    }
}

class PizzaStore {

	public void createPizza(String flavor, String style) {

        FlavorFactory ff = new FlavorFactory();
        StyleFactory  sf = new StyleFactory();
        Flavor        pizza = null;
        
        if (ff == null) {
            System.out.println("no");
        }
        if (sf == null) {
            System.out.println("no");
        }

		switch(flavor) {
			case "Cheese":
				switch(style) {
					case "NY":
						pizza = ff.createCheese(sf.createNY());
						break;
					case "Chicago":
						pizza = ff.createCheese(sf.createChicago());
						break;
				}
				break;
			case "Pepperoni":
				switch(style) {
					case "NY":
						pizza = ff.createPepperoni(sf.createNY());
						break;
					case "Chicago":
						pizza = ff.createPepperoni(sf.createChicago());
						break;
				}
				break;
		}
        pizza.show();
	}

}

public class Main {

	public static void main(String args[]) {

		String filename = args[0];
		PizzaStore store = new PizzaStore();

		try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;

			while((line = br.readLine()) != null) {
            	String[] s = line.split("\\s+");
				store.createPizza(s[0], s[1]);
			}
        } catch (IOException e) {
            System.out.println(e);
        }
	}
    
}
