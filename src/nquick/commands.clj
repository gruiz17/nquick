(ns nquick.commands
  (:require [nquick.util :as util]
            [clojure.java.io :as io]
            [clojure.pprint :as pprint]
            [clojure.string :as string]
            [clojure.tools.cli :refer [parse-opts]]))

;; helper to ask if sure
(defn- ask-if-sure []
  (do
    (println "r u sure")
    (let [answer (read-line)]
      (cond 
       (= answer "y") true
       (= answer "n") false
       :else (do 
               (println "answer with y or n")
               (ask-if-sure))))))

(defn purge 
  ([]
   (let [askval (ask-if-sure)]
     (if askval
       (do
         (spit util/nquick-default-file "")
         (println "default note file cleaned."))
       (println "aborted."))))
  ([filename]
   (let [askval (ask-if-sure)]
     (if askval
       (let [fullpath (str util/nquick-directory "/"  (util/mangle-name filename))]
         (if (and (not (= filename "default")) (.exists (io/file fullpath)))
           (do
             (io/delete-file (str util/nquick-directory "/" (util/mangle-name filename)) "")
             (println (str filename " deleted!")))
           (println "file can't be deleted!")))
       (println "aborted.")))))

(defn readnote
  ([] (println (slurp util/nquick-default-file)))
  ([filename] 
   (let [full-path (str util/nquick-directory "/" filename)]
     (if (not (.exists (io/file full-path)))
       (println "file does not exist!")
       (println (slurp full-path))))))

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

(defn write [s] 
  (do
    (println "Please write a title for note (blank for default): ")
    (write-title-helper s)))

(defn names [] 
  (doseq [i (range (count util/home-files-vec))]
    (println (get util/home-files-vec i))))

(def lines-for-help 
[{"command" "purge" "description" "Clears notes from default. Use flag '-n' with a filename to delete a certain note."}
 {"command" "readnote" "description" "Reads your notes from default. Use flag '-n' with a name to read a certain note."}
 {"command" "write" "description" "Write a note. Will prompt you for a title at the end. Leave blank to write more to default."}
 {"command" "names" "description" "List all the titles of notes that you have."}
 {"command" "help" "description" "Show this prompt you're seeing right now."}  
])

(defn help [] 
 (pprint/print-table lines-for-help))

(def command-map
  {"purge" purge
   "readnote" readnote
   "write" write
   "names" names
   "help" help })
