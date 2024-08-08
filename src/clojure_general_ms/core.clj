(ns clojure-general-ms.core
  (:require [clojure-general-ms.gateways.ws.routes.app-routes :as app-routes]
            [clojure-general-ms.configs.kafka.listeners-configuration :as listeners-configuration]
            [clojure-general-ms.gateways.ws.middlewares.custom-exception-handler :as custom-exception-handler]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [clojure-general-ms.configs.yml.yml-config :as yml-config]
            [compojure.core :refer :all]
            [clojure-general-ms.configs.mongo.mongo-config-doc :as mongo-config]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.middleware.cors :refer [wrap-cors]])
  (:gen-class)
  (:import (java.util LinkedHashMap)))

;(def app
;  (-> app-routes/app-routes
;      custom-exception-handler/custom-exception-handler
;      (wrap-json-response)
;      (wrap-cors :access-control-allow-origin [#".*"]
;                 :access-control-allow-methods [:get :post :put :delete :options]
;                 :access-control-allow-headers ["Content-Type" "Authorization"])
;      (wrap-defaults site-defaults)))

(def app
  (wrap-json-response (
                        custom-exception-handler/custom-exception-handler
                        app-routes/app-routes)))

(defn -main [& args]
  (try
    (mongo-config/ping-database)
    (listeners-configuration/start-consumers)
    (run-jetty app {:port 8080 :join? false})
    (catch Exception e
      (println (.getMessage e)))))
