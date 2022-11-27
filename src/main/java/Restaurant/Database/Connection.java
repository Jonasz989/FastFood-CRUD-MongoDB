/**
 * Class for whole connection with database and CRUD methods
 */

package Restaurant.Database;

import Restaurant.System.Menu;
import Restaurant.System.Order;
import Restaurant.Users.Session;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.ConnectionString;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import java.time.LocalDateTime;
import java.util.ArrayList;
import static com.mongodb.client.model.Filters.*;

public class Connection {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    static ConnectionString connectionString = new ConnectionString("mongodb+srv://damian-laby:9o4wtwe4QE01neAu@cluster0.seyvxu0.mongodb.net/?retryWrites=true&w=majority");

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //CLASS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
            return (int) collection.countDocuments() + 1;
        }
    }

    public static void addSession() { //TODO change from inserting document to updating one
        try (MongoClient mongoClient = MongoClients.create(connectionString)) { // adding session in database
            MongoDatabase database = mongoClient.getDatabase("lab03");
            MongoCollection<Document> collection = database.getCollection("session");
            Document newUserSession = new Document("_id", new ObjectId())
                    .append("klient_id", countDocumentsSession() + 1);
            collection.insertOne(newUserSession);
        }
    }

    public static void addOrder(Session client) {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("lab03");
            MongoCollection<Document> collection = database.getCollection("orders");
            Document newOrder = new Document("_id", new ObjectId())
                    .append("order_id", countDocumentsOrders() + 1)
                    .append("user_id", client.getUserID())
                    .append("positions", client.getIdsOfBasket())
                    .append("price", client.getCurrentPrice())
                    .append("date", LocalDateTime.now())
                    .append("order_state", "ORDERED")
                    .append("payment_method", client.getPaymentType())
                    .append("payment_status", "NO_PAYMENT")
                    .append("discount", "NO")
                    .append("chef_id", 0)
                    .append("cashier_id", 0);
            collection.insertOne(newOrder);
        }
    }

    public static void readMenu(ArrayList<Menu> menuWithFood) {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("lab03");
            MongoCollection<Document> collection = database.getCollection("menu");

            ObjectMapper objectMapper = new ObjectMapper();

            try (MongoCursor<Document> cursor = collection.find()
                    //.projection(projectionFields) //field which limits our viewing not needed at this moment
                    .iterator()) {
                while (cursor.hasNext()) {
                    Menu positionInMenu = objectMapper.readValue(cursor.next().toJson(), Menu.class); //using reader class from json to java class
                    menuWithFood.add(positionInMenu); //adding food to list on our clientapp
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void readOrdersFromUser(Session client) {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("lab03");
            MongoCollection<Document> collection = database.getCollection("orders");

            ObjectMapper objectMapper = new ObjectMapper();

            try (MongoCursor<Document> cursor = collection.find(eq("user_id", client.getUserID()))
                    .iterator()) {
                while (cursor.hasNext()) {
                    Order orderFromClient = objectMapper.readValue(cursor.next().toJson(), Order.class);
                    client.getOrders().add(orderFromClient);
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void readOrdersThatAreOrderedOrReady(ArrayList<Order> orders) {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("lab03");
            MongoCollection<Document> collection = database.getCollection("orders");

            ObjectMapper objectMapper = new ObjectMapper();

            try (MongoCursor<Document> cursor = collection.find(Filters.or(eq("order_state", "ORDERED"), eq("order_state", "READY")))
                    .iterator()) {
                while (cursor.hasNext()) {
                    Order order = objectMapper.readValue(cursor.next().toJson(), Order.class);
                    orders.add(order);
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void readOrdersThatAreInProgress(ArrayList<Order> orders) {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("lab03");
            MongoCollection<Document> collection = database.getCollection("orders");

            ObjectMapper objectMapper = new ObjectMapper();

            try (MongoCursor<Document> cursor = collection.find(eq("order_state", "IN PROGRESS"))
                    .iterator()) {
                while (cursor.hasNext()) {
                    Order order = objectMapper.readValue(cursor.next().toJson(), Order.class);
                    orders.add(order);
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void updateOrderPayment(int orderID) {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("lab03");
            MongoCollection<Document> collection = database.getCollection("orders");

            Bson query = Filters.eq("order_id", orderID);
            Bson updates = Updates.combine(Updates.set("payment_status", "PAYED"), Updates.set("order_state", "IN PROGRESS"));
            collection.updateOne(query, updates);

        }
    }

    public static void updateOrderChef(int orderID) {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("lab03");
            MongoCollection<Document> collection = database.getCollection("orders");

            Bson query = Filters.eq("order_id", orderID);
            Bson updates = Updates.set("order_state", "READY");
            collection.updateOne(query, updates);

        }
    }

    public static void updateOrderFinal(int orderID) {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("lab03");
            MongoCollection<Document> collection = database.getCollection("orders");

            Bson query = Filters.eq("order_id", orderID);
            Bson updates = Updates.set("order_state", "GIVEN TO CLIENT");
            collection.updateOne(query, updates);

        }
    }

    public static void updateOrderDiscount(int orderID) {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("lab03");
            MongoCollection<Document> collection = database.getCollection("orders");

            Bson query = Filters.eq("order_id", orderID);
            Bson updates = Updates.set("discount", "YES");
            collection.updateOne(query, updates);

        }
    }
}