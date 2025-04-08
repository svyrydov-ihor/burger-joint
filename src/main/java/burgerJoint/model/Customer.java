package burgerJoint.model;

public class Customer {
  public int id;
  public String name;
  public String phone;
  public Customer(int id, String name, String phone) {
    this.id = id;
    this.name = name;
    this.phone = phone;
  }

  @Override
  public String toString() {
    return String.format("Customer ID: %d\n" +
            "Name: %s\n" +
            "Phone: %s", this.id, this.name, this.phone);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Customer customer))
      return false;

    return name.equals(customer.name) && phone.equals(customer.phone);
  }
}
