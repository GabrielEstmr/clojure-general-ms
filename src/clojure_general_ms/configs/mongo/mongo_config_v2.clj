(ns clojure-general-ms.configs.mongo.mongo-config-v2
  (:require [clojure.data.json :as json])
  (:import (com.mongodb.client MongoClients MongoCollection)
           (org.bson Document)
           (org.bson.types ObjectId)
           (java.time Instant)))

(def client (MongoClients/create "mongodb://localhost:27017"))
(def db (.getDatabase client "dbAppClojure"))

(def users-collection (.getCollection db "users"))


(defn document->map [^Document doc]
  (into {} (.entrySet doc)))

(defn map->document [m]
  (Document. m))

(defn save-user [user]
  (.insertOne users-collection (map->document user)))

(defn get-user-by-id [id]
  (let [doc (.find users-collection (Document. "_id" (ObjectId. id)))]
    (if (.iterator doc)
      (document->map (.first doc))
      nil)))











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