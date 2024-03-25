// A subclass of Product Class
public class Clothing extends Product {
    private String Size; // Declaring variables
    private String Colour;

    // Constructors of variable
    public Clothing(String ProductID, String ProductName, int AvailableItems, double Price, String ProductType, String Size, String Colour) {
        super(ProductID, ProductName, AvailableItems, Price, ProductType);
        this.Size = Size;
        this.Colour = Colour;
    }

    // Getter method for size
    public String getSize() {
        return Size;
    }

    // Setter method for size
    public void setSize(String size) {
        Size = size;
    }

    // Getter method for colour
    public String getColour() {
        return Colour;
    }

    // Setter method for colour
    public void setColour(String colour) {
        Colour = colour;
    }
}
