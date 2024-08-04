(ns clojure-general-ms.gateways.mongodb.repositories.user-repository
  (:require [clojure-general-ms.configs.mongo.mongo-config-doc :as mongo-config]
            [clojure-general-ms.utils.date-utils :as date-utils]
            [clojure-general-ms.utils.mongo-doc-utils :as mongo-doc-utils])
  (:import (com.mongodb.client.model Filters)
           (java.time LocalDateTime)
           (org.bson Document)
           (org.bson.types ObjectId)))

(defonce users-collection (.getCollection mongo-config/database "user"))

;; TODO: rever :first-name and :last-name keys
(defn save [user-document]
  (let [now (LocalDateTime/now)
        doc (-> (Document.)
                (.append "first_name" (:first-name user-document))
                (.append "last_name" (:last-name user-document))
                (.append "age" (:age user-document))
                (.append "company" (:company user-document))
                (.append "created_date" now)
                (.append "last_modified_date" now))
        result (.insertOne users-collection doc)
        inserted-id (-> result
                        (.getInsertedId)
                        (.asObjectId)
                        (.getValue))
        updated-user-document (assoc user-document
                                :_id inserted-id
                                :created_date now
                                :last_modified_date now)]
    (println "Inserted a document with the following id:" inserted-id)
    updated-user-document))

(defn find-by-id [id]
  (let [filter (Filters/eq "_id" (ObjectId. (str id)))
        doc (.first (.find users-collection filter))
       docMap (mongo-doc-utils/doc-to-map doc)
        {:keys [_id
                first_name
                last_name
                age
                company
                created_date
                last_modified_date]} docMap
        ]
    (when doc
      (println "Found document:" (.toJson doc))
      {:_id                _id
       :first_name         first_name
       :last_name          last_name
       :age                age
       :company            company
       :created_date       (date-utils/date-to-local-datetime created_date)
       :last_modified_date (date-utils/date-to-local-datetime last_modified_date)})))
