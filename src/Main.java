
import Users.Worker;
import data.Database;
import java.lang.reflect.Field;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //showMenu();
        //Worker a = new Worker(666,"sds","admin","111");
        Database<Worker> databaseWorker = new Database<>("./src/data/listWorkers.txt",Worker.class);

        List<Field> parameters = databaseWorker.getClassParameters();
        System.out.println(parameters.get(1).getType());
        //List<Object> attributeValues = databaseWorker.getObjectAttributes(a);
        databaseWorker.readObjectFromFile();




    }
}