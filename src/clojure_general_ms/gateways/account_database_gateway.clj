(ns clojure-general-ms.gateways.account-database-gateway)

(defprotocol AccountDatabaseGateway
  (save [this user])
  (find-by-id [this id]))