package Products;

public class Product {
    private String name , id;
    private double price;
    public Product(String name, String id, double price/*, String description*/) {
        this.name = name;
        this.id = id;
        this.price = price;
        //this.description = description;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    /*
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    */

    public void showProduct() {
        System.out.println("Nombre del producto: " + name);
        System.out.println("ID del producto: " + id);
        System.out.println("Precio del producto: $" + price);
        //System.out.println("Descripción del producto: " + description);
    }
}
