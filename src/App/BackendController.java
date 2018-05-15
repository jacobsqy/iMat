package App;

import se.chalmers.cse.dat216.project.*;
import se.chalmers.cse.dat216.project.util.IOUtilities;

import java.util.ArrayList;
import java.util.List;

public class BackendController {

    // Backend
    public static IMatDataHandler backend = IMatDataHandler.getInstance();

    // Products

    public static List<Product> getProduct(){
        List<Product> products = backend.getProducts();
        return products;
    }

    public static ArrayList<String> getProductNames() {
        ArrayList<String> product = new ArrayList<>();
        for (Product item: getProduct()) {
            product.add(item.getName());
        }
        return product;
    }

    public static List<Product> getProductByCategory(String s) {

        List<Product> l = new ArrayList<Product>();
        switch (s) {
            case "Favoriter":
                l.addAll(backend.favorites());
                break;
            case "Bröd":
                l.addAll(backend.getProducts(ProductCategory.BREAD));
                break;
            case "Frukter":
                l.addAll(backend.getProducts(ProductCategory.CITRUS_FRUIT));
                l.addAll(backend.getProducts(ProductCategory.EXOTIC_FRUIT));
                l.addAll(backend.getProducts(ProductCategory.ROOT_VEGETABLE));
                l.addAll(backend.getProducts(ProductCategory.FRUIT));
                l.addAll(backend.getProducts(ProductCategory.MELONS));
                l.addAll(backend.getProducts(ProductCategory.BERRY));
                break;
            case "Grönsakser & Örtkryddor":
                l.addAll(backend.getProducts(ProductCategory.VEGETABLE_FRUIT));
                l.addAll(backend.getProducts(ProductCategory.CABBAGE));
                l.addAll(backend.getProducts(ProductCategory.HERB));
                l.addAll(backend.getProducts(ProductCategory.POD));
                break;
            case "Pasta, Potatis & Ris":
                l.addAll(backend.getProducts(ProductCategory.PASTA));
                l.addAll(backend.getProducts(ProductCategory.POTATO_RICE));
                break;
            case "Drycker":
                l.addAll(backend.getProducts(ProductCategory.HOT_DRINKS));
                l.addAll(backend.getProducts(ProductCategory.COLD_DRINKS));
                break;
            case "Fisk & Kött":
                l.addAll(backend.getProducts(ProductCategory.FISH));
                l.addAll(backend.getProducts(ProductCategory.MEAT));
                break;
            case "Mejeriprodukter":
                l.addAll(backend.getProducts(ProductCategory.DAIRIES));
                break;
            case "Mjöl, Socker, Salt":
                l.addAll(backend.getProducts(ProductCategory.FLOUR_SUGAR_SALT));
                break;
            case "Nötter och frön":
                l.addAll(backend.getProducts(ProductCategory.NUTS_AND_SEEDS));
                break;
            case "Sötsaker":
                l.addAll(backend.getProducts(ProductCategory.SWEET));
                break;
        }
        return l;
    }
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

    public static boolean isFavorite(Product p) {
        return IMatDataHandler.getInstance().isFavorite(p);
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

    public static void emptyShoppingCart() {
        IMatDataHandler.getInstance().getShoppingCart().clear();
    }

    // Misc

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
