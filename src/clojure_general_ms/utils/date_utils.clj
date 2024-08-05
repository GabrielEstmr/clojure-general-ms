(ns clojure-general-ms.utils.date-utils
  (:import (java.time LocalDateTime ZoneId)
           (java.time.format DateTimeFormatter)))

(defonce system-zone-id (ZoneId/systemDefault))
(defonce default-date-pattern (DateTimeFormatter/ISO_LOCAL_DATE_TIME))

;;TODO: review with when

(defn date-to-local-datetime [date]
  (let [instant (.toInstant date)]
    (LocalDateTime/ofInstant instant system-zone-id)))

(defn format-local-date-time-to-string [^LocalDateTime date ^DateTimeFormatter formatter]
  (.format date formatter))

(defn format-local-date-time-to-string-default [^LocalDateTime date]
  (.format date default-date-pattern))