# rpa-assessment-shortest_path

To build and package application use:
    mvn clean install

To run application with "interactiveMode", provide input map with full path:
  java -jar ShortestPathSolution-1.0-jar-with-dependencies.jar 
  
To run application with "Preloaded TestCases", add the test argument:
  java -jar ShortestPathSolution-1.0-jar-with-dependencies.jar test
  
To run application and show intermediate steps include the steps argument:
  java -jar ShortestPathSolution-1.0-jar-with-dependencies.jar steps    
  java -jar ShortestPathSolution-1.0-jar-with-dependencies.jar test steps
      



#### Notes:
W - Obstacle or Wall    (=0)
. - Clear Path, ie one can move to this cell (=3)
S - Start   (=1)
E - End     (=2)
* - Path taken (=5) (array to track - distance)
" - Visited    (=4) (array to track - visited)