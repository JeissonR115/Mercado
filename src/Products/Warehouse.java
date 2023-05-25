package Products;
import java.util.ArrayList;

public class Warehouse {
    private ArrayList<Product> products;

    public Warehouse() {
        products = new ArrayList<Product>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public void showAllProducts() {
        for (Product product : products) {
            product.showProduct();
            System.out.println();
        }
    }
}
