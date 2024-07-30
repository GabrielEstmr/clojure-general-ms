(ns clojure-general-ms.usecases.find-user-by-id)

(defn find-user-by-id [userDatabaseGateway]
  (fn [id]
    (println "Print inside usecase find-user-by-id")
    (.find-by-id userDatabaseGateway id)))