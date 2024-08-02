(ns clojure-general-ms.gateways.mongodb.repositories.user-repository
  (:require
    [clojure-general-ms.configs.mongo.mongo-config :as mongo-config])
  (:import
    (org.bson Document)
    (org.bson.types ObjectId)))


(def users-collection (.getCollection db "users"))

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
                (.append "created_date" (:created_date user-document))
                (.append "last_modified_date" (:last_modified_date user-document)))]
    (.insertOne collection doc)))

(defn find-by-id [id]
  (let [collection (.getCollection mongo-config/get-database "users")
        query (Document. "_id" (ObjectId. id))
        doc (.find collection query)]
    (when (.iterator doc)
      (first (.iterator doc)))))