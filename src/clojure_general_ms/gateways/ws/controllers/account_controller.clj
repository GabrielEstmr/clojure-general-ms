(ns clojure-general-ms.gateways.ws.controllers.account-controller
  (:require [clojure.data.json :as json]
            [compojure.core :refer :all]
            [clojure-general-ms.gateways.ws.resources.account-response :as account-response]
            [clojure-general-ms.gateways.ws.resources.create-account-request :as create-account-request]
            [clojure-general-ms.usecases.beans.usecase-beans :as usecase-beans]
            [ring.util.response :as response]))

(defn find-account-by-id-handler [id]
  (let [usecaseFindAccountById (:usecaseFindAccountById (usecase-beans/get-beans))
        account (usecaseFindAccountById id)
        response-body (json/write-str (account-response/create-account-response account))]
    (-> (response/response response-body)
        (response/status 201)
        (response/content-type "application/json"))))

(defn create-account-handler [request]
  (let [usecaseCreateAccount (:usecaseCreateAccount (usecase-beans/get-beans))
        body (slurp (:body request))
        account-request (json/read-str body :key-fn keyword)]
    (let [created-account (usecaseCreateAccount (create-account-request/to-domain account-request))
          response-body (json/write-str (account-response/create-account-response created-account))]
      (println "OK")
      (-> (response/response response-body)
          (response/status 201)
          (response/content-type "application/json")))))