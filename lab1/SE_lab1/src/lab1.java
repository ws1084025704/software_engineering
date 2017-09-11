import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;
import java.io.*;

/**
 * 
 */

/**
 * @author wangyong
 *
 */
public class lab1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		writeFile1();
//		Scanner scan=new Scanner(System.in);
//		System.out.println("Hello World");
//		while(scan.hasNext())
//		{
//			String str;
//			str=scan.next();
//			System.out.println(str);
//		}
//		scan.close();
	}
	public static void writeFile1()				
	{
		String fileName="input.txt";
		try {
			FileWriter writer=new FileWriter(fileName);
			writer.write("I love you wo di xiao xiang ying");
			writer.write("Such a beautiful@@ chen xiang ying!!!");
			writer.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public static void writeFile2()			//针对大量输入
	{
		String fileName="input2.txt";
		try{
			BufferedWriter writer=new BufferedWriter(new FileWriter(fileName));
			writer.write("I love you wo di xiao xiang ying");
			writer.newLine();
			writer.write("Such a beautiful@@ chen xiang ying!!!");
			writer.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	public static void readFile1()
	{
		String fileName="input.txt";
		try{
			FileReader reader=new FileReader(fileName);
			int c=0;
			c=reader.read();
			while(c!=-1)
			{
				System.out.println((char)c);
				c=reader.read();
			}
			reader.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void readFile2()
	{
		String fileName="input.txt";
		try {
			BufferedReader in=new BufferedReader(new FileReader(fileName));
			String line=in.readLine();
			while(line!=null)
			{
				System.out.println(line);
				line=in.readLine();
			}
			in.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
class AdjEdge{
	int vex;
	int weight;
	AdjEdge(int vex,int weight)
	{
		this.vex=vex;
		this.weight=weight;
	}
}
class Vex{
	Vector<AdjEdge> adjEdges;
	Vex()
	{
		adjEdges=new Vector<AdjEdge>();
	}
	public void addEdge(int vex,int weight)
	{
		adjEdges.addElement(new AdjEdge(vex,weight));
	}
}
class Graph{
	Vector<Vex> vexs;
	Map<String,Integer> IndexMap;
	Map<Integer,String> StringMap;
	Graph(){
		vexs=new Vector<Vex>();
		IndexMap=new HashMap<String,Integer>();
		StringMap=new HashMap<Integer,String>();
	}
	int n,m;	//顶点个数n,边的条数m
	
}

