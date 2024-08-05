(ns clojure-general-ms.usecases.create-user
  (:require [clojure-general-ms.usecases.find-user-by-id :as find-user-by-id]
            [clojure-general-ms.domains.user :as user])
  (:import [clojure_general_ms.java.domains.exceptions ResourceNotFoundException]))

(defn execute [userDatabaseGateway]
  (fn [user]
    (let [saved-user (.save userDatabaseGateway user)]
      (println "==============================> Print inside usecase create-user")
      saved-user)))

;(defn execute [userDatabaseGateway]
;  (fn [user]
;    (let [find-user-by-id-fn (find-user-by-id/execute userDatabaseGateway)
;          already-persisted-user (find-user-by-id-fn (user/get-id user))]
;      (when already-persisted-user
;        (throw (ResourceNotFoundException. "Business or conflict exception")))
;      (let [saved-user (.save userDatabaseGateway user)]
;        saved-user))))

