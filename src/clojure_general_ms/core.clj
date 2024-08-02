(ns clojure-general-ms.core
  (:require [clojure-general-ms.gateways.ws.routes.app-routes :as app-routes]
            [compojure.core :refer :all]
            [clojure-general-ms.configs.mongo.mongo-config-doc :as mongo-config]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.json :refer [wrap-json-response]]))

(def app
  (wrap-json-response app-routes/app-routes))

(defn -main [& args]
  (try
    (mongo-config/ping-database)
    (run-jetty app {:port 8080 :join? false})
    (catch Exception e
      (println (.getMessage e)))))
