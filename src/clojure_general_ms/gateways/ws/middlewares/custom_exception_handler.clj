(ns clojure-general-ms.gateways.ws.middlewares.custom-exception-handler
  (:require [clojure.data.json :as json])
  (:import [clojure_general_ms.java.domains.exceptions JsonUtilsException ResourceNotFoundException]))

(defn build-error-message-body [msg]
  {
   :message msg
   })

(defn build-error-response [status e]
  {:status  status
   :headers {"Content-Type" "application/json"}
   :body    (build-error-message-body (.getMessage e))})

(defn build-error-response-msg [status msg]
  {:status  status
   :headers {"Content-Type" "application/json"}
   :body    (build-error-message-body msg)})

(defn custom-exception-handler [handler]
  (fn [request]
    (try
      (handler request)
      (catch JsonUtilsException e
        (build-error-response 500 e))
      (catch ResourceNotFoundException e
        (build-error-response-msg 404 (.getMessage e)))
      (catch Exception e
        (build-error-response-msg 500 (.getMessage e))))))