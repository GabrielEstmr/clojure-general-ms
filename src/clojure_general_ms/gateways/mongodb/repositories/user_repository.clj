(ns clojure-general-ms.gateways.mongodb.repositories.user-repository
  (:require [clojure-general-ms.configs.mongo.mongo-config-doc :as mongo-config]
            [clojure-general-ms.utils.date-utils :as date-utils]
            [clojure-general-ms.utils.mongo-doc-utils :as mongo-doc-utils])
  (:import (com.mongodb.client.model Filters)
           (java.time LocalDateTime)
           (org.bson Document)))

(defonce users-collection (.getCollection mongo-config/database "user"))

;; TODO: rever :first-name and :last-name keys
(defn save [user-document]
  (let [now (LocalDateTime/now)
        doc (-> (Document.)
                (.append "first_name" (:first_name user-document))
                (.append "last_name" (:last_name user-document))
                (.append "username" (:username user-document))
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
  (let [filter (Filters/eq "_id" (mongo-doc-utils/string-to-object-id id))
        doc (.first (.find users-collection filter))
        docMap (mongo-doc-utils/doc-to-map doc)
        {:keys [_id
                first_name
                last_name
                username
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
       :username           username
       :age                age
       :company            company
       :created_date       (date-utils/date-to-local-datetime created_date)
       :last_modified_date (date-utils/date-to-local-datetime last_modified_date)})))


(defn find-by-username [username]
  (let [filter (Filters/eq "username" username)
        doc (.first (.find users-collection filter))
        docMap (mongo-doc-utils/doc-to-map doc)
        {:keys [_id
                first_name
                last_name
                username
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
       :username           username
       :age                age
       :company            company
       :created_date       (date-utils/date-to-local-datetime created_date)
       :last_modified_date (date-utils/date-to-local-datetime last_modified_date)})))
