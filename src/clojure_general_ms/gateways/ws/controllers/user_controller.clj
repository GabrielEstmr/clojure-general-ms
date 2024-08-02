(ns clojure-general-ms.gateways.ws.controllers.user-controller
  (:require [clojure.data.json :as json]
            [compojure.core :refer :all]
            [clojure-general-ms.gateways.ws.resources.user-responses :as user-responses]
            [clojure-general-ms.usecases.beans.usecase-beans :as usecase-beans]
            [ring.util.response :as response]))

(defn find-user-by-id-handler [id]
  (let [usecaseFindUserById (:usecaseFindUserById (usecase-beans/get-beans))
        user (usecaseFindUserById id)
        response-body (json/write-str (user-responses/create-user-response user))]

    (try
      (println "OK")
      (-> (response/response response-body)
          (response/status 201)
          (response/content-type "application/json"))
      (catch Exception e
        (let [error-body (json/write-str {:status "error"
                                          :message (.getMessage e)})]
          (-> (response/response error-body)
              (response/status 500)
              (response/content-type "application/json")))))))

(defn create-user-handler [request]
  (let [usecaseCreateUser (:usecaseCreateUser (usecase-beans/get-beans))
        body (slurp (:body request))
        user (json/read-str body :key-fn keyword)]
    (try
      (let [created-user (usecaseCreateUser user)
            response-body (json/write-str (user-responses/create-user-response created-user))]
        (println "OK")
        (-> (response/response response-body)
            (response/status 201)
            (response/content-type "application/json")))
      (catch Exception e
        (let [error-body (json/write-str {:status "error"
                                          :message (.getMessage e)})]
          (-> (response/response error-body)
              (response/status 500)
              (response/content-type "application/json")))))))