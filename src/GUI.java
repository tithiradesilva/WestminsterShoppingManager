import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GUI extends JFrame {
    private JTable productTable; // Table to display products.
    private TableRowSorter<TableModel> rowSorter; // TableRowSorter for sorting table rows.
    private JPanel detailsPanel; // Panel to display details about selected products and totals.
    private Product items; // The currently selected product.
    private JLabel totalPriceLabel; // Labels for displaying total prices and discounts.
    private JLabel totalLabel = new JLabel("Total: $0.0");; // Shopping cart instance to manage products selected by the user.
    private JLabel categoryDiscLabel;
    private JLabel finalTotalLabel;
    private User currentUser; // Current user information.
    private boolean isNewUser; //check if the user is new.
    private JLabel firstPurchaseDiscLabel; // Labels for displaying total prices and discounts.
    private ShoppingCart shoppingCart = new ShoppingCart();


    public GUI(ArrayList<Product> productList, User newUser, boolean isNewUser) {
        totalPriceLabel= new JLabel(); // Set up the basic properties of the JFrame.
        setTitle("Westminster Shopping Manager");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 900);
        this.currentUser = currentUser;
        this.isNewUser = isNewUser;

        // Initialize labels for display information
        totalPriceLabel = new JLabel(); // Label for displaying total price
        totalLabel = new JLabel(); // Label for displaying the subtotal
        firstPurchaseDiscLabel = new JLabel(); // Label for displaying discount for first purchase
        categoryDiscLabel = new JLabel(); // Label for displaying discount based on product category
        finalTotalLabel = new JLabel(); // Label for displaying the final total price after discounts

        // Create the table with column headers
        String[] columnHeaders = {"Product ID", "Name", "Category", "Price","Available Items", "Info"};

        // Initialize the table model with column headers
        DefaultTableModel tableModel = new DefaultTableModel(columnHeaders, 0);
        productTable = new JTable(tableModel); // Create a table

        JTableHeader header = productTable.getTableHeader(); // Customize the table header
        header.setFont(new Font("Arial", Font.BOLD, 15)); // Set the font of the table header

        JScrollPane scrollPane = new JScrollPane(productTable); // Add the table to a scroll pane
        add(scrollPane, BorderLayout.CENTER); // Add the scroll pane to the center of the GUI

        detailsPanel = new JPanel(); // Initialize a panel to display detailed information
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS)); // Set the layout of the details panel to a vertical stack

        add(detailsPanel, BorderLayout.SOUTH); // Add the details panel to the bottom.


        // Populate the table with product information
        displayProducts(productList);

        productTable.getSelectionModel().addListSelectionListener(e -> { // Add a selection listener to the table's selection model
            if (!e.getValueIsAdjusting()) { // if the selection event is not in a stable state,
                int selectedRow = productTable.getSelectedRow(); // Call a method to display details based on the selected row
                displayGetters(selectedRow); // This method shows the details of the product which is selected in the GUI

            }
        });



        //making the dropdown list
        String[] categories = {"All", "Electronic", "Clothing"}; //setting the combobox
        JComboBox<String> categoryComboBox = new JComboBox<>(categories);
        // Create a JComboBox (dropdown list) and initialize it with the category options
        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // This listener responds to user selections in the dropdown
                filterTableByCategory((String) categoryComboBox.getSelectedItem());
                // When an action is performed, we call the filter Table By Category method
            }
        });

        // Initialize a JPanel
        JPanel panel = new JPanel();
        panel.add(new JLabel("Select Product Category:")); // Add a label to the panel
        panel.add(categoryComboBox); // Add the previously created categoryComboBox to the panel
        add(panel, BorderLayout.NORTH); // Add the panel to the frame, positioning it at the top (North)


        JButton addToCartButton = new JButton("Add to Cart"); // Create a button
        addToCartButton.setAlignmentX(Component.CENTER_ALIGNMENT);  // Align the button to the center
        panel.add(Box.createHorizontalStrut(280)); // Add some vertical space
        panel.add(addToCartButton); // Add the button to the panel
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // Get the index of the selected row in the product table
                int selectedRow = productTable.getSelectedRow(); // Check if a valid row is selected
                if (selectedRow >= 0 && selectedRow < productList.size()) {
                    items = productList.get(selectedRow);
                    // Get quantity from user
                    String quantityString = JOptionPane.showInputDialog(GUI.this, "Enter Quantity:");
                    try {
                        int quantity = Integer.parseInt(quantityString);
                        int availableItems = items.getAvailableItems();
                        if (quantity <= availableItems) {
                            items.setAvailableItems(availableItems - quantity);
                            shoppingCart.addtocart(items, quantity);
                            displayProducts(productList);
                            JOptionPane.showMessageDialog(GUI.this, "Product added to cart!");
                        } else {
                            JOptionPane.showMessageDialog(GUI.this, "Please enter a valid quantity.");
                        }
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(GUI.this, "Please enter a numeric value.");
                    }
                } else {
                    JOptionPane.showMessageDialog(GUI.this, "Please select a product before adding to the cart.");
                }
            }
        });

        JButton shoppingCartButton = new JButton("Shopping Cart"); // Create a button labeled "Shopping Cart"
        panel.add(Box.createVerticalStrut(20));//to add space between the dropdown and the button
        panel.add(shoppingCartButton); // Add the shopping cart button to the panel
        shoppingCartButton.addActionListener(new ActionListener() {       // Add an ActionListener to the shopping cart button
            @Override
            public void actionPerformed(ActionEvent e) {
                displayShoppingCart(); // Call the method when the button is clicked
            }
        });

        // Initialize the TableRowSorter
        rowSorter = new TableRowSorter<>(productTable.getModel()); // Initialize a TableRowSorter for the productTable
        productTable.setRowSorter(rowSorter);

        setVisible(true); // Make the GUI frame visible to the user
    }

    private void displayProducts(List<Product> productList) { // Set the row sorter to the product table
        DefaultTableModel tableModel = (DefaultTableModel) productTable.getModel(); // This enables sorting functionality on the table's columns
        tableModel.setRowCount(0); // Clear existing rows

        for (Product product : productList) {
            Object[] rowData = {
                    product.getProductID(),
                    product.getProductName(),
                    product.getProductType(),
                    product.getPrice(),
                    product.getAvailableItems(),
                    getProductInfo(product)
            };

            // Get the number of available items for this product
            int availableItems = product.getAvailableItems();
            tableModel.addRow(rowData); // Add the row data to the table model

            int rowIndex = tableModel.getRowCount() - 1; // Get the index of the row that was just added

            // Check if the product has 3 or fewer available items.
            if (availableItems <= 3) {
                for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++) {
                    productTable.getColumnModel().getColumn(columnIndex).setCellRenderer(new CustomTableCellRenderer(productList));
                    // Set a custom cell renderer for low stock items
                }
            }
        }
    }

    private void displayShoppingCart() {
        JFrame cartFrame = new JFrame("Shopping Cart"); // Create a new frame for the shopping cart
        cartFrame.setSize(500, 500);  // Set the size of the frame

        // Column headers for the shopping cart table
        String[] cartColumnHeaders = {"Product", "Quantity", "Price"};
        DefaultTableModel cartTableModel = new DefaultTableModel(cartColumnHeaders, 0);
        JTable cartTable = new JTable(cartTableModel); // Create a table using this model

        JScrollPane cartScrollPane = new JScrollPane(cartTable);
        cartFrame.add(cartScrollPane, BorderLayout.CENTER);

        JButton removeButton = new JButton("Remove Selected");  // Create a button for removing selected items from the cart

        removeButton.addActionListener(new ActionListener() { // Add an action listener to the remove button
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = cartTable.getSelectedRow();
                if (selectedRow >= 0) {
                    Product selectedProduct = shoppingCart.getCartItems().get(selectedRow);
                    shoppingCart.removeItem(selectedProduct);
                    updateCartTable(cartTableModel, totalLabel, firstPurchaseDiscLabel, categoryDiscLabel, finalTotalLabel);
                    updateTotalPriceLabel();
                } else {
                    JOptionPane.showMessageDialog(cartFrame, "Please select an item to remove.");
                }
            }
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(removeButton);
        cartFrame.add(buttonsPanel, BorderLayout.NORTH);



        // Panel for total price and discounts
        JPanel totalsPanel = new JPanel(); // Create a panel for displaying buttons
        totalsPanel.setLayout(new BoxLayout(totalsPanel, BoxLayout.Y_AXIS));

        // Create labels for displaying the total price, discounts, and final total
        JLabel totalLabel = new JLabel("Total: $0.0");
        JLabel firstPurchaseDiscountLabel = new JLabel("First Purchase Discount (10%): -$0.0");
        JLabel categoryDiscountLabel = new JLabel("Three Items in Same Category Discount (20%): -$0.0");
        JLabel finalTotalLabel = new JLabel("Final Total: $0.0");

        totalsPanel.add(totalLabel);
        totalsPanel.add(firstPurchaseDiscountLabel);
        totalsPanel.add(categoryDiscountLabel);
        totalsPanel.add(finalTotalLabel);

        cartFrame.add(totalsPanel, BorderLayout.SOUTH);

        // Populate the cart table with items and calculate totals and discounts
        updateCartTable(cartTableModel, totalLabel, firstPurchaseDiscountLabel, categoryDiscountLabel, finalTotalLabel);

        cartFrame.setVisible(true);
    }

    // Method to filter the table based on the selected category
    private void filterTableByCategory(String category) {
        if ("All".equals(category)) {
            rowSorter.setRowFilter(null); // Show all rows
        }
        else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)^" + category + "$", 2)); // Filter based on category column
        }
    }

    // Method to display getters of the selected product in the detailsPanel
    private void displayGetters(int selectedRow) {
        detailsPanel.removeAll(); // Clear existing details
        detailsPanel.add(new JLabel("Details of Selected Product:"));

        // Check if the selected row is valid
        if (selectedRow >= 0 && selectedRow < productTable.getRowCount()) {
            // Get the index of the selected row in the model
            int modelIndex = productTable.convertRowIndexToModel(selectedRow);

            // Get the product from the filtered list
            DefaultTableModel tableModel = (DefaultTableModel) productTable.getModel();
            Object[] rowData = new Object[tableModel.getColumnCount()];
            for (int i = 0; i < rowData.length; i++) {
                rowData[i] = tableModel.getValueAt(modelIndex, i);
            }

            // Add details based on the type of product
            detailsPanel.add(new JLabel("Product ID: " + rowData[0]));
            detailsPanel.add(new JLabel("Product Name: " + rowData[1]));
            detailsPanel.add(new JLabel("Category: " + rowData[2]));
            detailsPanel.add(new JLabel("Price: " + rowData[3]));
            detailsPanel.add(new JLabel("Available Items: " + rowData[4]));

            //// Display additional information based on the product category
            if ("Electronic".equals(rowData[2])) {
                detailsPanel.add(new JLabel("Brand: " + rowData[5].toString().split(",")[0]));
                detailsPanel.add(new JLabel("Warranty Period: " + rowData[5].toString().split(",")[1]));
            } else if ("Clothing".equals(rowData[2])) {
                detailsPanel.add(new JLabel("Size: " + rowData[5].toString().split(",")[0]));
                detailsPanel.add(new JLabel("Colour: " + rowData[5].toString().split(",")[1]));
            }

            // Refresh the detailsPanel
            detailsPanel.revalidate();
            detailsPanel.repaint();
        }


    }

    private String getProductInfo(Product product) { // Returns a string with specific information based on product type
        if (product instanceof Electronic) {
            Electronic electronics = (Electronic) product;
            return ( electronics.getBrand()+", "+electronics.getWarrantyPeriod() + " Week(s)");
        } else if (product instanceof Clothing) {
            Clothing clothing = (Clothing) product;
            return( clothing.getSize() +", "+clothing.getColour());
        }
        return ""; // Return empty string if not Electronics or Clothing
    }

    private void updateCartTable(DefaultTableModel cartTableModel, JLabel totalLabel, JLabel firstPurchaseDiscountLabel, JLabel categoryDiscountLabel, JLabel finalTotalLabel) {
        // Clear the existing rows in the shopping cart table
        cartTableModel.setRowCount(0);
        double total = 0;
        for (Map.Entry<Product, Integer> entry : shoppingCart.getQuantityMap().entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();
            double totalPrice = product.getPrice() * quantity;
            total += totalPrice;

            Object[] cartRowData = {
                    product.getProductName(), quantity, totalPrice
            };
            cartTableModel.addRow(cartRowData);
        }

        double firstPurchaseDiscount = 0; // Implement logic to determine if the user is making their first purchase
        if (isNewUser) {
            firstPurchaseDiscount = total * 0.1; // Assuming a 10% discount for the first purchase
        }
        double categoryDiscount = 0;
        if (shoppingCart.hasSameCategoryProducts()) {
            categoryDiscount = total * 0.2; // Assuming a 20% discount for three items in the same category
        }
        double finalTotal = total - firstPurchaseDiscount - categoryDiscount;

        totalLabel.setText("Total: $" + String.format("%.2f", total));
        firstPurchaseDiscountLabel.setText("First Purchase Discount (10%): -$" + String.format("%.2f", firstPurchaseDiscount));
        categoryDiscountLabel.setText("Three Items in Same Category Discount (20%): -$" + String.format("%.2f", categoryDiscount));
        finalTotalLabel.setText("Final Total: $" + String.format("%.2f", finalTotal));

    }

    private void updateTotalPriceLabel() {
        double totalPrice = shoppingCart.getTotalPrice(); // Calculate the total price of all items currently in the shopping cart.
        totalPriceLabel.setText("Total Price: $" + String.format("%.2f", totalPrice));
        // Set the text of the totalPriceLabel to display the total price.
    }
}

class CustomTableCellRenderer extends DefaultTableCellRenderer { // List of products to determine the styling of each cell.
    private List<Product> productList;

    public CustomTableCellRenderer(java.util.List<Product> productList) {  //constructor
        this.productList = productList;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        // Default rendering of the cell.
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Customization based on product availability.
        if (row < productList.size()){
            Product product = productList.get(row);
            if (product.getAvailableItems()<=3){      // If the product has 3 or fewer items available, set the background to red and text to white.
                component.setBackground(Color.red);
                component.setForeground(Color.white);
            }else{
                component.setBackground(Color.white);
                component.setForeground(Color.BLACK);
            }
        }
        return component;
    }
}
