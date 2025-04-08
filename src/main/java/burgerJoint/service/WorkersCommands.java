package burgerJoint.service;

import java.util.Scanner;

public class WorkersCommands {
  private static Scanner scanner = new Scanner(System.in);
  private BurgerJointService burgerJointService;
  public WorkersCommands(BurgerJointService burgerJointService) {
    this.burgerJointService = burgerJointService;
  }
  public void cookBurger() {
    burgerJointService.viewBurgerTypes();
    System.out.println("Choose burger ID to cook");
    int id = UserInput.inputPositiveId();
    var burger = burgerJointService.getBurgerObject(id);
    if (burger == null){
      System.out.println(String.format("Burger with ID %d does not exist", id));
      return;
    }
    burgerJointService.cookBurger(burger);
  }
  public void sellBurger() {
    System.out.println("Available burgers:");
    burgerJointService.viewAvailableBurgers();
    System.out.println("Choose burger ID to sell");
    int id = UserInput.inputPositiveId();
    var burger = burgerJointService.getBurgerObject(id);
    if (burger == null){
      System.out.println(String.format("Burger with ID %d does not exist", id));
      return;
    }
    burgerJointService.sellBurger(burger);
  }
}
