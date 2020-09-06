package scaldings.maven;

import java.util.Iterator;
import java.util.Scanner;

import org.json.simple.JSONArray;

public class API
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        print("\nWelcome to the Mojang API,\n enter a username you want to find information about: ");
        String username = scanner.nextLine();
        scanner.close();

        print("\nUsername: " + username);
        print("\nUUID: " + Mojang.getUUIDFromUsername(username));
        print("\nName history: ");

        JSONArray names = Mojang.getNameHistory(username);
        Iterator<String> iterator = names.iterator();
        while(iterator.hasNext()) {print("\n    " + iterator.next());}
    }

    private static void print(Object obj) {System.out.print(obj);}
}
