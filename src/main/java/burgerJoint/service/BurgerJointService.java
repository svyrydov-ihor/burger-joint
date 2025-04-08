package burgerJoint.service;

import burgerJoint.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;

public class BurgerJointService {
  private BurgerJoint burgerJoint = new BurgerJoint(new ObjectMapper());

  public void createCustomer(Customer customer){
    try{
      burgerJoint.createCustomer(customer);
      System.out.println("Customer successfully created");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void getCustomer(int customerId){
    try{
      System.out.println(burgerJoint.getCustomer(customerId));
    } catch (Exception e){
      System.out.println(e.getMessage());
    }
  }

  public Customer getCustomerObject(int customerId){
    try{
      return burgerJoint.getCustomer(customerId);
    } catch (Exception e){
      return null;
    }
  }

  public void updateCustomer(int customerId, Customer newCustomer){
    try{
      burgerJoint.updateCustomer(customerId, newCustomer);
      System.out.println("Customer successfully updated");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void deleteCustomer(int customerId){
    try{
      burgerJoint.deleteCustomer(customerId);
      System.out.println("Customer successfully deleted");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public Hashtable<Integer, Customer> getCustomers(){
    return burgerJoint.getCustomers();
  }

  public void createBurger(Burger burger){
    try{
      burgerJoint.createBurger(burger);
      System.out.println("Burger successfully created");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void getBurger(int burgerId){
    try {
      System.out.println(burgerJoint.getBurger(burgerId));
    } catch (Exception e){
      System.out.println(e.getMessage());
    }
  }

  public Burger getBurgerObject(int burgerId){
    try{
      return burgerJoint.getBurger(burgerId);
    } catch (Exception e){
      return null;
    }
  }

  public void updateBurger(int burgerId, Burger newBurger){
    try{
      burgerJoint.updateBurger(burgerId, newBurger);
      System.out.println("Burger successfully updated");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void deleteBurger(int burgerId){
    try{
      burgerJoint.deleteBurger(burgerId);
      System.out.println("Burger successfully deleted");
    }
    catch (Exception e){
      System.out.println(e.getMessage());
    }
  }

  public void createOrder(Order order){
    try{
      burgerJoint.createOrder(order);
      System.out.println("Order successfully created");
    }
    catch (Exception e){
      System.out.println(e.getMessage());
    }
  }

  public void getOrder(int orderId){
    try{
      System.out.println(burgerJoint.getOrder(orderId));
    } catch (Exception e){
      System.out.println(e.getMessage());
    }
  }

  public Order getOrderObject(int orderId){
    try{
      return burgerJoint.getOrder(orderId);
    } catch (Exception e){
      return null;
    }
  }

  public void updateOrder(int orderId, Order newOrder){
    try{
      burgerJoint.updateOrder(orderId, newOrder);
      System.out.println("Order successfully updated");
    } catch (Exception e){
      System.out.println(e.getMessage());
    }
  }

  public void deleteOrder(int orderId){
    try{
      burgerJoint.deleteOrder(orderId);
      System.out.println("Order successfully deleted");
    } catch (Exception e){
      System.out.println(e.getMessage());
    }
  }
  
  public void cookBurger(Burger burger){
    try{
      burgerJoint.cookBurger(burger);
      System.out.println("Burger successfully cooked");
    } catch (Exception e){
      System.out.println(e.getMessage());
    }
  }

  public void sellBurger(Burger burger){
    try{
      burgerJoint.sellBurger(burger);
      System.out.println("Burger successfully sold");
    }
    catch (Exception e){
      System.out.println(e.getMessage());
    }
  }

  public void viewBurgerTypes(){
    var burgerTypes = burgerJoint.getBurgerTypes();
    if (burgerTypes.isEmpty()){
      System.out.println("No burger types exist");
      return;
    }

    for (var burger : burgerTypes.values()){
      System.out.println("--------------");
      System.out.println(burger);
    }
  }

  public Hashtable<Integer, Burger> getBurgerTypes(){
    return burgerJoint.getBurgerTypes();
  }

  public void viewCustomers(){
    var customers = burgerJoint.getCustomers();
    if (customers.isEmpty()){
      System.out.println("No customers exist");
      return;
    }

    for (var customer : customers.values()){
      System.out.println("--------------");
      System.out.println(customer);
    }
  }

  public void viewOrders(){
    var orders = burgerJoint.getOrders();
    if (orders.isEmpty()){
      System.out.println("No orders exist");
      return;
    }

    for (var order : orders.values()){
      System.out.println("--------------");
      System.out.println(order);
    }
  }

  public void viewAvailableBurgers(){
    var burgers = burgerJoint.getBurgersAvailable();
    if (burgers.isEmpty()){
      System.out.println("No burgers available");
      return;
    }
    for (var burger : burgers.keySet()){
      System.out.println("--------------");
      System.out.println(burger);
      System.out.println("Amount: " + burgers.get(burger));
    }
  }

  public void viewSoldBurgers(){
    var burgers = burgerJoint.getBurgersSold();
    if (burgers.isEmpty()){
      System.out.println("No burgers sold");
      return;
    }
    for (var burger : burgers.keySet()){
      System.out.println("--------------");
      System.out.println(burger);
      System.out.println("Amount: " + burgers.get(burger));
    }
  }

  public void exportBurgerTypes(OutputStream outputStream, BurgerSortOptions options){
    try{
      burgerJoint.exportBurgerTypes(outputStream, options);
      System.out.println("Burger types successfully exported");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void importBurgerTypes(InputStream inputStream){
    try{
      burgerJoint.importBurgerTypes(inputStream);
      System.out.println("Burger types successfully imported");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
