import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


abstract class DataStructure {
	public String name;
	abstract void add(String content);
	abstract void size();
	abstract void length();
	abstract void get(int index);
	abstract void getNode(int index);
	abstract void getAll();
}
	
class List2 extends DataStructure {
	public ArrayList<String> stringList;
	public List2(String str){
		this.name = str;
		stringList = new ArrayList<String>();
	}
	public void add(String content){
		stringList.add(content);
	}
	public void size(){
		System.out.println("List do not have method size");
	}
	public void length(){
		System.out.println(stringList.size());
	}
	public void get(int index){
		System.out.println(stringList.get(index));
	}
	public void getNode(int index){
		System.out.println("List do not have method getNode");
	}
	public void getAll(){
		for(String str: stringList){
			System.out.println(str);
		}
	}
}

class SkipList extends DataStructure{
	public ArrayList<SkipNode> skipnodeList;
	public SkipList(String str){
		this.name = str;
		skipnodeList = new ArrayList<SkipNode>();
	}
	public void add(String content){
		SkipNode node = new SkipNode(content);
		skipnodeList.add(node);
	}
	public void size(){
		System.out.println(skipnodeList.size());
	}
	public void length(){
		System.out.println("SkipList can not access length");
	}
	public void get(int index){
		System.out.println("SkipList do not have method get");
	}
	public void getNode(int index){
		System.out.println("SkipNode:" + skipnodeList.get(index).getContent());
	}
	public void getAll(){
		for(SkipNode node: skipnodeList){
			System.out.println("SkipNode:" + node.getContent());
		}
	}
}	

class SkipNode{
	String content;
	public SkipNode(String str){
		this.content = str;
	}
	public String getContent(){
		return content;
	}
}

class Manager {
	Map<String, DataStructure> map;
    
	public Manager(){
		map = new HashMap<String, DataStructure>();
	}
	
    public void create(String name, String structure){
		if(structure.equals("List")){
			List2 myList = new List2("List");
			map.put(name, myList);
		}
		else{
			SkipList mySkipList = new SkipList("SkipList");
			map.put(name, mySkipList);
		}
		return;
    }
	
	public void add(String name, String content){
		DataStructure ds = map.get(name);
		ds.add(content);
		return;
	}
	public void size(String name){
		DataStructure ds = map.get(name);
		ds.size();
		return;
	}
	public void length(String name){
		DataStructure ds = map.get(name);
		ds.length();
		return;
	}
	public void get(String name, int index){
		DataStructure ds = map.get(name);
		ds.get(index);
		return;
	}
	public void getNode(String name, int index){
		DataStructure ds = map.get(name);
		ds.getNode(index);
		return;
	}
	public void getAll(String name){
		DataStructure ds = map.get(name);
		ds.getAll();
		return;
	}
  
}

public class Main {
	public static void main(String args[]){
		try{
			BufferedReader reader = new BufferedReader(new FileReader(args[0]));
			String currentLine;
			
			Manager myManager = new Manager();
			while((currentLine = reader.readLine()) != null){
				String[] s = currentLine.split("\\s+");
				if(s[0].equals("Create")){
					myManager.create(s[1], s[2]);
				}
				else if(s[0].equals("Add")){
					myManager.add(s[1], s[2]);
				}
				else if(s[0].equals("Length")){
					myManager.length(s[1]);
				}
				else if(s[0].equals("Size")){
					myManager.size(s[1]);
				}
				else if(s[0].equals("Get")){
					myManager.get(s[1], Integer.parseInt(s[2]));
				}
				else if(s[0].equals("GetNode")){
					myManager.getNode(s[1], Integer.parseInt(s[2]));
				}
				else if(s[0].equals("PrintOutList")){
					myManager.getAll(s[1]);
				}
				else{					
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		return;
    }
}
