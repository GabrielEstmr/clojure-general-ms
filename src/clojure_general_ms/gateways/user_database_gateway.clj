(ns clojure-general-ms.gateways.user-database-gateway)

(defn create-user [create-user-impl user]
  (create-user-impl user))

(defn find-user-by-id [find-user-by-id-impl user]
  (find-user-by-id-impl user))