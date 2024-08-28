(ns clojure-general-ms.usecases.create-account
  (:require [clojure-general-ms.domains.account :as account]
            [clojure-general-ms.usecases.find-user-by-id :as find-user-by-id]))

(defn execute [userDatabaseGateway accountDatabaseGateway]
  (fn [account]
    (let [find-user-by-id-fn (find-user-by-id/execute userDatabaseGateway)
          user (find-user-by-id-fn (account/get-user-id account))]
      (when user
        (let [saved-account (.save accountDatabaseGateway account)]
          saved-account)))))