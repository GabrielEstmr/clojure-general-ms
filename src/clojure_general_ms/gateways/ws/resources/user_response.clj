(ns clojure-general-ms.gateways.ws.resources.user-response
  (:require [clojure-general-ms.utils.date-utils :as date-utils]))

(defn create-user-response-all-args [id first_name last_name username age company created_date last_modified_date]
  {:id                 id
   :first_name         first_name
   :last_name          last_name
   :username           username
   :age                age
   :company            company
   :created_date       (date-utils/format-local-date-time-to-string-default created_date)
   :last_modified_date (date-utils/format-local-date-time-to-string-default last_modified_date)})

(defn create-user-response [user]
  (let [{:keys [id
                first-name
                last-name
                username
                age
                company
                created-date
                last-modified-date]} user]
    (create-user-response-all-args
      id
      first-name
      last-name
      username
      age
      company
      created-date
      last-modified-date)))