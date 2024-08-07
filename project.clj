(defproject clojure-general-ms "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :repositories [["clojars" "https://repo.clojars.org/"]]
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.yaml/snakeyaml "1.29"]
                 [org.mongodb/mongodb-driver-sync "4.6.0"]
                 [cheshire "5.10.0"]
                 [org.slf4j/slf4j-api "1.7.30"]
                 [ring/ring-core "1.9.4"]
                 [ring/ring-jetty-adapter "1.9.4"]
                 [ring/ring-defaults "0.3.3"]
                 [ring/ring-json "0.5.0"]
                 [compojure "1.6.2"]
                 [ch.qos.logback/logback-classic "1.2.3"]
                 [http-kit "2.3.0"]
                 [org.clojure/data.json "2.4.0"]
                 [org.mongodb/bson "4.9.1"]
                 [org.apache.kafka/kafka-clients "3.4.0"]
                 [org.clojure/tools.logging "1.2.4"]
                 [ring-cors "0.1.13"]]
  :plugins [[lein-cloverage "1.2.4"]]
  :java-source-paths ["src/clojure_general_ms/java"]
  :main ^:skip-aot clojure-general-ms.core
  :resource-paths ["resources"]
  :target-path "target/%s"
  :profiles {:uberjar   {:aot      :all
                         :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}
             :cloverage {
                         :cloverage {:ns-exclude-regex "^clojure-general-ms\\.(gateways|configs)\\..*"}}}
  :prep-tasks ["javac" "compile"])
