(ns clojure-general-ms.usecases.find-account-by-id
  (:import
    (src.domains.exceptions ResourceNotFoundException)))

(defn execute [accountDatabaseGateway]
  (fn [id]
    (let [account (.find-by-id accountDatabaseGateway id)]
      (when-not account
        (throw (ResourceNotFoundException. "Account Not Found")))
      account)))