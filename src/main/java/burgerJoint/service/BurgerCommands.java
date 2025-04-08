package burgerJoint.service;

import burgerJoint.model.Burger;
import burgerJoint.model.BurgerSortOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.util.Scanner;

public class BurgerCommands {
  private static Scanner scanner = new Scanner(System.in);
  private BurgerJointService burgerJointService;
  public BurgerCommands(BurgerJointService burgerJointService) {
    this.burgerJointService = burgerJointService;
  }
  public void viewBurgerTypes(){
    burgerJointService.viewBurgerTypes();
  }
  public void exportBurgerTypes() {
    String folder;
    while (true){
      System.out.println("Enter folder to export burger types:");
      folder = scanner.nextLine();
      if (folder == null || folder.isEmpty()){
        System.out.println("Answer cannot be empty");
        continue;
      }
      break;
    }

    File file = new File(folder + "\\burgerTypes.json");
    if (!file.getParentFile().exists()){
      System.out.println("Folder does not exist");
      return;
    }
    FileOutputStream fileOutputStream = null;
    try {
      fileOutputStream = new FileOutputStream(file);
      fileOutputStream.close();
    } catch (Exception e){
      System.out.println("Error writing to file");
    }

    BurgerSortOptions options;
    System.out.println("Select an option for sorting export:");
    System.out.println("[1] Price descending");
    System.out.println("[2] Price ascending");
    System.out.println("[3] Name descending");
    System.out.println("[4] Name ascending");
    System.out.println("[5] Default");

    String choice = scanner.nextLine();
    switch (choice){
      case "1" -> options = BurgerSortOptions.PRICE_DESCENDING;
      case "2" -> options = BurgerSortOptions.PRICE_ASCENDING;
      case "3" -> options = BurgerSortOptions.NAME_DESCENDING;
      case "4" -> options = BurgerSortOptions.NAME_ASCENDING;
      case "5" -> options = BurgerSortOptions.DEFAULT;
      default -> {
        System.out.println("Export canceled");
        return;
      }
    }
    try {
      fileOutputStream = new FileOutputStream(file);
    } catch (Exception e){
      System.out.println("Error writing to file");
    }
    burgerJointService.exportBurgerTypes(fileOutputStream, options);
    try {
      fileOutputStream.close();
    } catch (Exception e){
      System.out.println("Error closing file");
    }
  }
  public void importBurgerTypes() {
    String filePath;
    while (true){
      System.out.println("Enter file path for importing burger types:");
      filePath = scanner.nextLine();
      if (filePath == null || filePath.isEmpty()){
        System.out.println("Path cannot be empty");
        continue;
      }
      break;
    }

    File file = new File(filePath);
    if (!file.exists()){
      System.out.println("File does not exist");
      return;
    }
    FileInputStream fileInputStream = null;
    try {
      fileInputStream = new FileInputStream(file);
      fileInputStream.close();
    } catch (Exception e){
      System.out.println("Error reading file");
    }

    System.out.println("Are you sure want import? It will replace existing burger types?");
    System.out.println("yes / no");

    String choice = scanner.nextLine().toLowerCase();
    if (!choice.equals("yes")){
      System.out.println("Import canceled");
      return;
    }
    try {
      fileInputStream = new FileInputStream(file);
    } catch (Exception e){
      System.out.println("Error reading file");
    }
    burgerJointService.importBurgerTypes(fileInputStream);
    try {
      fileInputStream.close();
    } catch (Exception e){
      System.out.println("Error closing file");
    }
  }
  public void createBurger(){
    int id = UserInput.inputPositiveId();
    String name = UserInput.inputName();
    double price = UserInput.inputPrice();
    var ingredients = UserInput.inputIngredients();
    Burger burger = new Burger(id, name, price, ingredients);
    burgerJointService.createBurger(burger);
  }
  public void getBurger(){
    int id = UserInput.inputPositiveId();
    burgerJointService.getBurger(id);
  }
  public void updateBurger(){
    int id = UserInput.inputPositiveId();
    var burgerObj = burgerJointService.getBurgerObject(id);
    if (burgerObj == null){
      System.out.println(String.format("Burger with ID %s does not exist", id));
      return;
    }
    var burger = new Burger(burgerObj.id, burgerObj.name, burgerObj.getPrice(), burgerObj.ingredients);
    System.out.println("Current burger to update:");
    System.out.println(burger);
    boolean isUpdated = false;
    while(!isUpdated){
      System.out.println("What would you like to update?");
      System.out.println("[1] Name");
      System.out.println("[2] Price");
      System.out.println("[3] Ingredients");
      System.out.println("[4] Back");
      String choice = scanner.nextLine();
      switch (choice) {
        case "1" ->{
          burger.name = UserInput.inputName();
          isUpdated = true;
        }
        case "2" ->{
          double price = UserInput.inputPrice();
          burger.setPrice(price);
          isUpdated = true;
        }
        case "3" ->{
          burger.ingredients = UserInput.inputIngredients();
          isUpdated = true;
        }
        case "4" -> {return;}
        default -> System.out.println("Invalid choice, please try again");
      }
    }
    burgerJointService.updateBurger(id, burger);
  }
  public void deleteBurger(){
    int id = UserInput.inputPositiveId();
    burgerJointService.deleteBurger(id);
  }
}
