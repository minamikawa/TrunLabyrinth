package com.example.turnlabyrinth;
 
public class Circle
{
   public float radius,x,y,dx,dy,m;
   public int cr,cg,cb;
   public Circle(float radius, float x, float y, float dx, float dy, float m, int cr, int cb, int cg)
   {
      this.radius = radius;
      this.x = x;
      this.y = y;
      this.dx = dx;
      this.dy = dy;
      this.m = m;
      this.cr = cr;
      this.cg = cg;
      this.cb = cb;
   }
}