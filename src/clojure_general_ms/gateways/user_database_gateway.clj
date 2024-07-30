(ns clojure-general-ms.gateways.user-database-gateway)

(defprotocol UserDatabaseGateway
  (save [this user])
  (find-by-id [this id]))