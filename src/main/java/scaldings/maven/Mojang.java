package scaldings.maven;

import java.util.HashMap;
import java.util.Map;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Mojang 
{
    public static String getUUIDFromUsername(String username) {return (String) getJSONObject("https://api.mojang.com/users/profiles/minecraft/" + username).get("id");}

    public static JSONArray getNameHistory(String username)
    {
        String UUID = getUUIDFromUsername(username);
        JSONArray arr = getJSONArray("https://api.mojang.com/user/profiles/" + UUID + "/names");
        Map<String, Long> history = new HashMap<>();
        arr.forEach
        (o ->
            {
                JSONObject obj = (JSONObject) o;
                history.put((String) obj.get("name"), obj.get("changedToAt") == null ? 0 : Long.parseLong(obj.get("changedToAt").toString()));
            }
        );
        
        JSONArray array = new JSONArray();
        for (String str : history.keySet())
        {
            array.add(str);
        }
        return array;
    }

    private static JSONObject getJSONObject(final String url)
    {
        JSONObject object;
        try
        {
            object = (JSONObject) new JSONParser().parse(Unirest.get(url).asString().getBody());
            final String error = (String) (object.get("error"));
            if (error != null)
            {
                switch (error)
                {
                    case "IllegalArgumentException":
                        throw new IllegalArgumentException((String) object.get("errorMessage"));
                    default:
                        throw new RuntimeException(error);
                }
            }
        }
        catch (ParseException | UnirestException e)
        {
            throw new RuntimeException(e);
        }
        return object;
    }

    private static JSONArray getJSONArray(String url)
    {
        JSONArray array;

        try
        {
            array = (JSONArray) new JSONParser().parse(Unirest.get(url).asString().getBody());

        }
        catch (ParseException | UnirestException e)
        {
            throw new RuntimeException(e);
        }

        return array;
    }
}