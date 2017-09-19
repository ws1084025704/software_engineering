import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Stack;

import javax.swing.plaf.synth.SynthSpinnerUI;

import java.util.Scanner;
import java.util.Set;
import java.io.*;
import java.util.Random;
public class lab1 {

	public static void main(String[] args) {
		lab1 cls = new lab1();
		Graph G=null;
		System.out.println("请输入数字指令进行相关操作:");
		System.out.println("0.查看帮助菜单");
		System.out.println("1.读入文本并生成有向图");
		System.out.println("2.展示有向图");
		System.out.println("3.查询桥接词");
		System.out.println("4.根据bridge word生成新文本");
		System.out.println("5.计算两个单词之间的最短路径");
		System.out.println("6.随机游走");
		System.out.println("7.退出程序");
		int op;
		Scanner scan=new Scanner(System.in);
		op=scan.nextInt();
		while(op!=7)
		{
			switch (op) {
			case 0:
				System.out.println("请输入数字指令进行相关操作:");
				System.out.println("0.查看帮助菜单");
				System.out.println("1.读入文本并生成有向图");
				System.out.println("2.展示有向图");
				System.out.println("3.查询桥接词");
				System.out.println("4.根据bridge word生成新文本");
				System.out.println("5.计算两个单词之间的最短路径");
				System.out.println("6.随机游走");
				System.out.println("7.退出程序");
				break;
			case 1:
				System.out.println("请输入文本文件地址");
				String input=scan.next();
				G=cls.createDirectedGraph(input);
				System.out.println("有向图生成成功");
				break;
			case 2:
				if(G==null)
				{
					System.out.println("请先生成有向图");
					break;
				}
				cls.showDirectedGraph(G);
				System.out.println("成功导出有向图DotGraph.pdf");
				break;
			case 3:
				if(G==null)
				{
					System.out.println("请先生成有向图");
					break;
				}
				System.out.println("请输入单词word1,word2，用空格隔开");
				String word1,word2;
				word1=scan.next();
				word2=scan.next();
				String bridge=cls.queryBridgeWords(G, word1, word2);
				System.out.println(bridge);
				break;
			case 4:
				if(G==null)
				{
					System.out.println("请先生成有向图");
					break;
				}
				System.out.println("请输入一行新文本");
				scan.nextLine();
				String inputText=scan.nextLine();
				System.out.println(cls.generateNewText(G, inputText));
				break;
			case 5:
				if(G==null)
				{
					System.out.println("请先生成有向图");
					break;
				}
				System.out.println("请选择输入命令标号和对应数量单词(以空格分割)");
				System.out.println("1.查询两个单词间的最短路径,2.查询一个单词到其他单词最短路径");
				int opx=scan.nextInt();
				String word3,word4;
				if(opx==1)
				{
					word3=scan.next();
					word4=scan.next();
					System.out.println(cls.calcShortestPath(G, word3, word4));
				}
				else if(opx==2)
				{
					word3=scan.next();
					System.out.println(cls.calcShortestPath(G, word3, ""));
				}
				else
				{
					System.out.println("输入错误，请重新操作");
					break;
				}
				break;
			case 6:
				if(G==null)
				{
					System.out.println("请先生成有向图");
					break;
				}
				System.out.println(cls.randomWalk(G));
				System.out.println("随机游走完毕");
				break;
			default:
				System.out.println("指令错误，请从新输入");
				break;
			}
			try {
				op=scan.nextInt();
			} catch (Exception e) {
				System.out.println("指令操作，请重新输入");
				op=scan.nextInt();
			}
		}
		System.out.println("程序退出完毕");
	}

	public void testQueryBridgeWords(lab1 cls, Graph g) {
		Scanner scan = new Scanner(System.in);
		while (true) {
			String word1, word2;
			word1 = scan.next();
			word2 = scan.next();
			if (word1.equals("*") || word2.equals("*"))
				break;
			System.out.println(cls.queryBridgeWords(g, word1, word2));
		}
		scan.close();
	}

	public Graph createDirectedGraph(String filename) {
		Graph g = new Graph();
		String pre = "", now = "";
		try {
			FileReader in = new FileReader(filename);
			int c = 0;
			c = in.read();
			while (c != -1) {
				if (c >= 'a' && c <= 'z') {
					now += (char) c;

				} else if (c >= 'A' && c <= 'Z') {
					now += (char) (c + 32);
				} else if ((c == ' ' || c == '\t' || c == '\n') && !now.equals("")) // now非空跳过多个空格
				{
					if (!g.vexs.containsKey(now)) // 当前串未出现在图中
					{
						g.vexs.put(now, new Vex());
						g.n++;
					}
					if (!pre.equals("")) {
						Vex v = g.vexs.get(pre);
						if (v.adjEdges.containsKey(now))
							v.adjEdges.put(now, v.adjEdges.get(now) + 1);
						else {
							v.adjEdges.put(now, 1);
							g.m++;
						}
					}
					pre = now;
					now = "";
				}
				c = in.read();
			}
			in.close();
			if (!now.equals("")) // 处理最后的串
			{
				if (!g.vexs.containsKey(now)) // 当前串未出现在图中
				{
					g.vexs.put(now, new Vex());
					g.n++;
				}
				if (!pre.equals(null)) {
					Vex v = g.vexs.get(pre);
					if (v.adjEdges.containsKey(now))
						v.adjEdges.put(now, v.adjEdges.get(now) + 1);
					else {
						v.adjEdges.put(now, 1);
						g.m++;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return g;
	}

	void showDirectedGraph(Graph g) {
		try {
			String dotFormat = "";

			for (Map.Entry<String, Vex> m : g.vexs.entrySet()) {
				Vex v = m.getValue();
				for (Map.Entry<String, Integer> t : v.adjEdges.entrySet()) {
					dotFormat += m.getKey() + "->" + t.getKey() + "[label=\"" + t.getValue() + "\"];";
					// System.out.println(m.getKey() + "->" + t.getKey() + "->"
					// + t.getValue());
				}
			}
			GraphViz.createDotGraph(dotFormat, "DotGraph");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	String queryBridgeWords(Graph G, String word1, String word2) {
		if (!G.vexs.containsKey(word1) || !G.vexs.containsKey(word2))
			return "No word1 or word2 in the graph!";
		Vex v1 = G.vexs.get(word1);
		String words = "", pre = "", now = "";
		for (Map.Entry<String, Integer> m : v1.adjEdges.entrySet()) {
			Vex v3 = G.vexs.get(m.getKey());
			if (v3.adjEdges.containsKey(word2)) {
				pre = now;
				now = m.getKey();
				if (!pre.equals(""))
					words += pre + ", ";
			}
		}
		if (words.equals("") && now.equals(""))
			return "No bridge words from \"" + word1 + "\" to \"" + word2 + "\"!";
		else if (words.equals(""))
			return "The bridge words from \"" + word1 + "\" to \"" + word2 + "\" is: " + now;
		else
			return "The bridge words from \"" + word1 + "\" to \"" + word2 + "\" are: " + words + "and " + now;
	}

	String generateNewText(Graph G, String inputText) {
		String result = "";
		String pre = "", now = "";
		for (int pos = 0; pos < inputText.length(); pos++) {
			char c = inputText.charAt(pos);
			if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))
				now += c;
			else if (c == ' ' || c == '\t') {
				if(pre!="")
				result +=pre+" ";
				if (!pre.equals("") && G.vexs.containsKey(pre.toLowerCase())) {
					Vex v1 = G.vexs.get(pre.toLowerCase());
					for (Map.Entry<String, Integer> m : v1.adjEdges.entrySet()) {
						Vex v3 = G.vexs.get(m.getKey());
						if (v3.adjEdges.containsKey(now)) {
							result += m.getKey()+" ";
							break;
						}
					}
				}
				pre = now;
				now = "";
			}

		}
		if (!now.equals("")) // 处理最后的串
		{
			result +=pre+" ";
			if (!pre.equals("") && G.vexs.containsKey(pre.toLowerCase())) {
				Vex v1 = G.vexs.get(pre.toLowerCase());
				for (Map.Entry<String, Integer> m : v1.adjEdges.entrySet()) {
					Vex v3 = G.vexs.get(m.getKey());
					if (v3.adjEdges.containsKey(now)) {
						result +=m.getKey()+" ";
						break;
					}
				}
			}
		}
		result +=now+" ";
		return result;
	}

	String calcShortestPath(Graph G, String word1, String word2) {
		if (!G.vexs.containsKey(word1))
			return "word1不存在";
		Map<String, String> pre = new HashMap<String, String>(); // 记录前置顶点
		Map<String, Integer> dist = new HashMap<String, Integer>(); // 记录到源点的距离
		Map<String, Boolean> mark = new HashMap<String, Boolean>();
		// 初始化
		Vex v0 = G.vexs.get(word1);
		for (Map.Entry<String, Integer> m : v0.adjEdges.entrySet()) {
			dist.put(m.getKey(), m.getValue());
			pre.put(m.getKey(), word1);
		}
		mark.put(word1, true);
		pre.put(word1, word1);
		for (int i = 1; i < G.vexs.size(); i++) {
			String u = word1;
			int Mindist = Integer.MAX_VALUE;
			for (Map.Entry<String, Integer> m : dist.entrySet()) // 寻找未加入的最短距离点
			{
				if (!mark.containsKey(m.getKey()) && Mindist > dist.get(m.getKey())) {
					u = m.getKey();
					Mindist = dist.get(m.getKey());
				}
			}
			// 将未加入的最短距离点加入
			mark.put(u, true);
			Vex v1 = G.vexs.get(u);
			for (Map.Entry<String, Integer> m : v1.adjEdges.entrySet())
				if (!dist.containsKey(m.getKey()) || dist.get(m.getKey()) > dist.get(u) + m.getValue()) {
					dist.put(m.getKey(), dist.get(u) + m.getValue());
					pre.put(m.getKey(), u);
				}
		}

		if (!word2.equals("")) {

			Stack<String> path = new Stack<String>();
			String p = word2;
			while (!p.equals(word1)) {
				path.push(p);
				p = pre.get(p);
			}
			try {
				String dotFormat;
				if(word2.equals(word1))
					dotFormat ="label=\"distance=0\";";
				else
					dotFormat = "label=\"distance="+dist.get(word2)+"\";";

				for (Map.Entry<String, Vex> m : G.vexs.entrySet()) {
					Vex v = m.getValue();
					for (Map.Entry<String, Integer> t : v.adjEdges.entrySet()) {
						if (path.contains(t.getKey()) && m.getKey().equals(pre.get(t.getKey())))
							dotFormat += m.getKey() + "->" + t.getKey() + "[color=\"red\"," + "label=\"" + t.getValue()
									+ "\"];";
						else
							dotFormat += m.getKey() + "->" + t.getKey() + "[label=\"" + t.getValue() + "\"];";
					}
				}
				GraphViz.createDotGraph(dotFormat, word1+"_"+word2);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			for (Map.Entry<String, Vex> m : G.vexs.entrySet()) {
				Stack<String> path = new Stack<String>();
				String p = m.getKey();
				while (!p.equals(word1)) {
					path.push(p);
					p = pre.get(p);
				}
				try {
					String dotFormat;
					if(word1.equals(m.getKey()))
						dotFormat = "label=\"distance=0\";";
					else
						dotFormat = "label=\"distance="+dist.get(m.getKey())+"\";";
					for (Map.Entry<String, Vex> m1 : G.vexs.entrySet()) {
						Vex v = m1.getValue();
						for (Map.Entry<String, Integer> t : v.adjEdges.entrySet()) {
							if (path.contains(t.getKey()) && m1.getKey().equals(pre.get(t.getKey())))
								dotFormat += m1.getKey() + "->" + t.getKey() + "[color=\"red\"," + "label=\"" + t.getValue()
										+ "\"];";
							else
								dotFormat += m1.getKey() + "->" + t.getKey() + "[label=\"" + t.getValue() + "\"];";
						}
					}
					GraphViz.createDotGraph(dotFormat, word1+"_"+m.getKey());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return "最短路径打印完毕";
	}
	String randomWalk(Graph G)
	{
		Map<Integer,String> index=new HashMap<Integer,String>();
		Set<StringPair> set=new HashSet<StringPair>();
		int i=0;
		for(Map.Entry<String, Vex> m:G.vexs.entrySet())
			index.put(i++,m.getKey());
		int randNum=new Random().nextInt(G.vexs.size());
		String s=index.get(randNum);
		String path="";
//		Scanner scan=new Scanner(System.in);
//		int times=0;
//		System.out.println("请输入遍历字符个数，输入0则全部遍历");
//		times=scan.nextInt();
		
		while(true)
		{
			path+=s+" ";
			Vex v=G.vexs.get(s);
			if(v.adjEdges.isEmpty())
				break;
			Map<Integer,String> vexIndex=new HashMap<Integer,String>();
			int j=0;
			for(Map.Entry<String, Integer> m:v.adjEdges.entrySet())
				vexIndex.put(j++,m.getKey());
			int rand_vex_Num=new Random().nextInt(v.adjEdges.size());
			String temp=vexIndex.get(rand_vex_Num);
			if(set.contains(new StringPair(s,temp)))
			{
				path+=temp;
				break;
			}
			set.add(new StringPair(s,temp));
			s=temp;
		}
		return path;
	}
//	String randomWalk(Graph G)
//	{
//		Map<Integer,String> index=new HashMap<Integer,String>();
//		Set<StringPair> set=new HashSet<StringPair>();
//		int i=0;
//		for(Map.Entry<String, Vex> m:G.vexs.entrySet())
//			index.put(i++,m.getKey());
//		int randNum=new Random().nextInt(G.vexs.size());
//		String s=index.get(randNum);
//		String path="";
////		Scanner scan=new Scanner(System.in);
////		int times=0;
////		System.out.println("请输入遍历字符个数，输入0则全部遍历");
////		times=scan.nextInt();
//		
//		while(true)
//		{
//			path+=s+" ";
//			Vex v=G.vexs.get(s);
//			if(v.adjEdges.isEmpty())
//				break;
//			Map<Integer,String> vexIndex=new HashMap<Integer,String>();
//			int j=0;
//			for(Map.Entry<String, Integer> m:v.adjEdges.entrySet())
//				vexIndex.put(j++,m.getKey());
//			int rand_vex_Num=new Random().nextInt(v.adjEdges.size());
//			String temp=vexIndex.get(rand_vex_Num);
//			if(set.contains(new StringPair(s,temp)))
//			{
//				path+=temp;
//				break;
//			}
//			set.add(new StringPair(s,temp));
//			s=temp;
//		}
//		return path;
//	}
}

class Vex {
	Map<String, Integer> adjEdges;

	Vex() {
		adjEdges = new HashMap<String, Integer>();
	}
}

class Pair {
	String vex;
	int weight;

	Pair(String vex, int weight) {
		this.vex = vex;
		this.weight = weight;
	}
}
class StringPair{
	String s1;
	String s2;
	public StringPair(String s1,String s2) {
		this.s1=s1;
		this.s2=s2;
	}
	public boolean equals(Object obj){
		if(this==obj)
			return true;
		if(null==obj)
			return false;
		if(getClass()!=obj.getClass())
			return false;
		StringPair temp=(StringPair)obj;
		if(!temp.s1.equals(this.s1)||!temp.s2.equals(this.s2))
			return false;
		return true;
	}
	public int hashCode(){
		return 1;
	}
}
class Graph {
	int n, m;
	Map<String, Vex> vexs;

	Graph() {
		n = 0;
		m = 0;
		vexs = new HashMap<String, Vex>();
	}
}
