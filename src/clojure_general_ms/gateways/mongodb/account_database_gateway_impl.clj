(ns clojure-general-ms.gateways.mongodb.account-database-gateway-impl
  (:require
    [clojure-general-ms.gateways.mongodb.repositories.account-repository :as account-repository]
    [clojure-general-ms.gateways.account-database-gateway :as account-database-gateway]
    [clojure-general-ms.gateways.mongodb.documents.account-document :as account-document]))

(defn save-impl [account]
  (let [account-doc (account-document/create-account-document account)
        saved-account-doc (account-repository/save account-doc)]
    (when saved-account-doc
      (account-document/to-domain saved-account-doc))))

(defn find-by-id-impl [id]
  (let [account-document (account-repository/find-by-id id)]
    (when account-document
      (account-document/to-domain account-document))))

(defrecord AccountDatabaseGatewayImpl []
  account-database-gateway/AccountDatabaseGateway
  (save [_ account]
    (save-impl account))
  (find-by-id [_ id]
    (find-by-id-impl id)))
