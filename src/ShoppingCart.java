import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart { // List to store products added to the cart.
    private ArrayList<Product> cartItems = new ArrayList<>(); // Each product in this list represents a unique product type in the cart.
    private List<Product> cart;
    private Map<Product, Integer> quantityMap; // A map to keep track of each product and its quantity in the cart.
    private double totalPrice = 0.0; // Total price of all products

    public ShoppingCart() { // Constructor
        cartItems = new ArrayList<>();
        quantityMap = new HashMap<>();
    }

    public void addtocart(Product product, int quantity) {
        if (!quantityMap.containsKey(product)) { // Add the product to the cart if it's not already in it.
            cartItems.add(product);
        }
        quantityMap.put(product, quantityMap.getOrDefault(product, 0) + quantity); // Update the quantity of the product in the cart.
    }


    public void removeItem(Product product) {
        int quantity = quantityMap.getOrDefault(product, 0);
        if (quantity > 1) {
            quantityMap.put(product, quantity - 1); //If the quantity is greater than one, it decrements the quantity.
        } else {
            quantityMap.remove(product); //  If the quantity is one, it removes the product from the cart entirely.
            cartItems.remove(product);
        }
        product.updateAvailableItems(quantity); // Update the available items of the product.
        getTotalPrice(); // Get the total price.
    }


    public List<Product> getCartItems() { //Returns the list of products added to the shopping cart.
        return cartItems;
    }

    public double getTotalPrice() {
        totalPrice = 0.0;  // Reset total price to 0 before calculating

        for (Map.Entry<Product, Integer> entry : quantityMap.entrySet()) { //Iterate over the entry set of the quantityMap.
            Product product = entry.getKey(); // Extract the product and quantity from the current map entry.
            Integer quantity = entry.getValue();
            totalPrice += product.getPrice() * quantity; // Calculate the total price for this product
        }
        return totalPrice;
    }

    public Map<Product, Integer> getQuantityMap() {
        return quantityMap;
    } // Return the quantityMap.

    public boolean hasSameCategoryProducts() {
        if (cartItems.size() < 2) {
            // If there are fewer than 2 items, can't have products from the same category
            return false;
        }

        // Get the product type of the first item in the cart.
        String firstCategory = cartItems.get(0).getProductType();

        for (int i = 1; i < cartItems.size(); i++) { // Compare each item's product type with the first item's product type.
            if (!firstCategory.equals(cartItems.get(i).getProductType())) {
                return false;
                // If any item's product type doesn't match the first item's product type, return false as not all products are from the same category.
            }
        }

        return true;
    }
}
