//generate and convert value to string
def genref =  "AD" + String.valueOf(((int)((Math.random()*9999999)+1)))

//uiD setup
def alpha = ["a", "b", "c", "d", "e", "f", "g", 
"h", "i", "j", "k", "l", "m", "n", "o", "p", 
"q", "r", "s", "t", "u", "v", "w", "x", "y", "z"] as String[]

def alphaKeys = new String[4]

def z = 0

for(def i = 0; i < 4; i++){
	
	z = ((int)((Math.random()*26)))

	alphaKeys[i] = alpha[z].toUpperCase()
	
}
	
def uid = alphaKeys[0] + alphaKeys[1] + String.valueOf(((int)((Math.random()*99999999)+1))).substring(0, 6) + alphaKeys[2] + alphaKeys[3]

println "\n Genref: " + genref + " " + "uid: " + uid 

updateXML(genref, uid)

dropVideo(genref, uid, "automation_ssm.mxf")

println ""
println "*******"
println "Genref: " + genref + " Uid: " + uid
println "*******"

/*update fields mappers in xml*/
def updateXML(genref, uid){
  
  println "\n Update xml..."
	
  //read file:
def xmlContents = new File("collections/collection.json").getText()

//where to put new file
def networkPath =  "collections/collection1.json"

//put values in array
def vals = [genref,uid]

//log.info vals

def keys = ["{#Project#AVODgenref}","{#Project#AVODuid}"]

def key = keys[0]

for(def i = 0; i < 2; ++i){
	
	xmlContents = xmlContents.replace(keys[i],vals[i]);	
	
}

File file = new File(networkPath)

file.text = xmlContents
  
  println "\n Done updating xml..."
  
}

def dropVideo(def genref, def uid, def name){
  
  println "\n Dropping source..."
 
  def soureFile = "//172.16.103.220/Encoder_Area/Ardome/Automation files/" + name
  def destFile = "//172.16.103.220/Encoder_Area/Ardome/AUTOMATION_SYSTEM/"
  
  def vidName = destFile + genref + "_" + uid + ".mxf"

  
  def srcStream = new File(soureFile).newDataInputStream()
  def dstStream = new File(vidName).newDataOutputStream()
  dstStream << srcStream
  srcStream.close()
  dstStream.close()

  println "\n Done dropping source..." + "\n" + vidName
}

logFolders(genref)

def logFolders(genref){
  
  File file = new File("../folders.txt")
  
  file.text = ''
 
  file << genref + "_" + "13303\n" +  genref + "_" + "13403\n"
  
}
  

