## playing with the .pst parser (java)

This code aims at playing with .pst files in java. 
It uses the lib-pff package build for java (sources : [https://github.com/rjohnsondev/java-libpst](https://github.com/rjohnsondev/java-libpst)  )

To get packages, see [https://search.maven.org](https://search.maven.org)  

1. change your code .java using all the functionnalities available here : [http://rjohnsondev.github.io/java-libpst/](http://rjohnsondev.github.io/java-libpst/)  

### TO LAUNCH IT : 

2. compile your code with maven : `mvn clean install`  

3. execute your code : `java -jar target/mynetwork-1.0-SNAPSHOT.jar /Users/Fleur/Documents/AGPC/01-java/MyNetWork/src/main/resources/mailbox.pst`

4. See results in your mongoDB



### mongo DB
A connection to a MongoDB has been added. 
To play with the mongoDB on your computer from the command line :  

* just start `mongo` : in case of error you may need to restart see (1)
* `show dbs` to see existing DB
* `use [DB name]` to switch to the right database
*  `show collections` to see the collections included
*  `db.[collection].find()` to list contents of the collection you want


(1) Problem with mongo : 
- Restart mongo `brew services restart  mongodb --verbose`
exit


**NEXT STEPS** : 
 
2. see how to plug and fill a mongoDB 
3. play with the data stored in mongo with d3.js
4. which means : learn d3.js X)   
5. Switch to scala instead of java (as both languages are based on jvm and can access .jar)
6. 
