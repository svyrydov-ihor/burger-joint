package burgerJoint.model;

import java.util.Hashtable;

public class Order {
  public int id;
  public int customerId;
  public Hashtable<Burger, Integer> burgers;
  public boolean isCompleted;
  public double getTotalPrice() {
    double totalPrice = 0;
    for (var entry : burgers.entrySet()) {
      totalPrice += entry.getKey().getPrice() * entry.getValue();
    }
    return totalPrice;
  }
  public Order(int id, int customerId, Hashtable<Burger, Integer> burgers) {
    this.id = id;
    this.customerId = customerId;
    this.burgers = burgers;
    isCompleted = false;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (var entry: this.burgers.entrySet()){
      sb.append(entry.getKey().name + " (" + entry.getValue() + ") | ");
    }
    sb.delete(sb.length()-2, sb.length());
    String s = String.format("Order ID: %d\n" +
            "Customer ID: %d\n" +
            "Burgers: %s\n" +
            "Total price: $%.2f\n" +
            "Is completed: %b", this.id, this.customerId, sb.toString(), this.getTotalPrice(), this.isCompleted);
    return s;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Order order))
      return false;

    return id == order.id && customerId == order.customerId && burgers.equals(order.burgers) && isCompleted == order.isCompleted;
  }
}
