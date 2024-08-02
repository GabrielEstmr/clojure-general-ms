(ns clojure-general-ms.gateways.mongodb.repositories.user-repository
  (:require [clojure-general-ms.configs.mongo.mongo-config-doc :as mongo-config])
  (:import (com.mongodb.client.model Filters)
           (java.time LocalDateTime ZoneId)
           (org.bson Document)
           (org.bson.types ObjectId)))

(defonce users-collection (.getCollection mongo-config/database "user"))

(defn document->map [^Document doc]
  (let [keys (.keySet doc)]                                 ;; Get the set of keys from the document
    (reduce (fn [m k]
              (assoc m (keyword k) (.get doc k)))           ;; Add each key-value pair to the map
            {}
            keys)))

(defn date-to-localdatetime [date]
  (let [instant (.toInstant date)
        zone-id (ZoneId/systemDefault)]
    (LocalDateTime/ofInstant instant zone-id)))

;TODO: Aqui retorna apenas ID
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
       docMap (document->map doc)
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
       :created_date       (date-to-localdatetime created_date)
       :last_modified_date (date-to-localdatetime last_modified_date)})))

