(ns clojure-general-ms.core
  (:require [compojure.core :refer :all]
            [clojure-general-ms.usecases.beans.usecase-beans :as usecase-beans]
            [compojure.route :as route]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.json :refer [wrap-json-response]]))

(defn user-handler []
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body {:first_name "Gabriel"
          :last_name "Gabriel"
          :age "Gabriel"
          :company "Gabriel"
          :created_date "2000-10-31T01:30:00.000-05:00"
          :last_modified_date "2000-10-31T01:30:00.000-05:00"}})

(defn response [id]
  (let [usecaseFindUserById (:usecaseFindUserById (usecase-beans/get-beans))]
    (println "Subtract result v2" (usecaseFindUserById id))))


(defn user-handler-v2 []
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (response "b6361da2-0a49-4499-9fe2-253b1bf71ee3")})


(defroutes app-routes
           (GET "/users" [] (user-handler-v2))
           ;(GET "/users/:id" [id]
           ;  (let [user (find-user-by-id/find-user-by-id (Integer. id))]
           ;    (if user
           ;      (ring.util.response/response user)
           ;      (ring.util.response/status 404 "User not found"))))
           (route/not-found "Not Found"))


(def app
  (wrap-json-response app-routes))

(defn -main [& args]
  (run-jetty app {:port 8080 :join? false}))