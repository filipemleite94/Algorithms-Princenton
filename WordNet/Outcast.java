public class Outcast {
 private WordNet wordnet;
 
 // constructor takes a WordNet object
 public Outcast(WordNet wordnet){
  this.wordnet = wordnet;
 }
 
 // given an array of WordNet nouns, return an outcast
 public String outcast(String[] nouns)   {
  int size = nouns.length;
  int[][] mat = new int[size][];
  for(int i=0;i<size;i++){
   mat[i] = new int[i];
   for(int j = 0; j<i; j++){
    mat[i][j] = wordnet.distance(nouns[i],nouns[j]);
   }
  }
  int max = -1;
  int id = 0;
  for(int i =1; i<size; i++){
   int sum = 0;
   int j;
   for(j=0; j<i; j++){
    sum+=mat[i][j];
   }
   for(j++;j<size;j++){
    sum+=mat[j][i];
   }
   if(sum>max){
    max=sum;
    id = i;
   }
  }
  return nouns[id];
 }
 
 // see test client below
 //public static void main(String[] args)
}