(ns clojure-general-ms.usecases.create-user)

;TODO: try/catch exceptions default application
(defn execute [userDatabaseGateway]
  (fn [user]
    (println "==============================> Print inside usecase create-user")
    (.save userDatabaseGateway user)))