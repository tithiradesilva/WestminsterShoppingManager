// Creating Abstract Class
abstract class Product {

    // Declaring variables
    private String ProductID;
    private String ProductName;
    private int AvailableItems;
    private double Price;
    private String ProductType;

    // Constructors of variable
    public Product(String ProductID, String ProductName, int AvailableItems, double Price, String ProductType) {
        this.ProductID = ProductID;
        this.ProductName = ProductName;
        this.AvailableItems = AvailableItems;
        this.Price = Price;
        this.ProductType = ProductType;
    }

    // Getter method for Product ID
    public String getProductID() {
        return ProductID;
    }

    // Setter method for Product ID
    public void setProductID(String productID) {
        ProductID = productID;
    }

    // Getter method for Product Name
    public String getProductName() {
        return ProductName;
    }

    // Setter method for Product Name
    public void setProductName(String productName) {
        ProductName = productName;
    }

    // Getter method for Available Items
    public int getAvailableItems() {
        return AvailableItems;
    }

    // Setter method for Available Items
    public void setAvailableItems(int availableItems) {
        AvailableItems = availableItems;
    }

    // Getter method for Price
    public double getPrice() {
        return Price;
    }

    // Setter method for Price
    public void setPrice(double price) {
        Price = price;
    }

    // Getter method for Get Product Type
    public String getProductType(){
        return ProductType;
    }

    // Method for update available items
    public void updateAvailableItems(int quantityRemoved) {
        AvailableItems += quantityRemoved;
    }
}


