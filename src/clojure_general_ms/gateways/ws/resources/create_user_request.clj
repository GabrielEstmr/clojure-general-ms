(ns clojure-general-ms.gateways.ws.resources.create-user-request
  (:require [clojure-general-ms.domains.user :as user]))

(defn to-domain [user-request]
  (let [{:keys [first_name
                last_name
                age
                company]} user-request]
    (user/create-user
      first_name
      last_name
      age
      company)))