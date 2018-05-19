package App;

import se.chalmers.cse.dat216.project.*;

import java.util.ArrayList;
import java.util.List;

public class BackendController {

    // Backend
    public static IMatDataHandler backend = IMatDataHandler.getInstance();

    // Products

    /**
     *  hämtar all produkter från txt filen och lägger dem i en list
     * @return produkt list
     */
    public static List<Product> getProduct(){
        List<Product> products = backend.getProducts();
        return products;
    }

    /**
     * hämtar produkt namn  i en lista
     * @return produkt list med bara namn
     */
    public static ArrayList<String> getProductNames() {
        ArrayList<String> product = new ArrayList<>();
        for (Product item: getProduct()) {
            product.add(item.getName());
        }
        return product;
    }

    /**
     * hämtar all produkter som ligger i samma katagoriet
     * @param s katagori namn
     * @return produkt list som ligger i samma katagoriet
     */
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
            case "Grönsakser":
                l.addAll(backend.getProducts(ProductCategory.VEGETABLE_FRUIT));
                l.addAll(backend.getProducts(ProductCategory.CABBAGE));
                l.addAll(backend.getProducts(ProductCategory.POD));
                break;
            case "Örter":
                l.addAll(backend.getProducts(ProductCategory.HERB));
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
            case "Skafferi":
                l.addAll(backend.getProducts(ProductCategory.FLOUR_SUGAR_SALT));
                break;
            case "Nötter & Frön":
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

    public static double getTotalPrice() {
        return IMatDataHandler.getInstance().getShoppingCart().getTotal();
    }

    public static double getTotalProductAmount() {
        double amount = 0;
        for (ShoppingItem shoppingItem : IMatDataHandler.getInstance().getShoppingCart().getItems()) {
            amount += shoppingItem.getAmount();
        }
        return amount;
    }

    public static void addToCart(Product p) {
        if (cartContains(p)) {
            addProductToShoppingItem(p);
        } else {
            IMatDataHandler.getInstance().getShoppingCart().addItem(new ShoppingItem(p));
        }
    }

    public static void addToCart(Product p, int amount) {
        if (amount < 2) {
            addToCart(p);
        } else if (cartContains(p)) {
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
        ShoppingItem shoppingItem;
        for (int i = 0; i < list.size(); i++) {
            shoppingItem = list.get(i);
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

    // Customer info
    public static Customer getCustomer() {
        return IMatDataHandler.getInstance().getCustomer();
    }
}
