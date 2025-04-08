package burgerJoint.service;

import burgerJoint.model.Customer;

import java.util.Scanner;

public class CustomerCommands {
  private static Scanner scanner = new Scanner(System.in);
  private BurgerJointService burgerJointService;
  public CustomerCommands(BurgerJointService burgerJointService) {
    this.burgerJointService = burgerJointService;
  }
  public void viewCustomers() {
    burgerJointService.viewCustomers();
  }
  public void createCustomer() {
    int id = UserInput.inputPositiveId();
    String name = UserInput.inputName();
    String phone = UserInput.inputPhone();
    Customer customer = new Customer(id, name, phone);
    burgerJointService.createCustomer(customer);
  }
  public void getCustomer() {
    int id = UserInput.inputPositiveId();
    burgerJointService.getCustomer(id);
  }
  public void updateCustomer() {
    int id = UserInput.inputPositiveId();
    var customerObj = burgerJointService.getCustomerObject(id);
    if (customerObj == null){
      System.out.println(String.format("Customer with ID %s does not exist", id));
      return;
    }
    var customer = new Customer(customerObj.id, customerObj.name, customerObj.phone);

    System.out.println("Current customer to update:");
    System.out.println(customer);
    boolean isUpdated = false;
    while(!isUpdated){
      System.out.println("What would you like to update?");
      System.out.println("[1] Name");
      System.out.println("[2] Phone");
      System.out.println("[3] Back");
      String choice = scanner.nextLine();
      switch (choice) {
        case "1" ->{
          customer.name = UserInput.inputName();
          isUpdated = true;
        }
        case "2" ->{
          customer.phone = UserInput.inputPhone();
          isUpdated = true;
        }
        case "3" -> {return;}
        default -> System.out.println("Invalid choice, please try again");
      }
    }
    burgerJointService.updateCustomer(id, customer);
  }
  public void deleteCustomer() {
    int id = UserInput.inputPositiveId();
    burgerJointService.deleteCustomer(id);
  }
}