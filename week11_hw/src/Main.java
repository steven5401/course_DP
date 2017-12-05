import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.List;
import java.util.LinkedList;

import java.util.Map;
import java.util.HashMap;

import java.util.Arrays;

interface Element {
    public String getName();
}

class ScrollBar implements Element {
    public String getName() {
        return "scrollBar";
    }
}

class ThickBlackBroder implements Element {
    public String getName() {
        return "thickBlackBorder";
    }
}

interface FileFormat {
    public void addElement(Element ele);
    public void display();
}

class TextView implements FileFormat {
    String name;
    String text;
    List<Element> elementList;
    public TextView(String name, String text) {
        this.name = name;
        this.text = text;
        this.elementList = new LinkedList<Element>();
    }
    public void addElement(Element ele) {
        elementList.add(ele);
    }
    public void display() {
        System.out.print(text);
        for(Element ele: elementList) {
            System.out.format(" %s", ele.getName());
        }
        System.out.println();
    }
}

class Window {
    FileFormat fileFormat;
    public Window(FileFormat fileFormat) {
        this.fileFormat = fileFormat;
    }
    public void display() {
        fileFormat.display();
    };
}

public class Main {
	public static void main(String args[]) {
		try {
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            String currentLine;
            Map<String, Window> windowMap = new HashMap<String, Window>();
            while ((currentLine = reader.readLine()) != null) {
                String[] s = currentLine.split("\\s+");
                String textViewName = s[0];
                if (s[1].equals("add")) {
                    FileFormat fileFormat = windowMap.get(textViewName).fileFormat;
                    String[] elementNameList = Arrays.copyOfRange(s, 2, s.length);
                    for(String elementName: elementNameList) {
                        if (elementName.equals("scrollBar")) {
                            fileFormat.addElement(new ScrollBar());
                        } else if (elementName.equals("thickBlackBorder")) {
                            fileFormat.addElement(new ThickBlackBroder());
                        } else {
                            assert false: "IMPOSSIBLE!" ;
                        }
                    }
                } else if (s[1].equals("display")) {
                    windowMap.get(textViewName).display();
                } else {
                    String text = s[1];
                    FileFormat fileFormat = new TextView(textViewName, text);
                    Window window = new Window(fileFormat);
                    windowMap.put(textViewName, window);
                }
            }
        } catch(IOException e) {
		    e.printStackTrace();
		}
    }
}
