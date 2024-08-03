(ns clojure-general-ms.domains.exceptions.application-exceptions
  (:gen-class
    :name ResourceNotFoundException
    :extends java.lang.Exception
    :constructors {[String] []})
  (:gen-class
    :name InternalServerErrorException
    :extends java.lang.Exception
    :constructors {[String] []}))


(defrecord ResourceNotFoundException [message]
  Throwable
  (getMessage [this] message)
  (getCause [this] nil)
  (printStackTrace [this] (println "Stack trace: " (.toString this)))
  Object
  (toString [this] (str "MyCustomException: " message)))

(defrecord InternalServerErrorException [message]
  Throwable
  (getMessage [this] message)
  (getCause [this] nil)
  (printStackTrace [this] (println "Stack trace: " (.toString this)))
  Object
  (toString [this] (str "MyCustomException: " message)))

