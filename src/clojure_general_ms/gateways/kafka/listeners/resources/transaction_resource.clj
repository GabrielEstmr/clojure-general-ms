(ns clojure-general-ms.gateways.kafka.listeners.resources.transaction-resource
  (:require [clojure-general-ms.domains.transaction :as transaction]))

(defn to-domain [transaction-resource]
  (let [{:keys [user_id
                account_id
                value]} transaction-resource]
    (transaction/create-transaction
      (str user_id)
      (str account_id)
      (double value))))