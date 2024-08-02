(ns clojure-general-ms.gateways.ws.routes.app-routes
  (:require [clojure-general-ms.gateways.ws.controllers.user-controller :as user-controller]
            [compojure.core :refer :all]
            [compojure.route :as route]))

(defroutes app-routes
           (POST "/users" request (user-controller/create-user-handler request))
           (GET "/users/:id" [id] (user-controller/find-user-by-id-handler id))
           (route/not-found "Not Found"))