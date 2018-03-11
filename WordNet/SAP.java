import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import java.util.TreeMap;
import java.util.Stack;
import java.lang.IllegalArgumentException;

public class SAP {
 private Digraph dig;
 private int size;
 // constructor takes a digraph (not necessarily a DAG)
 public SAP(Digraph G){
  if(G==null) throw new IllegalArgumentException();
  dig = new Digraph(G);
  size = dig.V();
 }

 // length of shortest ancestral path between v and w; -1 if no such path
 public int length(int v, int w){
  if(v<0||w<0||v>=size||w>=size) throw new IllegalArgumentException();
  return GetAncestor(v,w).length;
 }

 // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
 public int ancestor(int v, int w){
  if(v<0||w<0||v>=size||w>=size) throw new IllegalArgumentException();
  return GetAncestor(v,w).id;
 }

 // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
 public int length(Iterable<Integer> v, Iterable<Integer> w){
  if(v==null||w==null) throw new IllegalArgumentException();
  return GetAncestor(v, w).length;
 }

 // a common ancestor that participates in shortest ancestral path; -1 if no such path
 public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
  if(v==null||w==null) throw new IllegalArgumentException();
  return GetAncestor(v, w).id;
 }

 private class NodeRel{
  int length, id;
  NodeRel(int l, int i){length =l;id=i;}
 }
 
 private NodeRel GetAncestor(Integer n1, Integer n2){
  TreeMap<Integer, Integer> dMap1, dMap2;
  Stack<Integer> ids1 = new Stack<Integer>();
  dMap1 = BFS(n1, ids1);
  dMap2 = BFS(n2, null);
  int min = Integer.MAX_VALUE;
  int id = -1;
  while(!ids1.empty()){
   int i = ids1.pop();
   if(!dMap2.containsKey(i))
    continue;
   int sum = dMap1.get(i)+dMap2.get(i);
   if(sum<min){
    min = sum;
    id = i;
   }
  }
  if(id<0) return new NodeRel(-1,-1);
  return new NodeRel(min, id);
 }
 
 private TreeMap<Integer,Integer> BFS(int n, Stack<Integer> ids){
  Stack<Integer> next = new Stack<Integer>();
  Stack<Integer> current = new Stack<Integer>();
  TreeMap<Integer,Integer> map = new TreeMap<Integer,Integer>();
  next.push(n);
  int depth = 0;
  map.put(n, depth);
  if(ids!=null) ids.push(n);
  while(!next.empty()){
   while(!next.empty()){
    current.push(next.pop());
   }
   depth++;
   while(!current.empty()){
    for(Integer i:dig.adj(current.pop())){
     if(map.containsKey(i))
      continue;
     next.push(i);
     map.put(i, depth);
     if(ids!=null) ids.push(i);
    }
   }
  }
  return map;
 }
 
 private NodeRel GetAncestor(Iterable<Integer> n1, Iterable<Integer> n2){
  TreeMap<Integer, Integer> dMap1, dMap2;
  Stack<Integer> ids1 = new Stack<Integer>();
  dMap1 = BFS(n1, ids1);
  dMap2 = BFS(n2, null);
  int min = Integer.MAX_VALUE;
  int id = -1;
  while(!ids1.empty()){
   int i = ids1.pop();
   if(!dMap2.containsKey(i))
    continue;
   int sum = dMap1.get(i)+dMap2.get(i);
   if(sum<min){
    min = sum;
    id = i;
   }
  }
  if(id<0) return new NodeRel(-1,-1);
  return new NodeRel(min, id);
 }
 
 private TreeMap<Integer,Integer> BFS(Iterable<Integer> it, Stack<Integer> ids){
  Stack<Integer> next = new Stack<Integer>();
  Stack<Integer> current = new Stack<Integer>();
  TreeMap<Integer,Integer> map = new TreeMap<Integer,Integer>();
  int depth = 0;
  for(Integer n : it){
   next.push(n);
   map.put(n, depth);
   if(ids!=null) ids.push(n);
  }
  while(!next.empty()){
   while(!next.empty()){
    current.push(next.pop());
   }
   depth++;
   while(!current.empty()){
    for(Integer i:dig.adj(current.pop())){
     if(map.containsKey(i))
      continue;
     next.push(i);
     map.put(i, depth);
     if(ids!=null) ids.push(i);
    }
   }
  }
  return map;
 }
 
 // do unit testing of this class
 //public static void main(String[] args)
}