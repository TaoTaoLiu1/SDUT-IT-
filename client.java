public class Customer {
    private String name;
    private String email;
    private String phone;

    public Customer(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "Customer [name=" + name + ", email=" + email + ", phone=" + phone + "]";
    }
}
public class ServiceRequest {
    private int requestId;
    private Customer customer;
    private String issue;
    private boolean resolved;

    public ServiceRequest(int requestId, Customer customer, String issue) {
        this.requestId = requestId;
        this.customer = customer;
        this.issue = issue;
        this.resolved = false;
    }

    public int getRequestId() {
        return requestId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getIssue() {
        return issue;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void resolve() {
        this.resolved = true;
    }

    @Override
    public String toString() {
        return "ServiceRequest [requestId=" + requestId + ", customer=" + customer + ", issue=" + issue + ", resolved=" + resolved + "]";
    }
}
public class Feedback {
    private Customer customer;
    private String comments;
    private int rating;

    public Feedback(Customer customer, String comments, int rating) {
        this.customer = customer;
        this.comments = comments;
        this.rating = rating;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getComments() {
        return comments;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Feedback [customer=" + customer + ", comments=" + comments + ", rating=" + rating + "]";
    }
}
import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private List<ServiceRequest> requests;
    private List<Feedback> feedbacks;
    private int requestCounter;

    public CustomerService() {
        requests = new ArrayList<>();
        feedbacks = new ArrayList<>();
        requestCounter = 1;
    }

    // 创建服务请求
    public ServiceRequest createRequest(Customer customer, String issue) {
        ServiceRequest request = new ServiceRequest(requestCounter++, customer, issue);
        requests.add(request);
        return request;
    }

    // 处理服务请求
    public boolean resolveRequest(int requestId) {
        for (ServiceRequest request : requests) {
            if (request.getRequestId() == requestId && !request.isResolved()) {
                request.resolve();
                return true;
            }
        }
        return false;
    }

    // 提交反馈
    public void submitFeedback(Customer customer, String comments, int rating) {
        Feedback feedback = new Feedback(customer, comments, rating);
        feedbacks.add(feedback);
    }

    // 获取所有请求
    public List<ServiceRequest> getAllRequests() {
        return requests;
    }

    // 获取所有反馈
    public List<Feedback> getAllFeedbacks() {
        return feedbacks;
    }
}
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CustomerService service = new CustomerService();

        // 创建客户
        Customer customer1 = new Customer("Alice", "alice@example.com", "1234567890");
        Customer customer2 = new Customer("Bob", "bob@example.com", "9876543210");

        // 创建服务请求
        ServiceRequest request1 = service.createRequest(customer1, "Cannot access account");
        ServiceRequest request2 = service.createRequest(customer2, "Payment not processed");

        System.out.println("Service Requests:");
        for (ServiceRequest request : service.getAllRequests()) {
            System.out.println(request);
        }

        // 处理服务请求
        service.resolveRequest(request1.getRequestId());
        service.resolveRequest(request2.getRequestId());

        System.out.println("\nResolved Service Requests:");
        for (ServiceRequest request : service.getAllRequests()) {
            System.out.println(request);
        }

        // 提交客户反馈
        service.submitFeedback(customer1, "Great service, issue resolved quickly.", 5);
        service.submitFeedback(customer2, "Payment issue took too long to resolve.", 3);

        System.out.println("\nCustomer Feedback:");
        for (Feedback feedback : service.getAllFeedbacks()) {
            System.out.println(feedback);
        }
    }
}
