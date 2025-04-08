package burgerJoint.service;

import burgerJoint.model.Order;

import java.util.Scanner;

public class OrdersCommands {
  private static Scanner scanner = new Scanner(System.in);
  private BurgerJointService burgerJointService;
  public OrdersCommands(BurgerJointService burgerJointService) {
    this.burgerJointService = burgerJointService;
  }
  public void viewOrders(){
    burgerJointService.viewOrders();
  }
  public void createOrder(){
    var burgerTypes = burgerJointService.getBurgerTypes();
    if (burgerTypes.isEmpty()) {
      System.out.println("No burger types exist. Cannot create order");
      return;
    }
    var customers = burgerJointService.getCustomers();
    if (customers.isEmpty()) {
      System.out.println("No customers exist. Cannot create order");
      return;
    }

    int orderId = 0;
    while(true){
      System.out.println("Order ID:");
      orderId = UserInput.inputPositiveId();
      if (burgerJointService.getOrderObject(orderId) != null) {
        System.out.println(String.format("Order with ID %d already exists", orderId));
        continue;
      }
      break;
    }

    System.out.println("Customer ID:");
    int customerId = UserInput.inputPositiveId();
    if (burgerJointService.getCustomerObject(customerId) == null) {
      System.out.println(String.format("Customer with ID %d does not exist", customerId));
      return;
    }

    burgerJointService.viewBurgerTypes();
    var burgers = UserInput.inputBurgersOrder(burgerJointService);

    Order order = new Order(orderId, customerId, burgers);
    burgerJointService.createOrder(order);
  }
  public void getOrder(){
    int id = UserInput.inputPositiveId();
    burgerJointService.getOrder(id);
  }
  public void updateOrder(){
    int id = UserInput.inputPositiveId();
    Order orderObj = burgerJointService.getOrderObject(id);
    if (orderObj == null){
      System.out.println(String.format("Order with ID %d does not exist", id));
      return;
    }
    Order order = new Order(orderObj.id, orderObj.customerId, orderObj.burgers);

    System.out.println("Current order to update:");
    System.out.println(order);
    boolean isUpdated = false;
    while(!isUpdated){
      System.out.println("What would you like to update?");
      System.out.println("[1] Customer ID");
      System.out.println("[2] Burgers");
      System.out.println("[3] Is Complete");
      System.out.println("[4] Back");
      String choice = scanner.nextLine();
      switch (choice) {
        case "1" ->{
          System.out.println("Customer ID:");
          int customerId = UserInput.inputPositiveId();
          if (burgerJointService.getCustomerObject(customerId) == null) {
            System.out.println(String.format("Customer with ID %d does not exist", customerId));
            return;
          }
          isUpdated = true;
        }
        case "2" ->{
          burgerJointService.viewBurgerTypes();
          order.burgers = UserInput.inputBurgersOrder(burgerJointService);
          isUpdated = true;
        }
        case "3" ->{
          order.isCompleted = UserInput.inputIsCompleted();
          isUpdated = true;
        }
        case "4" -> {return;}
        default -> System.out.println("Invalid choice, please try again");
      }
    }
    burgerJointService.updateOrder(id, order);
  }
  public void deleteOrder(){
    int id = UserInput.inputPositiveId();
    burgerJointService.deleteOrder(id);
  }
}
