(ns clojure-general-ms.gateways.mongodb.repositories.user-repository
  (:require
    [clojure-general-ms.configs.mongo.mongo-config :as mongo-config])
  (:import
    (java.time Instant)
    (org.bson Document)
    (org.bson.types ObjectId)))


(defn get-users [user-document]
  (let [collection (.getCollection mongo-config/get-database "users")]
    (map (fn [user-document] (.toJson user-document)) (.find collection))))

(defn create-user [user-document]
  (let [collection (.getCollection mongo-config/get-database "users")
        doc (-> (Document.)
                (.append "first_name" (:first_name user-document))
                (.append "last_name" (:last_name user-document))
                (.append "age" (:age user-document))
                (.append "company" (:company user-document))
                (.append "created_date" (str (Instant/now)))
                (.append "last_modified_date" (str (Instant/now))))]
    (.insertOne collection doc)))

(defn find-user-by-id [id]
  (let [collection (.getCollection mongo-config/get-database "users")
        query (Document. "_id" (ObjectId. id))
        doc (.find collection query)]
    (when (.iterator doc)
      (first (.iterator doc)))))