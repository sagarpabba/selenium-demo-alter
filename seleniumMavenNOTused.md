#this selenium demo not using maven to build the project instead using ant.

# Introduction #

This project not use the Maven to build our project ,we use the ant to build the code .


# Details #

some disadvantage i had found using Maven to build selenium :
  * Network delay maybe caused the script run very slow;
  * some jar file has no maven project ,so you need to manually extract the jar file to be maven project;
  * Ant is very easy to build the script and understand the build process;
  * Cannot got know the dependency jar file easily from the project root;