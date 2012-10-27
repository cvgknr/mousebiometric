'''
Created on Oct 3, 2012

@author: Administrator
'''
import pprint
 
import xml.dom.minidom
from xml.dom.minidom import Node
def printMousePointers(L):
  print "MousePointers"
  for node2 in L:
    for node3 in node2.childNodes:
      node4 = node3.getElementsByTagName('starttime')
      node5 = node3.getElementsByTagName('endtime')
      print str(node4.item(0).childNodes[0].data) + " : " + str(node5.item(0).childNodes[0].data)

'''def printMouseMoves(L):
  print "MouseMoves"
  _List = ''
  for node2 in L:
    for node3 in node2.childNodes:
      node4 = node3.getElementsByTagName('starttime')
      xpix = node3.getElementsByTagName('xpix')
      ypix = node3.getElementsByTagName('ypix')
      wheel = node3.getElementsByTagName('wheel')
      window = node3.getElementsByTagName('window')
      
      print str(window.item(0).childNodes[0].data) + "  -  " + str(node4.item(0).childNodes[0].data) + "  -  " + str(xpix.item(0).childNodes[0].data) + "  -  " + str(ypix.item(0).childNodes[0].data) + "  -  " + str(wheel.item(0).childNodes[0].data)
'''

def printMouseMoves(L):
  print "MouseMoves"
  _List = []
  for node2 in L:
    for node3 in node2.childNodes:
      time = node3.getElementsByTagName('starttime').item(0).childNodes[0].data
      xpix = node3.getElementsByTagName('xpix').item(0).childNodes[0].data
      ypix = node3.getElementsByTagName('ypix').item(0).childNodes[0].data
      wheel = node3.getElementsByTagName('wheel').item(0).childNodes[0].data
      window = node3.getElementsByTagName('window').item(0).childNodes[0].data
      
      #print window + "  -  " + time + "  -  " + xpix + "  -  " + ypix + "  -  " + wheel
      d = {'window' : window, 'starttime': time, 'xpix': xpix, 'ypix': ypix, 'wheel': wheel}
      _List.append(d)
  return _List

def listApps(_List):
  _l=[]
  for t in _List:
    _l.append(t['window'])
  return _l
     
    
def printMouseClicks(L):
  print "MouseClicks"
  for node2 in L:
    for node3 in node2.childNodes:
      node4 = node3.getElementsByTagName('mousepresstime')
      node5 = node3.getElementsByTagName('mousereleasetime')
      print str(node4.item(0).childNodes[0].data) + " : " + str(node5.item(0).childNodes[0].data)
 
 
doc = xml.dom.minidom.parse("NedBakelman_WordProcessor_001.xml")
 
mapping = {}
for node in doc.getElementsByTagName("biometrics-data"):
  ''''L = node.getElementsByTagName("mousePointers")
  printMousePointers(L)

  L = node.getElementsByTagName("mouseClicks")
  printMouseClicks(L)
  '''
  L = node.getElementsByTagName("mouseMoves")
  _l = printMouseMoves(L)
  print _l
  _apps = listApps(_l)
  print _apps

