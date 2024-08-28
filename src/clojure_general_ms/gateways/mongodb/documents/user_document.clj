(ns clojure-general-ms.gateways.mongodb.documents.user-document
  (:require [clojure-general-ms.domains.user :as domain-user]
            [clojure-general-ms.utils.mongo-doc-utils :as mongo-doc-utils]))

(defn create-user-document-all-args [id first-name last-name username age company created-date last-modified-date]
  {:_id                id
   :first_name         first-name
   :last_name          last-name
   :username          username
   :age                age
   :company            company
   :created_date       created-date
   :last_modified_date last-modified-date})

(defn create-user-document [user]
  (let [{:keys [_id
                first-name
                last-name
                username
                age
                company
                created_date
                last_modified_date]} user]
    (create-user-document-all-args
      _id
      first-name
      last-name
      username
      age
      company
      created_date
      last_modified_date)))

(defn to-domain [user-document]
  (let [{:keys [_id
                first_name
                last_name
                username
                age
                company
                created_date
                last_modified_date]} user-document]
    (domain-user/create-user-all-args
      (mongo-doc-utils/object-id-to-string _id)
      first_name
      last_name
      username
      age
      company
      created_date,
      last_modified_date)))
