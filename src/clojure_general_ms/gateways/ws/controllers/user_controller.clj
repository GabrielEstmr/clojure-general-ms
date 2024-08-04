(ns clojure-general-ms.gateways.ws.controllers.user-controller
  (:require [clojure.data.json :as json]
            [compojure.core :refer :all]
            [clojure-general-ms.gateways.ws.resources.user-response :as user-response]
            [clojure-general-ms.gateways.ws.resources.create-user-request :as create-user-request]
            [clojure-general-ms.usecases.beans.usecase-beans :as usecase-beans]
            [ring.util.response :as response])
  (:import [clojure_general_ms.java.domains.exceptions ResourceNotFoundException]))

(defn find-user-by-id-handler [id]
  (let [usecaseFindUserById (:usecaseFindUserById (usecase-beans/get-beans))
        user (usecaseFindUserById id)
        response-body (json/write-str (user-response/create-user-response user))]

    (try
      (println "OK")
      (-> (response/response response-body)
          (response/status 201)
          (response/content-type "application/json"))
      (catch Exception e
        (let [error-body (json/write-str {:status  "error"
                                          :message (.getMessage e)})]
          (-> (response/response error-body)
              (response/status 500)
              (response/content-type "application/json")))))))

(defn risky-operation []
  (throw (ResourceNotFoundException. "Something went wrong")))

(defn create-user-handler [request]
  (let [usecaseCreateUser (:usecaseCreateUser (usecase-beans/get-beans))
        body (slurp (:body request))
        user-request (json/read-str body :key-fn keyword)]
    ;(risky-operation)
    (try
      (let [created-user (usecaseCreateUser (create-user-request/to-domain user-request))
            response-body (json/write-str (user-response/create-user-response created-user))]
        (println "OK")
        (-> (response/response response-body)
            (response/status 201)
            (response/content-type "application/json")))
      (catch Exception e
        (let [error-body (json/write-str {:status  "error"
                                          :message (.getMessage e)})]
          (-> (response/response error-body)
              (response/status 500)
              (response/content-type "application/json")))))))