@RestController
@RequestMapping("/payment")
public class PaymentController {

// 模拟订单与支付状态, key是订单号，value是支付状态  
private final Map<String, Boolean> orderPaymentStatus = new HashMap<>();  

// 模拟账户余额（实际应该是数据库操作）  
private final Map<String, Double> userBalance = new HashMap<>();  

// 初始化模拟数据  
public PaymentController() {  
    userBalance.put("user1", 1000.0);  
    userBalance.put("user2", 200.0);  
    orderPaymentStatus.put("order123", false);  
    orderPaymentStatus.put("order124", false);  
}  

// 支付接口  
@PostMapping("/pay")  
public String pay(  
        @RequestParam String username,  
        @RequestParam String orderId,  
        @RequestParam double amount) {  

    // 检查订单是否存在  
    if (!orderPaymentStatus.containsKey(orderId)) {  
        return "订单不存在";  
    }  

    // 检查是否已经支付  
    if (orderPaymentStatus.get(orderId)) {  
        return "订单已支付";  
    }  

    // 检查用户余额  
    Double balance = userBalance.get(username);  
    if (balance == null) {  
        return "用户不存在";  
    }  
    if (balance < amount) {  
        return "余额不足";  
    }  

    // 扣款  
    userBalance.put(username, balance - amount);  
    // 标记订单支付成功  
    orderPaymentStatus.put(orderId, true);  

    return "支付成功，订单号: " + orderId + "，支付金额: " + amount;  
}  

// 查询支付状态接口  
@GetMapping("/status")  
public String paymentStatus(@RequestParam String orderId) {  
    Boolean paid = orderPaymentStatus.get(orderId);  
    if (paid == null) {  
        return "订单不存在";  
    }  
    return paid ? "支付成功" : "未支付";  
}  
}
