(ns clojure-general-ms.gateways.ws.routes.app-routes
  (:require [clojure-general-ms.gateways.ws.controllers.user-controller :as user-controller]
            [clojure-general-ms.gateways.ws.controllers.account-controller :as account-controller]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :as response]))

(defroutes app-routes
           (POST "/api/v1/users" request (user-controller/create-user-handler request))
           (GET "/api/v1/users/:id" [id] (user-controller/find-user-by-id-handler id))
           (GET "/api/v1/users/username/:username" [username] (user-controller/find-user-by-username-handler username))
           (POST "/api/v1/accounts" request (account-controller/create-account-handler request))
           (GET "/api/v1/accounts/:id" [id] (account-controller/find-account-by-id-handler id))
           (GET "/swagger-ui" [] (response/resource-response "index.html" {:root "openapi"}))
           (GET "/" [] (slurp "resources/openapi/index.html"))
           (route/resources "/") ;; This line serves static files from resources/public
           (route/not-found "Not Found"))
