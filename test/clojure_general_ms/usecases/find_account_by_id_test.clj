(ns clojure-general-ms.usecases.find-account-by-id-test
  (:require [clojure.test :refer :all]
            [clojure-general-ms.domains.account :as account]
            [clojure-general-ms.usecases.find-account-by-id :as find-account-by-id]
            [clojure-general-ms.gateways.account-database-gateway :as account-database-gateway])
  (:import (clojure_general_ms.java.domains.exceptions ResourceNotFoundException)))

(defrecord AccountDatabaseGatewayImplMock []
  account-database-gateway/AccountDatabaseGateway
  (save [_ account]
    account)
  (find-by-id [_ id]
    (account/create-account "user-id")))


(defrecord AccountDatabaseGatewayImplMock2 []
  account-database-gateway/AccountDatabaseGateway
  (save [_ account]
    nil)
  (find-by-id [_ id]
    nil))


(deftest should-return-account-from-gateway
  (let [
        provider (find-account-by-id/execute (->AccountDatabaseGatewayImplMock))
        account (account/create-account "user-id")
        result (provider account)]

    (is (= account result) "The user should be saved correctly.")))

(deftest should-return-null-when-account-not-found-from-gateway
  (let [
        provider (find-account-by-id/execute (->AccountDatabaseGatewayImplMock2))]

    (is (thrown? ResourceNotFoundException (provider "user-id")))))