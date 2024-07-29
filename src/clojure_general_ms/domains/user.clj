(ns clojure-general-ms.domains.user)

(defn create-user [first-name last-name age company]
  {:first_name         first-name
   :last_name          last-name
   :age                age
   :company            company})

(defn create-user [first-name last-name age company created-date last-modified-date]
  {:first_name         first-name
   :last_name          last-name
   :age                age
   :company            company
   :created_date       created-date
   :last_modified_date last-modified-date})