(ns clojure-general-ms.domains.account)

(defn create-account-all-args [id user-id created-date last-modified-date]
  {:id                 id
   :user-id            user-id
   :created-date       created-date
   :last-modified-date last-modified-date})

(defn create-account [user-id]
  {:user-id user-id})

(defn get-user-id [account]
  (when account
    (get account :user-id nil)))