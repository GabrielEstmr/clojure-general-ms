(ns clojure-general-ms.gateways.mongodb.documents.user-document
  (:require [clojure-general-ms.domains.user :as domain-user])
  (:import [java.time Instant]))

(defn create-user-document-all-args [first-name last-name age company]
  {:first_name         first-name
   :last_name          last-name
   :age                age
   :company            company
   :created_date       (.toString (Instant/now))
   :last_modified_date (.toString (Instant/now))})

(defn create-user-document [user]
  (let [{:keys [first_name last_name age company]} user]
    (create-user-document-all-args first_name last_name age company)))


(defn to-domain [user-document]
  (let [{:keys [first_name last_name age company created_date last_modified_date]} user-document]
    (domain-user/create-user first_name last_name age company created_date, last_modified_date)))
