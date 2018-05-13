package App;

import se.chalmers.cse.dat216.project.*;
import se.chalmers.cse.dat216.project.util.IOUtilities;

import java.util.ArrayList;
import java.util.List;

public class BackendController {

    // Favorites
    public static void addAsFavorite(Product p) {
        IMatDataHandler.getInstance().addFavorite(p);
    }

    public static List<Product> getFavorites() {
        return IMatDataHandler.getInstance().favorites();
    }

    public static void removeAsFavorite(Product p) {
        IMatDataHandler.getInstance().removeFavorite(p);
    }

    //Shoppingcart
    public static List<ShoppingItem> getShoppingItems() {
        return IMatDataHandler.getInstance().getShoppingCart().getItems();
    }

    public static void addToCart(Product p) {
        if (cartContains(p)) {
            addProductToShoppingItem(p);
        } else {
            IMatDataHandler.getInstance().getShoppingCart().addItem(new ShoppingItem(p));
        }
    }

    public static void addToCart(Product p, int amount) {
        if (amount < 2) addToCart(p);
        if (cartContains(p)) {
            for (int i = 0; i < amount; i++)
                addProductToShoppingItem(p);
        } else {
            IMatDataHandler.getInstance().getShoppingCart().addItem(new ShoppingItem(p));
            addToCart(p, amount - 1);
        }
    }
    //removes an entire item (not just a product!) from the ShoppingCart
    //compare to removeFromCart(Product)
    public static void removeFromCart(ShoppingItem itemToRemove) {
        List<ShoppingItem> list = IMatDataHandler.getInstance().getShoppingCart().getItems();
        for (ShoppingItem shoppingItem : list) {
            if (shoppingItem.getProduct().getName().equals(itemToRemove.getProduct().getName())) {
                IMatDataHandler.getInstance().getShoppingCart().removeItem(itemToRemove);
            }
        }
    }

    public static void removeFromCart(Product productToRemove){
        List <ShoppingItem> list = IMatDataHandler.getInstance().getShoppingCart().getItems();
        for (ShoppingItem shoppingItem : list){
            if (shoppingItem.getProduct().getName().equals(productToRemove.getName())){
                if (shoppingItem.getAmount() > 1){
                    shoppingItem.setAmount(shoppingItem.getAmount() -1);
                }
                else {
                    IMatDataHandler.getInstance().getShoppingCart().removeItem(shoppingItem);
                }
            }
        }
    }

    private static boolean cartContains(Product p) {
        List<ShoppingItem> list = IMatDataHandler.getInstance().getShoppingCart().getItems();
        for (ShoppingItem shoppingItem : list) {
            if (shoppingItem.getProduct().getName().equals(p.getName())) {
                return true;
            }
        }
        return false;
    }

    private static void addProductToShoppingItem(Product p) {
        List<ShoppingItem> list = IMatDataHandler.getInstance().getShoppingCart().getItems();
        for (ShoppingItem shoppingItem : list) {
            if (shoppingItem.getProduct().getName().equals(p.getName())) {
                shoppingItem.setAmount(shoppingItem.getAmount() + 1);
            }
        }
    }
}
