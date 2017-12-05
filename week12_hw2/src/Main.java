import java.io.*;
import java.util.*;

class Application {
	private List<Document> documents = new ArrayList<Document>();

	public void present() {
		for(Document d: documents) {
			d.print();
		}
	}

	public void create(String s) {
		if(s.equals("Draw")) {
			documents.add(new DrawingDocument());
		}
		else if(s.equals("Text")) {
			documents.add(new TextDocument());
		}
	}
}

interface Document {
    public void print();
}

class DrawingDocument implements Document {
	public String type = "DrawingDocument";
    public void print() {
        System.out.println(type);
    }
}

class TextDocument implements Document {
	public String type = "TextDocument";
    public void print() {
        System.out.println(type);
    }
}

public class Main {
	public static void main(String args[]) {

		String filename = args[0];
		Application app = new Application();

		try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;

            while ((line=br.readLine()) != null) {
				if(line.startsWith("Draw")) {
					app.create("Draw");
                }
                else if(line.startsWith("Text")){
					app.create("Text");
                }
				else if(line.startsWith("Present")){
					app.present();
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
