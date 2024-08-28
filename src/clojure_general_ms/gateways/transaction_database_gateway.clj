(ns clojure-general-ms.gateways.transaction-database-gateway)

(defprotocol TransactionDatabaseGateway
  (save [this user])
  (find-by-id [this id]))