(ns clojure-general-ms.gateways.ws.middlewares.custom-exception-handler
  (:require [clojure.data.json :as json]))

(defn custom-exception-handler [handler]
  (fn [request]
    (try
      (handler request)
      (catch Exception e
        ;; Customize your exception handling logic here
        {:status 500
         :headers {"Content-Type" "application/json"}
         :body (json/write-str {:error "Internal Server Error", :message (.getMessage e)})}))))