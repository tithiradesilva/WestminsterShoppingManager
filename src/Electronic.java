// A subclass of Product Class
public class Electronic extends Product {

    // Declaring variables
    private String Brand;
    private int WarrantyPeriod;

    // Constructors of variable
    public Electronic(String ProductID, String ProductName, int AvailableItems, double Price, String ProductType,String Brand, int WarrantyPeriod) {
        super(ProductID, ProductName, AvailableItems, Price, ProductType);
        this.Brand = Brand;
        this.WarrantyPeriod = WarrantyPeriod;
    }

    // Getter method for Brand
    public String getBrand() {
        return Brand;
    }

    // Setter method for Brand
    public void setBrand(String brand) {
        Brand = brand;
    }

    // Getter method for Warranty Period
    public int getWarrantyPeriod() {
        return WarrantyPeriod;
    }

    // Setter method for Warranty Period
    public void setWarrantyPeriod(int warrantyPeriod) {
        WarrantyPeriod = warrantyPeriod;
    }
}
