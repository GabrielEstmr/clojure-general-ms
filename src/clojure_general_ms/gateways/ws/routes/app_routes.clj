(ns clojure-general-ms.gateways.ws.routes.app-routes
  (:require [compojure.core :refer :all]))

;(def post-users (POST "/users" request (create-user-handler request)))
;(def get-users-by-id (GET "/users/:id" [id]
;                    (let [user (user-handler-v2 (str id))]
;                      (if user
;                        (response/response user)
;                        (response/status 404 "User not found")))))
;
;
;(defn get-routes []
;  {:usecaseFindUserById post-users
;   :usecaseCreateUser   get-users-by-id})