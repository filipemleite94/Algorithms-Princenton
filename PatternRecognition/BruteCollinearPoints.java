import java.util.Comparator;
import java.util.Arrays;

public class BruteCollinearPoints {
   private LineSegment[] lines;
   public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
   {
       int i,j,k,l,o;
       if(points==null) throw new java.lang.IllegalArgumentException();
       int size = points.length;
       Point[] p = new Point[size];
       for(i=0;i<size;i++){
           if(points[i]==null) throw new java.lang.IllegalArgumentException();
           p[i] = points[i];
       }
       class NodeLine {
           public LineSegment line;
           public NodeLine next;
           public NodeLine(LineSegment seg, NodeLine node){
               line = seg;
               next = node;
           }
       }
       int length =0;
       NodeLine head = new NodeLine(null, null);
       NodeLine actual = head;
       Arrays.sort(p);
       for(i=0;i<size;i++){
           if(i>0)
               if(p[i].compareTo(p[i-1])==0) throw new java.lang.IllegalArgumentException();
           Comparator<Point> comp = p[i].slopeOrder();
           for(j=i+1;j<size;j++){
               Comparator<Point> comp2 = p[j].slopeOrder();
               for(k = j+1; k<size; k++){
                   if(comp.compare(p[j], p[k])!=0)
                       continue;
                   o=-1;
                   for(l=k+1; l<size; l++){
                       if(comp2.compare(p[k], p[l])==0){
                           o=l;
                       }
                   }
                   if(o>-1){
                       actual.next = new NodeLine(new LineSegment(p[i],p[o]), null);
                       actual = actual.next;
                       length++;
                   }
               }
           }
       }
       lines = new LineSegment[length];
       actual = head.next;
       head.next = null;
       for(i=0; i<length; i++)
       {
           lines[i]=actual.line;
           actual.line = null;
           head = actual;
           actual = actual.next;
           head.next = null;
       }
   }
   public           int numberOfSegments()        // the number of line segments
   {
       return lines.length;
   }
   public LineSegment[] segments()                // the line segments
   {
       int length = lines.length;
       LineSegment[] aux = new LineSegment[length];
       for(int i =0; i< length;  i++){
           aux[i] = lines[i];
       }
       return aux;
   }
}