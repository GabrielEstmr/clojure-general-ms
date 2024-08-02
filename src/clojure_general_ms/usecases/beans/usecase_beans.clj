(ns clojure-general-ms.usecases.beans.usecase-beans
  (:require [clojure-general-ms.gateways.mongodb.user-database-gateway-impl :as userDatabaseGateway]
            [clojure-general-ms.usecases.find-user-by-id :as usecaseFindUserById]
            [clojure-general-ms.usecases.create-user :as usecaseCreateUser]))

(defn get-beans []
  (let [userDatabaseGateway (userDatabaseGateway/->UserDatabaseGatewayImpl)
        usecaseFindUserById (usecaseFindUserById/execute userDatabaseGateway)
        usecaseCreateUser (usecaseCreateUser/execute userDatabaseGateway)]
    {:usecaseFindUserById usecaseFindUserById
     :usecaseCreateUser   usecaseCreateUser}))