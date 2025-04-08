import burgerJoint.model.Burger;
import burgerJoint.model.BurgerJoint;
import burgerJoint.model.Customer;
import burgerJoint.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Hashtable;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderTest {
  private BurgerJoint burgerJoint;
  private Order order1;
  private Burger burger1;
  @BeforeEach
  void setUp() {
    burgerJoint = new BurgerJoint(new ObjectMapper());

    var ingredients = new Hashtable<String, Integer>();
    ingredients.put("bun", 2);
    ingredients.put("cheese", 1);
    ingredients.put("onions", 1);
    ingredients.put("ketchup", 1);
    Burger burger1 = new Burger(123, "Cheeseburger", 2.99, ingredients);
    burgerJoint.createBurger(burger1);
    this.burger1 = burger1;

    var ingredients2 = new Hashtable<String, Integer>();
    ingredients.put("bun", 2);
    ingredients.put("cheese", 1);
    ingredients.put("ham", 1);
    ingredients.put("ketchup", 1);
    Burger burger2 = new Burger(456, "Hamburger", 3.99, ingredients);
    burgerJoint.createBurger(burger2);

    var customer1 = new Customer(234, "Ihor", "+380123456789");
    burgerJoint.createCustomer(customer1);

    var burgers = new Hashtable<Burger, Integer>();
    burgers.put(burger1, 2);
    burgers.put(burger2, 1);

    var order = new Order(345, 234, burgers);
    burgerJoint.createOrder(order);
    order1 = order;
  }

  @Test
  public void testGetOrder() {
    assertEquals(order1, burgerJoint.getOrder(345));
  }

  @Test
  public void testOrderTotalPrice(){
    double expectedPrice = (2.99 * 2) + 3.99;
    assertEquals(burgerJoint.getOrder(345).getTotalPrice(), expectedPrice);
  }

  @Test
  public void testCreateNullOrder() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> burgerJoint.createOrder(null));
    assertEquals("Order cannot be null", exception.getMessage());
  }

  @Test
  public void testCreateNotPositiveIdOrder() {
    var order = burgerJoint.getOrder(345);
    order.id = -3;

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> burgerJoint.createOrder(order));
    assertEquals("Order ID must be positive", exception.getMessage());
  }

  @Test
  public void testCreateSameIdOrder() {
    var order1 = burgerJoint.getOrder(345);
    var burgers = new Hashtable<Burger, Integer>();
    burgers.put(burger1, 3);
    var order2 = new Order(order1.id, order1.customerId, burgers);

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> burgerJoint.createOrder(order2));
    assertEquals(String.format("Order with ID %s already exists", order2.id), exception.getMessage());
  }

  @Test
  public void testUpdateNotMatchingIdOrder() {
    var order1 = burgerJoint.getOrder(345);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> burgerJoint.updateOrder(777, order1));
    assertEquals("Order ID and updated order ID must match", exception.getMessage());
  }

  @Test
  public void testUpdateNotExistingOrder() {
    var order1 = burgerJoint.getOrder(345);
    var order2 = new Order(111, order1.customerId, order1.burgers);

    assertThrows(NoSuchElementException.class, () -> burgerJoint.updateOrder(111, order2));
  }

  @Test
  public void testUpdateWithNotModifiedOrder(){
    var order1 = burgerJoint.getOrder(345);

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> burgerJoint.updateOrder(345, order1));
    assertEquals("Updated order already exists", exception.getMessage());
  }

  @Test
  public void testDeleteOrder() {
    burgerJoint.deleteOrder(345);
    assertThrows(NoSuchElementException.class, () -> burgerJoint.deleteOrder(345));
  }
}
