(ns clojure-general-ms.usecases.create-transaction
  (:require [clojure-general-ms.domains.transaction :as transaction]
            [clojure-general-ms.usecases.find-user-by-id :as find-user-by-id]
            [clojure-general-ms.usecases.find-account-by-id :as find-account-by-id]
            [clojure.tools.logging :as log])
  (:import (src.domains.exceptions InternalServerErrorException)))

(defn execute [transactionDatabaseGateway userDatabaseGateway accountDatabaseGateway]
  (fn [transaction]
    (try
      (let [find-user-by-id-fn (find-user-by-id/execute userDatabaseGateway)
            find-account-by-id-fn (find-account-by-id/execute accountDatabaseGateway)
            user (find-user-by-id-fn (transaction/get-user-id transaction))
            account (find-account-by-id-fn (transaction/get-account-id transaction))]
        (when (and user account)
          (let [saved-transaction (.save transactionDatabaseGateway transaction)]
            saved-transaction)))
      (catch Exception e
        (log/error (.getMessage e))
        (throw (InternalServerErrorException. "internal server error"))))))