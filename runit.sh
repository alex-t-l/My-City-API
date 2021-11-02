javac -cp ".:$(pwd)/gson-2.8.8.jar.:$(pwd)/java-json.jar" MyCity.java
java -classpath "./:./gson-2.8.8.jar.:$(pwd)/java-json.jar" MyCity New York