(ns nquick.util
  (:require [clojure.java.io :as io]
            [clojure.tools.cli :refer [cli]]))

(defn- user-prop [k]
  (System/getProperty (str "user." k)))

(def home-directory (user-prop "home"))
(def nquick-directory (str home-directory "/.nquick"))
(def nquick-default-file (str nquick-directory "/default.txt"))

(defn check-dir [filename]
  (if (not (.isDirectory (io/file nquick-directory)))
    (do 
      (.mkdir (io/file nquick-directory))
      (spit nquick-default-file "")
      (println "dirs created"))
    (println "ur good lol")))