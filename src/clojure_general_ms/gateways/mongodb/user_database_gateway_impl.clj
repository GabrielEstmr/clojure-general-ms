(ns clojure-general-ms.gateways.mongodb.user-database-gateway-impl
  (:require
    [clojure-general-ms.gateways.mongodb.repositories.user-repository :as user-repository]
    [clojure-general-ms.gateways.mongodb.documents.user-document :as user-document]))

(defn save [user]
  (user-repository/create-user (user-document/create-user-document user)))

(defn find-user-by-id [id]
  (user-repository/get-users id))