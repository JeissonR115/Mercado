import Users.Worker;
import data.Database;
import java.lang.reflect.Field;

import java.util.List;
import static ui.UIMenu.*;

public class Main {
    public static void main(String[] args) {
        //showMenu();
        Worker a = new Worker("666","sds","admin","***");
        Database<Worker> databaseWorker = new Database<>("./src/data/listWorkers.txt",Worker.class);

        List<Field> parameters = databaseWorker.getClassParameters(Worker.class);
        List<Object> attributeValues = databaseWorker.getObjectAttributes(a);


        for (int i = 0; i < parameters.size(); i++) {
            System.out.println(parameters.get(i).getName() + ": " + attributeValues.get(i));
        }



    }
}