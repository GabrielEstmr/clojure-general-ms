(ns clojure-general-ms.configs.kafka.listeners-configuration
  (:require [clojure-general-ms.configs.yml.yml-config :as yml-config]
            [clojure-general-ms.gateways.kafka.listeners.transaction-listener :as transaction-listener])
  (:import (java.util Collections Properties)
           (java.util.concurrent Executors)
           [org.apache.kafka.clients.consumer ConsumerConfig KafkaConsumer]
           (org.apache.kafka.common.serialization StringDeserializer)))

(defn create-consumer [topic]
  (let [props (doto (Properties.)
                (.put ConsumerConfig/BOOTSTRAP_SERVERS_CONFIG (yml-config/get-by-path "kafka.default-config.consumer.bootstrap-servers"))
                (.put ConsumerConfig/GROUP_ID_CONFIG (yml-config/get-by-path "kafka.default-config.consumer.group-id"))
                (.put ConsumerConfig/KEY_DESERIALIZER_CLASS_CONFIG StringDeserializer)
                (.put ConsumerConfig/VALUE_DESERIALIZER_CLASS_CONFIG StringDeserializer)
                (.put ConsumerConfig/AUTO_OFFSET_RESET_CONFIG (yml-config/get-by-path "kafka.default-config.consumer.auto-offset-reset"))
                (.put ConsumerConfig/MAX_POLL_RECORDS_CONFIG (int (yml-config/get-by-path "kafka.default-config.consumer.max-poll-records")))
                (.put ConsumerConfig/SESSION_TIMEOUT_MS_CONFIG (int (yml-config/get-by-path "kafka.default-config.consumer.session-timeout-ms")))
                (.put ConsumerConfig/MAX_POLL_INTERVAL_MS_CONFIG (int (yml-config/get-by-path "kafka.default-config.consumer.max-poll-interval-ms")))
                (.put ConsumerConfig/ENABLE_AUTO_COMMIT_CONFIG "false"))
        consumer (KafkaConsumer. props)]
    (.subscribe consumer (Collections/singletonList topic))
    consumer))

(defn listener-starter [listener-handler ^KafkaConsumer consumer]
  (loop []
    (let [records (.poll consumer 1000)]
      (when (seq records)
        (doseq [record records]
          (listener-handler consumer record)))
      (recur))))

(defn start-listeners []
  (let [consumer (create-consumer (yml-config/get-by-path "kafka.topics.transactions.name"))]
    (Thread. (fn [] (listener-starter transaction-listener/listen consumer)))  ; Start consumer in a new thread
    (.start (Thread. (fn [] (listener-starter transaction-listener/listen consumer)))))) ; Ensure the thread starts