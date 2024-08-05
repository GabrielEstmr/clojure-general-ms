(ns clojure-general-ms.domains.user)

(defn create-user-all-args [id first-name last-name username age company created-date last-modified-date]
  {:id                 id
   :first-name         first-name
   :last-name          last-name
   :username           username
   :age                age
   :company            company
   :created-date       created-date
   :last-modified-date last-modified-date})

(defn create-user [first-name last-name username age company]
  {:first-name first-name
   :last-name  last-name
   :username   username
   :age        age
   :company    company})

(defn get-id [user]
  (when user
    (get user :id nil)))