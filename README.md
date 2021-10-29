My City API

Application
Application takes in an input of a city name (spaces supported such as “New York” or “Palo Alto.
And GETS JSON file of a city’s weather information as from the Weather API. If this doesn’t work, it will attempt exponential backoff to reconnect to the server (in case the api is down.) After several attempts if the connection is still not established, program will stop. If it works, it will retrieve and parse the information to the console. Then it will try to connect to the Nearby Cities API and parse the information for that. That is really it, it is a simple application that demonstrates knowledge of communicating t the cloud using REST APIs and deserializing JSON.
Design
 ![IMG_B6E33B9AC78F-1](https://user-images.githubusercontent.com/74996590/139378966-2998b941-be3b-4c60-8471-7be94c42be8a.jpeg)

Usage
1.	UNZIP IT
2.	DRAG THE FOLDER INTO A LINUX BASED TERMINAL
3.	GO ON TERMINAL, MAKE SURE YOU’RE IN THE SAME FOLDER AS ./RUNIT.SH
4.	TYPE IN ‘chmod +rwx runit.sh’.
5.	GO INTO RUNIT.SH AND TYPE IN THE DESIRED CITY AFTER ‘MyCity’.
6.	SAVE ALL AND TYPE IN ‘runit.sh’ TO RUN THE PROGRAM.
7.	It will output the City’s weather info and some nearby cities for valid inputs.
```
Also works if you run locally on Java 
Just do javac MyCity.java
java MyCity {city name}

Example output:
$ javac MyCity.java
$ java MyCity Seattle
Weather report for Seattle, US
The conditions are rain, moderate rain
It feels like 50.76 degrees, but it's actually 51.53 degrees.
The low is 48.52 degrees, and the high is 54.79 degrees.
The wind is blowing at a speed of 1.99 mph, and a gust of 5.99 mph.
Nearby cities: 
Medina, WA
Mercer Island, WA
Bellevue, WA
Kirkland, WA
Rollingbay, WA
Seahurst, WA
Bainbridge Island, WA
Southworth, WA
Shoreline, WA
Manchester, WA
```
