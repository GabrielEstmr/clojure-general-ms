(ns clojure-general-ms.utils.mongo-doc-utils
  (:import (java.util Objects)
           (org.bson Document)
           (org.bson.types ObjectId)))

;(defn doc-to-map [^Document doc]
;  (if (Objects/isNull doc)
;    nil)
;  (let [keys (.keySet doc)]
;    (reduce (fn [m k]
;              (assoc m (keyword k) (.get doc k)))
;            {}
;            keys)))

(defn doc-to-map [^Document doc]
  (when doc
    (let [keys (.keySet doc)]
      (reduce (fn [m k]
                (assoc m (keyword k) (.get doc k)))
              {}
              keys))))

(defn object-id-to-string [^ObjectId obj-id]
  (when obj-id
    (.toHexString obj-id)))

(defn string-to-object-id [^String id]
  (try
    (ObjectId. (str id))
    (catch IllegalArgumentException e
      nil)))