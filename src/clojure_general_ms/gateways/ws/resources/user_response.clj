(ns clojure-general-ms.gateways.ws.resources.user-response
  (:require [clojure-general-ms.utils.date-utils :as date-utils]))

(defn create-user-response-all-args [id first-name last-name age company created-date last-modified-date]
  {:id                 id
   :first-name         first-name
   :last-name          last-name
   :age                age
   :company            company
   :created-date       (date-utils/format-local-date-time-to-string-default created-date)
   :last-modified-date (date-utils/format-local-date-time-to-string-default last-modified-date)})

(defn create-user-response [user]
  (let [{:keys [id
                first-name
                last-name
                age
                company
                created-date
                last-modified-date]} user]
    (create-user-response-all-args
      id
      first-name
      last-name
      age
      company
      created-date
      last-modified-date)))