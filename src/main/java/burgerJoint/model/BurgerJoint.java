package burgerJoint.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

public class BurgerJoint {
  private Hashtable<Integer, Customer> customers;
  private Hashtable<Integer, Burger> burgerTypes;
  private Hashtable<Burger, Integer> burgersAvailable;
  private Hashtable<Burger, Integer> burgersSold;
  private Hashtable<Integer, Order> orders;
  private ObjectMapper objectMapper;

  public Hashtable<Integer, Customer> getCustomers() {return customers;}
  public Hashtable<Integer, Burger> getBurgerTypes() {return burgerTypes;}
  public Hashtable<Burger, Integer> getBurgersAvailable() {return burgersAvailable;}
  public Hashtable<Burger, Integer> getBurgersSold() {return burgersSold;}
  public Hashtable<Integer, Order> getOrders() {return orders;}

  public BurgerJoint(ObjectMapper objectMapper){
    this.objectMapper = objectMapper;
    customers = new Hashtable<Integer, Customer>();
    burgerTypes = new Hashtable<Integer, Burger>();
    burgersAvailable = new Hashtable<Burger, Integer>();
    burgersSold = new Hashtable<Burger, Integer>();
    orders = new Hashtable<Integer, Order>();
  }

  public void createCustomer(Customer customer) {
    if (customer == null)
      throw new IllegalArgumentException("Customer cannot be null");

    if (customer.id < 1)
      throw new IllegalArgumentException("Customer ID must be positive");

    if (customers.containsKey(customer.id))
      throw new IllegalArgumentException(String.format("Customer with ID %s already exists", customer.id));

    if (customers.containsValue(customer))
      throw new IllegalArgumentException("Customer already exists");

    customers.put(customer.id, customer);
  }

  public Customer getCustomer(int customerId) {
    if (customerId < 1)
      throw new IllegalArgumentException("Customer ID must be positive");

    if (!(customers.containsKey(customerId)))
      throw new NoSuchElementException(String.format("Customer with ID %s does not exist", customerId));

    return customers.get(customerId);
  }

  public void updateCustomer(int customerId, Customer newCustomer) {
    if (newCustomer == null)
      throw new IllegalArgumentException("Customer cannot be null");

    if (customerId < 1)
      throw new IllegalArgumentException("Customer ID must be positive");

    if (customerId != newCustomer.id)
      throw new IllegalArgumentException("Customer ID and updated customer ID must match");

    if (!(customers.containsKey(customerId)))
      throw new NoSuchElementException(String.format("Customer with ID %s does not exist", customerId));

    if (customers.containsValue(newCustomer))
      throw new IllegalArgumentException("Updated customer already exists");

    customers.put(customerId, newCustomer);
  }

  public void deleteCustomer(int customerId) {
    if (customerId < 1)
      throw new IllegalArgumentException("Customer ID must be positive");

    if (!customers.containsKey(customerId))
      throw new NoSuchElementException(String.format("Customer with ID %s does not exist", customerId));

    customers.remove(customerId);
  }

  public void createBurger(Burger burger) {
    if (burger == null)
      throw new IllegalArgumentException("Burger cannot be null");

    if (burger.id < 1)
      throw new IllegalArgumentException("Burger id must be positive");

    if (burgerTypes.containsKey(burger.id))
      throw new IllegalArgumentException(String.format("Burger with id %s already exists", burger.id));

    if (burgerTypes.containsValue(burger))
      throw new IllegalArgumentException("Burger already exists");

    burgerTypes.put(burger.id, burger);
  }

  public Burger getBurger(int burgerId) {
    if (burgerId < 1)
      throw new IllegalArgumentException("Burger ID must be positive");

    if (!(burgerTypes.containsKey(burgerId)))
      throw new NoSuchElementException(String.format("Burger with ID %s does not exist", burgerId));

    return burgerTypes.get(burgerId);
  }

  public void updateBurger(int burgerId, Burger newBurger) {
    if (newBurger == null)
      throw new IllegalArgumentException("Burger cannot be null");

    if (burgerId < 1)
      throw new IllegalArgumentException("Burger ID must be positive");

    if (burgerId != newBurger.id)
      throw new IllegalArgumentException("Burger ID and updated Burger ID must match");

    if (!(burgerTypes.containsKey(burgerId)))
      throw new NoSuchElementException(String.format("Burger with ID %s does not exist", burgerId));

    if (burgerTypes.containsValue(newBurger))
      throw new IllegalArgumentException("Updated burger already exists");

    burgerTypes.put(burgerId, newBurger);
  }

  public void deleteBurger(int burgerId) {
    if (burgerId < 1)
      throw new IllegalArgumentException("Burger ID must be positive");

    if (!(burgerTypes.containsKey(burgerId)))
      throw new NoSuchElementException(String.format("Burger with ID %s does not exist", burgerId));

    burgerTypes.remove(burgerId);
  }

  public void createOrder(Order order) {
    if (order == null)
      throw new IllegalArgumentException("Order cannot be null");

    if (order.id < 1)
      throw new IllegalArgumentException("Order ID must be positive");

    if (orders.containsKey(order.id))
      throw new IllegalArgumentException(String.format("Order with ID %s already exists", order.id));

    orders.put(order.id, order);
  }

  public Order getOrder(int orderId) {
    if (orderId < 1)
      throw new IllegalArgumentException("Order ID must be positive");

    if (!(orders.containsKey(orderId)))
      throw new NoSuchElementException(String.format("Order with ID %s does not exist", orderId));

    return orders.get(orderId);
  }

  public void updateOrder(int orderId, Order newOrder) {
    if (newOrder == null)
      throw new IllegalArgumentException("Order cannot be null");

    if (orderId < 1)
      throw new IllegalArgumentException("Order ID must be positive");

    if (orderId != newOrder.id)
      throw new IllegalArgumentException("Order ID and updated order ID must match");

    if (!(orders.containsKey(orderId)))
      throw new NoSuchElementException(String.format("Order with ID %s does not exist", orderId));

    if (orders.containsValue(newOrder))
      throw new IllegalArgumentException("Updated order already exists");

    orders.put(orderId, newOrder);
  }

  public void deleteOrder(int orderId) {
    if (orderId < 1)
      throw new IllegalArgumentException("Order ID must be positive");

    if (!orders.containsKey(orderId))
      throw new NoSuchElementException(String.format("Order with ID %s does not exist", orderId));

    orders.remove(orderId);
  }

  public void cookBurger(Burger burger) {
    if (burger == null)
      throw new IllegalArgumentException("Burger cannot be null");

    if (burger.id < 1)
      throw new IllegalArgumentException("Burger ID must be positive");

    if (!burgerTypes.containsKey(burger.id))
      throw new NoSuchElementException(String.format("Burger with ID %s does not exist", burger.id));

    if (!burgersAvailable.containsKey(burger)){
      burgersAvailable.put(burger, 1);
      return;
    }

    int count = burgersAvailable.get(burger);
    burgersAvailable.put(burger, count + 1);
  }

  public void sellBurger(Burger burger) {
    if (burger == null)
      throw new IllegalArgumentException("Burger cannot be null");

    if (burger.id < 1)
      throw new IllegalArgumentException("Burger ID must be positive");

    if (!burgersAvailable.containsKey(burger))
      throw new NoSuchElementException(String.format("Burger with ID %s does not exist", burger.id));

    int availableCount = burgersAvailable.get(burger);
    burgersAvailable.put(burger, availableCount - 1);

    if (!burgersSold.containsKey(burger)){
      burgersSold.put(burger, 1);
      return;
    }

    int soldCount = burgersSold.get(burger);
    burgersSold.put(burger, soldCount + 1);
  }

  public void exportBurgerTypes(OutputStream outputStream, BurgerSortOptions options) throws IOException {
    List<Burger> burgerList = new ArrayList<>(burgerTypes.values());
    switch (options){
      case PRICE_ASCENDING:
        burgerList.sort(Comparator.comparing(Burger::getPrice));
        break;
      case PRICE_DESCENDING:
        burgerList.sort(Comparator.comparing(Burger::getPrice).reversed());
        break;
      case NAME_ASCENDING:
        burgerList.sort(Comparator.comparing(b -> b.name));
        break;
      case NAME_DESCENDING:
        burgerList.sort(Comparator.comparing((Burger b) -> b.name).reversed());
        break;
      case DEFAULT:
        default:
          break;
    }
    try {
      objectMapper.writeValue(outputStream, burgerList);
    } catch (IOException e) {
      throw new IOException("Failed to write JSON file");
    }
  }

  public void importBurgerTypes(InputStream inputStream) throws IOException {
    try {
      List<Burger> burgerList = objectMapper.readValue(inputStream, new TypeReference<List<Burger>>() {});
      Hashtable<Integer, Burger> burgerTable = new Hashtable<>();
      for (Burger burger : burgerList) {
        burgerTable.put(burger.id, burger);
      }
      burgerTypes = burgerTable;
    } catch (Exception e) {
      throw new IOException("Failed to read JSON file");
    }
  }
}
