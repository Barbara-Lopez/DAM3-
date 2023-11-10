import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.result.InsertOneResult;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        String uri = "mongodb://localhost:27017";

        try (MongoClient mongo = MongoClients.create(uri)) {
            MongoDatabase database = mongo.getDatabase("ejemplo");
            MongoCollection<Document> collection = database.getCollection("colpruebas");
        //MongoClient mongo = new MongoClient("localhost", 27017);
        // Accessing the database
        //MongoDatabase database = mongo.getDatabase("ejemplo");
        // Retieving a collection
        //MongoCollection<Document> collection = database.getCollection("colpruebas");
                        //Create document
        InsertOneResult result = collection.insertOne(new Document()
                .append("dni", "-1")
                .append("nombre", "nuevaPersona")
                .append("apellido", "nuevoApellido"));
        // Aniado documento
        //collection.insertOne(document);
        // cerrar conexión
        //mongo.close();
        } catch (MongoException me) {
            System.err.println("Unable to insert due to an error: " + me);
        }
    }
}