package covidcases;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author Anthony Jones
 */
public class CovidCases {

    public static void main(String[] args) {
        //Stores the url of the page to scrape
        final String url = "https://usafacts.org/visualizations/coronavirus-covid-19-spread-map/state/michigan";

        try {
            //Obtains and stores the HTML for the website
            final Document document = Jsoup.connect(url).get();
            
            //Creates file for data to be stored
            File covidFile = new File("michiganCovidCases.txt");
            PrintWriter covidList = new PrintWriter(covidFile);
            
            //Prints the county name, # of cases and deaths from each row using a enhanced for loop to a text document
            //note that tags may change due to website updates
            for (Element row : document.select("table.MuiTable-root-44.jss41 tr")) {
               
                //Skips the table row if it's empty
                if (row.select("a").text().equals("") || row.select("td:nth-of-type(1)").text().equals("") || row.select("td:nth-of-type(2)").text().equals("")) {
                    continue;
                }

                final String county = row.select("a").text();
                final String cases = row.select("td:nth-of-type(1)").text();
                final String deaths = row.select("td:nth-of-type(2)").text();
                
                
                covidList.println(county + ":\t\n Cases " + cases + "\t\n Deaths " + deaths);             
            }
            covidList.close();
            
            //creating scanner objects to read file and to take user input
            Scanner scan = new Scanner(covidFile); 
            Scanner userInput = new Scanner(System.in);
            
            //Takes user input and searches the file for specific county, and then it prints out the data
            System.out.println("Which county would you like to search for in regards to Covid-19 cases?");
            String countyName = userInput.nextLine();
            countyName = countyName.substring(0,1).toUpperCase() + countyName.substring(1);
            String line = "";
            while (scan.hasNextLine()){
                line = scan.nextLine();
                if (line.startsWith(countyName)){
                    System.out.println(line);
                    for (int i = 0; i < 2; ++i){
                        line = scan.nextLine();
                        System.out.println(line);
                    } 
                }
            }
            scan.close();
          
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
