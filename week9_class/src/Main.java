import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

abstract class Duck {
    private Fly flyStrategy;
    private Quackbase quackStrategy;
    public void flySetter(Fly f) {
  	    this.flyStrategy = f;
    }
    public void quackSetter(Quackbase q) {
  	    this.quackStrategy = q;
    }
	public void fly() {
  	    System.out.println(flyStrategy.perform());
    }
	public void quack() {
  	    System.out.println(quackStrategy.perform());
    }
    abstract public void swim();
}

class MallardDuck extends Duck {
	public void swim() {
  	    System.out.println("MallardDuck is swimming");
    }
}

class RedheadDuck extends Duck {
	public void swim() {
  	    System.out.println("RedheadDuck is swimming");
    }
}

class RubberDuck extends Duck {
	public void swim() {
  	    System.out.println("RubberDuck is swimming");
    }
}

class DecoyDuck extends Duck {
	public void swim() {
  	    System.out.println("DecoyDuck is swimming");
    }
}

interface Fly {
	public String perform();
}

class CanFly implements Fly {
	public String perform() {
		return "duck is flying";
	}
}

class CannotFly implements Fly {
	public String perform() {
		return "duck cannot fly";
	}
}

interface Quackbase{
	 public String perform();
}

class Quack implements Quackbase {
	public String perform() {
		return "quack quack quack";
	}
}

class Squeak implements Quackbase {
	public String perform() {
		return "squeak squeak squeak";
	}
}

class Silent implements Quackbase {
	public String perform() {
  	    return "(silent)";
	}
}

public class Main {
	public static void main(String args[]){
    try{
        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        String currentLine;
        
        RubberDuck RDuck = new RubberDuck();
        DecoyDuck DDuck = new DecoyDuck();
        MallardDuck MDuck = new MallardDuck();
        RedheadDuck RHDuck = new RedheadDuck();
        
        while( (currentLine  = reader.readLine()) != null) {
            String[] s = currentLine.split("\\s+");
        
        if( s[0].equals("duck") ) {
            Quackbase quackStrategy = null;
            Fly flyStrategy = null;
            
            if (s[2].equals("CanFly")) {
                flyStrategy = new CanFly();
            } else {
                flyStrategy = new CannotFly();
            }
            
            if( s[3].equals("Quack") )			quackStrategy = new Quack();
            else if( s[3].equals("Squeak") )	quackStrategy = new Squeak();
            else if( s[3].equals("Silent") )	quackStrategy = new Silent();

            if( s[1].equals("MallardDuck") ) {
                MDuck.flySetter(flyStrategy);
                MDuck.quackSetter(quackStrategy);
            }
            if( s[1].equals("DecoyDuck") ) {
                DDuck.flySetter(flyStrategy);
                DDuck.quackSetter(quackStrategy);
            }
            if( s[1].equals("RubberDuck") ) {
                RDuck.flySetter(flyStrategy);
                RDuck.quackSetter(quackStrategy);
            }
            if( s[1].equals("RedheadDuck") ) {
                RHDuck.flySetter(flyStrategy);
                RHDuck.quackSetter(quackStrategy);
            }
        }
        
        if( s[0].equals("changeFly") ){
            Fly flyStrategy = null;
            if (s[2].equals("CanFly")) {
                flyStrategy = new CanFly();
            } else {
                flyStrategy = new CannotFly();
            }
            if( s[1].equals("MallardDuck") )   MDuck.flySetter(flyStrategy);
            if( s[1].equals("DecoyDuck") )	   DDuck.flySetter(flyStrategy);
            if( s[1].equals("RubberDuck") )	   RDuck.flySetter(flyStrategy);
            if( s[1].equals("RedheadDuck") )   RHDuck.flySetter(flyStrategy);
        }
        
        if( s[0].equals("changeQuack") ){
            Quackbase quackStrategy = null;
            if( s[2].equals("Quack") )			quackStrategy = new Quack();
            else if( s[2].equals("Squeak") )	quackStrategy = new Squeak();
            else if( s[2].equals("Silent") )	quackStrategy = new Silent();
        
            if( s[1].equals("MallardDuck") )	MDuck.quackSetter(quackStrategy);
            if( s[1].equals("DecoyDuck") )		DDuck.quackSetter(quackStrategy);
            if( s[1].equals("RubberDuck") )		RDuck.quackSetter(quackStrategy);
            if( s[1].equals("RedheadDuck") )	RHDuck.quackSetter(quackStrategy);
        }
        
        if( s[0].equals("swim") ){
            if( s[1].equals("MallardDuck") )		MDuck.swim();
            if( s[1].equals("DecoyDuck") )			DDuck.swim();
            if( s[1].equals("RubberDuck") )			RDuck.swim();
            if( s[1].equals("RedheadDuck") )		RHDuck.swim();
        }
        
        if( s[0].equals("fly") ){
            if( s[1].equals("MallardDuck") )		MDuck.fly();
            if( s[1].equals("DecoyDuck") )			DDuck.fly();
            if( s[1].equals("RubberDuck") )			RDuck.fly();
            if( s[1].equals("RedheadDuck") )		RHDuck.fly();
        }
        
        if( s[0].equals("quack") ){
            if( s[1].equals("MallardDuck") )		MDuck.quack();
            if( s[1].equals("DecoyDuck") )			DDuck.quack();
            if( s[1].equals("RubberDuck") )			RDuck.quack();
            if( s[1].equals("RedheadDuck") ) 		RHDuck.quack();
        }
        
        }
    } catch(IOException e){
			e.printStackTrace();
		}
  }
}



