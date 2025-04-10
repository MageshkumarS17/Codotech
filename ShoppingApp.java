import java.awt.*;
import javax.swing.*;

public class ShoppingApp extends JFrame {
    private final DefaultListModel<Product> storeModel = new DefaultListModel<>();
    private final DefaultListModel<Product> cartModel = new DefaultListModel<>();
    private final ShoppingCart cart = new ShoppingCart();
    private final JLabel totalLabel = new JLabel("Total: $0.00");

    public ShoppingApp() {
        setTitle("Online Shopping Cart");
        setSize(800, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2));

        // Store panel
        JList<Product> storeList = new JList<>(storeModel);
        JButton addToCartBtn = new JButton("Add to Cart");
        JPanel storePanel = new JPanel(new BorderLayout());
        storePanel.add(new JLabel("🛍️ Store Products", SwingConstants.CENTER), BorderLayout.NORTH);
        storePanel.add(new JScrollPane(storeList), BorderLayout.CENTER);
        storePanel.add(addToCartBtn, BorderLayout.SOUTH);

        // Cart panel
        JList<Product> cartList = new JList<>(cartModel);
        JButton removeBtn = new JButton("Remove from Cart");
        JButton payBtn = new JButton("Pay Now");
        JPanel cartPanel = new JPanel(new BorderLayout());
        JPanel cartBottom = new JPanel(new GridLayout(2, 1));
        cartBottom.add(removeBtn);
        cartBottom.add(payBtn);

        cartPanel.add(new JLabel("🛒 Your Cart", SwingConstants.CENTER), BorderLayout.NORTH);
        cartPanel.add(new JScrollPane(cartList), BorderLayout.CENTER);
        cartPanel.add(totalLabel, BorderLayout.SOUTH);
        cartPanel.add(cartBottom, BorderLayout.EAST);

        // Add panels
        add(storePanel);
        add(cartPanel);

        // Sample Products
        storeModel.addElement(new Product("Laptop", 799.99, "Powerful laptop"));
        storeModel.addElement(new Product("Phone", 499.99, "Android smartphone"));
        storeModel.addElement(new Product("Headphones", 149.99, "Noise-cancelling headphones"));

        // Button actions
        addToCartBtn.addActionListener(e -> {
            Product selected = storeList.getSelectedValue();
            if (selected != null) {
                cart.addProduct(selected);
                cartModel.addElement(selected);
                updateTotal();
            }
        });

        removeBtn.addActionListener(e -> {
            Product selected = cartList.getSelectedValue();
            if (selected != null) {
                cart.removeProduct(selected);
                cartModel.removeElement(selected);
                updateTotal();
            }
        });

        payBtn.addActionListener(e -> {
            double total = cart.getTotal();
            if (total == 0) {
                JOptionPane.showMessageDialog(this, "Your cart is empty!");
            } else {
                JOptionPane.showMessageDialog(this, "Payment of $" + total + " successful!");
                cart.clearCart();
                cartModel.clear();
                updateTotal();
            }
        });

        setVisible(true);
    }

    private void updateTotal() {
        totalLabel.setText("Total: $" + String.format("%.2f", cart.getTotal()));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ShoppingApp::new);
    }
}
