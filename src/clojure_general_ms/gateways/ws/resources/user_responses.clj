(ns clojure-general-ms.gateways.ws.resources.user-responses
  (:import [java.time.format DateTimeFormatter]))

(defn format-local-date-time [ldt]
  (let [formatter (DateTimeFormatter/ofPattern "yyyy-MM-dd'T'HH:mm:ss")]
    (.format ldt formatter)))


(defn create-user-response-all-args [id first-name last-name age company created-date last-modified-date]
  {:id                 id
   :first-name         first-name
   :last-name          last-name
   :age                age
   :company            company
   :created-date       (format-local-date-time created-date)
   :last-modified-date (format-local-date-time last-modified-date)})

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
