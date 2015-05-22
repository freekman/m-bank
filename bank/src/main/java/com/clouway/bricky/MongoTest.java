package com.clouway.bricky;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.ReplaceOneModel;
import com.mongodb.client.model.UpdateOneModel;
import org.bson.Document;

import java.util.Arrays;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lte;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

/**
 * @author Marian Zlatev (mzlatev91@gmail.com)
 */
public class MongoTest {

  public static void main(String[] args) {

    MongoClient mongoClient = new MongoClient("localhost", 27017);

    MongoDatabase db = mongoClient.getDatabase("bank");

    MongoCollection<Document> collection = db.getCollection("users");

    mongoClient.close();
  }

  private static void bulkOperations(MongoCollection<Document> collection) {
    collection.bulkWrite(Arrays.asList(
            new InsertOneModel<Document>(new Document("age", 124)),
            new InsertOneModel<Document>(new Document("age", 133)),
            new InsertOneModel<Document>(new Document("age", 134)),
            new UpdateOneModel<Document>(
                    new Document("age", 124),
                    new Document("$set", new Document("age", 500))),
            new ReplaceOneModel<Document>(
                    new Document("age", 134),
                    new Document("age", 200)),
            new ReplaceOneModel<Document>(
                    new Document("age", 201),
                    new Document("age", 202))));
  }

  private static void updateSingleDocument(MongoCollection<Document> collection) {
    collection.updateOne(eq("age", 20), new Document("$set", new Document("age", 22).append("balance", "$0")));
    System.out.println(collection.find(eq("age", 22)).first().toJson());
  }

  private static void projectionSearch(MongoCollection<Document> collection) {
    MongoCursor<Document> cursor = collection.find(and(lte("age", 30), eq("gender", "female"))).projection(fields(excludeId(), include("name", "balance", "age"))).iterator();
    try {
      while (cursor.hasNext()) {
        System.out.println(cursor.next().toJson());
      }
    } finally {
      cursor.close();
    }
  }

  private static void forEachBlock(MongoCollection<Document> collection) {
    Block<Document> docPrinter = new Block<Document>() {
      @Override
      public void apply(Document document) {
        System.out.println(document.toJson());
      }
    };

    collection.find(gte("age", 38)).forEach(docPrinter);
  }

  private static void iterateDocuments(MongoCollection<Document> collection) {
    MongoCursor<Document> cursor = collection.find().iterator();

    try {
      while (cursor.hasNext()) {
        System.out.println(cursor.next().toJson());
      }
    } finally {
      cursor.close();
    }
  }

  private static void insertDocument(MongoCollection<Document> collection) {
    Document doc = new Document("isActive", true);
    doc.append("balance", "$1,000,000");
    doc.append("age", 20);

    collection.insertOne(doc);
  }

  private static void findDocWithEqualFilter(MongoCollection<Document> collection) {
    Document doc = collection.findOneAndDelete(eq("age", 20));
    System.out.println(doc.toJson());
  }
}
