(ns clojure-general-ms.gateways.mongodb.repositories.user-repository
  (:require [clojure-general-ms.configs.mongo.mongo-config-doc :as mongo-config])
  (:import (com.mongodb.client.model Filters)
           (org.bson Document)
           (org.bson.types ObjectId)))

(defonce users-collection (.getCollection mongo-config/database "user"))

;TODO: Aqui retorna apenas ID
(defn save [user-document]
  (let [doc (-> (Document.)
                (.append "first_name" (:first_name user-document))
                (.append "last_name" (:last_name user-document))
                (.append "age" (:age user-document))
                (.append "company" (:company user-document))
                (.append "created_date" (:created_date user-document))
                (.append "last_modified_date" (:last_modified_date user-document)))
        result (.insertOne users-collection doc)
        inserted-id (-> result
                        (.getInsertedId)
                        (.asObjectId)
                        (.getValue))]
    (println "Inserted a document with the following id:" inserted-id)
    inserted-id))

(defn find-by-id [id]
  (let [filter (Filters/eq "_id" (ObjectId. (str id)))
        doc (.first (.find users-collection filter))]
    (when doc
      (println "Found document:" (.toJson doc))
      doc)))
