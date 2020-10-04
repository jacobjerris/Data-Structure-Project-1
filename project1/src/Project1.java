import java.io.*;
import java.util.Scanner;

/*
    COP 3530: Project 1 - Array Searches and Sorts
    <p>
    @Author: Jacob Jerris
    @Version: 9/9/20
 */


/*
    Class Project1 houses the main method, and is the base for the entire program.
    <p>
    Takes in the Country objects, and calls upon the sorting methods that are in the Project1 class.
 */
public class Project1 {
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        Scanner csvFileName = new Scanner(System.in);
        Scanner keyTest = new Scanner(System.in);
        Scanner binaryKeyTest = new Scanner(System.in);

        String line = "";


        //Asks user for CSV file name, searches then loads the entire file
        System.out.print("Please enter the CSV file name: ");
        String csvFile = csvFileName.next();

        int total = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while((line = br.readLine()) != null) {
                total++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print("\nTotal records read: " + (total - 1) + "\n\n");

        //Creates an array of countries, but subtracts one to skip the line at the top.
        Country[] countries = new Country[total - 1];



        int indx = 0;
        try (BufferedReader br2 = new BufferedReader(new FileReader(csvFile))) {
            while((line = br2.readLine()) != null) {
                if(indx > 0) {
                    countries[(indx - 1)] = new Country(line.split(","));
                }
                indx++;
            }
        } catch (IOException e) {
                e.printStackTrace();
            }


        for(;;) {
            System.out.println("1. Print a countries report\n2. Sort by Name\n3. Sort by COVID-19 CFR\n4. Sort by GDP per capita\n5. Find and print a given country\n6. Quit");
            System.out.println("Enter your choice: ");
            int userOption = input.nextInt();
            if(userOption == 1) {
                printReport(countries);
            }else if(userOption == 2) {
                sortViaInsertion(countries);
            }else if(userOption == 3) {
                sortViaCFR(countries);
            }else if(userOption == 4) {
                sortPerCapita(countries);
            }else if(userOption == 5) {
                String key;
                String binaryKey;

                System.out.print("Please enter the Country you'd like to search for: ");
                if(countries[0].getName().equals("Afghanistan")) {
                    binaryKey = binaryKeyTest.next();
                    Country foundCountry = findCountryUsingBinary(countries, binaryKey);
                    printCountry(foundCountry);
                } else {
                    key = keyTest.next();
                    Country foundCountry = findCountryUsingSequential(countries, key);
                    printCountry(foundCountry);
                }

            }else if(userOption == 6) {
                System.out.print("Quit.\n");
                break;
            }else{
                System.out.println("Incorrect Input");
            }
        }
    }

    /*
        findCountryUsingBinary takes in the array of countries, and a key for what it needs to search for. This sorts
         using the sorting method of Binary Search, which I found on the PDF's that were provided to use from Dr. Liu.
         <p>
         Depending on the number that it is currently comparing, it either increments the upper or lower values. Once
          it has found the country, it returns its index, else it returns null and tells the user that it has not
          been found.
     */
    public static Country findCountryUsingBinary(Country[] countries, String binaryKey) {
        int lower = 0;
        int upper = countries.length - 1;
        int mid;

        while(lower <= upper ) {
            mid = (lower + upper) / 2;
            if(countries[mid].getName().equals(binaryKey)) {
                return countries[mid];
            } else if (countries[mid].getName().compareTo(binaryKey) > 0) {
                upper = mid - 1;
            } else {
                lower = mid + 1;
            }
        }
        return null;
    }

    /*
        findCountryUsingSequential takes in the array of countries, and a key for what it needs to search for. This sorts
        using the sorting method of Sequential Search, which I found on the PDF's that were provided to use from Dr. Liu.
        <p>
        Depending on the number that it is currently comparing, it either increments the upper or lower values. Once
        it has found the country, it returns its index, else it returns null and tells the user that it has not
        been found.
    */
    public static Country findCountryUsingSequential(Country[] values, String name) {
        for(int j = 0; j < values.length; j++) {
            if(values[j].getName().equals(name)) {
                return values[j];
            }
        }
        return null;
    }

    /*
        sortViaCFR takes in the array of countries.
        <p>
        This sorts using the sorting method of Selection Search, which I found on the PDF's that were provided to
        use from Dr. Liu.
        <p>
        Using selection sort it sorts the values from lowest to highest. Once it's complete it reprompts the user for
         a selection, and the user can choose to print out the sorted list.
    */
    public static void sortViaCFR(Country[] values) {
        for(int outer = 0; outer < values.length -1; outer++) {
            int lowest = outer;
            for(int inner = outer + 1; inner < values.length; inner++) {
                if(values[inner].getCFR() < values[lowest].getCFR()) {
                    lowest = inner;
                }
            }
            if (lowest != outer) {
                Country temp = values[lowest];
                values[lowest] = values[outer];
                values[outer] = temp;
            }
        }
    }

    /*
        findCountryUsingSequential takes in the array of countries, and a key for what it needs to search for. This sorts
        using the sorting method of Sequential Search, which I found on the PDF's that were provided to use from Dr. Liu.
        <p>
        Depending on the number that it is currently comparing, it either increments the upper or lower values. Once
        it has found the country, it returns its index, else it returns null and tells the user that it has not
        been found.
    */
    public static void sortViaInsertion(Country[] values) {
        int inner;
        int outer;

        for(outer = 0; outer < values.length; outer++) {
            Country temp = values[outer];
            inner = outer - 1;

            while((inner >= 0) && (values[inner].getName().compareTo(temp.getName()) > 0)) {
                values[inner + 1] = values[inner];
                inner--;
            }
            values[inner+1] = temp;
        }
    }

    /*
        findCountryUsingSequential takes in the array of countries, and a key for what it needs to search for. This sorts
        using the sorting method of Sequential Search, which I found on the PDF's that were provided to use from Dr. Liu.
        <p>
        Depending on the number that it is currently comparing, it either increments the upper or lower values. Once
        it has found the country, it returns its index, else it returns null and tells the user that it has not
        been found.
    */
    public static void sortPerCapita(Country[] values) {
        for(int outer = 0; outer < values.length - 1; outer++) {
            for(int inner = values.length -1; inner > outer; inner--) {
                if(values[inner].getGDPPerCap() < values[inner - 1].getGDPPerCap()) {
                    Country temp = values[inner];
                    values[inner] = values[inner - 1];
                    values[inner -1] = temp;
                }
            }
        }
    }

    /*
        findCountryUsingSequential takes in the array of countries, and a key for what it needs to search for. This sorts
        using the sorting method of Sequential Search, which I found on the PDF's that were provided to use from Dr. Liu.
        <p>
        Depending on the number that it is currently comparing, it either increments the upper or lower values. Once
        it has found the country, it returns its index, else it returns null and tells the user that it has not
        been found.
    */
    public static void printReport(Country[] values) {
        reportHead();
        for(int i = 0; i < (values.length - 1); i++) {
            reportRow(values[i]);
        }
    }

    /*
        findCountryUsingSequential takes in the array of countries, and a key for what it needs to search for. This sorts
        using the sorting method of Sequential Search, which I found on the PDF's that were provided to use from Dr. Liu.
        <p>
        Depending on the number that it is currently comparing, it either increments the upper or lower values. Once
        it has found the country, it returns its index, else it returns null and tells the user that it has not
        been found.
    */
    public static void printCountry(Country value) {
        if(value == null) {
            System.out.println("Could not find Country specified\n");
        } else {
            reportHead();
            reportRow(value);
        }
    }

    /*
        findCountryUsingSequential takes in the array of countries, and a key for what it needs to search for. This sorts
        using the sorting method of Sequential Search, which I found on the PDF's that were provided to use from Dr. Liu.
        <p>
        Depending on the number that it is currently comparing, it either increments the upper or lower values. Once
        it has found the country, it returns its index, else it returns null and tells the user that it has not
        been found.
    */
    public static void reportHead() {
        String a[] = {"Name", "Capitol", "Population", "GDP", "Cases", "Deaths"};

        for(int i = 0; i < (a.length); i++) {
            System.out.print(a[i] + "\t");
        }
        System.out.print("\n");
    }

    /*
        findCountryUsingSequential takes in the array of countries, and a key for what it needs to search for. This sorts
        using the sorting method of Sequential Search, which I found on the PDF's that were provided to use from Dr. Liu.
        <p>
        Depending on the number that it is currently comparing, it either increments the upper or lower values. Once
        it has found the country, it returns its index, else it returns null and tells the user that it has not
        been found.
    */
    public static void reportRow(Country value) {
        System.out.println(value.getName() + "\t" + value.getCapitol() + "\t" + value.getPopulation() + "\t" +
        value.getGDP() + "\t" + value.getCovidCases() + "\t" + value.getCovidDeaths());
    }
}


/*
    Class Country houses the constructors for the objects, and is the creator for the Country objects.
    <p>
    It houses the creation of Country objects.
 */
class Country {
    private String name;
    private String capitol;
    private double population;
    private double GDP;
    private int covidCases;
    private double covidDeaths;

    /*
        This constructor creates Country objects and assigns the values to certain values declared above.
     */
    public Country(String name, String capitol, int population, int GDP, int covidCases, int covidDeaths) {
        this.capitol = capitol;
        this.name = name;
        this.population = population;
        this.GDP = GDP;
        this.covidCases = covidCases;
        this.covidDeaths = covidDeaths;
    }

    /*
        This constructor creates Country objects and assigns the values to certain index's of the array.
     */
    public Country(String[] values) {
        this.capitol = values[1];
        this.name = values[0];
        this.population = Double.parseDouble(values[2]);
        this.GDP = Double.parseDouble(values[3]);
        this.covidCases = Integer.parseInt(values[4]);
        this.covidDeaths = Double.parseDouble(values[5]);
    }


    /*
        This takes in nothing
        <p>
        This returns whatever the covidCases has been set to.
     */
    public int getCovidCases() {
        return covidCases;
    }

    /*
        This returns whatever the covidDeaths has been set to.
    */
    public double getCovidDeaths() {
        return covidDeaths;
    }

    /*
       This takes in nothing
        <p>
       This returns whatever the GDP has been set to.
    */
    public double getGDP() {
        return GDP;
    }

    /*
       This takes in nothing
        <p>
       This returns whatever the population has been set to.
    */
    public double getPopulation() {
        return population;
    }

    /*
        This takes in nothing
        <p>
        This returns whatever the capitol has been set to.
    */
    public String getCapitol() {
        return capitol;
    }

    /*
        This takes in nothing
        <p>
        This returns whatever the name has been set to.
    */
    public String getName() {
        return name;
    }

    /*
       This takes in nothing
        <p>
       This returns whatever the result of covidCases/covidDeaths has been set to, if the covidCases are greater than
        0. If not, it returns 0.
    */
    public double getCFR() {
        if(covidCases > 0) {
            return (covidCases/covidDeaths);
        } else {
            return 0;
        }
    }

    /*
        This takes in nothing
        <p>
        This returns whatever the result of GDP/population is.
    */
    public double getGDPPerCap() {
        return (GDP/population);
    }

    /*
        This takes in a String called capitol and sets that to the object parameter
     */
    public void setCapitol(String capitol) {
        this.capitol = capitol;
    }

    /*
        This takes in a int called covidCases and sets that to the object parameter
     */
    public void setCovidCases(int covidCases) {
        this.covidCases = covidCases;
    }

    /*
        This takes in a double called covidDeaths and sets that to the object parameter
     */
    public void setCovidDeaths(double covidDeaths) {
        this.covidDeaths = covidDeaths;
    }

    /*
        This takes in a double called GDP and sets that to the object parameter
     */
    public void setGDP(double GDP) {
        this.GDP = GDP;
    }

    /*
        This takes in a String called name and sets that to the object parameter
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
        This takes in a double called population and sets that to the object parameter
     */
    public void setPopulation(double population) {
        this.population = population;
    }

}
