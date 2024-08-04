(ns clojure-general-ms.utils.mongo-doc-utils
  (:import (org.bson Document)
           (org.bson.types ObjectId)))

(defn doc-to-map [^Document doc]
  (let [keys (.keySet doc)]
    (reduce (fn [m k]
              (assoc m (keyword k) (.get doc k)))
            {}
            keys)))

(defn object-id-to-string [^ObjectId obj-id]
  (.toHexString obj-id))