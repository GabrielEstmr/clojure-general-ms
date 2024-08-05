(ns clojure-general-ms.usecases.find-user-by-id
  (:import
    (clojure_general_ms.java.domains.exceptions ResourceNotFoundException)))

(defn execute [userDatabaseGateway]
  (fn [id]
    (let [user (.find-by-id userDatabaseGateway id)]
      (when-not user
        (throw (ResourceNotFoundException. "User not found")))
      user)))
