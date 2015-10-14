package cpd3314.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * Final Term Project for CPD-3314 Java Development I
 *
 * @author Kuldeep Singh
 */
public class CPD3314Project {

    static String id = "";
    static String getDate = null, getId = null, sort = null;

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        getDate = null;
        getId = null;
        sort = null;
        id = "";
        String format = "XML";
        int valueID = -1;
        String find = null;
        for (String arg : args) {
            if (arg.contains("-format=")) {
                // Splits the argument on the = sign, and assigns the right half to the string
                // For example, -format=XML, the format variable will have "XML"
                format = arg.split("=")[1];
            }
            if (arg.contains("-sort=")) {
                //Splits the argument on the = sign, and assigns the right half to the string
                //Store into a sort variable
                //sort will take the right part
                //For example -sort=A, sort variabe will store the "A".
                sort = arg.split("=")[1];
            }

            if (arg.contains("-getID=")) {
                int index = arg.indexOf("=");
                //Split the argument on the = sign, and assign the right half to an Integer
                //Convert the string into integer and store the particular id into valueID variable
                //For example -getID=10, valueId stores the value 10 as Integer by converting it
                valueID = Integer.parseInt(arg.substring(index + 1));
            }
            if (arg.contains("-getDate=")) {
                //Split the argument on the = sign, and assign the right half to an Integer
                //Store the particular Date into the gatDate variable
                //For exmple,-getDate=2013-03-30, the getdate will have "2013-03-30"
                getDate = arg.split("=")[1];
            }

            if (arg.contains("-find=")) {
                //Split the argument on the = sign, and assign the right half to an Integer
                //Store the particular String into find variable
                //For example,-find=Desk, the find variable will have "Desk"
                find = arg.split("=")[1];
            }
        }

        /**
         * serialization is simple to use and revolves around several
         * annotations an a single persister object used to read and write
         * objects to and from XML serializer is object of Serializer interface.
         * Creating a list,to store all the products in it by calling the method
         * getProductList().
         */
        Serializer serializer = new Persister();
        File source = new File("ORIGINALS.xml");
        Products obj = serializer.read(Products.class, source);
        List<Product> productList = obj.getProductList();
        System.out.println( productList.get(5).getName());
        //Check for an ID 
        //if valueID is not equal to -1 then return true
        //Creatig new Product list
        //Remove Any List Items Not ID from list
        //store into the Original list
        if (valueID != -1) {
            ArrayList<Product> list = new ArrayList<>();
            list.add(productList.get(valueID - 1));
            productList = list;

        }
        // Check for a Date 
        //getDate is not equal to null then retune true
        //Creatig new Product list
        //Remove Any List Items Not on Date
        //store into the Original list
        if (getDate != null) {
            List<Product> dateList = new ArrayList<>();

            for (Product date : productList) {
                if (date.getDateAdded().equals(getDate)) {

                    dateList.add(date);
                }
            }
            productList = dateList;
        }
        //Check for a Filter 
        //if find is not equal to null then return true
        //Creatig new Product list
        //Remove Any List Items that Don't Match Filter
        //store into the Original list
        if (find != null) {
            ArrayList<Product> list = new ArrayList<>();
            for (Product p1 : productList) {
                if (p1.getName().contains(find) || p1.getDescription().contains(find)) {
                    list.add(p1);
                }
            }
            productList = list;
        }
        // Check for Sorting 
        //Convert string argument into upper case
        //to check whether it contains right arugment or not
        //and Sort the List by A (means by name), I(I means ID), D (D means Date)
        if (sort != null) {

            if (sort.toUpperCase().contains("A")) {

                Collections.sort(productList, productName);

            } else if (sort.toUpperCase().contains("I")) {

                Collections.sort(productList, productId);
            } else if (sort.toUpperCase().contains("D")) {

                Collections.sort(productList, productDate);

            }
        }

        // Check for limits
        // Splits the argument on the = sign, and assigns the right half to the string
        // For example, -limit=10, the limits variable will have 10 by converting string into Integer
        int limits = productList.size();
        for (String arg : args) {
            if (arg.contains("-limit=")) {
                int index = arg.indexOf("=");
                limits = Integer.parseInt(arg.substring(index + 1));
            }
        }

        // This is the Render Step
        String fileName = "CPD3314";
        for (String arg : args) {
            if (arg.contains("-o=")) {
                int index = arg.indexOf("=");
                fileName = arg.substring(index + 1);
            }
        }

        //if argument conatins HTML, XML, JSON, SQL or YAML then
        //One of the switch case will run.
        switch (format) {
            case "HTML":
                formatHTML(productList, fileName, limits, sort);
                break;
            case "XML":
                formatXML(productList, fileName, limits, sort);
                break;
            case "SQL":
                formatSQL(productList, fileName, limits, sort);
                break;
            case "YAML":
                formatYAML(productList, fileName, limits, sort);
                break;
            case "JSON":
                formatJSON(productList, fileName, limits, sort);
                break;
        }

    }

    /**
     * formatHTML method takes below argument and write the html contents to
     * file HTML header is defined at the top of the file for read the list and
     * call the toHTML method to write into file using PrintWriter. Write the
     * footer at the bottom in the file
     *
     * @param productList
     * @param fileName
     * @param limits
     * @param sort
     * @throws Exception
     */
    public static void formatHTML(List<Product> productList, String fileName, int limits, String sort) throws Exception {
        try {

            PrintWriter htmlWriter;
            htmlWriter = new PrintWriter(fileName + ".html");
            htmlWriter.println("<!DOCTYPE html>");
            htmlWriter.println("<html>");
            htmlWriter.println("<head>");
            htmlWriter.println("</head>");
            htmlWriter.println("<body>");
            for (int i = 0; i < limits; i++) {

                htmlWriter.println(productList.get(i).toHTML());
            }
            htmlWriter.println("</body>");
            htmlWriter.println("</html>");
            htmlWriter.close();

        } catch (FileNotFoundException ex) {
            System.out.println("Exception in formatHTML method in main");
        }
    }

    /**
     * formatXML method format the XML file. get the list items and print into
     * the XML file.
     *
     * @param productList
     * @param fileName
     * @param limits
     * @param sort
     * @throws Exception
     */
    public static void formatXML(List<Product> productList, String fileName, int limits, String sort) throws Exception {

        try (PrintWriter xmlwriter = new PrintWriter(fileName + ".xml")) {
            xmlwriter.println("<products>");
            for (int i = 0; i < limits; i++) {

                xmlwriter.println(productList.get(i).toXML());
            }
            xmlwriter.print("</products>");
            xmlwriter.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Exeception block in XML format");
        }

    }

    /**
     * formatYAML method format the YAML file. get the list items and print into
     * the YAML file.
     *
     * @param productList
     * @param fileName
     * @param limits
     * @param sort
     */
    public static void formatYAML(List<Product> productList, String fileName, int limits, String sort) {

        try {
            try (PrintWriter ymalwriter = new PrintWriter(fileName + ".yaml")) {
                for (int i = 0; i < limits; i++) {
                    ymalwriter.println(productList.get(i).toYAML());
                }
                ymalwriter.print("---\n");
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Exeception block in Json format");
        }
    }

    /**
     * formatJSON method format the JSON file. get the list items and print into
     * the JSON file.
     *
     * @param productList
     * @param fileName
     * @param limits
     * @param sort
     */
    public static void formatJSON(List<Product> productList, String fileName, int limits, String sort) {
        try {
            try (PrintWriter jsonwriter = new PrintWriter(fileName + ".json")) {
                String output = "";
                output += "{ \"products\" : [ ";
                for (int i = 0; i < limits; i++) {
                    output += (productList.get(i).toJSON());
                    output += (",");
                }
                output = output.substring(0, output.length() - 1);
                output += ("] }");
                jsonwriter.print(output);
            }
        } catch (IOException e) {
            System.err.println("JSON Block Exception");
        }
    }

    /**
     * formatSQL method format the SQL file. get the list items and print into
     * the SQL file.
     *
     * @param productList
     * @param fileName
     * @param limits
     * @param sort
     */
    public static void formatSQL(List<Product> productList, String fileName, int limits, String sort) {

        try {

            PrintWriter sqlWriter = new PrintWriter(fileName + ".sql");
            {
                sqlWriter.println("CREATE TABLE Products (id INT, name VARCHAR(50),"
                        + " description TEXT, quantity INT, dateAdded DATE);");
                for (int i = 0; i < limits; i++) {
                    sqlWriter.println(productList.get(i).toSQL());
                }
                sqlWriter.close();
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Exception in SQL format method");
        }

    }
    /**
     * A comparison function, which imposes a total ordering on some collection
     * of objects. Comparators can be passed to a sort method (such as
     * Collections.sort or Arrays.sort) to allow precise control over the sort
     * order. one Product's ID is compared to another product's ID. return the
     * order one Product's name is compared to another product's name. return
     * the order
     */
    private static Comparator<Product> productName = new Comparator<Product>() {
        @Override
        public int compare(Product o1, Product o2) {
            return (o1.getName().compareTo(o2.getName()));
        }
    };
    /**
     * one Product's ID is compared to another product's ID using comparison
     * function return the order
     */
    private static Comparator<Product> productId = new Comparator<Product>() {
        @Override
        public int compare(Product o1, Product o2) {
            int id1 = o1.getId();
            int id2 = o2.getId();
            return id1 - id2;
        }
    };
    /**
     * one Product's productDate is compared to another productDate using
     * comparison function return the order
     */
    private static final Comparator<Product> productDate = new Comparator<Product>() {
        @Override
        public int compare(Product o1, Product o2) {
            return (o1.getDateAdded().compareTo(o2.getDateAdded()));
        }
    };

}
