(ns clojure-general-ms.usecases.beans.usecase-beans
  (:require [clojure-general-ms.gateways.mongodb.user-database-gateway-impl :as userDatabaseGateway]
            [clojure-general-ms.usecases.find-user-by-id :as usecaseFindUserById]))


;(defn get-beans []
;  (let [userDatabaseGateway (userDatabaseGateway/->UserDatabaseGatewayImpl)
;        usecaseFindUserById (usecaseFindUserById/make-find-user-by-id userDatabaseGateway)]
;    {:usecaseFindUserById usecaseFindUserById}))


(defn get-beans []
  (let [userDatabaseGateway (userDatabaseGateway/->UserDatabaseGatewayImpl)
        usecaseFindUserById (usecaseFindUserById/find-user-by-id userDatabaseGateway)]
    {:usecaseFindUserById usecaseFindUserById}))