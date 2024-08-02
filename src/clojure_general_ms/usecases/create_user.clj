(ns clojure-general-ms.usecases.create-user)

;TODO: try/catch exceptions default application
(defn execute [userDatabaseGateway]
  (fn [user]
    (let [saved-user (.save userDatabaseGateway user)]
      (println "==============================> Print inside usecase create-user")
      saved-user)))