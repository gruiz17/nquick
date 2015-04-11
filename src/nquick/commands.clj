(ns nquick.commands
  (:require [nquick.core :as core]
            [nquick.util :as util]
            [clojure.java.io :as io]
            [clojure.string :as string]
            [clojure.tools.cli :refer [parse-opts]]))

(defn flush 
  ([] 
   (when (ask-if-sure)
     (do
       (spit util/nquick-default-file "")
       (println "default note file cleaned."))))
  ([filename]
   (when (ask-if-sure)
     (let [fullpath (str util/nquick-directory "/" filename)]
       (if (and (not (= filename "default")) (.exists (io/file fullpath)))
         (do
           (io/delete-file (str util/nquick-directory "/" filename) "")
           (println (str filename " deleted!")))
         (println "file can't be deleted!"))))))

;; helper to ask if sure
(defn- ask-if-sure []
  (let [answer (read-line)]
    (cond 
     (= answer "y") true
     (= answer "n") false
     :else (do 
             (println "answer with y or n")
             (ask-if-sure)))))

(defn read
  ([] (println (slurp util/nquick-default-file)))
  ([filename] 
   (let [full-path (str util/nquick-directory "/" filename)]
     (if (not (.exists (io/file full-path)))
       (println "file does not exist!")
       (println (slurp full-path))))))

(defn write [s] 
  (do
    (println "Please write a title for note (blank for default): ")
    (write-title-helper s)))

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
        (println "that note already exists! pick another title. (blank for default)")
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
