public class Product {
    private String name;
    private double price;
    private int stock;

    public Product(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public boolean reduceStock(int quantity) {
        if (quantity <= stock) {
            stock -= quantity;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return name + " - $" + price + " (Stock: " + stock + ")";
    }
}
public class Customer {
    private String name;
    private String email;

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return name + " (" + email + ")";
    }
}
public class OrderItem {
    private Product product;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return product.getName() + " x " + quantity + " = $" + getTotalPrice();
    }
}
import java.util.ArrayList;
import java.util.List;

public class Order {
    private static int orderCounter = 1;
    private int orderId;
    private Customer customer;
    private List<OrderItem> items;
    private boolean isSubmitted;

    public Order(Customer customer) {
        this.orderId = orderCounter++;
        this.customer = customer;
        this.items = new ArrayList<>();
        this.isSubmitted = false;
    }

    public int getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void addItem(OrderItem item) {
        if (!isSubmitted) {
            items.add(item);
        }
    }

    public double getTotalAmount() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public void submitOrder() {
        isSubmitted = true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: " + orderId + "\n");
        sb.append("Customer: " + customer + "\n");
        sb.append("Items:\n");
        for (OrderItem item : items) {
            sb.append("  " + item + "\n");
        }
        sb.append("Total Amount: $" + getTotalAmount() + "\n");
        sb.append("Order Status: " + (isSubmitted ? "Submitted" : "Not Submitted"));
        return sb.toString();
    }
}
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private List<Order> orders;

    public OrderService() {
        orders = new ArrayList<>();
    }

    public Order createOrder(Customer customer) {
        Order order = new Order(customer);
        orders.add(order);
        return order;
    }

    public boolean submitOrder(Order order) {
        if (!order.isSubmitted()) {
            order.submitOrder();
            return true;
        }
        return false;
    }

    public List<Order> getAllOrders() {
        return orders;
    }
}
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 创建商品
        Product product1 = new Product("Laptop", 1000.00, 5);
        Product product2 = new Product("Smartphone", 500.00, 10);
        Product product3 = new Product("Headphones", 150.00, 15);

        // 创建客户
        Customer customer1 = new Customer("Alice", "alice@example.com");
        Customer customer2 = new Customer("Bob", "bob@example.com");

        // 创建订单管理服务
        OrderService orderService = new OrderService();

        // 客户 Alice 创建订单
        Order order1 = orderService.createOrder(customer1);
        order1.addItem(new OrderItem(product1, 1));
        order1.addItem(new OrderItem(product2, 2));

        // 客户 Bob 创建订单
        Order order2 = orderService.createOrder(customer2);
        order2.addItem(new OrderItem(product3, 3));

        // 打印订单详情
        System.out.println("Orders Before Submission:");
        for (Order order : orderService.getAllOrders()) {
            System.out.println(order);
        }

        // 提交订单
        orderService.submitOrder(order1);
        orderService.submitOrder(order2);

        // 打印提交后的订单详情
        System.out.println("\nOrders After Submission:");
        for (Order order : orderService.getAllOrders()) {
            System.out.println(order);
        }
    }
}
