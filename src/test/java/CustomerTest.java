import burgerJoint.model.BurgerJoint;
import burgerJoint.model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerTest {
  private BurgerJoint burgerJoint;
  @BeforeEach
  void setUp() {
    burgerJoint = new BurgerJoint(new ObjectMapper());
    Customer customer1 = new Customer(123, "Oleg", "+380123456789");
    burgerJoint.createCustomer(customer1);
  }

  @Test
  public void testGetCustomer() {
    Customer customer1 = new Customer(123, "Oleg", "+380123456789");

    assertEquals(customer1, burgerJoint.getCustomer(123));
  }

  @Test
  public void testCreateNullCustomer() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> burgerJoint.createCustomer(null));
    assertEquals("Customer cannot be null", exception.getMessage());
  }

  @Test
  public void testCreateNotPositiveIdCustomer() {
    Customer customer2 = new Customer(-2, "Ihor", "+3805683456789");

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> burgerJoint.createCustomer(customer2));
    assertEquals("Customer ID must be positive", exception.getMessage());
  }

  @Test
  public void testCreateSameIdCustomer() {
    Customer customer2 = new Customer(123, "Not Ihor", "+3805683456789");

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> burgerJoint.createCustomer(customer2));
    assertEquals(String.format("Customer with ID %s already exists", customer2.id), exception.getMessage());
  }

  @Test
  public void testCreateSameCustomer() {
    Customer customer2 = new Customer(678, "Oleg", "+380123456789");

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> burgerJoint.createCustomer(customer2));
    assertEquals("Customer already exists", exception.getMessage());
  }

  @Test
  public void testUpdateNotExistingCustomer() {
    Customer customer2 = new Customer(678, "Oleg", "+380123456789");

    assertThrows(NoSuchElementException.class, () -> burgerJoint.updateCustomer(678, customer2));
  }

  @Test
  public void testUpdateNotMatchingIdCustomer() {
    var customer = burgerJoint.getCustomer(123);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> burgerJoint.updateCustomer(777, customer));
    assertEquals("Customer ID and updated customer ID must match", exception.getMessage());
  }

  @Test
  public void testUpdateWithNotModifiedCustomer() {
    var customer = burgerJoint.getCustomer(123);

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> burgerJoint.updateCustomer(123, customer));
    assertEquals("Updated customer already exists", exception.getMessage());
  }

  @Test
  public void testDeleteCustomer() {
    burgerJoint.deleteCustomer(123);
    assertThrows(NoSuchElementException.class, () -> burgerJoint.deleteCustomer(123));
  }
}
