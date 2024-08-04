(ns clojure-general-ms.configs.mongo.mongo-config-doc
  (:import (com.mongodb MongoClientSettings ServerApi ServerApiVersion ConnectionString)
           [com.mongodb.client MongoClients]
           (org.bson BsonDocument BsonInt64)))

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

