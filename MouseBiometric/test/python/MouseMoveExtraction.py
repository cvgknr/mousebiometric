'''
Created on Oct 3, 2012

@author: Venugopala C
'''
import pprint
from graphics import *

 
import xml.dom.minidom
from xml.dom.minidom import Node
import math

def printMouseMoves(L):
  print("MouseMoves")
  _List = []
  _time = None
  duration = 0
  for node2 in L:
    for node3 in node2.childNodes:
      time = node3.getElementsByTagName('starttime').item(0).childNodes[0].data
      xpix = node3.getElementsByTagName('xpix').item(0).childNodes[0].data
      ypix = node3.getElementsByTagName('ypix').item(0).childNodes[0].data
      wheel = node3.getElementsByTagName('wheel').item(0).childNodes[0].data
      window = ""#node3.getElementsByTagName('window').item(0).childNodes[0].data
      if None !=_time:
        duration = int(time) - int(_time)
      _time = time
      
      #print window + "  -  " + time + "  -  " + xpix + "  -  " + ypix + "  -  " + wheel
      d = {'window' : window, 'starttime': time, 'xpix': xpix, 'ypix': ypix, 'wheel': wheel, 'duration': duration}
      _List.append(d)
  return _List

def listApps(_List):
  _l=[]
  for t in _List:
    if t['window'] not in _l:
      _l.append(t['window'])
  return _l
def getAppMouseMove(app, _List):
  _l=[]
  for t in _List:
    if t['window'] == app:
      _l.append(t)
  return _l


def printPix(_List):
  for x in _List:
    print( str(x['starttime']) + '  ' + str(x['xpix'])+ '  ' + str(x['ypix']) + '  ' + str(x['duration']))
    
def drawPix(_List):
  win = GraphWin("Sample", 2000, 1500)
  pt = None
  for x in _List:
    if None != pt:
      #print (x['xpix'] + '  ' + x['ypix'] + '  ' + str(x['duration']))
      line = Line(pt, Point(x['xpix'], x['ypix']))
      line.draw(win)
    pt = Point(x['xpix'], x['ypix'])
  win.getMouse() # pause for click in window
  win.close()
  
def curveLength(_List):
  _pt = None
  sum = 0
  for p in _List:
    if None != _pt:
      _x = int(p['xpix']) - int(_pt['xpix'])
      _y = int(p['ypix']) - int(_pt['ypix'])
      #print("xdif : " + str(_x) + "   ydif : " + str(_y))
      _sq = _x * _x + _y * _y
      #print(_sq)
      sum = sum + math.sqrt(_sq)
    _pt = p
  return sum
def totalTime(_List):
  _dt = None
  sum = 0
  for p in _List:
    if None != _dt:
      sum = sum + (int(p['starttime']) - int(_dt['starttime']))
    _dt = p
  return sum


def averageSpeed(_List):
  _dt = None
  sum = 0
  for p in _List:
    if None != _dt:
      _x = int(p['xpix']) - int(_dt['xpix'])
      _y = int(p['ypix']) - int(_dt['ypix'])
      #print("xdif : " + str(_x) + "   ydif : " + str(_y))
      _sqrt = math.sqrt(_x * _x + _y * _y)
      _time =(int(p['starttime']) - int(_dt['starttime']))
      sum = sum + (_sqrt / _time)
    _dt = p
  return sum/len(_List)

    
#doc = xml.dom.minidom.parse("NedBakelman_WordProcessor_001.xml")
doc = xml.dom.minidom.parse("../../logsamples/VenugopalaChannarayappa_Browser_001.xml")
for node in doc.getElementsByTagName("biometrics-data"):
  L = node.getElementsByTagName("mouseMoves")
  _l = printMouseMoves(L)
  print (_l)
  _apps = listApps(_l)
  doc = getAppMouseMove('Document1 - Microsoft Word', _l)
  #doc = getAppMouseMove(, _l)
  sizeOfCurve = len(doc)
  '''print ("Size of curve: " + str(sizeOfCurve))
  print('Curve Length : ' + str(curveLength(doc)))
  print('Total Time : ' + str(totalTime(doc)))
  print('Average Speed : ' + str(averageSpeed(doc)))'''
  ##uncomment the below line to see the trajectory in Graphics 
  drawPix(_l)
  
  

