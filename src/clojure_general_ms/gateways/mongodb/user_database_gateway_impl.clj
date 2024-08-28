(ns clojure-general-ms.gateways.mongodb.user-database-gateway-impl
  (:require
    [clojure-general-ms.gateways.mongodb.repositories.user-repository :as user-repository]
    [clojure-general-ms.gateways.user-database-gateway :as user-database-gateway]
    [clojure-general-ms.gateways.mongodb.documents.user-document :as user-document]))

(defn save-impl [user]
  (let [user-doc (user-document/create-user-document user)
        saved-user-doc (user-repository/save user-doc)]
    (when saved-user-doc
      (user-document/to-domain saved-user-doc))))

(defn find-by-id-impl [id]
  (let [user-document (user-repository/find-by-id id)]
    (when user-document
      (user-document/to-domain user-document))))

(defn find-by-username-impl [username]
  (let [user-document (user-repository/find-by-username username)]
    (when user-document
      (user-document/to-domain user-document))))

(defrecord UserDatabaseGatewayImpl []
  user-database-gateway/UserDatabaseGateway
  (save [_ user]
    (save-impl user))
  (find-by-id [_ id]
    (find-by-id-impl id))
  (find-by-username [_ username]
    (find-by-username-impl username)))