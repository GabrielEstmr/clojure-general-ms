(ns clojure-general-ms.core
  (:require [clojure-general-ms.configs.yml.yml-config :as config])
  (:gen-class))

(defn -main []
  (println "App Name:" (get-in config/config ["app" "name"]))
  (println "Database Host:" (get-in config/config ["database" "host"])))
