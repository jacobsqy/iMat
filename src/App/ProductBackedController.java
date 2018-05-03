package App;

import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import java.util.ArrayList;
import java.util.List;

public class ProductBackedController {
    private final IMatDataHandler dataHandler = IMatDataHandler.getInstance();

    public List<Product> getProduct(){
        List<Product> products = dataHandler.getProducts();
        return products;
    }

    public ArrayList<String> getProductNames() {
        ArrayList<String> product = new ArrayList<>();
        for (Product item: getProduct()) {
            product.add(item.getName());
        }
        return product;
    }
}
