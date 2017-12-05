import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

interface Display {
    public void draw(List<Item> items);
}

class SpreadsheetDisplay implements Display {
    public void draw(List<Item> items) {
        for(Item item:items) {
            System.out.format("%s %d\n", item.name, item.value);
        }
    }
}

class BarChartDisplay implements Display {
    public void draw(List<Item> items) {
        for(Item item:items) {
            for(int i=1; i<=item.value; i++) {
                System.out.print("=");
            }
            System.out.format(" %s\n", item.name);
        }
    }
}

class PieChartDisplay implements Display {
    public void draw(List<Item> items) {
        int sum = 0;
        for(Item item:items) {
            sum = sum + item.value;
        }
        for(Item item:items) {
            System.out.format("%s %.1f%%\n", item.name, 100.0*item.value/sum);
        }
    }
}
  
class Item {
  	public String name;
    public int value;
  	public Item(String name, int value) {
    	this.name = name;
    	this.value = value;
  	}
  	boolean equals(Item i) {
    	return this.name.equals(i.name);
    }
}
  
class SpreadSheetApp {
  	List<Item> itemHolder;
  	List<Display> strategyHolder;
  	public SpreadSheetApp() {
      	itemHolder = new LinkedList<Item>();
      	strategyHolder = new LinkedList<Display>();
  	}
  	public void addStrategy(Display display){
    	strategyHolder.add(display);
    }
    public void addItem(String name, int value) {
        itemHolder.add(new Item(name, value));
    }
    public void change(String chartType, String name, int value) {
        Item temp = new Item(name, value);
        Item currItem = null;
        for (Item i: itemHolder) {
            if (i.equals(temp)) {
              	currItem = i;
            }
        }
        if (currItem == null) {
            itemHolder.add(temp);
        } else {
            currItem.value = temp.value;
        }
        System.out.println(chartType + " change " + name + " " + value + ".");
        for (Display s : strategyHolder) {
          	s.draw(itemHolder);
        }          
    }
}

public class Main {
	public static void main(String args[]) {
        SpreadSheetApp spreadSheetApp = new SpreadSheetApp();
		try {
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            String currentLine;
          	String item = null;
            int value = 0;
            String chartType = null;
            while((currentLine = reader.readLine()) != null) {
            	String[] s = currentLine.split("\\s+");
                switch(s[0]) {
              		case "data":
                  		item = s[1];
                  		value = Integer.parseInt(s[2]);
                  		spreadSheetApp.addItem(item, value);
                  		break;
                  	case "addChart":
                  		chartType = s[1];
                        switch(chartType) {
                          	case "Spreadsheet":
                          		spreadSheetApp.addStrategy(new SpreadsheetDisplay());
                          		break;
                          	case "BarChart":
                          		spreadSheetApp.addStrategy(new BarChartDisplay());
                          		break;
                          	case "PieChart":
                          		spreadSheetApp.addStrategy(new PieChartDisplay());
                          		break;
                        }
                  		break;
                    case "change":
                  		chartType = s[1];
                  		item = s[2];
                  		value = Integer.parseInt(s[3]);
                  		spreadSheetApp.change(chartType, item, value);
                  		break;
                }
            }
        } catch(IOException e) {
		    e.printStackTrace();
		}
    }
}
