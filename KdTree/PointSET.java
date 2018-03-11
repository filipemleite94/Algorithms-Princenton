import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.TreeSet;
import java.util.Stack;
import java.util.Iterator;

public class PointSET {
 private TreeSet<Point2D> tree;
 public PointSET()                               // construct an empty set of points 
 {
  tree = new TreeSet<Point2D>();
 }
 public boolean isEmpty()                      // is the set empty? 
 {
  return tree.size()==0;
 }
 public int size()                         // number of points in the set 
 {
  return tree.size();
 }
 public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
 {
           if(p==null)
          throw new java.lang.IllegalArgumentException();
  tree.add(p);
 }
 public boolean contains(Point2D p)            // does the set contain point p? 
 {
           if(p==null)
          throw new java.lang.IllegalArgumentException();
  return tree.contains(p);
 }
 public void draw()                         // draw all points to standard draw 
 {
  for(Point2D p : tree){
   p.draw();
  }
 }
 public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary) 
 {
           if(rect==null)
          throw new java.lang.IllegalArgumentException();
  Point2D auxMin = new Point2D(rect.xmin(), rect.ymin());
  Point2D auxMax = new Point2D(rect.xmax(), rect.ymax());
  Stack<Point2D> sp = new Stack<Point2D>();
     for(Point2D p:tree.subSet(auxMin, true, auxMax, true)){
   if(p.x()<rect.xmin()||p.x()>rect.xmax())
    continue;
   sp.push(p);
  }
  return sp;
 }
 public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
 {
           if(p==null)
          throw new java.lang.IllegalArgumentException();
           if(size()==0) return null;
  Point2D aux1, aux2;
  boolean isNull;
  aux1 = tree.lower(p);
  isNull=false;
  if(aux1==null)
  {
   isNull = true;
   aux1 = tree.ceiling(p);
  }
  double distance = p.distanceTo(aux1);
  if(!isNull){
   aux2 = tree.ceiling(p);
   if(aux2!=null){
    double distance2 = p.distanceTo(aux2);
    if(distance>distance2){
     distance=distance2;
     aux1 = aux2;
    }
   }
  }
  RectHV rect = new RectHV(p.x()-distance, p.y()-distance, p.x()+distance, p.y()+distance);
  for(Point2D p2: range(rect)){
   double distance2 = p.distanceTo(p2);
   if(distance>distance2){
    distance=distance2;
    aux1 = p2;
   }
  }
  return aux1;
 }
}