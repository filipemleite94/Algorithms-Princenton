import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import java.util.Hashtable;
import  java.util.Stack;
import java.util.ArrayList;
import java.util.HashSet;
import java.lang.IllegalArgumentException;

public class WordNet {
 private Digraph digraph;
 private Hashtable<String, Integer> stringMap;
 private String synsets[];
 private int root;
 private SAP sap;
 private int size =0;
 
 // constructor takes the name of the two input files
 public WordNet(String synsets, String hypernyms){  
  if(synsets==null||hypernyms==null)
   throw new IllegalArgumentException();
  In input = new In(synsets);
  Stack<String> sSynonims = new Stack<String>();
  size=0;
  while(input.hasNextLine()){
   size++;
   sSynonims.push(input.readLine().split(",")[1]);
  }
  input.close();
  digraph = new Digraph(size);
    
  input = new In(hypernyms);
  while(input.hasNextLine()){
   String[] sV = input.readLine().split(",");
   int source = Integer.parseInt(sV[0]);
   for(int i = 1; i<sV.length; i++){
    digraph.addEdge(source, Integer.parseInt(sV[i]));
   }
  }
  input.close();
  
  this.synsets = new String[size];
  stringMap = new Hashtable<String, Integer>();
  int i = size;
  root = -1;
  while(i-->0){
   this.synsets[i] = sSynonims.pop();
   if(digraph.outdegree(i)==0){
    if(root!=-1){
     throw new IllegalArgumentException();
    }
    root = i;
   }
   for(String s : this.synsets[i].split(" ")) stringMap.put(s, i);
  }
  if(root==-1)
   throw new IllegalArgumentException();
  
  if(detectCycle(root,new HashSet<Integer>()))
   throw new IllegalArgumentException();
  sap = new SAP(digraph);
  digraph = null;
 }
 
 private boolean detectCycle(int n, HashSet<Integer> m){
  if(m.contains(n))
   return true;
  m.add(n);
  for(Integer i:digraph.adj(n)){
   if(detectCycle(i, m))
    return true;
  }
  m.remove(n);
  return false;
 }

 // returns all WordNet nouns
 public Iterable<String> nouns(){
    ArrayList<String> arr = new ArrayList<String>(size);
    for(String vS : synsets){
        for(String s : vS.split(" ")){
            arr.add(s);
        }
    }
    return arr;
 }

 // is the word a WordNet noun?
 public boolean isNoun(String word){
    if(word==null) throw new IllegalArgumentException();
    return stringMap.containsKey(word);
 }

 // distance between nounA and nounB (defined below)
 public int distance(String nounA, String nounB){
  if(nounA==null||nounB==null)
     throw new IllegalArgumentException();
  Integer n1 = stringMap.get(nounA);
  Integer n2 = stringMap.get(nounB);
  if(n1==null||n2==null)
   throw new IllegalArgumentException();
  return sap.length(n1,n2);
 }

 // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
 // in a shortest ancestral path (defined below)
 public String sap(String nounA, String nounB){
  if(nounA==null||nounB==null)
     throw new IllegalArgumentException();
  Integer n1 = stringMap.get(nounA);
  Integer n2 = stringMap.get(nounB);
  if(n1==null||n2==null)
   throw new IllegalArgumentException();
  return synsets[sap.ancestor(n1,n2)];
 }

 // do unit testing of this class
 //public static void main(String[] args)
 }