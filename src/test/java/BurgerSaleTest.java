import burgerJoint.model.Burger;
import burgerJoint.model.BurgerJoint;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Hashtable;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BurgerSaleTest {
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
  public void testCookBurger() {
    var burger = burgerJoint.getBurger(123);
    burgerJoint.cookBurger(burger);
    var expectedAvailable = new Hashtable<Burger, Integer>();
    expectedAvailable.put(burger, 1);
    assertEquals(expectedAvailable, burgerJoint.getBurgersAvailable());
  }

  @Test
  public void testCook2Burgers() {
    var burger = burgerJoint.getBurger(123);
    burgerJoint.cookBurger(burger);
    burgerJoint.cookBurger(burger);
    var expectedAvailable = new Hashtable<Burger, Integer>();
    expectedAvailable.put(burger, 2);
    assertEquals(expectedAvailable, burgerJoint.getBurgersAvailable());
  }

  @Test
  public void testCookNotExistingBurger() {
    var burger = burgerJoint.getBurger(123);
    burger.id = 444;
    assertThrows(NoSuchElementException.class, () -> burgerJoint.cookBurger(burger));
  }

  @Test
  public void testSellBurgerSold() {
    var burger = burgerJoint.getBurger(123);
    burgerJoint.cookBurger(burger);
    burgerJoint.sellBurger(burger);
    var expectedSold = new Hashtable<Burger, Integer>();
    expectedSold.put(burger, 1);
    assertEquals(expectedSold, burgerJoint.getBurgersSold());
  }

  @Test
  public void testSellBurgerAvailable() {
    var burger = burgerJoint.getBurger(123);
    burgerJoint.cookBurger(burger);
    burgerJoint.sellBurger(burger);
    var expectedAvailable = new Hashtable<Burger, Integer>();
    expectedAvailable.put(burger, 0);
    assertEquals(expectedAvailable, burgerJoint.getBurgersAvailable());
  }

  @Test
  public void testSell2BurgersSold() {
    var burger = burgerJoint.getBurger(123);
    burgerJoint.cookBurger(burger);
    burgerJoint.cookBurger(burger);
    burgerJoint.sellBurger(burger);
    burgerJoint.sellBurger(burger);
    var expectedSold = new Hashtable<Burger, Integer>();
    expectedSold.put(burger, 2);
    assertEquals(expectedSold, burgerJoint.getBurgersSold());
  }

  @Test
  public void testSell2BurgersAvailable() {
    var burger = burgerJoint.getBurger(123);
    burgerJoint.cookBurger(burger);
    burgerJoint.cookBurger(burger);
    burgerJoint.sellBurger(burger);
    burgerJoint.sellBurger(burger);
    var expectedAvailable = new Hashtable<Burger, Integer>();
    expectedAvailable.put(burger, 0);
    assertEquals(expectedAvailable, burgerJoint.getBurgersAvailable());
  }
}
