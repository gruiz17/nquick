(ns nquick.core
  (:require [nquick.core :as core]
            [nquick.util :as util]
            [clojure.java.io :as io]
            [clojure.string :as string]
            [clojure.tools.cli :refer [parse-opts]]))

(defn flush 
  ([] ())
  ([filename] ()))

;; helper function that destroys all notes
(defn- the-nuclear-option
  ([] ()))

;; helper to ask if sure
(defn- ask-if-sure 
  (let [answer (read-line)]
    ()))

(defn read
  ([] (println (slurp util/nquick-default-file)))
  ([filename] 
   (let [full-path (str util/nquick-directory "/" (util/mangle-name title))]
     (if (not (.exists (io/file full-path)))
       (println "file does not exist!")
       (println (slurp full-path))))))

(defn write [& args] ())

;; helper function to optionally give a title to the notes
(defn- write-title-helper [text] 
  (let [title (read-line)]
    (cond
     (string/blank? title)
      (do
        (println "written to default!")
        (spit util/nquick-default-file text :append true))
      (.exists (io/file (str util/nquick-directory "/" (util/mangle-name title))))
      (do
        (println "that note already exists! pick another title.")
        (write-title-helper text))
      :else 
      (do 
        (println (str "written to " title "!"))
        (spit (str util/nquick-directory "/" (util/mangle-name title)) text)))))

(defn names [] 
  (doseq [i (range (count nquick.util/home-files-vec))]
    (println (get nquick.util/home-files-vec i))))

(defn help [] ())

(def command-map
  {:flush flush
   :read read
   :write write
   :names names
   :help help })
