package Restaurant.Database;

import Restaurant.System.Menu;
import Restaurant.Users.Session;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.ConnectionString;
import com.mongodb.client.*;
import com.mongodb.client.model.Projections;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Connection {

    static ConnectionString connectionString = new ConnectionString("mongodb+srv://damian-admin:nAYMpAAIXtscbovn@cluster0.seyvxu0.mongodb.net/?retryWrites=true&w=majority");
    static int id = 1;
    static Integer[] zamowienia = {1,2,3,4,5};

    public static void main(String[] args) {

    }

    public static int countDocumentsSession() {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("lab03");
            MongoCollection<Document> collection = database.getCollection("session");
            return (int) collection.countDocuments();
        }
    }

    public static int countDocumentsOrders() {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("lab03");
            MongoCollection<Document> collection = database.getCollection("orders");
            return (int) collection.countDocuments();
        }
    }

    public static void addSession() { //TODO change from inserting document to updating one
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("lab03");
            MongoCollection<Document> collection = database.getCollection("session");
            Document newUserSession = new Document("_id", new ObjectId())
                    .append("klient_id", countDocumentsSession() + 1);
            InsertOneResult result = collection.insertOne(newUserSession);
        }
    }

    public static void addOrder(Session client) {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("lab03");
            MongoCollection<Document> collection = database.getCollection("orders");
            Document newOrder = new Document("_id", new ObjectId())
                    .append("order_id", countDocumentsOrders() + 1)
                    .append("user_id", client.getUserID())
                    .append("positions", client.getBasket())
                    .append("price", client.getCurrentPrice())
                    .append("date", LocalDateTime.now())
                    .append("order_state", "ORDERED")
                    .append("payment_method", "CASH")
                    .append("discount", "NO")
                    .append("chef_id", 0)
                    .append("cashier_id", 0);
            InsertOneResult result = collection.insertOne(newOrder);
        }
    }

    public static void readMenu(ArrayList<Menu> menuWithFood) {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("lab03");
            MongoCollection<Document> collection = database.getCollection("menu");

            Bson projectionFields = Projections.fields(
                    Projections.include("menu_id", "name", "price"),
                    Projections.excludeId()
            );

            ObjectMapper objectMapper = new ObjectMapper();

            try (MongoCursor<Document> cursor = collection.find()
                    //.projection(projectionFields) //field which limits our viewing not needed at this moment
                    .iterator()) {
                while (cursor.hasNext()) {
                    Menu positionInMenu = objectMapper.readValue(cursor.next().toJson(), Menu.class); //using reader class from json to java class
                    menuWithFood.add(positionInMenu); //adding food to list on our clientapp
                }
            } catch (JsonMappingException e) {
                throw new RuntimeException(e);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void readOrdersFromUser(int userID) {

    }
}