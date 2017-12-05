import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

interface Product {
    public double getPrice();
}

class HouseBlend implements Product {
    private double price = 0.96;
    public double getPrice() {
        return price;
    }
}

class DarkRoast implements Product {
    private double price = 0.97;
    public double getPrice() {
        return price;
    }
}

class Decaf implements Product {
    private double price = 0.98;
    public double getPrice() {
        return price;
    }
}

class Espresso implements Product {
    private double price = 0.99;
    public double getPrice() {
        return price;
    }
}

class Condiment implements Product {
    Product product;
    Condiment(Product p) {
      	product = p;
    }
    public double getPrice(){
        //System.out.println("condiment");
        return product.getPrice();
    }
}

class Milk extends Condiment {
    private double price = 0.2;
    public Milk(Product p) {
        super(p);
    }
    public double addCondiment(){
      	return price;
    }
    public double getPrice(){
        return product.getPrice() + addCondiment();
    }
}

class Soy extends Condiment {
    private double price = 0.1;
    public Soy(Product p) {
        super(p);
    }
    public double addCondiment(){
      	return price;
    }
    public double getPrice(){
        return product.getPrice() + addCondiment();
    }
}

class Mocha extends Condiment {
    private double price = 0.25;
    public Mocha(Product p) {
        super(p);
    }
    public double addCondiment(){
      	return price;
    }
    public double getPrice(){
        return product.getPrice() + addCondiment();
    }
}


public class Main {
    public static void main(String args[]){
    try{
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            String currentLine;

            while( (currentLine  = reader.readLine()) != null) {
                    String[] s = currentLine.split("\\s+");
        
                    Product myCoffee;
                      //determine which coffee here
                    if( s[0].equals("HouseBlend") )   		myCoffee = new HouseBlend();            
                    else if( s[0].equals("DarkRoast") )		myCoffee = new DarkRoast();
                    else if( s[0].equals("Decaf") )			myCoffee = new Decaf();
                    else if( s[0].equals("Espresso") )		myCoffee = new Espresso();
                    else myCoffee = new Espresso();
                    //add condiment here  
                    for(int i=1; i<s.length; i++){
                        if( s[i].equals("Milk") )			myCoffee = new Milk(myCoffee);
                        else if( s[i].equals("Soy") )		myCoffee = new Soy(myCoffee);
                        else if( s[i].equals("Mocha") )		myCoffee = new Mocha(myCoffee);
                    }
              
                      //display total price here
                    System.out.println("price: " + myCoffee.getPrice());
                    
            }
    } 
    catch(IOException e){
            e.printStackTrace();
    }
            return;
    }
}