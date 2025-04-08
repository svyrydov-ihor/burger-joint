package burgerJoint.model;
import java.util.Hashtable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Burger {
  public int id;
  public String name;
  private double price;
  public double getPrice(){return price;}
  public void setPrice(double price){
    if (price > 0)
      this.price = price;
  }
  public Hashtable<String, Integer> ingredients;

  public Burger() {
    this.ingredients = new Hashtable<String, Integer>();
  }

  public Burger(int id, String name, double price, Hashtable<String, Integer> ingredients) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.ingredients = ingredients;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (var entry: this.ingredients.entrySet()){
      sb.append(entry.getKey() + " (" + entry.getValue() + ") | ");
    }
    String s = String.format("Burger ID: %d\n" +
            "Name: %s\n" +
            "Price: $%.2f\n" +
            "Ingredients: %s", this.id, this.name, this.price, sb.toString());
    return s;
  }

  @Override
  public boolean equals(Object obj){
    if(!(obj instanceof Burger burger))
      return false;

    return name.equals(burger.name) && price == burger.price && ingredients.equals(burger.ingredients);
  }

  @Override
  public int hashCode() {
    return (id + name).hashCode();
  }
}
