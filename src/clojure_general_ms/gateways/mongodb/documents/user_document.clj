(ns clojure-general-ms.gateways.mongodb.documents.user-document
  (:require [clojure-general-ms.domains.user :as domain-user])
  (:import [org.bson Document]
           [java.util Set]
           (org.bson.types ObjectId)))

(defn create-user-document-all-args [id first-name last-name age company created-date last-modified-date]
  {:_id                id
   :first_name         first-name
   :last_name          last-name
   :age                age
   :company            company
   :created_date       created-date
   :last_modified_date last-modified-date})

(defn create-user-document [user]
  (let [{:keys [_id
                first_name
                last_name
                age
                company
                created_date
                last_modified_date]} user]
    (create-user-document-all-args
      _id
      first_name
      last_name
      age
      company
      created_date
      last_modified_date)))

(defn object-id-to-string [^ObjectId obj-id]
  (.toHexString obj-id))

(defn to-domain [user-document]
  (let [
        docTemp (get user-document :_id)
        {:keys [_id
                first_name
                last_name
                age
                company
                created_date
                last_modified_date]} user-document]
    (domain-user/create-user
      (object-id-to-string _id)
      first_name
      last_name
      age
      company
      created_date,
      last_modified_date)))
