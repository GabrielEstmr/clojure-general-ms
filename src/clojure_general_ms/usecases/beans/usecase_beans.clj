(ns clojure-general-ms.usecases.beans.usecase-beans
  (:require [clojure-general-ms.gateways.mongodb.user-database-gateway-impl :as userDatabaseGateway]
            [clojure-general-ms.gateways.mongodb.account-database-gateway-impl :as accountDatabaseGateway]
            [clojure-general-ms.gateways.mongodb.transaction-database-gateway-impl :as transactionDatabaseGateway]
            [clojure-general-ms.usecases.find-user-by-id :as usecaseFindUserById]
            [clojure-general-ms.usecases.find-user-by-username :as usecaseFindUserByUsername]
            [clojure-general-ms.usecases.create-user :as usecaseCreateUser]
            [clojure-general-ms.usecases.find-account-by-id :as usecaseFindAccountById]
            [clojure-general-ms.usecases.create-account :as usecaseCreateAccount]
            [clojure-general-ms.usecases.create-transaction :as usecaseCreateTransaction]))

(defn get-beans []
  (let [userDatabaseGateway (userDatabaseGateway/->UserDatabaseGatewayImpl)
        accountDatabaseGateway (accountDatabaseGateway/->AccountDatabaseGatewayImpl)
        transactionDatabaseGateway (transactionDatabaseGateway/->TransactionDatabaseGatewayImpl)

        usecaseFindUserById (usecaseFindUserById/execute userDatabaseGateway)
        usecaseFindUserByUsername (usecaseFindUserByUsername/execute userDatabaseGateway)
        usecaseCreateUser (usecaseCreateUser/execute userDatabaseGateway)
        usecaseFindAccountById (usecaseFindAccountById/execute accountDatabaseGateway)
        usecaseCreateAccount (usecaseCreateAccount/execute userDatabaseGateway accountDatabaseGateway)
        usecaseCreateTransaction (usecaseCreateTransaction/execute transactionDatabaseGateway userDatabaseGateway accountDatabaseGateway)]
    {:usecaseFindUserById       usecaseFindUserById
     :usecaseCreateUser         usecaseCreateUser
     :usecaseFindUserByUsername usecaseFindUserByUsername
     :usecaseFindAccountById    usecaseFindAccountById
     :usecaseCreateAccount      usecaseCreateAccount
     :usecaseCreateTransaction  usecaseCreateTransaction}))