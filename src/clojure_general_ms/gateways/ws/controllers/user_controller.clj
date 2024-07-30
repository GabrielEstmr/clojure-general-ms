(ns clojure-general-ms.gateways.ws.controllers.user-controller
  (:require [compojure.core :refer :all]
            [ring.util.http-response :refer :all]
            [clojure.edn :as edn]))


(defn save-user-handler [request]
  (let [user (-> request :body slurp edn/read-string)]
    (db/save-user user)
    (ok {:status "User created successfully"})))

(defn get-user-handler [request]
  (let [id (-> request :params :id)
        user (db/find-user-by-id id)]
    (if user
      (ok user)
      (not-found {:status "User not found"}))))

(defroutes user-routes
           (POST "/user" request (save-user-handler request))
           (GET "/users/:id" request (get-user-handler request)))