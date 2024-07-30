(defproject clojure-general-ms "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :repositories [["clojars" "https://repo.clojars.org/"]]
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.yaml/snakeyaml "1.29"]
                 [org.mongodb/mongodb-driver-sync "4.6.0"]
                 [cheshire "5.10.0"]
                 [org.slf4j/slf4j-api "1.7.30"]
                 [ring/ring-core "1.9.4"]
                 [ring/ring-jetty-adapter "1.9.4"]]
  :main ^:skip-aot clojure-general-ms.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
