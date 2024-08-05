(ns clojure-general-ms.gateways.ws.resources.create-user-request
  (:require [clojure-general-ms.domains.user :as user]
            [clojure.spec.alpha :as s]))

(defn to-domain [user-request]
  (let [{:keys [first_name
                last_name
                username
                age
                company]} user-request]
    (user/create-user
      first_name
      last_name
      username
      age
      company)))


(s/def ::first_name string?)
(s/def ::last_name string?)
(s/def ::username string?)
(s/def ::age pos-int?)
(s/def ::company string?)

(s/def ::user (s/keys :req-un [::first_name ::last_name ::username ::age ::company]))

;(defn validate? [user]
;  (s/valid? ::user user))


(defn validate [user]
  (let [validation-result (s/explain-data ::user user)]
    (if validation-result
      {:valid? false
       :errors (mapv (fn [{:keys [path pred]}]
                       (let [field (name (first path))]
                         (str "Invalid value for " field ": does not satisfy " (str pred))))
                     (:clojure.spec.alpha/problems validation-result))}
      {:valid? true
       :data user})))


