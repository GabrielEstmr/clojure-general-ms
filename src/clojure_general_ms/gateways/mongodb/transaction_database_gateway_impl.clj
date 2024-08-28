(ns clojure-general-ms.gateways.mongodb.transaction-database-gateway-impl
  (:require
    [clojure-general-ms.gateways.mongodb.repositories.transaction-repository :as transaction-repository]
    [clojure-general-ms.gateways.transaction-database-gateway :as transaction-database-gateway]
    [clojure-general-ms.gateways.mongodb.documents.transaction-document :as transaction-document]))

(defn save-impl [transaction]
  (let [transaction-doc (transaction-document/create-transaction-document transaction)
        saved-transaction-doc (transaction-repository/save transaction-doc)]
    (when saved-transaction-doc
      (transaction-document/to-domain saved-transaction-doc))))

(defn find-by-id-impl [id]
  (let [transaction-doc (transaction-repository/find-by-id id)]
    (when transaction-doc
      (transaction-document/to-domain transaction-doc))))

(defrecord TransactionDatabaseGatewayImpl []
  transaction-database-gateway/TransactionDatabaseGateway
  (save [_ user]
    (save-impl user))
  (find-by-id [_ id]
    (find-by-id-impl id)))