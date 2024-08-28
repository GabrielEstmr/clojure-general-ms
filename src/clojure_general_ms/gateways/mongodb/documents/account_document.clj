(ns clojure-general-ms.gateways.mongodb.documents.account-document
  (:require [clojure-general-ms.domains.account :as domain-account]
            [clojure-general-ms.utils.mongo-doc-utils :as mongo-doc-utils]))

(defn create-account-document-all-args [id user-id created-date last-modified-date]
  {:_id                id
   :user_id            user-id
   :created_date       created-date
   :last_modified_date last-modified-date})

(defn create-account-document [account]
  (let [{:keys [_id
                user-id
                created_date
                last_modified_date]} account]
    (create-account-document-all-args
      _id
      user-id
      created_date
      last_modified_date)))

(defn to-domain [user-document]
  (let [{:keys [_id
                user_id
                created_date
                last_modified_date]} user-document]
    (domain-account/create-account-all-args
      (mongo-doc-utils/object-id-to-string _id)
      user_id
      created_date
      last_modified_date)))