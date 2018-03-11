import java.util.Comparator;
import java.util.Arrays;

public class FastCollinearPoints {
   private LineSegment[] lines;
   public FastCollinearPoints(Point[] points)    // finds all line segments containing 4 points
   {
       int i,j,k;
       if(points==null) throw new java.lang.IllegalArgumentException();
       int size = points.length;
       Point[] p = new Point[size];
       for(i=0;i<size;i++){
           if(points[i]==null) throw new java.lang.IllegalArgumentException();
           p[i] = points[i];
       }
       class NodeLine {
           public Point p1, p2;
           public NodeLine next;
           public NodeLine(Point p1, Point p2, NodeLine node){
               this.p1 = p1;
               this.p2 = p2;
               next = node;
           }
       }
       int length =0;
       NodeLine head = new NodeLine(null, null, null);
       NodeLine actual = head;
       int check;
       
       for(i=0;i<size;i++){ 
           Arrays.sort(p);
           if(i>0)
               if(p[i].compareTo(p[i-1])==0) throw new java.lang.IllegalArgumentException();
           Comparator<Point> comp = p[i].slopeOrder();
           Point point = p[i];
           Arrays.sort(p, comp);
           for(j=1;j<size-2; j=k-2){
               k = j +3;
               while(k<size && comp.compare(p[j],p[k])==0){
                   k++;
               }
               if(comp.compare(p[j],p[k-1])==0 && comp.compare(p[j],p[k-2])==0){
                   if(point.compareTo(p[j])>0)
                       continue;
                   Point p1 = (point.compareTo(p[j])<0)? point:p[j];
                   Point p2 = (point.compareTo(p[k-1])>0)? point:p[k-1]; 
                   actual.next = new NodeLine(p1,p2, null);
                   actual = actual.next;
                   length++;
               }
           }
       }
       
       lines = new LineSegment[length];
       actual = head.next;
       head.next = null;
       
       for(i=0; i<length; i++)
       {
           lines[i]=new LineSegment(actual.p1, actual.p2);
           head = actual;
           actual = actual.next;
           head.next = null;
       }
   }
   public int numberOfSegments()        // the number of line segments
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