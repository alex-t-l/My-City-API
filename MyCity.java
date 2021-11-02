import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import com.google.gson.*;
import java.net.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
public class MyCity {

  private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }

  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException, InterruptedException {
    URL url2 = new URL(url);

    HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
    conn.setRequestMethod("GET");
    conn.connect();
    
    //Getting the response code
    int responsecode = conn.getResponseCode();
    
    int max = 5, n = 1, time = 250;
    if (responsecode < 200 && responsecode > 299) {
        throw new RuntimeException("HttpResponseCode: " + responsecode);
    }
    //Backoff
    else if(responsecode > 499 && responsecode < 600 || responsecode == 429){
        System.out.println("Attempting backoff");
        do{
            conn = (HttpURLConnection) url2.openConnection();
            System.out.println("Retrying connection, attempt #" + n);
            Thread.sleep(time);
            time *= 2;
            n++;
        }
        while(n <= max && responsecode > 499 && responsecode < 600 || responsecode == 429);
    }
    InputStream is;
    try {
    is = new URL(url).openStream();
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String jsonText = readAll(rd);
      JSONObject json = new JSONObject(jsonText);
      is.close();
      return json;
    } catch(Exception e){
        System.out.println("Invalid input");
        System.exit(0);
    }
    return null;
  }

  public static void main(String[] args) throws IOException, JSONException, InterruptedException {
    StringBuilder name = new StringBuilder();
    //Case where we have a city name with spaces like "New York City" or "Palo Alto"
    if(args.length == 0){
        System.out.println("Invalid input");
    System.exit(0);
    }
    if (args.length > 1) {
        for (int i = 0; i < args.length; i++) {
            if (i != args.length - 1) {
                name.append(args[i] + " ");
            } else {
                name.append(args[i]);
            }
        }
    } else {
       name.append(args[0]);
    }
    //JSONObject weather = readJsonFromUrl("http://httpstat.us/500"); //backoff logic test
    //JsonElement c = readJsonFromUrl2("http://httpstat.us/500");
    JSONObject weather = readJsonFromUrl("http://api.openweathermap.org/data/2.5/weather?q="+name.toString()+"&units=imperial&appid=4d51f613beff882afdb57e69d55e2daf");
    //System.out.println(weather.toString());
  JsonElement c = readJsonFromUrl2("http://getnearbycities.geobytes.com/GetNearbyCities?radius=100&longitude=" + weather.getJSONObject("coord").get("lon") + "&latitude=" + weather.getJSONObject("coord").get("lat"));
    JsonArray cities = c.getAsJsonArray();
  System.out.println(outputWeather(weather));
  System.out.println("Nearby cities: ");
  int i = 0;
  for(JsonElement j: cities){
      if(i > 0){
    String[] s = j.toString().split(",");
    //System.out.println(j.toString());
    System.out.println(s[1].substring(1, s[1].length()-1) + ", " + s[2].substring(1, s[2].length()-1));
      }
      i++;
  }
    }

    public static String outputWeather(JSONObject json) throws JSONException{
        StringBuilder sb = new StringBuilder();
        try{
        sb.append("Weather report for " + json.get("name") + ", " + json.getJSONObject("sys").get("country"));
        }
        catch(JSONException e){
          sb.append("Weather report for " + json.get("name"));
        }
        JSONArray arr = json.getJSONArray("weather");
        sb.append("\nThe conditions are " + arr.getJSONObject(0).getString("main").toLowerCase() + ", " + arr.getJSONObject(0).getString("description"));
        sb.append("\nIt feels like " + json.getJSONObject("main").get("feels_like") + " degrees, but it's actually " + json.getJSONObject("main").get("temp") + " degrees.");
        sb.append("\nThe low is " + json.getJSONObject("main").get("temp_min") + " degrees, and the high is " + json.getJSONObject("main").get("temp_max") + " degrees.");
        sb.append("\nThe wind is blowing at a speed of " + json.getJSONObject("wind").get("speed") + " mph, and a gust of " + json.getJSONObject("wind").get("gust") + " mph.");
        return sb.toString();
    }

    public static JsonElement readJsonFromUrl2(String url) throws IOException, JSONException, InterruptedException {
        URL url2 = new URL(url);

        HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        
        //Getting the response code
        int responsecode = conn.getResponseCode();
        //System.out.println(responsecode);
        int max = 5, n = 1, time = 250;
        if (responsecode < 200 && responsecode > 299) {
            throw new RuntimeException("HttpResponseCode: " + responsecode);
        }
        //Backoff
        else if(responsecode > 499 && responsecode < 600 || responsecode == 429){
            System.out.println("Attempting backoff");
            do{
                conn = (HttpURLConnection) url2.openConnection();
                System.out.println("Retrying connection, attempt #" + n);
                Thread.sleep(time);
                time *= 2;
                n++;
                responsecode = conn.getResponseCode();
            }
            while(n <= max && responsecode > 499 && responsecode < 600 || responsecode == 429);
            if(responsecode > 499 && responsecode < 600 || responsecode == 429){
                System.out.println("Backoff failed, exiting");
                System.exit(0);
            }
        }
        InputStream is = new URL(url).openStream();
        try {
          BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
          //String jsonText = readAll(rd);
          return JsonParser.parseReader(rd);
        } finally {
          is.close();
        }
      }
    

  }

