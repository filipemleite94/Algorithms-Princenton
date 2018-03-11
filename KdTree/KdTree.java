import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Stack;
import java.util.Comparator;

public class KdTree { 
 private ListNode tree;
 public KdTree()                               // construct an empty set of points 
 {
  tree = new ListNode();
 }
 public boolean isEmpty()                      // is the set empty? 
 {
  return tree.size==0;
 }
 public int size()                         // number of points in the set 
 {
  return tree.size;
 }
  public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
 {
   if(p==null)
       throw new java.lang.IllegalArgumentException();
   tree.addNode(p);
 }
 public boolean contains(Point2D p)            // does the set contain point p? 
 {
  if(p==null)
      throw new java.lang.IllegalArgumentException();
  return tree.find(p)!=null;
 }
 public void draw()                         // draw all points to standard draw 
 {
  tree.draw();
 }
 public Iterable<Point2D> range(RectHV rect)
 {
     if(rect==null)
         throw new java.lang.IllegalArgumentException();
     return tree.rangeSearch(rect);
 }
 public Point2D nearest(Point2D p)
 {
     if(p==null)
         throw new java.lang.IllegalArgumentException();
     return tree.nearest(p);
 }
 
 private class ListNode{
  public Node head;
  public int size;
  public ListNode(){
   size = 0;
   head = null;
  }
  public void addNode(Point2D that){
      if(head==null){
          head = new HNode(that, new RectHV(0,0,1,1));
          size++;
          return;
      }
      else{
          if(head.addNode(that))
              size++;
      }
  }
  public Point2D find(Point2D that){
      if(head==null)
          return null;
      return head.find(that);
  }
  public void draw(){
      if(head == null)
          return;
      head.draw(0,1,0,1);
  }
  public Iterable<Point2D> rangeSearch(RectHV rect){
      Stack<Point2D> sp = new Stack<Point2D>();
      if(head == null)
          return sp;
      head.searchRange(sp, rect);
      return sp;
  }
  public Point2D nearest(Point2D p){
      if(head == null)
          return null;
      PointDist p2 = new PointDist(p);
      head.nearest(p2);
      return p2.pAux;
  }
 }
 private Comparator<Point2D> compH = Point2D.X_ORDER;
 private Comparator<Point2D> compV = Point2D.Y_ORDER;
 private class Node{
  public Point2D point;
  public RectHV rect;
  public Node smaller;
  public Node bigger;
  public Node(Point2D point, RectHV rect){
   this.point = point;
   this.rect = rect;
   smaller = null;
   bigger = null;
  }
  public Point2D find(Point2D that){
      if(point.equals(that)) return point;
      if(compare(that)<=0){
          if(bigger != null)
              return bigger.find(that);
          return null;
      }
      if(smaller!=null){
          return smaller.find(that);
      }
      return null; 
  }
  public int compare(Point2D that){return 0;};
  public boolean addNode(Point2D that){
   if(point.equals(that)) return false;
   if(compare(that)<=0){
       if(bigger==null){
           bigger = createDifferent(that, true);
           return true;
       }
       return bigger.addNode(that);
   }
   if(smaller==null){
       smaller = createDifferent(that, false);
       return true;
   }
   return smaller.addNode(that);
  }
  public void nearest(PointDist p){
      p.Associate(point);
      if(p.dist==0)
          return;
      boolean smallerNull = smaller==null;
      boolean biggerNull = bigger==null;
      if(smallerNull && biggerNull)
          return;
      if(biggerNull && !smallerNull){
          if(smaller.rect.distanceSquaredTo(p.point)<=p.dist)  smaller.nearest(p);
          return;
      }
      if(smallerNull){
          if(bigger.rect.distanceSquaredTo(p.point)<=p.dist)   bigger.nearest(p);
          return;
      }
      double d1 = bigger.rect.distanceSquaredTo(p.point);
      double d2 = smaller.rect.distanceSquaredTo(p.point);
      if(d1<=d2){
          if(d1<=p.dist){
              bigger.nearest(p);
              if(d2<=p.dist) smaller.nearest(p);
          }
          return;
      }
      if(d2<=p.dist){
          smaller.nearest(p);
          if(d1<=p.dist) bigger.nearest(p);
      }
      return;
  }
    public void searchRange(Stack<Point2D> sp, RectHV rect){
      if(rect.contains(point))
          sp.push(point);
      if(bigger!=null && bigger.rect.intersects(rect)) bigger.searchRange(sp,rect);
      if(smaller!=null && smaller.rect.intersects(rect)) smaller.searchRange(sp, rect);
  }
  public Node createDifferent(Point2D that, boolean b){return null;};
  public void draw(double xmin, double xmax, double ymin, double ymax){
      StdDraw.setPenColor(StdDraw.BLACK);
      StdDraw.setPenRadius(0.01);
      point.draw();
      StdDraw.setPenRadius();
      drawThis(xmin,xmax,ymin,ymax);
      if(smaller!=null)
          drawSmaller(xmin, xmax, ymin, ymax);
      if(bigger!=null)
          drawBigger(xmin, xmax, ymin, ymax);
  }
  public void drawThis(double xmin, double xmax, double ymin, double ymax){return;};
  public void drawSmaller(double xmin, double xmax, double ymin, double ymax){return;};
  public void drawBigger(double xmin, double xmax, double ymin, double ymax){return;};
 }
 private class HNode extends Node{
  public HNode(Point2D point, RectHV rect){
   super(point, rect);
  }
  @Override
  public int compare(Point2D that){
      return compH.compare(point, that);
  }
  @Override
  public Node createDifferent(Point2D that, boolean b){
      if(b)
          return new VNode(that, new RectHV(point.x(), rect.ymin(), rect.xmax(), rect.ymax()));
      return new VNode(that, new RectHV(rect.xmin(), rect.ymin(), point.x(), rect.ymax()));
  }
  @Override
  public void drawThis(double xmin, double xmax, double ymin, double ymax){
      StdDraw.setPenColor(StdDraw.RED);
      StdDraw.line(point.x(), ymin, point.y(), ymax);
  }
  @Override
  public void drawSmaller(double xmin, double xmax, double ymin, double ymax){
      smaller.draw(xmin, point.x(), ymin, ymax);
  }
  @Override
  public void drawBigger(double xmin, double xmax, double ymin, double ymax){
      bigger.draw(point.x(), xmax, ymin, ymax);
  }
 }
 private class VNode extends Node{
  public VNode(Point2D point, RectHV rect){
   super(point, rect);
  }
  @Override
  public int compare(Point2D that){
      return compV.compare(point, that);
  }
  @Override
  public Node createDifferent(Point2D that, boolean b){
      if(b)
          return new HNode(that, new RectHV(rect.xmin(), point.y(), rect.xmax(), rect.ymax()));
      return new HNode(that, new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), point.y()));
  }
  @Override
  public void drawThis(double xmin, double xmax, double ymin, double ymax){
      StdDraw.setPenColor(StdDraw.BLUE);
      StdDraw.line(point.x(), ymin, point.y(), ymax);
  }
  @Override
  public void drawSmaller(double xmin, double xmax, double ymin, double ymax){
      smaller.draw(xmin, xmax, ymin, point.y());
  }
  @Override
  public void drawBigger(double xmin, double xmax, double ymin, double ymax){
      bigger.draw(xmin, xmax, point.y(), ymax);
  }
 }
 private class PointDist{
     public Point2D point;
     public Point2D pAux;
     public double dist;
     public PointDist(Point2D point){
         dist = Double.POSITIVE_INFINITY;
         this.point = point;
         pAux = null;
     }
     public void Associate(Point2D p){
         double temp = p.distanceSquaredTo(point);
         if(temp>=dist)
             return;
         pAux=p;
         dist = temp;
         return;
     }
 }
}