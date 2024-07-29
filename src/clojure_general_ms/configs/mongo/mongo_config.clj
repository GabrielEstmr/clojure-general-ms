(ns clojure-general-ms.configs.mongo.mongo-config
  (:import [com.mongodb.client MongoClients MongoDatabase]))

(defonce ^MongoDatabase get-database
         (let [client (MongoClients/create "mongodb://localhost:27017")]
           (.getDatabase client "userdb")))