(ns clojure-general-ms.gateways.mongodb.user-database-gateway-impl
  (:require
    [clojure-general-ms.gateways.mongodb.repositories.user-repository :as user-repository]
    [clojure-general-ms.gateways.user-database-gateway :as user-database-gateway]
    [clojure-general-ms.gateways.mongodb.documents.user-document :as user-document]))

(defn save-impl [user]
  (let [user-doc (user-document/create-user-document user)
        saved-user-doc (user-repository/save user-doc)
        saved-user (user-document/to-domain saved-user-doc)]
    saved-user))


(defn find-by-id-impl [id]
  (user-document/to-domain (user-repository/find-by-id id)))

(defrecord UserDatabaseGatewayImpl []
  user-database-gateway/UserDatabaseGateway
  (save [_ user]
    (save-impl user))
  (find-by-id [_ id]
    (find-by-id-impl id)))