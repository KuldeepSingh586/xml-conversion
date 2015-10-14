/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpd3314.project;

import java.util.List;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 *
 * @author Kuldeep
 */
@Root
public class Products {

    /**
     * creating the list of products
     */
    @ElementList(inline = true, entry = "product")
    private List<Product> productList;

    /**
     * getProductList method returns the products of list.
     *
     * @return productList
     */
    public List<Product> getProductList() {
        return productList;
    }

    /**
     * pass the argument in setProductList method and set the list
     *
     * @param productList
     */
    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

}
