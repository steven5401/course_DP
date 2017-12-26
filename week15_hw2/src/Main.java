import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

abstract class Node {
    public String nodeType = null;
    public String type = null;
    public String code = null;
    public String content = null;
    public int nodeNum = 0;
    private static int nodeNumCounter = 1;
    public Node(String s1, String s2, String s3, String s4) {
        nodeType = s1;
        type = s2;
        code = s3;
        content = s4;
        nodeNum = nodeNumCounter;
        nodeNumCounter++;
    }
    abstract void checkType();
    abstract void generateCode();
    abstract void printContent();
}
class VariableRefNode extends Node {
    public VariableRefNode(String s1, String s2, String s3, String s4) {
        super(s1, s2, s3, s4);
    }
    @Override
    void checkType() {
        System.out.println("Node " + nodeNum + ": " + nodeType + " " + type);
    }
    @Override
    void generateCode() {
        System.out.println("Node " + nodeNum + ": " + nodeType + " " + code);
    }
    @Override
    void printContent() {
        System.out.println("Node " + nodeNum + ": " + nodeType + " " + content);
    }
}
class AssignmentNode extends Node {
    public AssignmentNode(String s1, String s2, String s3, String s4) {
        super(s1, s2, s3, s4);
    }
    @Override
    void checkType() {
        System.out.println("Node " + nodeNum + ": " + nodeType + " " + type);
    }
    @Override
    void generateCode() {
        System.out.println("Node " + nodeNum + ": " + nodeType + " " + code);
    }
    @Override
    void printContent() {
        System.out.println("Node " + nodeNum + ": " + nodeType + " " + content);
    }
}
interface ASTIter {
    boolean hasNext();
    Node next();
}
class ASTDFSIter implements ASTIter {
    int currentIdx = 1;
    int size = 0; 
    //int[] visit = null;
    AST ast = null;
    boolean _hasNext = true;
    Stack<Integer> s = new Stack<>();
    public ASTDFSIter(AST ast, int i) {
        //visit = new int[ast.nodeList.size()];
        size = ast.nodeList.size();
        this.ast = ast;
        currentIdx = i;
    }
    public Node next() {
        Node n = ast.nodeList.get(currentIdx - 1);
        //visit[currentIdx] = 1;
        if (currentIdx * 2 + 1 <= size) {
            s.push(currentIdx * 2 + 1);
        }
        if (currentIdx * 2 <= size) {
            s.push(currentIdx * 2);
        }
        if (!s.empty()) {
            currentIdx = s.pop().intValue();
        } else {
            _hasNext = false;
        }
        return n;
    }
    public boolean hasNext() {
        return _hasNext && (size != 0);
    }
}
class AST {
    ArrayList<Node> nodeList = null;
    public AST() {
        nodeList = new ArrayList<>();
    }
    void addNode(String s1, String s2, String s3, String s4) {
        Node n;
        if (s1.equals("VariableRefNode")) {
            n = new VariableRefNode(s1, s2, s3, s4);
        } else {
            n = new AssignmentNode(s1, s2, s3, s4);
        }
        nodeList.add(n);
    }
    ASTDFSIter getDFSIter(int i) {
        return new ASTDFSIter(this, i);
    }
}
public class Main {
	public static void main(String args[]){
		try{
			BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            String currentLine;
            AST ast = new AST();
			while( (currentLine  = reader.readLine()) != null) {
				String[] s = currentLine.split("\\s+");
                if (s[0].equals("node")) {
                    ast.addNode(s[1], s[2], s[3], s[4]);
                } else if (s[0].equals("checkType")) {
                    ASTIter it = ast.getDFSIter(Integer.parseInt(s[1]));
                    while (it.hasNext()) {
                        Node n = it.next();
                        n.checkType();
                    }
                } else if (s[0].equals("generateCode")) {
                    ASTIter it = ast.getDFSIter(Integer.parseInt(s[1]));
                    while (it.hasNext()) {
                        Node n = it.next();
                        n.generateCode();
                    }
                } else if (s[0].equals("printContent")) {
                    ASTIter it = ast.getDFSIter(Integer.parseInt(s[1]));
                    while (it.hasNext()) {
                        Node n = it.next();
                        n.printContent();
                    }
                }
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return;
	}
}