(ns clojure-general-ms.usecases.create-user)

(defn execute [userDatabaseGateway]
  (fn [user]
    (let [saved-user (.save userDatabaseGateway user)]
      (println "==============================> Print inside usecase create-user")
      saved-user)))