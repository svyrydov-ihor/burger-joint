package burgerJoint;

import java.util.Scanner;

import burgerJoint.service.*;

public class App {
  private static BurgerJointService burgerJointService = new BurgerJointService();
  private static BurgerCommands burgerCommands = new BurgerCommands(burgerJointService);
  private static CustomerCommands customerCommands = new CustomerCommands(burgerJointService);
  private static OrdersCommands orderCommands = new OrdersCommands(burgerJointService);
  private static WorkersCommands workersCommands = new WorkersCommands(burgerJointService);
  private static Scanner scanner = new Scanner(System.in);
  public static void main(String[] args) {
    while (true){
      System.out.println("----------------------");
      System.out.println("Burger joint commands:");
      System.out.println("[1] Burger types menu");
      System.out.println("[2] Customers menu");
      System.out.println("[3] Orders menu");
      System.out.println("[4] Workers menu");
      System.out.println("[5] Exit");

      String choice = scanner.nextLine();
      switch (choice) {
        case "1" -> burgerTypeMenu();
        case "2" -> customersMenu();
        case "3" -> ordersMenu();
        case "4" -> workersMenu();
        case "5" -> {return;}
        default -> System.out.println("Invalid choice, please try again");
      }
    }
  }
  private static void burgerTypeMenu() {
    while (true){
      System.out.println("-----------------");
      System.out.println("Burger types menu");
      System.out.println("[1] View burger types");
      System.out.println("[2] Export burger types");
      System.out.println("[3] Import burger types");
      System.out.println("[4] Create burger");
      System.out.println("[5] Get burger");
      System.out.println("[6] Update burger");
      System.out.println("[7] Delete burger");
      System.out.println("[8] Back");
      System.out.println("[9] Exit");

      String choice = scanner.nextLine();
      switch (choice) {
        case "1" -> burgerCommands.viewBurgerTypes();
        case "2" -> burgerCommands.exportBurgerTypes();
        case "3" -> burgerCommands.importBurgerTypes();
        case "4" -> burgerCommands.createBurger();
        case "5" -> burgerCommands.getBurger();
        case "6" -> burgerCommands.updateBurger();
        case "7" -> burgerCommands.deleteBurger();
        case "8" -> {return;}
        case "9" -> {System.exit(0);}
        default -> System.out.println("Invalid choice, please try again");
      }
    }
  }
  private static void customersMenu() {
    while (true){
      System.out.println("--------------");
      System.out.println("Customers menu");
      System.out.println("[1] View customers");
      System.out.println("[2] Create customer");
      System.out.println("[3] Get customer");
      System.out.println("[4] Update customer");
      System.out.println("[5] Delete customer");
      System.out.println("[6] Back");
      System.out.println("[7] Exit");

      String choice = scanner.nextLine();
      switch (choice) {
        case "1" -> customerCommands.viewCustomers();
        case "2" -> customerCommands.createCustomer();
        case "3" -> customerCommands.getCustomer();
        case "4" -> customerCommands.updateCustomer();
        case "5" -> customerCommands.deleteCustomer();
        case "6" -> {return;}
        case "7" -> {System.exit(0);}
        default -> System.out.println("Invalid choice, please try again");
      }
    }
  }
  private static void ordersMenu() {
    while (true){
      System.out.println("-----------");
      System.out.println("Orders menu");
      System.out.println("[1] View orders");
      System.out.println("[2] Create order");
      System.out.println("[3] Get order");
      System.out.println("[4] Update order");
      System.out.println("[5] Delete order");
      System.out.println("[6] Back");
      System.out.println("[7] Exit");

      String choice = scanner.nextLine();
      switch (choice) {
        case "1" -> orderCommands.viewOrders();
        case "2" -> orderCommands.createOrder();
        case "3" -> orderCommands.getOrder();
        case "4" -> orderCommands.updateOrder();
        case "5" -> orderCommands.deleteOrder();
        case "6" -> {return;}
        case "7" -> {System.exit(0);}
        default -> System.out.println("Invalid choice, please try again");
      }
    }
  }
  private static void workersMenu() {
    while (true){
      System.out.println("-----------");
      System.out.println("Workers menu");
      System.out.println("[1] Cook burger");
      System.out.println("[2] Sell burger");
      System.out.println("[3] View available burgers");
      System.out.println("[4] View sold burgers");
      System.out.println("[5] Back");
      System.out.println("[6] Exit");

      String choice = scanner.nextLine();
      switch (choice) {
        case "1" -> workersCommands.cookBurger();
        case "2" -> workersCommands.sellBurger();
        case "3" -> burgerJointService.viewAvailableBurgers();
        case "4" -> burgerJointService.viewSoldBurgers();
        case "5" -> {return;}
        case "6" -> System.exit(0);
        default -> System.out.println("Invalid choice, please try again");
      }
    }
  }
}
