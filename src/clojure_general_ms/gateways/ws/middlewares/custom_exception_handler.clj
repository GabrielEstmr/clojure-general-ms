(ns clojure-general-ms.gateways.ws.middlewares.custom-exception-handler
  (:import
    (src.domains.exceptions BadRequestException JsonUtilsException ResourceNotFoundException)
    (org.eclipse.jetty.http HttpStatus)))

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
        (build-error-response (HttpStatus/INTERNAL_SERVER_ERROR_500) e))
      (catch BadRequestException e
        (build-error-response-msg (HttpStatus/BAD_REQUEST_400) (.getMessage e)))
      (catch ResourceNotFoundException e
        (build-error-response-msg (HttpStatus/NOT_FOUND_404) (.getMessage e)))
      (catch Exception e
        (build-error-response-msg (HttpStatus/INTERNAL_SERVER_ERROR_500) (.getMessage e))))))