//check if file exists

import groovy.io.FileType

public class NoProcessedException extends Exception{
  
  public NoProcessedException(String message){
    super(message)
  }
  
}

Map<String, Integer> aMap = new HashMap<String, Integer>();

def newline = System.getProperty("line.separator")
def logPaths = new File("../folders.txt")
def logPaths_catalog = new File("../AD_Catalog.txt")
def checkText = new File("../folders.txt").getText()

//don't proceed if file is empty
if (!(checkText.length() > 0)) {
  
  println "Directory file not populated yet... aborting"
  
 // hudson.model.Result.SUCCESS;
  return
}

def a1 = 0;

def a2 = 0;

println "" 
println "Folder(s) scanned: " 

logPaths.withReader { reader ->

  while ((line = reader.readLine())!=null) {
           
 	foldername = "//172.16.103.220/MediaManager/irdeto.com/ContentGroup/" + line
    
	def list = []

	def dir = new File(foldername)
    
    println " " + foldername 
    
    if (dir.exists()){
    
		dir.eachFileRecurse (FileType.FILES) { file ->
       	
          println "   " +file.path
        
          if (file.path.indexOf("13303") > 0){a1++}  //explora
          if (file.path.indexOf("13403") > 0){a2++}  //nano
          
  			list << file
		}
      
      
    }
     
         }
      }

def fileExists(def path){
  
 return new File(path).exists()
  
}

println "" 

println "Folder(s) scanned - Catalog:" 

def newFolderName = "//172.16.103.220/Publish-QA/IS20/SAT/Asset/"

  logPaths_catalog.withReader { reader ->

    while ((line = reader.readLine())!=null) {

      def list = []

      def dir = new File(newFolderName + line)
    
      if (dir.exists()){
    
		    if (dir.path.indexOf("Explora") > 0){
          a1++
          println " " + newFolderName + line
          }  //explora

        if (dir.path.indexOf("Nano") > 0){
          a2++
          println " " + newFolderName + line
        }  

      } 
    }
  }

println ""
if(a1 > 4){println "EXP - YES"} else {println "EXP - X"}
if(a2 > 4){println "NAN - YES"} else {println "NAN - X"}
println ""
println a1
println a2

if((a1 > 4 ) && (a2 > 4)) {
  
  logPaths.text = ''
  logPaths_catalog.text = ''
 
} else {

  throw new NoProcessedException("Not all files were processed...");
  //hudson.model.Result.FAILURE;
}
