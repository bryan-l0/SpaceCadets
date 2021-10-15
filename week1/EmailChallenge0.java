import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.System;
import java.net.URL;
import java.net.URLConnection;
/**
 * This solution aims to solve the problem without any external libraries. However, this approach isn't very flexible,
 * and has to loop through the acquired webpage to find the line with the name.
 *
 * There are some optimizations that are obviously available, i.e. not combing through returned URLs that are clearly
 * not valid. Possibly there is a built-in java library which allows for Xpath queries?
 *
 * @author Bryan Lo
 */
public class EmailChallenge0 {
    /**
     * Method to concatenate email ID and directory.
     * @param emailInput - the inputted email ID through the terminal
     * @return - URL of the email ID's webpage
     */
    private static String emailConstructor(String emailInput) {
        String linkBase = "https://www.ecs.soton.ac.uk/people/";
        return linkBase + emailInput;
    }
    /**
     * Main method of class. Takes email ID from command line, and generates the URL to be scraped by the URL library.
     * Returned data is simply combed through and split to isolate the name field.
     * @throws IOException - BufferedReader readLine method exception
     */
    public static void main(String[] args) throws IOException {
        // Prompts the user for input, then takes the input as a string.
        System.out.println("Please input the email ID!");
        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
        String emailId = consoleInput.readLine();

        // Uses the emailConstructor method to concatenate the person's URL
        String URL = emailConstructor(emailId);

        // Creates URL object in order to fetch the data, then opens connection to the provided URL
        URL sotonLink = new URL(URL);
        URLConnection sotonConnection = sotonLink.openConnection();
        InputStream websiteData = sotonConnection.getInputStream();

        // Converts input stream into strings
        BufferedReader sotonInfo = new BufferedReader(new InputStreamReader(websiteData));
        String newLine = sotonInfo.readLine();

        // While loop to check each line for the stringified HTML tag for the name, then prints it if found
        String name = "";
        while (newLine != null) {
            if (newLine.contains("property=\"name\">")) {
                name = newLine.split("property=\"name\">",2)[1].split("<",2)[0];
                System.out.println(name);
                break;
            } else {
                newLine = sotonInfo.readLine();
            }
        }

        // Returns error if the name is not found
        if (name.equals("")) {
            System.out.println("email id was not valid");
        }
    }
}
