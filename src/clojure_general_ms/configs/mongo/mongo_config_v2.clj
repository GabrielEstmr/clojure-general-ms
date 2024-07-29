(ns clojure-general-ms.configs.mongo.mongo-config-v2
  (:import [com.mongodb.client MongoClients MongoClient MongoDatabase]
           [com.mongodb.MongoClientSettings]
           [org.bson.Document]))


(defonce client
         (let [connection-string "mongodb://localhost:27017"
               settings (MongoClientSettings/builder
                          .applyConnectionString (com.mongodb.ConnectionString. connection-string)
                          .build)]
           (MongoClients/create settings)))

(defonce database
         (.getDatabase client "mydatabase"))

(defn get-collection [name]
  (.getCollection database name))

;; Example function to insert a document
(defn insert-document [collection-name doc]
  (let [collection (get-collection collection-name)]
    (.insertOne collection (Document. doc))))