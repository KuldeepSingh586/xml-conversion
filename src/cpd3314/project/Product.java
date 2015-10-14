package cpd3314.project;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author Kuldeep Singh
 */
@Root
public class Product {

    @Element
    private int id;
    @Element
    private String name = "";
    @Element
    private String description = "";
    @Element
    private int quantity;
    @Element
    private String dateAdded = "";

    /**
     * constructor having five arguments
     *
     * @param id
     * @param name
     * @param description
     * @param quantity
     * @param dateAdded
     */
    Product(int id, String name, String description, int quantity, String dateAdded) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.dateAdded = dateAdded;
    }

    /**
     * default constructor
     */
    Product() {

    }

    /**
     * set method for set the value for id
     *
     * @param id
     */
    public void setId(int id) {

        this.id = id;
    }

    /**
     * get the id here
     *
     * @return -id
     */
    public int getId() {
        return id;
    }

    /**
     * setter for name
     *
     * @param Name
     */
    public void setName(String Name) {
        this.name = Name;
    }

    /**
     * get the name here
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * setter for description
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * getDescription method to get the Description
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * setter for quantity
     *
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * getQuantity method get the value of quantity
     *
     * @return quantity
     */
    public int getQuantity() {

        return quantity;
    }

    /**
     * setter for dateAdded
     *
     * @param dateAdded
     */
    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    /**
     * getDateAdded method will return the date
     *
     * @return dateAdded
     */
    public String getDateAdded() {

        return dateAdded;
    }

    /**
     * In toXML method, get the name, ID, description , date print on particular
     * place and write into the file
     *
     * @return - the XML String
     */
    public String toXML() {
        String string = "";
        string += ("    <product>\n");

        string += ("\t<id>");
        string += getId();
        string += ("</id>\n");

        string += ("\t<name>");
        string += getName();
        string += ("</name>\n");

        string += ("\t<description>");
        string += getDescription();
        string += ("</description>\n");

        string += ("\t<quantity>");
        string += getQuantity();
        string += ("</quantity>\n");

        string += ("\t<dateAdded>");
        string += getDateAdded();
        string += ("</dateAdded>\n");
        string += ("    </product>");

        return string;

    }

    /**
     * In toYAML method, get the name, ID, description , date print on
     * particular place and write into the file
     *
     * @return - the YAML String
     */
    public String toYAML() {
        String string = "";
        string += ("---\n");
        string += ("dateAdded: " + "'" + getDateAdded() + "'");
        string += ("\ndescription: " + getDescription());
        string += ("\nid: " + getId());
        string += ("\nname: " + getName());
        string += ("\nquantity: " + getQuantity());

        return string;
    }

    /**
     * In toJSON method, get the name, ID, description , date print on
     * particular place and write into the file
     *
     * @return - the JSON String
     */
    public String toJSON() {
        String string;
        String jsonString = "";
        try {
            StringWriter out = new StringWriter();
            JSONObject obj = new JSONObject();
            LinkedHashMap m1 = new LinkedHashMap();
            Map m2 = new HashMap();
            LinkedList l1 = new LinkedList();
            m1.put("quantity", getQuantity());
            m1.put("name", getName());
            m1.put("description", getDescription());
            m1.put("id", getId());
            m1.put("dateAdded", getDateAdded());
            obj.writeJSONString(out);
            jsonString = JSONValue.toJSONString(m1);
        } catch (IOException e) {

        }
        return jsonString;
    }

    /**
     * In toSQL method, get the name, ID, description , date print on particular
     * place and write into the file
     *
     * @return - the SQL String
     */
    public String toSQL() {
        String string;
        string = ("INSERT INTO Products VALUES (");
        string += ("" + getId() + ",");
        string += (" \"" + getName() + "\",");
        string += (" \"" + getDescription() + "\",");
        string += (" " + getQuantity() + ",");
        string += (" \"" + getDateAdded() + "\");");
        return string;
    }

    /**
     * In toHTML method, get the name, ID, description , date print on
     * particular place and write into the file
     *
     * @return - the HTML String
     */
    public String toHTML() {
        String string[] = {"<div class=\"product\">\n",
            "\n\t<h1>" + getName() + "</h1>",
            "\n\t<p>ID: " + getId() + "</p>",
            "\n\t<p>" + getDescription() + "</p>",
            "\n\t<p>Quantity: " + getQuantity() + "</p>",
            "\n\t<p>Added: " + getDateAdded() + "</p>",
            "\n</div>"};

        String str = Arrays.toString(string).replaceAll(">[ \t\n]+", ">").trim();
        String result = str.replace("[", "").replace("]", "").replace(">,", ">");
        return result;

    }

}
