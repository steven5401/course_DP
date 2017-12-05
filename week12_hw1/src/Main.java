import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;


interface Style {
    public void present(List<Widget> widgeList);
}

class PM implements Style {
    public void present(List<Widget> widgeList) {
        for (Widget widget: widgeList) {
            System.out.format("PM%s %s\n", widget.getType(), widget.getName());
        }
    }
}

class Motif implements Style {
    public void present(List<Widget> widgeList) {
        for (Widget widget: widgeList) {
            System.out.format("Motif%s %s\n", widget.getType(), widget.getName());
        }
    }
}


interface Widget {
    public String getName();
    public String getType();
}

class Window implements Widget {
    String name;
    public Window(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public String getType() {
        return "Window";
    }
}

class ScrollBar implements Widget {
    String name;
    public ScrollBar(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public String getType() {
        return "ScrollBar";
    }
}

class Button implements Widget {
    String name;
    public Button(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public String getType() {
        return "Button";
    }
}

class Application{
	List<Widget> windows, scrollBars, buttons;

	Style currentStyle;

	public Application(){
		this.currentStyle = new Motif();
		this.windows = new ArrayList<Widget>();
		this.scrollBars = new ArrayList<Widget>();
		this.buttons = new ArrayList<Widget>();
	}

	void present(){
		this.currentStyle.present(this.windows);
		this.currentStyle.present(this.scrollBars);
		this.currentStyle.present(this.buttons);
		return;
	}
}
			

public class Main {
    public static void main(String args[]){
        try{
                BufferedReader reader = new BufferedReader(new FileReader(args[0]));
                String currentLine;

		Application app = new Application();

                while( (currentLine  = reader.readLine()) != null) {
                    String[] s = currentLine.split("\\s+");

                    if( s[0].equals("ScrollBar") )   		app.scrollBars.add(new ScrollBar(s[1]));
		    else if( s[0].equals("Window") )		app.windows.add(new Window(s[1]));
		    else if( s[0].equals("Button") )		app.buttons.add(new Button(s[1]));
		    else if( s[0].equals("Motif") )		app.currentStyle = new Motif();
		    else if( s[0].equals("PM") )		app.currentStyle = new PM();
		    else if( s[0].equals("Present") )		app.present();
                    else System.out.println("Warning, invalid input!");
                }
        }
        catch(IOException e){
                e.printStackTrace();
        }
        return;
    }
}
