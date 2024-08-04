(ns clojure-general-ms.utils.date-utils
  (:import (java.time LocalDateTime ZoneId)))

(defonce system-zone-id (ZoneId/systemDefault))

(defn date-to-local-datetime [date]
  (let [instant (.toInstant date)]
    (LocalDateTime/ofInstant instant system-zone-id)))