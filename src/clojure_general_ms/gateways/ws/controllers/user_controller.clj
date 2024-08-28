(ns clojure-general-ms.gateways.ws.controllers.user-controller
  (:require [clojure.data.json :as json]
            [compojure.core :refer :all]
            [clojure-general-ms.gateways.ws.resources.user-response :as user-response]
            [clojure-general-ms.gateways.ws.resources.create-user-request :as create-user-request]
            [clojure-general-ms.usecases.beans.usecase-beans :as usecase-beans]
            [ring.util.response :as response])
  (:import (src.domains.exceptions BadRequestException)))

(defn find-user-by-id-handler [id]
  (let [usecaseFindUserById (:usecaseFindUserById (usecase-beans/get-beans))
        user (usecaseFindUserById id)
        response-body (json/write-str (user-response/create-user-response user))]
    (-> (response/response response-body)
        (response/status 201)
        (response/content-type "application/json"))))

(defn find-user-by-username-handler [username]
  (let [usecaseFindUserById (:usecaseFindUserByUsername (usecase-beans/get-beans))
        user (usecaseFindUserById username)
        response-body (json/write-str (user-response/create-user-response user))]
    (-> (response/response response-body)
        (response/status 201)
        (response/content-type "application/json"))))

(defn create-user-handler [request]
  (let [usecaseCreateUser (:usecaseCreateUser (usecase-beans/get-beans))
        body (slurp (:body request))
        user-request (json/read-str body :key-fn keyword)
        valid (create-user-request/validate user-request)]
    (if-not (:valid? valid)
      (throw (BadRequestException. (str (:errors valid))))
      (let [created-user (usecaseCreateUser (create-user-request/to-domain user-request))
            response-body (json/write-str (user-response/create-user-response created-user))]
        (-> (response/response response-body)
            (response/status 201)
            (response/content-type "application/json"))))))
