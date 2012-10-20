'''
Created on Oct 3, 2012

@author: Administrator
'''
from graphics import *
def main():
  win = GraphWin("My Circle", 100, 100)
  c = Circle(Point(50,50), 10)
  c.draw(win)
  win.getMouse() # pause for click in window
  win.close()
main()