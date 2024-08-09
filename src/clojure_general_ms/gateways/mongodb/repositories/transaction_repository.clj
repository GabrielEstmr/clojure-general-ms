(ns clojure-general-ms.gateways.mongodb.repositories.transaction-repository
  (:require [clojure-general-ms.configs.mongo.mongo-config-doc :as mongo-config]
            [clojure-general-ms.utils.date-utils :as date-utils]
            [clojure-general-ms.utils.mongo-doc-utils :as mongo-doc-utils])
  (:import (com.mongodb.client.model Filters)
           (java.time LocalDateTime)
           (org.bson Document)))

(defonce transaction-collection (.getCollection mongo-config/database "transaction"))

(defn save [transaction-document]
  (let [now (LocalDateTime/now)
        doc (-> (Document.)
                (.append "user_id" (:user_id transaction-document))
                (.append "account_id" (:account_id transaction-document))
                (.append "value" (:value transaction-document))
                (.append "created_date" now)
                (.append "last_modified_date" now))
        result (.insertOne transaction-collection doc)
        inserted-id (-> result
                        (.getInsertedId)
                        (.asObjectId)
                        (.getValue))
        updated-transaction-document (assoc transaction-document
                                       :_id inserted-id
                                       :created_date now
                                       :last_modified_date now)]
    (println "Inserted a document with the following id:" inserted-id)
    updated-transaction-document))

(defn find-by-id [id]
  (let [filter (Filters/eq "_id" (mongo-doc-utils/string-to-object-id id))
        doc (.first (.find transaction-collection filter))
        docMap (mongo-doc-utils/doc-to-map doc)
        {:keys [_id
                user_id
                account_id
                value
                created_date
                last_modified_date]} docMap
        ]
    (when doc
      (println "Found document:" (.toJson doc))
      {:_id                _id
       :user_id            user_id
       :account_id         account_id
       :value              value
       :created_date       (date-utils/date-to-local-datetime created_date)
       :last_modified_date (date-utils/date-to-local-datetime last_modified_date)})))
