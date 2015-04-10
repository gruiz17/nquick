(ns nquick.core
  (:require [nquick.core :as core]
            [nquick.util :as util]
            [clojure.string :as string]
            [clojure.tools.cli :refer [parse-opts]]))

(defn flush 
  ([] ())
  ([filename] ()))

;; helper function that destroys all notes
(defn- the-nuclear-option
  ([] ()))

(defn read
  ([] ())
  ([filename] ()))

(defn write [& args] ())

;; helper function to optionally give a title to the notes
(defn- write-title-helper [] 
  (let [title (read-line)]
    ()))

;; helper to choose to overwrite or append to a note
(defn- overwrite-or-append [] 
  (let [answer (read-line)] 
    ()))

(defn names [] ())

(defn help [] ())

(def command-map
  {:flush flush
   :read read
   :write write
   :names names
   :help help })
