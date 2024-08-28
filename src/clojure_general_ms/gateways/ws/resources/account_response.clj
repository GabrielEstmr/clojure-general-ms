(ns clojure-general-ms.gateways.ws.resources.account-response
  (:require [clojure-general-ms.utils.date-utils :as date-utils]))

(defn create-account-response-all-args [id user-id created-date last-modified-date]
  {:id                 id
   :user_id            user-id
   :created_date       (date-utils/format-local-date-time-to-string-default created-date)
   :last_modified_date (date-utils/format-local-date-time-to-string-default last-modified-date)})

(defn create-account-response [account]
  (let [{:keys [id
                user-id
                created-date
                last-modified-date]} account]
    (create-account-response-all-args
      id
      user-id
      created-date
      last-modified-date)))