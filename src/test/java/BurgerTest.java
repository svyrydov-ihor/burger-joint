import burgerJoint.model.Burger;
import burgerJoint.model.BurgerJoint;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Hashtable;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BurgerTest {
  private BurgerJoint burgerJoint;
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
  }

  @Test
  public void testGetBurger() {
    var ingredients = new Hashtable<String, Integer>();
    ingredients.put("bun", 2);
    ingredients.put("cheese", 1);
    ingredients.put("onions", 1);
    ingredients.put("ketchup", 1);
    Burger burger1 = new Burger(123, "Cheeseburger", 2.99, ingredients);

    assertEquals(burger1, burgerJoint.getBurger(123));
  }

  @Test
  public void testCreateNullBurger() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> burgerJoint.createBurger(null));
    assertEquals("Burger cannot be null", exception.getMessage());
  }

  @Test
  public void testCreateNotPositiveIdBurger() {
    var ingredients = new Hashtable<String, Integer>();
    ingredients.put("bun", 2);
    ingredients.put("cheese", 1);
    ingredients.put("beef patty", 1);
    ingredients.put("ketchup", 1);
    Burger burger2 = new Burger(-1, "Hamburger", 2.99, ingredients);

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> burgerJoint.createBurger(burger2));
    assertEquals("Burger id must be positive", exception.getMessage());
  }

  @Test
  public void testCreateSameIdBurger() {
    var ingredients = new Hashtable<String, Integer>();
    ingredients.put("bun", 2);
    ingredients.put("cheese", 1);
    ingredients.put("ham", 1);
    ingredients.put("ketchup", 1);
    Burger burger2 = new Burger(123, "Hamburger", 2.99, ingredients);

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> burgerJoint.createBurger(burger2));
    assertEquals(String.format("Burger with id %s already exists", burger2.id), exception.getMessage());
  }

  @Test
  public void testCreateSameBurger() {
    var ingredients = new Hashtable<String, Integer>();
    ingredients.put("bun", 2);
    ingredients.put("cheese", 1);
    ingredients.put("onions", 1);
    ingredients.put("ketchup", 1);
    Burger burger2 = new Burger(456, "Cheeseburger", 2.99, ingredients);

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> burgerJoint.createBurger(burger2));
    assertEquals("Burger already exists", exception.getMessage());
  }

  @Test
  public void testUpdateNotExistingBurger() {
    var ingredients = new Hashtable<String, Integer>();
    ingredients.put("bun", 2);
    ingredients.put("cheese", 1);
    ingredients.put("onions", 1);
    ingredients.put("ketchup", 1);
    Burger burger2 = new Burger(321, "Cheeseburger", 2.99, ingredients);

    assertThrows(NoSuchElementException.class, () -> burgerJoint.updateBurger(321, burger2));
  }

  @Test
  public void testUpdateNotMatchingIdBurger() {
    var burger = burgerJoint.getBurger(123);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> burgerJoint.updateBurger(777, burger));
    assertEquals("Burger ID and updated Burger ID must match", exception.getMessage());
  }

  @Test
  public void testUpdateWithNotModifiedBurger(){
    var burger = burgerJoint.getBurger(123);

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> burgerJoint.updateBurger(123, burger));
    assertEquals("Updated burger already exists", exception.getMessage());
  }

  @Test
  public void testDeleteBurger() {
    burgerJoint.deleteBurger(123);
    assertThrows(NoSuchElementException.class, () -> burgerJoint.deleteBurger(123));
  }
}
