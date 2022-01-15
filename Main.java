import java.io.IOException;
import java.util.ArrayList; 
import java.util.Scanner;
//import java.util.*;
  
//import java.io.File;  // Import the File class
//import java.io.FileNotFoundException;  // Import this class to handle errors
 
public class Main {
 
    public static void main(String[] args) throws IOException 
    {
       // try {
     // File myObj = new File("input.txt");
      //Scanner in = new Scanner(myObj);
      Scanner in = new Scanner(System.in);
      int holder = 1;
      while (in.hasNextInt() && holder != 0) 
      {
        int n = in.nextInt();
        if(n != 0)
        {
        int m = in.nextInt();
        int s = in.nextInt(); int d = in.nextInt();
    ArrayList<ArrayList<Node>> edges = new ArrayList<ArrayList<Node>>();
     for (int i = 0; i <= m; i++) 
     {
       ArrayList<Node> item = new ArrayList<Node>();
       edges.add(item);
     }
      for (int i = 0; i < m; i++) 
     {
       int u = in.nextInt();
       edges.get(u).add(new Node(u, in.nextInt(), in.nextInt()));
     }
         Pq pq = new Pq();
         pq.StartHeap(m, m);
     Dijkstra(edges, pq, m, 0, s, d);
        }
        else
        {
          holder = 0;
        }
      }
      in.close();
   // //}
      //  catch (FileNotFoundException e) {
     // System.out.println("An error occurred.");
    //  e.printStackTrace();
    }
  //}
  public static void Dijkstra(ArrayList<ArrayList<Node>>  edges, Pq pq, int n, int test, int s, int d)
  {
    if(n == 1)
    {
      System.out.println("-1");
      return;
    }
    boolean[] visited = new boolean[n + 1];
    ArrayList<ArrayList<Node>> shortestPath = new ArrayList<ArrayList<Node>>(); // could place this in
   // System.out.println("b");
    for (int i = 0; i <= n; i++) 
    {
      ArrayList<Node> item = new ArrayList<Node>();
      shortestPath.add(item);
    }
   //System.out.println("c");
    Node begin = new Node(0,0,0);
    
    shortestPath.get(s).add(begin);
    /*setsup intial queue*/
    
    for(int i = 0; i < edges.get(s).size(); i++)
    {
      Node temp = edges.get(s).get(i);
      pq.Insert(temp, temp.cost);
    }
   // System.out.println("c1");
    while(!(pq.IsEmpty()))
    {
     // System.out.println("d");
      Node pop = pq.ExtractMin();
     // search neigbors queues
        //if true check neigbors
      if(nodeProcess(pop, visited, shortestPath))
      {
        //end location dont check edges
        if(pop.to != d)
        {
         // System.out.println("f");
          for(int i = 0; i < edges.get(pop.to).size(); i++)
        {
          pq.Insert(edges.get(pop.to).get(i), (edges.get(pop.to).get(i).cost + pop.cost));
        }
        } 
      }
    }
    //System.out.println("g");
    if(test == 0)
    {
      
           /* for(int j = 0; j < shortestPath.size(); j++)
      {
        System.out.println(shortestPath.get(j).get(0).from + " " + shortestPath.get(j).get(0).to);
      }*/
      
      //System.out.println("a");
    destroy(shortestPath, edges, pq, d, s); //make v value target location /*fix*/
    // System.out.println("a");
    
    for(int i = 0; i < edges.size(); i++)
    {
      for(int j = 0; j < edges.get(i).size(); j++)
      {
       
        //System.out.println(edges.get(i).get(j).from + " " + edges.get(i).get(j).to);
      }
    }
    //System.out.println("a");
    Dijkstra(edges, pq, n, 1, s, d);
  }
    else
    {
     // System.out.println("h");
      if(shortestPath.get(d).size() > 0)
      {
        System.out.println(shortestPath.get(d).get(0).cost);
      }
      else
      {
        System.out.println(-1);
      }
    }
  }
  /*updates shortest path among other stuff*//////////
  public static boolean nodeProcess(Node pop, boolean[] visited, ArrayList<ArrayList<Node>> shortestPath)
  {
    //System.out.println(pop.to +" " + visited.length);
  if(!(visited[pop.to]))
  {
    shortestPath.get(pop.to).add(pop);
    
    visited[pop.to] = true;
    
    
    return true;
  }
  else if (shortestPath.get(pop.to).get(0).cost == pop.cost)
  {
    //it knows the value already does not have to check neigbors
    shortestPath.get(pop.to).add(pop);
    return false;
  }
  else if(shortestPath.get(pop.to).get(0).cost > pop.cost)
  {
    shortestPath.get(pop.to).clear();
    shortestPath.get(pop.to).add(pop);
    return true;
  }
  else
  {
    return false;
  }
  }
  //create new list 
  
  
  
  
  
  
  
  /*finds the shortest path bewteen two nodes and destroys them*/
  
  public static void destroy(ArrayList<ArrayList<Node>> shortestPath, ArrayList<ArrayList<Node>> edges, Pq pq, int d, int s)
  {
   
    if(d == s) // create a from
    {
      return;
    }
   // System.out.println(d + " " + s);
    for(int i = 0; i < shortestPath.get(d).size(); i++ )
    {
      for(int j = 0; j < edges.get(shortestPath.get(d).get(i).from).size(); j ++)
      {
        if(edges.get(shortestPath.get(d).get(i).from).get(j).to == shortestPath.get(d).get(i).to)
        {
          
         // System.out.println(shortestPath.get(d).get(i).from + " A " + shortestPath.get(d).get(i).to);
          
          edges.get(shortestPath.get(d).get(i).from).remove(j);
          
          destroy(shortestPath, edges, pq, shortestPath.get(d).get(i).from, s);
        }
      }
    }
  } 
}
/////////////////////
class Pq
{
  //Dictionary pos = new Hashtable(); takes up to much memory cant implement position
  public Node[] H;
  public int size;

  public int parent(int index)
  {
    return index/2;
  }
  public int leftChild(int index)
  {
    return (2 * index);
  }
  public int rightChild(int index)
  {
    return (2 * index) + 1;
  }
  public void Heapify_Up(int index)
  {
    if(index > 1)
    {
      int j = parent(index);
      if(H[index].cost < H[j].cost)
      {
        Node temp = H[j];
        H[j] = H[index];
        H[index] = temp;
        /*add pos change*/
        Heapify_Up(j);
      }

    }
  }
  public void Heapify_Down(int index)
  {
    int j = 0;
    int n = size;
    if(2*index > n)
    {
      return;
    }
    else if (2*index < n)
    {
     int left = 2*index;
     int right = 2*index + 1;
        /*j equal to smallest key node*/
     if(left == right)
     {
       j = left;
     }
     else if(left < right)
     {
       j = left;
     }
     else
     {
       j = right;
     }
        /*if(compare index and j)*/
    }
    else if(2*index == n)
    {
     j = 2*index;
        /*if(compare index and j)
         * clean up put j at base 
         swap at end 
         */
    }
    
    /*add pos change*/
    if(H[index].cost > H[j].cost)
    {
      Node temp = H[j];
      H[j] = H[index];
      H[index] = temp;
      Heapify_Down(j);
    }
      
  }
  public void StartHeap(int n, int m)
  {
    H = new Node[n + 1];
    //pos = new int[m+1][m+1];
  }
  public void Insert(Node item, int value)
  {
    /*update*/
    Node holder = new Node(item.from, item.to, value);
    H[size + 1] = holder;
    
   // System.out.println(H[1].cost + " " + (size + 1));
    /*update pos*/
    Heapify_Up(size + 1);
    //pos.put(holder, (size + 1));
    
    
   // System.out.println(H[1].cost + " " + (size + 1));
    size++;
  }
  public Node FindMin()
  {
    return H[1];
  }
  public void Delete(int index)
  {
    H[index] = H[size];
    Heapify_Down(index);
    size--;
  }
  public Node ExtractMin()
  {
    Node i = FindMin();
    Delete(1);
    return i;
  }
  // could not fiqure out a position structure that wouldnt eat memory
  public void ChangeKey(Node item, int newValue)
  {
   // int i = pos[item.from][item.to];
    /*update to node*/
  //  H[i].cost = newValue;
  }
  public boolean IsEmpty()
  {
    if(size == 0)
    {
      return true;
    }
    else 
    {
      return false;
    }
  }
}
class Node  {
    public int cost;
    public int from;
    public int to;
    // Constructors of this class
  
    // Constructor 1
    public Node() {}
  
    // Constructor 2
    public Node(int from, int to, int cost)
    {
        this.from = from;
        this.to = to;
        this.cost = cost;
    }
}