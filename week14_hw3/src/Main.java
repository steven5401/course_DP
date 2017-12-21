import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.lang.String;

interface Menu{
	void show();
	void add(String str);
}

class BreakfastMenu implements Menu{
	ArrayList myList = new ArrayList();
	public void show(){
		if(myList.size() > 0) System.out.println("PancakeHouseMenu:");
		for(int i=0; i<myList.size(); i++){
			System.out.println("MenuItem:" + myList.get(i));
		}
		return;
	}
	public void add(String str){
		this.myList.add( str );
		return;
	}
}
class LunchMenu implements Menu{
	int currentIndex = 0;
	String[] myArray = new String[10];
       	ArrayList<Menu> dessertMenuList = new ArrayList<Menu>();

	public void show(){
		if(currentIndex > 0) System.out.println("DinerMenu:");
		for(int i=0; i<currentIndex; i++){
			System.out.println("MenuItem:" + myArray[i]);
		}
		if(currentIndex > 0) {	//submenu should not appear if lunchmenu does not exist
			for(int i=0; i<dessertMenuList.size(); i++){
				dessertMenuList.get(i).show();
			}
		}

		return;
	}
	public void add(String str){
		if(currentIndex == myArray.length)
			myArray = extendArray( myArray );
		myArray[currentIndex] = str;
		currentIndex += 1;
		return;
	}

	private String[] extendArray( String[] origin ){
		String[] tmp = new String[10 + origin.length];
		for(int i=0; i<origin.length; i++){
			tmp[i] = origin[i];
		}
		return tmp;
	}

	
}
class SubMenu implements Menu{
	ArrayList myList = new ArrayList();
	public void show(){
		if( myList.size() > 0) System.out.println("SubMenu:");
		for(int i=0; i<myList.size(); i++){
			System.out.println("MenuItem:" + myList.get(i));
		}
		return;
	}
	public void add(String str){
		this.myList.add( str );
		return;
	}
}


interface  Factory{
	public void addItem(String str);
}

class BreakfastMenuFactory implements Factory{
	private static BreakfastMenuFactory instance = null;
	private static Menu myMenu = new BreakfastMenu();

	private BreakfastMenuFactory(){}
	public static BreakfastMenuFactory getInstance(){
		if(instance == null) return new BreakfastMenuFactory();
		return instance;
	}
	public static Menu getProduct(){
		return myMenu;
	}	
	public void addItem(String str){
		myMenu.add( str );
		return;
	}
}
class LunchMenuFactory implements Factory{
	private static LunchMenuFactory instance = null;
	private static LunchMenu myMenu = new LunchMenu();

	private LunchMenuFactory(){}
	public static LunchMenuFactory getInstance(){
		if(instance == null) {
			return new LunchMenuFactory();
		}
		return instance;
	}
	public static Menu getProduct(){
		return myMenu;
	}	
	public void addItem(String str){
		myMenu.add( str );
		return;
	}
	public static void setSubMenu(ArrayList<Menu> myList){
		myMenu.dessertMenuList = myList;
		return;
	}
	
}
class SubMenuFactory implements Factory{
	private static SubMenuFactory instance = null;
	private static ArrayList<Menu> myMenuList = new ArrayList<Menu>();
	private static SubMenu tmpSubMenu = null;

	private SubMenuFactory(){}
	public static SubMenuFactory getInstance(){
		if(instance == null) {
			instance = new SubMenuFactory(); 	
			instance.tmpSubMenu = new SubMenu();
		       	return instance;	
		}

		myMenuList.add(tmpSubMenu);
		tmpSubMenu = new SubMenu();
		return instance;
	}
	public static ArrayList<Menu> getProduct(){
		myMenuList.add(tmpSubMenu);
		return myMenuList;
	}	
	public void addItem(String str){
		tmpSubMenu.add( str );
		return;
	}
}

class Watress{
	Menu BM = null;
	Menu LM = null;
	
	void setBreakfastMenu( Menu BM){
		this.BM = BM;
		return;
	}

	void setLunchMenu(Menu LM){
		this.LM = LM;
		return;
	}

	void show(){
		this.BM.show();
		this.LM.show();
	}
}

public class Main {
	public static void main(String args[]){
		try{
			BufferedReader reader = new BufferedReader(new FileReader(args[0]));
			String currentLine;


			Factory factory = null;
			while( (currentLine  = reader.readLine()) != null) {
				String[] s = currentLine.split("\\s+");

				if( s[0].equals("PancakeHouse") ) 	factory = BreakfastMenuFactory.getInstance();
				else if( s[0].equals("Diner") )		factory = LunchMenuFactory.getInstance();
				else if( s[0].equals("SubMenu") )	factory = SubMenuFactory.getInstance();
				else{
					factory.addItem( s[0] );
				}
			}
			
			LunchMenuFactory.setSubMenu( SubMenuFactory.getProduct() );	
			Menu BM = BreakfastMenuFactory.getProduct();
			Menu LM = LunchMenuFactory.getProduct();


			Watress watress = new Watress();
			watress.setBreakfastMenu(BM);
			watress.setLunchMenu(LM);
			watress.show();

		}
		catch(IOException e){
			e.printStackTrace();
		}
		return;
	}
}
