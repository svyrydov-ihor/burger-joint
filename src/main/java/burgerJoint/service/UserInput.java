package burgerJoint.service;

import burgerJoint.model.Burger;
import org.json.JSONObject;
import java.util.Hashtable;
import java.util.Scanner;

public class UserInput {
  private static Scanner scanner = new Scanner(System.in);
  public static int inputPositiveId(){
    while (true){
      System.out.println("Enter ID:");
      String stringId = scanner.nextLine();
      try{
        int id = Integer.parseInt(stringId);
        if (id < 1){
          System.out.println("ID must be a positive integer");
          continue;
        }
        return id;
      } catch (Exception e){
        System.out.println("ID must be a positive integer");
      }
    }
  }
  public static String inputName(){
    while (true){
      System.out.println("Enter name:");
      String name = scanner.nextLine();
      if (name == null || name.isEmpty()){
        System.out.println("Name cannot be empty");
        continue;
      }
      return name;
    }
  }
  public static String inputPhone(){
    while (true){
      System.out.println("Enter phone number:");
      System.out.println("For example: +3801234567890");
      String name = scanner.nextLine();
      if (name == null || name.isEmpty()){
        System.out.println("Phone cannot be empty");
        continue;
      }
      return name;
    }
  }
  public static double inputPrice(){
    while (true){
      System.out.println("Enter price:");
      String stringPrice = scanner.nextLine();
      try{
        double id = Double.parseDouble(stringPrice);
        if (id <= 0){
          System.out.println("Price must be a positive double");
          continue;
        }
        return id;
      } catch (Exception e){
        System.out.println("Price must be a positive double");
      }
    }
  }
  public static Hashtable<String, Integer> inputIngredients(){
    while (true){
      try {
        System.out.println("Enter ingredients in following format:");
        System.out.println("\"bun\": 2, \"beef patty\": 1, \"cheese\": 1, \"ketchup\": 1");
        String stringIngredients = scanner.nextLine();
        if (stringIngredients == null || stringIngredients.isEmpty()) {
          System.out.println("Ingredients cannot be empty");
          continue;
        }
        stringIngredients = stringIngredients.toLowerCase();
        stringIngredients = "{" + stringIngredients + "}";

        JSONObject inputObj = new JSONObject(stringIngredients);
        var hashTable = new Hashtable<String, Integer>();
        for (var key : inputObj.keySet()) {
          int amount = inputObj.getInt(key);
          if (amount < 1) {
            System.out.println("Ingredient amount must be a positive integer");
            continue;
          }
          hashTable.put(key, amount);
        }
        return hashTable;
      } catch (Exception e){
        System.out.println("Invalid input, please follow the format");
      }
    }
  }
  public static Hashtable<Burger, Integer> inputBurgersOrder(BurgerJointService burgerJointService){
    while (true){
      try{
        System.out.println("Enter burger IDs and number of burgers to order in following format:");
        System.out.println("ID: number, ID: number");
        System.out.println("Use existing burger types above");

        String burgers = scanner.nextLine();
        if (burgers == null || burgers.isEmpty()) {
          System.out.println("Burgers cannot be empty");
          continue;
        }
        burgers = "{" + burgers + "}";

        JSONObject inputObj = new JSONObject(burgers);
        var hashTable = new Hashtable<Burger, Integer>();
        for (var key : inputObj.keySet()) {
          int id = Integer.parseInt(key);
          int amount = inputObj.getInt(key);
          if (burgerJointService.getBurgerObject(id) == null) {
            throw new Exception();
          } else if (amount < 1) {
            System.out.println("Number of burgers must be a positive integer");
            throw new Exception();
          }
          hashTable.put(burgerJointService.getBurgerObject(id), amount);
        }

        return hashTable;
      } catch (Exception e){
        System.out.println("Invalid input, please follow the format");
      }
    }
  }
  public static boolean inputIsCompleted(){
    while (true){
      System.out.println("Is the order completed?:");
      System.out.println("yes / no");
      String name = scanner.nextLine();
      if (name == null || name.isEmpty()){
        System.out.println("Answer cannot be empty");
        continue;
      } else if (name.equalsIgnoreCase("yes")){
        return true;
      } else if (name.equalsIgnoreCase("no")){
        return false;
      }
      System.out.println("Please enter yes or no");
    }
  }
}