package App;

import se.chalmers.cse.dat216.project.*;
import se.chalmers.cse.dat216.project.util.IOUtilities;

import java.util.ArrayList;
import java.util.List;

public class BackendController {
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    // Favorites
    public void addAsFavorite(Product p) {
        iMatDataHandler.addFavorite(p);
    }

    public List<Product> getFavorites() {
        return iMatDataHandler.favorites();
    }

    public void removeAsFavorite(Product p) {
        iMatDataHandler.removeFavorite(p);
    }

    //Shoppingcart
    public void addToCart(Product p) {
        if (cartContains(p)) {
            addProductToShoppingItem(p);
        } else {
            iMatDataHandler.getShoppingCart().addItem(new ShoppingItem(p));
        }
    }
    //removes an entire item (not just a product!) from the ShoppingCart
    //compare to removeFromCart(Product)
    public void removeFromCart(ShoppingItem itemToRemove) {
        List<ShoppingItem> list = iMatDataHandler.getShoppingCart().getItems();
        for (ShoppingItem shoppingItem : list) {
            if (shoppingItem.getProduct().getName() == itemToRemove.getProduct().getName()) {

            }
        }
    }

    private boolean cartContains(Product p) {
        List<ShoppingItem> list = iMatDataHandler.getShoppingCart().getItems();
        for (ShoppingItem shoppingItem : list) {
            if (shoppingItem.getProduct().getName() == p.getName()) {
                return true;
            }
        }
        return false;
    }

    private void addProductToShoppingItem(Product p) {
        List<ShoppingItem> list = iMatDataHandler.getShoppingCart().getItems();
        for (ShoppingItem shoppingItem : list) {
            if (shoppingItem.getProduct().getName() == p.getName()) {
                shoppingItem.setAmount(shoppingItem.getAmount() + 1);
            }
        }
    }
}
