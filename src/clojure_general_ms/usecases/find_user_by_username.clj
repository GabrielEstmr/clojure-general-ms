(ns clojure-general-ms.usecases.find-user-by-username
  (:import (clojure_general_ms.java.domains.exceptions ResourceNotFoundException)))

(defn execute [userDatabaseGateway]
  (fn [username]
    (let [user (.find-by-username userDatabaseGateway username)]
      (when-not user
        (throw (ResourceNotFoundException. "User not found")))
      user)))