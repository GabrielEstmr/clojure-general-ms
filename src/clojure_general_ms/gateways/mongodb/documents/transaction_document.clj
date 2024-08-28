(ns clojure-general-ms.gateways.mongodb.documents.transaction-document
  (:require [clojure-general-ms.domains.transaction :as domain-transaction]
            [clojure-general-ms.utils.mongo-doc-utils :as mongo-doc-utils]))

(defn create-transaction-document-all-args [id user-id account-id value created-date last-modified-date]
  {:_id                id
   :user_id            user-id
   :account_id         account-id
   :value              value
   :created_date       created-date
   :last_modified_date last-modified-date})

(defn create-transaction-document [transaction]
  (let [{:keys [_id
                user-id
                account-id
                value
                created_date
                last_modified_date]} transaction]
    (create-transaction-document-all-args
      _id
      user-id
      account-id
      value
      created_date
      last_modified_date)))

(defn to-domain [transaction-document]
  (let [{:keys [_id
                user_id
                account_id
                value
                created_date
                last_modified_date]} transaction-document]
    (domain-transaction/create-transaction-all-args
      (mongo-doc-utils/object-id-to-string _id)
      (str user_id)
      (str account_id)
      (double value)
      created_date
      last_modified_date)))