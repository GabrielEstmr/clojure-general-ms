(ns clojure-general-ms.core
  (:require [clojure-general-ms.gateways.ws.routes.app-routes :as app-routes]
            [clojure-general-ms.configs.kafka.listeners-configuration :as listeners-configuration]
            [clojure-general-ms.configs.kafka.listener2 :as listener2]
            [clojure-general-ms.gateways.ws.middlewares.custom-exception-handler :as custom-exception-handler]
            [compojure.core :refer :all]
            [clojure-general-ms.configs.mongo.mongo-config-doc :as mongo-config]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.json :refer [wrap-json-response]])
  (:gen-class))

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
    ;(listener2/start-consumer-thread)
    (listeners-configuration/start-listeners)
    (run-jetty app {:port 8081 :join? false})
    (catch Exception e
      (println (.getMessage e)))))
