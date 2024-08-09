(ns clojure-general-ms.domains.transaction
  (:import (java.time LocalDateTime)))

(defn create-transaction-all-args [^String id ^String user-id ^String account-id ^Double value ^LocalDateTime created-date ^LocalDateTime last-modified-date]
  {:id                 id
   :user-id            user-id
   :account-id         account-id
   :value              value
   :created-date       created-date
   :last-modified-date last-modified-date})

(defn create-transaction [^String user-id ^String account-id ^Double value]
  {:user-id    user-id
   :account-id account-id
   :value      value})

(defn get-property-or-nil [transaction key]
  (when transaction
    (get transaction key nil)))

(defn get-user-id [transaction]
  (get-property-or-nil transaction :user-id))

(defn get-account-id [transaction]
  (when transaction
    (get transaction :account-id nil)))

(defn get-value [transaction]
  (get-property-or-nil transaction :value))

