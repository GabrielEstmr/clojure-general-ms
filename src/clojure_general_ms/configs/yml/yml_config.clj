(ns clojure-general-ms.configs.yml.yml-config
  (:import (java.util LinkedHashMap List Map)
           [org.yaml.snakeyaml Yaml])
  (:require [clojure.java.io :as io]))

(defn read-config [file]
  (let [yaml (Yaml.)
        input-stream (io/input-stream file)]
    (.load yaml input-stream)))

;; Example usage
;(def config (read-config "./src/resources/config.yml"))
(def config (read-config "./resources/config.yml"))

(defn get-app-configs []
  config)

(defn get-by-path
  [^LinkedHashMap path]
  (let [keys (clojure.string/split path #"\.")]
    (reduce
      (fn [m k] (if (instance? LinkedHashMap m)
                  (.get m k)
                  nil))
      config
      keys)))


(defn java-to-clj [data]
  (cond
    (instance? Map data) (into {} (for [[k v] data]
                                    [(keyword k) (java-to-clj v)]))
    (instance? List data) (vec (map java-to-clj data))
    :else data))