import burgerJoint.model.BurgerJoint;
import burgerJoint.model.Burger;
import burgerJoint.model.Customer;
import burgerJoint.model.Order;
import burgerJoint.model.BurgerSortOptions;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExportImportTest {
  @Mock
  private ObjectMapper mockObjectMapper;

  @Captor
  private ArgumentCaptor<List<Burger>> burgerListCaptor;

  private BurgerJoint burgerJoint;
  private Burger burger1, burger2, burger3;
  @BeforeEach
  void setUp() {
    burgerJoint = new BurgerJoint(mockObjectMapper);

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
    Burger burger2 = new Burger(456, "A hamburger", 3.99, ingredients);
    burgerJoint.createBurger(burger2);
    this.burger2 = burger2;

    var ingredients3 = new Hashtable<String, Integer>();
    ingredients.put("bun", 3);
    ingredients.put("cheese", 3);
    ingredients.put("ham", 3);
    ingredients.put("ketchup", 1);
    Burger burger3 = new Burger(888, "Royal burger", 5.99, ingredients);
    burgerJoint.createBurger(burger3);
    this.burger3 = burger3;

    var customer1 = new Customer(234, "Ihor", "+380123456789");
    burgerJoint.createCustomer(customer1);

    var burgers = new Hashtable<Burger, Integer>();
    burgers.put(burger1, 2);
    burgers.put(burger2, 1);

    var order = new Order(345, 234, burgers);
    burgerJoint.createOrder(order);
  }

  @Test
  @DisplayName("Export should call ObjectMapper.writeValue with correctly sorted burger list (price desc)")
  public void testWritesSortedBurgerListPriceDesc() throws IOException {
    List<Burger> expectedSortedList = Arrays.asList(burger3, burger2, burger1);

    ByteArrayOutputStream testOutputStream = new ByteArrayOutputStream();
    burgerJoint.exportBurgerTypes(testOutputStream, BurgerSortOptions.PRICE_DESCENDING);

    verify(mockObjectMapper, times(1)).writeValue(eq(testOutputStream), burgerListCaptor.capture());
    List<Burger> actualWrittenList = burgerListCaptor.getValue();

    assertTrue(expectedSortedList.equals(actualWrittenList));

    testOutputStream.write(1);
    testOutputStream.close();
  }

  @Test
  @DisplayName("Export should call ObjectMapper.writeValue with correctly sorted burger list (name asc)")
  public void testWritesSortedBurgerListNameAsc() throws IOException {
    List<Burger> expectedSortedList = Arrays.asList(burger2, burger1, burger3);

    ByteArrayOutputStream testOutputStream = new ByteArrayOutputStream();
    burgerJoint.exportBurgerTypes(testOutputStream, BurgerSortOptions.NAME_ASCENDING);

    verify(mockObjectMapper, times(1)).writeValue(eq(testOutputStream), burgerListCaptor.capture());
    List<Burger> actualWrittenList = burgerListCaptor.getValue();

    assertTrue(expectedSortedList.equals(actualWrittenList));

    testOutputStream.write(1);
    testOutputStream.close();
  }

  @Test
  @DisplayName("Export should call ObjectMapper.writeValue with unsorted list")
  void testWritesDefaultList() throws IOException {
    ByteArrayOutputStream testOutputStream = new ByteArrayOutputStream();

    burgerJoint.exportBurgerTypes(testOutputStream, BurgerSortOptions.DEFAULT);

    verify(mockObjectMapper).writeValue(eq(testOutputStream), burgerListCaptor.capture());
    List<Burger> actualWrittenList = burgerListCaptor.getValue();

    assertNotNull(actualWrittenList);
    assertEquals(3, actualWrittenList.size());
    assertTrue(actualWrittenList.containsAll(List.of(burger1, burger2, burger3)));

    testOutputStream.write(1);
    testOutputStream.close();
  }

  @Test
  @DisplayName("Import Should call ObjectMapper.readValue and update internal hashtable")
  void testReadsAndUpdatesHashtable() throws IOException {
    var importedBurger1 = new Burger(998, "Imported 1", 2.99, new Hashtable<>());
    var importedBurger2 = new Burger(999, "Imported 2", 4.99, new Hashtable<>());
    List<Burger> listFromMapper = Arrays.asList(importedBurger1, importedBurger2);

    InputStream mockInputStream = mock(InputStream.class);
    when(mockObjectMapper.readValue(any(InputStream.class), any(TypeReference.class)))
            .thenReturn(listFromMapper);

    burgerJoint.importBurgerTypes(mockInputStream);

    verify(mockObjectMapper, times(1)).readValue(eq(mockInputStream), any(TypeReference.class));

    Hashtable<Integer, Burger> internalHashtable = burgerJoint.getBurgerTypes();
    assertEquals(2, internalHashtable.size());
    assertTrue(internalHashtable.containsKey(998));
    assertTrue(internalHashtable.containsKey(999));
    assertEquals("Imported 1", internalHashtable.get(998).name);
    assertEquals("Imported 2", internalHashtable.get(999).name);
    assertFalse(internalHashtable.containsKey(123));
  }


  @Test
  @DisplayName("Import should not clear hashtable if ObjectMapper returns null list")
  void importHandlesNullListFromMapper() throws IOException {
    InputStream mockInputStream = mock(InputStream.class);
    when(mockObjectMapper.readValue(any(InputStream.class), any(TypeReference.class)))
            .thenReturn(null);

    assertNotEquals(0, burgerJoint.getBurgerTypes().size());
    assertThrows(IOException.class, () -> burgerJoint.importBurgerTypes(mockInputStream));
  }
}