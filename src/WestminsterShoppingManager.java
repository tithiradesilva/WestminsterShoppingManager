import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

// Defining Interface
interface ShoppingManager {
    void addProduct(Product product); // Method to add a new product
    void deleteProduct(String productId, int productType); // Method to delete a product
    void printProductList(); // Method to print the list
    void saveProductsToFile(String fileName); // Method to save the data to a file
    void loadProductsFromFile(String fileName); // Method to load products from a file
}

// Implementing class WestminsterShoppingManager from ShoppingManager
class  WestminsterShoppingManager implements ShoppingManager {

    // Main menu
    public static void MainMenu(){
        System.out.println("Westminster Shopping Manager Main Menu");
        System.out.println("---------------------------------");
        System.out.println("1. Add Products to the System.");
        System.out.println("2. Delete a product from the system.");
        System.out.println("3. Print the list of products.");
        System.out.println("4. Save data into a file.");
        System.out.println("5. load from text file.");
        System.out.println("6. Graphical User Interface (User Only).");
        System.out.println("7. Exit \n");
    }

    public static void main(String[] args) {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        MainMenu(); // Running the menu file

        while(true){
            Scanner input = new Scanner(System.in);
            System.out.print("\nEnter your option - "); //asks for user input
            int option = input.nextInt();

            switch(option){ // switch statement

                // When user adding a product.
                case 1 :
                    int typeChoice = 0;
                    while (typeChoice != 1 && typeChoice != 2) { // if the user not selects 1 or 2,
                        System.out.print("Enter 1 for Electronic and 2 for Clothing: ");
                        try {
                            typeChoice = input.nextInt();
                            if (typeChoice != 1 && typeChoice != 2) { // if the user not selects 1 and 2,
                                System.out.println("Invalid input. Please enter 1 or 2.");
                                continue;
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a number.");
                            input.next();
                            continue;
                        }
                    }

                    // asking product ID
                    System.out.print("Enter product ID: ");
                    String productId = input.next();

                    // asking product name
                    System.out.print("Enter product name: ");
                    String productName = input.next();

                    int availableItems = -1;
                    while (availableItems < 0) {
                        System.out.print("Enter available items: ");
                        try {
                            availableItems = input.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a number.");
                            input.next();
                        }
                    }

                    double price = -1.0;

                    // Loop until a valid price is entered.
                    while (price < 0.0) {
                        System.out.print("Enter price: ");
                        try {
                            price = input.nextDouble();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a number.");
                            input.next();
                        }
                    }

                    // if user selects 1 to add electronic product,
                    if (typeChoice == 1) {
                        String ProductType = "Electronic";

                        System.out.print("Enter brand: ");
                        String brand = input.next();

                        System.out.print("Enter warranty period: ");
                        int warrantyPeriod = input.nextInt();

                        // Creating an new electronic object
                        Electronic electronic = new Electronic(productId, productName, availableItems, price, ProductType, brand, warrantyPeriod);

                        System.out.println("New item added.");
                        shoppingManager.addProduct(electronic);
                    }

                    // if user selects 2 to add clothing product,
                    else if (typeChoice == 2) {
                        String ProductType = "Clothing";

                        System.out.print("Enter size: ");
                        String size = input.next();

                        String color = "";
                        while (true) {
                            System.out.print("Enter color: ");
                            color = input.next();
                            if (color.matches("^[a-zA-Z]+")) { // check whether the user has used string,
                                break;
                            } else {
                                System.out.println("Invalid input. Please enter a valid color (string).");
                            }
                        }

                        // Creating an new clothing object
                        Clothing clothing = new Clothing(productId, productName, availableItems, price, ProductType, size, color);

                        System.out.println("New item added.");
                        shoppingManager.addProduct(clothing);
                    }

                    else {
                        System.out.println("Invalid product type choice.");
                    }

                    break;

                case 2 :

                    int delChoice = 0;
                    while (delChoice != 1 && delChoice != 2) { // if the user not selects 1 or 2,
                        System.out.print("Enter 1 for Electronic and 2 for Clothing: ");
                        try {
                            delChoice = input.nextInt();
                            if (delChoice != 1 && delChoice != 2) { // if the user not selects 1 or 2,
                                System.out.println("Invalid input. Please enter 1 or 2.");
                                continue;
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a number.");
                            input.next();
                            continue;
                        }
                    }
                    System.out.print("Enter product ID to delete: ");
                    String deleteProductId = input.next(); // Sets deleting product.

                    shoppingManager.deleteProduct(deleteProductId, delChoice);
                    break;

                case 3 :
                    // Call the printProductList method of shoppingManager to display all products.
                    shoppingManager.printProductList();
                    break;

                case 4 :
                    // Save the current product list to a file.
                    shoppingManager.saveProductsToFile("Text Data.txt");
                    break;

                case 5 :
                    // Load data from a file.
                    shoppingManager.loadProductsFromFile("Text Data.txt");
                    break;

                case 6 :
                    // Display GUI.
                    System.out.print("Enter User Name: ");
                    String username = input.next();
                    System.out.print("Enter Password: ");
                    String password = input.next();
                    User newUser = new User(username, password);
                    boolean isNewUser = false;

                    if (!UserUtils.isUserExists(newUser)) {
                        System.out.println("New user. First purchase discount available.");
                        try {
                            UserUtils.saveUser(newUser);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        isNewUser = true;
                    }else {
                        System.out.println("User Already Exists!");
                    }

                    ArrayList<Product> productList = shoppingManager.getProductList();
                    GUI myGUI = new GUI(productList, newUser, isNewUser);
                    break;

                case 7:
                    System.out.println("Thank You for Using Westminster Shopping Manager.");
                    System.exit(0);
                    break;

                default:
                    // If an invalid option is entered, inform the user.
                    System.out.println("Invalid Option.");
                    break;
            }
        }
    }

    // Declaration of a list.
    private List<Product> productList;

    // Constructor for WestminsterShoppingManager list.
    public WestminsterShoppingManager() {
        this.productList = new ArrayList<>();
    }

    // Method to retrieve the list of products.
    public ArrayList<Product> getProductList() {
        return (ArrayList<Product>) productList;
    }

    // Method to add a product.
    @Override
    public void addProduct(Product product) {
        // Adding the product to the productList.
        productList.add(product);
    }

    // Method to delete a product.
    @Override
    public void deleteProduct(String productId, int productType) {
        if (productType == 1) { // If the product type is Electronic

            // Remove the product from productList if it's ID and type match
            productList.removeIf(product -> product.getProductID().equals(productId) && product.getProductType().equals("Electronic"));

            // Displays deleted product message.
            System.out.println("Product " + productId + " removed successfully from the system");
        } else if (productType == 2) { // If the product type is Clothing

            // Remove the product from productList if it's ID and type match
            productList.removeIf(product -> product.getProductID().equals(productId) && product.getProductType().equals("Clothing"));

            // Displays deleted product message.
            System.out.println("Product " + productId + " removed successfully from the system");
        }
    }

    // Method to print a products list.
    @Override
    public void printProductList() {
        for (Product product : productList) {
            if (product.getProductType().equals("Electronic")) { //  If the product is of type "Electronic".

                Electronic electronic = (Electronic) product;   // Cast the product to Electronic to access it's properties.

                System.out.println("\nProduct Type - " + product.getProductType() + "\n" + "Product ID - " + product.getProductID() + "\n" + "Product Name - "
                        + product.getProductName() + "\n" + "Products Available - " + product.getAvailableItems() + "\n" + "Product Price - " + product.getPrice()
                        + "\n" + "Product Brand - " + electronic.getBrand() + "\n" + "Product warranty period - " + electronic.getWarrantyPeriod() + "\n");
            }
            else if (product.getProductType().equals("Clothing")) { // If the product is of type "Clothing".

                Clothing clothing = (Clothing) product;  // Cast the product to Clothing to access it's properties.

                System.out.println("\nProduct Type - " + product.getProductType() + "\n" + "Product ID - " + product.getProductID() + "\n" + "Products Available - "
                        + product.getAvailableItems() + "\n" + "Product Price - " + product.getPrice()
                        + "\n" + "Product Size - " + clothing.getSize() + "\n" + "Product colour - " + clothing.getColour() + "\n");
            }
        }
    }

    // Method to save the product details into a file.
    @Override
    public void saveProductsToFile(String fileName) {
        try {
            // Create a FileWriter
            FileWriter textFile = new FileWriter(fileName);

            for (Product product : productList) {

                // if the product type is Electronic,
                if (product.getProductType().equals("Clothing")) {
                    Clothing clothing = (Clothing) product; // Cast the product to Clothing to access it's properties.
                    textFile.write("Product Type - " + product.getProductType() + "\n" + "Product ID - " + product.getProductID() + "\n" + "Products Available - "
                            + product.getAvailableItems() + "\n" + "Product Price - " + product.getPrice() + "\n" + "Product Size - " + clothing.getSize() + "\n"
                            + "Product colour - " + clothing.getColour() + "\n");
                    textFile.write("\n");
                }

                // if the product type is Electronic,
                else if (product.getProductType().equals("Electronic")) {
                    Electronic electronic = (Electronic) product;  // Cast the product to Electronic to access it's properties.
                    textFile.write("Product Type - " + product.getProductType() + "\n" + "Product ID - " + product.getProductID() + "\n" + "Product Name - "
                            + product.getProductName() + "\n" + "Products Available - " + product.getAvailableItems() + "\n" + "Product Price - " + product.getPrice()
                            + "\n" + "Product Brand - " + electronic.getBrand() + "\n" + "Product warranty period - " + electronic.getWarrantyPeriod() + "\n");
                    textFile.write("\n");
                }
            }

            textFile.close();
            System.out.println("Successfully wrote to the file.\n");

        }

        catch (IOException e) {  // Catch and handle IO exceptions.
            System.out.println("An error occurred.");
        }
    }

    @Override
    public void loadProductsFromFile(String fileName) {
        try (Scanner readFile = new Scanner(new File(fileName))) {
            // Use a delimiter to read the entire product block
            readFile.useDelimiter("\\n\\n");

            while (readFile.hasNext()) {
                String productBlock = readFile.next();

                // Extract the product type.
                String productType = extractValue(productBlock, "Product Type");

                // Extract the product ID.
                String productId = extractValue(productBlock, "Product ID");

                // Extract the product name.
                String productName = extractValue(productBlock, "Product Name");

                // Extract the number of available items.
                int availableItems = Integer.parseInt(extractValue(productBlock, "Products Available"));

                // Extract the product price.
                double price = Double.parseDouble(extractValue(productBlock, "Product Price"));

                Product product;

                // For Electronic type, extract brand and warranty period.
                if (productType.equals("Electronic")) {
                    String brand = extractValue(productBlock, "Product Brand");
                    int warrantyPeriod = Integer.parseInt(extractValue(productBlock, "Product warranty period"));

                    // Create a Electronic product
                    product = new Electronic(productId, productName, availableItems, price, productType, brand, warrantyPeriod);
                }

                // For Clothing type, extract size and color.
                else {
                    String size = extractValue(productBlock, "Product Size");
                    String color = extractValue(productBlock, "Product colour");

                    // Create a Clothing product
                    product = new Clothing(productId, productName, availableItems, price, productType, size, color);
                }

                addProduct(product);
            }

            System.out.println("Successfully loaded products from the file.");
        }

        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String extractValue(String block, String key) {
        // Use regular expression to extract the value based on the key
        String regex = key + " - (.+)";

        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);  // Compile the regular expression into a Pattern object
        java.util.regex.Matcher matcher = pattern.matcher(block); // Create a Matcher object that will match the given block against the pattern

        if (matcher.find()) { // Check if the pattern can find a match in the block
            return matcher.group(1).trim();
        }
        return ""; // Return an empty string
    }

    public boolean isProductIdExists(String productId) {
        // Check if shoppingList is null
        if (productList == null) {
            // Handle the case where shoppingList is not properly initialized
            return false;
        }
        // Iterate over shoppingList only if it's not null
        for (Product product : productList) {
            if (product.getProductID().equals(productId)) {
                return true; // Product ID already exists
            }
        }
        // If no match is found, return false
        return false; // Product ID does not exist
    }


    public ArrayList<Product> filterProductsByType(String productType) {

        // Initialize an ArrayList to hold the filtered products
        ArrayList<Product> filteredProducts = new ArrayList<>();

        for (Product product : productList) {

            // Check if the product type matches the specified type or if "All" is specified
            if (productType.equals("All") || product.getProductType().equals(productType)) {

                // If it matches, add the product to the filtered list
                filteredProducts.add(product);
            }
        }

        // Return the list of filtered products
        return filteredProducts;
    }
}