(ns clojure-general-ms.core
  (:require [clojure.data.json :as json]
            [compojure.core :refer :all]
            [clojure-general-ms.configs.mongo.mongo-config-doc :as mongo-config]
            [clojure-general-ms.usecases.beans.usecase-beans :as usecase-beans]
            [compojure.route :as route]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.util.response :as response]))

(defn user-handler []
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    {:first_name         "Gabriel"
             :last_name          "Gabriel"
             :age                "Gabriel"
             :company            "Gabriel"
             :created_date       "2000-10-31T01:30:00.000-05:00"
             :last_modified_date "2000-10-31T01:30:00.000-05:00"}})

(defn response [id]
  (let [usecaseFindUserById (:usecaseFindUserById (usecase-beans/get-beans))]
    (println "Subtract result v2" (usecaseFindUserById id))))


;(defn user-handler-v2 []
;  {:status  200
;   :headers {"Content-Type" "application/json"}
;   :body    (response "b6361da2-0a49-4499-9fe2-253b1bf71ee3")})
;
;
;(defroutes app-routes
;           (GET "/users" [] (user-handler-v2))
;           ;(GET "/users/:id" [id]
;           ;  (let [user (find-user-by-id/find-user-by-id (Integer. id))]
;           ;    (if user
;           ;      (ring.util.response/response user)
;           ;      (ring.util.response/status 404 "User not found"))))
;           (route/not-found "Not Found"))


(defn user-handler-v2 [id]
  (let [usecaseFindUserById (:usecaseFindUserById (usecase-beans/get-beans))
        user (usecaseFindUserById id)]
    (if user
      (response/response
        (json/write-str
          {:first_name (get user :first_name)
           :last_name (get user :last_name)
           :age (get user :age)
           :company (get user :company)
           :created_date (get user :created_date)
           :last_modified_date (get user :last_modified_date)}))
      (response/response {:error "User not found"}))))

(defn create-user-handler [request]
  (let [usecaseCreateUser (:usecaseCreateUser (usecase-beans/get-beans))
        body (slurp (:body request))
        user (json/read-str body :key-fn keyword)]
    (try
      (let [created-user (usecaseCreateUser user)]
        (response/response
          (json/write-str {:status "success"
                           :data (-> created-user
                                     (.toJson)
                                     (json/read-str :key-fn keyword))})
          {:status 201
           :headers {"Content-Type" "application/json"}}))
      (catch Exception e
        (response/response
          (json/write-str {:status "error"
                           :message (.getMessage e)}))))))

(defroutes app-routes
           (POST "/users" request (create-user-handler request))
           (GET "/users/:id" [id]
             (let [user (user-handler-v2 (str id))]
               (if user
                 (response/response user)
                 (response/status 404 "User not found"))))
           (route/not-found "Not Found"))


(def app
  (wrap-json-response app-routes))

(defn -main [& args]
  (try
    (mongo-config/ping-database)
    (run-jetty app {:port 8080 :join? false})
    (catch Exception e
      (println (.getMessage e)))))
