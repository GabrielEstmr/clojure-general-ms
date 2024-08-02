(ns clojure-general-ms.configs.mongo.mongo-config-doc
  (:import (com.mongodb MongoClientSettings ServerApi ServerApiVersion ConnectionString)
           [com.mongodb.client MongoClients MongoDatabase]
           (com.mongodb.client.model Filters)
           (org.bson BsonDocument BsonInt64 Document)
           (org.bson.types ObjectId)))

(defonce uri "mongodb://localhost:27017")

(defn get-server-api []
  (-> (ServerApi/builder)
      (.version ServerApiVersion/V1)
      (.build)))

(defonce mongo-client
         (let [settings (-> (MongoClientSettings/builder)
                            (.applyConnectionString (new ConnectionString uri))
                            (.serverApi (get-server-api))
                            (.build))]
           (MongoClients/create settings)))

(defn ping-database []
  (let [database (.getDatabase mongo-client "admin")
        command (BsonDocument. "ping" (BsonInt64. 1))
        command-result (.runCommand database command)]
    (println "Pinged your deployment. You successfully connected to MongoDB!")
    command-result))

(defonce database (.getDatabase mongo-client "testDatabase"))


(defn insert-document []
  (let [database (.getDatabase mongo-client "testDatabase")
        collection (.getCollection database "testCollection")
        doc1 (-> (Document. "color" "red")
                 (.append "qty" 5)
                 (.append "name" "Gabriel"))
        result (.insertOne collection doc1)
        inserted-id (-> result
                        (.getInsertedId)
                        (.asObjectId)
                        (.getValue))]
    (println "Inserted a document with the following id:" inserted-id)
    inserted-id))

;(defn query-documents []
;  (let [database (.getDatabase mongo-client "testDatabase")
;        collection (.getCollection database "testCollection")
;        filter (Filters/and (Filters/gt "qty" 3))
;        docs (.find collection filter)]
;    ;; Print each document as JSON
;    (doseq [doc docs]
;      (println (.toJson doc)))))

(defn find-document-by-id [id]
  (let [database (.getDatabase mongo-client "testDatabase")
        collection (.getCollection database "testCollection")
        filter (Filters/eq "_id" (ObjectId. (str id)))
        doc (.first (.find collection filter))]
    (when doc
      (println "Found document:" (.toJson doc))
      (.toJson doc))))


(defn convert-to-object-id [id]
  (-> (ObjectId. (str id))))

(defn -main [& args]
  (try
    (let [document-id (insert-document)
          documentsFind (find-document-by-id document-id)]
      (ping-database))
    (catch Exception e
      (println (.getMessage e)))))


