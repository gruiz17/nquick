(ns nquick.util
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(defn- user-prop [k]
  (System/getProperty (str "user." k)))

(def home-directory (user-prop "home"))
(def nquick-directory (str home-directory "/.nquick"))
(def nquick-default-file (str nquick-directory "/default.txt"))

(defn check-dir []
  (when (not (.isDirectory (io/file nquick-directory)))
    (do 
      (.mkdir (io/file nquick-directory))
      (spit nquick-default-file "")
      (println "dirs created"))))

(defn destroy-and-recreate []
  (do
    (spit nquick-default-file "")
    (println "default note file cleaned")))

(defn mangle-name [s]
  (->> s
       (string/join "-")
       (string/lower-case)))

;; for later
; (def cli-options
;   [["-p" "--port PORT" "Port number"
;     :default 80
;     :required "port number"
;     :parse-fn #(Integer/parseInt %)
;     :validate [#(< 0 % 0x10000) "must be number between 0 and 65536"]]
;    ["-v" nil "Verbosity level"
;     :id :verbosity
;     :default 0
;     :assoc-fn (fn [m k _] (update-in m [k] inc))]
;    ["-h" "--help" "lol"]
;    ["-f" "--file FILENAME" "Name of File"
;     :id :filename
;     :default util/nquick-default-file
;     :validate [#(nil? (re-find #"\s+" %)) "filename can't have whitespace"]]])
