(ns clojure-general-ms.gateways.ws.resources.create-account-request
  (:require [clojure-general-ms.domains.account :as account]))

(defn to-domain [user-request]
  (let [{:keys [user_id]} user-request]
    (account/create-account
      user_id)))