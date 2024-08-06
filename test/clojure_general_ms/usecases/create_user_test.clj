(ns clojure-general-ms.usecases.create-user-test
  (:require [clojure.test :refer :all])
  (:require [clojure-general-ms.usecases.create-user :as create-user]
            [clojure-general-ms.gateways.user-database-gateway :as user-database-gateway]))

(defrecord UserDatabaseGatewayImplMock []
  user-database-gateway/UserDatabaseGateway
  (save [_ user]
    user)
  (find-by-id [_ id]
    id)
  (find-by-username [_ username]
    username))


(deftest test-execute
  (let [
        provider (create-user/execute (->UserDatabaseGatewayImplMock))
        user {:first-name "John" :last-name "Doe" :age 30 :company "ACME"}
        result (provider user)]

    (is (= user result) "The user should be saved correctly.")))
