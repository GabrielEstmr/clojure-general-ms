(ns clojure-general-ms.gateways.ws.controllers.user-controller
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure-general-ms.usecases.beans.usecase-beans :as usecase-beans]
            [ring.util.response :refer [response]]))

;(defn create-user [req]
;  (let [user (assoc (-> req :body)
;               :created_date (time/instant)
;               :last_modified_date (time/instant))]
;    (db/save-user user)
;    (response {:message "User created successfully"})))


(defn post-twitter-handler
  [req]
  (let [tweet-json (:body req)
        saved (try
                (d/post-tweet tweet-json)
                (catch Exception e
                  (do
                    (log/error e)
                    false)))]
    (log/info tweet-json)
    {:status  201
     :headers {"Content-Type" "text/html"}
     :body    "test"}))

(defroutes app-routes
           (POST "/tweets" [] (mj/wrap-json-body post-twitter-handler {:keywords? true :bigdecimals? true})))


;(defn get-user [id]
;  (if-let [usecaseFindUserById (:usecaseFindUserById usecase-beans/get-beans)]
;    (response {:msg (usecaseFindUserById id)})
;    (response {:error "User not found"} 404)))
;
;(defroutes app-routes
;           ;(POST "/users" req (create-user req))
;           (GET "/users/:id" [id] (get-user id))
;           (route/not-found "Not Found"))