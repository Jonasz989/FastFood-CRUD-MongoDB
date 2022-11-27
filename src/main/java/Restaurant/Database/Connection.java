package Restaurant.Database;

import Restaurant.System.Menu;
import Restaurant.System.Order;
import Restaurant.System.TimeParser;
import Restaurant.Users.Session;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.ConnectionString;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.BSONTimestamp;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.mongodb.client.model.Filters.*;

public class Connection {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    static ConnectionString connectionString = new ConnectionString("mongodb+srv://damian-admin:nAYMpAAIXtscbovn@cluster0.seyvxu0.mongodb.net/?retryWrites=true&w=majority");
    static int id = 1;
    static Integer[] zamowienia = {1,2,3,4,5};

    public static void main(String[] args) throws InterruptedException {

        LocalDateTime data = LocalDateTime.now();
        System.out.println(data);
        TimeUnit.SECONDS.sleep(5);
        LocalDateTime data2 = LocalDateTime.now();
        LocalDateTime data3 = data2.minusMinutes(5);
        System.out.println(data3);
        System.out.println(data.compareTo(data3));

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
            return (int) collection.countDocuments() + 1;
        }
    }

    public static void addSession() { //TODO change from inserting document to updating one
        try (MongoClient mongoClient = MongoClients.create(connectionString)) { // adding session in database
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
                    .append("positions", client.getIdsOfBasket())
                    .append("price", client.getCurrentPrice())
                    .append("date", LocalDateTime.now())
                    .append("order_state", "ORDERED")
                    .append("payment_method", client.getPaymentType())
                    .append("payment_status", "NO_PAYMENT")
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
            } catch (JsonMappingException e) {
                throw new RuntimeException(e);
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
            } catch (JsonMappingException e) {
                throw new RuntimeException(e);
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
            } catch (JsonMappingException e) {
                throw new RuntimeException(e);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void updateOrderPayment(int orderID) {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("lab03");
            MongoCollection<Document> collection = database.getCollection("orders");

            Bson query  = Filters.eq("order_id",orderID);
            Bson updates  = Updates.combine(Updates.set("payment_status","PAYED"),Updates.set("order_state","IN PROGRESS"));
            UpdateResult upResult = collection.updateOne(query, updates);

        }
    }

    public static void updateOrderChef(int orderID) {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("lab03");
            MongoCollection<Document> collection = database.getCollection("orders");

            Bson query  = Filters.eq("order_id",orderID);
            Bson updates  = Updates.set("order_state","READY");
            UpdateResult upResult = collection.updateOne(query, updates);

        }
    }

    public static void updateOrderFinal(int orderID) {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("lab03");
            MongoCollection<Document> collection = database.getCollection("orders");

            Bson query  = Filters.eq("order_id",orderID);
            Bson updates  = Updates.set("order_state","GIVEN TO CLIENT");
            UpdateResult upResult = collection.updateOne(query, updates);

        }
    }

    public static void updateOrderDiscount(int orderID) {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("lab03");
            MongoCollection<Document> collection = database.getCollection("orders");

            Bson query  = Filters.eq("order_id",orderID);
            Bson updates  = Updates.set("discount","YES");
            UpdateResult upResult = collection.updateOne(query, updates);

        }
    }
}