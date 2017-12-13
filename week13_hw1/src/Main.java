import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

class Document {
    String token;
    public Document(String str){
	    this.token = str;
    }
}

interface Format {
  	void show(Document document);
}

class Tex implements Format {
    public void show(Document document) {
        /*
        convert 'C' to 'c'
        convert 'F' to '_'
        convert 'P' to '|'
        */
        String currString = document.token;
        currString = currString.replace("C", "c");
        currString = currString.replace("F", "_");
        currString = currString.replace("P", "|");
        System.out.println(currString);
    }
}

class TextWidget implements Format {
    public void show(Document document) {
        /*
        convert 'C' to '<Char>'
        convert 'F' to '<Font>'
        convert 'P' to '<Paragraph>'
        */
        String currString = document.token;
        currString = currString.replace("C", "<Char>");
        currString = currString.replace("F", "<Font>");
        currString = currString.replace("P", "<Paragraph>");
        System.out.println(currString);
    }
}

class Reader {
    Document document;
    Format   format;
    //Reader(Document document, Format format) {
    //    this.document = document;
    //    this.format   = format;
    //}
    void setFormat(Format format){
	this.format = format;
    	return;
    }
    void setDocument(Document doc){
	this.document = doc;
    	return;
    }
    void convert() {
        format.show(document);
    }
}

public class Main {
    public static void main(String args[]){
        try{
                BufferedReader reader = new BufferedReader(new FileReader(args[0]));
                String currentLine;

                Reader myReader = new Reader();

                while( (currentLine  = reader.readLine()) != null) {
                    String[] s = currentLine.split("\\s+");

                    if( s[0].equals("TeX") )			myReader.setFormat( new Tex() );
                    else if( s[0].equals("TextWidget") )	myReader.setFormat( new TextWidget() );
                    else {
			//case: read in TRF token
		    	myReader.setDocument( new Document(s[0]) );
			myReader.convert();
		    }
                }
        }
        catch(IOException e){
                e.printStackTrace();
        }
        return;
    }
}
