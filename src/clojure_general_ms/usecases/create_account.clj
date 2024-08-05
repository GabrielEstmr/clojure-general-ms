(ns clojure-general-ms.usecases.create-account)

(defn execute [accountDatabaseGateway]
  (fn [account]
    (let [account-user (.save accountDatabaseGateway account)]
      (println "==============================> Print inside usecase create-account")
      account-user)))