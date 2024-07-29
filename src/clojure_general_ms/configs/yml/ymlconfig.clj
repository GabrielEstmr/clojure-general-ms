(ns clojure-general-ms.configs.yml.ymlconfig
  (:import [org.yaml.snakeyaml Yaml])
  (:require [clojure.java.io :as io]))

(defn read-config [file]
  (let [yaml (Yaml.)
        input-stream (io/input-stream file)]
    (.load yaml input-stream)))

;; Example usage
;(def config (read-config "./src/resources/config.yml"))
(def config (read-config "./resources/config.yml"))

(println config)