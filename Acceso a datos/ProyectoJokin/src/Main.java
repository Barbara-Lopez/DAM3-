import com.mongodb.MongoException;
import com.mongodb.client.*;
import org.bson.Document;
import com.mongodb.client.result.InsertOneResult;

import java.util.Iterator;

import static com.mongodb.client.model.Filters.eq;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        String uri = "mongodb://localhost:27017";

        try (MongoClient mongo = MongoClients.create(uri)) {
            MongoDatabase database = mongo.getDatabase("tienda");
            MongoCollection<Document> collection = database.getCollection("productos");
        //MongoClient mongo = new MongoClient("localhost", 27017);
        // Accessing the database
        //MongoDatabase database = mongo.getDatabase("ejemplo");
        // Retieving a collection
        //MongoCollection<Document> collection = database.getCollection("colpruebas");
                        //Create document
        /* FUNCIONA
        InsertOneResult result = collection.insertOne(new Document()
                .append("dni", "-1")
                .append("nombre", "nuevaPersona")
                .append("apellido", "nuevoApellido"));
        */
       /* FUNCIONA
        // Getting the iterable object
            FindIterable<Document> iterDoc = collection.find();
        // Getting the iterator
            Iterator it = iterDoc.iterator();
            while (it.hasNext()) {
                System.out.println(it.next());
            }
        */
            Document document = collection.find(eq("referencia","P0004")).first();
            if(document==null) {
                System.out.println("Está vacio");
            }else{
                System.out.println(document);
            }
        // Aniado documento
        //collection.insertOne(document);
        // cerrar conexión
        //mongo.close();
        } catch (MongoException me) {
            System.err.println("Unable to insert due to an error: " + me);
        }
    }
}